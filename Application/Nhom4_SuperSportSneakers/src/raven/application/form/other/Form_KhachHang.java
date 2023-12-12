/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.KhachHang;
import Model.NhanVien;
import Repository.KhachHangRepositoryM;
import Utils.Auth;
import Utils.MsgBox;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import Utils.Validate;
import Utils.XDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.Timer;
import raven.application.Application;

/**
 *
 * @author manhnt
 */
public class Form_KhachHang extends javax.swing.JPanel {

    private static final KhachHangRepositoryM khachHangRepository = new KhachHangRepositoryM();
    private static List<KhachHang> list = new ArrayList<>();
    private static List<Object> listLSGD = new ArrayList<>();
    private static int page = 1;
    private static int page2 = 1;
    private static int lmit = 4;
    private static int gioiHanPage = (int) ((Math.ceil(khachHangRepository.getRowCountKH() / lmit))) + 1;

    //private static int gioiHanPage2 = (int) ((Math.ceil(khachHangRepository.getRowCountKH() / lmit)));
    private static int index = -1;
    private NhanVien nhanVien = Auth.nv;

    /**
     * Creates new form Form_KhachHang
     */
    public Form_KhachHang() {
        initComponents();
        init();
    }

    void init() {
        list = khachHangRepository.getAll(page, lmit);
        fillToTableKH(list);
        lblPageTTKH.setText(1 + " / " + gioiHanPage);
        setBtn();
    }

    private void setBtn() {
        boolean ktr = (this.index >= 0);
        btnInsert.setEnabled(!ktr);
        btnUpdate.setEnabled(ktr);
    }

    private void fillToTableKH(List<KhachHang> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblKH.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (KhachHang kh : list) {
            dtm.addRow(kh.rowData(i));
            i++;
        }
    }

    private void fillToTableLSDD(List<Object> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblLSGD.getModel();
        dtm.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            dtm.addRow((Object[]) list.get(i + 1));
        }
    }

    private void showData(int index) {
        KhachHang khachHang = khachHangRepository.getAll(page, lmit).get(index);
        txtDiaChi.setText(khachHang.getDiaChi());
        txtDiem.setText(khachHang.getDiem() + "");
        txtEmail.setText(khachHang.getEmail());
        txtHoTen.setText(khachHang.getTenKhachHang());
        txtMaKH.setText(khachHang.getMaKhachHang());
        txtNgaySinh.setDate(khachHang.getNgaySinh());
        txtSDT.setText(khachHang.getSdt());
        boolean gioiTinh = khachHang.isGioiTinh();
        rdoNam.setSelected(gioiTinh);
        rdoNu.setSelected(!gioiTinh);
        cboCapBac.setSelectedIndex(khachHang.getCapBac());
    }

    private KhachHang getForm() {
        // Khi có nhân viên thì mở cmt
        //NhanVien nhanVien = Auth.nv;
        String maKH = txtMaKH.getText().trim();
        String tenKH = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String ngaySinh = txtNgaySinh.getDate() + "";
        System.out.println(ngaySinh);
        boolean gioiTinh = rdoNam.isSelected();
        int maxIDKH = khachHangRepository.getRowCountKH() + 1;
        if (maKH.isEmpty()) {
            if (maxIDKH < 10) {
                maKH = "PT" + "00" + maxIDKH;
            } else if (maxIDKH < 100) {
                maKH = "PT" + "0" + maxIDKH;
            } else {
                maKH = "PT" + maxIDKH;
            }
        } else {
            maKH = txtMaKH.getText().trim();
        }
        if (tenKH.isEmpty()) {
            MsgBox.aleart(this, "Tên khách hàng chưa có !");
            txtHoTen.requestFocus();
            return null;
        } else {
            if (Validate.checkLength(tenKH, 50)) {
                MsgBox.aleart(this, "Tên khách hàng tối da 50 ký tự !");
                txtHoTen.requestFocus();
                return null;
            }
            if (!Validate.isName(tenKH)) {
                MsgBox.aleart(this, "Tên khách hàng sai đinh dạng !");
                txtHoTen.requestFocus();
                return null;
            }
        }
        if (sdt.isEmpty()) {
            MsgBox.aleart(this, "Số điện thoại khách hàng chưa có  !");
            txtSDT.requestFocus();
            return null;
        } else {
            if (Validate.checkLength(sdt, 15)) {
                MsgBox.aleart(this, "Số điện thoại tối da 15 ký tự !");
                txtSDT.requestFocus();
                return null;
            }
            if (!Validate.isPhoneNumber(sdt)) {
                MsgBox.aleart(this, "Số điện thoại đinh dạng !");
                txtSDT.requestFocus();
                return null;
            }
        }
        if (email.isEmpty()) {
            MsgBox.aleart(this, "Emal khách hàng chưa có  !");
            txtEmail.requestFocus();
            return null;
        } else {
            if (Validate.checkLength(email, 50)) {
                MsgBox.aleart(this, "Email tối da 50 ký tự !");
                txtEmail.requestFocus();
                return null;
            }
            if (!Validate.isEmail(email)) {
                MsgBox.aleart(this, "Email đinh dạng !");
                txtEmail.requestFocus();
                return null;
            }
        }

        if (diaChi.isEmpty()) {
            MsgBox.aleart(this, "Đia chỉ khách hàng chưa có  !");
            txtDiaChi.requestFocus();
            return null;
        }
        Date date = null;
/// ahasfjah
        
        try {
            date = txtNgaySinh.getDate();

        } catch (Exception e) {
            MsgBox.aleart(this, "Ngày tháng năm ko hợp lệ");
            return null;
        }
        if (date != null ) {
            if (date == null ) {
                MsgBox.aleart(this, "Bạn hãy điền đúng ngày dd-MM-yyy");
                return null;
            }

        }

        if (date == null ) {
            MsgBox.aleart(this, "Bạn hãy điền đủ  ngày");
            return null;
        }

        if (date != null ) {

            System.out.println("Vaof ");
            if (!Utils.Validate.isDate(XDate.toString(date, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày sinh sai định dạng dd-MM-yyyy");
                txtNgaySinh.requestFocus();
                return null;
            }
            if (!Utils.Validate.isDate(XDate.toString(date, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày sinh sai định dạng dd-MM-yyyy");
                txtNgaySinh.requestFocus();
                return null;
            }
            try {
                XDate.toDate(XDate.toString(date, "dd-MM-yyyy"), "dd-MM-yyyy");
              //  XDate.toDate(XDate.toString(date2, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");

                return null;
            }

           
        }

        if (Utils.Validate.isEmpty(XDate.toString(date, "dd-MM-yyyy"))) {
            MsgBox.aleart(this, "Ngày bắt đầu không được để trống");
            txtNgaySinh.requestFocus();
            return null;
        } else {
            if (!Utils.Validate.isDate(XDate.toString(date, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày bắt đầu sai định dạng dd-MM-yyyy");
                txtNgaySinh.requestFocus();
                return null;
            }
            try {
                XDate.toDate(XDate.toString(date, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");
                txtNgaySinh.requestFocus();
                return null;
            }
        }

        if (Utils.Validate.isEmpty(XDate.toString(date, "dd-MM-yyyy"))) {
            MsgBox.aleart(this, "Ngày kết thúc không được để trống");
            txtNgaySinh.requestFocus();
            return null;
        } else {
            if (!Utils.Validate.isDate(XDate.toString(date, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày kết thúc sai định dạng dd-MM-yyyy");
                txtNgaySinh.requestFocus();
                return null;
            }
            try {
                XDate.toDate(XDate.toString(date, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");
                txtNgaySinh.requestFocus();
                return null;
            }
        }
        //date = XDate.convertDateFormat(date, "MM-dd-yyyy");

        return new KhachHang(nhanVien, maKH, tenKH, sdt, date, gioiTinh, email, diaChi);
    }

    private void insert() {
        KhachHang khachHang = getForm();
        if (khachHang == null) {
            return;
        }
        int ketQua = khachHangRepository.insertAll(khachHang);
        if (ketQua == 1) {
            MsgBox.aleart(this, "Thêm thành công một khách hàng");
            list = khachHangRepository.getAll(page, lmit);
            fillToTableKH(list);
            clearForm();
        } else {
            MsgBox.aleart(this, "Thêm thất bại một khách hàng");
        }
    }

    private void update() {
        KhachHang khachHang = getForm();
        if (khachHang == null) {
            return;
        }
        int ketQua = khachHangRepository.update(khachHang);
        if (ketQua == 1) {
            MsgBox.aleart(this, "Sửa thành công một khách hàng");
            list = khachHangRepository.getAll(page, lmit);
            fillToTableKH(list);
            clearForm();
        } else {
            MsgBox.aleart(this, "Sửa thất bại một khách hàng");
        }
    }

    private void clearForm() {
        index = -1;
        setBtn();
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtNgaySinh.setDate(null);
        rdoNam.setSelected(true);
        txtDiem.setText("");
        cboCapBac.setSelectedIndex(3);
    }

    private void first() {
        if (cboGioiTinh.getSelectedIndex() > 0) {
            lblPageTTKH.setText(1 + " / " + gioiHanPage);
            list = khachHangRepository.listSearchCbo(cboGioiTinh.getSelectedIndex() - 1, page, lmit);
            fillToTableKH(list);
            return;
        }
        lblPageTTKH.setText(1 + " / " + gioiHanPage);
        list = khachHangRepository.getAll(1, lmit);
        fillToTableKH(list);
    }

    private void prev() {
        page--;
        if (cboGioiTinh.getSelectedIndex() > 0) {
            if (page >= 1) {
                lblPageTTKH.setText(page + " / " + gioiHanPage);
                list = khachHangRepository.listSearchCbo(cboGioiTinh.getSelectedIndex() - 1, page, lmit);
                fillToTableKH(list);

            }
            page = 1;
            return;
        }
        if (page >= 1) {
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            list = khachHangRepository.getAll(page, lmit);
            fillToTableKH(list);
            return;
        }
        page = 1;

    }

    private void next() {
        page++;
        if (cboGioiTinh.getSelectedIndex() > 0) {
            if (page <= gioiHanPage) {
                list = khachHangRepository.listSearchCbo(cboGioiTinh.getSelectedIndex() - 1, page, lmit);
                fillToTableKH(list);
                lblPageTTKH.setText(page + " / " + gioiHanPage);
                return;
            }
            page = gioiHanPage;
            return;
        }
        if (page <= gioiHanPage) {
            list = khachHangRepository.getAll(page, lmit);
            fillToTableKH(list);
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            return;
        }
        page = gioiHanPage;
    }

    private void last() {
        if (cboGioiTinh.getSelectedIndex() > 0) {
            lblPageTTKH.setText(gioiHanPage + " / " + gioiHanPage);
            list = khachHangRepository.listSearchCbo(cboGioiTinh.getSelectedIndex() - 1, page, lmit);
            fillToTableKH(list);
        }
        lblPageTTKH.setText(gioiHanPage + " / " + gioiHanPage);
        list = khachHangRepository.getAll(gioiHanPage, lmit);
        fillToTableKH(list);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngKHGT = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDiem = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cboCapBac = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        pbgTienDo = new javax.swing.JProgressBar();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tabKH = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKH = new javax.swing.JTable();
        btnDau = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        btnCuoi = new javax.swing.JButton();
        btnTien = new javax.swing.JButton();
        lblPageTTKH = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLSGD = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1000, 772));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Mã KH :");

        txtMaKH.setEditable(false);

        jLabel3.setText("SDT :");

        jLabel4.setText("Điểm");

        txtDiem.setEditable(false);

        jLabel5.setText("Họ và Tên :");

        jLabel6.setText("Email :");

        jLabel7.setText("Địa Chỉ :");

        cboCapBac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đồng", "Bạc", "Vàng", "Không có" }));

        jLabel8.setText("Cấp Bậc :");

        jLabel9.setText("Tiến Trình :");

        jLabel10.setText("Giới Tính :");

        btngKHGT.add(rdoNam);
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        btngKHGT.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel11.setText("Ngày Sinh :");

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        pbgTienDo.setForeground(new java.awt.Color(255, 102, 0));

        txtNgaySinh.setDateFormatString("dd-MM-yyyy");
        txtNgaySinh.setMaxSelectableDate(new java.util.Date(253370743316000L));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(72, 72, 72)
                                        .addComponent(rdoNu))
                                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(251, 251, 251))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboCapBac, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pbgTienDo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 436, Short.MAX_VALUE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(430, 430, 430)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCapBac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)))
                    .addComponent(pbgTienDo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInsert)
                            .addComponent(btnClear))
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdate)
                        .addGap(34, 34, 34))))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thiết lập thông tin khách hàng ");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Thông tin khách hàng");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setText("Tìm kiếm :");

        txtTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimActionPerformed(evt);
            }
        });

        jLabel14.setText("Giới tính :");

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "------- Tất cả -------", "Nữ ", "Nam" }));
        cboGioiTinh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboGioiTinhItemStateChanged(evt);
            }
        });

        tblKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KH", "Họ tên", "Giới tính", "Số điện thoại", "Địa chỉ", "Email", "Ngày sinh", "Điểm", "Cấp bậc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKH);

        btnDau.setText("<<");
        btnDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDauActionPerformed(evt);
            }
        });

        btnLui.setText("<");
        btnLui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuiActionPerformed(evt);
            }
        });

        btnCuoi.setText(">>");
        btnCuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuoiActionPerformed(evt);
            }
        });

        btnTien.setText(">");
        btnTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTienActionPerformed(evt);
            }
        });

        lblPageTTKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPageTTKH.setText("1/ 2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDau, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPageTTKH)
                .addGap(31, 31, 31)
                .addComponent(btnTien, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(318, 318, 318))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLui)
                    .addComponent(btnCuoi)
                    .addComponent(btnTien)
                    .addComponent(lblPageTTKH)
                    .addComponent(btnDau))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tabKH.addTab("Thông tin khách hàng", jPanel2);

        tblLSGD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày giao dịch", "Tổng tiền", "Điểm đã dùng", "Cấp bậc tại giao dịch", "Trạng thái"
            }
        ));
        tblLSGD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLSGDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLSGD);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1364, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        tabKH.addTab("Lịch sử giao dịch", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabKH, javax.swing.GroupLayout.PREFERRED_SIZE, 1364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabKH, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKHMouseClicked
        pbgTienDo.setMaximum(0);
        pbgTienDo.setValue(0);
        index = tblKH.getSelectedRow();
        setBtn();
        showData(index);
        KhachHang khachHang = khachHangRepository.getAll(page, lmit).get(index);
        String maKH = txtMaKH.getText().trim();
        listLSGD = khachHangRepository.listLSGDByMaKH(maKH, page2, lmit);

        if (khachHang.getMaKhachHang().equals("KH000") || khachHang.getCapBac() == 3) {
            pbgTienDo.setMaximum(0);
            pbgTienDo.setValue(0);
        }
        if (khachHang.getCapBac() == null) {

        } else {
            if (khachHang.getCapBac() == 0 && !khachHang.getMaKhachHang().equals("KH000")) {
                pbgTienDo.setMaximum(5000000);
                BigDecimal money = khachHangRepository.getTongTienKH(maKH);
                if (money != null) {
                    int tien = (int) money.doubleValue();
                    pbgTienDo.setValue(tien);
                }
            }
            if (khachHang.getCapBac() == 1) {
                pbgTienDo.setMaximum(10000000);
                BigDecimal money = khachHangRepository.getTongTienKH(maKH);
                if (money != null) {
                    int tien = (int) money.doubleValue();
                    pbgTienDo.setValue(tien);
                }
            }
            if (khachHang.getCapBac() == 2) {
                pbgTienDo.setMaximum(20000000);
                BigDecimal money = khachHangRepository.getTongTienKH(maKH);
                if (money != null) {
                    int tien = (int) money.doubleValue();
                    pbgTienDo.setValue(tien);
                }
            }
        }
//        Timer timer = new Timer(1, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int value = pgbChao.getValue();
//                if (value < 100) {
//                    pgbChao.setValue(value + 1);
//                }
//            }
//        });
//        timer.start();
        fillToTableLSDD(listLSGD);
    }//GEN-LAST:event_tblKHMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();

    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDauActionPerformed
        first();
    }//GEN-LAST:event_btnDauActionPerformed

    private void btnLuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuiActionPerformed
        prev();
    }//GEN-LAST:event_btnLuiActionPerformed

    private void btnTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTienActionPerformed
        next();
    }//GEN-LAST:event_btnTienActionPerformed

    private void btnCuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuoiActionPerformed
        last();
    }//GEN-LAST:event_btnCuoiActionPerformed

    private void txtTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimActionPerformed
        String thongTin = this.txtTim.getText().trim();
        list = khachHangRepository.listSearch(thongTin, page, lmit);
        fillToTableKH(list);
    }//GEN-LAST:event_txtTimActionPerformed

    private void cboGioiTinhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboGioiTinhItemStateChanged
        int gt = cboGioiTinh.getSelectedIndex();
        if (gt == 0) {
            list = khachHangRepository.getAll(page, lmit);
            fillToTableKH(list);
        } else if (gt == 1) {
            list = khachHangRepository.listSearchCbo(gt - 1, page, lmit);
            fillToTableKH(list);
        } else {
            list = khachHangRepository.listSearchCbo(gt - 1, page, lmit);
            fillToTableKH(list);
        }
    }//GEN-LAST:event_cboGioiTinhItemStateChanged

    private void tblLSGDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLSGDMouseClicked
        int index = tblLSGD.getSelectedRow();
        String maHD = tblLSGD.getValueAt(index, 1).toString();
        String maKH = txtMaKH.getText().trim();
        String tenKH = txtHoTen.getText();
        ViewLSGDManh v = new ViewLSGDManh(new Application(), true, maKH, maHD, tenKH);
        v.setVisible(true);
    }//GEN-LAST:event_tblLSGDMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCuoi;
    private javax.swing.JButton btnDau;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnTien;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup btngKHGT;
    private javax.swing.JComboBox<String> cboCapBac;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPageTTKH;
    private javax.swing.JProgressBar pbgTienDo;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tabKH;
    private javax.swing.JTable tblKH;
    private javax.swing.JTable tblLSGD;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDiem;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaKH;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
