/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.SanPhamChiTiet;
import Model.DotGiamGia_M;
import Model.KichThuoc;
import Model.MauSac;
import Model.SanPham;
import Model.ThuongHieu;
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
public class SanPhamCT_Repository {

    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;

    Connection connect = DBConnection.getConnect();
     public SanPhamChiTiet getCTSPByIdCTSP(Long idSanPham, Long idThuongHieu, Long idMauSac, Long idKichCo) {
    
        String query = ""
                + "SELECT CTSP.ID as ID, CTSP.MaCTSP as MaCTSP, SP.ID AS IdSP, SP.TenSP AS TenSP, S.ID AS IdSize, S.TenSize AS TenSize,\n"
                + " TH.ID AS IdTH, TH.TenThuongHieu AS TenThuongHieu, M.ID AS IdM, M.TenMau AS TenMau,\n"
                + " IdDGG, DGG.MaDGG AS MaDGG, DGG.Loai AS LoaiDGG, DGG.GiaTri AS GiaTriDGG, DGG.TrangThai AS TrangThaiDGG,\n"
                + " SoLuongTon, GiaNiemYet, GiaBan\n"
                + " FROM CHI_TIET_SAN_PHAM AS CTSP\n"
                + " JOIN SANPHAM AS SP ON CTSP.IdSP = SP.ID\n"
                + " JOIN SIZE AS S ON CTSP.IdSize = S.ID\n"
                + " JOIN THUONGHIEU AS TH ON CTSP.IdThuongHieu = TH.ID\n"
                + " JOIN MAU AS M ON CTSP.IdMau = M.ID\n"
                + " LEFT JOIN DOT_GIAM_GIA AS DGG ON CTSP.IdDGG = DGG.ID\n"
                + " WHERE TH.ID = ? AND SP.ID = ? AND M.ID = ? AND S.ID = ?";
                
        try (Connection con = DBConnection.getConnect();
             PreparedStatement stm = con.prepareStatement(query)) {

            stm.setLong(1, idThuongHieu);
            stm.setLong(2, idSanPham);
            stm.setLong(3,idMauSac);
            stm.setLong(4, idKichCo);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    DotGiamGia_M dgg = new DotGiamGia_M(
                            rs.getLong("IdDGG"),
                            rs.getString("MaDGG"),
                            rs.getInt("LoaiDGG"),
                            rs.getBigDecimal("GiaTriDGG"),
                            rs.getInt("TrangThaiDGG"));

                    SanPham sp = new SanPham(rs.getLong("IdSP"), rs.getString("TenSP"));
                    KichThuoc kt = new KichThuoc(rs.getLong("IdSize"), rs.getFloat("TenSize"));
                    MauSac mau = new MauSac(rs.getLong("IdM"), rs.getString("TenMau"));
                    ThuongHieu th = new ThuongHieu(rs.getLong("IdTH"), rs.getString("TenThuongHieu"));
                    SanPhamChiTiet ctspm = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"),
                            rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"),
                            mau, kt, th, sp);

                    System.out.println(ctspm.toString());
                    return ctspm;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamCT_Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<SanPhamChiTiet> getToAll() {
        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = "select  CTSP.ID ,CTSP.MaCTSP ,SP.TenSP,TH.TenThuongHieu,S.TenSize,M.TenMau,CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai , CTSP.ID as ID from CHI_TIET_SAN_PHAM as CTSP\n"
                + "join MAU as M on M.ID = CTSP.IdMau\n"
                + "join SIZE as S on S.ID = CTSP.IdSize\n"
                + "join THUONGHIEU as TH on TH.ID = CTSP.IdThuongHieu\n"
                + "join SANPHAM as SP on SP.ID = CTSP.IdSP\n";

        try {
            PreparedStatement pstm = connect.prepareCall(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));

                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
                list.add(sanPhamChiTiet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<SanPhamChiTiet> search_SanPhamChiTiet(String text) {
        List<SanPhamChiTiet> listSearch = new ArrayList<>();
        String query = "select  CTSP.ID,CTSP.MaCTSP,SP.TenSP,TH.TenThuongHieu,S.TenSize,M.TenMau,CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai , CTSP.ID as ID from CHI_TIET_SAN_PHAM as CTSP\n"
                + "join MAU as M on M.ID = CTSP.IdMau\n"
                + "join SIZE as S on S.ID = CTSP.IdSize\n"
                + "join THUONGHIEU as TH on TH.ID = CTSP.IdThuongHieu\n"
                + "join SANPHAM as SP on SP.ID = CTSP.IdSP\n"
                + "WHERE MACTSP like ? OR TenSP like ? OR TenThuongHieu like ?";
        // Sản phảm lỗi phần này neey quét mã sẽ ra ko đúng thông tin 
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");
            ps.setString(3, "%" + text + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));

                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
                listSearch.add(sanPhamChiTiet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listSearch;
    }

    public List<SanPhamChiTiet> searchTrangThai_SanPhamChiTiet(int n) {
        List<SanPhamChiTiet> listSearch = new ArrayList<>();
        String query = "select  CTSP.ID ,CTSP.MaCTSP,SP.TenSP,TH.TenThuongHieu,S.TenSize,M.TenMau,CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai , CTSP.ID as ID from CHI_TIET_SAN_PHAM as CTSP\n"
                + "join MAU as M on M.ID = CTSP.IdMau\n"
                + "join SIZE as S on S.ID = CTSP.IdSize\n"
                + "join THUONGHIEU as TH on TH.ID = CTSP.IdThuongHieu\n"
                + "join SANPHAM as SP on SP.ID = CTSP.IdSP\n"
                + "WHERE CTSP.TrangThai = ?";
        try {
            PreparedStatement ps = connect.prepareCall(query);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));

                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
                listSearch.add(sanPhamChiTiet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listSearch;
    }

    public List<SanPhamChiTiet> get(int page, int limt) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = "SELECT CTSP.ID, CTSP.MaCTSP, SP.TenSP, TH.TenThuongHieu, S.TenSize, M.TenMau, CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai\n"
                + "FROM CHI_TIET_SAN_PHAM as CTSP\n"
                + "JOIN MAU as M on M.ID = CTSP.IdMau\n"
                + "JOIN SIZE as S on S.ID = CTSP.IdSize\n"
                + "JOIN THUONGHIEU as TH on TH.ID = CTSP.IdThuongHieu\n"
                + "JOIN SANPHAM as SP on SP.ID = CTSP.IdSP\n"
                + "ORDER BY CTSP.ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try {
            PreparedStatement pstm = connect.prepareStatement(sql);
            // Công thức chỉ cần ghi như vậy
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));
                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
                list.add(sanPhamChiTiet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<SanPhamChiTiet> get3(int page, int limt) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = "SELECT CTSP.ID, CTSP.MaCTSP, SP.TenSP, TH.TenThuongHieu, S.TenSize, M.TenMau, CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai\n"
                + "FROM CHI_TIET_SAN_PHAM as CTSP\n"
                + "JOIN MAU as M on M.ID = CTSP.IdMau\n"
                + "JOIN SIZE as S on S.ID = CTSP.IdSize\n"
                + "JOIN THUONGHIEU as TH on TH.ID = CTSP.IdThuongHieu\n"
                + "JOIN SANPHAM as SP on SP.ID = CTSP.IdSP\n"
                + " where CTSP.SoLuongTon > 0 "
                + "ORDER BY CTSP.ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try {
            PreparedStatement pstm = connect.prepareStatement(sql);
            // Công thức chỉ cần ghi như vậy
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));
                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
                list.add(sanPhamChiTiet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void insertSPCT(List<SanPhamChiTiet> spctList) {
        String query = "INSERT INTO CHI_TIET_SAN_PHAM (IdSP, IdThuongHieu, IdMau, IdSize, MaCTSP, SoLuongTon, GiaNiemYet, GiaBan, MoTa, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = connect.prepareCall(query);

            for (SanPhamChiTiet spct : spctList) {
                pstm.setLong(1, spct.getIdSanPham().getIdSanPham());
                pstm.setLong(2, spct.getIdThuongHieu().getIdThuongHieu());
                pstm.setLong(3, spct.getIdMau().getIdMau());
                pstm.setLong(4, spct.getIdKichThuoc().getIdSize());
                pstm.setString(5, spct.getMaSPCT());
                pstm.setInt(6, spct.getSoLuong());
                pstm.setBigDecimal(7, spct.getGiaNiemYet());
                pstm.setBigDecimal(8, spct.getGiaBan());
                pstm.setString(9, spct.getMoTa());
                pstm.setInt(10, spct.getTrangThai());

                pstm.addBatch();  // Add the current statement to the batch
            }

            // Execute the batch
            pstm.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insertSPCT(SanPhamChiTiet spct) {
        String query = " Insert  into CHI_TIET_SAN_PHAM (IdSP,IdThuongHieu,IdMau,IdSize,MaCTSP,SoLuongTon,GiaNiemYet,GiaBan,MoTa,TrangThai) Values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = connect.prepareCall(query);
            pstm.setLong(1, spct.getIdSanPham().getIdSanPham());
            pstm.setLong(2, spct.getIdThuongHieu().getIdThuongHieu());
            pstm.setLong(3, spct.getIdMau().getIdMau());
            pstm.setLong(4, spct.getIdKichThuoc().getIdSize());
            pstm.setString(5, spct.getMaSPCT());
            pstm.setInt(6, spct.getSoLuong());
            pstm.setBigDecimal(7, spct.getGiaNiemYet());
            pstm.setBigDecimal(8, spct.getGiaBan());
            pstm.setString(9, spct.getMoTa());
            pstm.setInt(10, spct.getTrangThai());

            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateSPCT(SanPhamChiTiet spct, String maCTSP) {
        String query = " UPDATE CHI_TIET_SAN_PHAM  set SoLuongTon = ?,GiaNiemYet = ?,GiaBan = ?,MoTa = ?,TrangThai=? WHERE MaCTSP = ?";
        try {
            PreparedStatement pstm = connect.prepareCall(query);

            pstm.setInt(1, spct.getSoLuong());
            pstm.setBigDecimal(2, spct.getGiaNiemYet());
            pstm.setBigDecimal(3, spct.getGiaBan());
            pstm.setString(4, spct.getMoTa());
            pstm.setInt(5, spct.getTrangThai());
            pstm.setString(6, maCTSP);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public SanPhamChiTiet getProductByMa(String ma) {
        SanPhamChiTiet result = null;
        String sql = "select CTSP.ID ,CTSP.MaCTSP,SP.TenSP,TH.TenThuongHieu,S.TenSize,M.TenMau,CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai  from CHI_TIET_SAN_PHAM as CTSP\n"
                + "join MAU as M on M.ID = CTSP.IdMau\n"
                + "join SIZE as S on S.ID = CTSP.IdSize\n"
                + "join THUONGHIEU as TH on TH.ID = CTSP.IdThuongHieu\n"
                + "join SANPHAM as SP on SP.ID = CTSP.IdSP\n"
                + "WHERE CTSP.MaCTSP = ?";

        try {
            PreparedStatement pstm = connect.prepareCall(sql);
            pstm.setString(1, ma);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));

                result = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public List<SanPhamChiTiet> getAll_M(int page, int limt) {
        List<SanPhamChiTiet> list = new ArrayList<>();

        try {
            con = DBConnection.getConnect();
            query = ""
                    + "SELECT CTSP.ID as ID , CTSP.MaCTSP as  MaCTSP , SP.ID AS IdSP , SP.TenSP AS TenSP , S.ID AS IdSize , S.TenSize AS TenSize,\n"
                    + " TH.ID AS IdTH , TH.TenThuongHieu AS TenThuongHieu , M.ID AS IdM , M.TenMau AS TenMau ,\n"
                    + " IdDGG , DGG.MaDGG AS MaDGG , DGG.Loai AS LoaiDGG , DGG.GiaTri AS GiaTriDGG , DGG.TrangThai AS TrangThaiDGG ,\n"
                    + " SoLuongTon, GiaNiemYet , GiaBan\n"
                    + " FROM CHI_TIET_SAN_PHAM AS CTSP\n"
                    + " JOIN SANPHAM AS SP ON CTSP.IdSP = SP.ID\n"
                    + " JOIN SIZE AS S ON CTSP.IdSize = S.ID\n"
                    + " JOIN THUONGHIEU AS TH ON CTSP.IdThuongHieu =TH.ID\n"
                    + " JOIN MAU AS M ON CTSP.IdMau = M.ID\n"
                    + " LEFT JOIN DOT_GIAM_GIA  AS DGG ON CTSP.IdDGG = DGG.ID\n"
                    + " WHERE CTSP.SoLuongTon > 0"
                    + "	order by ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            rs = pstm.executeQuery();

            while (rs.next()) {
                DotGiamGia_M dgg = new DotGiamGia_M(
                        rs.getLong("IdDGG"),
                        rs.getString("MaDGG"),
                        rs.getInt("LoaiDGG"),
                        rs.getBigDecimal("GiaTriDGG"),
                        rs.getInt("TrangThaiDGG"));
// them --------
                SanPham sp = new SanPham(rs.getLong("IdSP"), rs.getString("TenSP"));
                KichThuoc kt = new KichThuoc(rs.getLong("IdSize"), rs.getFloat("TenSize"));
                MauSac mau = new MauSac(rs.getLong("IdM"), rs.getString("TenMau"));
                ThuongHieu th = new ThuongHieu(rs.getLong("IdTH"), rs.getString("TenThuongHieu"));
                SanPhamChiTiet ctspm = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"),
                        rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"),
                        mau, kt, th, sp);
                ctspm.setIdDGG(dgg);
                list.add(ctspm);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (stm != null && !stm.isClosed()) {
                    stm.close();
                }
                if (pstm != null && !pstm.isClosed()) {
                    pstm.close();
                }
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getRowCount() {
        String countSql = "SELECT COUNT(*) AS totalRows FROM CHI_TIET_SAN_PHAM "
                + " WHERE SoLuongTon > 0 ";
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
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (stm != null && !stm.isClosed()) {
                    stm.close();
                }
                if (pstm != null && !pstm.isClosed()) {
                    pstm.close();
                }
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public SanPhamChiTiet getCTSPByMaCTSP(String maCTSP) {
        try {
            query = ""
                    + "SELECT CTSP.ID as ID , CTSP.MaCTSP as  MaCTSP , SP.ID AS IdSP , SP.TenSP AS TenSP , S.ID AS IdSize , S.TenSize AS TenSize,\n"
                    + " TH.ID AS IdTH , TH.TenThuongHieu AS TenThuongHieu , M.ID AS IdM , M.TenMau AS TenMau ,\n"
                    + " IdDGG , DGG.MaDGG AS MaDGG , DGG.Loai AS LoaiDGG , DGG.GiaTri AS GiaTriDGG , DGG.TrangThai AS TrangThaiDGG ,\n"
                    + " SoLuongTon, GiaNiemYet , GiaBan\n"
                    + " FROM CHI_TIET_SAN_PHAM AS CTSP\n"
                    + " JOIN SANPHAM AS SP ON CTSP.IdSP = SP.ID\n"
                    + " JOIN SIZE AS S ON CTSP.IdSize = S.ID\n"
                    + " JOIN THUONGHIEU AS TH ON CTSP.IdThuongHieu =TH.ID\n"
                    + " JOIN MAU AS M ON CTSP.IdMau = M.ID\n"
                    + " LEFT JOIN DOT_GIAM_GIA  AS DGG ON CTSP.IdDGG = DGG.ID\n"
                    + " WHERE CTSP.MaCTSP LIKE '" + maCTSP + "'";
            con = DBConnection.getConnect();
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            while (rs.next()) {
                DotGiamGia_M dgg = new DotGiamGia_M(
                        rs.getLong("IdDGG"),
                        rs.getString("MaDGG"),
                        rs.getInt("LoaiDGG"),
                        rs.getBigDecimal("GiaTriDGG"),
                        rs.getInt("TrangThaiDGG"));

                SanPham sp = new SanPham(rs.getLong("IdSP"), rs.getString("TenSP"));
                KichThuoc kt = new KichThuoc(rs.getLong("IdSize"), rs.getFloat("TenSize"));
                MauSac mau = new MauSac(rs.getLong("IdM"), rs.getString("TenMau"));
                ThuongHieu th = new ThuongHieu(rs.getLong("IdTH"), rs.getString("TenThuongHieu"));
                SanPhamChiTiet ctspm = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"),
                        rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"),
                        mau, kt, th, sp);
                System.out.println(ctspm.toString());
                return ctspm;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamCT_Repository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getSoLuongTonByMaCTSP(String maCTSP) {
        int soLuonTon = 0;
        query = "SELECT SoLuongTon FROM CHI_TIET_SAN_PHAM WHERE MaCTSP LIKE '" + maCTSP + "' ;";
        con = DBConnection.getConnect();
        try {
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                soLuonTon = rs.getInt("SoLuongTon");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietHoaDon_RepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soLuonTon;
    }

    public void updateSLSP(int sl, String maCTSP) {
        try {
            con = DBConnection.getConnect();
            query = "update CHI_TIET_SAN_PHAM\n"
                    + "set SoLuongTon = ?\n"
                    + "where MaCTSP like ?";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, sl);
            pstm.setString(2, maCTSP);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamCT_Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSLSPByMa(String ma, int soLuong) {
        try {
            query = "UPDATE CHI_TIET_SAN_PHAM\n"
                    + "SET SoLuongTon = ?\n"
                    + "WHERE MaCTSP LIKE ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, soLuong);
            pstm.setString(2, ma);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamCT_Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
///////////////////////////////////////////////

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

    public List<SanPhamChiTiet> searchItem(Long idMau, Long idSize, Long idThuongHieu, Long idSanPham, int trangThai) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        if (connect != null) {
            try {
                StringBuilder query = new StringBuilder("SELECT CTSP.ID, CTSP.MaCTSP, SP.TenSP, TH.TenThuongHieu, S.TenSize, M.TenMau, CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai, CTSP.ID as ID FROM CHI_TIET_SAN_PHAM as CTSP\n"
                        + "JOIN MAU as M ON M.ID = CTSP.IdMau\n"
                        + "JOIN SIZE as S ON S.ID = CTSP.IdSize\n"
                        + "JOIN THUONGHIEU as TH ON TH.ID = CTSP.IdThuongHieu\n"
                        + "JOIN SANPHAM as SP ON SP.ID = CTSP.IdSP\n"
                        + " WHERE 1=1"
                );

                // Màu Sắc
                if (idMau != null) {
                    query.append(" AND M.ID = " + idMau);
                }
                // Kích Thước
                if (idSize != null) {
                    query.append(" AND S.ID = " + idSize);
                }
                // Thương Hiệu
                if (idThuongHieu != null) {
                    query.append(" AND TH.ID = " + idThuongHieu);
                }
                // Sản Phẩm
                if (idSanPham != null) {
                    query.append(" AND SP.ID = " + idSanPham);
                }

                if (trangThai >= 0) {
                    query.append("AND CTSP.TrangThai = " + trangThai);
                }

                String queryFinal = query.toString();

                PreparedStatement ps = connect.prepareStatement(queryFinal);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    SanPham sanPham = new SanPham(rs.getString("TenSP"));
                    MauSac mauSac = new MauSac(rs.getString("TenMau"));
                    ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                    KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));
                    SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
                    list.add(sanPhamChiTiet);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return list;
    }
// Đếm tổng số bản ghi 

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
