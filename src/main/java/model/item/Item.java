package model.item;

import exception.ItemException;
import model.item.ItemCategory;

public class Item {

    private final int id;
    private static int idCounter = 1;
    private String name;
    private ItemCategory category;
    private double price;
    private int availableQuantity;
    private int minimumAllowedQuantity;


    public Item(String name, ItemCategory category,
                double price, int availableQuantity, int minimumAllowedQuantity) {

        if (name == null || name.isBlank()) {
            throw new ItemException("Item name cannot be null or blank");
        }
        if (price < 0) {
            throw new ItemException("Item price cannot be negative");
        }
        if (availableQuantity < 0) {
            throw new ItemException("Item available quantity cannot be negative");
        }
        if (minimumAllowedQuantity < 0) {
            throw new ItemException("Item minimum allowed quantity cannot be negative");
        }

        this.id = generateUniqueId();
        this.name = name.trim();
        this.category = category == null ? ItemCategory.OTHER : category;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.minimumAllowedQuantity = minimumAllowedQuantity;
    }

    public static synchronized int generateUniqueId() {
        return idCounter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public ItemCategory getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }


    public synchronized int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getMinimumAllowedQuantity() {
        return minimumAllowedQuantity;
    }
    

    // All setters just if needed!

    public synchronized void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new ItemException("Item name cannot be null or blank");
        }
        this.name = name.trim();
    }

    public synchronized void setCategory(ItemCategory category) {
        if (category == null) {
            throw new ItemException("Item category cannot be null");
        }
        this.category = category;
    }

    public synchronized void setPrice(double price) {
        if (price < 0) {
            throw new ItemException("Item price cannot be negative");
        }
        this.price = price;
    }

    public synchronized void setMinimumAllowedQuantity(int minimumAllowedQuantity) {
        if (minimumAllowedQuantity < 0) {
            throw new ItemException("Item minimum allowed quantity cannot be negative");
        }
        this.minimumAllowedQuantity = minimumAllowedQuantity;
    }

    public synchronized boolean adjustQuantity(int amount) {
        int newQuantity = this.availableQuantity + amount;
        if (newQuantity < 0) {
            return false;
        }
        this.availableQuantity = newQuantity;
        return true;
    }

    public synchronized boolean reserve(int amount) {
        if (amount <= 0) {
            throw new ItemException("Reservation amount must be positive");
        }

        if (availableQuantity - amount < 0) {
            throw new ItemException("Not enough stock available to reserve " + amount + " items");
        }
        if(availableQuantity - amount <minimumAllowedQuantity){
        throw new ItemException("its below the minimum" +minimumAllowedQuantity);
        
        }
        availableQuantity -= amount;
        return true;
    }

    public boolean isBelowThreshold() {
        return availableQuantity <= minimumAllowedQuantity;
    }

    public String getStockStatusMessage() {
        if (availableQuantity == 0) {
            return "Stock is completely out!";
        } else if (availableQuantity <= minimumAllowedQuantity) {
            return "Stock is low (" + availableQuantity + " items left).";
        } else {
            return "Stock is sufficient (" + availableQuantity + " items available).";
        }
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +
                ", minimumAllowedQuantity=" + minimumAllowedQuantity +
                '}';
    }
}
