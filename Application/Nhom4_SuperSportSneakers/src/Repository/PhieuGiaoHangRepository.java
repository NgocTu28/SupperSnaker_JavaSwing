/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.HoaDon;
import Model.KhachHang;
import Model.PhieuGiaoHang;
import Utils.XDate;
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
import static org.apache.poi.hssf.usermodel.HeaderFooter.page;

/**
 *
 * @author manhnt
 */
public class PhieuGiaoHangRepository {

    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;

    public PhieuGiaoHang getPGHByMaHD(String maHD) {
        PhieuGiaoHang phieuGiaoHang = new PhieuGiaoHang();
        try {
            con = DBConnection.getConnect();
            query = " SELECT PGH.ID , PGH.IdHoaDon as idhd FROM HOADON\n"
                    + "JOIN PHIEUGIAOHANG AS PGH ON PGH.IdHoaDon = HOADON.ID\n"
                    + "WHERE HOADON.MaHoaDon LIKE '" + maHD + "'";

            pstm = con.prepareCall(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                HoaDon hoaDon = new HoaDon();

                hoaDon.setId(rs.getLong("idhd"));
                phieuGiaoHang.setId(rs.getLong("ID"));
                phieuGiaoHang.setIdHD(hoaDon);
                return phieuGiaoHang;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phieuGiaoHang;
    }

    public List<PhieuGiaoHang> getAll(int page, int limt) {
        List<PhieuGiaoHang> list = new ArrayList<>();
        try {
            query = "SELECT PGH.ID ,  HD.ID AS IDHD ,  MaVanDon , MaHoaDon , TenKhachHang , PGH.DiaChi , SDTNguoiNhan , GiaShip , TenShip , SDTShip , "
                    + "PGH.NgayTaoPhieu , ĐonViVanChuyen ,NgayHoanThanhDon , PGH.TrangThai FROM PHIEUGIAOHANG AS PGH\n"
                    + "JOIN HOADON AS HD ON HD.ID = PGH.IdHoaDon\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = PGH.IdKH "
                    + "	order by PGH.NgayTaoPhieu DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachHang.setSdt(rs.getString("SDTNguoiNhan"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getLong("IDHD"));

                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));

                PhieuGiaoHang phieuGiaoHang = new PhieuGiaoHang();
                phieuGiaoHang.setId(rs.getLong("ID"));
                phieuGiaoHang.setIdHD(hoaDon);
                phieuGiaoHang.setIdKH(khachHang);
                phieuGiaoHang.setMaVanDon(rs.getString("MaVanDon"));
                phieuGiaoHang.setSdtShip(rs.getString("SDTShip"));
                phieuGiaoHang.setTenShip(rs.getString("TenShip"));
                phieuGiaoHang.setGiaShip(rs.getBigDecimal("GiaShip"));
                phieuGiaoHang.setSdtNNguoiNhan(rs.getString("SDTNguoiNhan"));
                phieuGiaoHang.setNgayTao(rs.getDate("NgayTaoPhieu"));
                phieuGiaoHang.setNgayHoanThanh(rs.getDate("NgayHoanThanhDon"));
                phieuGiaoHang.setDonViVanChuyen(rs.getString("ĐonViVanChuyen"));
                phieuGiaoHang.setTrangThai(rs.getInt("TrangThai"));
                list.add(phieuGiaoHang);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
            return list;
        }
    }

    public List<PhieuGiaoHang> timTxt(String ma, int page, int limt) {
        List<PhieuGiaoHang> list = new ArrayList<>();
        try {

            query = "SELECT PGH.ID ,  HD.ID AS IDHD ,  MaVanDon , MaHoaDon , TenKhachHang , PGH.DiaChi , SDTNguoiNhan , GiaShip , TenShip , SDTShip , "
                    + "PGH.NgayTaoPhieu , ĐonViVanChuyen ,NgayHoanThanhDon , PGH.TrangThai FROM PHIEUGIAOHANG AS PGH\n"
                    + "JOIN HOADON AS HD ON HD.ID = PGH.IdHoaDon\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = PGH.IdKH "
                    + " WHERE MaVanDon like ? OR MaHoaDon LIKE ? OR SDTNguoiNhan LIKE ? "
                    + "	order by PGH.NgayTaoPhieu DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, "%" + ma + "%");
            pstm.setString(2, "%" + ma + "%");
            pstm.setString(3, "%" + ma + "%");
            pstm.setInt(4, (page - 1) * limt);
            pstm.setInt(5, limt);
            rs = pstm.executeQuery();

            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachHang.setSdt(rs.getString("SDTNguoiNhan"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getLong("IDHD"));

                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));

                PhieuGiaoHang phieuGiaoHang = new PhieuGiaoHang();
                phieuGiaoHang.setId(rs.getLong("ID"));
                phieuGiaoHang.setIdHD(hoaDon);
                phieuGiaoHang.setIdKH(khachHang);
                phieuGiaoHang.setMaVanDon(rs.getString("MaVanDon"));
                phieuGiaoHang.setSdtShip(rs.getString("SDTShip"));
                phieuGiaoHang.setTenShip(rs.getString("TenShip"));
                phieuGiaoHang.setGiaShip(rs.getBigDecimal("GiaShip"));
                phieuGiaoHang.setSdtNNguoiNhan(rs.getString("SDTNguoiNhan"));
                phieuGiaoHang.setNgayTao(rs.getDate("NgayTaoPhieu"));
                phieuGiaoHang.setNgayHoanThanh(rs.getDate("NgayHoanThanhDon"));
                phieuGiaoHang.setDonViVanChuyen(rs.getString("ĐonViVanChuyen"));
                phieuGiaoHang.setTrangThai(rs.getInt("TrangThai"));
                list.add(phieuGiaoHang);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<PhieuGiaoHang> timCbo(int ma, int page, int limt) {
        List<PhieuGiaoHang> list = new ArrayList<>();
        try {

            query = "SELECT PGH.ID ,  HD.ID AS IDHD ,  MaVanDon , MaHoaDon , TenKhachHang , PGH.DiaChi , SDTNguoiNhan , GiaShip , TenShip , SDTShip , "
                    + "PGH.NgayTaoPhieu , ĐonViVanChuyen ,NgayHoanThanhDon , PGH.TrangThai FROM PHIEUGIAOHANG AS PGH\n"
                    + "JOIN HOADON AS HD ON HD.ID = PGH.IdHoaDon\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = PGH.IdKH ";
            if (ma >= 0) {
                query += " WHERE PGH.TrangThai = ? ";
            }

            query += "	order by PGH.NgayTaoPhieu DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            if (ma < 0) {
                pstm.setInt(1, (page - 1) * limt);
                pstm.setInt(2, limt);
                rs = pstm.executeQuery();
            } else {
                pstm.setInt(1, ma);
                pstm.setInt(2, (page - 1) * limt);
                pstm.setInt(3, limt);
                rs = pstm.executeQuery();
            }

            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachHang.setSdt(rs.getString("SDTNguoiNhan"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getLong("IDHD"));

                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));

                PhieuGiaoHang phieuGiaoHang = new PhieuGiaoHang();
                phieuGiaoHang.setId(rs.getLong("ID"));
                phieuGiaoHang.setIdHD(hoaDon);
                phieuGiaoHang.setIdKH(khachHang);
                phieuGiaoHang.setMaVanDon(rs.getString("MaVanDon"));
                phieuGiaoHang.setSdtShip(rs.getString("SDTShip"));
                phieuGiaoHang.setTenShip(rs.getString("TenShip"));
                phieuGiaoHang.setGiaShip(rs.getBigDecimal("GiaShip"));
                phieuGiaoHang.setSdtNNguoiNhan(rs.getString("SDTNguoiNhan"));
                phieuGiaoHang.setNgayTao(rs.getDate("NgayTaoPhieu"));
                phieuGiaoHang.setNgayHoanThanh(rs.getDate("NgayHoanThanhDon"));
                phieuGiaoHang.setDonViVanChuyen(rs.getString("ĐonViVanChuyen"));
                phieuGiaoHang.setTrangThai(rs.getInt("TrangThai"));
                list.add(phieuGiaoHang);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void huyDon(String maPGH, int trangThaiHuy) {
        try {
            con = DBConnection.getConnect();
            query = "UPDATE PHIEUGIAOHANG\n"
                    + "SET TrangThai = ?\n"
                    + "WHERE MaVanDon LIKE ?";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, trangThaiHuy);
            pstm.setString(2, maPGH);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    public int update(PhieuGiaoHang phieuGiaoHang) {
        try {
            query = "UPDATE PHIEUGIAOHANG \n"
                    + "SET TenNguoiNhan = ? , SDTNguoiNhan = ? , DiaChi = ? , TenShip = ? , SDTShip = ? , ĐonViVanChuyen = ? , NgayHoanThanhDon = ? , GiaShip = ? \n"
                    + "WHERE MaVanDon LIKE ? ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, phieuGiaoHang.getTenNguoiNhan());
            pstm.setString(2, phieuGiaoHang.getSdtNNguoiNhan());
            pstm.setString(3, phieuGiaoHang.getDiaChi());
            pstm.setString(4, phieuGiaoHang.getTenShip());
            pstm.setString(5, phieuGiaoHang.getSdtShip());
            pstm.setString(6, phieuGiaoHang.getDonViVanChuyen());
            pstm.setString(7, XDate.toString(phieuGiaoHang.getNgayHoanThanh(), "MM-dd-yyyy"));
            pstm.setBigDecimal(8, phieuGiaoHang.getGiaShip());
            pstm.setString(9, phieuGiaoHang.getMaVanDon());
            System.out.println("Repository.PhieuGiaoHangRepository.update()" + "      OKE");
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Repository.PhieuGiaoHangRepository.update()");
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    public List<HoaDon> listDSHD() {
        List<HoaDon> list = new ArrayList<>();
        try {
//            query = "SELECT HD.ID AS IDHD, MaHoaDon, KH.ID AS IDKH, HD.NgayTao, TenKhachHang\n"
//                    + "FROM HOADON AS HD\n"
//                    + "JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH\n"
//                    + "WHERE HD.HinhThucMua = 1 AND HD.TrangThai = 1 AND NOT EXISTS (\n"
//                    + "    SELECT 1 FROM PHIEUGIAOHANG AS PGH WHERE PGH.IDHoaDon = HD.ID\n"
//                    + ");";
            query = "SELECT HD.ID AS IDHD, MaHoaDon, KH.ID AS IDKH, HD.NgayTao, TenKhachHang , hd.TrangThai\n"
                    + "                    FROM HOADON AS HD\n"
                    + "                    JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH\n"
                    + "                    WHERE HD.TrangThai =3 or  HD.TrangThai = 0 AND NOT EXISTS (\n"
                    + "                       SELECT 1 FROM PHIEUGIAOHANG AS PGH WHERE PGH.IDHoaDon = HD.ID\n"
                    + "                    );";
            con = DBConnection.getConnect();
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));

                khachHang.setId(rs.getLong("IDKH"));
                HoaDon hoaDon = new HoaDon();
                hoaDon.setIdKH(khachHang);
                hoaDon.setId(rs.getLong("IDHD"));
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                hoaDon.setNgayTao(rs.getDate("NgayTao"));
                list.add(hoaDon);

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int insert(PhieuGiaoHang phieuGiaoHang) {
        try {
            query = "INSERT INTO PHIEUGIAOHANG (IdHoaDon , IdKH , TenNguoiNhan , SDTNguoiNhan , DiaChi , TenShip , SDTShip , GiaShip , MaVanDon , ĐonViVanChuyen , NgayHoanThanhDon)\n"
                    + "VALUES  (? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, phieuGiaoHang.getIdHD().getId());
            pstm.setLong(2, phieuGiaoHang.getIdKH().getId());
            pstm.setString(3, phieuGiaoHang.getTenNguoiNhan());
            pstm.setString(4, phieuGiaoHang.getSdtNNguoiNhan());
            pstm.setString(5, phieuGiaoHang.getDiaChi());
            pstm.setString(6, phieuGiaoHang.getTenShip());
            pstm.setString(7, phieuGiaoHang.getSdtShip());
            pstm.setBigDecimal(8, phieuGiaoHang.getGiaShip());
            pstm.setString(9, phieuGiaoHang.getMaVanDon());
            pstm.setString(10, phieuGiaoHang.getDonViVanChuyen());
            pstm.setDate(11, new java.sql.Date(phieuGiaoHang.getNgayHoanThanh().getTime()));
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public List<Object> listPGHCT(String maP) {
        List<Object> list = new ArrayList<>();
        try {
            query = "SELECT MaHoaDon, TenNguoiNhan, PGH.NgayTaoPhieu, MaCTSP, TenSP, SoLuong, HDCT.GiaBan, (SoLuong * HDCT.GiaBan) AS THANHTIEN\n"
                    + "FROM HOADON\n"
                    + "JOIN PHIEUGIAOHANG AS PGH ON PGH.IdHoaDon = HOADON.ID\n"
                    + "JOIN HOADONCHITIET AS HDCT ON HDCT.IdHoaDon = HOADON.ID\n"
                    + "JOIN CHI_TIET_SAN_PHAM AS CTSP ON CTSP.ID = HDCT.IdCTSP\n"
                    + "JOIN SANPHAM AS SP ON SP.ID = CTSP.IdSP "
                    + "WHERE MaVanDon LIKE ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, maP);
            rs = pstm.executeQuery();
            int i = 1;
            while (rs.next()) {
                Object[] ob = new Object[]{i, rs.getString("MaHoaDon"),
                    rs.getString("TenNguoiNhan"), rs.getDate("NgayTaoPhieu"), rs.getString("MaCTSP"), rs.getString("TenSP"), rs.getInt("SoLuong"),
                    rs.getFloat("GiaBan"), rs.getFloat("THANHTIEN")};
                list.add(ob);
                i++;
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public int getRowCount() {
        String countSql = "SELECT COUNT(*) AS totalRows FROM PHIEUGIAOHANG";
        Connection con = DBConnection.getConnect();
        Statement stm;
        ResultSet rs;
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

    public int updateTrangThaiPGH(String ma, int trangThai) {
        try {
            con = DBConnection.getConnect();
            query = "UPDATE PHIEUGIAOHANG\n"
                    + "SET TrangThai = ?\n"
                    + "WHERE MaVanDon LIKE '" + ma + "' ";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, trangThai);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    public void updateNgayNhan(String ma , Date date) {
        try {
            query = "UPDATE PHIEUGIAOHANG\n"
                    + "SET NgayHoanThanhDon = ? \n"
                    + "WHERE MaVanDon LIKE ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setObject(1, date);
            pstm.setObject(2, ma);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiaoHangRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
