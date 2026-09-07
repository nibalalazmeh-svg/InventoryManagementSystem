package model.task;

import exception.TaskException;
import model.product.Product;
import model.productionLine.ProductionLine;
import model.task.TaskDelayOption;
import model.task.TaskStatus;
import service.InventoryService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import model.item.Item;

public class Task implements Runnable {

    private final int taskId;
    private static int idCounter = 1;

    private Product product;
    private int requiredQuantity;
    private String client;
    private LocalDate startDate;
    private LocalDate endDate;
    private model.task.TaskStatus status;
    private int progress;

    private ProductionLine productionLine;  //*****
    private model.task.TaskDelayOption delayOption;
    private InventoryService inventoryService;


    private final Map<Item, Integer> reservedItems = new HashMap<>(); //*** //***
    private boolean autoReserve;

    // simple flag to prevent starting same task multiple times
    private volatile boolean started = false;



    // Constructor
    public Task(Product product, int requiredQuantity, String client, ProductionLine productionLine, TaskDelayOption delayOption, boolean autoReserve, InventoryService inventoryService) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null"); //*************
        if (requiredQuantity <= 0) throw new IllegalArgumentException("Quantity must be positive"); //*********
        if (client == null || client.isBlank()) throw new IllegalArgumentException("Client cannot be null");

        this.taskId = generateUniqueId();
        this.product = product;
        this.requiredQuantity = requiredQuantity;
        this.client = client;
        this.productionLine = productionLine;
        this.status = TaskStatus.PENDING;
        this.progress = 0;
        this.delayOption = delayOption;
        this.autoReserve = autoReserve;
        this.inventoryService = inventoryService;
    }


    private static synchronized int generateUniqueId() {
        return idCounter++;
    }


    public int getTaskId() {
        return taskId;
    }

    public Product getProduct() {
        return product;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public String getClient() {
        return client;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getProgress() {
        return progress;
    }

    public ProductionLine getProductionLine() {
        return productionLine;
    }

    public TaskDelayOption getDelayOption() {
        return delayOption;
    }

    // mark started to avoid multiple starts
    public synchronized boolean markStarted() {
        if (started) return false;
        started = true;
        return true;
    }


    // Setters just if needed
    public synchronized void setProduct(Product product) {
        if (product == null) {
            throw new TaskException("Product cannot be null");

        }
        this.product = product;
    }

    public synchronized void setRequiredQuantity(int quantity) {
        if (quantity <= 0) {
            throw new TaskException("Quantity must be positive");

        }
        this.requiredQuantity = quantity;
    }

    public synchronized void setClient(String client) {
        if (client == null || client.isBlank()) {
            throw new TaskException("Client cannot be null");
        }
        this.client = client;
    }

    public synchronized void setProductionLine(ProductionLine productionLine) {
        if (productionLine == null)
            throw new TaskException("Production line cannot be null");
        this.productionLine = productionLine;
    }

    public synchronized void updateProgress(int completedUnits) {
        if(requiredQuantity > 0) {
            this.progress = (completedUnits * 100) / requiredQuantity;
        }
    }


    @Override
    public void run() {
        executeProduction();
    }

    public synchronized boolean checkInventory() {
        for (Map.Entry<Item, Integer> entry : product.getRequiredItems().entrySet()) {
            Item item = entry.getKey();
            int quantityNeeded = entry.getValue() * requiredQuantity;

            int available = item.getAvailableQuantity();
            int minimum = item.getMinimumAllowedQuantity();

            if (available - quantityNeeded < minimum) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean reserveMaterials() {
        if (!checkInventory()) {
            throw new TaskException("Not enough materials to reserve");
        }

        reservedItems.clear();
        for (Map.Entry<Item, Integer> entry : product.getRequiredItems().entrySet()) {
            Item item = entry.getKey();
            int quantityNeeded = entry.getValue() * requiredQuantity;
            item.reserve(quantityNeeded);
            reservedItems.put(item, quantityNeeded);
        }

        return true;
    }

    public synchronized void releaseMaterials() {
        for (Map.Entry<Item, Integer> entry : reservedItems.entrySet()) {
            Item item = entry.getKey();
            int qty = entry.getValue();
            item.adjustQuantity(qty);
        }
        reservedItems.clear();
    }


    private void executeProduction() {

        synchronized (this) {
            startDate = LocalDate.now();
            progress = 0;
            status = TaskStatus.Waiting_for_stock;
        }

        try {

            while (!checkInventory()) {
                if (delayOption == TaskDelayOption.CANCEL_IF_NOT_ENOUGH) {
                    synchronized (this) {
                        status = TaskStatus.CANCELLED;
                        releaseMaterials();
                    }
                    System.out.println("Task " + taskId + " cancelled due to insufficient.");
                    return;
                }


                status = TaskStatus.Waiting_for_stock;
                System.out.println("Task " + taskId + " is waiting for materials...");
                Thread.sleep(1000);
            }


            if (autoReserve) {
                reserveMaterials();
            }

            synchronized (this) {
                status = TaskStatus.In_Progress;
            }


            for (int i = 1; i <= requiredQuantity; i++) {
                if(!autoReserve) {
                    for (Map.Entry<Item, Integer> entry : product.getRequiredItems().entrySet()) {
                        Item item = entry.getKey();
                        int amountPerUnit = entry.getValue();
                        item.reserve(amountPerUnit);
                    }
                }


                Thread.sleep(100);
                updateProgress(i);
            }

            synchronized (this) {
                status = TaskStatus.COMPLETED;
                endDate = LocalDate.now();
                progress = 100;
            }

            if (productionLine != null) {
                productionLine.incrementProduced(requiredQuantity);
            }

            if (inventoryService != null) {
                inventoryService.addProducedProduct(product, requiredQuantity);
            }

        } catch (InterruptedException e) {
            synchronized (this) {
                status = TaskStatus.CANCELLED;
            }
            releaseMaterials();
            Thread.currentThread().interrupt();
        }
    }


    public synchronized void cancelTask() {

        if (status == TaskStatus.COMPLETED)
        {
            throw new TaskException("Cannot cancel a completed task");
        }
        releaseMaterials();
        status = TaskStatus.CANCELLED;
    }


    @Override
    public synchronized String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", product=" + product.getName() +
                ", requiredQuantity=" + requiredQuantity +
                ", client='" + client + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", progress=" + progress +
                ", productionLine=" +
                (productionLine != null ? productionLine.getName() : "None") + '\'' +
                ", productionLineId=" +
                (productionLine != null ? productionLine.getId() : "None") +
                '}';
    }
}
