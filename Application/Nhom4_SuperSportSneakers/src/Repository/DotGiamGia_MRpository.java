/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.DotGiamGia_M;
import Model.KichThuoc;
import Model.MauSac;
import Model.NhanVien;
import Model.SanPham;
import Model.SanPhamChiTiet;
import Model.ThuongHieu;
import Utils.XDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
public class DotGiamGia_MRpository {

    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;

    public List<DotGiamGia_M> getAllDGG(int page, int limit) {
        List<DotGiamGia_M> list = new ArrayList<>();
        try {
            query = "SELECT NV.ID AS IDNV , NV.HoVaTen ,DGG.ID AS ID  , MaDGG , TenDGG, Loai , GiaTri , "
                    + " NgayBatDau , NgayKetThuc , DGG.NgayTao, MoTa , DGG.TrangThai FROM DOT_GIAM_GIA AS DGG\n"
                    + "	JOIN NHANVIEN AS NV ON NV.ID = DGG.IdNV"
                    + "	order by ID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";;
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, (page - 1) * limit);
            pstm.setInt(2, limit);
            rs = pstm.executeQuery();
            while (rs.next()) {
                DotGiamGia_M dggm = new DotGiamGia_M();
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IDNV"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                dggm.setIdNV(nhanVien);
                dggm.setIdDGG(rs.getLong("ID"));
                dggm.setMaDGG(rs.getString("MaDGG"));
                dggm.setTenDGG(rs.getString("TenDGG"));
                dggm.setHinhThucDGG(rs.getInt("Loai"));
                dggm.setGiaTri(rs.getBigDecimal("GiaTri"));
                dggm.setNgayBatDau(rs.getDate("NgayBatDau"));
                dggm.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                dggm.setNgayTao(rs.getDate("NgayTao"));
                dggm.setMoTa(rs.getString("MoTa"));
                dggm.setTrangThai(rs.getInt("TrangThai"));
                list.add(dggm);

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<DotGiamGia_M> listTimCboTT(int tt, int page, int limit) {
        List<DotGiamGia_M> list = new ArrayList<>();
        try {
            query = "SELECT NV.ID AS IDNV, NV.HoVaTen, DGG.ID AS ID, MaDGG, TenDGG, Loai, GiaTri,\n"
                    + "       NgayBatDau, NgayKetThuc, DGG.NgayTao, MoTa, DGG.TrangThai\n"
                    + "FROM DOT_GIAM_GIA AS DGG\n"
                    + "JOIN NHANVIEN AS NV ON NV.ID = DGG.IdNV\n"
                    + "WHERE DGG.TrangThai = ?\n"
                    + "ORDER BY ID DESC\n"
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, tt);
            pstm.setInt(2, (page - 1) * limit);
            pstm.setInt(3, limit);
            rs = pstm.executeQuery();
            while (rs.next()) {
                DotGiamGia_M dggm = new DotGiamGia_M();
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IDNV"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                dggm.setIdNV(nhanVien);
                dggm.setIdDGG(rs.getLong("ID"));
                dggm.setMaDGG(rs.getString("MaDGG"));
                dggm.setTenDGG(rs.getString("TenDGG"));
                dggm.setHinhThucDGG(rs.getInt("Loai"));
                dggm.setGiaTri(rs.getBigDecimal("GiaTri"));
                dggm.setNgayBatDau(rs.getDate("NgayBatDau"));
                dggm.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                dggm.setNgayTao(rs.getDate("NgayTao"));
                dggm.setMoTa(rs.getString("MoTa"));
                dggm.setTrangThai(rs.getInt("TrangThai"));
                list.add(dggm);

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<DotGiamGia_M> listTimTT(String tt, int page, int limit) {
        List<DotGiamGia_M> list = new ArrayList<>();
        try {
            query = "SELECT NV.ID AS IDNV, NV.HoVaTen, DGG.ID AS ID, MaDGG, TenDGG, Loai, GiaTri,\n"
                    + "       NgayBatDau, NgayKetThuc, DGG.NgayTao, MoTa, DGG.TrangThai\n"
                    + "FROM DOT_GIAM_GIA AS DGG\n"
                    + "JOIN NHANVIEN AS NV ON NV.ID = DGG.IdNV\n"
                    + "WHERE MaDGG LIKE ? OR TenDGG LIKE ? \n"
                    + "ORDER BY ID DESC\n"
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, "%" + tt + "%");
            pstm.setString(2, "%" + tt + "%");
            pstm.setInt(3, (page - 1) * limit);
            pstm.setInt(4, limit);
            rs = pstm.executeQuery();
            while (rs.next()) {
                DotGiamGia_M dggm = new DotGiamGia_M();
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IDNV"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                dggm.setIdNV(nhanVien);
                dggm.setIdDGG(rs.getLong("ID"));
                dggm.setMaDGG(rs.getString("MaDGG"));
                dggm.setTenDGG(rs.getString("TenDGG"));
                dggm.setHinhThucDGG(rs.getInt("Loai"));
                dggm.setGiaTri(rs.getBigDecimal("GiaTri"));
                dggm.setNgayBatDau(rs.getDate("NgayBatDau"));
                dggm.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                dggm.setNgayTao(rs.getDate("NgayTao"));
                dggm.setMoTa(rs.getString("MoTa"));
                dggm.setTrangThai(rs.getInt("TrangThai"));
                list.add(dggm);

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int insertDGG(DotGiamGia_M dgg) {
        try {
            query = "INSERT INTO DOT_GIAM_GIA (IdNV , MaDGG , TenDGG , Loai , GiaTri , NgayBatDau , NgayKetThuc, TrangThai  )VALUES \n"
                    + "( ? , ? , ? , ? , ? , ? , ? , ? )";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, dgg.getIdNV().getId());
            pstm.setString(2, dgg.getMaDGG());
            pstm.setString(3, dgg.getTenDGG());
            pstm.setInt(4, dgg.getHinhThucDGG());
            pstm.setBigDecimal(5, dgg.getGiaTri());
            pstm.setObject(6, dgg.getNgayBatDau());
            pstm.setObject(7, dgg.getNgayKetThuc());
            pstm.setObject(8, dgg.getTrangThai());

            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int update(DotGiamGia_M dgg) {
        try {
            query = "UPDATE DOT_GIAM_GIA\n"
                    + "SET TenDGG = ? , Loai = ? , GiaTri = ? , NgayBatDau = ? , NgayKetThuc = ? , MoTa  = ?\n"
                    + "WHERE MaDGG LIKE ? ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, dgg.getTenDGG());
            pstm.setInt(2, dgg.getHinhThucDGG());
            pstm.setObject(3, dgg.getGiaTri());
            pstm.setObject(4, dgg.getNgayBatDau());
            pstm.setObject(5, dgg.getNgayKetThuc());
            pstm.setString(6, dgg.getMoTa());
            pstm.setString(7, dgg.getMaDGG());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    public DotGiamGia_M getDGGByMaCTSP(String maCTSP) {
        try {
            query = " SELECT CTSP.ID as ID , CTSP.MaCTSP as  MaCTSP ,\n"
                    + "  IdDGG , DGG.MaDGG AS MaDGG , DGG.Loai AS LoaiDGG , DGG.GiaTri AS GiaTriDGG , DGG.TrangThai AS TrangThaiDGG ,\n"
                    + "  SoLuongTon, GiaNiemYet , GiaBan\n"
                    + "   FROM  DOT_GIAM_GIA  AS DGG\n"
                    + "    JOIN CHI_TIET_SAN_PHAM AS CTSP ON CTSP.IdDGG = DGG.ID\n"
                    + "	WHERE CTSP.MaCTSP LIKE '" + maCTSP + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                DotGiamGia_M dgg = new DotGiamGia_M(
                        rs.getLong("IdDGG"),
                        rs.getString("MaDGG"),
                        rs.getInt("LoaiDGG"),
                        rs.getBigDecimal("GiaTriDGG"),
                        rs.getInt("TrangThaiDGG"));
                return dgg;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<SanPhamChiTiet> getAllSPST_DGG(Date ngayBatDau, Date ngayKetThuc) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        try {

            query = "SELECT CTSP.ID AS IdCTSP, CTSP.MaCTSP , SP.TenSP , CTSP.GiaNiemYet , TH.TenThuongHieu , S.TenSize , M.TenMau\n"
                    + "FROM CHI_TIET_SAN_PHAM AS CTSP\n"
                    + "JOIN SANPHAM AS SP ON SP.ID  = CTSP.IdSP\n"
                    + "JOIN SIZE AS S ON S.ID = CTSP.IdSize\n"
                    + "JOIN THUONGHIEU AS TH ON TH.ID = CTSP.IdThuongHieu\n"
                    + "JOIN MAU AS M ON M.ID = CTSP.IdMau\n"
                    + "WHERE CTSP.TrangThai = 0 -- Chi tiết sản phẩm còn hàng\n"
                    + "    AND NOT EXISTS (\n"
                    + "        SELECT 1\n"
                    + "        FROM CHI_TIET_DGG AS CTDGG\n"
                    + "        JOIN DOT_GIAM_GIA AS DGG ON CTDGG.IdDGG = DGG.ID\n"
                    + "        WHERE CTDGG.IdCTSP = CTSP.ID\n"
                    + "            AND (DGG.NgayBatDau BETWEEN ? AND ? \n"
                    + "                 OR DGG.NgayKetThuc BETWEEN ?  AND ? \n"
                    + "                 OR (DGG.NgayBatDau <= ? AND DGG.NgayKetThuc >= ? ))\n"
                    + "    );";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            System.out.println(XDate.toString(XDate.convertDateFormat(XDate.toString(ngayBatDau, "dd-MM-yyyy"), "MM-dd-yyyy"), "MM-dd-yyyy"));
            System.out.println(XDate.toString(XDate.convertDateFormat(XDate.toString(ngayKetThuc, "dd-MM-yyyy"), "MM-dd-yyyy"), "MM-dd-yyyy"));

            pstm.setString(1, XDate.toString(XDate.convertDateFormat(XDate.toString(ngayBatDau, "dd-MM-yyyy"), "MM-dd-yyyy"), "MM-dd-yyyy"));
            pstm.setString(2, XDate.toString(XDate.convertDateFormat(XDate.toString(ngayKetThuc, "dd-MM-yyyy"), "MM-dd-yyyy"), "MM-dd-yyyy"));
            pstm.setString(3, XDate.toString(XDate.convertDateFormat(XDate.toString(ngayBatDau, "dd-MM-yyyy"), "MM-dd-yyyy"), "MM-dd-yyyy"));
            pstm.setString(4, XDate.toString(XDate.convertDateFormat(XDate.toString(ngayKetThuc, "dd-MM-yyyy"), "MM-dd-yyyy"), "MM-dd-yyyy"));
            pstm.setString(5, XDate.toString(XDate.convertDateFormat(XDate.toString(ngayBatDau, "dd-MM-yyyy"), "MM-dd-yyyy"), "MM-dd-yyyy"));
            pstm.setString(6, XDate.toString(XDate.convertDateFormat(XDate.toString(ngayKetThuc, "dd-MM-yyyy"), "MM-dd-yyyy"), "MM-dd-yyyy"));

            rs = pstm.executeQuery();
            while (rs.next()) {
                KichThuoc kichThuoc = new KichThuoc();
                kichThuoc.setTenSize(rs.getFloat("TenSize"));
                MauSac mauSac = new MauSac();
                mauSac.setTenMau(rs.getString("TenMau"));
                SanPham sanPham = new SanPham();
                sanPham.setTenSanpham(rs.getString("TenSP"));
                ThuongHieu thuongHieu = new ThuongHieu();
                thuongHieu.setTenThuongHieu(rs.getString("TenThuongHieu"));
                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                sanPhamChiTiet.setIdSPCT(rs.getLong("IdCTSP"));
                sanPhamChiTiet.setMaSPCT(rs.getString("MaCTSP"));
                sanPhamChiTiet.setGiaNiemYet(rs.getBigDecimal("GiaNiemYet"));
                sanPhamChiTiet.setIdKichThuoc(kichThuoc);
                sanPhamChiTiet.setIdMau(mauSac);
                sanPhamChiTiet.setIdSanPham(sanPham);
                sanPhamChiTiet.setIdThuongHieu(thuongHieu);
                list.add(sanPhamChiTiet);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public DotGiamGia_M getDGGByMaDGG(String maDGG) {
        try {
            query = "SELECT ID , MaDGG , TenDGG , Loai , GiaTri FROM DOT_GIAM_GIA \n"
                    + "WHERE MaDGG LIKE '" + maDGG + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                DotGiamGia_M dgg = new DotGiamGia_M();
                dgg.setIdDGG(rs.getLong("ID"));
                dgg.setMaDGG(rs.getString("MaDGG"));
                dgg.setTenDGG(rs.getString("TenDGG"));
                dgg.setHinhThucDGG(rs.getInt("Loai"));
                dgg.setGiaTri(rs.getBigDecimal("GiaTri"));
                return dgg;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getRowCount() {
        String countSql = "SELECT COUNT(*) AS totalRows FROM DOT_GIAM_GIA";
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

    public DotGiamGia_M getDGG_BH(String maCTSP) {
        DotGiamGia_M dgg = new DotGiamGia_M();
        try {
            query = "SELECT MaDGG , Loai , GiaTri  FROM DOT_GIAM_GIA\n"
                    + "JOIN CHI_TIET_SAN_PHAM  AS CTSP ON CTSP.IdDGG = DOT_GIAM_GIA.ID\n"
                    + "WHERE MaCTSP LIKE '" + maCTSP + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                dgg.setMaDGG(rs.getString("MaDGG"));
                dgg.setHinhThucDGG(rs.getInt("Loai"));
                dgg.setGiaTri(rs.getBigDecimal("GiaTri"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dgg;
    }

    public void updateBD(String date) {
        try {
          
            query = "UPDATE DOT_GIAM_GIA\n"
                    + "SET TrangThai = 1\n"
                    + "WHERE CAST(NgayBatDau AS DATE) = CAST(GETDATE() AS DATE);";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateKT(String date) {
        try {
            query = "UPDATE DOT_GIAM_GIA\n"
                    + "SET TrangThai =2 \n"
                    + "WHERE NgayKetThuc < '" + date + "' ;";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DotGiamGia_MRpository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
