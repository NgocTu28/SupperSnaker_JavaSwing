/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.ChiTietDotGiamGia;
import Model.DotGiamGia_M;
import Model.KichThuoc;
import Model.MauSac;

import Model.SanPham;
import Model.SanPhamChiTiet;
import Model.ThuongHieu;
import Utils.Format;
import java.math.BigDecimal;
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
public class ChiTietDotGiamRepository {

    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;

    public List<ChiTietDotGiamGia> getAll() {
        List<ChiTietDotGiamGia> list = new ArrayList<>();
        con = DBConnection.getConnect();
        query = "";
        return null;

    }

    public List<ChiTietDotGiamGia> getAllByIDDgg(Long idDGG) {
        try {
            List<ChiTietDotGiamGia> list = new ArrayList<>();
            query = "SELECT IdDGG AS IDDGG , IdCTSP , MaCTSP , DonGia , DonGiaConLai , GiaTriGiam FROM CHI_TIET_DGG AS CTDGG\n"
                    + "LEFT JOIN DOT_GIAM_GIA AS DGG ON DGG.ID = CTDGG.IdDGG\n"
                    + " WHERE IDDGG = ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, idDGG);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ChiTietDotGiamGia chiTietDotGiamGia = new ChiTietDotGiamGia();
                DotGiamGia_M dgg = new DotGiamGia_M();
                dgg.setIdDGG(rs.getLong("IDDGG"));
                chiTietDotGiamGia.setIdDGG(dgg);
                chiTietDotGiamGia.setIdCTSP(rs.getLong("IdCTSP"));
                chiTietDotGiamGia.setMsSP(rs.getString("MaCTSP"));
                chiTietDotGiamGia.setDonGia(rs.getBigDecimal("DonGia"));
                chiTietDotGiamGia.setDonGiaConLai(rs.getBigDecimal("DonGiaConLai"));
                chiTietDotGiamGia.setGiaTriGiam(rs.getFloat("GiaTriGiam"));
                list.add(chiTietDotGiamGia);

            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public List<ChiTietDotGiamGia> getAllCT_CTDGG() {
        List<ChiTietDotGiamGia> list = new ArrayList<>();
        try {

            con = DBConnection.getConnect();
            query = "SELECT IdDGG AS IDDGG , IdCTSP , MaCTSP , DonGia , DonGiaConLai , GiaTriGiam ,DGG.TrangThai FROM CHI_TIET_DGG AS CTDGG\n"
                    + "    LEFT JOIN DOT_GIAM_GIA AS DGG ON DGG.ID = CTDGG.IdDGG\n"
                    + "					WHERE DGG.TrangThai =1";

            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ChiTietDotGiamGia ctdgg = new ChiTietDotGiamGia();
                DotGiamGia_M dgg = new DotGiamGia_M();
                dgg.setIdDGG(rs.getLong("IDDGG"));
                dgg.setTrangThai(rs.getInt("TrangThai"));

                ctdgg.setIdDGG(dgg);
                ctdgg.setIdCTSP(rs.getLong("IdCTSP"));
                ctdgg.setMsSP(rs.getString("MaCTSP"));
                ctdgg.setDonGia(rs.getBigDecimal("DonGia"));
                ctdgg.setDonGiaConLai(rs.getBigDecimal("DonGiaConLai"));
                ctdgg.setGiaTriGiam(rs.getFloat("GiaTriGiam"));
                list.add(ctdgg);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<ChiTietDotGiamGia> getAllCT_CTDGG_KT() {
        List<ChiTietDotGiamGia> list = new ArrayList<>();
        try {

            con = DBConnection.getConnect();
            query = "SELECT IdDGG AS IDDGG , IdCTSP , MaCTSP , DonGia , DonGiaConLai , GiaTriGiam ,DGG.TrangThai FROM CHI_TIET_DGG AS CTDGG\n"
                    + "    LEFT JOIN DOT_GIAM_GIA AS DGG ON DGG.ID = CTDGG.IdDGG\n"
                    + "					WHERE DGG.TrangThai =2";

            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ChiTietDotGiamGia ctdgg = new ChiTietDotGiamGia();
                DotGiamGia_M dgg = new DotGiamGia_M();
                dgg.setIdDGG(rs.getLong("IDDGG"));
                dgg.setTrangThai(rs.getInt("TrangThai"));

                ctdgg.setIdDGG(dgg);
                ctdgg.setIdCTSP(rs.getLong("IdCTSP"));
                ctdgg.setMsSP(rs.getString("MaCTSP"));
                ctdgg.setDonGia(rs.getBigDecimal("DonGia"));
                ctdgg.setDonGiaConLai(rs.getBigDecimal("DonGiaConLai"));
                ctdgg.setGiaTriGiam(rs.getFloat("GiaTriGiam"));
                list.add(ctdgg);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void update_SP(ChiTietDotGiamGia ctdgg) {
        try {
            String sql = "UPDATE CHI_TIET_SAN_PHAM\n"
                    + "	SET IdDGG = ? ,  GiaBan = ? \n"
                    + "	WHERE ID = ? ";

            Connection c = DBConnection.getConnect();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setObject(1, ctdgg.getIdDGG().getIdDGG());
            ps.setBigDecimal(2, ctdgg.getDonGiaConLai());
            ps.setLong(3, ctdgg.getIdCTSP());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(DotGiamGia_M dgg, ChiTietDotGiamGia ctdgg) {
        try {
            query = "UPDATE CHI_TIET_DGG \n"
                    + "SET DonGia = ? , DonGiaConLai = ? , GiaTriGiam = ? \n"
                    + "WHERE IdCTSP = ? AND IdDGG = ?  AND MaCTSP LIKE ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setBigDecimal(1, ctdgg.getDonGia());
            pstm.setObject(2, ctdgg.getDonGiaConLai());
            pstm.setObject(3, dgg.getGiaTri());
            pstm.setLong(4, ctdgg.getIdCTSP());
            pstm.setLong(5, dgg.getIdDGG());
            pstm.setString(6, ctdgg.getMsSP());
            pstm.execute();

        } catch (Exception ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
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

    public SanPhamChiTiet getProductByID(Long id) {

        String sql = "SELECT CTSP.ID, CTSP.MaCTSP, SP.TenSP, TH.TenThuongHieu, S.TenSize, M.TenMau, CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai \n"
                + "FROM CHI_TIET_SAN_PHAM AS CTSP\n"
                + "JOIN MAU AS M ON M.ID = CTSP.IdMau\n"
                + "JOIN SIZE AS S ON S.ID = CTSP.IdSize\n"
                + "JOIN THUONGHIEU AS TH ON TH.ID = CTSP.IdThuongHieu\n"
                + "JOIN SANPHAM AS SP ON SP.ID = CTSP.IdSP\n"
                + "WHERE CTSP.ID = ?;";

        try {
            SanPhamChiTiet result = null;
            Connection con = DBConnection.getConnect();
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));

                result = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Object> listSPCTDGG(String maDGG) {
        System.out.println("Repository.ChiTietDotGiamRepository.listSPCTDGG()" + maDGG);
        List<Object> list = new ArrayList<>();
        try {
            query = "SELECT IdCTSP , IdDGG , MaDGG, CTDGG.DonGia , GiaTriGiam , loai ,GiaTri FROM CHI_TIET_DGG AS CTDGG \n"
                    + " JOIN DOT_GIAM_GIA AS DGG ON DGG.ID = CTDGG.IdDGG \n"
                    + " WHERE MaDGG LIKE '" + maDGG.trim() + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            int i = 1;
            SanPhamChiTiet spct;

            while (rs.next()) {
                spct = null;
                spct = this.getProductByID(rs.getLong("IdCTSP"));
//                System.out.println("ID "+ spct.getIdSPCT());
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                Object[] ob = new Object[]{
                    i,
                    spct.getMaSPCT(),
                    spct.getIdSanPham().getTenSanpham(),
                    spct.getIdThuongHieu().getTenThuongHieu(),
                    spct.getIdMau().getTenMau(),
                    spct.getIdKichThuoc().getTenSize(),
                    Format.format(donGia) ,
                    rs.getInt("loai") == 0 ? rs.getBigDecimal("GiaTriGiam") + " ( % )" : rs.getBigDecimal("GiaTriGiam") + " ( VND )"

                };

                list.add(ob);
                i++;
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void updaet(ChiTietDotGiamGia cTDGG, DotGiamGia_M dgg, SanPhamChiTiet spct) {
        try {
            query = "UPDATE CHI_TIET_DGG\n"
                    + "SET DonGia = ? , DonGiaConLai = ? , GiaTriGiam = ?\n"
                    + "WHERE IdDGG = ? AND IdCTSP = ? ";
            con = DBConnection.getConnect();
            pstm = con.prepareCall(query);
            pstm.setBigDecimal(1, spct.getGiaNiemYet());
            pstm.setBigDecimal(2, cTDGG.getDonGiaConLai());
            pstm.setFloat(3, Float.parseFloat(dgg.getGiaTri() + ""));
            pstm.setLong(4, dgg.getIdDGG());
            pstm.setLong(5, spct.getIdSPCT());
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int insert(ChiTietDotGiamGia cTDGG, DotGiamGia_M dgg, SanPhamChiTiet spct) {

        try {
            query = "INSERT INTO CHI_TIET_DGG (IdDGG , IdCTSP , MaCTSP , DonGia , DonGiaConLai , GiaTriGiam) VALUES \n"
                    + "(? , ? , ? , ? , ? , ?)";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, dgg.getIdDGG());
            pstm.setLong(2, spct.getIdSPCT());
            pstm.setString(3, spct.getMaSPCT());
            pstm.setBigDecimal(4, spct.getGiaNiemYet());
            pstm.setBigDecimal(5, cTDGG.getDonGiaConLai());
            pstm.setFloat(6, Float.parseFloat(dgg.getGiaTri() + ""));
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public SanPhamChiTiet getSPCTByMa(String ma) {

        try {
            query = "SELECT id , MaCTSP , GiaNiemYet FROM CHI_TIET_SAN_PHAM WHERE MaCTSP LIKE '" + ma + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            SanPhamChiTiet spct = new SanPhamChiTiet();
            if (rs.next()) {

                spct.setIdSPCT(rs.getLong("id"));
                spct.setMaSPCT(rs.getString("MaCTSP"));
                spct.setGiaNiemYet(rs.getBigDecimal("GiaNiemYet"));
            }
            return spct;
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void updateTrangIDGG_SP(Long id) {
        try {
            query = "UPDATE CHI_TIET_SAN_PHAM\n"
                    + "SET IdDGG = NULL , GiaBan = GiaNiemYet\n"
                    + "WHERE ID = " + id + "";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietDotGiamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
