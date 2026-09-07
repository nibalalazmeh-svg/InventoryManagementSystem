/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ui;

import model.item.Item;
//import model.item.ItemCategory;
import model.product.Product;
import model.productionLine.ProductionLine;
import service.InventoryService;

/**
 *
 * @author Nibal
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            InventoryService service = new InventoryService();

  
        service.loadItemsFromFile();
        service.loadProductsFromFile(); 
        service.loadLinesFromFile();
        service.loadTasksFromFile();
        
    
        if (service.getAllItems().isEmpty()) {
       
        }

        java.awt.EventQueue.invokeLater(() -> {
//            new ProductionSuperior(service).setVisible(true); 
//            new Manager(service).setVisible(true); 
              new Login(service).setVisible(true);
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//    try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) break;
//            }
//        } catch (Exception ex) {}
//
//        InventoryService service = new InventoryService();
//
//        // 1. تحميل المواد والخطوط (الأساسيات)
//        service.loadItemsFromFile();
//        service.loadLinesFromFile();
//
//        // =================================================================
//        // 2. (هام جداً) إعادة تعريف الكتالوج دائماً قبل تحميل المهام
//        // =================================================================
//        try {
//            // نحاول جلب المواد الخام (سواء من ملف أو سننشئها لاحقاً)
//            // ملاحظة: الأسماء هنا يجب أن تطابق ما حفظته في ItemDialog
//            Item screen = service.findItemByName("LCD Screen");
//            Item cpu = service.findItemByName("Intel CPU");
//            Item ram = service.findItemByName("8GB RAM");
//            Item wood = service.findItemByName("Oak Wood");
//
//            // تعريف منتج 1: لابتوب
//            // حتى لو المواد null حالياً، سننشئ المنتج للاسم فقط لكي ينجح تحميل المهام
//            Product laptop = new Product("Gaming Laptop");
//            if (screen != null && cpu != null && ram != null) {
//                laptop.addRequiredItem(screen, 1);
//                laptop.addRequiredItem(cpu, 1);
//                laptop.addRequiredItem(ram, 2);
//            }
//            service.addProductDefinition(laptop); // إضافة للكتالوج
//
//            // تعريف منتج 2: طاولة
//            Product table = new Product("Wooden Table");
//            if (wood != null) {
//                table.addRequiredItem(wood, 10);
//            }
//            service.addProductDefinition(table);
//            
//            // يمكنك إضافة المزيد من المنتجات هنا إذا كنت تستخدم منتجات أخرى
//
//        } catch (Exception e) {
//            System.out.println("Warning defining catalog: " + e.getMessage());
//        }
//
//        // =================================================================
//        // 3. الآن نحمل المهام (لأن الكتالوج أصبح جاهزاً بالأسماء)
//        // =================================================================
//        service.loadTasksFromFile();
//
//        // 4. إذا كان النظام فارغاً تماماً (أول مرة)، نضيف بيانات وهمية
//        if (service.getAllItems().isEmpty() && service.getAllProductionLines().isEmpty()) {
//            System.out.println("Fresh start. Seeding data...");
//            seedInitialData(service);
//        } else {
//            System.out.println("System loaded from files.");
//        }
//
//  
//        java.awt.EventQueue.invokeLater(() -> {
//             new Login().setVisible(true); 
//            // يمكنك تشغيل المدير أو المشرف
////             new Manager(service).setVisible(true); 
////             new ProductionSuperior(service).setVisible(true);
//        });
//    }
//
//    // دالة مساعدة لإضافة البيانات أول مرة
//    private static void seedInitialData(InventoryService service) {
//        try {
//            Item screen = new Item("LCD Screen", model.item.ItemCategory.ELECTRONICS, 50.0, 100, 10);
//            Item cpu = new Item("Intel CPU", model.item.ItemCategory.ELECTRONICS, 150.0, 100, 5);
//            Item ram = new Item("8GB RAM", model.item.ItemCategory.ELECTRONICS, 40.0, 200, 20);
//            Item wood = new Item("Oak Wood", model.item.ItemCategory.OTHER, 20.0, 500, 50);
//
//            service.addItem(screen);
//            service.addItem(cpu);
//            service.addItem(ram);
//            service.addItem(wood);
//
//            // أعدنا تعريفهم هنا لربطهم بالمواد الجديدة
//            Product laptop = new Product("Gaming Laptop");
//            laptop.addRequiredItem(screen, 1);
//            laptop.addRequiredItem(cpu, 1);
//            laptop.addRequiredItem(ram, 2);
//            service.addProductDefinition(laptop);
//
//            Product table = new Product("Wooden Table");
//            table.addRequiredItem(wood, 10);
//            service.addProductDefinition(table);
//
//            ProductionLine line1 = new ProductionLine("Electronics Line 1");
//            service.addProductionLine(line1);
//            
//            ProductionLine line2 = new ProductionLine("Furniture Line");
//            service.addProductionLine(line2);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 4. تشغيل الواجهة
        // سنبدأ بواجهة المشرف (ProductionSuperior) لتجربة Items و Tasks
//        java.awt.EventQueue.invokeLater(() -> {
//            // نمرر السيرفس للواجهة
//            
//            new ProductionSuperior(service).setVisible(true);
//            
//            // ملاحظة: إذا أردت البدء بمدير، استخدم:
////             new Manager(service).setVisible(true);
//        });
    }
    
}
