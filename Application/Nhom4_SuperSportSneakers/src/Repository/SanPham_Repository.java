/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.MauSac;
import Model.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SanPham_Repository {

    Connection connect = DBConnection.getConnect();

    public ArrayList<SanPham> getToAllSanPham() {
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        String query = "Select ID,MaSP, TenSP, TrangThai From SANPHAM";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listSanPham.add(new SanPham(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (Exception e) {
            System.out.println("Error at get to all sanPham");
        }
        return listSanPham;
    }

    public String MaTuDongSanPham() {
        String ma = "SP";
        int newTotal = 0;

        try {
            PreparedStatement ps = connect.prepareCall("SELECT COUNT(*) FROM SANPHAM");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                newTotal = rs.getInt(1) + 1;
            }

            // Sử dụng vòng lặp for để tạo mã sản phẩm tự động
            String[] prefixes = {"0000", "000", "00", "0"};
            int index = 0;

            for (int limit : new int[]{10, 100, Integer.MAX_VALUE}) {
                if (newTotal < limit) {
                    ma = ma + prefixes[index] + newTotal;
                    break;
                }
                index++;
            }

        } catch (Exception e) {
            System.out.println("Error at Key");
        }

        return ma;
    }

    public void addSanPham(SanPham sp) {

        String sql = "INSERT INTO SANPHAM (MaSP, TenSP,TrangThai) VALUES (?,?,?)";
        try {
            PreparedStatement ps = connect.prepareCall(sql);
            ps.setString(1, sp.getMaSanPham());
            ps.setString(2, sp.getTenSanpham());
            ps.setInt(3, sp.getTrangThai());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateSanPham(SanPham sp, String ma) {

        String sql = "UPDATE SANPHAM set TenSP = ?, TrangThai = ? where MaSP = ?";
        try {
            PreparedStatement ps = connect.prepareCall(sql);
            ps.setString(1, sp.getTenSanpham());
            ps.setInt(2, sp.getTrangThai());
            ps.setString(3, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<SanPham> search_SanPham(String text) {
        List<SanPham> listSearch = new ArrayList<>();
        String query = "SELECT  ID,MaSP, TenSP FROM SANPHAM WHERE MaSP LIKE ? OR TenSP LIKE ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new SanPham(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while searching for SanPham", e);
        }
        return listSearch;
    }

    public Boolean check(String ma) {
        String sql = "Select * From SANPHAM Where MaSP = ?";
        try {
            PreparedStatement pstm = connect.prepareCall(sql);
            pstm.setString(1, ma);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error at check");
            return false;
        }
        return false;
    }

    public List<SanPham> get1(int page, int limt) {
        List<SanPham> list = new ArrayList<>();
        String sql = "Select ID,MaSP, TenSP, TrangThai From SANPHAM " + "ORDER BY ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try {
            PreparedStatement pstm = connect.prepareStatement(sql);
            // Công thức chỉ cần ghi như vậy
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new SanPham(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
// Đếm tổng số bản ghi 

    public int getRowCount() {
        String query = "SELECT COUNT(*) AS totalRows FROM SANPHAM";

        try {
            PreparedStatement pstm = connect.prepareCall(query);
            ResultSet rs = pstm.executeQuery();
            int totalRows = 0;
            if (rs.next()) {
                return totalRows = rs.getInt("totalRows");
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamCT_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public List<SanPham> search_SanPhamByTrangThai(int n) {
        List<SanPham> listSearch = new ArrayList<>();
        String query = "SELECT  MaSP, TenSP, TrangThai FROM SANPHAM WHERE TrangThai = ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new SanPham(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while searching for SanPham", e);
        }
        return listSearch;
    }

    public SanPham findSanPhamByName(String sanPhamStr) {
        SanPham th = null;
        String query = "SELECT ID, MaSP, TenSP, TrangThai FROM SANPHAM WHERE TenSP LIKE ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setString(1, sanPhamStr);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                th = new SanPham(rs.getLong("ID"), rs.getString("MaSP"), rs.getString("TenSP"), rs.getInt("TrangThai"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(th);
        return th;
    }

}
