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
public class ThuongHieu_Repository {

    Connection connect = DBConnection.getConnect();

   public ArrayList<ThuongHieu> getToAll() {
        ArrayList<ThuongHieu> list = new ArrayList<>();
        String query = "Select ID,MaThuongHieu, TenThuongHieu From THUONGHIEU";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ThuongHieu(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public String MaTuDong() {
        String ma = "TH";
        int newTotal = 0;

        try {
            PreparedStatement ps = connect.prepareCall("SELECT COUNT(*) FROM THUONGHIEU");
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

    public void addThuongHieu(ThuongHieu th) {

        String sql = "INSERT INTO THUONGHIEU(MaThuongHieu, TenThuongHieu, TrangThai) VALUES (?,?,?)";
        try {
            PreparedStatement ps = connect.prepareCall(sql);
            ps.setString(1, th.getMaThuongHieu());
            ps.setString(2, th.getTenThuongHieu());
            ps.setInt(3, th.getTrangThai());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateThuongHieu(ThuongHieu th, String ma) {

        String sql = "UPDATE THUONGHIEU set TenThuongHieu = ?, TrangThai = ? where MaThuongHieu = ?";
        try {
            PreparedStatement ps = connect.prepareCall(sql);
            ps.setString(1, th.getTenThuongHieu());
            ps.setInt(2, th.getTrangThai());
            ps.setString(3, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<ThuongHieu> search_MauSac(String text) {
        List<ThuongHieu> listSearch = new ArrayList<>();
        String query = "SELECT  MaThuongHieu, TenThuongHieu,TrangThai FROM THUONGHIEU WHERE MaThuongHieu LIKE ? OR TenThuongHieu LIKE ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new ThuongHieu(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
} catch (Exception e) {
            throw new RuntimeException("Error while searching for SanPham", e);
        }
        return listSearch;
    }

    public List<ThuongHieu> search_MauSacByTrangThai(int n) {
        List<ThuongHieu> listSearch = new ArrayList<>();
        String query = "SELECT  MaThuongHieu, TenThuongHieu, TrangThai FROM THUONGHIEU WHERE TrangThai = ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new ThuongHieu(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while searching for SanPham", e);
        }
        return listSearch;
    }

    public List<ThuongHieu> get(int page, int limt) {
        List<ThuongHieu> list = new ArrayList<>();
        String sql = "Select ID,MaThuongHieu, TenThuongHieu, TrangThai From THUONGHIEU " + "ORDER BY ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try {
            PreparedStatement pstm = connect.prepareStatement(sql);
            // Công thức chỉ cần ghi như vậy
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new ThuongHieu(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public int getRowCount() {
        String query = "SELECT COUNT(*) AS totalRows FROM THUONGHIEU";

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

    public ThuongHieu findThuongHieuByName(String name) {
        ThuongHieu th = null;
        String query = "SELECT ID, MaThuongHieu, TenThuongHieu FROM THUONGHIEU WHERE MaThuongHieu LIKE ?";

        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setString(1, "%" + name + "%"); // Add wildcard characters here
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    th = new ThuongHieu(rs.getLong(1), rs.getString(2), rs.getString(3));
                    // Note: If there are multiple results, th will be overwritten, and only the last result will be returned.
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching for SanPham", e);
        }

        return th;
    }
}
