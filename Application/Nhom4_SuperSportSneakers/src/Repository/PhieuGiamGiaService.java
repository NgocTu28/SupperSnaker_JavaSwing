/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.HoaDon;
import Model.NhanVien;
import Model.PhieuGiamGia;
import Utils.XDate;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class PhieuGiamGiaService {

    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;

    public ArrayList<PhieuGiamGia> getAllPGG(int page, int limt) {
        ArrayList<PhieuGiamGia> listPGG = new ArrayList<>();
        Connection conn = DBConnection.getConnect();
        String sql = "SELECT PGG.ID , NV.HoVaTen  , nv.ID as IDNV  , MaPhieu , TenPhieu , LoaiPhieu , GiaTri , SoLuongPhieu ,  "
                + "DonToiThieu , \n"
                + "NgayBatDau , NgayKetThuc ,  PGG.NgayTao , MoTa , PGG.TrangThai FROM PHIEU_GIAM_GIA AS PGG\n"
                + "JOIN NHANVIEN AS NV ON NV.ID = PGG.IdNV"
                + "	order by ID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";;

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IDNV"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                pgg.setIdNV(nhanVien);
                pgg.setMaPhieu(rs.getString("MaPhieu"));
                pgg.setTenPhieu(rs.getString("TenPhieu"));
                pgg.setLoaiPhieu(rs.getInt(("LoaiPhieu")));
                pgg.setGiaTri(rs.getBigDecimal("GiaTri"));
                pgg.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                pgg.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
                pgg.setNgayBatDau(rs.getDate("NgayBatDau"));
                pgg.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pgg.setNgayTao(rs.getDate("NgayTao"));
                pgg.setMoTa(rs.getString("MoTa"));
                pgg.setTrangThai(rs.getInt("TrangThai"));

                listPGG.add(pgg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPGG;
    }

    public List<PhieuGiamGia> getALL(BigDecimal donToiThieu) {
        List<PhieuGiamGia> list = new ArrayList<>();
        try {
            query = " SELECT ID , MaPhieu , TenPhieu , LoaiPhieu , GiaTri , SoLuongPhieu , DonToiThieu , TrangThai  FROM PHIEU_GIAM_GIA \n"
                    + " WHERE TrangThai = 1 AND SoLuongPhieu >0 AND DonToiThieu <= ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setBigDecimal(1, donToiThieu);
            rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGiamGia giamGia = new PhieuGiamGia();
                giamGia.setIdPGG(rs.getLong("ID"));
                giamGia.setMaPhieu(rs.getString("MaPhieu"));
                giamGia.setTenPhieu(rs.getString("TenPhieu"));
                giamGia.setLoaiPhieu(rs.getInt("LoaiPhieu"));
                giamGia.setGiaTri(rs.getBigDecimal("GiaTri"));
                giamGia.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                giamGia.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
                giamGia.setTrangThai(rs.getInt("TrangThai"));
                list.add(giamGia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<PhieuGiamGia> getLoc(Date NgayBatDau, Date NgayKetThuc, int LoaiPhieu, int TrangThai) {
        ArrayList<PhieuGiamGia> listPGG = new ArrayList<>();
        Connection conn = DBConnection.getConnect();
        System.out.println(TrangThai);

        try {
            String sql = "Select IdNV, MaPhieu, TenPhieu, LoaiPhieu, GiaTri, SoLuongPhieu, DonToiThieu, NgayBatDau, NgayKetThuc,NgayTao, MoTa, TrangThai from PHIEU_GIAM_GIA ";
            ArrayList<String> whereQueries = new ArrayList<>();
            String NgayBatDauStr = null, NgayKetThucStr = null;

            if (NgayBatDau != null && NgayKetThuc != null) {
                SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
                NgayBatDauStr = spd.format(NgayBatDau);
                NgayKetThucStr = spd.format(NgayKetThuc);

                whereQueries.add("NgayBatDau >= ? AND NgayKetThuc <= ?");
            }
            whereQueries.add("LoaiPhieu = ?");
            if (TrangThai != -1) {
                whereQueries.add("TrangThai = ?");
            }
            String whereQuery = "";
            for (int i = 0; i < whereQueries.size(); i++) {
                String dieukien = whereQueries.get(i);
                whereQuery += dieukien;
                if (i != whereQueries.size() - 1) {
                    whereQuery += " AND ";
                }
            }
            System.out.println(whereQuery);

            sql += "WHERE " + whereQuery;

            PreparedStatement pr = conn.prepareStatement(sql);
            if (NgayBatDau != null && NgayKetThuc != null) {
                pr.setObject(1, NgayBatDauStr);
                pr.setObject(2, NgayKetThucStr);
                pr.setObject(3, LoaiPhieu);
                if (TrangThai != -1) {
                    pr.setObject(4, TrangThai);
                }
            } else if (TrangThai != -1) {
                pr.setObject(1, LoaiPhieu);
                pr.setObject(2, TrangThai);
            } else {
                pr.setObject(1, LoaiPhieu);
            }

            //PreparedStatement pr= conn.prepareStatement(sql);
//            pr.setObject(1, dateStart);
//            pr.setObject(2, dateEnd);
//            pr.setObject(3, loai);
//            pr.setObject(4, trangThai);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
//                NhanVien nv= new NhanVien();
//                nv.setIdNV(rs.getLong(1));
                //  pgg.setIdNV(rs.getLong(1));
                pgg.setMaPhieu(rs.getString(2));
                pgg.setTenPhieu(rs.getString(3));
                pgg.setLoaiPhieu(rs.getInt(4));
                pgg.setGiaTri(rs.getBigDecimal(5));
                pgg.setSoLuongPhieu(rs.getInt(6));
                pgg.setDonToiThieu(rs.getBigDecimal(7));
                pgg.setNgayBatDau(rs.getDate(8));
                pgg.setNgayKetThuc(rs.getDate(9));
                pgg.setNgayTao(rs.getDate(10));
                pgg.setMoTa(rs.getString(11));
                pgg.setTrangThai(rs.getInt(12));

                listPGG.add(pgg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPGG;
    }

    public List<PhieuGiamGia> getListLoc(PhieuGiamGia giamGia, int page, int limt) {
        List<PhieuGiamGia> list = new ArrayList<>();
        query = "SELECT PGG.ID , nv.ID AS IDNV , NV.HoVaTen , MaPhieu , TenPhieu , LoaiPhieu , GiaTri , \n"
                + "SoLuongPhieu , DonToiThieu , NgayBatDau , NgayKetThuc , PGG.NgayTao , MoTa , PGG.TrangThai FROM PHIEU_GIAM_GIA  AS PGG\n"
                + "JOIN NHANVIEN AS NV ON NV.ID = PGG.IdNV\n";
        ArrayList<String> where = new ArrayList<>();
        String wheres = "";
        con = DBConnection.getConnect();
        try {

            if (giamGia.getNgayBatDau() == null && giamGia.getNgayKetThuc() == null && giamGia.getLoaiPhieu() == -1 && giamGia.getTrangThai() == -1) {
                query += "order by ID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                System.out.println(query);
                pstm = con.prepareStatement(query);
                pstm.setInt(1, (page - 1) * limt);
                pstm.setInt(2, limt);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    PhieuGiamGia pgg = new PhieuGiamGia();
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setId(rs.getLong("IDNV"));
                    nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                    pgg.setIdNV(nhanVien);
                    pgg.setMaPhieu(rs.getString("MaPhieu"));
                    pgg.setTenPhieu(rs.getString("TenPhieu"));
                    pgg.setLoaiPhieu(rs.getInt(("LoaiPhieu")));
                    pgg.setGiaTri(rs.getBigDecimal("GiaTri"));
                    pgg.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                    pgg.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
                    pgg.setNgayBatDau(rs.getDate("NgayBatDau"));
                    pgg.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                    pgg.setNgayTao(rs.getDate("NgayTao"));
                    pgg.setMoTa(rs.getString("MoTa"));
                    pgg.setTrangThai(rs.getInt("TrangThai"));
                    list.add(pgg);
                }
                return list;
            }
            if (giamGia.getNgayBatDau() != null && giamGia.getNgayKetThuc() != null) {
                where.add("(NgayBatDau >= ? AND NgayKetThuc <= ? ) ");
            }
            if (giamGia.getLoaiPhieu() != -1) {
                where.add("LoaiPhieu = ?");
            }
            if (giamGia.getTrangThai() != -1) {
                where.add("PGG.TrangThai = ? ");
            }
            int count = 0;
            for (int i = 0; i < where.size(); i++) {
                int checkLast = (where.size() - 1);
                wheres += where.get(i);
                if (i != checkLast && where.size() != 1) {
                    wheres += " AND ";
                }
                count++;
            }

            query += "WHERE " + wheres + " order by ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            System.out.println(query);
            pstm = con.prepareStatement(query);
            if (giamGia.getNgayBatDau() == null && giamGia.getNgayKetThuc() == null && giamGia.getLoaiPhieu() == -1 && (giamGia.getTrangThai() != -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 6);
                pstm.setInt(1, giamGia.getTrangThai());
                pstm.setInt(2, (page - 1) * limt);
                pstm.setInt(3, limt);
            }

            if (giamGia.getNgayBatDau() == null && giamGia.getNgayKetThuc() == null && giamGia.getLoaiPhieu() != -1 && giamGia.getTrangThai() == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 7);
                pstm.setInt(1, giamGia.getLoaiPhieu());
                pstm.setInt(2, (page - 1) * limt);
                pstm.setInt(3, limt);
            }

            if (giamGia.getNgayBatDau() == null && giamGia.getNgayKetThuc() == null && giamGia.getLoaiPhieu() != -1 && giamGia.getTrangThai() != -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 8);
                pstm.setInt(1, giamGia.getLoaiPhieu());
                // sua
                pstm.setInt(2, giamGia.getTrangThai());
                pstm.setInt(3, (page - 1) * limt);
                pstm.setInt(4, limt);
            }

            if (giamGia.getNgayBatDau() != null && giamGia.getNgayKetThuc() != null && giamGia.getLoaiPhieu() != -1 && (giamGia.getTrangThai() != -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 1);
                pstm.setString(1, XDate.toString(giamGia.getNgayBatDau(), "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(giamGia.getNgayKetThuc(), "MM-dd-yyyy"));
                pstm.setInt(3, giamGia.getLoaiPhieu());
                pstm.setInt(4, giamGia.getTrangThai());
                pstm.setInt(5, (page - 1) * limt);
                pstm.setInt(6, limt);

            }
            if (giamGia.getNgayBatDau() != null && giamGia.getNgayKetThuc() != null && giamGia.getLoaiPhieu() != -1 && giamGia.getTrangThai() == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 2);
                pstm.setString(1, XDate.toString(giamGia.getNgayBatDau(), "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(giamGia.getNgayKetThuc(), "MM-dd-yyyy"));
                pstm.setInt(3, giamGia.getLoaiPhieu());
                pstm.setInt(4, (page - 1) * limt);
                pstm.setInt(5, limt);
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 2);
            }
            if (giamGia.getNgayBatDau() != null && giamGia.getNgayKetThuc() != null && giamGia.getTrangThai() != -1 && giamGia.getLoaiPhieu() == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 3);
                pstm.setString(1, XDate.toString(giamGia.getNgayBatDau(), "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(giamGia.getNgayKetThuc(), "MM-dd-yyyy"));
                pstm.setInt(3, giamGia.getTrangThai());
                pstm.setInt(4, (page - 1) * limt);
                pstm.setInt(5, limt);

            }
            if (giamGia.getNgayBatDau() != null && giamGia.getNgayKetThuc() != null && giamGia.getLoaiPhieu() == -1 && (giamGia.getTrangThai() == -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 4);

                pstm.setString(1, XDate.toString(giamGia.getNgayBatDau(), "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(giamGia.getNgayKetThuc(), "MM-dd-yyyy"));
                pstm.setInt(3, (page - 1) * limt);
                pstm.setInt(4, limt);

            }

            rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IDNV"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                pgg.setIdNV(nhanVien);
                pgg.setMaPhieu(rs.getString("MaPhieu"));
                pgg.setTenPhieu(rs.getString("TenPhieu"));
                pgg.setLoaiPhieu(rs.getInt(("LoaiPhieu")));
                pgg.setGiaTri(rs.getBigDecimal("GiaTri"));
                pgg.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                pgg.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
                pgg.setNgayBatDau(rs.getDate("NgayBatDau"));
                pgg.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pgg.setNgayTao(rs.getDate("NgayTao"));
                pgg.setMoTa(rs.getString("MoTa"));
                pgg.setTrangThai(rs.getInt("TrangThai"));
                list.add(pgg);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
            return list;
        }

    }

    public PhieuGiamGia getPGGByMa(String ma) {
        try {
            query = "SELECT PGG.ID as ID , nv.ID AS IDNV , NV.HoVaTen , MaPhieu , TenPhieu , LoaiPhieu , GiaTri , \n"
                    + "SoLuongPhieu , DonToiThieu , NgayBatDau , NgayKetThuc , PGG.NgayTao , MoTa , PGG.TrangThai FROM PHIEU_GIAM_GIA  AS PGG\n"
                    + "JOIN NHANVIEN AS NV ON NV.ID = PGG.IdNV\n"
                    + "WHERE MaPhieu LIKE '%" + ma + "%'";
            con = DBConnection.getConnect();
            System.out.println(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IDNV"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                pgg.setIdNV(nhanVien);
                pgg.setIdPGG(rs.getLong("ID"));
                pgg.setMaPhieu(rs.getString("MaPhieu"));
                pgg.setTenPhieu(rs.getString("TenPhieu"));
                pgg.setLoaiPhieu(rs.getInt(("LoaiPhieu")));
                pgg.setGiaTri(rs.getBigDecimal("GiaTri"));
                pgg.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                pgg.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
                pgg.setNgayBatDau(rs.getDate("NgayBatDau"));
                pgg.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pgg.setNgayTao(rs.getDate("NgayTao"));
                pgg.setMoTa(rs.getString("MoTa"));
                pgg.setTrangThai(rs.getInt("TrangThai"));
                return pgg;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public PhieuGiamGia getPGGByMa2(String ma) {
        try {
            query = "SELECT\n"
                    + "    PGG.ID AS ID,\n"
                    + "    MaPhieu,\n"
                    + "    TenPhieu,\n"
                    + "    LoaiPhieu,\n"
                    + "    GiaTri,\n"
                    + "    SoLuongPhieu,\n"
                    + "    DonToiThieu,\n"
                    + "    NgayBatDau,\n"
                    + "    NgayKetThuc,\n"
                    + "    PGG.NgayTao,\n"
                    + "    MoTa,\n"
                    + "    PGG.TrangThai\n"
                    + "FROM\n"
                    + "    PHIEU_GIAM_GIA AS PGG\n"
                    + "WHERE   MaPhieu LIKE '" + ma + "' ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();

                pgg.setIdPGG(rs.getLong("ID"));
                pgg.setMaPhieu(rs.getString("MaPhieu"));
                pgg.setTenPhieu(rs.getString("TenPhieu"));
                pgg.setLoaiPhieu(rs.getInt(("LoaiPhieu")));
                pgg.setGiaTri(rs.getBigDecimal("GiaTri"));
                pgg.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                pgg.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
                pgg.setNgayBatDau(rs.getDate("NgayBatDau"));
                pgg.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pgg.setNgayTao(rs.getDate("NgayTao"));
                pgg.setMoTa(rs.getString("MoTa"));
                pgg.setTrangThai(rs.getInt("TrangThai"));
                return pgg;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static NhanVien getNhanVien(long idPGG) {
        //ArrayList<PhieuGiamGia> listPGG= new ArrayList<>();
        NhanVien nv = null;
        Connection conn = DBConnection.getConnect();
        String sql = "select NHANVIEN.ID,HoVaTen from PHIEU_GIAM_GIA join NHANVIEN on PHIEU_GIAM_GIA.ID= NHANVIEN.ID where PHIEU_GIAM_GIA.ID=?";

        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setObject(1, idPGG);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                nv = new NhanVien();
                nv.setId(rs.getLong("ID"));
                nv.setTenNhanVien(rs.getString("HoVaTen"));
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

    public List<Object> getAllHDByMaPhieu(String ma) {
        List<Object> list = new ArrayList<>();
        try {
            query = "SELECT HOADON.MaHoaDon , PHIEU_GIAM_GIA.MaPhieu , HOADON.NgayTao , PHIEU_GIAM_GIA.LoaiPhieu , PHIEU_GIAM_GIA.GiaTri FROM PHIEU_GIAM_GIA\n"
                    + "JOIN HOADON ON HOADON.IdPGG = PHIEU_GIAM_GIA.ID\n"
                    + "WHERE MaPhieu LIKE ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, ma);
            rs = pstm.executeQuery();
            int i = 1;
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setNgayTao(rs.getDate("NgayTao"));
                hd.setMaHoaDon(rs.getString("MaHoaDon"));
                PhieuGiamGia pgg = new PhieuGiamGia();
                pgg.setMaPhieu(rs.getString("MaPhieu"));
                pgg.setLoaiPhieu(0);
                Object[] ob = new Object[]{
                    i, rs.getString("MaHoaDon"), rs.getString("MaPhieu"), rs.getDate("NgayTao"), rs.getInt("LoaiPhieu") == 0 ? rs.getFloat("GiaTri") + " % "
                    : rs.getFloat("GiaTri") + " VND "
                };
                i++;
                list.add(ob);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

//     public ArrayList<PhieuGiamGia> getLoc(Date NgayBatDau, Date NgayKetThuc, int LoaiPhieu, int TrangThai){
//        ArrayList<PhieuGiamGia> listPGG= new ArrayList<>();
//        Connection conn= DBContext.getConnection();
//        System.out.println(TrangThai);
//        
//        try {
//            String sql="Select IdNV, MaPhieu, TenPhieu, LoaiPhieu, GiaTri, SoLuongPhieu, DonToiThieu, NgayBatDau, NgayKetThuc,NgayTao, MoTa, TrangThai from PHIEU_GIAM_GIA ";
//            ArrayList<String> whereQueries = new ArrayList<>();
//            String NgayBatDauStr = null, NgayKetThucStr = null;
//            
//            if (NgayBatDau != null && NgayKetThuc != null) {
//                SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
//                NgayBatDauStr = spd.format(NgayBatDau);
//                NgayKetThucStr = spd.format(NgayKetThuc);
//                
//                whereQueries.add("NgayBatDau >= ? AND NgayKetThuc <= ?");
//            }
//            whereQueries.add("LoaiPhieu = ?");
//            if (TrangThai != -1) {
//                whereQueries.add("TrangThai = ?");
//            }
//            String whereQuery = "";
//            for (int i = 0; i < whereQueries.size(); i++) {
//                String dieukien = whereQueries.get(i);
//                whereQuery += dieukien;
//                if (i != whereQueries.size() - 1) {
//                    whereQuery += " AND ";
//                }
//            }
//            System.out.println(whereQuery);
//           
//            sql += "WHERE " + whereQuery;
//            
//            PreparedStatement pr= conn.prepareStatement(sql);
//            if (NgayBatDau != null && NgayKetThuc != null) {
//                pr.setObject(1, NgayBatDauStr);
//                pr.setObject(2, NgayKetThucStr);
//                pr.setObject(3, LoaiPhieu);
//                if (TrangThai != -1){
//                    pr.setObject(4, TrangThai);
//                }
//            } else if (TrangThai != -1) {
//                pr.setObject(1, LoaiPhieu);
//                pr.setObject(2, TrangThai);
//            } else {
//                pr.setObject(1, LoaiPhieu);
//            }
//            
//        
//            //PreparedStatement pr= conn.prepareStatement(sql);
////            pr.setObject(1, dateStart);
////            pr.setObject(2, dateEnd);
////            pr.setObject(3, loai);
////            pr.setObject(4, trangThai);
//            
//            ResultSet rs= pr.executeQuery();
//            while(rs.next()){
//                PhieuGiamGia pgg= new PhieuGiamGia();
////                NhanVien nv= new NhanVien();
////                nv.setIdNV(rs.getLong(1));
//                pgg.setIdNV(rs.getLong(1));
//                pgg.setMaPhieu(rs.getString(2));
//                pgg.setTenPhieu(rs.getString(3));
//                pgg.setLoaiPhieu(rs.getInt(4));
//                pgg.setGiaTri(rs.getFloat(5));
//                pgg.setSoLuongPhieu(rs.getInt(6));
//                pgg.setDonToiThieu(rs.getBigDecimal(7));
//                pgg.setNgayBatDau(rs.getDate(8));
//                pgg.setNgayKetThuc(rs.getDate(9));
//                pgg.setNgayTao(rs.getDate(10));
//                pgg.setMoTa(rs.getString(11));
//                pgg.setTrangThai(rs.getInt(12));
//                
//                listPGG.add(pgg);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return listPGG;
//    }
    public Integer ThemPGG(PhieuGiamGia pgg) {
        Integer row = null;
        Connection conn = DBConnection.getConnect();
        String sql = "INSERT INTO PHIEU_GIAM_GIA (IdNV , MaPhieu , TenPhieu , LoaiPhieu , GiaTri , SoLuongPhieu , DonToiThieu , NgayBatDau , NgayKetThuc , MoTa)\n"
                + "VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        try {
            PreparedStatement pr = conn.prepareCall(sql);
            pr.setObject(1, pgg.getIdNV().getId());
            pr.setObject(2, pgg.getMaPhieu());
            pr.setObject(3, pgg.getTenPhieu());
            pr.setObject(4, pgg.getLoaiPhieu());
            pr.setObject(5, pgg.getGiaTri());
            pr.setObject(6, pgg.getSoLuongPhieu());
            pr.setObject(7, pgg.getDonToiThieu());
            pr.setObject(8, pgg.getNgayBatDau());
            pr.setObject(9, pgg.getNgayKetThuc());
            pr.setObject(10, pgg.getMoTa());
            row = pr.executeUpdate();
        } catch (Exception e) {
        }
        return row;
    }

    public Integer SuaPGG(PhieuGiamGia pgg) {
        Integer row = null;
        Connection conn = DBConnection.getConnect();
        String sql = "UPDATE PHIEU_GIAM_GIA\n"
                + "SET TenPhieu = ? , LoaiPhieu = ? , GiaTri =? , SoLuongPhieu = ? , DonToiThieu = ? , NgayBatDau = ? , NgayKetThuc = ? , MoTa =? \n"
                + "WHERE MaPhieu LIKE ?";
        try {
            PreparedStatement pr = conn.prepareCall(sql);
            pr.setObject(1, pgg.getTenPhieu());
            pr.setObject(2, pgg.getLoaiPhieu());
            pr.setObject(3, pgg.getGiaTri());
            pr.setObject(4, pgg.getSoLuongPhieu());
            pr.setObject(5, pgg.getDonToiThieu());
            pr.setObject(6, pgg.getNgayBatDau());
            pr.setObject(7, pgg.getNgayKetThuc());
            pr.setObject(8, pgg.getMoTa());
            pr.setObject(9, pgg.getMaPhieu());

            row = pr.executeUpdate();
        } catch (Exception e) {
        }
        return row;
    }

    public Integer XoaPGG(String ma) {
        Integer row = null;
        Connection conn = DBConnection.getConnect();
        String sql = "DELETE PHIEU_GIAM_GIA WHERE MaPhieu=?";
        try {
            PreparedStatement pr = conn.prepareCall(sql);
            pr.setObject(1, ma);

            row = pr.executeUpdate();
        } catch (Exception e) {
        }
        return row;
    }

    public ArrayList<PhieuGiamGia> getPhanTrang(int page) {
//        paginate();
        ArrayList<PhieuGiamGia> listPGG = new ArrayList<>();
        int startRow = (page - 1) * 10 + 1;
        int endRow = page * 10;
        Connection conn = DBConnection.getConnect();
        String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY id) AS rownum,* FROM PHIEU_GIAM_GIA ) as PGG_NUMROW  WHERE rownum between ? and ?";
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setObject(1, startRow);
            pr.setObject(2, endRow);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
//                NhanVien nv= new NhanVien();
//                nv.setIdNV(rs.getLong("IdNV"));
                pgg.setIdPGG(rs.getLong("ID"));
                //     pgg.setIdNV(rs.getLong("IDNV"));
                pgg.setMaPhieu(rs.getString("MaPhieu"));
                pgg.setTenPhieu(rs.getString("TenPhieu"));
                pgg.setLoaiPhieu(rs.getInt("LoaiPhieu"));
                pgg.setGiaTri(rs.getBigDecimal("GiaTri"));
                pgg.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                pgg.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
                pgg.setNgayBatDau(rs.getDate("NgayBatDau"));
                pgg.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pgg.setNgayTao(rs.getDate("NgayTao"));
                pgg.setMoTa(rs.getString("MoTa"));
                pgg.setTrangThai(rs.getInt("TrangThai"));

                listPGG.add(pgg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPGG;
    }

    public Integer countPageItem(int page) {
//        paginate();
        int startRow = (page - 1) * 10 + 1;
//        int endRow= page * 10;
        Connection conn = DBConnection.getConnect();
        String sql = "SELECT TOP 1 * FROM (SELECT ROW_NUMBER() OVER (ORDER BY id) AS rownum,COUNT(ID) AS sl FROM PHIEU_GIAM_GIA GROUP BY ID) as PGG_NUMROW  WHERE rownum >= ?";
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setObject(1, startRow);
            //pr.setObject(2, endRow);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt("sl");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<PhieuGiamGia> listPGG_TT_1() {
        List<PhieuGiamGia> list = new ArrayList<>();
        try {
            query = "SELECT ID, MaPhieu , TenPhieu , LoaiPhieu , GiaTri , SoLuongPhieu , DonToiThieu FROM PHIEU_GIAM_GIA\n"
                    + "                    WHERE TrangThai =1 and SoLuongPhieu >0";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGiamGia phieuGiamGia = new PhieuGiamGia();

                phieuGiamGia.setIdPGG(rs.getLong("ID"));
                phieuGiamGia.setMaPhieu(rs.getString("MaPhieu"));
                phieuGiamGia.setTenPhieu(rs.getString("TenPhieu"));
                phieuGiamGia.setLoaiPhieu(rs.getInt("LoaiPhieu"));
                phieuGiamGia.setGiaTri(rs.getBigDecimal("GiaTri"));
                phieuGiamGia.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                phieuGiamGia.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
                list.add(phieuGiamGia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getRowCountPGG() {
        String countSql = "SELECT COUNT(*) AS totalRows FROM PHIEU_GIAM_GIA";
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

    public Integer updateSoLuongPhieu(Long idPGG, Integer soLuong) {
        try {
            con = DBConnection.getConnect();
            query = "UPDATE PHIEU_GIAM_GIA\n"
                    + "SET SoLuongPhieu = ?\n"
                    + "WHERE ID = ?";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, soLuong);
            pstm.setLong(2, idPGG);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    public void updateKT(String date) {
        try {
            query = "UPDATE PHIEU_GIAM_GIA\n"
                    + "SET TrangThai = 2\n"
                    + "WHERE NgayKetThuc <'" + date + "' or SoLuongPhieu =0 ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateBD(String date) {
        try {
            query = "UPDATE PHIEU_GIAM_GIA\n"
                    + "SET TrangThai = 1\n"
                    + "WHERE NgayBatDau = '"+date+"' and SoLuongPhieu >0";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuGiamGiaService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
