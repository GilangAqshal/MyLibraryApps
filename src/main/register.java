/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import java.io.*;
import java.util.*;
/**
 *
 * @author gilan
 */
public class register extends javax.swing.JFrame {
    int xx, xy;
    /** Creates new form login */
    public register() {
        initComponents();
        setResizable(false);

    }
    
     void bersih(){
        txtUsername.setText("Username");
        txtPassword.setText("Password");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgLayout = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnClose = new javax.swing.JLabel();
        mt = new javax.swing.JLabel();
        txtLogin = new javax.swing.JLabel();
        iconPassword = new javax.swing.JLabel();
        iconUsername = new javax.swing.JLabel();
        iconFullName = new javax.swing.JLabel();
        btnDaftar = new javax.swing.JButton();
        txtPassword = new javax.swing.JTextField();
        txtFullName = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        bgLogin = new javax.swing.JLabel();

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

        bgLayout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 16)); // NOI18N
        jLabel2.setText("Sudah punya akun? ");
        bgLayout.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 360, -1, -1));

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 16)); // NOI18N
        jLabel3.setText("Login disini");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        bgLayout.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 360, -1, -1));

        btnClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_close_window_40px.png"))); // NOI18N
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });
        bgLayout.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, -1, -1));

        mt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mata.png"))); // NOI18N
        bgLayout.add(mt, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 220, 40, -1));

        txtLogin.setBackground(new java.awt.Color(27, 38, 59));
        txtLogin.setFont(new java.awt.Font("Arial Black", 1, 30)); // NOI18N
        txtLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLogin.setText("REGISTER");
        txtLogin.setToolTipText("");
        bgLayout.add(txtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 240, 50));

        iconPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/password_icon40px.png"))); // NOI18N
        bgLayout.add(iconPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 220, -1, -1));

        iconUsername.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/staff_icon40px.png"))); // NOI18N
        bgLayout.add(iconUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, -1, 40));

        iconFullName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/full name.png"))); // NOI18N
        bgLayout.add(iconFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, -1, -1));

        btnDaftar.setBackground(new java.awt.Color(27, 38, 59));
        btnDaftar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnDaftar.setForeground(new java.awt.Color(255, 255, 255));
        btnDaftar.setText("DAFTAR");
        btnDaftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarActionPerformed(evt);
            }
        });
        bgLayout.add(btnDaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 280, 250, 60));

        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setText("Password");
        txtPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 38, 59)));
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordFocusLost(evt);
            }
        });
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        bgLayout.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 250, 60));

        txtFullName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFullName.setText("FULL NAME");
        txtFullName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 38, 59)));
        txtFullName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFullNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFullNameFocusLost(evt);
            }
        });
        txtFullName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFullNameActionPerformed(evt);
            }
        });
        bgLayout.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 250, 60));

        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsername.setText("Username");
        txtUsername.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 38, 59)));
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsernameFocusLost(evt);
            }
        });
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        bgLayout.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 250, 60));

        bgLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bg60x40.png"))); // NOI18N
        bgLayout.add(bgLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
       int y = evt.getYOnScreen();
       this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarActionPerformed
    String inputUsername = txtUsername.getText().trim();
    String inputPassword = txtPassword.getText().trim();
    String inputFullName = txtFullName.getText().trim(); // Tambahan

    if (inputUsername.isEmpty() || inputUsername.equals("Username") || 
        inputPassword.isEmpty() || inputPassword.equals("Password") ||
        inputFullName.isEmpty() || inputFullName.equals("Full Name")) {
        javax.swing.JOptionPane.showMessageDialog(this, "Semua field harus diisi dengan benar!");
        return;
    }

    boolean usernameSudahAda = false;

    try {
        File file = new File("akun.txt");
        if (!file.exists()) {
            file.createNewFile(); // buat file jika belum ada
        }

        // Cek apakah username sudah terdaftar
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(";");
            if (data.length >= 1 && data[0].trim().equalsIgnoreCase(inputUsername)) {
                usernameSudahAda = true;
                break;
            }
        }
        scanner.close();

        if (usernameSudahAda) {
            javax.swing.JOptionPane.showMessageDialog(this, "Username sudah digunakan. Silakan pilih username lain.");
        } else {
            // Format simpan: username;password;fullname
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(inputUsername + ";" + inputPassword + ";" + inputFullName);
            writer.newLine();
            writer.close();

            javax.swing.JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login.");
            this.dispose(); // tutup form register
            new loginForm().setVisible(true); // kembali ke login
        }

    } catch (IOException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan akun.");
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnDaftarActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
        
         //Jika ditekan akan hilang usernamenya
        String Username = txtUsername.getText();
        if(Username.equals("Username")){
            txtUsername.setText("");
        }
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusLost
        
          //jika tidak ditekan akan kembali lagi stringnya
        String Username = txtUsername.getText();
         if(Username.equals("")|| Username.equals("Username")){
            txtUsername.setText("Username");
        }
    }//GEN-LAST:event_txtUsernameFocusLost

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        // TODO add your handling code here:
        
         //Jika ditekan akan hilang usernamenya
        String Username = txtPassword.getText();
        if(Username.equals("Password")){
            txtPassword.setText("");
        }
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusLost
       
        
          //jika tidak ditekan akan kembali lagi stringnya
        String Password = txtPassword.getText();
         if(Password.equals("")|| Password.equals("Password")){
            txtPassword.setText("Password");
        }
    }//GEN-LAST:event_txtPasswordFocusLost

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.dispose();
        new loginForm().setVisible(true);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void txtFullNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFullNameFocusGained
        String Username = txtFullName.getText();
        if(Username.equals("Full Name")){
            txtFullName.setText("");
        }
    }//GEN-LAST:event_txtFullNameFocusGained

    private void txtFullNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFullNameFocusLost
        String Username = txtFullName.getText();
         if(Username.equals("")|| Username.equals("Full Name")){
            txtFullName.setText("Full Name");
        }
    }//GEN-LAST:event_txtFullNameFocusLost

    private void txtFullNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFullNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFullNameActionPerformed

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
            java.util.logging.Logger.getLogger(register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new register().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bgLayout;
    private javax.swing.JLabel bgLogin;
    private javax.swing.JLabel btnClose;
    private javax.swing.JButton btnDaftar;
    private javax.swing.JLabel iconFullName;
    private javax.swing.JLabel iconPassword;
    private javax.swing.JLabel iconUsername;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel mt;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JLabel txtLogin;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

}
