/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.DotGiamGia_M;
import Model.HoaDon;
import Model.KhachHang;
import Model.KichThuoc;
import Model.MauSac;
import Model.NhanVien;
import Model.PhieuGiaoHang;
import Model.SanPham;
import Model.ThuongHieu;
import Repository.DotGiamGia_MRpository;
import Repository.HoaDon_MRepositoryM;
import Repository.PhieuGiaoHangRepository;
import Repository.SanPhamCT_Repository;
import Utils.Auth;
import Utils.MsgBox;
import Utils.Validate;
import Utils.XDate;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import raven.application.Application;

/**
 *
 * @author manhnt
 */
public class Form_PhieuGiaoHang extends javax.swing.JPanel {

    private PhieuGiaoHangRepository pghr = new PhieuGiaoHangRepository();
    private List<PhieuGiaoHang> list = new ArrayList<>();
    private List<HoaDon> lisHD = new ArrayList<>();
    private List<Object> listCTPG = new ArrayList<>();
    private static PhieuGiaoHangRepository phieuGiaoHangRepository = new PhieuGiaoHangRepository();
    private static SanPhamCT_Repository sanPhamCT_Repository = new SanPhamCT_Repository();
    private static HoaDon_MRepositoryM hoaDon_MRepositoryM = new HoaDon_MRepositoryM();
    private static int page = 1;
    private static int lmit = 10;
    private static int gioiHanPage = (int) ((Math.ceil(phieuGiaoHangRepository.getRowCount() / lmit))) + 1;
    private KhachHang khachHang = new KhachHang();

    private static int indexPGH = -1;

    private NhanVien nhanVien = Auth.nv;

    /**
     * Creates new form Form_PhieuGiaoHang
     */
    public Form_PhieuGiaoHang() {
        initComponents();
        init();
    }

    void init() {
        list = pghr.getAll(page, lmit);
        filfToTablePGH(list);
        lisHD = phieuGiaoHangRepository.listDSHD();
        fillToTableHD(lisHD);
        txtDau.setDate(new Date());
        setBtn();
    }

    private void filfToTablePGH(List<PhieuGiaoHang> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblPGH.getModel();
        dtm.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            dtm.addRow(list.get(i).rowData(i));
        }
    }

    private void fillToTableHD(List<HoaDon> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblHD.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (HoaDon hd : list) {
            dtm.addRow(hd.rowDataPGH(i));
            i++;
        }
    }

    private void fillToTableCTPH(List<Object> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblCTPGH.getModel();
        dtm.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            dtm.addRow((Object[]) list.get(i));
        }
    }

    private KhachHang getKH(int index) {
        KhachHang khachHang = phieuGiaoHangRepository.listDSHD().get(index).getIdKH();
        return khachHang;
    }

    private PhieuGiaoHang getForm() {
        String maPhieu = txtMaPhieu.getText().trim();;
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

        String tennKh = txtTenKh.getText().trim();;
        String sdtKh = txtSDTKh.getText().trim();
        String diaChi = txtDiaChi.getText();
        String tenShip = txtTenShip.getText().trim();
        String sdtShip = txtSdtShip.getText().trim();
        String giaShip = txtGiaShip.getText().trim();
        String donViVC = txtDonViVC.getText().trim();

        if (tennKh.isEmpty()) {
            MsgBox.aleart(this, "Tên khách hàng chưa có !");
            txtTenKh.requestFocus();
            return null;
        } else {
            if (Validate.checkLength(tennKh, 50)) {
                MsgBox.aleart(this, "Tên khách hàng tố đa 50 ký tự !");
                txtTenKh.requestFocus();
                return null;
            }
            if (!Validate.isName(tennKh)) {
                MsgBox.aleart(this, "Tên khách hàng sai đinh dạng !");
                txtTenKh.requestFocus();
                return null;
            }
        }

        if (sdtKh.isEmpty()) {
            MsgBox.aleart(this, "SDT khách hàng chưa có !");
            txtSDTKh.requestFocus();
            return null;
        } else {
            if (!Validate.isPhoneNumber(sdtKh)) {
                MsgBox.aleart(this, "SDT sai định dạng!");
                txtSDTKh.requestFocus();
                return null;
            }
        }
        if (diaChi.isEmpty()) {
            MsgBox.aleart(this, "Đia chỉ khách hàng chưa có !");
            txtDiaChi.requestFocus();
            return null;
        }
        if (Validate.checkLength(diaChi, 100)) {
            MsgBox.aleart(this, "Đia chỉ đa 100 ký tự !");
            txtTenKh.requestFocus();
            return null;
        }

        if (tenShip.isEmpty()) {
            MsgBox.aleart(this, "Tên Ship chưa có !");
            txtTenShip.requestFocus();
            return null;
        } else {
            if (Validate.checkLength(tenShip, 50)) {
                MsgBox.aleart(this, "Tên Ship tối đa 50 ký tự !");
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
            txtSdtShip.requestFocus();
            return null;
        } else {
            if (!Validate.isPhoneNumber(sdtShip)) {
                MsgBox.aleart(this, "SDT sai định dạng!");
                txtSdtShip.requestFocus();
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
                    MsgBox.aleart(this, "Giá ship >= 0 !");
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
            txtDonViVC.requestFocus();
            return null;
        }

        if (Validate.checkLength(donViVC, 100)) {
            MsgBox.aleart(this, "Đơn vị vận chuyển tối đa 100 ký tự !");
            txtDonViVC.requestFocus();
            return null;
        }
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = txtDau.getDate();
            date2 = txtDau1.getDate();

        } catch (Exception e) {
            MsgBox.aleart(this, "Ngày tháng năm ko hợp lệ");
            return null;
        }
        if (date1 != null || date2 != null) {
            if (date1 == null || date2 == null) {
                MsgBox.aleart(this, "Bạn hãy điền đủ 2 ngày");
                return null;
            }

        }

        if (date1 == null || date2 == null) {
            MsgBox.aleart(this, "Bạn hãy điền đủ 2 ngày");
            return null;
        }

        if (date1 != null && date2 != null) {

            System.out.println("Vaof ");
            if (!Utils.Validate.isDate(XDate.toString(date1, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày sinh sai định dạng dd-MM-yyyy");
                txtDau.requestFocus();
                return null;
            }
            if (!Utils.Validate.isDate(XDate.toString(date2, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày sinh sai định dạng dd-MM-yyyy");
                txtDau1.requestFocus();
                return null;
            }
            try {
                XDate.toDate(XDate.toString(date1, "dd-MM-yyyy"), "dd-MM-yyyy");
                XDate.toDate(XDate.toString(date2, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");

                return null;
            }

            if (!compareDates(XDate.toString(date1, "dd-MM-yyyy"), XDate.toString(date2, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày nhận > ngày tạo");
                return null;
            }
        }

        if (Utils.Validate.isEmpty(XDate.toString(date1, "dd-MM-yyyy"))) {
            MsgBox.aleart(this, "Ngày tạo không được để trống");
            txtDau.requestFocus();
            return null;
        } else {
            if (!Utils.Validate.isDate(XDate.toString(date1, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày tạo sai định dạng dd-MM-yyyy");
                txtDau.requestFocus();
                return null;
            }
            try {
                XDate.toDate(XDate.toString(date1, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");
                txtDau.requestFocus();
                return null;
            }
        }

        if (Utils.Validate.isEmpty(XDate.toString(date2, "dd-MM-yyyy"))) {
            MsgBox.aleart(this, "Ngày nhận không được để trống");
            txtDau1.requestFocus();
            return null;
        } else {
            if (!Utils.Validate.isDate(XDate.toString(date2, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày nhận sai định dạng dd-MM-yyyy");
                txtDau1.requestFocus();
                return null;
            }
            try {
                XDate.toDate(XDate.toString(date1, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");
                txtDau1.requestFocus();
                return null;
            }
        }
        if (date2 == null) {
            MsgBox.aleart(this, "Bạn hãy nhập ngày dự kiến nhận hàng");
            txtDau1.requestFocus();
            return null;
        }
        PhieuGiaoHang phieuGiaoHang = new PhieuGiaoHang();
        phieuGiaoHang.setMaVanDon(maPhieu);
        phieuGiaoHang.setTenNguoiNhan(tennKh);
        phieuGiaoHang.setSdtNNguoiNhan(sdtKh);
        phieuGiaoHang.setDiaChi(diaChi);
        phieuGiaoHang.setTenShip(tenShip);
        phieuGiaoHang.setSdtShip(sdtShip);
        phieuGiaoHang.setGiaShip(giaShipB);
        phieuGiaoHang.setDonViVanChuyen(donViVC);
        phieuGiaoHang.setNgayHoanThanh(date2);

        return phieuGiaoHang;
    }

    private List<HoaDon> getHD() {
        List<HoaDon> list = new ArrayList<>();
        int rowCount = tblHD.getRowCount();
        long idKh1 = 0, idKh2 = 0;
        int count = 0;

        for (int i = 0; i < rowCount; i++) {
            Object ktr = tblHD.getValueAt(i, 4);

            if (ktr instanceof Boolean && (Boolean) ktr) {
                HoaDon currentHD = phieuGiaoHangRepository.listDSHD().get(i);
                idKh2 = currentHD.getIdKH().getId();

                if (count == 0) {
                    idKh1 = idKh2;
                    list.add(currentHD);
                    count++;
                } else if (idKh1 == idKh2) {
                    list.add(currentHD);
                    count++;
                } else {
                    MsgBox.aleart(this, "Phiếu giao hàng chỉ giao cho 1 khách hàng !!");
                    list.clear();
                    for (int j = 0; j < tblHD.getRowCount(); j++) {
                        tblHD.setValueAt(false, j, 4);
                    }
                    return null;
                }
            }
        }

        return list;
    }

    private void showData(int index) {
        PhieuGiaoHang phieuGiaoHang = phieuGiaoHangRepository.getAll(page, lmit).get(index);
        txtDiaChi.setText(phieuGiaoHang.getIdKH().getDiaChi());
        txtDonViVC.setText(phieuGiaoHang.getDonViVanChuyen());
        txtGiaShip.setText(phieuGiaoHang.getGiaShip() + "");
        txtDau.setDate(phieuGiaoHang.getNgayTao());
        txtDau1.setDate(phieuGiaoHang.getNgayHoanThanh());
        txtSDTKh.setText(phieuGiaoHang.getIdKH().getSdt());
        txtSdtShip.setText(phieuGiaoHang.getSdtShip());
        txtTenKh.setText(phieuGiaoHang.getIdKH().getTenKhachHang());
        txtTenShip.setText(phieuGiaoHang.getTenShip());
        txtMaPhieu.setText(phieuGiaoHang.getMaVanDon());
        txtDonViVC.setText(phieuGiaoHang.getDonViVanChuyen());

    }

    private void first() {
        page = 1;
        lblPageTTKH.setText(1 + " / " + gioiHanPage);
        list = phieuGiaoHangRepository.getAll(1, lmit);
        filfToTablePGH(list);
    }

    private void prev() {
        page--;

        if (page >= 1) {
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            list = phieuGiaoHangRepository.getAll(page, lmit);
            filfToTablePGH(list);
            return;
        }
        page = 1;

    }

    private void next() {
        page++;
        if (page <= gioiHanPage) {
            list = phieuGiaoHangRepository.getAll(page, lmit);
            filfToTablePGH(list);
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            return;
        }
        page = gioiHanPage;
    }

    private void last() {
        lblPageTTKH.setText(gioiHanPage + " / " + gioiHanPage);
        list = phieuGiaoHangRepository.getAll(gioiHanPage, lmit);
        filfToTablePGH(list);
    }

    private void clearForm() {
        txtDiaChi.setText("");
        txtDonViVC.setText("");
        txtGiaShip.setText("");
        txtMaPhieu.setText("");
        txtDau1.setDate(null);
        txtSDTKh.setText("");
        txtSdtShip.setText("");
        txtTenKh.setText("");
        txtTenShip.setText("");
        indexPGH = -1;
        setBtn();
    }

    private void setBtn() {
        
        if(Auth.isManager()){
            btnChoGiao.setVisible(true);
            btnDangGiao.setVisible(true);
            btnHenLai.setVisible(true);
            btnHuy.setVisible(true);
            btnNhanHang.setVisible(true);
            btnSua.setVisible(true);
            btnThem.setVisible(true);
        }else{
             btnChoGiao.setVisible(false);
            btnDangGiao.setVisible(false);
            btnHenLai.setVisible(false);
            btnHuy.setVisible(false);
            btnNhanHang.setVisible(false);
            btnSua.setVisible(false);
            btnThem.setVisible(false);
        }

        boolean ktr = (indexPGH == -1);
        boolean ktrHuy = false, ktrTT = false;
        btnSua.setEnabled(!ktr);
        try {
            ktrHuy = tblPGH.getValueAt(indexPGH, 11).toString().equals("Hủy");
            btnHuy.setEnabled(!ktr && !ktrHuy);
            ktrTT = tblPGH.getValueAt(indexPGH, 11).toString().equals("Đã nhận hàng");

        } catch (Exception e) {
            btnHuy.setEnabled(false);

        }
        if (ktrHuy ) {
            btnDangGiao.setEnabled(false);
            btnHenLai.setEnabled(false);
            btnNhanHang.setEnabled(false);
            btnThem.setEnabled(false);
            btnSua.setEnabled(false);
            btnChoGiao.setEnabled(false);
        } else {
            btnDangGiao.setEnabled(true);
            btnHenLai.setEnabled(true);
            btnNhanHang.setEnabled(true);
            btnThem.setEnabled(true);
            btnSua.setEnabled(true);
            btnChoGiao.setEnabled(true);

        }
         if ( ktrTT || ktrHuy) {
            btnDangGiao.setEnabled(false);
            btnHenLai.setEnabled(false);
            btnNhanHang.setEnabled(false);
            btnThem.setEnabled(false);
            btnSua.setEnabled(false);
            btnChoGiao.setEnabled(false);
            btnHuy.setEnabled(false);
        } else {
            btnDangGiao.setEnabled(true);
            btnHenLai.setEnabled(true);
            btnNhanHang.setEnabled(true);
            btnThem.setEnabled(true);
            btnSua.setEnabled(true);
            btnChoGiao.setEnabled(true);
           // btnHuy.setEnabled(false);

        }
        btnThem.setEnabled(ktr);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtMaPhieu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTenKh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSDTKh = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDonViVC = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        txtDau = new com.toedter.calendar.JDateChooser();
        txtSdtShip = new javax.swing.JTextField();
        txtGiaShip = new javax.swing.JTextField();
        txtTenShip = new javax.swing.JTextField();
        txtDau1 = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        btnDangGiao = new javax.swing.JButton();
        btnNhanHang = new javax.swing.JButton();
        btnHenLai = new javax.swing.JButton();
        btnChoGiao = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPGH = new javax.swing.JTable();
        txtTim = new javax.swing.JTextField();
        cboTT = new javax.swing.JComboBox<>();
        btnDau = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        lblPageTTKH = new javax.swing.JLabel();
        btnTien = new javax.swing.JButton();
        btnCuoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCTPGH = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtMaPhieu.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Mã Phiếu :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Tên khách hàng :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Số điện thoại KH :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Địa chỉ");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Tên ship :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Số điện thoại S:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Giá ship :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Ngày tạo :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Đợn vị VC :");

        txtDonViVC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonViVCActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Ngày thanh toan :");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        txtDau.setDateFormatString("dd-MM-yyyy");
        txtDau.setEnabled(false);
        txtDau.setMaxSelectableDate(new java.util.Date(253370743316000L));

        txtDau1.setDateFormatString("dd-MM-yyyy");
        txtDau1.setMaxSelectableDate(new java.util.Date(253370743316000L));

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MÃ HD", "Ngày Tạo", "Khách Hang", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHD);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Danh sách hóa đơn ");

        jButton2.setText("Làm mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnDangGiao.setText("Đang giao");
        btnDangGiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangGiaoActionPerformed(evt);
            }
        });

        btnNhanHang.setText("Đã nhận hàng");
        btnNhanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanHangActionPerformed(evt);
            }
        });

        btnHenLai.setText("Hẹn lại");
        btnHenLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHenLaiActionPerformed(evt);
            }
        });

        btnChoGiao.setText("Chờ giao");
        btnChoGiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoGiaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSDTKh, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtGiaShip, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtSdtShip, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenShip, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(jLabel12)
                                    .addGap(42, 42, 42))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel11)
                                .addGap(48, 48, 48)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDau1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(txtDonViVC, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(91, 91, 91)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnDangGiao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(btnChoGiao))
                        .addGap(101, 101, 101)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2)
                            .addComponent(btnHenLai, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
                            .addComponent(btnNhanHang, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtDonViVC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(txtDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(369, 369, 369))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtTenShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtSdtShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13)
                                        .addComponent(txtDau1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(txtGiaShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton2)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnThem)
                                                .addComponent(btnSua)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnDangGiao)
                                                .addComponent(btnChoGiao))
                                            .addComponent(btnHenLai)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnHuy)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnNhanHang)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtSDTKh))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(246, 246, 246))))
        );

        jLabel1.setText("Thiết lập thông tin");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel15.setText("Tim kiếm :");

        tblPGH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Phiếu", "Mã HD", "Tên Khách Hang", "SDT KH", "Địa Chỉ", "Giá Ship", "Tên Ship", "SDT Ship", "Ngày Tạo", "Ngày Hoàn Thanh", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPGH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPGHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPGH);

        txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKeyReleased(evt);
            }
        });

        cboTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Tất cả --", "Chờ Giao ", "Đang giao", "Hẹn Lại ", "Hủy" }));
        cboTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTTActionPerformed(evt);
            }
        });

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

        lblPageTTKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPageTTKH.setText("1/1");

        btnTien.setText(">");
        btnTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTienActionPerformed(evt);
            }
        });

        btnCuoi.setText(">>");
        btnCuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(cboTT, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
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
                .addGap(450, 450, 450))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLui)
                    .addComponent(btnCuoi)
                    .addComponent(btnTien)
                    .addComponent(lblPageTTKH)
                    .addComponent(btnDau))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Danh dách phiếu", jPanel2);

        tblCTPGH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HD", "Tên KH", "Ngày Tạo", "Mã SPCT", "Tên SP", "Số Lượng", "Giá Bán", "Thành Tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblCTPGH);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Chi tiết phiếu", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        PhieuGiaoHang phieuGiaoHang = getForm();
        if (phieuGiaoHang == null) {
            return;
        }
        if (getHD() == null) {
            MsgBox.aleart(this, "Chưa có hóa đơn để giao");
            return;
        }
        if (getHD().isEmpty()) {
            MsgBox.aleart(this, "Chưa có hóa đơn để giao");
            return;
        }

        int coutn = 0;
        for (HoaDon hd : getHD()) {
            phieuGiaoHang.setIdHD(hd);
            phieuGiaoHang.setIdKH(hd.getIdKH());
            phieuGiaoHangRepository.insert(phieuGiaoHang);
            coutn++;
        }
        if (coutn != 0) {
            MsgBox.aleart(this, "Tạo thành công phiếu giao hàng");
            lisHD = phieuGiaoHangRepository.listDSHD();
            fillToTableHD(lisHD);
            page = 1;
            list = phieuGiaoHangRepository.getAll(1, lmit);
            filfToTablePGH(list);
        }
        clearForm();


    }//GEN-LAST:event_btnThemActionPerformed

    public boolean compareDates(String dateStr1, String dateStr2) {
        // Định dạng để chuyển đổi từ chuỗi sang LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Chuyển đổi chuỗi thành LocalDate
        LocalDate date1 = LocalDate.parse(dateStr1, formatter);
        LocalDate date2 = LocalDate.parse(dateStr2, formatter);

        // So sánh ngày
        return date2.isAfter(date1);
    }
    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        PhieuGiaoHang phieuGiaoHang = getForm();
        int kq = phieuGiaoHangRepository.update(phieuGiaoHang);
        if (kq != -1) {
            MsgBox.aleart(this, "Update thành công Mã Phiếu : " + phieuGiaoHang.getMaVanDon());
            list = phieuGiaoHangRepository.getAll(page, lmit);
            filfToTablePGH(list);
            clearForm();
        } else {
            MsgBox.aleart(this, "Update không thành công Mã Phiếu : " + phieuGiaoHang.getMaVanDon());
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtDonViVCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonViVCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonViVCActionPerformed

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        int index = tblHD.getSelectedRow();
        String tenKh = tblHD.getValueAt(index, 3).toString();
        txtTenKh.setText(tenKh);
    }//GEN-LAST:event_tblHDMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clearForm();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblPGHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPGHMouseClicked
        indexPGH = tblPGH.getSelectedRow();
        showData(indexPGH);
        String maHD = tblPGH.getValueAt(indexPGH, 2).toString();
        String maP = tblPGH.getValueAt(indexPGH, 1).toString();
        System.out.println("ma P " + maP + "     " + maHD);
        String ma = txtMaPhieu.getText();
        listCTPG = phieuGiaoHangRepository.listPGHCT(ma);
        fillToTableCTPH(listCTPG);
        setBtn();
    }//GEN-LAST:event_tblPGHMouseClicked

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

    private void txtTimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyReleased
        list = phieuGiaoHangRepository.timTxt(txtTim.getText().trim(), page, lmit);
        filfToTablePGH(list);
    }//GEN-LAST:event_txtTimKeyReleased

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // HD 4 hủy : GH 3 hủy
        indexPGH = tblPGH.getSelectedRow();
        String maVanDon = tblPGH.getValueAt(indexPGH, 1).toString();
        if (indexPGH != -1) {
            for (int i = 0; i < tblCTPGH.getRowCount(); i++) {
                String maHD = tblCTPGH.getValueAt(i, 1).toString();
                String maCTSP = tblCTPGH.getValueAt(i, 4).toString();
                int soLuongTra = Integer.parseInt(tblCTPGH.getValueAt(i, 6).toString());
                int soLuongTon = sanPhamCT_Repository.getSoLuongTonByMaCTSP(maCTSP);
                int tongSoLuong = soLuongTon + soLuongTra;
                System.out.println("maVD " + maVanDon + "  maHD " + maHD + " sp " + maCTSP + " " + tongSoLuong);
                // tra hang
                sanPhamCT_Repository.updateSLSP(tongSoLuong, maCTSP);
                // huy phieu giao
                phieuGiaoHangRepository.huyDon(maVanDon, 3);
                // 
                hoaDon_MRepositoryM.updateTTHD(maHD, 4);

            }
            MsgBox.aleart(this, "Phiếu giao hàng đã được hủy");
            page = 1;
            list = phieuGiaoHangRepository.getAll(1, lmit);
            filfToTablePGH(list);
            clearForm();
        }
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnNhanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanHangActionPerformed
        if (indexPGH == -1) {
            return;
        }
        String maHD = tblPGH.getValueAt(indexPGH, 2).toString();
        String maP = tblPGH.getValueAt(indexPGH, 1).toString();
        System.out.println("ma P " + maP + "     " + maHD);
        hoaDon_MRepositoryM.updateTTHD2(maHD, 1);
        int kqP = phieuGiaoHangRepository.updateTrangThaiPGH(maP, 4);
        if (kqP != -1) {
            MsgBox.aleart(this, "Update thành công phiếu giao : " + maP);
            list = pghr.getAll(page, lmit);
            filfToTablePGH(list);
        }
    }//GEN-LAST:event_btnNhanHangActionPerformed

    private void btnChoGiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoGiaoActionPerformed
        if (indexPGH == -1) {
            return;
        }
        String maHD = tblPGH.getValueAt(indexPGH, 2).toString();
        String maP = tblCTPGH.getValueAt(indexPGH, 1).toString();
        hoaDon_MRepositoryM.updateTTHD(maHD, 2);
        int kqP = phieuGiaoHangRepository.updateTrangThaiPGH(maP, 0);
        if (kqP != -1) {
            MsgBox.aleart(this, "Update thành công phiếu giao : " + maP);
            list = pghr.getAll(page, lmit);
            filfToTablePGH(list);
        }
    }//GEN-LAST:event_btnChoGiaoActionPerformed

    private void btnDangGiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangGiaoActionPerformed
        indexPGH = tblPGH.getSelectedRow();
        if (indexPGH == -1) {
            return;
        }
        String maHD = tblPGH.getValueAt(indexPGH, 2).toString();
        String maP = tblPGH.getValueAt(indexPGH, 1).toString();
        System.out.println("ma P " + maP + "     " + maHD);
        hoaDon_MRepositoryM.updateTTHD(maHD, 2);
        int kqP = phieuGiaoHangRepository.updateTrangThaiPGH(maP, 1);
        if (kqP != -1) {
            MsgBox.aleart(this, "Update thành công phiếu giao : " + maP);
            list = pghr.getAll(page, lmit);
            filfToTablePGH(list);
        }
    }//GEN-LAST:event_btnDangGiaoActionPerformed

    private void btnHenLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHenLaiActionPerformed
        if (indexPGH == -1) {
            return;
        }
        ViewHenlai viewHenlai = new ViewHenlai(new Application(), true);
        viewHenlai.setVisible(true);

        if (viewHenlai.getDate == null) {
            MsgBox.aleart(this, "Hãy nhập lại !!!");
            return;
        }

        if (!compareDates(XDate.toString(txtDau1.getDate(), "dd-MM-yyyy"), XDate.toString(viewHenlai.getDate, "dd-MM-yyyy"))) {
            MsgBox.aleart(this, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
            return;
        }
        String maHD = tblPGH.getValueAt(indexPGH, 2).toString();
        String maP = tblPGH.getValueAt(indexPGH, 1).toString();
        hoaDon_MRepositoryM.updateTTHD(maHD, 2);
        phieuGiaoHangRepository.updateNgayNhan(maP, viewHenlai.getDate);
        int kqP = phieuGiaoHangRepository.updateTrangThaiPGH(maP, 2);
        if (kqP != -1) {
            MsgBox.aleart(this, "Update thành công phiếu giao : " + maP);
            list = pghr.getAll(page, lmit);
            filfToTablePGH(list);
        }
    }//GEN-LAST:event_btnHenLaiActionPerformed

    private void cboTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTTActionPerformed
        int tt = cboTT.getSelectedIndex() - 1;
        list = phieuGiaoHangRepository.timCbo(tt, page, lmit);
        filfToTablePGH(list);
    }//GEN-LAST:event_cboTTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChoGiao;
    private javax.swing.JButton btnCuoi;
    private javax.swing.JButton btnDangGiao;
    private javax.swing.JButton btnDau;
    private javax.swing.JButton btnHenLai;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnNhanHang;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTien;
    private javax.swing.JComboBox<String> cboTT;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblPageTTKH;
    private javax.swing.JTable tblCTPGH;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblPGH;
    private com.toedter.calendar.JDateChooser txtDau;
    private com.toedter.calendar.JDateChooser txtDau1;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtDonViVC;
    private javax.swing.JTextField txtGiaShip;
    private javax.swing.JTextField txtMaPhieu;
    private javax.swing.JTextField txtSDTKh;
    private javax.swing.JTextField txtSdtShip;
    private javax.swing.JTextField txtTenKh;
    private javax.swing.JTextField txtTenShip;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
