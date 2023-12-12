/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package raven.application.form.other;

import Model.HoaDon;
import Model.KhachHang;
import Model.PhieuGiaoHang;
import Repository.HoaDon_MRepositoryM;
import Repository.PhieuGiaoHangRepository;
import Utils.MsgBox;
import Utils.Validate;
import Utils.XDate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author manhnt
 */
public class View_TT_DatHang extends javax.swing.JDialog {

    private final PhieuGiaoHangRepository phieuGiaoHangRepository = new PhieuGiaoHangRepository();

    PhieuGiaoHang phieuGiaoHang_ = new PhieuGiaoHang();
    long idHD = -1;
    long idKh = -1;
    private HoaDon_MRepositoryM hoaDon_MRepositoryM = new HoaDon_MRepositoryM();

    /**
     * Creates new form View_TT_DatHang
     */
    public View_TT_DatHang(java.awt.Frame parent, boolean modal, long idHD, long idKh) {
        super(parent, modal);
        initComponents();
        this.idHD = idHD;
        this.idKh = idKh;
    }

    private PhieuGiaoHang getForm() {
        String tenNgN = txtTenKh.getText().trim();
        String sdtNgNh = txtSDTKH.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String tenShip = txtTenShip.getText().trim();
        String sdtShip = txtSDTShip.getText().trim();
        String giaShip = txtGiaShip.getText().trim();
        String donViVC = txtDonViVanChuyen.getText().trim();
        int httt = cboHTT.getSelectedIndex() + 2;

        String maPhieu = "";
        int max = phieuGiaoHangRepository.getRowCount() + 1;
        if (maPhieu.isEmpty()) {
            if (max < 10) {
                maPhieu = "PGH" + "00" + maPhieu;
            } else if (max < 100) {
                maPhieu = "PGH" + "0" + max;
            } else {
                maPhieu = "PGH" + max;
            }
        }

        if (tenNgN.isEmpty()) {
            MsgBox.aleart(this, "Tên khách hàng chưa có !");
            txtTenKh.requestFocus();
            return null;
        } else {
            if (Validate.checkLength(tenNgN, 50)) {
                MsgBox.aleart(this, "Tên khách hàng tố đa 50 ký tự !");
                txtTenKh.requestFocus();
                return null;
            }

            if (!Validate.isName(tenNgN)) {
                MsgBox.aleart(this, "Tên khách hàng sai đinh dạng !");
                txtTenKh.requestFocus();
                return null;
            }
        }

        if (sdtNgNh.isEmpty()) {
            MsgBox.aleart(this, "SDT khách hàng chưa có !");
            txtSDTKH.requestFocus();
            return null;
        } else {
            if (!Validate.isPhoneNumber(sdtNgNh)) {
                MsgBox.aleart(this, "SDT sai định dạng!");
                txtSDTKH.requestFocus();
                return null;
            }
        }
        if (diaChi.isEmpty()) {
            MsgBox.aleart(this, "Đia chỉ khách hàng chưa có !");
            txtDiaChi.requestFocus();
            return null;
        }

        if (tenShip.isEmpty()) {
            MsgBox.aleart(this, "Tên Ship chưa có !");
            txtTenShip.requestFocus();
            return null;
        } else {
            if (Validate.checkLength(tenShip, 50)) {
                MsgBox.aleart(this, "Tên Ship tố đa 50 ký tự !");
                txtTenShip.requestFocus();
                return null;
            }

            if (!Validate.isName(tenShip)) {
                MsgBox.aleart(this, "Tên ship sai đinh dạng !");
                txtTenShip.requestFocus();
                return null;
            }
        }

        if (sdtShip.isEmpty()) {
            MsgBox.aleart(this, "SDT ship chưa có !");
            txtSDTShip.requestFocus();
            return null;
        } else {
            if (!Validate.isPhoneNumber(sdtShip)) {
                MsgBox.aleart(this, "SDT sai định dạng!");
                txtSDTShip.requestFocus();
                return null;
            }
        }
        BigDecimal giaShipB = BigDecimal.ZERO;
        if (giaShip.isEmpty()) {
            MsgBox.aleart(this, "Giá ship chưa có !");
            txtGiaShip.requestFocus();
            return null;
        } else {
            try {
                giaShipB = new BigDecimal(giaShip);
                if (Float.parseFloat(giaShip) < 0) {
                    MsgBox.aleart(this, "Giá ship phải > 0 !");
                    txtGiaShip.requestFocus();
                    return null;
                }
            } catch (Exception e) {
                MsgBox.aleart(this, "Giá ship phải là số !");
                txtGiaShip.requestFocus();
                return null;
            }
        }
        if (donViVC.isEmpty()) {
            MsgBox.aleart(this, "Đơn vị ship chưa có !");
            txtDonViVanChuyen.requestFocus();
            return null;
        }
        Date date = null;

        try {

            date = txtNgayNhan.getDate();

        } catch (Exception e) {
            MsgBox.aleart(this, "Ngày tháng năm ko hợp lệ");
            return null;
        }
        if (date != null) {
            if (date == null) {
                MsgBox.aleart(this, "Bạn hãy điền đủ  ngày");
                return null;
            }

        }

        if (date == null) {
            MsgBox.aleart(this, "Bạn hãy điền đủ  ngày");
            return null;
        }

        if (date != null) {

            if (!Utils.Validate.isDate(XDate.toString(date, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày sinh sai định dạng dd-MM-yyyy");
                txtNgayNhan.requestFocus();
                return null;
            }
            try {

                XDate.toDate(XDate.toString(date, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");

                return null;
            }

        }
        
        // thêm 
        
        if (compareDates(XDate.toString(date, "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"))) {
                    MsgBox.aleart(this, "Ngày nhận hàng >= ngày hôm nay");
                    return null;
                }

        ///
        try {
            date = txtNgayNhan.getDate();
        } catch (Exception e) {
            MsgBox.aleart(this, "Ngày nhận hàng ko hợp lê !!!");
            return null;
        }
        if (date == null) {
            MsgBox.aleart(this, "Bạn hãy nhập ngày dự kiến nhận hàng");
            txtNgayNhan.requestFocus();
            return null;
        }

        HoaDon hd = new HoaDon();
        hd.setId(idHD);
        KhachHang khachHang = new KhachHang();
        khachHang.setId(idKh);
        hd.setTrangThai(httt);
        PhieuGiaoHang phieuGiaoHang = new PhieuGiaoHang();
        phieuGiaoHang.setIdKH(khachHang);
        phieuGiaoHang.setMaVanDon(maPhieu);
        phieuGiaoHang.setTenNguoiNhan(tenNgN);
        phieuGiaoHang.setSdtNNguoiNhan(sdtNgNh);
        phieuGiaoHang.setDiaChi(diaChi);
        phieuGiaoHang.setTenShip(tenShip);
        phieuGiaoHang.setSdtShip(sdtShip);
        phieuGiaoHang.setGiaShip(giaShipB);
        phieuGiaoHang.setDonViVanChuyen(donViVC);
        phieuGiaoHang.setNgayHoanThanh(date);
        phieuGiaoHang.setIdHD(hd);

        return phieuGiaoHang;

    }

    public PhieuGiaoHang getPhieuGH() {
        return phieuGiaoHang_;
    }
 public boolean compareDates(String dateStr1, String dateStr2) {
        // Định dạng để chuyển đổi từ chuỗi sang LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Chuyển đổi chuỗi thành LocalDate
        LocalDate date1 = LocalDate.parse(dateStr1, formatter);
        LocalDate date2 = LocalDate.parse(dateStr2, formatter);

        // So sánh ngày
        return date2.isAfter(date1);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTenKh = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSDTKH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenShip = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtSDTShip = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtGiaShip = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDonViVanChuyen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNgayNhan = new com.toedter.calendar.JDateChooser();
        btnTaoPhieu = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cboHTT = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thông tin đặt hàng");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Thông tin đặt hàng ");

        jLabel2.setText("Tên người nhận :");

        jLabel3.setText("SDT người nhận :");

        jLabel4.setText("Tên Ship :");

        jLabel5.setText("Địa chỉ :");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        jLabel6.setText("SDT Ship :");

        jLabel7.setText("Giá Ship :");

        jLabel8.setText("Đơn vị vẩn chuyển :");

        jLabel9.setText("Ngày nhận hàng :");

        txtNgayNhan.setDateFormatString("dd-MM-yyyy");
        txtNgayNhan.setMaxSelectableDate(new java.util.Date(253370743316000L));

        btnTaoPhieu.setText("Tạo phiếu ");
        btnTaoPhieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuActionPerformed(evt);
            }
        });

        jLabel10.setText("Hình thức thanh toán :");

        cboHTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thanh toán khi nhận hàng", "Thanh toán trước" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenShip, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(txtSDTShip))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(txtGiaShip))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(txtDonViVanChuyen))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(txtNgayNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboHTT, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(284, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(386, 386, 386)
                .addComponent(btnTaoPhieu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSDTShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtGiaShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDonViVanChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboHTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtNgayNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnTaoPhieu)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoPhieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuActionPerformed
        phieuGiaoHang_ = getForm();
        if (phieuGiaoHang_ != null) {
            int kq = phieuGiaoHangRepository.insert(phieuGiaoHang_);

            hoaDon_MRepositoryM.updateLoaiHD(idHD, 1, phieuGiaoHang_.getIdHD().getTrangThai());
            if (kq != -1) {
                System.out.println(phieuGiaoHang_);
                MsgBox.aleart(this, "Tạo thành công 1 phiếu giao hàng : " + phieuGiaoHang_.getMaVanDon());
                this.dispose();
            } else {
                MsgBox.aleart(this, "Tạo không thành công phiếu giao hàng : " + phieuGiaoHang_.getMaVanDon());
            }
        } else {
            MsgBox.aleart(this, "Tạo không thành công phiếu giao hàng : " + phieuGiaoHang_.getMaVanDon());
        }
    }//GEN-LAST:event_btnTaoPhieuActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(View_TT_DatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(View_TT_DatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(View_TT_DatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(View_TT_DatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                View_TT_DatHang dialog = new View_TT_DatHang(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTaoPhieu;
    private javax.swing.JComboBox<String> cboHTT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtDonViVanChuyen;
    private javax.swing.JTextField txtGiaShip;
    private com.toedter.calendar.JDateChooser txtNgayNhan;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtSDTShip;
    private javax.swing.JTextField txtTenKh;
    private javax.swing.JTextField txtTenShip;
    // End of variables declaration//GEN-END:variables
}
