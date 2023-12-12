/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.PhieuTra;
import Model.PhieuTraChiTiet;
import Model.SanPhamChiTiet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manhnt
 */
public class PhieuTraChiTietRepository {

    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;

    public void insert(SanPhamChiTiet sanPhamChiTiet, Long idPT) {
        try {
            con = DBConnection.getConnect();
            query = "INSERT INTO CHITIETPHIEUTRA (IdPhieuTra , MaCTSP , TensSP , Mau , Size , ThuongHieu , SoLuong) VALUES \n"
                    + "(?  , ? , ? , ? , ? , ? , ?)";
            pstm = con.prepareCall(query);
            pstm.setLong(1, idPT);
            pstm.setString(2, sanPhamChiTiet.getMaSPCT());
            pstm.setString(3, sanPhamChiTiet.getIdSanPham().getTenSanpham());
            pstm.setString(4, sanPhamChiTiet.getIdMau().getTenMau());
            pstm.setFloat(5, sanPhamChiTiet.getIdKichThuoc().getTenSize());
            pstm.setString(6, sanPhamChiTiet.getIdThuongHieu().getTenThuongHieu());
            pstm.setInt(7, sanPhamChiTiet.getSoLuong());
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraChiTietRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<PhieuTraChiTiet> listPT(Long ID) {
         List<PhieuTraChiTiet> list = new ArrayList<>();
        try {
           
            query = "SELECT MaCTSP , TensSP  ,  Mau ,ThuongHieu ,  Size , SoLuong FROM CHITIETPHIEUTRA \n"
                    + "WHERE IdPhieuTra = " + ID + " ;";
            con = DBConnection.getConnect();
            pstm = con.prepareCall(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuTraChiTiet phieuTraChiTiet = new PhieuTraChiTiet();

                phieuTraChiTiet.setMaCTSP(rs.getString("MaCTSP"));

                phieuTraChiTiet.setTenSP(rs.getString("TensSP"));
                phieuTraChiTiet.setMau(rs.getString("Mau"));
                phieuTraChiTiet.setThuongHieu(rs.getString("ThuongHieu"));
                phieuTraChiTiet.setSoLuong(rs.getInt("SoLuong"));
                phieuTraChiTiet.setSize(rs.getFloat("Size"));
                list.add(phieuTraChiTiet);

            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraChiTietRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
