package model.product;

import exception.ProductException;
import model.item.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Product {

    private final int id;
    private static int idCounter = 1;

    private String name;

    private final Map<Item, Integer> requiredItems;

    public Product(String name) {

        if (name == null || name.isBlank()) {
            throw new ProductException("Product name cannot be null or blank");
        }

        this.id = generateUniqueId();
        this.name = name.trim();
        this.requiredItems = new HashMap<>();
    }

    public static synchronized int generateUniqueId() {
        return idCounter++;
    }

    public int getId() {
        return id;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new ProductException("Product name cannot be null or blank");
        }
        this.name = name.trim();
    }


    public synchronized void addRequiredItem(Item item, int quantity) {

        if (item == null) {
            throw new ProductException("Item cannot be null");
        }

        if (quantity <= 0) {
            throw new ProductException("Required quantity must be positive");
        }

        requiredItems.put(item, quantity);
    }

  
    public synchronized void removeRequiredItem(Item item) {

        if (item == null) {
            throw new ProductException("Item cannot be null");
        }

        requiredItems.remove(item);
    }


    public synchronized Map<Item, Integer> getRequiredItems() {
        return Collections.unmodifiableMap(requiredItems);
    }

  
    public synchronized double calculateCost() {
        double totalCost = 0;

        for (Map.Entry<Item, Integer> entry : requiredItems.entrySet()) {
            Item item = entry.getKey();
            int qty = entry.getValue();
            totalCost += item.getPrice() * qty;
        }

        return totalCost;
    }

    public synchronized boolean hasNoRequiredItems() {
        return requiredItems.isEmpty();
    }

    @Override
    public synchronized String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", requiredItems=" + requiredItems +
                '}';
    }
}
