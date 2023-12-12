/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.KhachHang;
import Model.NhanVien;
import Model.PhieuGiamGia;
import Repository.KhachHangRepositoryM;
import Repository.PhieuGiamGiaService;
import Utils.Auth;
import Utils.Email;
import Utils.MsgBox;
import Utils.Validate;
import Utils.XDate;
import Utils.XuLyString;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manhnt
 */
public class Form_PhieuGiamGia extends javax.swing.JPanel {

    private static KhachHangRepositoryM khachHangRepository = new KhachHangRepositoryM();

    //DefaultTableModel model1 = new DefaultTableModel();
    //DefaultTableModel model2= new DefaultTableModel();
    ArrayList<PhieuGiamGia> listPGG = new ArrayList<>();
    //ArrayList<HoaDon> listHD = new ArrayList<>();
    private static PhieuGiamGiaService servicePGG = new PhieuGiamGiaService();
    public List<Object> listHF = new ArrayList<>();

    private static int page = 1;
    private static int page2 = 1;
    private static int lmit = 4;
    private static int gioiHanPage = (int) ((Math.ceil(servicePGG.getRowCountPGG() / lmit))) + 1;

    /**
     * Creates new form Form_PhieuGiamGia
     */
    public Form_PhieuGiamGia() {
        initComponents();
        listPGG = servicePGG.getAllPGG(page, lmit);
        loadToTable(listPGG);
        if (Auth.isManager()) {
            btnSua.setVisible(true);
            btnThem.setVisible(true);
        } else {
            btnSua.setVisible(false);
            btnThem.setVisible(false);
        }
        
         txtNguoiTao.setText(Auth.nv.getMaNhanVien());
    }

    public void loadToTable(ArrayList<PhieuGiamGia> listPgg) {
        DefaultTableModel model = (DefaultTableModel) this.tblPhieuGG.getModel();
        //       System.out.println(listPGG.size());
        model.setRowCount(0);
        int i = 1;
        for (PhieuGiamGia pgg : listPgg) {
            model.addRow(pgg.rowDate(i));
            i++;
        }
    }

    public void loadToTableHD(ArrayList<Object> listPgg) {
        DefaultTableModel model = (DefaultTableModel) this.tblHD2.getModel();

        model.setRowCount(0);
        int i = 1;
        for (Object ob : listPgg) {
            model.addRow((Object[]) ob);
            i++;
        }
    }

    public void show2() {
        int row = tblPhieuGG.getSelectedRow();
        PhieuGiamGia pgg = this.listPGG.get(row);
        txtNguoiTao.setText(String.valueOf(pgg.getIdNV()));
        String giaTriStr = tblPhieuGG.getValueAt(row, 4).toString();
        giaTriStr = giaTriStr.replace("%", "");
        giaTriStr = giaTriStr.replace("VNĐ", "");
        giaTriStr = XuLyString.formatTienToNumBer(giaTriStr);

        String donToiThieu = tblPhieuGG.getValueAt(row, 6).toString();
        donToiThieu = XuLyString.formatTienToNumBer(donToiThieu);
        txtMaPhieu.setText(tblPhieuGG.getValueAt(row, 1).toString());
        txtTenPhieu.setText(tblPhieuGG.getValueAt(row, 2).toString());
        cboLoaiPhieu.setSelectedItem(tblPhieuGG.getValueAt(row, 3).toString());
        txtGiaTri.setText(giaTriStr);
        txtSoLuong.setText(tblPhieuGG.getValueAt(row, 5).toString());
        txtDonTuoiThieu.setText(donToiThieu);
        try {
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date dateBD = date.parse(tblPhieuGG.getValueAt(row, 7).toString());
            Date dateKT = date.parse(tblPhieuGG.getValueAt(row, 8).toString());
//            tdateNgayBatDau.setDate(dateBD);
//            tdateNgayKetThuc.setDate(dateKT); SUA
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtMoTa.setText(tblPhieuGG.getValueAt(row, 10).toString());
        if (tblPhieuGG.getValueAt(row, 11).toString().equalsIgnoreCase("Sắp diễn ra")) {
            cboTrangThai.setSelectedIndex(0);
        } else if (tblPhieuGG.getValueAt(row, 11).toString().equalsIgnoreCase("Đang diễn ra")) {
            cboTrangThai.setSelectedIndex(1);
        } else {
            cboTrangThai.setSelectedIndex(3);
        }
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

    private PhieuGiamGia getForm() {
        NhanVien nhanVien = Auth.nv;
        String maPhieu = txtMaPhieu.getText().trim();
        int max = servicePGG.getRowCountPGG();
        if (maPhieu.isEmpty()) {
            if (max < 10) {
                maPhieu = "PGG" + "00" + maPhieu;
            } else if (max < 100) {
                maPhieu = "PGG" + "0" + max;
            } else {
                maPhieu = "PGG" + max;
            }
        }

        String tenPhieu = txtTenPhieu.getText().trim();
        int loaiPhieu = cboLoaiPhieu.getSelectedIndex();
        String giaTri = txtGiaTri.getText().trim();
        String soLuong = txtSoLuong.getText().trim();
        String donToiThieu = txtDonTuoiThieu.getText().trim();
//        String ngayBD = XDate.toString(txtNgayBatDAu.getDate(), "dd-MM-yyyy");
//        String ngayKetThuc = XDate.toString(txtNgayKetThuc.getDate(), "dd-MM-yyyy");

        String moTa = txtMoTa.getText().trim();
        BigDecimal giaTriB = BigDecimal.ZERO;
        BigDecimal donDt = BigDecimal.ZERO;
        int soLuongI = 0;
        if (tenPhieu.isEmpty()) {
            MsgBox.aleart(this, "Tên phiếu chưa có !");
            txtTenPhieu.requestFocus();
            return null;
        } else {
            if (Validate.checkLength(tenPhieu, 99)) {
                MsgBox.aleart(this, "Tên phiếu tối da 99 ký tự !");
                txtTenPhieu.requestFocus();
                return null;
            }
        }
        if (giaTri.isEmpty()) {
            MsgBox.aleart(this, "Giá trị phiếu chưa có !");
            txtGiaTri.requestFocus();
            return null;
        } else {

            try {
                giaTriB = new BigDecimal(giaTri);
                if (cboLoaiPhieu.getSelectedIndex() == 0) {
                    if (giaTriB.compareTo(new BigDecimal("100.0")) >= 0) {
                        MsgBox.aleart(this, "Giá trị % nhở hơn 100 !");
                        txtGiaTri.requestFocus();
                        return null;
                    }

                }
                if (Float.parseFloat(giaTri) <= 0) {
                    MsgBox.aleart(this, "Giá trị > 0");
                    txtGiaTri.requestFocus();
                    return null;
                }
            } catch (Exception e) {
                MsgBox.aleart(this, "Giá trị phiếu phải là số !");
                txtGiaTri.requestFocus();
                return null;
            }
        }
        if (soLuong.isEmpty()) {
            MsgBox.aleart(this, "Số lượng phiếu chưa có !");
            txtSoLuong.requestFocus();
            return null;
        } else {
            try {
                soLuongI = Integer.parseInt(soLuong);
                if (soLuongI <= 0) {
                    MsgBox.aleart(this, "Số lượng phải >0");
                    return null;
                }

                if (soLuongI >= 1000000) {
                    MsgBox.aleart(this, "Số lượng phải < 1000000");
                    return null;
                }
            } catch (Exception e) {
                MsgBox.aleart(this, "Số lượng phiếu phải là số !");
                txtSoLuong.requestFocus();
                return null;
            }
        }
        if (donToiThieu.isEmpty()) {
            MsgBox.aleart(this, "Đơn tối thiểu phiếu chưa có !");
            txtDonTuoiThieu.requestFocus();
            return null;
        } else {
            try {
                float dtt = Float.parseFloat(donToiThieu);
                donDt = new BigDecimal(donToiThieu);
                if (dtt < 0) {
                    MsgBox.aleart(this, "Đơn tối thiểu phiếu phải > 0!");
                    txtDonTuoiThieu.requestFocus();
                    return null;
                }
            } catch (Exception e) {
                MsgBox.aleart(this, "Đơn tối thiểu phiếu phải là số !");
                txtDonTuoiThieu.requestFocus();
                return null;
            }
        }
        // date 
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
                MsgBox.aleart(this, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
                return null;
            }
        }

        if (Utils.Validate.isEmpty(XDate.toString(date1, "dd-MM-yyyy"))) {
            MsgBox.aleart(this, "Ngày bắt đầu không được để trống");
            txtDau.requestFocus();
            return null;
        } else {
            if (!Utils.Validate.isDate(XDate.toString(date1, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày bắt đầu sai định dạng dd-MM-yyyy");
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
            MsgBox.aleart(this, "Ngày kết thúc không được để trống");
            txtDau1.requestFocus();
            return null;
        } else {
            if (!Utils.Validate.isDate(XDate.toString(date2, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày kết thúc sai định dạng dd-MM-yyyy");
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
        System.out.println("raven.application.form.other.Form_PhieuGiamGia.getForm()" + " don toi thiu " + donDt);
        java.util.Date utilDate1 = txtDau.getDate(); // Giả sử dateNgaybd.getDate() trả về java.util.Date
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
        java.util.Date utilDate2 = txtDau1.getDate(); // Giả sử dateNgaybd.getDate() trả về java.util.Date
        java.sql.Date sqlDate2 = new java.sql.Date(utilDate2.getTime());
        PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
        phieuGiamGia.setIdNV(nhanVien);
        phieuGiamGia.setMaPhieu(maPhieu);
        phieuGiamGia.setTenPhieu(tenPhieu);
        phieuGiamGia.setLoaiPhieu(loaiPhieu);
        phieuGiamGia.setGiaTri(giaTriB);
        phieuGiamGia.setSoLuongPhieu(soLuongI);
        phieuGiamGia.setDonToiThieu(donDt);
        phieuGiamGia.setNgayBatDau(sqlDate1);
        phieuGiamGia.setNgayKetThuc(sqlDate2);
        phieuGiamGia.setMoTa(moTa);
        return phieuGiamGia;

    }

    private void showData(int index) {

        PhieuGiamGia pgg = servicePGG.getAllPGG(page, lmit).get(index);
        txtDonTuoiThieu.setText(pgg.getDonToiThieu() + "");
        txtGiaTri.setText(pgg.getGiaTri() + "");
        txtMaPhieu.setText(pgg.getMaPhieu() + "");
        txtMoTa.setText(pgg.getMoTa() + "");
        txtDau.setDate(pgg.getNgayBatDau());
        txtDau1.setDate(pgg.getNgayKetThuc());
        txtNguoiTao.setText(pgg.getIdNV().getTenNhanVien() + "");
        txtSoLuong.setText(pgg.getSoLuongPhieu() + "");
        txtTenPhieu.setText(pgg.getTenPhieu() + "");
        cboLoaiPhieu.setSelectedIndex(pgg.getLoaiPhieu());
    }

    private void first() {

        lblPageTTKH.setText(1 + " / " + gioiHanPage);
        listPGG = servicePGG.getAllPGG(1, lmit);
        loadToTable(listPGG);
    }

    private void prev() {
        page--;

        if (page >= 1) {
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            listPGG = servicePGG.getAllPGG(page, lmit);
            loadToTable(listPGG);
            return;
        }
        page = 1;

    }

    private void next() {
        page++;

        if (page <= gioiHanPage) {
            listPGG = servicePGG.getAllPGG(page, lmit);
            loadToTable(listPGG);
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            return;
        }
        page = gioiHanPage;
    }

    private void last() {

        lblPageTTKH.setText(gioiHanPage + " / " + gioiHanPage);
        listPGG = servicePGG.getAllPGG(gioiHanPage, lmit);
        loadToTable(listPGG);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboLoaiPhieu = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtGiaTri = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDonTuoiThieu = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        txtTenPhieu = new javax.swing.JTextField();
        txtMaPhieu = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtDau1 = new com.toedter.calendar.JDateChooser();
        txtDau = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboLoaiPhieu1 = new javax.swing.JComboBox<>();
        cboTrangThai1 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        btnLoc = new javax.swing.JButton();
        txtNgayKTLoc = new com.toedter.calendar.JDateChooser();
        txtNgayBDLoc = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhieuGG = new javax.swing.JTable();
        btnDau = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        lblPageTTKH = new javax.swing.JLabel();
        btnTien = new javax.swing.JButton();
        btnCuoi = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblHD2 = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thiết lập phiếu giảm giá");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Người tạo :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Mã Phiếu :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Tên Phiếu :");

        txtNguoiTao.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Loại Phiếu");

        cboLoaiPhieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboLoaiPhieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "                              Giảm theo %", "                          Giảm theo VND" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Giá trị :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Số lượng phiếu :");

        txtSoLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtSoLuongMouseEntered(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Đơn tối thiểu :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Trạng thái :");

        cboTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "                               Sắp đến", "                         Đang diễn ra", "                            Đã hết hạn" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Ngày bắt đầu :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Ngày kết thúc :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Mô tả :");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        txtMaPhieu.setEditable(false);

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

        jButton3.setText("Làm mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtDau1.setDateFormatString("dd-MM-yyyy");
        txtDau1.setMaxSelectableDate(new java.util.Date(253370743316000L));

        txtDau.setDateFormatString("dd-MM-yyyy");
        txtDau.setMaxSelectableDate(new java.util.Date(253370743316000L));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboLoaiPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtDonTuoiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtSoLuong))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(392, 392, 392)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDau1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDau, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDau1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12)
                            .addComponent(txtTenPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonTuoiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cboLoaiPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSua)
                        .addComponent(jButton3)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Ngày bắt đầu :");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Ngày kết thúc :");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Loại Phiếu");

        cboLoaiPhieu1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboLoaiPhieu1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "                     --Tất cả--", "                     Giảm theo %", "                 Giảm theo VND" }));

        cboTrangThai1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboTrangThai1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "                            Tất cả", "                           Sắp đến", "                       Đang diễn ra", "                         Đã hết hạn", " " }));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Trạng thái :");

        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        txtNgayKTLoc.setDateFormatString("dd-MM-yyyy");
        txtNgayKTLoc.setMaxSelectableDate(new java.util.Date(253370743316000L));

        txtNgayBDLoc.setDateFormatString("dd-MM-yyyy");
        txtNgayBDLoc.setMaxSelectableDate(new java.util.Date(253370743316000L));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayBDLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNgayKTLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboLoaiPhieu1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboTrangThai1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(btnLoc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(txtNgayBDLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(cboTrangThai1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboLoaiPhieu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(btnLoc))
                    .addComponent(txtNgayKTLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Tìm kiếm phiếu");

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblPhieuGG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Người Tạo", "Mã Phiếu", "Tên Phiếu", "Loại Phiếu", "Giá Trị", "Số Lượng Phát Hành", "Đơn tối thiểu", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Ngày Tạo", "Mô Tả", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuGG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuGGMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPhieuGG);
        if (tblPhieuGG.getColumnModel().getColumnCount() > 0) {
            tblPhieuGG.getColumnModel().getColumn(7).setResizable(false);
            tblPhieuGG.getColumnModel().getColumn(8).setResizable(false);
        }

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
        lblPageTTKH.setText("1/ 2");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDau, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPageTTKH)
                .addGap(31, 31, 31)
                .addComponent(btnTien, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(419, 419, 419))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDau)
                    .addComponent(btnLui)
                    .addComponent(btnCuoi)
                    .addComponent(btnTien)
                    .addComponent(lblPageTTKH))
                .addGap(11, 11, 11))
        );

        jTabbedPane2.addTab("Danh sách phiếu ", jPanel5);

        tblHD2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Mã Phiếu", "Ngày Áp Dụng", "Giá Trị Giảm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblHD2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Danh sách hóa đơn ap dụng phiếu", jPanel10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14)
                    .addComponent(jLabel1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu giảm giá", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuoiActionPerformed
        last();
    }//GEN-LAST:event_btnCuoiActionPerformed

    private void btnTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTienActionPerformed
        next();
    }//GEN-LAST:event_btnTienActionPerformed

    private void btnLuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuiActionPerformed
        prev();
    }//GEN-LAST:event_btnLuiActionPerformed

    private void btnDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDauActionPerformed
        first();
    }//GEN-LAST:event_btnDauActionPerformed

    private void tblPhieuGGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuGGMouseClicked
        int index = tblPhieuGG.getSelectedRow();
        showData(index);
        String maPhieu = tblPhieuGG.getValueAt(index, 2).toString();

        //Long id = servicePGG.getPGGByMa(maPhieu).getIdPGG();
        listHF = servicePGG.getAllHDByMaPhieu(maPhieu);
        loadToTableHD((ArrayList<Object>) listHF);
    }//GEN-LAST:event_tblPhieuGGMouseClicked

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        Date date1 = null;
        Date date2 = null;
        try {
            try {
                date1 = txtDau.getDate();
                date2 = txtDau1.getDate();
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày tháng năm ko hợp lệ");
                return;
            }
            if (date1 != null || date2 != null) {
                if (date1 == null || date2 == null) {
                    MsgBox.aleart(this, "Bạn hãy điền đúng 2 ngày");
                    return;
                }

            }
            if (date1 != null && date2 != null) {

                System.out.println("Vaof ");
                if (!Utils.Validate.isDate(XDate.toString(date1, "dd-MM-yyyy"))) {
                    MsgBox.aleart(this, "Ngày sinh sai định dạng dd-MM-yyyy");
                    txtDau.requestFocus();
                    return;
                }
                if (!Utils.Validate.isDate(XDate.toString(date2, "dd-MM-yyyy"))) {
                    MsgBox.aleart(this, "Ngày sinh sai định dạng dd-MM-yyyy");
                    txtDau1.requestFocus();
                    return;
                }
                try {
                    XDate.toDate(XDate.toString(date1, "dd-MM-yyyy"), "dd-MM-yyyy");
                    XDate.toDate(XDate.toString(date2, "dd-MM-yyyy"), "dd-MM-yyyy");
                } catch (Exception e) {
                    MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");

                    return;
                }

                if (!compareDates(XDate.toString(date1, "dd-MM-yyyy"), XDate.toString(date2, "dd-MM-yyyy"))) {
                    MsgBox.aleart(this, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
                    return;
                }
            }
        } catch (Exception e) {
            MsgBox.aleart(this, "Kiểm tra lại ngày của bạn nhập vào");
            return;
        }
        int loaiPhieu = cboLoaiPhieu1.getSelectedIndex() - 1;
        int trangThai = cboTrangThai1.getSelectedIndex() - 1;
        System.out.println("raven.a" + trangThai);
        PhieuGiamGia gia = new PhieuGiamGia();
        gia.setNgayBatDau(txtNgayBDLoc.getDate());
        gia.setNgayKetThuc(txtNgayKTLoc.getDate());
        gia.setLoaiPhieu(loaiPhieu);
        gia.setTrangThai(trangThai);
        listPGG = (ArrayList<PhieuGiamGia>) servicePGG.getListLoc(gia, page, lmit);
        loadToTable(listPGG);
    }//GEN-LAST:event_btnLocActionPerformed

    private void clear() {
        txtMaPhieu.setText("");
        txtNguoiTao.setText("");
        txtTenPhieu.setText("");
        txtGiaTri.setText("");
        txtSoLuong.setText("");
        txtDonTuoiThieu.setText("");
        txtDau.setDate(null);
        txtDau1.setDate(null);
        txtMoTa.setText("");
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clear();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        PhieuGiamGia phieuGiamGia = getForm();
        System.out.println(phieuGiamGia);
        if (phieuGiamGia == null) {
            return;
        }
        int kq = servicePGG.SuaPGG(phieuGiamGia);
        if (kq == 1) {
            MsgBox.aleart(this, "Sửa thành có");
            String date = XDate.toString(new Date(), "yyyy-MM-dd");
            servicePGG.updateKT(date);
            servicePGG.updateBD(date);
            listPGG = servicePGG.getAllPGG(page, lmit);
            loadToTable(listPGG);
            clear();
        } else {
            MsgBox.aleart(this, "Sửa không thành công");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        PhieuGiamGia phieuGiamGia = getForm();
        if (phieuGiamGia == null) {
            return;
        }
// tao
        Impl.ExportQr.exportQrHD("src\\qrpgg", phieuGiamGia.getMaPhieu());

        int kq = servicePGG.ThemPGG(phieuGiamGia);
        String date = XDate.toString(new Date(), "yyyy-MM-dd");
        servicePGG.updateKT(date);
        servicePGG.updateBD(date);
        if (kq != -1) {
            MsgBox.aleart(this, "Thêm thành có");
            listPGG = servicePGG.getAllPGG(page, lmit);
            loadToTable(listPGG);
            // email
            for (KhachHang kh : khachHangRepository.getAll2()) {
                try {
                    if (kh.getMaKhachHang() == null) {
                        continue;
                    }
                    String thongTin = "Shop SuperSport Sneakers  \n Gửi tặng khách hàng " + kh.getTenKhachHang() + " phiếu giảm giá \n"
                            + "Mã phiếu : " + phieuGiamGia.getMaPhieu() + " .\n"
                            + "Giá trị phiếu : " + phieuGiamGia.getTenPhieu() + " .\n"
                            + (phieuGiamGia.getLoaiPhieu() == 0 ? phieuGiamGia.getGiaTri() + " % " : phieuGiamGia.getGiaTri() + " VNĐ ") + " \n "
                            + "Ngày bắt đầu : " + XDate.toString(phieuGiamGia.getNgayBatDau(), "dd-MM-yyyy") + " \n "
                            + "Ngày kết thúc : " + XDate.toString(phieuGiamGia.getNgayKetThuc(), "dd-MM-yyyy");
                    Email.sendMailwithFile(kh.getEmail(), thongTin, "Phiếu giảm giá danh tặng cho bạn", "src\\qrpgg\\" + phieuGiamGia.getMaPhieu() + ".png");
                } catch (Exception e) {
                    continue;
                }
            }

            clear();
        } else {
            MsgBox.aleart(this, "Thêm không thành công");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtSoLuongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSoLuongMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCuoi;
    private javax.swing.JButton btnDau;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTien;
    private javax.swing.JComboBox<String> cboLoaiPhieu;
    private javax.swing.JComboBox<String> cboLoaiPhieu1;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JComboBox<String> cboTrangThai1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblPageTTKH;
    private javax.swing.JTable tblHD2;
    private javax.swing.JTable tblPhieuGG;
    private com.toedter.calendar.JDateChooser txtDau;
    private com.toedter.calendar.JDateChooser txtDau1;
    private javax.swing.JTextField txtDonTuoiThieu;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtMaPhieu;
    private javax.swing.JTextArea txtMoTa;
    private com.toedter.calendar.JDateChooser txtNgayBDLoc;
    private com.toedter.calendar.JDateChooser txtNgayKTLoc;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenPhieu;
    // End of variables declaration//GEN-END:variables
}
