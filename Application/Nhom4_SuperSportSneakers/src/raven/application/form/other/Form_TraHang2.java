/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.ChiTietHoaDon;
import Model.HoaDon;
import Model.NhanVien;
import Model.PhieuTra;
import Model.PhieuTraChiTiet;
import Model.SanPhamChiTiet;
import Repository.PhieuTraChiTietRepository;
import Repository.PhieuTraRepository;
import Utils.Auth;
import Utils.Format;
import Utils.MsgBox;
import Utils.XDate;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import static raven.application.form.other.Form_BanHang.indexWebcam;

/**
 *
 * @author manhnt
 */
public class Form_TraHang2 extends javax.swing.JPanel implements Runnable, ThreadFactory {

    private PhieuTraRepository phieuTraRepository = new PhieuTraRepository();
    private PhieuTraChiTietRepository phieuTraChiTietRepository = new PhieuTraChiTietRepository();
    private List<ChiTietHoaDon> listCTHD = new ArrayList<>();

    private List<PhieuTraChiTiet> listCT = new ArrayList<>();
    //

    private NhanVien nhanVien = Auth.nv;
    //Scan QR
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private Thread thread;

    JPanel jpnWebcam = new JPanel();
    JDialog dlScanQr = new JDialog();

    private HoaDon hoaDon = new HoaDon();
    private static List<SanPhamChiTiet> listSP = new ArrayList<>();
    private static List<PhieuTra> list = new ArrayList<>();

    /**
     * Creates new form Form_TraHang2
     */
    public Form_TraHang2() {
        initComponents();
        list = phieuTraRepository.listPT("");
        fillToTablePT(list);
    }

    private void fillToTableHDCT(List<ChiTietHoaDon> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblHDCT.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (ChiTietHoaDon cthd : list) {
            dtm.addRow(cthd.rowDataTraHang(i));
            i++;
        }
    }

    private void fillToTableCTPT(List<PhieuTraChiTiet> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblCt.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (PhieuTraChiTiet cthd : list) {
            dtm.addRow(cthd.rowData(i));
            i++;
        }
    }

    private void fillToTablePT(List<PhieuTra> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblPT.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (PhieuTra cthd : list) {
            dtm.addRow(cthd.rowData(i));
            i++;
        }
    }

    private void fillToTableTra(List<SanPhamChiTiet> list) {

        DefaultTableModel dtm = (DefaultTableModel) this.tblCT_PTH.getModel();
        dtm.setRowCount(0);
        int i = 1;
        for (SanPhamChiTiet sanPhamChiTiet : list) {
            dtm.addRow(sanPhamChiTiet.rowTraHang(i));
            i++;
        }
        tinhTienTra();
    }

    private void tinhTienTra() {
        BigDecimal tt = BigDecimal.ZERO;
        for (int i = 0; i < tblCT_PTH.getRowCount(); i++) {
            String gia1 = tblCT_PTH.getValueAt(i, 8).toString();
            String giaCatChuoi = gia1.replaceAll("[^0-9]", "");
            giaCatChuoi = giaCatChuoi.replaceAll("[^0-9]+$", "");
            try {
                BigDecimal giaBigDecimal = new BigDecimal(giaCatChuoi);
                tt = tt.add(giaBigDecimal);
                txtTienHoan.setText(Format.format(tt));
            } catch (NumberFormatException e) {
                System.err.println("Không thể chuyển đổi thành số nguyên.");
            }
        }
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
        int i = 0;
        thread = new Thread() {
            @Override
            public void run() {
                System.out.println(".run()" + i);
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

                        listCTHD = phieuTraRepository.getHDCTByMaHD(resultText);
                        fillToTableHDCT(listCTHD);
                        hoaDon = phieuTraRepository.getThongTinHD(resultText);
                        setForm(hoaDon);
                        dlScanQr.setVisible(false);
                        webcam.close();
                        thread.stop();
                    }

                } while (true);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void setForm(HoaDon hoaDon) {
        txtMaHD.setText(hoaDon.getMaHoaDon());
        txtTen2.setText(hoaDon.getIdKH().getTenKhachHang());
        txtTenKH.setText(hoaDon.getIdKH().getTenKhachHang());
        txttongTien.setText(Format.format(hoaDon.getTongTienSP()));
        txtNgay.setText(XDate.toString(hoaDon.getNgayThanhToan(), "dd-MM-yyyy"));
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
        jLabel2 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtNgay = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        txttongTien = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtTen2 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        txtTienHoan = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCT_PTH = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPT = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCt = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtTim2 = new javax.swing.JTextField();

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tìm kiếm hóa đơn : ");

        txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKeyReleased(evt);
            }
        });

        jButton1.setText("Quét Mã QR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Stop");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setText("Trả tất cả ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Thông tin trả hàng ");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel15.setText("Mã HD :");

        txtMaHD.setText(" ");

        jLabel16.setText("Tên KH :");

        txtTenKH.setText(" ");

        jLabel17.setText("Ngày TT :");

        txtNgay.setText(" ");

        jLabel18.setText("Tồng tiền :");

        txttongTien.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator14)
                            .addComponent(txttongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator11)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator13)
                                .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator12)
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtMaHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTenKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNgay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txttongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Thanh toán ");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel19.setText("Tên KH :");

        txtTen2.setText(" ");

        jLabel20.setText("Tiền trả lại :");

        txtTienHoan.setText(" ");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        jLabel21.setText("Ghi chú :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTen2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTienHoan, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTen2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTienHoan)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jButton5.setText("Xác nhận");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã CTSP", "Tên SP", "Số Lượng", "Giá Bán", "Tổng Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHDCT);

        tblCT_PTH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTSP", "Tên SP", "Hãng", "Màu Sắc", "Size", "Số Lượng", "Giá Bán", "Thành Tiền", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCT_PTH);

        jLabel1.setText("Chi tiết hóa đơn :");

        jLabel4.setText("Danh sách sản phẩm trả :");

        jButton6.setText("Xóa");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Chọn tất cả");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton7.setText("Làm mới");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTim, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(88, 88, 88)
                        .addComponent(jButton4)
                        .addGap(106, 106, 106)
                        .addComponent(jButton3)
                        .addGap(120, 120, 120)
                        .addComponent(jButton7)
                        .addGap(292, 292, 292))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addComponent(jLabel8)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(jButton5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(804, 804, 804))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox1)
                                        .addGap(37, 37, 37)
                                        .addComponent(jButton6))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 3, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(27, 27, 27))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton7))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jButton6)
                            .addComponent(jCheckBox1))
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(159, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Trả hàng", jPanel1);

        tblPT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Phiếu", "Mã Hóa Đơn", "Ngày Trả", "Tiền Hoàn Trả", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPTMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblPT);

        jLabel5.setText("Danh sách phiếu trả");

        jLabel6.setText("Chi tiết phiếu trả");

        tblCt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTSP", "Tên SP", "Màu", "Thương Hiệu", "Size", "Số Lượng Trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblCt);

        jLabel7.setText("Tìm kiếm :");

        txtTim2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTim2KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtTim2, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1384, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1384, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTim2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jTabbedPane1.addTab("Lịch sử trả hàng", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyReleased
        String maHD = txtTim.getText().trim();
        listCTHD = phieuTraRepository.getHDCTByMaHD(maHD);
        fillToTableHDCT(listCTHD);
        hoaDon = phieuTraRepository.getThongTinHD(maHD);
        setForm(hoaDon);
    }//GEN-LAST:event_txtTimKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
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
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
        int index = tblHDCT.getSelectedRow();
        String maSp = tblHDCT.getValueAt(index, 1).toString();
        Integer soHD = Integer.parseInt(tblHDCT.getValueAt(index, 3).toString());
        Integer soLuongtra = 0;
        String soLuongTraStr = JOptionPane.showInputDialog("Xin mời bạn nhập số lượng cần trả");
        try {
            soLuongtra = Integer.parseInt("0" + soLuongTraStr);
            if (soLuongtra <= 0) {
                MsgBox.aleart(this, "Số lượng trả  phải > 0 !!");
                return;
            }

            if (soLuongtra > soHD) {
                MsgBox.aleart(this, "Số lượng trả  > số lượng trong hóa đơn !!");
                return;
            }

        } catch (Exception e) {
            MsgBox.aleart(this, "Số lượng phải là số !!");
            return;
        }

        for (int i = 0; i < listSP.size(); i++) {
            if (maSp.endsWith(listSP.get(i).getMaSPCT())) {
                int soLc = listSP.get(i).getSoLuong();
                int soMoi = soLc + soLuongtra;
                if (soMoi > soHD) {
                    MsgBox.aleart(this, "Tổng số lượng trả của SP : " + maSp + " > số lượng trong hóa đơn");
                    return;
                }
                listSP.get(i).setSoLuong(soMoi);
                fillToTableTra(listSP);
                return;
            }
        }
        SanPhamChiTiet sanPhamChiTiet = phieuTraRepository.getSPTraHang(maSp, soLuongtra);
        listSP.add(sanPhamChiTiet);
        fillToTableTra(listSP);

    }//GEN-LAST:event_tblHDCTMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String ma = "PT";
        int maxHD = phieuTraRepository.getRowCount();

        Date date = new Date();
        String dateStr = XDate.toString(date, "yy-MM-ddHHmm");
        ma += dateStr + maxHD;

        String ghiChu = txtGhiChu.getText().trim();

        String gia1 = txtTienHoan.getText().trim();
        String giaCatChuoi = gia1.replaceAll("[^0-9]", "");
        giaCatChuoi = giaCatChuoi.replaceAll("[^0-9]+$", "");
        BigDecimal giaBigDecimal = BigDecimal.ZERO;
        try {
            giaBigDecimal = new BigDecimal(giaCatChuoi);
        } catch (NumberFormatException e) {
            System.err.println("Không thể chuyển đổi thành số nguyên.");
        }
        PhieuTra phieuTra = new PhieuTra();
        phieuTra.setIdHD(hoaDon);
        phieuTra.setIdNV(Auth.nv);
        phieuTra.setGhiChu(ghiChu);
        phieuTra.setMaPhieu(ma);
        phieuTra.setTienHoanTra(giaBigDecimal);
        phieuTraRepository.insertPT(phieuTra);
        Long idPt = phieuTraRepository.getIdPt(ma);
        int cout = 0;
        for (SanPhamChiTiet chiTiet : listSP) {
            phieuTraChiTietRepository.insert(chiTiet, idPt);
            cout++;
        }
        if (cout == listSP.size()) {
            MsgBox.aleart(this, "Tạo thành công phiếu tra ");
        }

        listSP.clear();
        fillToTableTra(listSP);


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        listSP.clear();
        for (int i = 0; i < tblHDCT.getRowCount(); i++) {
            String maSp = tblHDCT.getValueAt(i, 1).toString();
            Integer soHD = Integer.parseInt(tblHDCT.getValueAt(i, 3).toString());
            SanPhamChiTiet sanPhamChiTiet = phieuTraRepository.getSPTraHang(maSp, soHD);
            listSP.add(sanPhamChiTiet);
        }
        fillToTableTra(listSP);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        for (int i = listSP.size() - 1; i >= 0; i--) {
            Object ktr = tblCT_PTH.getValueAt(i, 9);
            if (ktr instanceof Boolean && (Boolean) ktr) {
                listSP.remove(i);
            }
        }

        fillToTableTra(listSP);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        for (int i = 0; i < tblCT_PTH.getRowCount(); i++) {
            tblCT_PTH.setValueAt(true, i, 9);

        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        listSP.clear();
        listCTHD.clear();
        fillToTableHDCT(listCTHD);
        fillToTableTra(listSP);
        txtTim.setText("");
        txtMaHD.setText("");
        txtGhiChu.setText("");
        txtTen2.setText("");
        txtTenKH.setText("");
        txtNgay.setText("");
        txtTienHoan.setText("");
        txttongTien.setText("");
        txtTienHoan.setText("");

    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtTim2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTim2KeyReleased
        String tt = txtTim2.getText().trim();
        list = phieuTraRepository.listPT(tt);
        fillToTablePT(list);
    }//GEN-LAST:event_txtTim2KeyReleased

    private void tblPTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPTMouseClicked
        int index = tblPT.getSelectedRow();
        Long id = list.get(index).getId();
        System.out.println(id);
        listCT = phieuTraChiTietRepository.listPT(id);
        fillToTableCTPT(listCT);
    }//GEN-LAST:event_tblPTMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblCT_PTH;
    private javax.swing.JTable tblCt;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblPT;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JLabel txtMaHD;
    private javax.swing.JLabel txtNgay;
    private javax.swing.JLabel txtTen2;
    private javax.swing.JLabel txtTenKH;
    private javax.swing.JLabel txtTienHoan;
    private javax.swing.JTextField txtTim;
    private javax.swing.JTextField txtTim2;
    private javax.swing.JLabel txttongTien;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Thread newThread(Runnable r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
