/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.ChiTietDotGiamGia;
import Model.DotGiamGia_M;
import Model.NhanVien;
import Model.PhieuGiamGia;
import Model.PhieuGiaoHang;
import Model.SanPhamChiTiet;
import Model.ThuongHieu;
import Repository.ChiTietDotGiamRepository;
import Repository.DotGiamGia_MRpository;
import Repository.PhieuGiamGiaService;
import Repository.PhieuGiaoHangRepository;
import Repository.ThuongHieu_Repository;
import Utils.Auth;
import Utils.MsgBox;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.application.Application;

/**
 *
 * @author manhnt
 */
public class Form_DotGiamGia extends javax.swing.JPanel {

    private DefaultComboBoxModel model3 = new DefaultComboBoxModel();
    DefaultTableModel dtmSP = new DefaultTableModel();
    private ThuongHieu_Repository thuongHieu_Repository = new ThuongHieu_Repository();
    private static DotGiamGia_MRpository dotGiamGia_MRpository = new DotGiamGia_MRpository();
    private static ChiTietDotGiamRepository chiTietDotGiamRepository = new ChiTietDotGiamRepository();
    private static List<SanPhamChiTiet> lisrSP = new ArrayList<>();
    private List<SanPhamChiTiet> listSelected = new ArrayList<>();
    private static List<DotGiamGia_M> listDGG = new ArrayList<>();
    private List<Object> listSP_DGG = new ArrayList<>();
    private static int page = 1;
    private static int lmit = 6;
    private static int gioiHanPage = (int) ((Math.ceil(dotGiamGia_MRpository.getRowCount() / lmit))) + 1;
    private static int index = -1;

    private static NhanVien nhanVien = Auth.nv;

    public Form_DotGiamGia() {
        initComponents();
        init();

    }

    void init() {
        fillToCboThuongHieu();
        listDGG = dotGiamGia_MRpository.getAllDGG(page, lmit);
        fillToTableDGG(listDGG);
        lblPageTTKH.setText(page + " / " + gioiHanPage);
        setBtn();
        if (Auth.isManager()) {
            btnSua.setVisible(true);
            btnThem.setVisible(true);
        } else {
            btnSua.setVisible(false);
            btnThem.setVisible(false);
        }
        
        txtNguoiTao.setText(Auth.nv.getMaNhanVien());
    }

    private void setBtn() {
        boolean ktr = (this.index >= 0);
        btnThem.setEnabled(!ktr);
        btnSua.setEnabled(ktr);
    }

    private void fillToTableSP(List<SanPhamChiTiet> list) {
        dtmSP = (DefaultTableModel) this.tblSP.getModel();
        dtmSP.setRowCount(0);
        for (SanPhamChiTiet spct : list) {
            dtmSP.addRow(spct.rowDataViewDGG());
        }
    }

    private void fillToTableCT_DGG(List<Object> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblSPDGG.getModel();
        dtm.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            dtm.addRow((Object[]) list.get(i));
        }
    }

    private void fillToTableDGG(List<DotGiamGia_M> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblPhieuGG.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (DotGiamGia_M dggm : list) {
            dtm.addRow(dggm.rowData(i));
            i++;
        }
    }

    public void fillToCboThuongHieu() {
        model3 = (DefaultComboBoxModel) this.cboThuongHieu.getModel();
        model3.removeAllElements();;
        model3.addElement("---Tất cả---");
        List<ThuongHieu> list = thuongHieu_Repository.getToAll();
        for (ThuongHieu th : list) {

            model3.addElement(th);
        }
    }

    private void first() {
        lblPageTTKH.setText(1 + " / " + gioiHanPage);
        listDGG = dotGiamGia_MRpository.getAllDGG(1, lmit);
        System.out.println(listDGG.size());
        fillToTableDGG(listDGG);
        setBtn();
    }

    private void prev() {
        page--;

        if (page >= 1) {
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            listDGG = dotGiamGia_MRpository.getAllDGG(page, lmit);
            fillToTableDGG(listDGG);
            return;
        }
        page = 1;
        setBtn();

    }

    private void next() {
        page++;

        if (page <= gioiHanPage) {
            listDGG = dotGiamGia_MRpository.getAllDGG(page, lmit);
            fillToTableDGG(listDGG);
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            return;
        }
        page = gioiHanPage;
        setBtn();

    }

    private void last() {
        lblPageTTKH.setText(gioiHanPage + " / " + gioiHanPage);
        listDGG = dotGiamGia_MRpository.getAllDGG(gioiHanPage, lmit);
        fillToTableDGG(listDGG);
        setBtn();

    }

    private DotGiamGia_M getForm() {
        NhanVien nhanVien = new NhanVien(7L);
        System.out.println(nhanVien + " tétt");

        String maDGG = txtMaPhieu.getText().trim();
        if (maDGG.isEmpty()) {
            int maxDGG = dotGiamGia_MRpository.getRowCount() + 1;
            if (maxDGG < 10) {
                maDGG = "DGG00" + maxDGG;
            } else if (maxDGG < 100) {
                maDGG = "DGG0" + maxDGG;
            } else {
                maDGG = "DGG" + maxDGG;
            }
        } else {
            maDGG = txtMaPhieu.getText().trim();
        }
        String tenDGG = txtTenPhieu.getText().trim();
        int loaiDGG = cboLoaiPhieu.getSelectedIndex();
        String giaTriStr = txtGiaTri.getText().trim();
        BigDecimal giaTriB = BigDecimal.ZERO;
        String moTa = txtMoTa.getText().trim();
        try {
            giaTriB = new BigDecimal(giaTriStr);
            if (loaiDGG == 0) {
                if (giaTriB.compareTo(BigDecimal.valueOf(100)) > 0) {
                    MsgBox.aleart(this, "Giá trị cho ĐGG theo % phải nhỏ hơn 100");
                    return null;
                }
            }

        } catch (Exception e) {
            MsgBox.aleart(this, "Giá trị nhập vào phải là số");
            return null;
        }

        if (Float.parseFloat(giaTriB + "") < 0) {
            MsgBox.aleart(this, "Giá trị nhập vào phải > 0");
            return null;
        }
        Date date1 = null;
        Date date2 = null;
        try {

            date1 = txtDau.getDate();
            if (date1 == null) {
                MsgBox.aleart(this, "Ngày bắt đầu chưa có");
                return null;
            }
        } catch (Exception e) {
            MsgBox.aleart(this, "Ngày bắt đầu sai định dạng");
            return null;
        }
        try {

            date2 = txtDau1.getDate();
            if (date2 == null) {
                MsgBox.aleart(this, "Ngày kết thúc chưa có");
                return null;
            }
        } catch (Exception e) {
            MsgBox.aleart(this, "Ngày kết thúc sai định dạng");
            return null;
        }

        DotGiamGia_M dgg = new DotGiamGia_M();
        dgg.setIdNV(nhanVien);
        dgg.setMaDGG(maDGG);
        dgg.setHinhThucDGG(loaiDGG);
        dgg.setGiaTri(giaTriB);
        dgg.setTenDGG(tenDGG);
        dgg.setMoTa(moTa);
        dgg.setNgayBatDau(date1);
        dgg.setNgayKetThuc(date2);
        dgg.setTrangThai(0);
        return dgg;
    }

    public List<SanPhamChiTiet> getTable() {
        List<SanPhamChiTiet> listSP = new ArrayList<>();

        for (int i = 0; i < tblSP.getRowCount(); i++) {
            Object ktr = tblSP.getValueAt(i, 6);
            if (ktr instanceof Boolean && (Boolean) ktr) {
                String maCTSP = tblSP.getValueAt(i, 0).toString();
                SanPhamChiTiet sanPhamChiTiet = chiTietDotGiamRepository.getSPCTByMa(maCTSP);
                listSP.add(sanPhamChiTiet);

            }
        }
        return listSP;
    }

    private BigDecimal tinhGiaGiam(DotGiamGia_M dgg, BigDecimal giaNiemYet) {
        BigDecimal giaTriGiam = BigDecimal.ZERO;

        Integer checkLoai = dgg.getHinhThucDGG();
        BigDecimal giaTriGiamDGG = dgg.getGiaTri(); // Lấy giá trị giảm từ dgg

        if (checkLoai == 0) { // Tính theo %
            // Chuyển đổi phần trăm thành dạng thập phân và làm tròn
            BigDecimal phanTram = giaTriGiamDGG.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            System.out.println("phan tram " + phanTram);
            // Tính giá trị giảm dựa trên phần trăm và giá niêm yết
            giaTriGiam = giaNiemYet.multiply(phanTram);
            System.out.println("giaTriGiam" + giaTriGiam);
            // Trừ giá trị giảm từ giá niêm yết
            giaTriGiam = giaNiemYet.subtract(giaTriGiam);
            System.out.println("Loại 0 ... %: " + giaTriGiam + " " + giaTriGiamDGG);

            return giaTriGiam;
        } else if (checkLoai == 1) { // Giảm theo giá trị cố định (VND)
            giaTriGiam = giaNiemYet.subtract(giaTriGiamDGG);
            if (giaTriGiam.compareTo(BigDecimal.ZERO) < 0) {
                giaTriGiam = BigDecimal.ZERO;

            }
            System.out.println("Loại 1 ... VND: " + giaTriGiam);
            return giaTriGiam;
        } else {
            return BigDecimal.ZERO;
        }
    }

    private void update() {
        DotGiamGia_M dgg = getForm();
        if (dgg == null) {
            return;
        }
        System.out.println("raven.application.form.other.Form_DotGiamGia.update()" + dgg.getMaDGG());
        Long id = dotGiamGia_MRpository.getDGGByMaDGG(dgg.getMaDGG()).getIdDGG();
        dgg.setIdDGG(id);
        int kq = dotGiamGia_MRpository.update(dgg);

        String date = XDate.toString(new Date(), "yyyy-MM-dd");
        dotGiamGia_MRpository.updateKT(date);
        List<ChiTietDotGiamGia> listCTDGG = chiTietDotGiamRepository.getAllCT_CTDGG_KT();
        for (ChiTietDotGiamGia ctdgg : listCTDGG) {
            chiTietDotGiamRepository.updateTrangIDGG_SP(ctdgg.getIdCTSP());
        }
        // bat dau
        dotGiamGia_MRpository.updateBD(date);
        List<ChiTietDotGiamGia> listCTDGG_BD = chiTietDotGiamRepository.getAllCT_CTDGG();
        for (ChiTietDotGiamGia ctdggBD : listCTDGG_BD) {
            chiTietDotGiamRepository.update_SP(ctdggBD);
        }
        if (kq != -1) {

            List<ChiTietDotGiamGia> list = chiTietDotGiamRepository.getAllByIDDgg(dgg.getIdDGG());
            for (ChiTietDotGiamGia ctdgg : list) {
                System.out.println(tinhGiaGiam(dgg, ctdgg.getDonGia()) + "");
                ctdgg.setDonGiaConLai(tinhGiaGiam(dgg, ctdgg.getDonGia()
                ));
                chiTietDotGiamRepository.update(dgg, ctdgg);
            }
            listDGG = dotGiamGia_MRpository.getAllDGG(page, lmit);
            fillToTableDGG(listDGG);
            MsgBox.aleart(this, "Update thành công");
        } else {
            MsgBox.aleart(this, "Update thất bại");
        }
    }

    private void insertDGG() {
        DotGiamGia_M dgg = getForm();
        if (dgg == null) {
            return;
        }
        int kq = dotGiamGia_MRpository.insertDGG(dgg);
        // Ket thuc
        String date = XDate.toString(new Date(), "yyyy-MM-dd");
        dotGiamGia_MRpository.updateKT(date);
        List<ChiTietDotGiamGia> listCTDGG = chiTietDotGiamRepository.getAllCT_CTDGG_KT();
        for (ChiTietDotGiamGia ctdgg : listCTDGG) {
            chiTietDotGiamRepository.updateTrangIDGG_SP(ctdgg.getIdCTSP());
        }
        // bat dau
        dotGiamGia_MRpository.updateBD(date);
        List<ChiTietDotGiamGia> listCTDGG_BD = chiTietDotGiamRepository.getAllCT_CTDGG();
        for (ChiTietDotGiamGia ctdggBD : listCTDGG_BD) {
            chiTietDotGiamRepository.update_SP(ctdggBD);
        }
        if (kq != -1) {
            MsgBox.aleart(this, "Tạo thành công 1 phiếu giảm giá");
            listDGG = dotGiamGia_MRpository.getAllDGG(page, lmit);
            fillToTableDGG(listDGG);
            String maDGG = dgg.getMaDGG();
            DotGiamGia_M getDGG = dotGiamGia_MRpository.getDGGByMaDGG(maDGG);
            System.out.println("getTable : " + getTable());
            for (SanPhamChiTiet sp : getTable()) {
                System.out.println("DGG chạy");
                BigDecimal giaGiam = tinhGiaGiam(getDGG, sp.getGiaNiemYet());
                ChiTietDotGiamGia ctdgg = new ChiTietDotGiamGia();
                ctdgg.setDonGiaConLai(giaGiam);
                chiTietDotGiamRepository.insert(ctdgg, getDGG, sp);
            }
            lisrSP.clear();
            fillToTableSP(lisrSP);
        } else {
            MsgBox.aleart(this, "Tạo không thành công 1 phiếu giảm giá");
            return;
        }
    }

    private void showData(int index) {
        DotGiamGia_M dgg = listDGG.get(index);
        txtGiaTri.setText(dgg.getGiaTri() + "");
        txtMaPhieu.setText(dgg.getMaDGG());
        txtMoTa.setText(dgg.getMoTa());
        txtDau.setDate(dgg.getNgayBatDau());
        txtDau1.setDate(dgg.getNgayKetThuc());
        txtNguoiTao.setText(dgg.getIdNV().getTenNhanVien());
        txtTenPhieu.setText(dgg.getTenDGG());
        cboLoaiPhieu.setSelectedIndex(dgg.getHinhThucDGG());
        setBtn();
    }

    private void clearForm() {
        txtGiaTri.setText("");
        txtMaPhieu.setText("");
        txtMoTa.setText("");
        txtDau.setDate(null);
        txtDau1.setDate(null);
        txtNguoiTao.setText("");
        txtTenPhieu.setText("");
        cboLoaiPhieu.setSelectedIndex(0);
        lisrSP.clear();
        fillToTableSP(lisrSP);
        page = 1;
        index = -1;
        listDGG = dotGiamGia_MRpository.getAllDGG(page, lmit);
        fillToTableDGG(listDGG);
        setBtn();

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
        txtTenPhieu = new javax.swing.JTextField();
        txtMaPhieu = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnLoc = new javax.swing.JButton();
        txtDau = new com.toedter.calendar.JDateChooser();
        txtDau1 = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        chkAll = new javax.swing.JCheckBox();
        jButton7 = new javax.swing.JButton();
        cboThuongHieu = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhieuGG = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnDau = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        lblPageTTKH = new javax.swing.JLabel();
        btnTien = new javax.swing.JButton();
        btnCuoi = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSPDGG = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thiết lập đợt giảm giá");

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
        cboLoaiPhieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "                Giảm theo %", "             Giảm theo VND" }));
        cboLoaiPhieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiPhieuActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Giá trị :");

        txtGiaTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriActionPerformed(evt);
            }
        });

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

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Ngày bắt đầu :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Ngày kết thúc :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Mô tả :");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        txtDau.setDateFormatString("dd-MM-yyyy");
        txtDau.setMaxSelectableDate(new java.util.Date(253370743316000L));

        txtDau1.setDateFormatString("dd-MM-yyyy");
        txtDau1.setMaxSelectableDate(new java.util.Date(253370743316000L));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDau1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(txtDau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(btnLoc)
                .addGap(18, 18, 18))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(7, 7, 7)
                        .addComponent(btnLoc)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel11))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDau1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên sản phẩm", "Giá Bán", "Hãng", "Mùa sắc", "Size", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblSP);

        chkAll.setText("Chọn tất cả");
        chkAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAllActionPerformed(evt);
            }
        });

        jButton7.setText("Bỏ chọn tấ cả");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        cboThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Tất Cả--", " " }));
        cboThuongHieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThuongHieuItemStateChanged(evt);
            }
        });
        cboThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboThuongHieuMouseClicked(evt);
            }
        });
        cboThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThuongHieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(chkAll)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkAll)
                    .addComponent(jButton7)
                    .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel8.setText("Sản phẩm muosos ap dụng đợt giảm giá");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(txtNguoiTao))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaPhieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboLoaiPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(cboLoaiPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTenPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton3)
                                .addComponent(btnSua)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton4.setText("<<");

        jButton5.setText(">>");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("0 / 0");

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblPhieuGG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Người Tạo", "Mã Phiếu", "Tên Phiếu", "Loại Phiếu", "Giá Trị", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Ngày Tạo", "Mô Tả", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
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
            tblPhieuGG.getColumnModel().getColumn(8).setHeaderValue("");
            tblPhieuGG.getColumnModel().getColumn(9).setHeaderValue("Mô Tả");
            tblPhieuGG.getColumnModel().getColumn(10).setHeaderValue("Trạng Thái");
        }

        jLabel7.setText("Tìm kiếm :");

        txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKeyReleased(evt);
            }
        });

        cboTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "                       Sắp đến", "                    Đang diễn ra", "                       Đã hết hạn" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Trạng thái :");

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
        lblPageTTKH.setText("0/0");

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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(428, 428, 428)
                        .addComponent(btnDau, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPageTTKH)
                        .addGap(31, 31, 31)
                        .addComponent(btnTien, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btnCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLui)
                    .addComponent(btnCuoi)
                    .addComponent(btnTien)
                    .addComponent(lblPageTTKH)
                    .addComponent(btnDau))
                .addGap(24, 24, 24))
        );

        jTabbedPane2.addTab("Danh sách khuyến mãi", jPanel10);

        tblSPDGG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Ma CTSP", "Tên SP", "Thương Hiệu", "Màu", "Size", "Đơn giá", "Giá trị giảm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblSPDGG);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(25, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addGap(58, 58, 58)))
        );

        jTabbedPane2.addTab("Danh sách sản phẩm có trong đơt giảm giá", jPanel11);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(524, 524, 524)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)
                        .addGap(27, 27, 27)
                        .addComponent(jButton5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1362, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jLabel19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Đợt giảm giá", jPanel1);

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

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        int tt = cboTrangThai.getSelectedIndex();
        page = 1;
        listDGG = dotGiamGia_MRpository.listTimCboTT(tt, page, lmit);
        lblPageTTKH.setText(page + " / " + gioiHanPage);
        fillToTableDGG(listDGG);
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void txtTimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyReleased
        String tt = txtTim.getText().trim();
        page = 1;
        System.out.println(tt);
        listDGG = dotGiamGia_MRpository.listTimTT(tt, page, lmit);
        fillToTableDGG(listDGG);
    }//GEN-LAST:event_txtTimKeyReleased

    private void tblPhieuGGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuGGMouseClicked
        index = tblPhieuGG.getSelectedRow();

        showData(index);// TODO add your handling code here:
        String maDGG = tblPhieuGG.getValueAt(index, 2).toString().trim();
        System.out.println("  " + listSP_DGG.size() + " " + maDGG);
        listSP_DGG = chiTietDotGiamRepository.listSPCTDGG(maDGG);

        fillToTableCT_DGG(listSP_DGG);
    }//GEN-LAST:event_tblPhieuGGMouseClicked

    private void cboThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThuongHieuActionPerformed
        try {
            String tt = cboThuongHieu.getSelectedItem().toString();
            System.out.println(tt);
            dtmSP.setRowCount(0);
            if (tt.equals("---Tất cả---")) {
                for (SanPhamChiTiet spct : lisrSP) {

                    dtmSP.addRow(spct.rowDataViewDGG());
                }
            }
            for (SanPhamChiTiet spct : lisrSP) {
                System.out.println("raven.application.form.other.Form_DotGiamGia.cboThuongHieuActionPerformed()" + spct.getIdSanPham().getTenSanpham());
                if (spct.getIdThuongHieu().getTenThuongHieu().equals(tt)) {
                    dtmSP.addRow(spct.rowDataViewDGG());
                }

            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_cboThuongHieuActionPerformed

    private void cboThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboThuongHieuMouseClicked

    }//GEN-LAST:event_cboThuongHieuMouseClicked

    private void cboThuongHieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThuongHieuItemStateChanged

    }//GEN-LAST:event_cboThuongHieuItemStateChanged

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        chkAll.setSelected(false);
        for (int i = 0; i < tblSP.getRowCount(); i++) {
            tblSP.setValueAt(false, i, 6);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void chkAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAllActionPerformed
        for (int i = 0; i < tblSP.getRowCount(); i++) {
            tblSP.setValueAt(true, i, 6);
        }
    }//GEN-LAST:event_chkAllActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = txtDau.getDate();
            date2 = txtDau1.getDate();

        } catch (Exception e) {
            MsgBox.aleart(this, "Ngày tháng năm ko hợp lệ");
            return;
        }
        if (date1 != null || date2 != null) {
            if (date1 == null || date2 == null) {
                MsgBox.aleart(this, "Bạn hãy điền đủ 2 ngày");
                return;
            }

        }

        if (date1 == null || date2 == null) {
            MsgBox.aleart(this, "Bạn hãy điền đủ 2 ngày");
            return;
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

        if (Utils.Validate.isEmpty(XDate.toString(date1, "dd-MM-yyyy"))) {
            MsgBox.aleart(this, "Ngày bắt đầu không được để trống");
            txtDau.requestFocus();
            return;
        } else {
            if (!Utils.Validate.isDate(XDate.toString(date1, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày bắt đầu sai định dạng dd-MM-yyyy");
                txtDau.requestFocus();
                return;
            }
            try {
                XDate.toDate(XDate.toString(date1, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");
                txtDau.requestFocus();
                return;
            }
        }

        if (Utils.Validate.isEmpty(XDate.toString(date2, "dd-MM-yyyy"))) {
            MsgBox.aleart(this, "Ngày kết thúc không được để trống");
            txtDau1.requestFocus();
            return;
        } else {
            if (!Utils.Validate.isDate(XDate.toString(date2, "dd-MM-yyyy"))) {
                MsgBox.aleart(this, "Ngày kết thúc sai định dạng dd-MM-yyyy");
                txtDau1.requestFocus();
                return;
            }
            try {
                XDate.toDate(XDate.toString(date1, "dd-MM-yyyy"), "dd-MM-yyyy");
            } catch (Exception e) {
                MsgBox.aleart(this, "Ngày hoặc Tháng hoặc Năm sai ");
                txtDau1.requestFocus();
                return;
            }
        }
        System.out.println("raven.application.form.other.Form_DotGiamGia.jButton6ActionPerformed()" + date1 + date2 + "");
        if (date1 == null || date2 == null) {
            MsgBox.aleart(this, "Bạn hãy điển đủ thông tin");
            return;
        }
        ViewCTSP_DGG cTSP_DGG = new ViewCTSP_DGG(new Application(), true, date1, date2);
        cTSP_DGG.setVisible(true);
        lisrSP = cTSP_DGG.getTable();
        fillToTableSP(lisrSP);
    }//GEN-LAST:event_btnLocActionPerformed
    public boolean compareDates(String dateStr1, String dateStr2) {
        // Định dạng để chuyển đổi từ chuỗi sang LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Chuyển đổi chuỗi thành LocalDate
        LocalDate date1 = LocalDate.parse(dateStr1, formatter);
        LocalDate date2 = LocalDate.parse(dateStr2, formatter);

        // So sánh ngày
        return date2.isAfter(date1);
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insertDGG();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtGiaTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriActionPerformed

    private void cboLoaiPhieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiPhieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiPhieuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCuoi;
    private javax.swing.JButton btnDau;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTien;
    private javax.swing.JComboBox<String> cboLoaiPhieu;
    private javax.swing.JComboBox<String> cboThuongHieu;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JCheckBox chkAll;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblPageTTKH;
    private javax.swing.JTable tblPhieuGG;
    private javax.swing.JTable tblSP;
    private javax.swing.JTable tblSPDGG;
    private com.toedter.calendar.JDateChooser txtDau;
    private com.toedter.calendar.JDateChooser txtDau1;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtMaPhieu;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtTenPhieu;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
