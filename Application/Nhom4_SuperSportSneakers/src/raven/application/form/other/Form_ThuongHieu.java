/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package raven.application.form.other;

import Model.KichThuoc;
import Model.MauSac;
import Model.ThuongHieu;
import Repository.KichThuoc_Repository;
import Repository.MauSac_Reponsitory;
import Repository.ThuongHieu_Repository;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

/**
 *
 * @author vutu8
 */
public class Form_ThuongHieu extends javax.swing.JPanel {

    DefaultTableModel tblModel = new DefaultTableModel();
    private static List<ThuongHieu> list = new ArrayList<>();
    private static ThuongHieu_Repository msr = new ThuongHieu_Repository();
    private static int page = 1;
    private static int gioiHanPage = (int) ((Math.ceil(msr.getRowCount() / 4)) + 1);

    public Form_ThuongHieu() {
        initComponents();
        list = msr.get(page, 5);
        fillToTable(list);
    }

    private void fillToTable(List<ThuongHieu> list) {
        tblModel = (DefaultTableModel) tblThuongHieu.getModel();
        tblModel.setRowCount(0);
        int n = 1;
        for (ThuongHieu i : list) {
            Object[] row = new Object[4];
            row[0] = n++;
            row[1] = i.getMaThuongHieu();
            row[2] = i.getTenThuongHieu();
            row[3] = (i.getTrangThai() == 0) ? "Còn Dùng" : "Không Dùng";
            tblModel.addRow(row);
        }
    }

    private KichThuoc getKichThuoc() {
        String ten = txtTen_ThuongHieu.getText();
        int index = tblThuongHieu.getSelectedRow();
        String ma;
        if (txtMa_ThuongHieu.getText().isEmpty()) {
            ma = msr.MaTuDong();
        } else if (index > 0) {
            ma = txtMa_ThuongHieu.getText();
        } else {
            return null;
        }
        Float tenSize = 0f;
        if (ten == null || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Vui lòng không để trống Màu Sắc.");
            return null;
        } else {
            try {
                tenSize = Float.parseFloat(ten);
            } catch (Exception e) {
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tên Size phải là số"
                        + "");
                return null;
            }
        }
        int trangThai = 1;
        if (rboCon.isSelected()) {
            trangThai = 0;
        } else if (rboKhong.isSelected()) {
            trangThai = 1;
        }
        System.out.println(trangThai);
        return new KichThuoc(ma, tenSize, trangThai);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        btnSauCuoi = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnDauCuoi = new javax.swing.JButton();
        soTrang = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThuongHieu = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rboCon_Search = new javax.swing.JRadioButton();
        rboKhong_search = new javax.swing.JRadioButton();
        txtSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMa_ThuongHieu = new javax.swing.JTextField();
        txtTen_ThuongHieu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rboCon = new javax.swing.JRadioButton();
        rboKhong = new javax.swing.JRadioButton();

        btnSauCuoi.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btnSauCuoi.setText("<<");
        btnSauCuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSauCuoiActionPerformed(evt);
            }
        });

        btnLui.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btnLui.setText("<");
        btnLui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuiActionPerformed(evt);
            }
        });

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnThem.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnClean.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btnClean.setText("LÀM MỚI");
        btnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClean, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(18, 18, 18)
                .addComponent(btnClean)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNext.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 3, 24)); // NOI18N
        jLabel1.setText("THÔNG TIN THƯƠNG HIỆU");

        btnDauCuoi.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btnDauCuoi.setText(">>");
        btnDauCuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDauCuoiActionPerformed(evt);
            }
        });

        soTrang.setText("     Số Trang");

        tblThuongHieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Màu ", "Màu Sắc", "Trạng Thái"
            }
        ));
        tblThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuongHieuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThuongHieu);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TÌM KIẾM THƯƠNG HIỆU", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 18))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel5.setText("Tìm Kiếm");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel6.setText("Trạng Thái");

        buttonGroup2.add(rboCon_Search);
        rboCon_Search.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        rboCon_Search.setText("Còn Sử Dụng");
        rboCon_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rboCon_SearchActionPerformed(evt);
            }
        });

        buttonGroup2.add(rboKhong_search);
        rboKhong_search.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        rboKhong_search.setText("Không Sử Dụng");
        rboKhong_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rboKhong_searchActionPerformed(evt);
            }
        });

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(jLabel6)
                .addGap(33, 33, 33)
                .addComponent(rboCon_Search)
                .addGap(43, 43, 43)
                .addComponent(rboKhong_search)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rboCon_Search)
                    .addComponent(rboKhong_search)
                    .addComponent(jLabel6))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(txtSearch)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Sitka Display", 3, 18)); // NOI18N
        jLabel2.setText("Mã Thương Hiệu");

        jLabel3.setFont(new java.awt.Font("Sitka Display", 3, 18)); // NOI18N
        jLabel3.setText("Trạng Thái");

        txtMa_ThuongHieu.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Sitka Display", 3, 18)); // NOI18N
        jLabel4.setText("Tên Thương Hiệu");

        buttonGroup1.add(rboCon);
        rboCon.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        rboCon.setText("Còn Sử Dụng");

        buttonGroup1.add(rboKhong);
        rboKhong.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        rboKhong.setText("Không Sử Dụng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtMa_ThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen_ThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rboCon)
                                .addGap(43, 43, 43)
                                .addComponent(rboKhong)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMa_ThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTen_ThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rboCon)
                    .addComponent(rboKhong))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(btnSauCuoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(soTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext)
                        .addGap(18, 18, 18)
                        .addComponent(btnDauCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(soTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSauCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDauCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSauCuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSauCuoiActionPerformed
        fillToTable(msr.get(1, 4));
    }//GEN-LAST:event_btnSauCuoiActionPerformed

    private void btnLuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuiActionPerformed
        page--;
        if (page >= 1) {
            soTrang.setText(page + " / " + gioiHanPage);
            fillToTable(msr.get(page, 4));
        } else {
            page = 1;
        }
    }//GEN-LAST:event_btnLuiActionPerformed
    private ThuongHieu getThuongHieu() {

        String ten = txtTen_ThuongHieu.getText();
        int index = tblThuongHieu.getSelectedRow();
        String ma;
        if (txtMa_ThuongHieu.getText().isEmpty()) {
            ma = msr.MaTuDong();
        } else if (index >= 0) {
            ma = txtMa_ThuongHieu.getText();
        } else {
            return null;
        }

        if (ten == null || ten.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Vui lòng không để trống Màu Sắc.");
            return null;
        }
        int trangThai;
        if (rboCon.isSelected()) {
            trangThai = 0;
        } else if (rboKhong.isSelected()) {
            trangThai = 1;
        } else {
            return null;
        }
        return new ThuongHieu(ma, ten, trangThai);
    }
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        ThuongHieu ms = getThuongHieu();
        if (ms == null) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Xảy ra lỗi vui lòng kiểm tra lại.");
            return;
        } else {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm thương hiệu không ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                // Người dùng chọn "Có"
                msr.addThuongHieu(ms);
                list = msr.getToAll();
                fillToTable(list);
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã thêm thành công thương hiệu mới.");
            } else if (check == JOptionPane.NO_OPTION) {
                // Người dùng chọn "Không"
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã thoát thêm thương hiệu.");
                return;
            } else {
                // Người dùng đóng hoặc đặt lại hộp thoại
                System.out.println("Hộp thoại đã đóng hoặc đặt lại.");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        ThuongHieu ms = getThuongHieu();

        if (ms == null) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Xảy ra lỗi vui lòng kiểm tra lại.");
            return;
        } else {
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa thương hiệu không ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                // Người dùng chọn "Có"
                String ma = txtMa_ThuongHieu.getText();
                msr.updateThuongHieu(ms, ma);
                list = msr.getToAll();
                fillToTable(list);
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã update thành công màu sắc.");
            } else if (check == JOptionPane.NO_OPTION) {
                // Người dùng chọn "Không"
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã thoát sửa màu sắc.");
                return;
            } else {
                // Người dùng đóng hoặc đặt lại hộp thoại
                System.out.println("Hộp thoại đã đóng hoặc đặt lại.");
            }
    }//GEN-LAST:event_btnSuaActionPerformed
    }
    private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanActionPerformed
        txtMa_ThuongHieu.setText("");
        txtTen_ThuongHieu.setText("");
        rboCon.setSelected(true);
        rboCon_Search.setSelected(true);
    }//GEN-LAST:event_btnCleanActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        page++;
        if (page <= gioiHanPage) {
            fillToTable(msr.get(page, 4));
            soTrang.setText(page + " / " + gioiHanPage);
            return;
        }
        page = gioiHanPage;
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnDauCuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDauCuoiActionPerformed
        fillToTable(msr.get(gioiHanPage, 4));
    }//GEN-LAST:event_btnDauCuoiActionPerformed

    private void tblThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuongHieuMouseClicked
        int index = tblThuongHieu.getSelectedRow();
        if (index < 0) {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Vui lòng chọn dòng cần hiện thông tin.");
            return;
        } else {
            txtMa_ThuongHieu.setText((String) tblThuongHieu.getValueAt(index, 1));
            txtTen_ThuongHieu.setText((String) tblThuongHieu.getValueAt(index, 2));
            if (tblThuongHieu.getValueAt(index, 3).equals("Còn Dùng")) {
                rboCon.setSelected(true);
            } else {
                rboKhong.setSelected(true);
            }
        }
    }//GEN-LAST:event_tblThuongHieuMouseClicked

    private void rboCon_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rboCon_SearchActionPerformed
        List<ThuongHieu> list2 = msr.search_MauSacByTrangThai(0);
        fillToTable(list2);
    }//GEN-LAST:event_rboCon_SearchActionPerformed

    private void rboKhong_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rboKhong_searchActionPerformed
        List<ThuongHieu> list3 = msr.search_MauSacByTrangThai(1);
        fillToTable(list3);
    }//GEN-LAST:event_rboKhong_searchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        List<ThuongHieu> listMauSearch = msr.search_MauSac(txtSearch.getText().trim());
        fillToTable(listMauSearch);
    }//GEN-LAST:event_txtSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClean;
    private javax.swing.JButton btnDauCuoi;
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSauCuoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rboCon;
    private javax.swing.JRadioButton rboCon_Search;
    private javax.swing.JRadioButton rboKhong;
    private javax.swing.JRadioButton rboKhong_search;
    private javax.swing.JLabel soTrang;
    private javax.swing.JTable tblThuongHieu;
    private javax.swing.JTextField txtMa_ThuongHieu;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTen_ThuongHieu;
    // End of variables declaration//GEN-END:variables
}
