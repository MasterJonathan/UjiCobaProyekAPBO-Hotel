/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaapplication44;

import java.awt.Component;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kevin Jonathan
 */
public class NewJFrame_main extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame_main
     */
    
    private static final String PORT = "3306";
    private static final String HOSTNAME = "localhost";
    private static final String DB_NAME = "aplikasi_hotel";
    private static final String URL = "jdbc:mysql://"+HOSTNAME+":"+PORT+"/"+DB_NAME;
//    private static final String URL = "jdbc:mysql://localhost:3306/aplikasi_hotel";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public static Connection con;
    public static Statement statement;
    public static ResultSet resultSet;
    
    DefaultListModel<String> modelList_status;
    DefaultComboBoxModel<String> modelComboBox_jenisKamar;
    DefaultTableModel modelTable_data;


    Customer stageCust;
    int stageHarga = 0;

    // Menyimpan ID kamar yang akan diproses
    ArrayList<Integer> stageKamar;
    ArrayList<Integer> kamarTerpesan;


    ArrayList<Customer> arrCust;
    ArrayList<Kamar> arrKamar;

   
    
    public NewJFrame_main() {
        initComponents();
        doreset();
        
       
        
        // Inisialisasi ArrayList
        stageKamar = new  ArrayList<Integer>();
        
        arrKamar = new ArrayList<Kamar>();
        arrCust = new ArrayList<Customer>();
        
        // Inisiasi List
        modelList_status = new DefaultListModel<String>();
        jList1_listStatus.setModel(modelList_status);
        
        
        // Inisiasi ComboBox
        modelComboBox_jenisKamar = new DefaultComboBoxModel();
        jComboBox1_jenisKamar.setModel(modelComboBox_jenisKamar);
        
        
        // Inisialisasi Tabel
        
        modelTable_data = new DefaultTableModel();
        modelTable_data.addColumn("Kode");
        modelTable_data.addColumn("Nama Customer");
        modelTable_data.addColumn("Nomor Kamar");
        modelTable_data.addColumn("Jumlah Tamu");
        modelTable_data.addColumn("AddOn");
        modelTable_data.addColumn("Tanggal Checkin");
        modelTable_data.addColumn("Tanggal Checkout");
        modelTable_data.addColumn("Harga Total");
        jTable1_HTrans.setModel(modelTable_data);
        
        loadComponent();
        
    }
    
    public void loadComponent() {
        Kamar obj1;
        String query;
        
        jTextField1_TotalBayar.setEditable(false);
        jTextArea1_rincianPesanan.setEditable(false);

        jTextField1_TotalBayar.setText(stageHarga+"");
        
        modelComboBox_jenisKamar.removeAllElements();
        modelList_status.removeAllElements();
        
        // Input Kamar (Combo Box)
        try {
            con = DriverManager.getConnection(URL,USERNAME, PASSWORD);
            
            query = "SELECT k.id_kamar AS `ID`, kk.nama_kategori AS `Kategori`, kk.harga_kategori AS `Harga` " +
                   " FROM kamar k, kategori_kamar kk" +
                   " WHERE k.id_kategori = kk.id_kategori" +
                   " GROUP BY `ID`";
            
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                obj1 = new Kamar(resultSet.getString("ID"),
                                 resultSet.getString("Kategori"),
                                resultSet.getInt("Harga")
                                );
//                System.out.println(resultSet.getString("ID") + " - " +
//                                 resultSet.getString("Kategori") + " - " +
//                                resultSet.getInt("Harga"));
                modelComboBox_jenisKamar.addElement(obj1.toString());
            }
            
            statement.close();
            resultSet.close();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        
        
        try {
            con = DriverManager.getConnection(URL,USERNAME, PASSWORD);
            query = "SELECT k.id_kamar AS `ID`, kk.nama_kategori AS `Kategori`, kk.harga_kategori AS `Harga`" +
                   " FROM kamar k, kategori_kamar kk" +
                   " WHERE k.id_kategori = kk.id_kategori AND k.status_kamar != \"0\"" +
                   " GROUP BY `ID`";
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
//                modelList_status.addElement(arrKamar.get(i).getNama() + " - " + arrKamar.get(i).getTipe() + " - " + arrKamar.get(i).getHarga() + " - " + occupied);
            }
            
            statement.close();
            resultSet.close();
            con.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        
//        for (int i = 0; i < arrKamar.size(); i++) {
//            String occupied = "";
//            if (arrKamar.get(i).getStatus() == 0) {
//                occupied = "Kosong";
//
//                
//                boolean notInStage = true;
//                
//                for (int j = 0; j < stageKamar.size(); j++) {
//                    if (arrKamar.get(i).getId() == stageKamar.get(j)) {
//                        notInStage = false;
//                    } 
//                }
//                
//                
//                if (notInStage) {
//                    // Load ke ComboBox untuk Kamar yang tersedia
//                    modelComboBox_jenisKamar.addElement(arrKamar.get(i).getNama() + " - " + arrKamar.get(i).getTipe() + " - " + arrKamar.get(i).getHarga());
//                }
//
//
//            } else {
//                occupied = "Terisi";
//            }
            
            // Load ke list untuk setiap status yang ada.
//            modelList_status.addElement(arrKamar.get(i).getNama() + " - " + arrKamar.get(i).getTipe() + " - " + arrKamar.get(i).getHarga() + " - " + occupied);
//        }

        jDateChooser1.setDate(new Date());
    }
    
    public void updateRincian(String ID, String tipe, int jumOrang, int harga) {
        String textAreaTampil;
        
        // Mendapatkan Nama Customer untuk ditampilkan
        String namaCust = jTextField1_nama.getText();
        textAreaTampil = "NAMA : " + namaCust + "\n";
        
        // Detail Kamar
        textAreaTampil += "[" + ID + " - " + tipe + " - " + jumOrang + " orang - " + harga + "]\n";
        
        if(jCheckBox2.isSelected()) {
            textAreaTampil += "- Breakfast for " + jumOrang + " @Rp. " + (10000*jumOrang);
            stageHarga = harga + (10000 * jumOrang);
        }
        
        
//        for (int i = 0; i < stageKamar.size(); i++) {
//            for (int j = 0; j < arrKamar.size(); j++) {    
//                if (stageKamar.get(i).equals(arrKamar.get(j).getId())) {
//                    String namaKamar = arrKamar.get(j).getNama();
//                    String jenisKamar = arrKamar.get(j).getTipe();
//                    int hargaKamar = arrKamar.get(j).getHarga();
//                    int jumlahOrang = arrKamar.get(j).getJumlahOrang();
//
//                    ArrayList<AddOn> daftarAddOn;
//
//                    textAreaTampil += "[" + namaKamar + " - " + jenisKamar + " - " + jumlahOrang + " orang - " + hargaKamar + "]\n"; 
//
//                    for (int k = 0; k < arrKamar.get(j).getAddOnTerpasang().size(); k++) {
//                        daftarAddOn = arrKamar.get(j).getAddOnTerpasang();
//                        textAreaTampil += "(" + (k+1) + ") " + daftarAddOn.get(k).getNama() + " - " + daftarAddOn.get(k).getHarga() + "\n";
//                    }  
//                } 
//            }
//        }
        
        System.out.println("anjasioaufdf a");
        jTextArea1_rincianPesanan.append(textAreaTampil);       
                
    }
    
    public void doreset() {
        
        // Reset Nama, Jenis, Spinner
        jTextField1_nama.setText("");
        jComboBox1_jenisKamar.setSelectedIndex(0);
        jSpinner1_jumlahKamar.setValue(1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1_HTrans = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1_nama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1_jenisKamar = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jSpinner1_jumlahKamar = new javax.swing.JSpinner();
        jButton2_tambahKamar = new javax.swing.JButton();
        jPanel1_AddOn = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1_rincianPesanan = new javax.swing.JTextArea();
        jButton1_checkin = new javax.swing.JButton();
        jButton2_Batal = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextField1_TotalBayar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1_listStatus = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jButton_checkout = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1_HTrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1_HTrans);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setText("Nama");

        jTextField1_nama.setText("jTextField1");
        jTextField1_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1_namaActionPerformed(evt);
            }
        });

        jLabel3.setText("Kamar");

        jComboBox1_jenisKamar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kamar 1 - Reguler - 500000 ", "Kamar 2 - Reguler - 500000", "Kamar 3 - Reguler - 500000 ", "Kamar 4 - VIP - 1000000", "Kamar 5 - VIP - 1000000" }));
        jComboBox1_jenisKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1_jenisKamarActionPerformed(evt);
            }
        });

        jLabel10.setText("Jumlah Orang ");

        jSpinner1_jumlahKamar.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5, 1));

        jButton2_tambahKamar.setText("Tambah Kamar");
        jButton2_tambahKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_tambahKamarActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Breakfast - Rp. 10000");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Add On");

        javax.swing.GroupLayout jPanel1_AddOnLayout = new javax.swing.GroupLayout(jPanel1_AddOn);
        jPanel1_AddOn.setLayout(jPanel1_AddOnLayout);
        jPanel1_AddOnLayout.setHorizontalGroup(
            jPanel1_AddOnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_AddOnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1_AddOnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jCheckBox2))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel1_AddOnLayout.setVerticalGroup(
            jPanel1_AddOnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1_AddOnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox1_jenisKamar, javax.swing.GroupLayout.Alignment.LEADING, 0, 215, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinner1_jumlahKamar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1_nama, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2_tambahKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1_AddOn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1_jenisKamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSpinner1_jumlahKamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1_AddOn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton2_tambahKamar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel5.setText("Rincian Pesanan");

        jTextArea1_rincianPesanan.setColumns(20);
        jTextArea1_rincianPesanan.setRows(5);
        jTextArea1_rincianPesanan.setMargin(new java.awt.Insets(4, 6, 4, 6));
        jScrollPane1.setViewportView(jTextArea1_rincianPesanan);

        jButton1_checkin.setText("Check In");
        jButton1_checkin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_checkinActionPerformed(evt);
            }
        });

        jButton2_Batal.setText("Batal");
        jButton2_Batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_BatalActionPerformed(evt);
            }
        });

        jLabel12.setText("TOTAL :");

        jTextField1_TotalBayar.setText("jTextField1");

        jLabel2.setText("TGL Checkin");

        jLabel7.setText("TGL Checkout");

        jDateChooser1.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1_TotalBayar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2_Batal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1_checkin))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField1_TotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(92, 92, 92)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2_Batal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1_checkin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jList1_listStatus.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1_listStatus);

        jLabel8.setText("Status Kamar");

        jButton_checkout.setText("Check Out");
        jButton_checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_checkoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 165, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_checkout)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_checkout)
                .addContainerGap())
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setText("APLIKASI HOTEL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(jLabel9)))))
                .addGap(0, 73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel9)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jTextField1_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1_namaActionPerformed

    private void jButton2_tambahKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_tambahKamarActionPerformed
        // TODO add your handling code here:
        
        String namaCust = jTextField1_nama.getText();
        String getIDKamar;
        String getTipeKamar;
        int getHargaKamar, getJumOrang;
        getJumOrang = (Integer) jSpinner1_jumlahKamar.getValue();
        System.out.println(getJumOrang);
        
        ArrayList<AddOn> getAddOn = new ArrayList<>();
        
        
        if (namaCust.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isikan nama terlebih dahulu!");
        } else {
            // Lock Nama
            jTextField1_nama.setEditable(false);
            
            // Get Combobox Jenis
            getIDKamar = jComboBox1_jenisKamar.getSelectedItem().toString().split(" - ", 3)[0];
            getTipeKamar = jComboBox1_jenisKamar.getSelectedItem().toString().split(" - ", 3)[1];
            getHargaKamar = Integer.parseInt(jComboBox1_jenisKamar.getSelectedItem().toString().split(" - ", 3)[2]);
            System.out.println("a = " + jComboBox1_jenisKamar.getSelectedItem().toString().split(" - ", 3)[2]);
            System.out.println("b = " + getIDKamar);
//            getIDKamar = getIDKamar.split(" ", 0)[1];
//            System.out.println(getIDKamar);
//            System.out.println("");
            
            // Get AddOn
            
//            Component[] components = jPanel1_AddOn.getComponents();
//            for (Component component : components) {
//                if (component instanceof JCheckBox) {
//                    JCheckBox checkBox = (JCheckBox) component;
//                    boolean isSelected = checkBox.isSelected();
//                    // Use the isSelected value as needed
//
//                    if(isSelected == true)
//                    {
//                        String namaAddOn = checkBox.getText().split(" - ",2)[0];
//                        int hargaAddOn = Integer.parseInt(checkBox.getText().split(" - ",2)[1]);
//                        AddOn objAddOn = new AddOn(namaAddOn, hargaAddOn);
//                        getAddOn.add(objAddOn);
//                        
//                        stageHarga += hargaAddOn;
//                    }
//                }
//            }

            
            // Tulis Data
//            stageCust = new Customer(namaCust);
             
            
//            int foundID = -1;
            
            
//            for (int i = 0; i < arrKamar.size(); i++) {    
//                if (arrKamar.get(i).getId().equals(getIDKamar)) {
//                    int hargaKamar = arrKamar.get(i).getHarga();
//                    int jumlahOrang = (Integer) jSpinner1_jumlahKamar.getValue();
//                    
//                    arrKamar.get(i).setAddOnTerpasang(getAddOn);
//                    arrKamar.get(i).setJumlahOrang(jumlahOrang);
////                    stageKamar.add(arrKamar.get(i).getId());
//                    stageHarga += hargaKamar;
//                }
//            }
            
            updateRincian(getIDKamar, getTipeKamar, getJumOrang, getHargaKamar);
            loadComponent();
        }
    }//GEN-LAST:event_jButton2_tambahKamarActionPerformed

    private void jComboBox1_jenisKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1_jenisKamarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1_jenisKamarActionPerformed

    private void jButton1_checkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_checkinActionPerformed
        // TODO add your handling code here:
        kamarTerpesan = stageKamar;
        
        String Kode = "";
        String namaCust = jTextField1_nama.getText();
        String getIDKamar = "";
        int getJumOrang = (Integer) jSpinner1_jumlahKamar.getValue();
        String addOn = "";
        Date checkIn = jDateChooser1.getDate();
        Date checkOut = jDateChooser2.getDate();
        long diff = checkOut.getTime() - checkIn.getTime();
        int selisih = (int) (diff /1000/60/60/24) + 1;
        System.out.println(diff);
        System.out.println(selisih);
        if(diff<=0) {
            System.out.println("jgn ngawor");
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

            String scheckIn = sdf.format(checkIn);
            String scheckOut = sdf.format(checkOut);

            Kode = namaCust.substring(0,2) + scheckIn.substring(0,2) + scheckOut.substring(0,2) + diff;

            int total = Integer.parseInt(jTextField1_TotalBayar.getText()) * selisih;

//            for (int i = 0; i < kamarTerpesan.size(); i++) {            
//                for (int j = 0; j < arrKamar.size(); j++) {    
//                    if (kamarTerpesan.get(i) == arrKamar.get(j).getId()) {
//                        namaKamar += arrKamar.get(j).getNama() + ", ";
//                        String jenisKamar = arrKamar.get(j).getTipe();
//                        jumlahOrang += arrKamar.get(j).getJumlahOrang();
//
//                        arrKamar.get(j).setStatus(1);
//
//                        ArrayList<AddOn> daftarAddOn;
//
//                        for (int k = 0; k < arrKamar.get(j).getAddOnTerpasang().size(); k++) {
//                            daftarAddOn = arrKamar.get(j).getAddOnTerpasang();
//                            addOn += daftarAddOn.get(k).getNama() + ", ";
//                        }
//
//
//                        // database input
//
//
//                    } 
//
//                }
//
//            }


            
            modelTable_data.addRow(new Object[]
                   {Kode,
                    namaCust,
                    getIDKamar,
                    getJumOrang,
                    addOn,
                    scheckIn,
                    scheckOut,
                    total,
            });


            loadComponent();


        }
        

    }//GEN-LAST:event_jButton1_checkinActionPerformed

    private void jButton2_BatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_BatalActionPerformed
        // TODO add your handling code here:
        jTextArea1_rincianPesanan.setText("");
        doreset();
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        jTextField1_TotalBayar.setText("");
        jTextField1_nama.setEditable(true);
        
        
        
        
        
//        for (int i = 0; i < stageKamar.size(); i++) {
//            for (int j = 0; j < arrKamar.size(); j++) {    
//                if (stageKamar.get(i) == arrKamar.get(j).getId()) {
//                    arrKamar.get(j).setAddOnTerpasang(null);
//                } 
//                  
//            }
//  
//        }
        
        stageCust = null;
        stageHarga = 0;
        stageKamar = new ArrayList<Integer>();

        
        loadComponent();
        
    }//GEN-LAST:event_jButton2_BatalActionPerformed

    private void jButton_checkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_checkoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_checkoutActionPerformed

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
            java.util.logging.Logger.getLogger(NewJFrame_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame_main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_checkin;
    private javax.swing.JButton jButton2_Batal;
    private javax.swing.JButton jButton2_tambahKamar;
    private javax.swing.JButton jButton_checkout;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1_jenisKamar;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1_listStatus;
    private javax.swing.JPanel jPanel1_AddOn;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinner1_jumlahKamar;
    private javax.swing.JTable jTable1_HTrans;
    private javax.swing.JTextArea jTextArea1_rincianPesanan;
    private javax.swing.JTextField jTextField1_TotalBayar;
    private javax.swing.JTextField jTextField1_nama;
    // End of variables declaration//GEN-END:variables
}
