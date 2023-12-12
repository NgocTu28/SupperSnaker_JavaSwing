/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.KichThuoc;
import Model.MauSac;
import Model.ThuongHieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vutu8
 */
public class KichThuoc_Repository {

    Connection connect = DBConnection.getConnect();

    public ArrayList<KichThuoc> getToAllKichThuoc() {
        ArrayList<KichThuoc> listKichThuoc = new ArrayList<>();
        String query = "Select ID,MaSize, TenSize, TrangThai From SIZE";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listKichThuoc.add(new KichThuoc(rs.getLong(1), rs.getString(2), rs.getFloat(3), rs.getInt(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listKichThuoc;
    }

    public String MaTuDong() {
        String ma = "KT";
        int newTotal = 0;

        try {
            PreparedStatement ps = connect.prepareCall("SELECT COUNT(*) FROM SIZE");
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

    public void addKichThuoc(KichThuoc kt) {

        String sql = "INSERT INTO SIZE(MaSize, TenSize, TrangThai) VALUES (?,?,?)";
        try {
            PreparedStatement ps = connect.prepareCall(sql);
            ps.setString(1, kt.getMaSize());
            ps.setFloat(2, kt.getTenSize());
            ps.setInt(3, kt.getTrangThai());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateMauSac(KichThuoc kt, String ma) {

        String sql = "UPDATE SIZE set TenSize = ? , TrangThai = ? where MaSize = ?";
        try {
            PreparedStatement ps = connect.prepareCall(sql);
            ps.setFloat(1, kt.getTenSize());
            ps.setInt(2, kt.getTrangThai());
            ps.setString(3, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<KichThuoc> get(int page, int limt) {
        List<KichThuoc> list = new ArrayList<>();
        String sql = "Select ID,MaSize, TenSize, TrangThai From SIZE " + "ORDER BY ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try {
            PreparedStatement pstm = connect.prepareStatement(sql);
            // Công thức chỉ cần ghi như vậy
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new KichThuoc(rs.getLong(1), rs.getString(2), rs.getFloat(3), rs.getInt(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
// Đếm tổng số bản ghi 

    public int getRowCount() {
        String query = "SELECT COUNT(*) AS totalRows FROM SIZE";

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

    public List<KichThuoc> search_KichThuoc(String text) {
        List<KichThuoc> listSearch = new ArrayList<>();
        String query = "SELECT  MaSize, TenSize,TrangThai FROM SIZE WHERE MaSize LIKE ? OR TenSize LIKE ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new KichThuoc(rs.getString(1), rs.getFloat(2), rs.getInt(3)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while searching for KichThuoc", e);
        }
        return listSearch;
    }

    public List<KichThuoc> search_KichThuocByTrangThai(int n) {
        List<KichThuoc> listSearch = new ArrayList<>();
        String query = "SELECT  MaSize, TenSize,TrangThai FROM SIZE WHERE TrangThai = ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new KichThuoc(rs.getString(1), rs.getFloat(2), rs.getInt(3)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while searching for KichThuoc", e);
        }
        return listSearch;
    }

    public KichThuoc findKichCoByName(Float kichCoStr) {
    KichThuoc kichThuoc = null;
    String query = "SELECT ID, MaSize, TenSize, TrangThai FROM SIZE WHERE TenSize = ?";
    
    try (PreparedStatement ps = connect.prepareCall(query)) {
        ps.setFloat(1, kichCoStr);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                kichThuoc = new KichThuoc(rs.getLong(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error while searching for KichThuoc", e);
    }
    
    return kichThuoc;
}

}
