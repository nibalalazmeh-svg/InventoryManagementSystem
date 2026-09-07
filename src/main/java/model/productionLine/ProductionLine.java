package model.productionLine;

import exception.ProductionLineException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.product.Product;
import model.task.Task;
import model.task.TaskDelayOption;
import model.task.TaskStatus;
import model.productionLine.ProductionLineStatus;

public class ProductionLine implements Runnable {
    private final int id;
    private static int idCounter = 1;

    private String name;
    private model.productionLine.ProductionLineStatus status;
    private final List<Task> tasks;

    private int totalProduced;
    private boolean running;

    public ProductionLine(String name) {
        if (name == null || name.isBlank()) {
            throw new ProductionLineException("Production line name cannot be null or blank");
        }

        this.id = generateUniqueId();
        this.name = name.trim();
        this.status = ProductionLineStatus.Active;
        this.tasks = new ArrayList<>();
        this.running = false;
        this.totalProduced = 0;
    }

    private static synchronized int generateUniqueId() {
        return idCounter++;
    }


    public int getId() {
        return id;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized ProductionLineStatus getStatus() {
        return status;
    }

    public synchronized int getTotalProduced() {
        return totalProduced;
    }

    public synchronized List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public synchronized void setStatus(ProductionLineStatus newStatus) {
        if (newStatus == null) return;

        switch (newStatus) {
            case Active -> {
                if (status != ProductionLineStatus.Active) {
                    startLine();
                }
            }
            case Stopped -> {
                if (status != ProductionLineStatus.Stopped) {
                    stopLine();
                }
            }
            case Maintenance -> {
                stopLine();
                System.out.println("Line under maintenance...");
            }
        }

    }

    public synchronized void addTask(Task task) {
        if (task == null) {
            throw new ProductionLineException("Task cannot be null ");
        }
        if (status != ProductionLineStatus.Active) {
            throw new ProductionLineException("Cannot add task to inactive production line");
        }
        tasks.add(task);
    }


    public synchronized void removeTask(Task task) {
        if (tasks.contains(task) && status == ProductionLineStatus.Active) {
            tasks.remove(task);
        } else {
            throw new ProductionLineException("Cannot delete task that's not exisit or non active");
        }
    }

    public synchronized void cancelTask(Task task) {
        if (tasks.contains(task) && status == ProductionLineStatus.Active) {
            task.cancelTask();
            System.out.println("Task " + task.getTaskId() + " cancelled.");
        }
    }

    public synchronized void startLine() {
        if (status != ProductionLineStatus.Active) {
            throw new ProductionLineException("Production line is not active");
        }

        if (running) return;

        running = true;
        new Thread(this, "ProductLine-" + name).start();
    }

    public synchronized void stopLine() {
        running = false;
        status = ProductionLineStatus.Stopped;
    }

    public synchronized void setMaintenance() {
        status = ProductionLineStatus.Maintenance;
        running = false;
    }
    public synchronized void incrementProduced(int qty) {
        totalProduced += qty;
    }


    private void executeTask(Task task) {
        try {
//            while (!task.checkInventory() && task.getdelyOption() == TaskDelayOption.WAIT_IF_NOT_ENOUGH) {
//                System.out.println("Task " + task.getTaskId() + " waiting for materials...");
//                Thread.sleep(500);
//            }
////            task.run();
//            new Thread(task).start();
            // mark started to avoid duplicates
            if (!task.markStarted()) {
                return; // already started previously
            }

            // Start the task in a new thread; Task will update line's production when complete
            new Thread(task).start();

            // DO NOT check task.getStatus() here to update totalProduced.
            // Task itself will call productionLine.incrementProduced(...) when done.

//            synchronized (this) {
//                if (task.getStatus() == TaskStatus.COMPLETED) {
//                    totalProduced += task.getRequiredQuantity();
//                }
//            }
        }catch (Exception e) {
            System.err.println("Error executing task " + task.getTaskId() + ": " + e.getMessage());
        }
    }

    public void executeNextTask() {
        Task taskToRun = null;
        synchronized (this) {
            for (Task t : tasks) {
                if (t.getStatus() == TaskStatus.PENDING) {
                    taskToRun = t;
                    break;
                }

            }

        }
        if (taskToRun != null) {
            executeTask(taskToRun);
        }
    }

    private void sleepBriefly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        while (running) {
            executeNextTask();
            sleepBriefly();
        }

    }

    public synchronized List<Task> getTasksByProduct(Product product) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getProduct().equals(product)) {
                result.add(task);
            }
        }
        return result;
    }

    public synchronized List<Product> getProducedProducts() {
        Set<Product> produced = new HashSet<>();
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.COMPLETED) {
                produced.add(task.getProduct());
            }
        }
        return new ArrayList<>(produced);
    }


    public synchronized double getLinePerformance() {
        if (tasks.isEmpty()) return 0;

        double totalProgress = 0;
        for (Task task : tasks) {
            totalProgress += task.getProgress();
        }
        return totalProgress / tasks.size();
    }


    public synchronized String toString() {
        return "ProductionLine{" +
                "lineId=" + id +
                ", name='" + name + '\'' +
                ", running=" + running +
                ", tasksCount=" + tasks.size() +
                ", totalProduced=" + totalProduced +
                '}';
    }

}
