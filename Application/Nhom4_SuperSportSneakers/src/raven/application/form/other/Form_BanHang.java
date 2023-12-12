/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.ChiTietDotGiamGia;
import Model.ChiTietHoaDon;
import Model.SanPhamChiTiet;
import Model.DotGiamGia_M;
import Model.HoaDon;
import Model.KhachHang;
import Model.KichThuoc;
import Model.MauSac;
import Model.NhanVien;
import Model.PhieuGiamGia;
import Model.PhieuGiamGia;
import Model.PhieuGiaoHang;
import Model.SanPham;
import Model.ThuongHieu;
import Repository.ChiTietDotGiamRepository;
import Repository.ChiTietHoaDon_RepositoryM;
import Repository.SanPhamCT_Repository;
import Repository.DotGiamGia_MRpository;
import Repository.HoaDon_MRepositoryM;
import Repository.KhachHangRepositoryM;
import Repository.KichThuoc_Repository;
import Repository.MauSac_Reponsitory;
import Repository.PhieuGiamGiaService;
import Repository.PhieuGiaoHangRepository;
import Repository.SanPham_Repository;
import Repository.ThuongHieu_Repository;
import Utils.Auth;
import Utils.MsgBox;
import Utils.Validate;
import Utils.XDate;
import antlr.Utils;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import raven.application.Application;
import Utils.Format;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.DefaultComboBoxModel;
import Utils.Email;

/**
 *
 * @author manhnt
 */
public class Form_BanHang extends javax.swing.JPanel implements Runnable, ThreadFactory {

    private static Long IdNV = 0L;

    private static SanPham_Repository sanPham_Repository = new SanPham_Repository();
    private static List<SanPhamChiTiet> list = new ArrayList<>();
    private static List<HoaDon> listHD = new ArrayList<>();
    private static List<ChiTietHoaDon> listGH = new ArrayList<>();
    private static HoaDon_MRepositoryM hoaDon_MRepository = new HoaDon_MRepositoryM();
    private static SanPhamCT_Repository chiTietSanPham_Repository = new SanPhamCT_Repository();
    private static ChiTietHoaDon_RepositoryM chiTietHoaDon_Repository = new ChiTietHoaDon_RepositoryM();
    private static DotGiamGia_MRpository dotGiamGia_MRpository = new DotGiamGia_MRpository();
    private static KhachHangRepositoryM khachHangRepositoryM = new KhachHangRepositoryM();
    private static KhachHang defaultKhachHang = new KhachHang(1L, "KH00", "Khách bán lẻ", 0, 3);

    private static PhieuGiaoHangRepository phieuGiaoHangRepository = new PhieuGiaoHangRepository();
    private static ChiTietDotGiamRepository chiTietDotGiamRepository = new ChiTietDotGiamRepository();
    private static int page = 1;
    private static int lmit = 9;
    private static int gioiHanPage = (int) ((Math.ceil(chiTietSanPham_Repository.getRowCount() / lmit))) + 1;

    DefaultComboBoxModel cboModelTenGiay1 = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelThuongHieu1 = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelKichThuoc1 = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelMauSac1 = new DefaultComboBoxModel();
    //
    DefaultComboBoxModel cboModelThuongHieu = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelKichThuoc = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelMauSac = new DefaultComboBoxModel();
    DefaultComboBoxModel cboModelTenGiay = new DefaultComboBoxModel();
    private static SanPhamCT_Repository sanPhamCT_Repository = new SanPhamCT_Repository();
    public static SanPhamChiTiet spct = new SanPhamChiTiet();
    //
    MauSac_Reponsitory mauSac_Reponsitory = new MauSac_Reponsitory();
    KichThuoc_Repository kichThuoc_Repository = new KichThuoc_Repository();
    ThuongHieu_Repository hieu_Repository = new ThuongHieu_Repository();
    private List<ChiTietDotGiamGia> listCTDGG = new ArrayList<>();
    private DefaultTableModel dtm = new DefaultTableModel();
    // pgg
    private static PhieuGiamGiaService phieuGiamGiaService = new PhieuGiamGiaService();
    private static List<PhieuGiamGia> listPGG = new ArrayList<>();
    private PhieuGiamGia phieuGiamGia = new PhieuGiamGia();

    // pgh
    private static PhieuGiaoHang phieuGiaoHang = new PhieuGiaoHang();
    private static int indexHD = -1;

    private static int demClick = 0;
    static int indexWebcam = 0;
    //Webcam
    private Webcam webcam = null;

    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    private Thread thread;

    private String date = XDate.toString(new Date(), "yyyy-MM-dd");

    /**
     * Creates new form Form_BanHang
     */
    public Form_BanHang() {
        initComponents();
        initWebcam();
        captureThread();
        init();

    }

    void init() {
        txtNV.setText(Auth.nv.getTenNhanVien());
        lblDonTreo.setText(hoaDon_MRepository.getHoaDonTrao(0, Auth.nv.getId()) + "");
        lblPageTTKH.setText(1 + " / " + gioiHanPage);
        setLblCapBac(defaultKhachHang);
        listHD = hoaDon_MRepository.getAllHDByTrangThai2(0, Auth.nv.getId());
        fillToTableHD(listHD);
        ///  Dợt Giam Giá
        // CHeck kết thúc
        dotGiamGia_MRpository.updateKT(date);
        List<ChiTietDotGiamGia> listCTDGGKT = chiTietDotGiamRepository.getAllCT_CTDGG_KT();
        for (ChiTietDotGiamGia ctdgg : listCTDGGKT) {
            chiTietDotGiamRepository.updateTrangIDGG_SP(ctdgg.getIdCTSP());
        }
        // Bắt Dầu DGG
        listCTDGG = chiTietDotGiamRepository.getAllCT_CTDGG();
        for (ChiTietDotGiamGia ctdgg : listCTDGG) {
            System.out.println("raven.application.form.other.Form_BanHang.init()");
            chiTietDotGiamRepository.update_SP(ctdgg);
        }
        phieuGiamGiaService.updateBD(date);
        phieuGiamGiaService.updateKT(date);
        hoaDon_MRepository.updateSP();
        list = chiTietSanPham_Repository.get3(page, lmit);

        fillToTableSP(list);
        fillToCboTenSP();
        fillToCboMauSac1();
        fillToCboKichThuoc1();
        fillToCboThuongHieu1();
        System.out.println("NV " + Auth.nv);

    }

    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        webcam.setViewSize(size);

        dlWeb = new WebcamPanel(webcam);
        dlWeb.setPreferredSize(size);
        //dlWeb.setFPSDisplayed(true);

        jpnWebcam.add(dlWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));

        executor.execute(this);
    }

    private void captureThread() {

        thread = new Thread() {
            @Override
            public void run() {

                do {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
                    } catch (NotFoundException e) {
                        //No result...
                    }

                    if (result != null) {
                        String resultText = result.getText();
                        String subPGG = resultText.substring(0, 3);
                        if (subPGG.equals("PGG")) {
                            System.out.println(".run()" + resultText);
                            phieuGiamGia = phieuGiamGiaService.getPGGByMa2(resultText);
                            if (Float.parseFloat(phieuGiamGia.getDonToiThieu() + "") > Float.parseFloat("0" + txtTongTien.getText().trim())) {
                                MsgBox.aleart(new Application(), "Đơn hàng bạn chưa đủ điều kiện áp dụng !!");

                            } else {
                                hoaDon_MRepository.updateIdDGGInHDByMaHD(txtMHD.getText().trim(), phieuGiamGia);
                            }

                            setFormTT(indexHD);
                            return;
                        }
                        System.out.println(resultText);
                        String[] arrResult = resultText.split("\\n");
                        // txtSearch.setText(arrResult[1].substring(10));
                        // Chạy đoạn này " :  .....
                        System.out.println("Chuoi : 0 " + arrResult[0].substring(10));
                        System.out.println("Chuoi : 0 " + arrResult[1].substring(10));
                        List<SanPhamChiTiet> listSearch = sanPhamCT_Repository.search_SanPhamChiTiet(arrResult[0].substring(17));
                        System.out.println(listSearch);
                        fillToTableSP(listSearch);
                        //   searchSanPham();

                    }
                } while (true);
            }
        };
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    public Thread newThread(Runnable r) {
//        Thread t = new Thread(r, "My Thread");
//        t.setDaemon(true);
//        return t;
        return null;
//        Thread t = new Thread(r, "My Thread");
//        t.setDaemon(true);
//        return t;
    }

    private void searchSanPham() {
        String keyWord = (String) txtSearch.getText();
        if (keyWord.isEmpty() || keyWord == null) {
            return;
        }
        SanPhamChiTiet result = (SanPhamChiTiet) chiTietSanPham_Repository.search_SanPhamChiTiet(keyWord);
        if (result == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy");
            return;
        }

        cboTenGiay.setSelectedItem(result.getIdSanPham().getTenSanpham());
        cboMauSac.setSelectedItem(result.getIdMau().getTenMau());
        cboKichThuoc.setSelectedItem(result.getIdKichThuoc().getTenSize());
        cboThuongHieu.setSelectedItem(result.getIdThuongHieu().getTenThuongHieu());
        spct = result;

    }

    public void fillToCboTenSP() {
        cboModelTenGiay1 = (DefaultComboBoxModel) this.cboTenGiay.getModel();
        List<SanPham> list = sanPham_Repository.getToAllSanPham();
        for (SanPham sanPham : list) {
            cboModelTenGiay1.addElement(sanPham);
        }
    }

    public void fillToCboMauSac1() {
        cboModelMauSac1 = (DefaultComboBoxModel) this.cboMauSac.getModel();
        List<MauSac> list = mauSac_Reponsitory.getToAll();
        for (MauSac ms : list) {
            cboModelMauSac1.addElement(ms);
        }
    }

    public void fillToCboKichThuoc1() {
        cboModelKichThuoc1 = (DefaultComboBoxModel) this.cboKichThuoc.getModel();
        List<KichThuoc> list = kichThuoc_Repository.getToAllKichThuoc();
        for (KichThuoc kt : list) {
            cboModelKichThuoc1.addElement(kt);
        }
    }

    public void fillToCboThuongHieu1() {
        cboModelThuongHieu1 = (DefaultComboBoxModel) this.cboThuongHieu.getModel();
        List<ThuongHieu> list = hieu_Repository.getToAll();
        for (ThuongHieu th : list) {
            cboModelThuongHieu1.addElement(th);
        }
    }

    private void setLblCapBac(KhachHang khachHang) {

        if (null != khachHang.getCapBac()) {
            lblDiem.setText(khachHang.getDiem() + "");
            txtMaKH.setText(khachHang.getMaKhachHang());
            txtTenKh.setText(khachHang.getTenKhachHang());
            txtSDTKh.setText(khachHang.getSdt());

            switch (khachHang.getCapBac()) {
                case 0:
                    Color copperColor = new Color(184, 115, 51);
                    lblCapBac.setForeground(copperColor);
                    lblCapBac.setText("Đồng");
                    lblPhanTramHD.setText(khachHang.setGiamGiaCapBac(khachHang.getCapBac()) + "");
                    lblCapBac.setBackground(Color.GRAY);
                    break;
                case 1:
                    Color silverColor = new Color(192, 192, 192);
                    lblCapBac.setForeground(silverColor);
                    lblCapBac.setText("Bạc");
                    lblPhanTramHD.setText(khachHang.setGiamGiaCapBac(khachHang.getCapBac()) + "");
                    lblCapBac.setBackground(Color.GRAY);
                    break;
                case 2:
                    lblCapBac.setForeground(Color.YELLOW);
                    lblCapBac.setText("Vàng");
                    lblPhanTramHD.setText(khachHang.setGiamGiaCapBac(khachHang.getCapBac()) + "");
                    lblCapBac.setBackground(Color.GRAY);
                    break;
                case 3:
                    lblCapBac.setForeground(null);
                    lblCapBac.setBackground(null);
                    lblPhanTramHD.setText(khachHang.setGiamGiaCapBac(khachHang.getCapBac()) + "");
                    lblCapBac.setText("");
                    break;
                default:
                    break;
            }
        }
    }

    private void fillToTableSP(List<SanPhamChiTiet> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblSP.getModel();
        dtm.setRowCount(0);

        for (SanPhamChiTiet ctspm : list) {
            dtm.addRow(ctspm.rowDataSPBH());

        }
    }

    private void fillToTableGH(List<ChiTietHoaDon> list) {
        dtm = (DefaultTableModel) this.tblGH.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (ChiTietHoaDon chiTietHoaDon : list) {
            dtm.addRow(chiTietHoaDon.rowDataGioHang(i));
            //setFormTT();
            i++;
        }
    }

    private void fillToTableHD(List<HoaDon> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblHD.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (HoaDon don : list) {
            dtm.addRow(don.rowDataHDBH(i));
            i++;
        }
    }

    private void setFormTT(int index) {

        txtDiem.setText("");
        lblGiamDiem.setText("");
        txtTienKhachDua.setText("0");
        txtChuyenKhoan.setText("0");
        cboHTTT.setSelectedIndex(0);
        txtThua.setText("0");
        String maKH = tblHD.getValueAt(index, 4).toString();
        defaultKhachHang = khachHangRepositoryM.findKHByMaKH(maKH);
        setLblCapBac(defaultKhachHang);
        int khachHang = defaultKhachHang.getCapBac();
        if (khachHang == 3) {
            txtDiem.setEditable(false);
        } else {
            txtDiem.setEditable(true);
        }
        BigDecimal tongTien = BigDecimal.ZERO;
        BigDecimal thanhTien = BigDecimal.ZERO;

        String maHD = tblHD.getValueAt(indexHD, 1).toString();
        int trangThai = hoaDon_MRepository.getTrangThaiHD(maHD);
        if (trangThai == 2) {
            txtTienKhachDua.setEditable(false);
            txtChuyenKhoan.setEditable(false);
        } else {
            txtTienKhachDua.setEditable(true);
            txtChuyenKhoan.setEditable(true);
        }
        PhieuGiaoHang idPGH = phieuGiaoHangRepository.getPGHByMaHD(maHD);
        phieuGiaoHang = idPGH;
        System.out.println(idPGH);
        try {
            if (idPGH.getIdHD().getId() > 0L) {

                rdoDatHang.setSelected(true);
                rdoTaiQuay.setSelected(false);
                rdoTaiQuay.setEnabled(false);

            } else {
                rdoDatHang.setSelected(false);
                rdoTaiQuay.setSelected(true);
                rdoDatHang.setEnabled(true);
            }
        } catch (Exception e) {
            rdoDatHang.setSelected(false);
            rdoTaiQuay.setSelected(true);
            rdoDatHang.setEnabled(true);
            rdoTaiQuay.setEnabled(false);
        }

        txtMHD.setText(maHD);
        txtNV.setText(Auth.nv.getTenNhanVien());
        int i = 0;

        for (ChiTietHoaDon cthd : listGH) {
//             có thể bỏ  1 if
            if (defaultKhachHang.getCapBac() != 3) {
                //   giaTriGiam    ;
                tongTien = tongTien.add(cthd.getThanhTien());
            } else {

                tongTien = tongTien.add(cthd.getThanhTien());

            }
            i++;

        }
        thanhTien = tongTien;
        txtTongTien.setText(Format.format1(tongTien) + "");
        if (thanhTien.compareTo(BigDecimal.ZERO) < 0) {
            txtThanhTien.setText(Format.format1(new BigDecimal(0)));
        } else {
            txtThanhTien.setText(Format.format1(thanhTien));
        }
        // Lamf cap bac o day


        listPGG = phieuGiamGiaService.getALL(tongTien);
        int count0 = 0;
        for (PhieuGiamGia pgg : listPGG) {
            if (thanhTien.compareTo(pgg.getDonToiThieu()) >= 0) {
                count0++;
            }

        }
        //
        BigDecimal giamPGG = BigDecimal.ZERO;

        BigDecimal mucGiam = BigDecimal.ZERO; //vouCher.getMucGiam();

        Long idDGG = hoaDon_MRepository.getPPGByMaHD(maHD);
        if (idDGG == 0) {
            if (count0 > 0) {
                txtPhieuGiamGia.setText("Bạn có  " + count0 + " phiếu giảm giá");
            } else {
                txtPhieuGiamGia.setText("");
            }
        } else {
            phieuGiamGia = hoaDon_MRepository.getPGGByID_BH(idDGG);
            txtPhieuGiamGia.setText(phieuGiamGia.getLoaiPhieu() == 0 ? phieuGiamGia.getGiaTri() + " ( % )" : Format.format(phieuGiamGia.getGiaTri()) + "");
            if (phieuGiamGia.getLoaiPhieu() == 0) {
                mucGiam = phieuGiamGia.getGiaTri();
                BigDecimal quyDoiPhanTram = mucGiam.divide(new BigDecimal(100));
                giamPGG = quyDoiPhanTram.multiply(tongTien);
                thanhTien = tongTien.subtract(giamPGG);
                System.out.println(" % " + giamPGG);
            } else {
                giamPGG = phieuGiamGia.getGiaTri();
                thanhTien = tongTien.subtract(giamPGG);
                System.out.println(" VND " + giamPGG);
            }
        }
        txtThanhTien.setText(Format.format(thanhTien));
        System.out.println("Thanh tien sau phieu : " + thanhTien);
        BigDecimal capBac = BigDecimal.ZERO;
        BigDecimal quyTienCapBac = BigDecimal.ZERO;
        BigDecimal phanTramGiam = BigDecimal.ZERO;
        if (defaultKhachHang.getCapBac() == 0 || defaultKhachHang.getCapBac() == 3) {
            capBac = new BigDecimal(0);
            lblPhanTramHD.setText(capBac + " % ");
            phanTramGiam = capBac.divide(new BigDecimal(100));
            quyTienCapBac = phanTramGiam.multiply(thanhTien);
        } else if (defaultKhachHang.getCapBac() == 1) {
            capBac = new BigDecimal(3);
            lblPhanTramHD.setText(capBac + " % ");
            phanTramGiam = capBac.divide(new BigDecimal(100));
            quyTienCapBac = phanTramGiam.multiply(thanhTien);
        } else if (defaultKhachHang.getCapBac() == 2) {
            capBac = new BigDecimal(8);
            lblPhanTramHD.setText(capBac + " % ");
            phanTramGiam = capBac.divide(new BigDecimal(100));
            quyTienCapBac = phanTramGiam.multiply(thanhTien);
            
        }
        System.out.println("CAp BC "+ quyTienCapBac);
        thanhTien = thanhTien.subtract(quyTienCapBac);

        txtThanhTien.setText(Format.format1(thanhTien) + "");

        //  htt
        if (cboHTTT.getSelectedIndex() == 0) {
            txtChuyenKhoan.setEditable(false);
        } else {
            txtChuyenKhoan.setEditable(true);
        }
        BigDecimal diemDoi = BigDecimal.ZERO;
        BigDecimal tienKhachDua = BigDecimal.ZERO;
        try {
            if (khachHang != 3) {
                tienKhachDua = new BigDecimal(txtThua.getText().trim());
            }
        } catch (Exception e) {
        }

        if (Float.parseFloat(thanhTien + "") <= 0) {
            txtTongTien.setText("");
        }

    }

    private void first() {
        lblPageTTKH.setText(1 + " / " + gioiHanPage);
        list = chiTietSanPham_Repository.getAll_M(1, lmit);
        fillToTableSP(list);
    }

    private void prev() {
        page--;
        if (page >= 1) {
            list = chiTietSanPham_Repository.getAll_M(page, lmit);
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            fillToTableSP(list);
        } else {
            page = 1;
        }
    }

    private void next() {
        page++;
        if (page <= gioiHanPage) {
            list = chiTietSanPham_Repository.getAll_M(page, lmit);
            fillToTableSP(list);
            lblPageTTKH.setText(page + " / " + gioiHanPage);
            return;
        }
        page = gioiHanPage;
    }

    private void last() {
        lblPageTTKH.setText(gioiHanPage + " / " + gioiHanPage);
        list = chiTietSanPham_Repository.getAll_M(gioiHanPage, lmit);
        fillToTableSP(list);
    }

    private List<ChiTietHoaDon> getGioHang() {
        List<ChiTietHoaDon> list = new ArrayList<>();
        int rowCount = dtm.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Object ktr = dtm.getValueAt(i, 7);
            if (ktr instanceof Boolean && (Boolean) ktr) {
                ChiTietHoaDon chiTietHoaDonRow = listGH.get(i);
                list.add(chiTietHoaDonRow);
            }
        }
        return list;
    }

    public void getTable() {

        String maHD = tblHD.getValueAt(indexHD, 1).toString();
        for (int i = 0; i < tblGH.getRowCount(); i++) {
            Object ktr = tblGH.getValueAt(i, 8);

            if (ktr instanceof Boolean && (Boolean) ktr) {

                String maSP = tblGH.getValueAt(i, 1).toString();

                int soLuongTbl = Integer.parseInt(tblGH.getValueAt(i, 4).toString());
                int slTon = chiTietSanPham_Repository.getSoLuongTonByMaCTSP(maSP);
                int soSL = soLuongTbl + slTon;
                System.out.println(soLuongTbl + " " + slTon + " " + soSL);
                long idHD = hoaDon_MRepository.findIDByMaHD(maHD);
                long idSP = chiTietHoaDon_Repository.getIDSPCT(idHD);
                long idHDCT = listGH.get(i).getId();

                //  chiTietSanPham_Repository.updateSLSPByMa(maSP, soSL);
                chiTietHoaDon_Repository.deleteHDCT(idHDCT);

            }
        }
        System.out.println("MaHD : " + maHD);
        listGH = chiTietHoaDon_Repository.getAllHDCT(maHD);
        list = chiTietSanPham_Repository.get3(page, lmit);
        fillToTableGH(listGH);
        fillToTableSP(list);

    }

//    private void deleteGH() {
//
//        String maHD = "";
////         update lai so luong
//        for (ChiTietHoaDon chiTietHoaDon : getGioHang()) {
//            Long idHD = hoaDon_MRepository.findIDByMaHD(chiTietHoaDon.getIdHoaDon().getMaHoaDon());
//            maHD = chiTietHoaDon.getIdHoaDon().getMaHoaDon();
//            // int soLuongSPGH = tblGH.getValueAt(ERROR, lmit)
//            chiTietHoaDon_Repository.deleteHDCT(chiTietHoaDon.getIdCTSP().getIdSPCT(), idHD);
//        }
//        if (maHD.isEmpty()) {
//            maHD = tblHD.getValueAt(indexHD, 1).toString();
//        }
////        fillToTableGH(chiTietHoaDon_Repository.getAllHDCT(maHD));
//
//    }
    private void insertKH() {
        /////////////////////////
        NhanVien nhanVien = new NhanVien(2L);
        //////////////
        String sdt = txtSDTKh.getText().trim();
        if (sdt.isEmpty()) {
            MsgBox.aleart(this, "Số điện thoại khách hàng chưa có  !");
            txtSDTKh.requestFocus();
            return;
        } else {
            if (Validate.checkLength(sdt, 15)) {
                MsgBox.aleart(this, "Số điện thoại tối da 15 ký tự !");
                txtSDTKh.requestFocus();
                return;
            }
            if (!Validate.isPhoneNumber(sdt)) {
                MsgBox.aleart(this, "Số điện thoại đinh dạng !");
                txtSDTKh.requestFocus();
                return;
            }
        }
        KhachHang khachHang = khachHangRepositoryM.findKhBySDT(sdt);
        String tenKH = "";

        if (khachHang.getId() == null) {

            boolean kqHoi = MsgBox.confirm(this, "Khách hàng ko tồn tại . \n Bạn có muốn tạo mới khách hàng ");
            if (kqHoi) {
                tenKH = JOptionPane.showInputDialog("Xin mời bạn nhập tên khách hàng !!!");
                if (tenKH.isEmpty()) {
                    MsgBox.aleart(this, "Tên khách hàng chưa có !");
                    return;
                } else {
                    if (Validate.checkLength(tenKH, 50)) {
                        MsgBox.aleart(this, "Tên khách hàng tối da 50 ký tự !");
                        return;
                    }
                    if (!Validate.isName(tenKH)) {
                        MsgBox.aleart(this, "Tên khách hàng sai đinh dạng !");
                        return;
                    }
                }

                int maxIDKH = khachHangRepositoryM.getRowCountKH() + 1;
                String maKH = "";
                if (maKH.isEmpty()) {
                    if (maxIDKH < 10) {
                        maKH = "KH" + "00" + maKH;
                    } else if (maxIDKH < 100) {
                        maKH = "KH" + "0" + maxIDKH;
                    } else {
                        maKH = "KH" + maxIDKH;
                    }
                }
                khachHang.setIdNV(nhanVien);
                khachHang.setTenKhachHang(tenKH);
                khachHang.setSdt(sdt);
                khachHang.setMaKhachHang(maKH);
                int kqInsert = khachHangRepositoryM.insertKH_BH(khachHang);
                if (kqInsert != -1) {
                    MsgBox.aleart(this, "tạo thành công 1 khách hàng");
                    khachHang = khachHangRepositoryM.findKhBySDT(sdt);
                    System.out.println(khachHang);
                    defaultKhachHang = khachHang;
                    setLblCapBac(khachHang);
                }
            }

        } else {
            defaultKhachHang = khachHang;
            setLblCapBac(khachHang);
            //  System.out.println("chayj2");
        }

        indexHD = tblHD.getSelectedRow();
        if (indexHD != -1) {
            String maHD = tblHD.getValueAt(indexHD, 1).toString();
            hoaDon_MRepository.updateHDBy(defaultKhachHang, maHD);
            listHD = hoaDon_MRepository.getAllHDByTrangThai2(0, Auth.nv.getId());
            fillToTableHD(listHD);
        }
        if (defaultKhachHang != null) {
            setLblCapBac(defaultKhachHang);
        }
    }

    private boolean tinhTienThua() {
        String tienMatStr = "0";
        String tienCkStr = "0";
        int cbo = cboHTTT.getSelectedIndex();
        System.out.println("rave " + cbo + " dddd");
        tienMatStr = "0" + txtTienKhachDua.getText().trim();
        tienCkStr = "0" + txtChuyenKhoan.getText().trim();
        String ttStr = txtThanhTien.getText().trim();
        BigDecimal tienKD = new BigDecimal(0);
        BigDecimal tienCK = new BigDecimal(0);
        BigDecimal tienTTKH = new BigDecimal(0);
        String maHD = txtMHD.getText().trim();
        if (maHD.isEmpty()) {
            MsgBox.aleart(this, "Chưa thấy mã hóa đơn");
            return true;
        }
        int trangThai = hoaDon_MRepository.getTrangThaiHD(maHD);
        if (trangThai == 2) {
            return false;
        }
        try {
            tienKD = new BigDecimal(tienMatStr);
            tienCK = new BigDecimal(tienCkStr);

        } catch (Exception e) {
            MsgBox.aleart(this, "Số tiền bạn nhập phải là số !!!");
            return true;
        }
        ttStr = ttStr.replaceAll(",", "");
        BigDecimal tongTien = new BigDecimal(ttStr);
        BigDecimal tienThua = new BigDecimal(0);
        if (cbo == 0) {
            if (tienKD.compareTo(BigDecimal.ZERO) < 0) {
                MsgBox.aleart(this, "Số tiền lớn hơn 0 !!!");
                return true;
            }
            if (tienKD.compareTo(tongTien) >= 0) {
                tienTTKH = tienKD.add(tienCK);
                tienThua = tienTTKH.subtract(tongTien);
            }

        } // CK
        else if (cbo == 1) {

            if (tienCK.compareTo(BigDecimal.ZERO) < 0) {
                MsgBox.aleart(this, "Số tiền lớn hơn 0 !!!");
                return true;
            }
            if (tienCK.compareTo(tongTien) >= 0) {
                tienTTKH = tienCK.add(tienKD);
                tienThua = tienTTKH.subtract(tongTien);
            }

        } else {

            if (tienCK.compareTo(BigDecimal.ZERO) < 0 || tienKD.compareTo(BigDecimal.ZERO) < 0) {
                MsgBox.aleart(this, "Số tiền lớn hơn 0 !!!");
                return true;
            }

            tienTTKH = tienKD.add(tienCK);
            tienThua = tienTTKH.subtract(tongTien);

        }
        if (tienThua.compareTo(BigDecimal.ZERO) >= 0) {
            System.out.println("rav orm_BanHang.tinhTienThua()" + tienTTKH);
            txtThua.setText(Format.format1(tienThua));
        }

        if (tienTTKH.compareTo(tongTien) >= 0) {
            return false;
        } else {
            return true;
        }

        //return false;
    }

//    private void deleteGHBySelected() {
//        String maHD = "";
//        // update lai so luong
//        for (ChiTietHoaDon chiTietHoaDon : getGioHang()) {
//            Long idHD = hoaDon_MRepository.findIDByMaHD(chiTietHoaDon.getIdHoaDon().getMaHoaDon());
//            maHD = chiTietHoaDon.getIdHoaDon().getMaHoaDon();
//            // int soLuongSPGH = tblGH.getValueAt(ERROR, lmit)
//            chiTietHoaDon_Repository.deleteHDCT(chiTietHoaDon.getIdCTSP().getIdSPCT(), idHD);
//        }
//        fillToTableGH(chiTietHoaDon_Repository.getAllHDCT(maHD));
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        btnXoaHD = new javax.swing.JButton();
        btnTapHD = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblDonTreo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGH = new javax.swing.JTable();
        ckbAll = new javax.swing.JCheckBox();
        btnXoaGioiHang = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        btnDau = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        lblPageTTKH = new javax.swing.JLabel();
        btnTien = new javax.swing.JButton();
        btnCuoi = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        cboTenGiay = new javax.swing.JComboBox<>();
        cboMauSac = new javax.swing.JComboBox<>();
        cboThuongHieu = new javax.swing.JComboBox<>();
        cboKichThuoc = new javax.swing.JComboBox<>();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenKh = new javax.swing.JTextField();
        lblCapBac = new javax.swing.JLabel();
        btnThemNhanhKH = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblDiem = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSDTKh = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMHD = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNV = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblPhanTramHD = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cboHTTT = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        txtThua = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtChuyenKhoan = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        rdoDatHang = new javax.swing.JRadioButton();
        rdoTaiQuay = new javax.swing.JRadioButton();
        txtPhieuGiamGia = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        lblGiamDiem = new javax.swing.JLabel();
        txtDiem = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dlWeb = new javax.swing.JPanel();
        jpnWebcam = new javax.swing.JPanel();

        setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Số hóa đơn treo :");

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HD", "Ngày tạo", "Nhân viên", "Khách Hàng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHD);

        btnXoaHD.setText("Xóa");
        btnXoaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDActionPerformed(evt);
            }
        });

        btnTapHD.setText("Tạo hóa đơn");
        btnTapHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTapHDActionPerformed(evt);
            }
        });

        jButton2.setText("QR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        lblDonTreo.setBackground(new java.awt.Color(255, 0, 0));
        lblDonTreo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDonTreo.setForeground(new java.awt.Color(255, 51, 0));
        lblDonTreo.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 724, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDonTreo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTapHD)
                        .addGap(67, 67, 67)
                        .addComponent(btnXoaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnTapHD, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblDonTreo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(btnXoaHD)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Bán hàng");

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblGH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Đơn giá", "Số lượng", "Giảm giá", "Giá bán", "Thành tiền", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGH);

        ckbAll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ckbAll.setText("Tất cả");
        ckbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbAllActionPerformed(evt);
            }
        });

        btnXoaGioiHang.setText("Xóa");
        btnXoaGioiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaGioiHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckbAll)
                    .addComponent(btnXoaGioiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ckbAll)
                        .addGap(20, 20, 20)
                        .addComponent(btnXoaGioiHang)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Chi tiết hóa đơn");

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jButton1.setText("Tim");

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Má CTSP", "Tên SP", "Màu Sắc", "Size", "Hãng", "SL tồn", "Đơn giá", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSP);

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

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tên Sản Phẩm");

        cboTenGiay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Tên Sản Phẩm --" }));
        cboTenGiay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenGiayItemStateChanged(evt);
            }
        });

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
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
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
                .addGap(170, 170, 170))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboTenGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144))))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel14)
                    .addContainerGap(1039, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTenGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDau)
                    .addComponent(btnLui)
                    .addComponent(btnCuoi)
                    .addComponent(btnTien)
                    .addComponent(lblPageTTKH))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(141, 141, 141)
                    .addComponent(jLabel14)
                    .addContainerGap(165, Short.MAX_VALUE)))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Danh sách sản phẩm");

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Mã KH :");

        txtMaKH.setEditable(false);

        jLabel7.setText("Tên KH :");

        txtTenKh.setEditable(false);

        lblCapBac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCapBac.setForeground(new java.awt.Color(255, 0, 0));
        lblCapBac.setText("Cấp bậc");

        btnThemNhanhKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemNhanhKH.setText("+");
        btnThemNhanhKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanhKHActionPerformed(evt);
            }
        });

        jLabel9.setText("Điểm KH :");

        lblDiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDiem.setText("0");

        jLabel8.setText("SDT :");

        txtSDTKh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTKhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDTKh, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblCapBac, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThemNhanhKH))
                    .addComponent(lblDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCapBac))
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemNhanhKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSDTKh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblDiem)))
        );

        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setText("Mã hóa đơn :");

        txtMHD.setEditable(false);

        jLabel12.setText("Tên nhân viên :");

        txtNV.setEditable(false);

        jLabel13.setText("Tổng tiền :");

        txtTongTien.setEditable(false);

        jLabel20.setText("Phiếu GG :");

        jLabel21.setText("% giảm HD :");

        lblPhanTramHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPhanTramHD.setText("0%");

        jLabel19.setText("HT thanh toán :");

        cboHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt ", "Chuyển Khoản", "Tiền mắt vs chuyển khoản" }));
        cboHTTT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboHTTTItemStateChanged(evt);
            }
        });
        cboHTTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHTTTActionPerformed(evt);
            }
        });

        jLabel22.setText("Tiền Thừa :");

        txtThua.setEditable(false);
        txtThua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThuaActionPerformed(evt);
            }
        });

        jLabel24.setText("Tiền khách CK  :");

        txtChuyenKhoan.setEditable(false);
        txtChuyenKhoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtChuyenKhoanKeyPressed(evt);
            }
        });

        jLabel25.setText("Thành Tiền :");

        txtThanhTien.setEditable(false);
        txtThanhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhTienActionPerformed(evt);
            }
        });

        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        jLabel26.setText("Tiền khách đưa :");

        jLabel27.setText("Hình thức bán :");

        buttonGroup1.add(rdoDatHang);
        rdoDatHang.setText("Đặt hàng");
        rdoDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDatHangActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoTaiQuay);
        rdoTaiQuay.setSelected(true);
        rdoTaiQuay.setText("Tại quầy");
        rdoTaiQuay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTaiQuayActionPerformed(evt);
            }
        });

        txtPhieuGiamGia.setEditable(false);
        txtPhieuGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPhieuGiamGiaMouseClicked(evt);
            }
        });

        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        lblGiamDiem.setText("0");

        txtDiem.setEditable(false);
        txtDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiemActionPerformed(evt);
            }
        });
        txtDiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiemKeyReleased(evt);
            }
        });

        jLabel18.setText("Giảm điểm :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThanhToan)
                .addGap(102, 102, 102))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtThua, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTienKhachDua))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNV, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                    .addComponent(txtMHD)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(35, 35, 35)
                                .addComponent(lblPhanTramHD, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel20))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                    .addComponent(txtPhieuGiamGia)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(38, 38, 38)
                                .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGiamDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel27))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(rdoTaiQuay)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoDatHang))
                                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtMHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lblPhanTramHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lblGiamDiem)
                    .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(rdoDatHang)
                    .addComponent(rdoTaiQuay))
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(34, 34, 34)
                .addComponent(btnThanhToan)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Thông tin đơn hàng");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tab.addTab("Thiết lập thông tin đơn hàng", jPanel5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 312, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 750, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Đơn hàng");

        dlWeb.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quét mã vạch sản phẩm và phiếu gg", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jpnWebcam.setBackground(new java.awt.Color(255, 255, 255));
        jpnWebcam.setMaximumSize(new java.awt.Dimension(50, 50));
        jpnWebcam.setMinimumSize(new java.awt.Dimension(50, 50));
        jpnWebcam.setPreferredSize(new java.awt.Dimension(50, 50));
        jpnWebcam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout dlWebLayout = new javax.swing.GroupLayout(dlWeb);
        dlWeb.setLayout(dlWebLayout);
        dlWebLayout.setHorizontalGroup(
            dlWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dlWebLayout.setVerticalGroup(
            dlWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnWebcam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1))
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dlWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(596, 596, 596))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dlWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemNhanhKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanhKHActionPerformed

        Form_KhachHangJDialogManh form_KhachHangJDialog = new Form_KhachHangJDialogManh(new Application(), true);
        form_KhachHangJDialog.setVisible(true);
        defaultKhachHang = form_KhachHangJDialog.getSelectedKhachHang();

        indexHD = tblHD.getSelectedRow();
        if (indexHD != -1) {
            String maHD = tblHD.getValueAt(indexHD, 1).toString();
            hoaDon_MRepository.updateHDBy(defaultKhachHang, maHD);
            listHD = hoaDon_MRepository.getAllHDByTrangThai2(0, Auth.nv.getId());

            fillToTableHD(listHD);
        }
        if (defaultKhachHang != null) {
            setLblCapBac(defaultKhachHang);
        }


    }//GEN-LAST:event_btnThemNhanhKHActionPerformed

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked
        indexHD = tblHD.getSelectedRow();
        if (indexHD == -1) {
            MsgBox.aleart(this, "Hãy chọn 1 hóa đơn");
            return;
        }
        int index = tblSP.getSelectedRow();
        int soLuongTbl = Integer.parseInt(tblSP.getValueAt(index, 5).toString());
        String maSp = tblSP.getValueAt(index, 0).toString();

        SanPhamChiTiet sanPhamChiTiet = list.get(index);
        // System.out.println(" " + sanPhamChiTiet.getMaSPCT());
        String maHD = "";

        maHD = tblHD.getValueAt(indexHD, 1).toString();
        int soLuong = 0;
        String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng !!!");
        int soSum = hoaDon_MRepository.getSoLuongGH(maHD, maSp);
        System.out.println("so luo  sum " + soSum);
        try {
            soLuong = Integer.parseInt(soLuongStr);
            if (soLuong > soLuongTbl) {
                MsgBox.aleart(this, "Số lượng sản phẩm không đủ !!!");
                return;
            }
            if ((soLuong + soSum) > soLuongTbl) {
                MsgBox.aleart(this, "Số lượng sản phẩm không đủ !!!");
                return;
            }
            if (soLuong <= 0) {
                MsgBox.aleart(this, "Số lượng sản phẩm lớn hơn 0 !!!");
                return;
            }
        } catch (Exception e) {
            MsgBox.aleart(this, "Số lượng sản phải là số !!!");
            return;
        }

        long idHD = hoaDon_MRepository.getIDHDByMaHD(maHD);
        HoaDon hd = new HoaDon();
        hd.setId(idHD);

        DotGiamGia_M dgg = dotGiamGia_MRpository.getDGG_BH(maSp);
        BigDecimal quyDoi = BigDecimal.ZERO, giaBanB = BigDecimal.ZERO, donGiaB = BigDecimal.ZERO;
        giaBanB = sanPhamChiTiet.getGiaBan();
        donGiaB = sanPhamChiTiet.getGiaNiemYet();
        quyDoi = donGiaB.subtract(giaBanB);
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
        chiTietHoaDon.setIdHoaDon(hd);
        chiTietHoaDon.setIdCTSP(sanPhamChiTiet);
        chiTietHoaDon.setSoLuong(soLuong);
        chiTietHoaDon.setMaDGG(dgg.getMaDGG());
        chiTietHoaDon.setLoaiDGG(dgg.getHinhThucDGG());
        chiTietHoaDon.setQuyDoiDGGTT(quyDoi);
        chiTietHoaDon.setGiaTriDGG(dgg.getGiaTri());
        chiTietHoaDon.setDonGia(donGiaB);
        chiTietHoaDon.setGiaBan(giaBanB);

        BigDecimal tt = BigDecimal.ZERO;
        tt = sanPhamChiTiet.getGiaBan().multiply(new BigDecimal(soLuong));
        chiTietHoaDon.setThanhTien(tt);
        //chiTietHoaDon_Repository.insertCTHD(chiTietHoaDon);
//        int soSum = hoaDon_MRepository.getSoLuongGH(maHD, maSp);
        if (!listHD.isEmpty()) {
            hoaDon_MRepository.updateSLGH(list.get(index).getIdSPCT(), soLuong + soSum);
        }
        if (soSum == 0) {
            chiTietHoaDon_Repository.insertCTHD(chiTietHoaDon);
        }

        listGH = chiTietHoaDon_Repository.getAllHDCT(maHD);
        fillToTableGH(listGH);
        int slTru = soLuongTbl - soLuong;
        // chiTietSanPham_Repository.updateSLSPByMa(maSp, slTru);
        list = chiTietSanPham_Repository.get3(page, lmit);
        fillToTableSP(list);
        setFormTT(indexHD);

//        listHD = hoaDon_MRepository.getAllHDByTrangThai(0);
//        fillToTableHD(listHD);

    }//GEN-LAST:event_tblSPMouseClicked

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        indexHD = tblHD.getSelectedRow();
        if (indexHD == -1) {
            return;
        }
        String maHD = tblHD.getValueAt(indexHD, 1).toString();
        listGH = chiTietHoaDon_Repository.getAllHDCT(maHD);
        fillToTableGH(listGH);
        setFormTT(indexHD);
    }//GEN-LAST:event_tblHDMouseClicked

    private void txtThuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThuaActionPerformed

    private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThanhTienActionPerformed

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

    private void btnXoaGioiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaGioiHangActionPerformed
        getTable();
    }//GEN-LAST:event_btnXoaGioiHangActionPerformed

    private void btnXoaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDActionPerformed
        if (indexHD == -1) {
            return;
        }
        for (int i = 0; i < tblGH.getRowCount(); i++) {
            tblGH.setValueAt(true, i, 8);
        }
        getTable();
    }//GEN-LAST:event_btnXoaHDActionPerformed

    private void cboHTTTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboHTTTItemStateChanged

    }//GEN-LAST:event_cboHTTTItemStateChanged

    private HoaDon thanhToan() {
        HoaDon hoaDon = new HoaDon();

        try {
            String ttSP = "0" + txtTongTien.getText().trim().replaceAll(",", "");
            BigDecimal ttSPB = new BigDecimal(ttSP);
            String maHD = txtMHD.getText().trim();
            int capCap = defaultKhachHang.getCapBac();
            int ptt = cboHTTT.getSelectedIndex();
            int diemDoi = 0;
            try {
                diemDoi = Integer.parseInt(txtDiem.getText().trim());
            } catch (Exception e) {
            }
            //   ttStr = ttStr.replaceAll(",", "");
            String tienKHDuaStr = "0" + txtTienKhachDua.getText().trim();
            tienKHDuaStr = tienKHDuaStr.replaceAll(",", "");
            String tienChuyenKhoanStr = "0" + txtChuyenKhoan.getText().trim();
            tienChuyenKhoanStr = "0" + tienChuyenKhoanStr.replaceAll(",", "");
            String tienThuaStr = "0" + txtThua.getText().trim();
            String thanhTienStr = "0" + txtThanhTien.getText().trim().replaceAll(",", "");
            System.out.println(" TTT : " + thanhTienStr);
            tienThuaStr = tienThuaStr.replaceAll(",", "");
            BigDecimal tienKHDua = new BigDecimal(tienKHDuaStr);
            BigDecimal tienChuyenKhoan = new BigDecimal(tienChuyenKhoanStr);
            BigDecimal thanhTien = new BigDecimal(thanhTienStr);
            BigDecimal tongCK_TD = BigDecimal.ZERO;
            tongCK_TD = tienChuyenKhoan.add(tienKHDua);

            int trangThai = hoaDon_MRepository.getTrangThaiHD(maHD);
            if (trangThai == 0) {
                trangThai = 1;
            }

            if (cboHTTT.getSelectedIndex() == 0) {

                tongCK_TD = tienKHDua.add(tienChuyenKhoan);
            } else if (cboHTTT.getSelectedIndex() == 1 && new BigDecimal(tienChuyenKhoanStr).compareTo(new BigDecimal(thanhTienStr)) < 0) {
                tongCK_TD = tienKHDua.add(tienChuyenKhoan);
            } else if (cboHTTT.getSelectedIndex() == 2 && tongCK_TD.compareTo((thanhTien)) < 0) {
                tongCK_TD = tienKHDua.add(tienChuyenKhoan);
            }
            if (trangThai != 2) {
                if (tongCK_TD.compareTo(thanhTien) < 0) {
                    MsgBox.aleart(this, "Số tiền khách trả chưa đủ !!!");
                    return null;
                }
            }

            Date date = new Date();

            hoaDon.setMaHoaDon(maHD);
            hoaDon.setCapBac(capCap);
            hoaDon.setDiemDoi(new BigDecimal(diemDoi));
            hoaDon.setPhuongThucTT(ptt);
            hoaDon.setTienKhDua(new BigDecimal(tienKHDuaStr));
            hoaDon.setTienKhChuyenKhoan(new BigDecimal(tienChuyenKhoanStr));
            hoaDon.setTienThua(new BigDecimal(tienThuaStr));
            // hoaDon.setLoai(hhtm);
            hoaDon.setThanhTien(thanhTien);
            if (trangThai == 2 || trangThai == 3) {
                date = null;
            }
            hoaDon.setNgayThanhToan(date);
            hoaDon.setTrangThai(trangThai);
            hoaDon.setTongTienSP(ttSPB);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.aleart(this, "Lỗi thanh toán !!!");
            return null;
        }
        return hoaDon;
    }

    private void updateKh(int diem, BigDecimal thanhTien) {
        if (defaultKhachHang.getCapBac() == 3) {
            return;
        }
        BigDecimal tt = khachHangRepositoryM.getTongTienKH(defaultKhachHang.getMaKhachHang());
        System.out.println("------------- " + tt);
        int capBact = 0;
        if (tt.compareTo(new BigDecimal("5000000")) >= 0 && tt.compareTo(new BigDecimal("10000000")) < 0) {
            System.out.println(" ------------- Cap bac vaof 1 ");
            capBact = 1;
        } else if (tt.compareTo(new BigDecimal("10000000")) >= 0) {
            System.out.println(" ------------- Cap bac vaof 2 ");
            capBact = 2;
        }
        khachHangRepositoryM.updateCapBac(defaultKhachHang.getMaKhachHang(), capBact);
        khachHangRepositoryM.updateDiem(defaultKhachHang.getMaKhachHang(), defaultKhachHang.getDiem() - diem);
        int diemCong = 0;
        if (thanhTien.compareTo(new BigDecimal("3000000")) >= 0 && thanhTien.compareTo(new BigDecimal("5000000")) < 0) {
            System.out.println(" ------------- Cap bac vaof 1 ");
            diemCong = 20; // + điểm 
        } else if (tt.compareTo(new BigDecimal("5000000")) >= 0 && thanhTien.compareTo(new BigDecimal("8000000")) < 0) {
            System.out.println(" ------------- Cap bac vaof 2 ");
            diemCong = 40; // + diêm 
        } else if (tt.compareTo(new BigDecimal("10000000")) >= 0) {
            System.out.println(" ------------- Cap bac vaof 2 ");
            diemCong = 60; // + diêm 
        }
        Integer diemCuoi = (defaultKhachHang.getDiem() - diem) + diemCong;
        if (diemCuoi < 0) {
            diemCuoi = 0;
        }
        System.out.println("Diem " + diemCong + " diem còn lại " + (defaultKhachHang.getDiem() - diem) + diemCong);
        khachHangRepositoryM.updateDiem(defaultKhachHang.getMaKhachHang(), diemCuoi);
    }

    private void clearFormTT() {
        txtChuyenKhoan.setText("");
        txtDiem.setText("");
        txtMHD.setText("");
        txtPhieuGiamGia.setText("");
        txtThanhTien.setText("");
        txtThua.setText("");
        txtTienKhachDua.setText("");
        txtTongTien.setText("");
        lblGiamDiem.setText("");
        cboHTTT.setSelectedIndex(0);
        phieuGiamGia = new PhieuGiamGia();
        phieuGiaoHang = new PhieuGiaoHang();
    }
    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        if (indexHD == -1) {
            MsgBox.aleart(this, "Bạn hãy chọn 1 hóa đơn !!!");
            return;
        }
        System.out.println("fdsgfkds " + phieuGiaoHang.toString());
        if (listGH.isEmpty()) {
            MsgBox.aleart(this, "Hóa đơn chưa có sản phẩm nào !!!");
            return;
        }
        HoaDon hoaDon = thanhToan();
        if (hoaDon == null) {
            return;
        }

        if (tinhTienThua()) {
            System.out.println("chua du tien");
            return;
        }

        Integer soLuong = 0;
        try {
            Long idPGG = phieuGiamGia.getIdPGG();
            if (idPGG >= 0) {
                soLuong = phieuGiamGia.getSoLuongPhieu();
            }
            phieuGiamGiaService.updateSoLuongPhieu(idPGG, soLuong - 1);
            phieuGiamGiaService.updateKT(date);
        } catch (Exception e) {
        }

        for (int i = 0; i < tblGH.getRowCount(); i++) {
            String maSP = tblGH.getValueAt(i, 1).toString();
            Integer slGH = Integer.parseInt(tblGH.getValueAt(i, 04).toString());
            Integer soLuongTon = hoaDon_MRepository.getSoLuongTon(maSP);
            sanPhamCT_Repository.updateSLSP(soLuongTon - slGH, maSP);
        }

        int kq = hoaDon_MRepository.thanh_toanHD(hoaDon);
        if (kq == 1) {
            MsgBox.aleart(this, "Thánh toán thành công");
            listHD = hoaDon_MRepository.getAllHDByTrangThai2(0, Auth.nv.getId());
            fillToTableHD(listHD);
            updateKh(Integer.parseInt(hoaDon.getDiemDoi() + ""), hoaDon.getThanhTien());

            if (MsgBox.confirm(this, "Bạn có muốn in hóa đơn không ?")) {
                String path = "src\\bill";
                String ma = txtMHD.getText().trim();

                if (Impl.Bill.exportPdf(path, ma)) {
                    Email.sendFile(defaultKhachHang.getEmail(), "Cảm ơn bạn đã đặt hàng tại shop \n Đây là thông tin háo đơn của bạn: \n", "Hóa đơn", "src\\bill\\" + hoaDon.getMaHoaDon() + ".pdf",
                            "src\\qrbill\\" + hoaDon.getMaHoaDon() + ".png");
                    clearFormTT();
                }
            }
            list = sanPhamCT_Repository.get3(page, lmit);
            fillToTableSP(list);
            listGH.clear();
            fillToTableGH(listGH);
            lblDonTreo.setText(hoaDon_MRepository.getHoaDonTrao(0, Auth.nv.getId()) + "");
        } else {
            MsgBox.aleart(this, "Thánh toán không thành công");
        }


    }//GEN-LAST:event_btnThanhToanActionPerformed


    private void ckbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbAllActionPerformed
        for (int i = 0; i < tblGH.getRowCount(); i++) {
            tblGH.setValueAt(true, i, 8);
            String maHD = "";
        }
    }//GEN-LAST:event_ckbAllActionPerformed

    private void txtSDTKhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTKhActionPerformed
        insertKH();
    }//GEN-LAST:event_txtSDTKhActionPerformed

    private void rdoDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDatHangActionPerformed
        if (indexHD == -1) {
            MsgBox.aleart(this, "Hãy chọn hóa đơn");
            return;
        }
        long id = hoaDon_MRepository.getAllHDByTrangThai2(0, Auth.nv.getId()).get(indexHD).getId();
        View_TT_DatHang view_TT_DatHang = new View_TT_DatHang(new raven.application.Application(), true, id, defaultKhachHang.getId());
        view_TT_DatHang.setVisible(true);

        phieuGiaoHang = view_TT_DatHang.getPhieuGH();

        System.out.println("pppp" + phieuGiaoHang.toString());
    }//GEN-LAST:event_rdoDatHangActionPerformed

    private void btnTapHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTapHDActionPerformed

        int maxHD = hoaDon_MRepository.getRowCountHD();
        String maHD = "HD";
        Date date = new Date();
        String dateStr = XDate.toString(date, "yy-MM-ddHHmm");
        maHD += dateStr + maxHD;
        HoaDon hd = new HoaDon();
        hd.setIdKH(defaultKhachHang);
        hd.setIdNV(Auth.nv);
        hd.setIdPGG(phieuGiamGia);
        hd.setMaHoaDon(maHD);
//        Impl.ExportQr.exportQrHD("C:\\Users\\manhnt\\Desktop\\SuperSport-Sneakers\\Application\\Nhom4_SuperSportSneakers\\Qrbill", maHD);
        Impl.ExportQr.exportQrHD("src\\qrbill", maHD);
        hd.setQr(maHD + ".png");
        int kq = hoaDon_MRepository.create(hd);
        if (kq != -1) {

            MsgBox.aleart(this, "Tạo thành công hóa đơn : " + maHD);
            listHD = hoaDon_MRepository.getAllHDByTrangThai2(0, Auth.nv.getId());
            fillToTableHD(listHD);
            lblDonTreo.setText(hoaDon_MRepository.getHoaDonTrao(0, Auth.nv.getId()) + "");
            return;
        } else {
            MsgBox.aleart(this, "Tạo không thành công hóa đơn : " + maHD);
        }
    }//GEN-LAST:event_btnTapHDActionPerformed

    private void tblGHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGHMouseClicked
        demClick++;
        if (demClick > 2) {
            demClick = 0;
        }

        if (demClick == 2) {
            int indexGH = tblGH.getSelectedRow();
            String maHD = tblHD.getValueAt(indexHD, 1).toString();
            long IDHDCT = listGH.get(indexGH).getId();
            String maSP = tblGH.getValueAt(indexGH, 1).toString();

            int soLuongTon = chiTietHoaDon_Repository.getSLSPByMa(maSP).getSoLuong();
            System.out.println(maHD + " " + maSP + " " + IDHDCT + " ssl " + soLuongTon);
            Integer slSPGh = Integer.parseInt(tblGH.getValueAt(indexGH, 4).toString());
            Integer slInp = 0;
            Integer slInsert = 0;

            String sl = JOptionPane.showInputDialog("Xin mời bạn nhập lại số lượng !!!");

            try {
                slInp = Integer.parseInt(sl.trim());
                if (slInp <= 0) {
                    MsgBox.aleart(this, "Số lưởng phải lớn hơn 0 nếu muốn xóa hãy chọn và xóa !!");
                    return;
                }
                if ((slSPGh + soLuongTon) < slInp) {
                    MsgBox.aleart(this, "Số lượng sản phẩm ko đủ bán !!");
                    return;
                }

                if ((slSPGh) > soLuongTon || slInp > soLuongTon) {
                    MsgBox.aleart(this, "Số lượng sản phẩm ko đủ bán !!");
                    return;
                }

                if (slInp < slSPGh) {
                    slInsert = soLuongTon + (slSPGh - slInp);
                    System.out.println(slInsert + " Manh 1 =>" + maSP);
                    //  chiTietSanPham_Repository.updateSLSP(slInsert, maSP);
                    chiTietHoaDon_Repository.updateSL_Ban(IDHDCT, slInp);

                } else if (slInp > slSPGh) {
                    slInsert = soLuongTon - (slInp - slSPGh);
                    System.out.println(slInsert + " Manh 21 =>" + maSP);
                    //  chiTietSanPham_Repository.updateSLSP(slInsert, maSP);
                    chiTietHoaDon_Repository.updateSL_Ban(IDHDCT, slInp);
                } else {
                    demClick = 0;
                    System.out.println("SO In SL GH = nahu");
                    return;
                }
                demClick = 0;
                list = chiTietSanPham_Repository.get(page, lmit);
                fillToTableSP(list);
                listGH = chiTietHoaDon_Repository.getAllHDCT(maHD);
                fillToTableGH(listGH);
            } catch (Exception e) {
                MsgBox.aleart(this, "Số lương phải là số !!");
                demClick = 0;
                return;
            }
        }
    }//GEN-LAST:event_tblGHMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            if (indexWebcam == 0) {

                indexWebcam = 1;
                System.out.println("raven dong " + indexWebcam);
                if (webcam != null) {
                    if (webcam.isOpen()) {
                        webcam.close();
                        thread.stop();
                    }
                }
            } else if (indexWebcam == 1) {
                initWebcam();
                captureThread();
                indexWebcam = 0;
                System.out.println("raven mơ " + indexWebcam);
            }
        } catch (Exception e) {
            System.out.println("ra catch rformed()");
            if (webcam != null) {
                if (webcam.isOpen()) {
                    webcam.close();
                    thread.stop();
                }
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        List<SanPhamChiTiet> listSearch = sanPhamCT_Repository.search_SanPhamChiTiet(txtSearch.getText().trim());
        System.out.println(listSearch);
        fillToTableSP(listSearch);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void cboHTTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHTTTActionPerformed
        switch (cboHTTT.getSelectedIndex()) {
            case 0:

                txtChuyenKhoan.setText("");
                txtTienKhachDua.setEditable(true);
                txtChuyenKhoan.setEditable(false);
                break;
            case 1:
                txtChuyenKhoan.setEditable(true);
                txtTienKhachDua.setEditable(false);
                txtTienKhachDua.setText("");
                break;
            case 2:
                txtChuyenKhoan.setEditable(true);
                txtTienKhachDua.setEditable(true);
                break;
            default:
                break;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cboHTTTActionPerformed

    private void txtPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPhieuGiamGiaMouseClicked
        String ttStr = "0";
        ttStr = txtTongTien.getText().trim();
        ttStr = ttStr.replaceAll(",", "");
        System.out.println(ttStr);
        BigDecimal tt = new BigDecimal(ttStr);
        if (tt.compareTo(BigDecimal.ZERO) == 0) {
            MsgBox.aleart(this, "Bạn không có khuyến mãi nào !!");
            return;

        }
        ViewPhieuGiam viewPhieuGiam = new ViewPhieuGiam(new Application(), true, tt, txtMHD.getText());
        viewPhieuGiam.setVisible(true);
        phieuGiamGia = viewPhieuGiam.getPGGSelect();
        setFormTT(indexHD);
        System.out.println(phieuGiamGia.getTenPhieu());
    }//GEN-LAST:event_txtPhieuGiamGiaMouseClicked


    private void txtDiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiemKeyReleased

    }//GEN-LAST:event_txtDiemKeyReleased

    private void txtDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemActionPerformed
        String diemStr = "0";
        diemStr += txtDiem.getText().trim();
        String ttSTr = txtThanhTien.getText().trim();
        ttSTr = ttSTr.replaceAll(",", "");
        BigDecimal quyDiem = BigDecimal.ZERO;
        BigDecimal tt = new BigDecimal(ttSTr);
        int diemI = 0;
        try {
            diemI = Integer.parseInt(diemStr);
            if (diemI < 0) {
                MsgBox.aleart(this, "Điểm phải > 0 !!");
                return;
            }
            if (diemI > Integer.parseInt(lblDiem.getText().trim())) {
                MsgBox.aleart(this, "Số điểm không đủ \n Bạn có " + lblDiem.getText().trim() + " điểm .");
                return;
            }
            quyDiem = new BigDecimal(diemI).multiply(new BigDecimal(5000));
            lblGiamDiem.setText(Format.format(quyDiem) + "");

            tt = tt.subtract(quyDiem);
            if (tt.compareTo(BigDecimal.ZERO) < 0) {
                txtThanhTien.setText(Format.format1(new BigDecimal(0)));
            } else {
                // Xử lý khi thanhTien không nhỏ hơn 0
                txtThanhTien.setText(Format.format1(tt));
            }

        } catch (Exception e) {
            MsgBox.aleart(this, "Điểm bạn nhập phải là số !!");
            return;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        tinhTienThua();
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtChuyenKhoanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChuyenKhoanKeyPressed
        tinhTienThua();
    }//GEN-LAST:event_txtChuyenKhoanKeyPressed

    private void rdoTaiQuayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTaiQuayActionPerformed
        phieuGiaoHang = new PhieuGiaoHang();
    }//GEN-LAST:event_rdoTaiQuayActionPerformed

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

        List<SanPhamChiTiet> list = chiTietSanPham_Repository.searchItem(idMau, idKichThuoc, idThuongHieu, idSanPham, trangThai);
        fillToTableSP(list);
    }
    private void cboTenGiayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenGiayItemStateChanged
        initSearch();
    }//GEN-LAST:event_cboTenGiayItemStateChanged

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

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTrangThaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCuoi;
    private javax.swing.JButton btnDau;
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnTapHD;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemNhanhKH;
    private javax.swing.JButton btnTien;
    private javax.swing.JButton btnXoaGioiHang;
    private javax.swing.JButton btnXoaHD;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboHTTT;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboTenGiay;
    private javax.swing.JComboBox<String> cboThuongHieu;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JCheckBox ckbAll;
    private javax.swing.JPanel dlWeb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jpnWebcam;
    private javax.swing.JLabel lblCapBac;
    private javax.swing.JLabel lblDiem;
    private javax.swing.JLabel lblDonTreo;
    private javax.swing.JLabel lblGiamDiem;
    private javax.swing.JLabel lblPageTTKH;
    private javax.swing.JLabel lblPhanTramHD;
    private javax.swing.JRadioButton rdoDatHang;
    private javax.swing.JRadioButton rdoTaiQuay;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblGH;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtChuyenKhoan;
    private javax.swing.JTextField txtDiem;
    private javax.swing.JTextField txtMHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNV;
    private javax.swing.JTextField txtPhieuGiamGia;
    private javax.swing.JTextField txtSDTKh;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenKh;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtThua;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
