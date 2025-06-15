          /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templatesrs
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;           // buat baca file baris per baris
import java.io.BufferedWriter;           // buat nulis file biar cepet
import java.io.FileReader;               // baca file pake karakter
import java.io.FileWriter;               // nulis file pake karakter
import java.io.IOException;              // buat handle error pas baca/tulis file

import javax.swing.JOptionPane;          // buat munculin popup 
import javax.swing.table.DefaultTableModel; // biar data di tabel bisa diedit2

import java.util.Date;                   // buat nampung tanggal
import java.text.SimpleDateFormat;       // buat ubah tanggal ke string atau sebaliknya
import java.awt.event.ActionEvent;       // event pas tombol ditekan
import java.awt.event.ActionListener;    // listener buat nangkep action dari tombol
import java.beans.PropertyChangeListener; // buat dengerin perubahan properti (jarang dipake sih)
import java.io.File;
import java.text.ParseException;         // buat handle error parsing tanggal
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.FileOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author gilang
 */
public class dashboardAdmin extends javax.swing.JFrame {

    int xx, xy;
    //Model untuk table buku    
    DefaultTableModel modelAddBook;
    //Model untuk tbale peminjaman
    DefaultTableModel modelLoan;

    //ini adalah kolo untuk table buku
    private void initTableModelAddBook() {
        modelAddBook = new DefaultTableModel(new String[]{"ID Buku", "Nama Buku", "Nama Pengarang", "Jenis Buku"}, 0);
        tblBuku.setModel(modelAddBook);
    }
    //    ini adalah kolom untuk table buku
    public void initTableModelLoan() {
    modelLoan = new DefaultTableModel();
    modelLoan.addColumn("ID Buku");
    modelLoan.addColumn("Nama Peminjam");
    modelLoan.addColumn("Nama Buku");
    modelLoan.addColumn("Tanggal Pinjam");
    modelLoan.addColumn("Tanggal Kembali");
    modelLoan.addColumn("Estimasi");

    tblLoan.setModel(modelLoan); 
}
    public void ReportLoadTable() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Peminjaman");
    model.addColumn("Nama Peminjam");
    model.addColumn("Judul Buku");
    model.addColumn("Tanggal Pinjam");
    model.addColumn("Tanggal Kembali");
    model.addColumn("Lama Peminjaman");

    try {
        File file = new File("loan.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 6) {
                model.addRow(data);
            }
        }

        br.close();
        tableDataReport.setModel(model); // Mengisi data ke tabel kamu

    } catch (IOException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Gagal membaca data loan.txt: " + e.getMessage());
    }
}

    //ini Membaca data dari file addBook.txt dan menampilkan ke table buku geyz
    private void loadDataAddBook() {
        modelAddBook.setRowCount(0);        
        try (BufferedReader br = new BufferedReader(new FileReader("addBook.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                modelAddBook.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data dari file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //ini Membaca data dari file loan.txt dan menampilkan ke table loan geyz
    public void loadDataLoan() {
        modelLoan.setRowCount(0); // Clear dulu
        try (BufferedReader br = new BufferedReader(new FileReader("loan.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    modelLoan.addRow(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data peminjaman dari file!");
        }
    }
    // Menyimpan semua data dari tabel buku ke dalam file addBook.txt
    private void saveTableToFileAddBook() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("addBook.txt"))) {
            for (int i = 0; i < modelAddBook.getRowCount(); i++) {
                String id = modelAddBook.getValueAt(i, 0).toString();
                String nama = modelAddBook.getValueAt(i, 1).toString();
                String jenis = modelAddBook.getValueAt(i, 2).toString();
                String pengarang = modelAddBook.getValueAt(i, 3).toString();

                bw.write(id + "," + nama + "," + jenis + "," + pengarang);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveTableToFileLoan() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("loan.txt"))) {
        for (int i = 0; i < modelLoan.getRowCount(); i++) {
            String line = modelLoan.getValueAt(i, 0).toString() + "," +
                          modelLoan.getValueAt(i, 1).toString() + "," +
                          modelLoan.getValueAt(i, 2).toString() + "," +
                          modelLoan.getValueAt(i, 3).toString() + "," +
                          modelLoan.getValueAt(i, 4).toString() + "," +
                          modelLoan.getValueAt(i, 5).toString();
            bw.write(line);
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal nyimpen file bro!");
        }
    }

    //Reset seluruh form input buku
    private void clearForm() {
        txtIdBuku.setText("");
        txtNamaBuku.setText("");
        //    cmbJenisBuku.setSelectedIndex(0);
        txtNamaPengarang.setText("");
        txtJenisBuku.setText("");
    }
    
    private void clearFormLoan() {
        cmbIdPeminjam.setSelectedIndex(0); // reset ke item pertama
        txtNamaPeminjam.setText("");
        txtNamaBukuLoan.setText("");
        jDatePeminjaman.setDate(null);
        jDatePengembalian.setDate(null);
        txtEstimasi.setText("");
    }


    public void loadIdBukuToComboLoan() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("addBook.txt"));
            String line;
            cmbIdPeminjam.removeAllItems(); // Kosongkan dulu

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    cmbIdPeminjam.addItem(data[0]); // hanya ambil ID Buku
                }
            }

            br.close();
            
            if (cmbIdPeminjam.getItemCount() > 0) {
            cmbIdPeminjam.setSelectedIndex(0);
        }
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat ID Buku dari file!");
        }
    }
        public dashboardAdmin(String fullName) {
            initComponents();
            labelFullName.setText(fullName);
        }

    public dashboardAdmin() {
  
        initComponents();
        initTableModelAddBook();// set kolom tabel
        loadDataAddBook();// load data dari file ke tabel
        clearForm();             // reset field inputan
        clearFormLoan();
        setVisible(true);
        setResizable(false);
        initTableModelLoan();
        loadDataLoan();
        loadIdBukuToComboLoan();
        ReportLoadTable();

        txtNamaBukuLoan.setEditable(false);
        txtEstimasi.setEditable(false);

        
        // Contructor Panel Loan
        cmbIdPeminjam.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedId = cmbIdPeminjam.getSelectedItem().toString();
            for (int i = 0; i < modelAddBook.getRowCount(); i++) {
                if (modelAddBook.getValueAt(i, 0).toString().equals(selectedId)) {
                    txtNamaBukuLoan.setText(modelAddBook.getValueAt(i, 1).toString()); // Asumsi kolom 1 adalah nama buku
                    break;
                }
            }
        }
    });
        if (cmbIdPeminjam.getItemCount() > 0) {
            cmbIdPeminjam.setSelectedIndex(0); // ini akan memicu actionPerformed secara otomatis
        }

        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        jDatePeminjaman.setDateFormatString("dd-MM-yyyy");
        jDatePengembalian.setDateFormatString("dd-MM-yyyy");
        //Menghitung Estimasi Waktu Date Start - Date End
        PropertyChangeListener listener = evt -> {
        if (jDatePeminjaman.getDate() != null && jDatePengembalian.getDate() != null) {
            long start = jDatePeminjaman.getDate().getTime();
            long end = jDatePengembalian.getDate().getTime();
            long diff = (end - start) / (1000 * 60 * 60 * 24);
             // Format tanggal jika ingin ditampilkan/log
            String tglPinjam = sdf.format(jDatePeminjaman.getDate());
            String tglKembali = sdf.format(jDatePengembalian.getDate());
            txtEstimasi.setText(String.valueOf(diff + " Hari"));
        }
    };
        
    jDatePeminjaman.getDateEditor().addPropertyChangeListener(listener);
    jDatePengembalian.getDateEditor().addPropertyChangeListener(listener);

            String gifPath = "/img/literasi.gif"; 
            String gifPathh = "/img/book2.gif"; 
            try {
            // Memuat GIF menggunakan getClass().getResource()
            // Ini mencari file relatif terhadap classpath aplikasi Anda
            ImageIcon gifIcon = new ImageIcon(getClass().getResource(gifPath));
            ImageIcon gifIconn = new ImageIcon(getClass().getResource(gifPathh));

            // Mengatur ikon ke JLabel Anda
            txtIconBook.setIcon(gifIcon);
            txtBook.setIcon(gifIconn);

            // Opsional: Jika Anda tidak ingin ada teks, kosongkan teksnya
            txtIconBook.setText(""); 
            txtBook.setText(""); 

            } catch (NullPointerException e) {
                // Ini terjadi jika getResource() mengembalikan null, berarti file tidak ditemukan
                System.err.println("Error: File GIF tidak ditemukan di classpath: " + gifPath);
                System.err.println("Pastikan 'gif' ada di folder 'src/img' proyek NetBeans Anda.");
                e.printStackTrace();
            } catch (Exception e) {
                // Menangkap error lain yang mungkin terjadi saat memuat gambar
                System.err.println("Terjadi kesalahan saat memuat GIF: " + e.getMessage());
                e.printStackTrace();
            }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerat ed by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidebarPanel = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnAddBook = new javax.swing.JButton();
        btnLoanReturn = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        btnAbout = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        labelFullName = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        iconClose = new javax.swing.JLabel();
        iconLogo = new javax.swing.JLabel();
        headerGradient = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        home = new javax.swing.JPanel();
        txtIconBook = new javax.swing.JLabel();
        txtBook = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();
        addBook = new javax.swing.JPanel();
        labelBuku = new javax.swing.JLabel();
        labelNamaBuku = new javax.swing.JLabel();
        labelNamaPengarang = new javax.swing.JLabel();
        labelJenisBuk = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuku = new javax.swing.JTable();
        txtIdBuku = new javax.swing.JTextField();
        txtNamaBuku = new javax.swing.JTextField();
        txtNamaPengarang = new javax.swing.JTextField();
        txtJenisBuku = new javax.swing.JTextField();
        btnSaveAddBook = new javax.swing.JButton();
        btnUpdateAddBook = new javax.swing.JButton();
        btnDeleteAddBook = new javax.swing.JButton();
        btnRefreshAddBook = new javax.swing.JButton();
        loan = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLoan = new javax.swing.JTable();
        labelIdPeminjam = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbIdPeminjam = new javax.swing.JComboBox<>();
        txtNamaPeminjam = new javax.swing.JTextField();
        txtNamaBukuLoan = new javax.swing.JTextField();
        txtEstimasi = new javax.swing.JTextField();
        jDatePeminjaman = new com.toedter.calendar.JDateChooser();
        jDatePengembalian = new com.toedter.calendar.JDateChooser();
        btnDeleteLoan = new javax.swing.JButton();
        btnUpdateLoan = new javax.swing.JButton();
        btnSaveLoan = new javax.swing.JButton();
        btnRefreshLoan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        report = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDataReport = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        labelIConBook1 = new javax.swing.JLabel();
        labelIConBook2 = new javax.swing.JLabel();
        labelIConBook3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        about = new javax.swing.JPanel();
        bgAbout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        sidebarPanel.setBackground(new java.awt.Color(27, 38, 59));

        btnHome.setBackground(new java.awt.Color(119, 141, 169));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home_icon50px.png"))); // NOI18N
        btnHome.setText("Home");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnAddBook.setBackground(new java.awt.Color(119, 141, 169));
        btnAddBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/addBook_icon50px.png"))); // NOI18N
        btnAddBook.setText("Book");
        btnAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBookActionPerformed(evt);
            }
        });

        btnLoanReturn.setBackground(new java.awt.Color(119, 141, 169));
        btnLoanReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exchangeBook_icon50px.png"))); // NOI18N
        btnLoanReturn.setText("Loan");
        btnLoanReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoanReturnActionPerformed(evt);
            }
        });

        btnReport.setBackground(new java.awt.Color(119, 141, 169));
        btnReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/report_icon50px.png"))); // NOI18N
        btnReport.setText("Report");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        btnAbout.setBackground(new java.awt.Color(119, 141, 169));
        btnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/about_icon50px.png"))); // NOI18N
        btnAbout.setText("About");
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(119, 141, 169));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout_icon50px.png"))); // NOI18N
        btnLogout.setText("LOGOUT");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        labelFullName.setFont(new java.awt.Font("Javanese Text", 1, 25)); // NOI18N
        labelFullName.setForeground(java.awt.Color.white);
        labelFullName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout sidebarPanelLayout = new javax.swing.GroupLayout(sidebarPanel);
        sidebarPanel.setLayout(sidebarPanelLayout);
        sidebarPanelLayout.setHorizontalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAbout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnLoanReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelFullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidebarPanelLayout.setVerticalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarPanelLayout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addComponent(labelFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoanReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        getContentPane().add(sidebarPanel, java.awt.BorderLayout.LINE_START);

        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_close_window_40px.png"))); // NOI18N
        iconClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconCloseMouseClicked(evt);
            }
        });
        headerPanel.add(iconClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 10, -1, -1));

        iconLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logoICON (1).png"))); // NOI18N
        headerPanel.add(iconLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 190, 60));

        headerGradient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/headerGradient.png"))); // NOI18N
        headerPanel.add(headerGradient, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 80));

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        mainPanel.setBackground(new java.awt.Color(119, 141, 169));
        mainPanel.setLayout(new java.awt.CardLayout());

        home.setBackground(new java.awt.Color(235, 239, 245));
        home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        home.add(txtIconBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 176, 200));
        home.add(txtBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 200, 200, 157));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/homePanel.png"))); // NOI18N
        home.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 620));

        mainPanel.add(home, "card3");

        addBook.setBackground(new java.awt.Color(235, 239, 245));

        labelBuku.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        labelBuku.setText("ID BUKU");

        labelNamaBuku.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        labelNamaBuku.setText("Nama Buku");

        labelNamaPengarang.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        labelNamaPengarang.setText("Nama Pengarang");

        labelJenisBuk.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        labelJenisBuk.setText("Jenis Buku");

        tblBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID BUKU", "NAMA BUKU", "NAMA PENGGARANG", "JENIS BUKU"
            }
        ));
        tblBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBuku);

        txtJenisBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJenisBukuActionPerformed(evt);
            }
        });

        btnSaveAddBook.setBackground(new java.awt.Color(40, 167, 69));
        btnSaveAddBook.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnSaveAddBook.setForeground(java.awt.Color.white);
        btnSaveAddBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disket.png"))); // NOI18N
        btnSaveAddBook.setText("Save");
        btnSaveAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAddBookActionPerformed(evt);
            }
        });

        btnUpdateAddBook.setBackground(new java.awt.Color(0, 123, 255));
        btnUpdateAddBook.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnUpdateAddBook.setForeground(java.awt.Color.white);
        btnUpdateAddBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/draw.png"))); // NOI18N
        btnUpdateAddBook.setText("Update");
        btnUpdateAddBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateAddBookMouseClicked(evt);
            }
        });
        btnUpdateAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateAddBookActionPerformed(evt);
            }
        });

        btnDeleteAddBook.setBackground(new java.awt.Color(220, 53, 69));
        btnDeleteAddBook.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnDeleteAddBook.setForeground(java.awt.Color.white);
        btnDeleteAddBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/trash-bin.png"))); // NOI18N
        btnDeleteAddBook.setText("Delete");
        btnDeleteAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAddBookActionPerformed(evt);
            }
        });

        btnRefreshAddBook.setBackground(new java.awt.Color(108, 117, 125));
        btnRefreshAddBook.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnRefreshAddBook.setForeground(java.awt.Color.white);
        btnRefreshAddBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"))); // NOI18N
        btnRefreshAddBook.setText("Refresh");
        btnRefreshAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshAddBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addBookLayout = new javax.swing.GroupLayout(addBook);
        addBook.setLayout(addBookLayout);
        addBookLayout.setHorizontalGroup(
            addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(addBookLayout.createSequentialGroup()
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelBuku)
                            .addComponent(labelJenisBuk)
                            .addComponent(labelNamaBuku)
                            .addComponent(labelNamaPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdBuku, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                            .addComponent(txtNamaBuku)
                            .addComponent(txtNamaPengarang)
                            .addComponent(txtJenisBuku))
                        .addGap(167, 167, 167)
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSaveAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRefreshAddBook, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateAddBook, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addContainerGap())
        );
        addBookLayout.setVerticalGroup(
            addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBookLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addBookLayout.createSequentialGroup()
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSaveAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefreshAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(addBookLayout.createSequentialGroup()
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelBuku)
                            .addComponent(txtIdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNamaBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNamaPengarang)
                            .addComponent(txtNamaPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(addBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelJenisBuk)
                            .addComponent(txtJenisBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(addBook, "card2");

        loan.setBackground(new java.awt.Color(235, 239, 245));

        tblLoan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLoan);

        labelIdPeminjam.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        labelIdPeminjam.setText("Id Peminjam");

        jLabel3.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        jLabel3.setText("Nama Peminjam");

        jLabel7.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        jLabel7.setText("Nama Buku");

        jLabel8.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        jLabel8.setText("Tanggal Pinjam");

        jLabel9.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        jLabel9.setText("Tanggal Kembali");

        jLabel10.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        jLabel10.setText("Estimasi Waktu");

        cmbIdPeminjam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbIdPeminjam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbIdPeminjamItemStateChanged(evt);
            }
        });

        btnDeleteLoan.setBackground(new java.awt.Color(220, 53, 69));
        btnDeleteLoan.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnDeleteLoan.setForeground(java.awt.Color.white);
        btnDeleteLoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/trash-bin.png"))); // NOI18N
        btnDeleteLoan.setText("Delete");
        btnDeleteLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteLoanActionPerformed(evt);
            }
        });

        btnUpdateLoan.setBackground(new java.awt.Color(0, 123, 255));
        btnUpdateLoan.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnUpdateLoan.setForeground(java.awt.Color.white);
        btnUpdateLoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/draw.png"))); // NOI18N
        btnUpdateLoan.setText("Update");
        btnUpdateLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateLoanActionPerformed(evt);
            }
        });

        btnSaveLoan.setBackground(new java.awt.Color(40, 167, 69));
        btnSaveLoan.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnSaveLoan.setForeground(java.awt.Color.white);
        btnSaveLoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disket.png"))); // NOI18N
        btnSaveLoan.setText("Save");
        btnSaveLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveLoanActionPerformed(evt);
            }
        });

        btnRefreshLoan.setBackground(new java.awt.Color(108, 117, 125));
        btnRefreshLoan.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnRefreshLoan.setForeground(java.awt.Color.white);
        btnRefreshLoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"))); // NOI18N
        btnRefreshLoan.setText("Refresh");
        btnRefreshLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshLoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loanLayout = new javax.swing.GroupLayout(loan);
        loan.setLayout(loanLayout);
        loanLayout.setHorizontalGroup(
            loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loanLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(loanLayout.createSequentialGroup()
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(loanLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDatePeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, loanLayout.createSequentialGroup()
                                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelIdPeminjam)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7))
                                .addGap(50, 50, 50)
                                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNamaPeminjam, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(txtNamaBukuLoan, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(cmbIdPeminjam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, loanLayout.createSequentialGroup()
                                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(loanLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jDatePengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(loanLayout.createSequentialGroup()
                                        .addGap(49, 49, 49)
                                        .addComponent(txtEstimasi, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loanLayout.createSequentialGroup()
                                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDeleteLoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSaveLoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRefreshLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdateLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loanLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(179, 179, 179))))))
        );
        loanLayout.setVerticalGroup(
            loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loanLayout.createSequentialGroup()
                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelIdPeminjam)
                            .addComponent(cmbIdPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNamaPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNamaBukuLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(24, 24, 24)
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jDatePeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(loanLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdateLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSaveLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDeleteLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefreshLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loanLayout.createSequentialGroup()
                        .addComponent(jDatePengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(loanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEstimasi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(loanLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(loan, "card4");

        report.setBackground(new java.awt.Color(235, 239, 245));

        tableDataReport.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableDataReport);

        btnPrint.setBackground(new java.awt.Color(255, 255, 255));
        btnPrint.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/printer.png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        labelIConBook1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bukuA.png"))); // NOI18N

        labelIConBook2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bukuastronot.png"))); // NOI18N

        labelIConBook3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bukutulis.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tumpukanbuku.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/equals.png"))); // NOI18N

        javax.swing.GroupLayout reportLayout = new javax.swing.GroupLayout(report);
        report.setLayout(reportLayout);
        reportLayout.setHorizontalGroup(
            reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(reportLayout.createSequentialGroup()
                        .addComponent(labelIConBook3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addComponent(labelIConBook1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(reportLayout.createSequentialGroup()
                    .addGap(67, 67, 67)
                    .addComponent(labelIConBook2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(708, Short.MAX_VALUE)))
            .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportLayout.createSequentialGroup()
                    .addContainerGap(799, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(21, 21, 21)))
        );
        reportLayout.setVerticalGroup(
            reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(reportLayout.createSequentialGroup()
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIConBook3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelIConBook1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportLayout.createSequentialGroup()
                    .addContainerGap(470, Short.MAX_VALUE)
                    .addComponent(labelIConBook2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(25, 25, 25)))
            .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(reportLayout.createSequentialGroup()
                    .addGap(168, 168, 168)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(372, Short.MAX_VALUE)))
        );

        jLabel2.getAccessibleContext().setAccessibleName("");
        jLabel4.getAccessibleContext().setAccessibleName("");

        mainPanel.add(report, "card5");

        about.setBackground(new java.awt.Color(235, 239, 245));

        bgAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/AboutUs.png"))); // NOI18N

        javax.swing.GroupLayout aboutLayout = new javax.swing.GroupLayout(about);
        about.setLayout(aboutLayout);
        aboutLayout.setHorizontalGroup(
            aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgAbout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        aboutLayout.setVerticalGroup(
            aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgAbout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainPanel.add(about, "card6");

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void iconCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconCloseMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_iconCloseMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        // TODO add your handling code here:
        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(report);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(about);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnAboutActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // Tampilkan pesan konfirmasi
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Jika pengguna menekan "Yes"
        if (confirm == JOptionPane.YES_OPTION) {
            // Tutup jendela dashboardAdmin
            this.dispose();

            // Buka halaman login
            new loginForm().setVisible(true);
        }

    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnLoanReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoanReturnActionPerformed
        // TODO add your handling code here:

        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(loan);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnLoanReturnActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(home);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBookActionPerformed
        // TODO add your handling code here:
        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(addBook);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnAddBookActionPerformed

    private void txtJenisBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJenisBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJenisBukuActionPerformed

    private void btnSaveAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAddBookActionPerformed
        String idBuku = txtIdBuku.getText();
        String namaBuku = txtNamaBuku.getText();
        String namaPengarang = txtNamaPengarang.getText();
        String jenisBuku = txtJenisBuku.getText();

        // Validasi sederhana (pastikan tidak ada yang kosong)
        if (idBuku.isEmpty() || namaBuku.isEmpty() || namaPengarang.isEmpty() || jenisBuku.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Validasi agar ID tidak duplikat       
        for (int i = 0; i < modelAddBook.getRowCount(); i++) {
            if (modelAddBook.getValueAt(i, 0).toString().equals(idBuku)) {
                JOptionPane.showMessageDialog(this, "ID Buku sudah digunakan!");
                return;
            }
        }

        // Tambahkan ke tabel
        modelAddBook.addRow(new Object[]{idBuku, namaBuku, namaPengarang, jenisBuku});

        // Simpan ke dalam file addBook.txt
        try (FileWriter fw = new FileWriter("addBook.txt", true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(idBuku + "," + namaBuku + "," + namaPengarang + "," + jenisBuku);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        loadDataLoan();
        // kosongkam inputan 
        clearForm();

        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_btnSaveAddBookActionPerformed

    private void btnUpdateAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAddBookActionPerformed
        int row = tblBuku.getSelectedRow(); // Ambil baris yg dipilih

        if (row >= 0) {
            modelAddBook.setValueAt(txtIdBuku.getText(), row, 0);
            modelAddBook.setValueAt(txtNamaBuku.getText(), row, 1);
            modelAddBook.setValueAt(txtNamaPengarang.getText(), row, 2);
//            modelAddBook.setValueAt(cmbJenisBuku.getSelectedItem().toString(), row, 2);
            modelAddBook.setValueAt(txtJenisBuku.getText(), row, 3);

            saveTableToFileAddBook(); // Simpan ulang ke file
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate!");
        }
    }//GEN-LAST:event_btnUpdateAddBookActionPerformed

    private void btnUpdateAddBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateAddBookMouseClicked

    }//GEN-LAST:event_btnUpdateAddBookMouseClicked

    private void tblBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBukuMouseClicked
        int row = tblBuku.getSelectedRow();

        txtIdBuku.setText(modelAddBook.getValueAt(row, 0).toString());
        txtNamaBuku.setText(modelAddBook.getValueAt(row, 1).toString());
        txtNamaPengarang.setText(modelAddBook.getValueAt(row, 2).toString());
//        cmbJenisBuku.setSelectedItem(modelAddBook.getValueAt(row, 2).toString());
        txtJenisBuku.setText(modelAddBook.getValueAt(row, 3).toString());
    }//GEN-LAST:event_tblBukuMouseClicked

    private void btnDeleteAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAddBookActionPerformed
        int selectedRow = tblBuku.getSelectedRow();

        if (selectedRow >= 0) {
            // Konfirmasi penghapusan
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                modelAddBook.removeRow(selectedRow); // hapus dari tabel
                saveTableToFileAddBook(); // simpan ulang file tanpa data itu
                clearForm(); // kosongkan form input
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
        }
    }//GEN-LAST:event_btnDeleteAddBookActionPerformed

    private void cmbIdPeminjamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIdPeminjamItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbIdPeminjamItemStateChanged

    private void btnSaveLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveLoanActionPerformed
    // Ambil data dari form
    String idPeminjam = cmbIdPeminjam.getSelectedItem().toString();
    String namaPeminjam = txtNamaPeminjam.getText();
    String namaBuku = txtNamaBukuLoan.getText();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String tanggalPinjam = sdf.format(jDatePeminjaman.getDate());
    String tanggalKembali = sdf.format(jDatePengembalian.getDate());
    String estimasi = txtEstimasi.getText();

    // Tambahin ke tabel biar langsung muncul
    modelLoan.addRow(new Object[]{
        idPeminjam, namaPeminjam, namaBuku, tanggalPinjam, tanggalKembali, estimasi
    });

    // Simpen ke file
    saveTableToFileLoan();

    // Bersihin form
    clearFormLoan();

    // Kasih notif
    JOptionPane.showMessageDialog(this, "Data berhasil disimpan bro!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    
    }//GEN-LAST:event_btnSaveLoanActionPerformed

    private void btnUpdateLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateLoanActionPerformed
    int selectedRow = tblLoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Silakan pilih data yang ingin diupdate!");
            return;
        }

        // Ambil data dari form
        String idBuku = cmbIdPeminjam.getSelectedItem().toString();
        String namaPeminjam = txtNamaPeminjam.getText();
        String namaBuku = txtNamaBukuLoan.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String tglPinjam = sdf.format(jDatePeminjaman.getDate());
        String tglKembali = sdf.format(jDatePengembalian.getDate());
        String estimasi = txtEstimasi.getText();

        // Update data di tabel
        modelLoan.setValueAt(idBuku, selectedRow, 0);
        modelLoan.setValueAt(namaPeminjam, selectedRow, 1);
        modelLoan.setValueAt(namaBuku, selectedRow, 2);
        modelLoan.setValueAt(tglPinjam, selectedRow, 3);
        modelLoan.setValueAt(tglKembali, selectedRow, 4);
        modelLoan.setValueAt(estimasi, selectedRow, 5);

        // Simpan ulang ke file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("loan.txt"))) {
            for (int i = 0; i < modelLoan.getRowCount(); i++) {
                String line = modelLoan.getValueAt(i, 0).toString() + "," +
                              modelLoan.getValueAt(i, 1).toString() + "," +
                              modelLoan.getValueAt(i, 2).toString() + "," +
                              modelLoan.getValueAt(i, 3).toString() + "," +
                              modelLoan.getValueAt(i, 4).toString() + "," +
                              modelLoan.getValueAt(i, 5).toString();
                bw.write(line);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data ke file!");
        }
    }//GEN-LAST:event_btnUpdateLoanActionPerformed

    private void tblLoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoanMouseClicked
    int row = tblLoan.getSelectedRow();

    if (row >= 0) {
        cmbIdPeminjam.setSelectedItem(modelLoan.getValueAt(row, 0).toString());
        txtNamaPeminjam.setText(modelLoan.getValueAt(row, 1).toString());
        txtNamaBukuLoan.setText(modelLoan.getValueAt(row, 2).toString());

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date tglPinjam = sdf.parse(modelLoan.getValueAt(row, 3).toString());
            Date tglKembali = sdf.parse(modelLoan.getValueAt(row, 4).toString());

            jDatePeminjaman.setDate(tglPinjam);
            jDatePengembalian.setDate(tglKembali);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtEstimasi.setText(modelLoan.getValueAt(row, 5).toString());
    }
    }//GEN-LAST:event_tblLoanMouseClicked

    private void btnRefreshAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshAddBookActionPerformed
        clearForm();
    }//GEN-LAST:event_btnRefreshAddBookActionPerformed

    private void btnDeleteLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteLoanActionPerformed
     int selectedRow = tblLoan.getSelectedRow(); // cek baris yg dipilih

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data dulu yang mau dihapus, bro!");
        return;
    }

    int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin mau hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (konfirmasi == JOptionPane.YES_OPTION) {
        modelLoan.removeRow(selectedRow); // hapus dari tabel
        saveTableToFileLoan(); // simpan ulang ke file
        clearFormLoan(); // bersihin form
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus, mantap!");
    }
    }//GEN-LAST:event_btnDeleteLoanActionPerformed

    private void btnRefreshLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshLoanActionPerformed
        clearFormLoan();
    }//GEN-LAST:event_btnRefreshLoanActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Simpan Laporan sebagai PDF");
    fileChooser.setSelectedFile(new File("ReportPeminjaman.pdf"));

    int userSelection = fileChooser.showSaveDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();

        // Tambahkan .pdf jika belum ada ekstensi
        if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
        }

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();

            // Judul
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Laporan Data Peminjaman", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Table
            PdfPTable pdfTable = new PdfPTable(tableDataReport.getColumnCount());
            pdfTable.setWidthPercentage(100);

            // Header
            for (int i = 0; i < tableDataReport.getColumnCount(); i++) {
                pdfTable.addCell(new PdfPCell(new Phrase(tableDataReport.getColumnName(i))));
            }

            // Data
            for (int row = 0; row < tableDataReport.getRowCount(); row++) {
                for (int col = 0; col < tableDataReport.getColumnCount(); col++) {
                    Object value = tableDataReport.getValueAt(row, col);
                    pdfTable.addCell(value != null ? value.toString() : "");
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan di:\n" + fileToSave.getAbsolutePath());

            // Buka otomatis
            Desktop.getDesktop().open(fileToSave);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mencetak laporan: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnPrintActionPerformed

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
            java.util.logging.Logger.getLogger(dashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboardAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel about;
    private javax.swing.JPanel addBook;
    private javax.swing.JLabel bg;
    private javax.swing.JLabel bgAbout;
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnDeleteAddBook;
    private javax.swing.JButton btnDeleteLoan;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLoanReturn;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefreshAddBook;
    private javax.swing.JButton btnRefreshLoan;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnSaveAddBook;
    private javax.swing.JButton btnSaveLoan;
    private javax.swing.JButton btnUpdateAddBook;
    private javax.swing.JButton btnUpdateLoan;
    private javax.swing.JComboBox<String> cmbIdPeminjam;
    private javax.swing.JLabel headerGradient;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel home;
    private javax.swing.JLabel iconClose;
    private javax.swing.JLabel iconLogo;
    private com.toedter.calendar.JDateChooser jDatePeminjaman;
    private com.toedter.calendar.JDateChooser jDatePengembalian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelBuku;
    private javax.swing.JLabel labelFullName;
    private javax.swing.JLabel labelIConBook1;
    private javax.swing.JLabel labelIConBook2;
    private javax.swing.JLabel labelIConBook3;
    private javax.swing.JLabel labelIdPeminjam;
    private javax.swing.JLabel labelJenisBuk;
    private javax.swing.JLabel labelNamaBuku;
    private javax.swing.JLabel labelNamaPengarang;
    private javax.swing.JPanel loan;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel report;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JTable tableDataReport;
    private javax.swing.JTable tblBuku;
    private javax.swing.JTable tblLoan;
    private javax.swing.JLabel txtBook;
    private javax.swing.JTextField txtEstimasi;
    private javax.swing.JLabel txtIconBook;
    private javax.swing.JTextField txtIdBuku;
    private javax.swing.JTextField txtJenisBuku;
    private javax.swing.JTextField txtNamaBuku;
    private javax.swing.JTextField txtNamaBukuLoan;
    private javax.swing.JTextField txtNamaPeminjam;
    private javax.swing.JTextField txtNamaPengarang;
    // End of variables declaration//GEN-END:variables

}
