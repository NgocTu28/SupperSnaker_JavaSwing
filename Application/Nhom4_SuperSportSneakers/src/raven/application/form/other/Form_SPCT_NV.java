/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Impl.giayChiTiet_Impl;
import Model.KichThuoc;
import Model.MauSac;
import Model.SanPham;
import Model.SanPhamChiTiet;
import Model.ThuongHieu;
import Repository.KichThuoc_Repository;
import Repository.MauSac_Reponsitory;
import Repository.SanPhamCT_Repository;
import Repository.SanPham_Repository;
import Repository.ThuongHieu_Repository;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import static raven.application.form.other.Form_SPCT.spct;
import raven.toast.Notifications;

/**
 *
 * @author vutu8
 */
public class Form_SPCT_NV extends javax.swing.JPanel {

    private static SanPham_Repository sanPham_Repository = new SanPham_Repository();
    private static SanPhamCT_Repository sanPhamCT_Repository = new SanPhamCT_Repository();
    public static SanPhamChiTiet spct = new SanPhamChiTiet();
    //
    MauSac_Reponsitory mauSac_Reponsitory = new MauSac_Reponsitory();
    KichThuoc_Repository kichThuoc_Repository = new KichThuoc_Repository();
    ThuongHieu_Repository hieu_Repository = new ThuongHieu_Repository();
    //
    List<SanPhamChiTiet> listSPCT = new ArrayList<>();
    List<SanPhamChiTiet> listSPCT1 = new ArrayList<>();
    List<SanPham> listSP = new ArrayList<>();
    List<SanPham> listSP1 = new ArrayList<>();
    DefaultTableModel tblModelSanPham = new DefaultTableModel();
    DefaultTableModel tblModelSanPhamChiTiet = new DefaultTableModel();
    //
    private static int page = 1;
    private static int gioiHanPage = (int) ((Math.ceil(sanPhamCT_Repository.getRowCount() / 4)) + 1);
    private static int gioiHanPage1 = (int) ((Math.ceil(sanPham_Repository.getRowCount() / 4)) + 1);
    private static giayChiTiet_Impl chiTiet_Impl = new giayChiTiet_Impl();
    //
    DefaultComboBoxModel cboModelTenGiay1 = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelThuongHieu1 = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelKichThuoc1 = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelMauSac1 = new DefaultComboBoxModel();
    //
    DefaultComboBoxModel cboModelThuongHieu = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelKichThuoc = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelMauSac = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelTenGiay = new DefaultComboBoxModel();
    //
    JPanel jpnWebcam = new JPanel();
    JDialog dlScanQr = new JDialog();
    //Scan QR
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private Thread thread;

    public Form_SPCT_NV() {
        initComponents();
        listSP1 = sanPham_Repository.get1(page, 5);
        listSPCT1 = sanPhamCT_Repository.get(page, 10);
        //
        fillToTableSanPhamChiTiet(listSPCT1);
        fillToTableSanPham(listSP1);
        //
        fillToCboTenSP();
        fillToCboMauSac1();
        fillToCboKichThuoc1();
        fillToCboThuongHieu1();
        // 
        fillToComboThuongHieu();
        fillToComboMauSac();
        fillToComboKichThuoc();
        fillToComboTenSanPham();
        //
        initSearch();
        //
        ImageIcon iconDialog = new ImageIcon("E:\\Fpoly\\Snaker\\SuperSport-Sneakers\\Application\\Nhom4_SuperSportSneakers\\src\\raven\\icon\\png/logo3.png");
        ImageIcon iconDialogThem = new ImageIcon("E:\\Fpoly\\Snaker\\SuperSport-Sneakers\\Application\\Nhom4_SuperSportSneakers\\src\\raven\\icon\\pngaddUser.png");
        dlScanQr.setTitle("Scan QR");
        dlScanQr.setIconImage(iconDialog.getImage());
        soTrang.setText(page + " / " + gioiHanPage);
        soTrang1.setText(page + " / " + gioiHanPage);
    }

     private void initWebcam() {
        Dimension d = new Dimension(100, 100);
        webcam = Webcam.getWebcams().get(0);
        webcam.setCustomViewSizes(new Dimension[]{d});
        webcam.setViewSize(d);

        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setPreferredSize(d);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setVisible(true);
        webcamPanel.setDisplayDebugInfo(true);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setMirrored(true);
        if (jpnWebcam != null && jpnWebcam.getParent() != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jpnWebcam.getParent().revalidate();
                    jpnWebcam.getParent().repaint();
                }
            });
        }
        dlScanQr.add(webcamPanel);

    }

    private void captureThread() {
        thread = new Thread() {
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    Result result = null;
                    BufferedImage image = null;

                    if (webcam.isOpen()) {
                        if ((image = webcam.getImage()) == null) {
                            continue;
                        }
                    }
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    try {
                        result = new MultiFormatReader().decode(bitmap);
                    } catch (NotFoundException ex) {
                        ex.printStackTrace();
                        continue;
                    }
                    if (result != null) {
                        String resultText = result.getText();
                        String[] arrResult = resultText.split("\\n");
                        txtSearch.setText(arrResult[1].substring(10));
                        dlScanQr.setVisible(false);
                        searchSanPham();
                        webcam.close();
                        thread.stop();
                    }

                } while (true);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void searchSanPham() {
        String keyWord = (String) txtSearch.getText();
        if (keyWord.isEmpty() || keyWord == null) {
            return;
        }
        SanPhamChiTiet result = (SanPhamChiTiet) sanPhamCT_Repository.search_SanPhamChiTiet(keyWord);
        if (result == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy");
            return;
        }

        cboTenGiay1.setSelectedItem(result.getIdSanPham().getTenSanpham());
        txtMaSPCT.setText(result.getMaSPCT());
        String soLuong = String.valueOf(result.getSoLuong());
        txtSoLuong1.setText(soLuong);
        String giaBan = result.getGiaBan().toString();
        txtGiaBan.setText(giaBan);
        String giaBan1 = result.getGiaNiemYet().toString();
        txtGiaNiemYet.setText(giaBan1);
        if (result.getTrangThai() == 0) {
            cboTrangThai1.setSelectedIndex(0);
        } else if (result.getTrangThai() == 1) {
            cboTrangThai1.setSelectedIndex(1);
        } else if (result.getTrangThai() == 2) {
            cboTrangThai1.setSelectedIndex(2);
        }
        cboMauSac1.setSelectedItem(result.getIdMau().getTenMau());
        cboKichThuoc1.setSelectedItem(result.getIdKichThuoc().getTenSize());
        cboThuongHieu1.setSelectedItem(result.getIdThuongHieu().getTenThuongHieu());
        txtMoTa1.setText(result.getMoTa());
        spct = result;

    }

    private void initSearch() {

        KichThuoc kt = new KichThuoc();
        if (cboKichThuoc.getSelectedItem() instanceof KichThuoc) {
            kt = (KichThuoc) cboKichThuoc.getSelectedItem();
        }
        Long idKichThuoc = null;
        idKichThuoc = kt.getIdSize();

        MauSac ms = new MauSac();
        if (cboMauSac.getSelectedItem() instanceof MauSac) {
            ms = (MauSac) cboMauSac.getSelectedItem();
        }
        Long idMau = null;
        idMau = ms.getIdMau();

        SanPham sp = new SanPham();
        if (cboTenGiay.getSelectedItem() instanceof SanPham) {
            sp = (SanPham) cboTenGiay.getSelectedItem();
        }
        Long idSanPham = null;
        idSanPham = sp.getIdSanPham();

        ThuongHieu th = new ThuongHieu();
        if (cboThuongHieu.getSelectedItem() instanceof ThuongHieu) {
            th = (ThuongHieu) cboThuongHieu.getSelectedItem();
        }
        Long idThuongHieu = null;
        idThuongHieu = th.getIdThuongHieu();

        int trangThai = 0;
        if (cboTrangThai.getSelectedIndex() == 1) {
            trangThai = 0;
        } else if (cboTrangThai.getSelectedIndex() == 2) {
            trangThai = 1;
        } else if (cboTrangThai.getSelectedIndex() == 3) {
            trangThai = 2;
        }

        List<SanPhamChiTiet> list = sanPhamCT_Repository.searchItem(idMau, idKichThuoc, idThuongHieu, idSanPham, trangThai);
        fillToTableSanPhamChiTiet(list);
    }

    public void fillToComboTenSanPham() {
        cboModelTenGiay = (DefaultComboBoxModel) this.cboTenGiay.getModel();
        List<SanPham> list = sanPham_Repository.getToAllSanPham();
        for (SanPham sanPham : list) {
            cboModelTenGiay.addElement(sanPham);
        }
    }

    public void fillToComboKichThuoc() {
        cboModelKichThuoc = (DefaultComboBoxModel) this.cboKichThuoc.getModel();
        List<KichThuoc> list = kichThuoc_Repository.getToAllKichThuoc();
        for (KichThuoc kt : list) {
            cboModelKichThuoc.addElement(kt);
        }
    }

    public void fillToComboMauSac() {
        cboModelMauSac = (DefaultComboBoxModel) this.cboMauSac.getModel();
        List<MauSac> list = mauSac_Reponsitory.getToAll();
        for (MauSac ms : list) {
            cboModelMauSac.addElement(ms);
        }
    }

    public void fillToComboThuongHieu() {
        cboModelThuongHieu = (DefaultComboBoxModel) this.cboThuongHieu.getModel();
        List<ThuongHieu> list = hieu_Repository.getToAll();
        for (ThuongHieu th : list) {
            cboModelThuongHieu.addElement(th);
        }
    }

    private SanPhamChiTiet getSanPhamChiTiet() {
        KichThuoc kt = new KichThuoc();
        if (cboKichThuoc1.getSelectedItem() instanceof KichThuoc) {
            kt = (KichThuoc) cboKichThuoc1.getSelectedItem();
        }
        MauSac ms = new MauSac();
        if (cboMauSac.getSelectedItem() instanceof MauSac) {
            ms = (MauSac) cboMauSac.getSelectedItem();
        }
        SanPham sp = new SanPham();
        if (cboTenGiay1.getSelectedItem() instanceof SanPham) {
            sp = (SanPham) cboTenGiay1.getSelectedItem();
        }
        ThuongHieu th = new ThuongHieu();
        if (cboThuongHieu1.getSelectedItem() instanceof ThuongHieu) {
            th = (ThuongHieu) cboThuongHieu1.getSelectedItem();
        }
        String maSanPhamCT;

        if (txtMaSPCT.getText().isEmpty()) {
            maSanPhamCT = sanPhamCT_Repository.MaTuDongSanPham();
        } else {
            maSanPhamCT = txtMaSPCT.getText();
        }

        if (txtSoLuong1.getText().isEmpty() || txtSoLuong1.getText() == null) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Số lượng không được để trống.");
            return null;
        }

        int soLuong = 0;
        try {
            soLuong = Integer.parseInt(txtSoLuong1.getText());
            if (soLuong < 0) {
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Số lượng phải lớn hơn hoặc bằng 0.");
                return null;
            }
        } catch (Exception e) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Số lượng phải là số tự nhiên.");
            return null;
        }

        BigDecimal giaBan, giaNiemYet;

        String txtGiaBanValue = txtGiaBan.getText();
        String txtGiaNiemYetValue = txtGiaNiemYet.getText();

        if (txtGiaBanValue == null || txtGiaNiemYetValue.isEmpty() || txtGiaBanValue == null || txtGiaNiemYetValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Giá tiền không được để trống.");
            return null;
        }

        try {
            giaBan = new BigDecimal(txtGiaBanValue);
            giaNiemYet = new BigDecimal(txtGiaNiemYetValue);

            if (giaBan.compareTo(BigDecimal.ZERO) < 0 || giaNiemYet.compareTo(BigDecimal.ZERO) < 0) {
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Giá tiền phải lớn hơn hoặc bằng 0.");
                return null;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Giá tiền phải là số thực.");
            return null;
        }

        String moTa = txtMoTa1.getText();
        int trangThai = cboTrangThai1.getSelectedIndex();

        return new SanPhamChiTiet(maSanPhamCT, soLuong, giaBan, giaNiemYet, trangThai, moTa, ms, kt, th, sp);
    }

    public void fillToCboTenSP() {
        cboModelTenGiay1 = (DefaultComboBoxModel) this.cboTenGiay1.getModel();
        List<SanPham> list = sanPham_Repository.getToAllSanPham();
        for (SanPham sanPham : list) {
            cboModelTenGiay1.addElement(sanPham);
        }
    }

    public void fillToCboMauSac1() {
        cboModelMauSac1 = (DefaultComboBoxModel) this.cboMauSac1.getModel();
        List<MauSac> list = mauSac_Reponsitory.getToAll();
        for (MauSac ms : list) {
            cboModelMauSac1.addElement(ms);
        }
    }

    public void fillToCboKichThuoc1() {
        cboModelKichThuoc1 = (DefaultComboBoxModel) this.cboKichThuoc1.getModel();
        List<KichThuoc> list = kichThuoc_Repository.getToAllKichThuoc();
        for (KichThuoc kt : list) {
            cboModelKichThuoc1.addElement(kt);
        }
    }

    public void fillToCboThuongHieu1() {
        cboModelThuongHieu1 = (DefaultComboBoxModel) this.cboThuongHieu1.getModel();
        List<ThuongHieu> list = hieu_Repository.getToAll();
        for (ThuongHieu th : list) {
            cboModelThuongHieu1.addElement(th);
        }
    }

    public void fillToTableSanPhamChiTiet(List<SanPhamChiTiet> listSanPhamChiTiet) {
        tblModelSanPhamChiTiet = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        tblModelSanPhamChiTiet.setRowCount(0);
        int n = 1;
        for (SanPhamChiTiet i : listSanPhamChiTiet) {
            Object[] row = new Object[13];
            row[0] = i.getMaSPCT();
            row[1] = i.getIdSanPham().getTenSanpham();
            row[2] = i.getIdThuongHieu().getTenThuongHieu();
            row[3] = i.getIdKichThuoc().getTenSize();
            row[4] = i.getIdMau().getTenMau();
            row[5] = i.getGiaNiemYet();
            row[6] = i.getTrangThai() == 0 ? "CÒN HÀNG" : (i.getTrangThai() == 1 ? "TẠM HẾT" : "DỪNG BÁN");
            row[7] = i.getSoLuong();
            tblModelSanPhamChiTiet.addRow(row);
        }
    }

    public void fillToTableSanPham(List<SanPham> lst) {
        tblModelSanPham = (DefaultTableModel) tblSanPham.getModel();
        tblModelSanPham.setRowCount(0);
        int n = 1;
        for (SanPham i : lst) {
            Object[] rows = new Object[4];
            rows[0] = i.getMaSanPham();
            rows[1] = i.getTenSanpham();
            rows[2] = (i.getTrangThai() == 0) ? "Đang Kinh Doanh" : ((i.getTrangThai() == 1) ? "Ngừng Kinh Doanh" : "Đã Hết Hàng");
            tblModelSanPham.addRow(rows);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        cboThuongHieu = new javax.swing.JComboBox<>();
        cboKichThuoc = new javax.swing.JComboBox<>();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnQRScan = new javax.swing.JButton();
        cboTenGiay = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboTrangThaiSanPham = new javax.swing.JComboBox<>();
        btnReset_SanPham = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        soTrang1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cboTenGiay1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtMaSPCT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboThuongHieu1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cboKichThuoc1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cboMauSac1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtGiaNiemYet = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cboTrangThai1 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMoTa1 = new javax.swing.JTextArea();
        soTrang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSoLuong1 = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("Danh Sách Sản Phẩm");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Sản Phẩm"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tên Sản Phẩm");

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Màu Sắc --" }));
        cboMauSac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMauSacItemStateChanged(evt);
            }
        });

        cboThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Thương Hiệu --" }));
        cboThuongHieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThuongHieuItemStateChanged(evt);
            }
        });

        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Kích Thước --" }));
        cboKichThuoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKichThuocItemStateChanged(evt);
            }
        });

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Trạng Thái --", "Còn Hàng", "Tạm Hết", "Dừng Bán" }));
        cboTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTrangThaiItemStateChanged(evt);
            }
        });

        btnQRScan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/qr-code_9460284.png"))); // NOI18N
        btnQRScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQRScanActionPerformed(evt);
            }
        });

        cboTenGiay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Tên Sản Phẩm --" }));
        cboTenGiay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenGiayItemStateChanged(evt);
            }
        });

        txtSearch.setText("Mã Sản Phẩn Chi Tiết");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cboTenGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnQRScan)
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTenGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnQRScan)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Trạng Thái Sản Phẩm"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        jLabel3.setText("Mã Sản Phẩm:");

        txtMaSanPham.setEnabled(false);

        jLabel5.setText("Tên Sản Phẩm:");

        jLabel6.setText("Trạng Thái:");

        cboTrangThaiSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Kinh Doanh", "Ngừng Kinh Doanh", "Đã Hết Hàng" }));

        btnReset_SanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/png/clean.png"))); // NOI18N
        btnReset_SanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset_SanPhamActionPerformed(evt);
            }
        });

        jButton5.setText("|<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setText(">|");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton6.setText("<<");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText(">>");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        soTrang1.setText("1 / 2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(soTrang1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtMaSanPham))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenSanPham)
                                    .addComponent(cboTrangThaiSanPham, 0, 217, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset_SanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(soTrang1)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboTrangThaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnReset_SanPham)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi Tiết Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SPCT", "Tên Giày ", "Thương Hiệu", "Kích Thước", "Màu Sắc", "Giá Niêm Yết", "Trạng Thái", "Số Lượng"
            }
        ));
        tblSanPhamChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamChiTietMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPhamChiTiet);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 28, 660, 296));

        jButton12.setText("<<");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 342, 61, 38));

        jButton9.setText(">>");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 342, 61, 38));

        jButton10.setText(">|");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 342, 61, 38));

        jButton11.setText("|<");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 342, 61, 38));

        jLabel4.setText("Tên Giày:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 58, -1, -1));

        jPanel3.add(cboTenGiay1, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 55, 190, -1));

        jLabel9.setText("Mã SPCT");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 25, -1, -1));
        jPanel3.add(txtMaSPCT, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 22, 191, -1));

        jLabel10.setText("Thương Hiệu");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 83, -1, -1));

        jPanel3.add(cboThuongHieu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 83, 190, -1));

        jLabel11.setText("Kích Thước");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 114, -1, -1));

        jPanel3.add(cboKichThuoc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 111, 190, -1));

        jLabel12.setText("Màu Sắc");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 139, -1, -1));

        jPanel3.add(cboMauSac1, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 139, 190, -1));

        jLabel13.setText("Giá Niêm Yết");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 170, -1, -1));
        jPanel3.add(txtGiaNiemYet, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 167, 190, -1));

        jLabel14.setText("Giá Bán");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 195, -1, -1));
        jPanel3.add(txtGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 195, 190, -1));

        jLabel15.setText("Trạng Thái");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 226, -1, -1));

        cboTrangThai1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn Hàng", "Tạm Hết", "Dừng Bán" }));
        jPanel3.add(cboTrangThai1, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 223, 190, -1));

        jLabel16.setText("Mô Tả");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 294, -1, -1));

        txtMoTa1.setColumns(20);
        txtMoTa1.setRows(5);
        jScrollPane3.setViewportView(txtMoTa1);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 285, 251, 95));

        soTrang.setText("1 / 2");
        jPanel3.add(soTrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 342, 55, 38));

        jLabel17.setText("Số Lượng");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 251, -1, -1));
        jPanel3.add(txtSoLuong1, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 251, 190, -1));

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/png/clean.png"))); // NOI18N
        jButton14.setText("Reset");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1180, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(345, 345, 345)
                            .addComponent(jLabel2))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 724, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(6, 6, 6)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(4, 4, 4)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(2, 2, 2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(330, 330, 330)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboMauSacItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMauSacItemStateChanged
        initSearch();
    }//GEN-LAST:event_cboMauSacItemStateChanged

    private void cboThuongHieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThuongHieuItemStateChanged
        initSearch();
    }//GEN-LAST:event_cboThuongHieuItemStateChanged

    private void cboKichThuocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKichThuocItemStateChanged
        initSearch();
    }//GEN-LAST:event_cboKichThuocItemStateChanged

    private void cboTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTrangThaiItemStateChanged
        initSearch();
    }//GEN-LAST:event_cboTrangThaiItemStateChanged

    private void btnQRScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQRScanActionPerformed
        if (webcam != null) {
            if (webcam.isOpen()) {
                webcam.close();
                thread.stop();
                dlScanQr.setVisible(false);
            }
        }
        initWebcam();
        captureThread();
        dlScanQr.setVisible(true);
        dlScanQr.setSize(500, 500);
        dlScanQr.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnQRScanActionPerformed

    private void cboTenGiayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenGiayItemStateChanged
        initSearch();
    }//GEN-LAST:event_cboTenGiayItemStateChanged

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        List<SanPhamChiTiet> listSearch = sanPhamCT_Repository.search_SanPhamChiTiet(txtSearch.getText().trim());
        System.out.println(listSearch);
        fillToTableSanPhamChiTiet(listSearch);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int index = tblSanPham.getSelectedRow();
        if (index < 0) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Vui lòng chọn dòng cần hiện thông tin.");
            return;
        } else {
            txtMaSanPham.setText((String) tblSanPham.getValueAt(index, 0));
            txtTenSanPham.setText((String) tblSanPham.getValueAt(index, 1));

            if (tblSanPham.getValueAt(index, 2).equals("Đang Kinh Doanh")) {
                cboTrangThaiSanPham.setSelectedIndex(0);
            } else if (tblSanPham.getValueAt(index, 2).equals("Ngừng Kinh Doanh")) {
                cboTrangThaiSanPham.setSelectedIndex(1);
            } else if (tblSanPham.getValueAt(index, 2).equals("Đã Hết Hàng")) {
                cboTrangThaiSanPham.setSelectedIndex(2);
            }
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnReset_SanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset_SanPhamActionPerformed
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        cboTrangThaiSanPham.setSelectedIndex(0);
        fillToTableSanPham(listSP1);
    }//GEN-LAST:event_btnReset_SanPhamActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        fillToTableSanPham(sanPham_Repository.get1(1, 5));
        soTrang1.setText(page + " / " + gioiHanPage);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        fillToTableSanPham(sanPham_Repository.get1(gioiHanPage, 5));
        soTrang1.setText(page + " / " + gioiHanPage);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        page--;
        if (page >= 1) {
            soTrang1.setText(page + " / " + gioiHanPage);
            fillToTableSanPham(sanPham_Repository.get1(page, 5));
        } else {
            page = 1;
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        page++;
        if (page <= gioiHanPage) {
            fillToTableSanPham(sanPham_Repository.get1(page, 5));
            soTrang1.setText(page + " / " + gioiHanPage);
            return;
        }
        page = gioiHanPage;
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tblSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamChiTietMouseClicked
        int index = tblSanPhamChiTiet.getSelectedRow();
        String maSPCT = tblSanPhamChiTiet.getValueAt(index, 0).toString();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trên bảng để hiển thị.");
            return;
        } else {
            SanPhamChiTiet sp = sanPhamCT_Repository.getProductByMa(maSPCT);
            txtMaSPCT.setText(maSPCT);
            String nameShoes = (String) tblSanPhamChiTiet.getValueAt(index, 1).toString();
            cboModelTenGiay1.setSelectedItem(nameShoes);

            String mauSac = (String) tblSanPhamChiTiet.getValueAt(index, 4);
            cboModelMauSac1.setSelectedItem(mauSac);

            String kichThuoc = (String) tblSanPhamChiTiet.getValueAt(index, 3).toString();
            cboModelKichThuoc1.setSelectedItem(kichThuoc);

            String thuongHieu = (String) tblSanPhamChiTiet.getValueAt(index, 2);
            cboModelThuongHieu1.setSelectedItem(thuongHieu);

            BigDecimal giaNiemYet = sp.getGiaNiemYet();
            String gia1 = String.valueOf(giaNiemYet);
            txtGiaNiemYet.setText(gia1);

            BigDecimal giaBan = sp.getGiaBan();
            String gia2 = String.valueOf(giaBan);
            txtGiaBan.setText(gia2);
            txtGiaBan.setEditable(false);
            if (tblSanPhamChiTiet.getValueAt(index, 6).equals("CÒN HÀNG")) {
                cboTrangThai.setSelectedIndex(0);
            } else if (tblSanPhamChiTiet.getValueAt(index, 6).equals("TẠM HẾT")) {
                cboTrangThai.setSelectedIndex(1);
            } else if (tblSanPhamChiTiet.getValueAt(index, 6).equals("DỪNG BÁN")) {
                cboTrangThai.setSelectedIndex(2);
            }
            txtSoLuong1.setText((String) tblSanPhamChiTiet.getValueAt(index, 7).toString());
            String MoTa = sp.getMoTa();
            txtMoTa1.setText(MoTa);
        }
    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        page--;
        if (page >= 1) {
            soTrang.setText(page + " / " + gioiHanPage);
            fillToTableSanPhamChiTiet(sanPhamCT_Repository.get(page, 10));
        } else {
            page = 1;
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        page++;
        if (page <= gioiHanPage) {
            fillToTableSanPhamChiTiet(sanPhamCT_Repository.get(page, 10));
            soTrang.setText(page + " / " + gioiHanPage);
            return;
        }
        page = gioiHanPage;
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        fillToTableSanPhamChiTiet(sanPhamCT_Repository.get(gioiHanPage, 10));
        soTrang.setText(page + " / " + gioiHanPage);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        fillToTableSanPhamChiTiet(sanPhamCT_Repository.get(1, 10));
        soTrang.setText(page + " / " + gioiHanPage);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        txtMaSPCT.setText("");
        txtGiaBan.setText("");
        txtGiaNiemYet.setText("");
        txtMoTa1.setText("");
        cboTenGiay.setSelectedIndex(0);
        txtSoLuong1.setText("");
        cboThuongHieu1.setSelectedIndex(0);
        cboThuongHieu.setSelectedIndex(0);
        cboKichThuoc.setSelectedIndex(0);
        cboKichThuoc1.setSelectedIndex(0);
        cboMauSac.setSelectedIndex(0);
        cboMauSac1.setSelectedIndex(0);
        cboTenGiay1.setSelectedIndex(0);
        cboTrangThai1.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        fillToTableSanPhamChiTiet(listSPCT1);
    }//GEN-LAST:event_jButton14ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQRScan;
    private javax.swing.JButton btnReset_SanPham;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboKichThuoc1;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboMauSac1;
    private javax.swing.JComboBox<String> cboTenGiay;
    private javax.swing.JComboBox<String> cboTenGiay1;
    private javax.swing.JComboBox<String> cboThuongHieu;
    private javax.swing.JComboBox<String> cboThuongHieu1;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JComboBox<String> cboTrangThai1;
    private javax.swing.JComboBox<String> cboTrangThaiSanPham;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField soTrang;
    private javax.swing.JTextField soTrang1;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNiemYet;
    private javax.swing.JTextField txtMaSPCT;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextArea txtMoTa1;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong1;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
}
