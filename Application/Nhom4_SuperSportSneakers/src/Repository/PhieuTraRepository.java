/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.ChiTietHoaDon;
import Model.HoaDon;
import Model.KhachHang;
import Model.KichThuoc;
import Model.MauSac;
import Model.PhieuTra;
import Model.SanPham;
import Model.SanPhamChiTiet;
import Model.ThuongHieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manhnt
 */
public class PhieuTraRepository {
    
    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;
    
    public List<ChiTietHoaDon> getHDCTByMaHD(String maHD) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        
        try {
            query = "SELECT MaCTSP , TenSP , HDCT.SoLuong , HDCT.GiaBan , HDCT.ThanhTien\n"
                    + "FROM HOADON AS HD\n"
                    + "JOIN KHACHHANG AS KH  ON KH.ID = HD.IdKH\n"
                    + "JOIN HOADONCHITIET AS HDCT ON HDCT.IdHoaDon = HD.ID\n"
                    + "JOIN CHI_TIET_SAN_PHAM AS CTSP ON CTSP.ID = HDCT.IdCTSP\n"
                    + "JOIN SANPHAM AS SP ON SP.ID = CTSP.IdSP\n"
                    + "WHERE HD.NgayThanhToan >= DATEADD(DAY, -3, GETDATE())\n"
                    + "      AND HD.NgayThanhToan <= GETDATE() AND HD.MaHoaDon like '%" + maHD + "%' ;";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                sanPhamChiTiet.setIdSanPham(sanPham);
                sanPhamChiTiet.setMaSPCT(rs.getString("MaCTSP"));
                chiTietHoaDon.setIdCTSP(sanPhamChiTiet);
                chiTietHoaDon.setSoLuong(rs.getInt("SoLuong"));
                chiTietHoaDon.setGiaBan(rs.getBigDecimal("GiaBan"));
                chiTietHoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                list.add(chiTietHoaDon);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public HoaDon getThongTinHD(String maHD) {
        HoaDon hoaDon = new HoaDon();
        try {
            query = "SELECT HD.ID AS ID , MaHoaDon , TenKhachHang , HD.NgayThanhToan  , HD.TongTienSP \n"
                    + "FROM HOADON AS HD\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH\n"
                    + "JOIN HOADONCHITIET AS HDCT ON HDCT.IdHoaDon = HD.ID\n"
                    + "JOIN CHI_TIET_SAN_PHAM AS CTSP ON CTSP.ID = HDCT.IdCTSP\n"
                    + "JOIN SANPHAM AS SP ON SP.ID = CTSP.IdSP\n"
                    + "WHERE HD.NgayThanhToan >= DATEADD(DAY, -3, GETDATE())\n"
                    + "      AND HD.NgayThanhToan <= GETDATE()\n"
                    + "      AND HD.MaHoaDon LIKE '" + maHD + "';";
            
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                hoaDon.setIdKH(khachHang);
                hoaDon.setId(rs.getLong("ID"));
                hoaDon.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                hoaDon.setTongTienSP(rs.getBigDecimal("TongTienSP"));
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hoaDon;
    }
    
    public SanPhamChiTiet getSPTraHang(String ma, int soLuong) {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        try {
            query = "  SELECT  MaCTSP , TenSP  , TenThuongHieu , TenMau , TenSize , HDCT.GiaBan FROM CHI_TIET_SAN_PHAM AS CTSP\n"
                    + "	 RIGHT JOIN SANPHAM AS SP ON SP.ID= CTSP.IdSP\n"
                    + "	RIGHT  JOIN THUONGHIEU AS TH ON TH.ID = CTSP.IdThuongHieu\n"
                    + "	RIGHT  JOIN MAU AS M ON M.ID = CTSP.IdMau\n"
                    + "	RIGHT  JOIN SIZE AS S ON S.ID = CTSP.IdSize\n"
                    + "	RIGHT  JOIN HOADONCHITIET AS HDCT ON HDCT.IdCTSP = CTSP.ID\n"
                    + "	  WHERE MaCTSP LIKE '" + ma + "' ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                
                sanPhamChiTiet.setIdKichThuoc(kichThuoc);
                sanPhamChiTiet.setIdMau(mauSac);
                sanPhamChiTiet.setIdSanPham(sanPham);
                sanPhamChiTiet.setIdThuongHieu(thuongHieu);
                sanPhamChiTiet.setSoLuong(soLuong);
                sanPhamChiTiet.setGiaBan(rs.getBigDecimal("GiaBan"));
                sanPhamChiTiet.setMaSPCT(rs.getString("MaCTSP"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sanPhamChiTiet;
        
    }
    
    public void insertPT(PhieuTra phieuTra) {
        try {
            query = "INSERT INTO PHIEUTRA (IdHoaDon , IdNhanVien ,  MaPhieu , NgayTra , TienHoanTra , GhiChu) values\n"
                    + "(? , ? ,  ? , ? , ? , ? )";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, phieuTra.getIdHD().getId());
            pstm.setLong(2, phieuTra.getIdNV().getId());
            pstm.setString(3, phieuTra.getMaPhieu());
            pstm.setObject(4, new Date());
            pstm.setBigDecimal(5, phieuTra.getTienHoanTra());
            pstm.setString(6, phieuTra.getGhiChu());
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Long getIdPt(String ma) {
        Long id = 0L;
        try {
            query = "SELECT ID FROM PHIEUTRA WHERE MaPhieu LIKE '" + ma + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareCall(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                id = rs.getLong("ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public int getRowCount() {
        String countSql = "SELECT COUNT(*) AS totalRows FROM PHIEUTRA";
        con = DBConnection.getConnect();
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(countSql);
            int totalRows = 0;
            if (rs.next()) {
                return totalRows = rs.getInt("totalRows");
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public List<PhieuTra> listPT(String ma) {
        List<PhieuTra> list = new ArrayList<>();
        try {
            query = "SELECT PT.ID AS IDPT , HD.MaHoaDon ,  MaPhieu , NgayTra , TienHoanTra , GhiChu FROM PHIEUTRA AS PT\n"
                    + "JOIN HOADON AS HD ON HD.ID = PT.IdHoaDon\n"
                    + "WHERE HD.MaHoaDon LIKE '%" + ma + "%' OR MaPhieu LIKE '%" + ma + "%' ;";
            
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {                
                PhieuTra phieuTra = new PhieuTra();
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                phieuTra.setIdHD(hoaDon);
                phieuTra.setId(rs.getLong("IDPT"));
                phieuTra.setMaPhieu(rs.getString("MaPhieu"));
                phieuTra.setNgayTra(rs.getDate("NgayTra"));
                phieuTra.setTienHoanTra(rs.getBigDecimal("TienHoanTra"));
                phieuTra.setGhiChu(rs.getString("GhiChu"));
                
                list.add(phieuTra);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
