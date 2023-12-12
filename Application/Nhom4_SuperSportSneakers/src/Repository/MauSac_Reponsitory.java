/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.KichThuoc;
import Model.MauSac;
import Model.SanPham;
import Model.SanPhamChiTiet;
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
public class MauSac_Reponsitory {

    Connection connect = DBConnection.getConnect();

    public ArrayList<MauSac> getToAll() {
        ArrayList<MauSac> listMauSac = new ArrayList<>();
        String query = "Select ID,MaMau, TenMau, TrangThai From MAU";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listMauSac.add(new MauSac(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listMauSac;
    }

    public String MaTuDong() {
        String ma = "MAU";
        int newTotal = 0;

        try {
            PreparedStatement ps = connect.prepareCall("SELECT COUNT(*) FROM MAU");
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

    public List<MauSac> get(int page, int limt) {
        List<MauSac> list = new ArrayList<>();
        String sql = "Select ID,MaMau, TenMau, TrangThai From MAU " + "ORDER BY ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try {
            PreparedStatement pstm = connect.prepareStatement(sql);
            // Công thức chỉ cần ghi như vậy
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new MauSac(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
// Đếm tổng số bản ghi 

    public int getRowCount() {
        String query = "SELECT COUNT(*) AS totalRows FROM MAU";

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

    public void addMauSac(MauSac ms) {

        String sql = "INSERT INTO MAU(MaMau, TenMau,TrangThai) VALUES (?,?,?)";
        try {
            PreparedStatement ps = connect.prepareCall(sql);
            ps.setString(1, ms.getMaMau());
            ps.setString(2, ms.getTenMau());
            ps.setInt(3, ms.getTrangThai());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateMauSac(MauSac ms, String ma) {

        String sql = "UPDATE SANPHAM set TenMau = ?,TrangThai = ? where MaMau = ?";
        try {
            PreparedStatement ps = connect.prepareCall(sql);
            ps.setString(1, ms.getTenMau());
            ps.setInt(2, ms.getTrangThai());
            ps.setString(3, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<MauSac> search_MauSac(String text) {
        List<MauSac> listSearch = new ArrayList<>();
        String query = "SELECT  MaMau, TenMau,TrangThai FROM MAU WHERE MaMau LIKE ? OR TenMau LIKE ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new MauSac(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while searching for SanPham", e);
        }
        return listSearch;
    }

    public List<MauSac> search_MauSacByTrangThai(int n) {
        List<MauSac> listSearch = new ArrayList<>();
        String query = "SELECT  MaMau, TenMau,TrangThai FROM MAU WHERE TrangThai = ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new MauSac(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while searching for SanPham", e);
        }
        return listSearch;
    }

    public MauSac findMauSacByName(String mauSacStr) {
        MauSac mauSac = null;
        String query = "SELECT ID, MaMau, TenMau, TrangThai FROM MAU WHERE TenMau LIKE ?";

        try (PreparedStatement ps = connect.prepareCall(query)) {
            ps.setString(1, "%" + mauSacStr + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mauSac = new MauSac(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching for MauSac", e);
        }

        return mauSac;
    }

}
