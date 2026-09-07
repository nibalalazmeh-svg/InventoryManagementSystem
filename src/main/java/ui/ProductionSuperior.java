/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import javax.swing.table.DefaultTableModel;
import model.productionLine.ProductionLine;
import model.task.Task;
import service.InventoryService;

/**
 *
 * @author Nibal
 */
public class ProductionSuperior extends javax.swing.JFrame {
    private InventoryService inventoryService;
    /**
     * Creates new form ProductionSuperior
     */
    public ProductionSuperior(InventoryService service) {
            this.inventoryService = service;
        initComponents();
         jTable2.setRowSelectionAllowed(true);
        jTable2.setColumnSelectionAllowed(false);
                jTable2.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent event) {
            
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = jTable2.getSelectedRow();
                    if (selectedRow != -1) {
                       
                        int taskId = (int) jTable2.getValueAt(selectedRow, 0);
                        
                  
                        model.task.Task selectedTask = findTaskById(taskId);
                        
                      
                        updateTaskItemsTable(selectedTask);
                    }
                }
            }
        });
        
      
        
        
       
javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {

    refreshTaskTable(); 
    
   
    if(jTabbedPane1.getSelectedIndex() == 0) { 
         refreshItemTable();
    }
});
timer.start();
        refreshItemTable();
        
        

        
        
    }
 public ProductionSuperior() {
        initComponents();
    }
 
 
 
 

 
 
 
   public void refreshItemTable() {

 DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
 int savedRow = jTable1.getSelectedRow();
        model.setRowCount(0);

        
        boolean showAvailable = jCheckBox2.isSelected();
        boolean showOutOfStock = jCheckBox3.isSelected();
        boolean showBelowMin = jCheckBox4.isSelected();


        boolean showAll = !showAvailable && !showOutOfStock && !showBelowMin;

        for (model.item.Item item : inventoryService.getAllItems()) {
            boolean shouldAdd = false;

            if (showAll) {
                shouldAdd = true;
            } else {
             
                if (showAvailable && item.getAvailableQuantity() > 0) shouldAdd = true;
                if (showOutOfStock && item.getAvailableQuantity() == 0) shouldAdd = true;
                if (showBelowMin && item.isBelowThreshold()) shouldAdd = true;
            }

       
            if (shouldAdd) {
                model.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getCategory(),
                    item.getAvailableQuantity(),
                    item.getPrice(),
                    item.getMinimumAllowedQuantity()
                });
            }
        }
          if (savedRow != -1 && savedRow < jTable1.getRowCount()) {
          
            jTable1.setRowSelectionInterval(savedRow, savedRow);
    }
   }
   
   
   //هون تحديث جدول ال task
   
   
   
   public void refreshTaskTable() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int savedRow = jTable2.getSelectedRow();
        model.setRowCount(0); 

  
        for (ProductionLine line : inventoryService.getAllProductionLines()) {
            for (Task task : line.getTasks()) {
                
 

                model.addRow(new Object[]{
                    task.getTaskId(),
                    task.getProduct().getName(),
                    task.getRequiredQuantity(),
                    task.getClient(),
                    line.getName(),   
                    task.getStatus(),    
                    task.getProgress() + "%", 

                });
                
            }
        }
           if (savedRow != -1 && savedRow < jTable2.getRowCount()) {
          
            jTable2.setRowSelectionInterval(savedRow, savedRow);
        }
   }
   
   
   
   
   
   
   
   
   private model.task.Task findTaskById(int taskId) {
        for (model.productionLine.ProductionLine line : inventoryService.getAllProductionLines()) {
            for (model.task.Task t : line.getTasks()) {
                if (t.getTaskId() == taskId) {
                    return t;
                }
            }
        }
        return null;
    }
   
   
   
   
   
   
   
   
   
   
   
   
   // دالة لتحديث الجدول السفلي بناءً على المهمة المختارة
    private void updateTaskItemsTable(model.task.Task task) {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);

        if (task == null) return;


        model.product.Product product = task.getProduct();
        int taskQty = task.getRequiredQuantity();

        for (java.util.Map.Entry<model.item.Item, Integer> entry : product.getRequiredItems().entrySet()) {
            model.item.Item item = entry.getKey();
            int qtyPerUnit = entry.getValue();
            int totalRequired = qtyPerUnit * taskQty; 
            
            String status = (item.getAvailableQuantity() >= totalRequired) ? "Available" : "Shortage";
            


            model.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getPrice(),
                totalRequired,       
                item.getAvailableQuantity(), 
                status              
            });
        }
    }
   
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton9 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        textArea2 = new java.awt.TextArea();
        jLabel8 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jScrollPane2.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Inventory Management System");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 500, 70));

        jPanel1.setBackground(new java.awt.Color(21, 21, 40));
        jPanel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jTable1.setBackground(new java.awt.Color(220, 233, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Category", "Quantity", "Price", "MinQty"
            }
        ));
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(220, 233, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("Save State");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(220, 233, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setText("Update ");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(220, 233, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton5.setText("Add");
        jButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(220, 233, 255));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton10.setText("Delete");
        jButton10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTextField2.setText("Search...");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FOOD", "CLOTHING", "ELECTRONICS", "OTHER" }));

        jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Show Avaliable");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setText("Show out of Stock");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox4.setText("Show Below Minimum");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jButton9.setText("Search");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(220, 233, 255));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton14.setText("LogOut");
        jButton14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBox4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton10)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jButton14)
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Items", jPanel1);

        jPanel2.setBackground(new java.awt.Color(21, 21, 40));

        jTable2.setBackground(new java.awt.Color(220, 233, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "TaskId", "Product", "Quantity", "Client", "Production Line", "Status", "Progress", "Required Items"
            }
        ));
        jTable2.setCellSelectionEnabled(true);
        jTable2.setGridColor(new java.awt.Color(204, 204, 204));
        jTable2.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTable2.setShowGrid(true);
        jTable2.setSurrendersFocusOnKeystroke(true);
        jScrollPane3.setViewportView(jTable2);

        jTable3.setBackground(new java.awt.Color(220, 233, 255));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Item Id", "Name", "Catogary", "price", "Required Quantity", "Available Quantity", "Status"
            }
        ));
        jTable3.setGridColor(new java.awt.Color(204, 204, 204));
        jTable3.setShowGrid(true);
        jTable3.setSurrendersFocusOnKeystroke(true);
        jScrollPane4.setViewportView(jTable3);

        jButton3.setText("Add Task");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Edit Task");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton7.setText("View Task Details");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton6.setText("Cancel Task");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton11.setText("Save State");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Add Product");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jButton12)
                        .addGap(28, 28, 28)
                        .addComponent(jButton3)
                        .addGap(29, 29, 29)
                        .addComponent(jButton2)
                        .addGap(33, 33, 33)
                        .addComponent(jButton7)
                        .addGap(33, 33, 33)
                        .addComponent(jButton6)
                        .addGap(28, 28, 28)
                        .addComponent(jButton11)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton7)
                    .addComponent(jButton6)
                    .addComponent(jButton11)
                    .addComponent(jButton12))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tasks", jPanel2);

        jPanel3.setBackground(new java.awt.Color(21, 21, 40));

        textArea2.setBackground(new java.awt.Color(220, 233, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Report");

        jButton8.setText("Generate full Report");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jButton8)))
                .addContainerGap(265, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(50, 50, 50)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jTabbedPane1.addTab("Report", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 820, 480));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Nibal\\Documents\\NetBeansProjects\\PIMS\\src\\main\\image\\Gemini_Generated_Image_gg0kr4gg0kr4gg0k.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 576));

        pack();
    }// </editor-fold>//GEN-END:initComponents
     
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

     inventoryService.saveItemsToFile();
           javax.swing.JOptionPane.showMessageDialog(this, "Data Saved to items.csv");
    }//GEN-LAST:event_jButton1ActionPerformed
// زر  add item 
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ItemDialog dialog = new ItemDialog(this, true, inventoryService);
        
      
        dialog.setVisible(true);
        
     
        refreshItemTable();
    }//GEN-LAST:event_jButton5ActionPerformed
// زر البحث حسب الاسم والفئة
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
          String searchText = jTextField2.getText().trim().toLowerCase();
        String selectedCategory = jComboBox2.getSelectedItem().toString();
        
     
        if(searchText.equals("search...")) searchText = "";

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (model.item.Item item : inventoryService.getAllItems()) {
       
            boolean nameMatch = item.getName().toLowerCase().contains(searchText);
            boolean catMatch = item.getCategory().toString().equalsIgnoreCase(selectedCategory);
            

            if (nameMatch && catMatch) {
                model.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getCategory(),
                    item.getAvailableQuantity(),
                    item.getPrice(),
                    item.getMinimumAllowedQuantity()
                });
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed
//زر ال update
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
            int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Select an item first!");
            return;
        }

     
        int itemId = (int) jTable1.getValueAt(selectedRow, 0);
        model.item.Item selectedItem = null;
        for (model.item.Item item : inventoryService.getAllItems()) {
            if (item.getId() == itemId) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem != null) {
     
            String input = javax.swing.JOptionPane.showInputDialog(this, 
                    "Current Stock: " + selectedItem.getAvailableQuantity() + "\n" +
                    "Enter amount to ADD (positive) or REMOVE (negative):", "0");
            
            if (input != null && !input.isEmpty()) {
                try {
                    int amount = Integer.parseInt(input);
                    
             
                    boolean success = selectedItem.adjustQuantity(amount);
                    
                    if (success) {
                        refreshItemTable(); 
                        javax.swing.JOptionPane.showMessageDialog(this, "Stock Updated!");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(this, "Error: Result cannot be negative!");
                    }
                } catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Invalid Number!");
                }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed
// زر الحذف
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
         int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an item to delete!");
            return;
        }

        
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Delete", javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
          
            int itemId = (int) jTable1.getValueAt(selectedRow, 0);

           
            inventoryService.deleteItem(itemId);

        
            refreshItemTable();
            javax.swing.JOptionPane.showMessageDialog(this, "Item Deleted.");
        }
    }//GEN-LAST:event_jButton10ActionPerformed
//زر show Available
    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
   refreshItemTable();
    }//GEN-LAST:event_jCheckBox2ActionPerformed
// زر show out of stock
    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
      refreshItemTable();
    }//GEN-LAST:event_jCheckBox3ActionPerformed
// زر  show below Minimum
    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
     refreshItemTable();
    }//GEN-LAST:event_jCheckBox4ActionPerformed
// زر addTask
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
     AddTaskDialog dialog = new AddTaskDialog(this, true, inventoryService);
        
    
        dialog.setVisible(true);
        
    
        refreshTaskTable();
    }//GEN-LAST:event_jButton3ActionPerformed

    
    
    
    
    
    // زر  ال cancel task
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a task to cancel!");
            return;
        }

        int taskId = (int) jTable2.getValueAt(selectedRow, 0);
        model.task.Task task = findTaskById(taskId);

        if (task != null) {
      
            if (task.getStatus() == model.task.TaskStatus.COMPLETED) {
                javax.swing.JOptionPane.showMessageDialog(this, "Cannot cancel a completed task!");
                return;
            }

         
            int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Cancel Task #" + taskId + "? Materials will be released.", "Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
            
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                try {
              
                    task.cancelTask();
                    
         
                    refreshTaskTable();
                    refreshItemTable();
                    javax.swing.JOptionPane.showMessageDialog(this, "Task Cancelled Successfully.");
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed
// زر showDetails
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a task!");
            return;
        }

        int taskId = (int) jTable2.getValueAt(selectedRow, 0);
        model.task.Task task = findTaskById(taskId);
        
        if (task != null) {
            new TaskDetailsDialog(this, true, task).setVisible(true);
        }
    }//GEN-LAST:event_jButton7ActionPerformed
// زر Edit task
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
          int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a task to edit!");
            return;
        }

        int taskId = (int) jTable2.getValueAt(selectedRow, 0);
        model.task.Task task = findTaskById(taskId);

        if (task != null) {
          
            if (task.getStatus() != model.task.TaskStatus.PENDING && task.getStatus() != model.task.TaskStatus.Waiting_for_stock) {
                javax.swing.JOptionPane.showMessageDialog(this, "Cannot edit a task that is already running or completed!");
                return;
            }}
           String newQtyStr = javax.swing.JOptionPane.showInputDialog(this, "Enter new quantity:", task.getRequiredQuantity());
            if (newQtyStr != null) {
                try {
                    int newQty = Integer.parseInt(newQtyStr);
                    task.setRequiredQuantity(newQty); 
                    refreshTaskTable();
                    javax.swing.JOptionPane.showMessageDialog(this, "Task Updated!");
                } catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Invalid Number!");
                }
            }
        
    }//GEN-LAST:event_jButton2ActionPerformed
//زر لل report 
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
       String report = ""; 
        

        report += "============= FINAL REPORT =============\n";
        report += "Date: " + java.time.LocalDate.now() + "\n\n";

  
        report += "--- 1. STOCK STATUS ---\n";
        for (model.item.Item item : inventoryService.getAllItems()) {
            report += "- " + item.getName() + ": " + item.getAvailableQuantity() + " units";
            
      
            if (item.getAvailableQuantity() <= item.getMinimumAllowedQuantity()) {
                report += " [LOW STOCK!]";
            }
            report += "\n";
        }
        report += "\n";

 
        report += "--- 2. PRODUCTION LINES ---\n";
        for (model.productionLine.ProductionLine line : inventoryService.getAllProductionLines()) {
            report += "- Line: " + line.getName() + "\n";
            report += "  Status: " + line.getStatus() + "\n";
            report += "  Total Produced: " + line.getTotalProduced() + "\n";
            report += "--------------------\n";
        }
        report += "\n";


        report += "--- 3. RECENT TASKS ---\n";
        for (model.productionLine.ProductionLine line : inventoryService.getAllProductionLines()) {
            for (model.task.Task task : line.getTasks()) {
                report += "Task #" + task.getTaskId() + " (" + task.getProduct().getName() + ") -> " + task.getStatus() + "\n";
            }
        }
 

textArea2.setText(report);


        textArea2.setText(report);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
      inventoryService.saveTasksToFile();
      
    }//GEN-LAST:event_jButton11ActionPerformed
// زر add Product
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
          new AddProductDialog(this, true, inventoryService).setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
     this.dispose();
    }//GEN-LAST:event_jButton14ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProductionSuperior.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductionSuperior.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductionSuperior.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductionSuperior.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductionSuperior().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField2;
    private java.awt.TextArea textArea2;
    // End of variables declaration//GEN-END:variables
}
