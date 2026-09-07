package service;


import exception.InventoryException;
import model.item.Item;
import model.item.ItemCategory;
import model.product.Product;
import model.productionLine.ProductionLine;
import model.task.Task;
import model.task.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InventoryService {

    private final List<Item> items;
    private final Map<Product, Integer> producedProducts;
    private final List<ProductionLine> productionLines;
 private final List<Product> productCatalog; 


    public InventoryService() {
        this.items = new ArrayList<>();
        this.producedProducts = new HashMap<>();
        this.productionLines = new ArrayList<>();
        this.productCatalog = new ArrayList<>();
    

    }
    

 public synchronized void addProductDefinition(Product product) {
        if (product != null) {
            productCatalog.add(product);
        }
    }
  public synchronized List<Product> getAllProductDefinitions() {
        return new ArrayList<>(productCatalog);
    }
  
  
    public synchronized void addItem(Item item) {
        if (item == null) {
            throw new InventoryException("Item cannot be null");
        }
        items.add(item);
    }
   public synchronized boolean deleteItem(int id) {
     
        return items.removeIf(item -> item.getId() == id);
    }
    public synchronized List<Item> getAllItems() {
        return new ArrayList<>(items);
    }
    

    public synchronized Item findItemByName(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public synchronized List<Item> findItemsByCategory(ItemCategory category) {
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getCategory() == category) {
                result.add(item);
            }
        }

        return result;
    }

    public synchronized List<Item> getAvailableItems() {
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getAvailableQuantity() > 0) {
                result.add(item);
            }
        }

        return result;
    }

    public synchronized List<Item> getOutOfStockItems() {
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getAvailableQuantity() == 0) {
                result.add(item);
            }
        }

        return result;
    }

    public synchronized List<Item> getLowStockItems() {
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.isBelowThreshold()) {
                result.add(item);
            }
        }

        return result;
    }

    public synchronized void addProductionLine(ProductionLine line) {
        if (line == null) {
            throw new InventoryException("Production line cannot be null");
        }
        productionLines.add(line);
    }

    public synchronized List<ProductionLine> getAllProductionLines() {
        return new ArrayList<>(productionLines);
    }

    public synchronized List<ProductionLine> getLinesProducingProduct(Product product) {

        List<ProductionLine> result = new ArrayList<>();

        for (ProductionLine line : productionLines) {
            for (Task task : line.getTasks()) {
                if (task.getProduct().equals(product)
                        && task.getStatus() == TaskStatus.COMPLETED) {

                    result.add(line);
                    break;
                }
            }
        }

        return result;
    }
    

    


    public synchronized void assignTaskToLine(Task task, ProductionLine line) {
        if (task == null || line == null) {
            throw new InventoryException("Task or ProductionLine cannot be null");
        }
        line.addTask(task);
    }


    public synchronized void addProducedProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new InventoryException("Invalid product or quantity");
        }

        producedProducts.put(
                product,
                producedProducts.getOrDefault(product, 0) + quantity
        );
    }

    public synchronized int getProducedQuantity(Product product) {
        return producedProducts.getOrDefault(product, 0);
    }

    public synchronized Map<Product, Integer> getAllProducedProducts() {
        return new HashMap<>(producedProducts);
    }
    
     public void saveItemsToFile() {
      
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File("items.csv"))) {
       
            writer.println("id,name,category,price,available,min");
            
            for (Item item : items) {
              
                writer.printf("%d,%s,%s,%.2f,%d,%d%n",
                        item.getId(),
                        item.getName(),
                        item.getCategory(),
                        item.getPrice(),
                        item.getAvailableQuantity(),
                        item.getMinimumAllowedQuantity());
            }
            System.out.println("Items saved successfully to items.csv");
        } catch (java.io.IOException e) {
            System.err.println("Error saving items: " + e.getMessage());
        }
    }


    public void loadItemsFromFile() {
        java.io.File file = new java.io.File("items.csv");
        if (!file.exists()) return; 

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine();

            items.clear(); 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                
               
                if (parts.length == 6) {
                    String name = parts[1];
                    ItemCategory cat = ItemCategory.valueOf(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    int available = Integer.parseInt(parts[4]);
                    int min = Integer.parseInt(parts[5]);
                    
              
                    Item item = new Item(name, cat, price, available, min);
                    items.add(item);
                }
            }
            System.out.println("Items loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading items: " + e.getMessage());
        }
    }
public void saveTasksToFile() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File("tasks.csv"))) {
     
            writer.println("product_name,qty,client,line_name,status,progress");
            
            for (model.productionLine.ProductionLine line : productionLines) {
                for (model.task.Task task : line.getTasks()) {
                    writer.printf("%s,%d,%s,%s,%s,%d%n",
                            task.getProduct().getName(),
                            task.getRequiredQuantity(),
                            task.getClient(),
                            line.getName(),
                            task.getStatus(),
                            task.getProgress());
                }
            }
            System.out.println("Tasks saved successfully.");
        } catch (java.io.IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }
public void loadTasksFromFile() {
        java.io.File file = new java.io.File("tasks.csv");
        if (!file.exists()) return;

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); 

            while (scanner.hasNextLine()) {
                String lineStr = scanner.nextLine();
                String[] parts = lineStr.split(",");
                
                if (parts.length >= 6) {
                    String prodName = parts[0];
                    int qty = Integer.parseInt(parts[1]);
                    String client = parts[2];
                    String lineName = parts[3];
                    String statusStr = parts[4];
                    int progress = Integer.parseInt(parts[5]);

                    model.product.Product product = null;
                    for (model.product.Product p : productCatalog) {
                        if (p.getName().equalsIgnoreCase(prodName)) {
                            product = p;
                            break;
                        }
                    }
                    
     
                    if (product == null) {
                  
                        product = new model.product.Product(prodName);
                        
                        productCatalog.add(product);
                    }
            
                    model.productionLine.ProductionLine targetLine = null;
                    for (model.productionLine.ProductionLine l : productionLines) {
                        if (l.getName().equalsIgnoreCase(lineName)) {
                            targetLine = l;
                            break;
                        }
                    }

               
                    if (targetLine != null) {
                        model.task.Task task = new model.task.Task(
                                product, 
                                qty, 
                                client, 
                                targetLine, 
                                model.task.TaskDelayOption.WAIT_IF_NOT_ENOUGH, 
                                false, 
                                this
                        );
                        
                  

                        targetLine.addTask(task);
                        
                   
                        if (targetLine.getStatus() == model.productionLine.ProductionLineStatus.Active) {
                            targetLine.startLine();
                        }
                    }
                }
            }
            System.out.println("Tasks loaded.");
        } catch (Exception e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
    }
  public void saveLinesToFile() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File("lines.csv"))) {
            writer.println("id,name,status,total_produced");
            for (ProductionLine line : productionLines) {
                writer.printf("%d,%s,%s,%d%n",
                        line.getId(),
                        line.getName(),
                        line.getStatus(),
                        line.getTotalProduced());
            }
            System.out.println("Lines saved to lines.csv");
        } catch (java.io.IOException e) {
            System.err.println("Error saving lines: " + e.getMessage());
        }
    }
  public void loadLinesFromFile() {
        java.io.File file = new java.io.File("lines.csv");
        if (!file.exists()) return;

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); 
            
       
            
            while (scanner.hasNextLine()) {
                String lineStr = scanner.nextLine();
                String[] parts = lineStr.split(",");
                if (parts.length == 4) {
               
                    String name = parts[1];
                    String statusStr = parts[2];
                    
              
                    ProductionLine line = new ProductionLine(name);
                    line.setStatus(model.productionLine.ProductionLineStatus.valueOf(statusStr));
                    
          
                    addProductionLine(line);
                }
            }
            System.out.println("Lines loaded.");
        } catch (Exception e) {
            System.err.println("Error loading lines: " + e.getMessage());
        }
    }
  public void saveProductsToFile() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File("products.csv"))) {
            writer.println("product_name,ingredients"); 
            
            for (model.product.Product p : productCatalog) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getName()).append(",");
                
              
                for (java.util.Map.Entry<Item, Integer> entry : p.getRequiredItems().entrySet()) {
                    sb.append(entry.getKey().getName())
                      .append(":")
                      .append(entry.getValue())
                      .append(";");
                }
                writer.println(sb.toString());
            }
            System.out.println("Products (Catalog) saved.");
        } catch (Exception e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }

    public void loadProductsFromFile() {
        java.io.File file = new java.io.File("products.csv");
        if (!file.exists()) return;

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); 

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(","); 
                
                if (parts.length >= 2) {
                    String prodName = parts[2];
                    model.product.Product product = new model.product.Product(prodName);
                    
                    if (parts.length > 1 && !parts[1].isEmpty()) {
                  
                        String[] ingredients = parts[1].split(";");
                        for (String ing : ingredients) {
                            String[] itemData = ing.split(":"); 
                            if (itemData.length == 3) {
                                String itemName = itemData[2];
                                int qty = Integer.parseInt(itemData[3]);
                                
                            
                                Item item = findItemByName(itemName);
                                if (item != null) {
                                    product.addRequiredItem(item, qty);
                                }
                            }
                        }
                    }
                    addProductDefinition(product);
                }
            }
            System.out.println("Products (Catalog) loaded.");
        } catch (Exception e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
    }
    
  
}
