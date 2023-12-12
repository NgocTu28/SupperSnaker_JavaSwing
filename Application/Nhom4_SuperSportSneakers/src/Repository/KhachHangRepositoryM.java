/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.KhachHang;
import Model.NhanVien;
import Utils.XDate;
import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manhnt
 */
public class KhachHangRepositoryM {

    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;

    public List<KhachHang> getAll(int page, int limt) {
        try {
            query = "select KH.ID as ID , NV.ID as IdNV , MaKhachHang , TenKhachHang , KH.GioiTinh as GioiTinh , KH.SDT as SDT  , KH.DiaChi as DiaChi, KH.Email as Email, KH.NgaySinh as NgaySinh , Diem  , CapBac from KHACHHANG as KH\n"
                    + "	join NHANVIEN as NV on NV.ID = KH.IdNV\n"
                    + "	order by ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            rs = pstm.executeQuery();
            List<KhachHang> list = new ArrayList<>();
            while (rs.next()) {
                KhachHang khachHang = new KhachHang(rs.getLong("ID"), new NhanVien(rs.getLong("IdNV")),
                        rs.getString("MaKhachHang"), rs.getString("TenKhachHang"),
                        rs.getString("SDT"),
                        rs.getDate("NgaySinh"),
                        rs.getBoolean("GioiTinh"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"), rs.getInt("Diem"), rs.getInt("CapBac"));
                list.add(khachHang);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<KhachHang> getAll2() {
        try {
            query = "select KH.ID as ID , NV.ID as IdNV , MaKhachHang , TenKhachHang , KH.GioiTinh as GioiTinh , KH.SDT as SDT  , KH.DiaChi as DiaChi, KH.Email as Email, KH.NgaySinh as NgaySinh , Diem  , CapBac from KHACHHANG as KH\n"
                    + "	join NHANVIEN as NV on NV.ID = KH.IdNV\n";

            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            List<KhachHang> list = new ArrayList<>();
            while (rs.next()) {
                KhachHang khachHang = new KhachHang(rs.getLong("ID"), new NhanVien(rs.getLong("IdNV")),
                        rs.getString("MaKhachHang"), rs.getString("TenKhachHang"),
                        rs.getString("SDT"),
                        rs.getDate("NgaySinh"),
                        rs.getBoolean("GioiTinh"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"), rs.getInt("Diem"), rs.getInt("CapBac"));
                list.add(khachHang);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<KhachHang> listSearch(String thongTin, int page, int limt) {
        try {
            query = "SELECT ID, IdNV , MaKhachHang , TenKhachHang  , SDT , GioiTinh , NgaySinh , Email , DiaChi , Diem , CapBac FROM KHACHHANG \n"
                    + "    WHERE MaKhachHang LIKE ? OR TenKhachHang LIKE ? OR SDT LIKE  ? \n"
                    + "	order by ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, "%" + thongTin + "%");
            pstm.setString(2, "%" + thongTin + "%");
            pstm.setString(3, "%" + thongTin + "%");
            pstm.setInt(4, (page - 1) * limt);
            pstm.setInt(5, limt);
            rs = pstm.executeQuery();
            List<KhachHang> list = new ArrayList<>();
            while (rs.next()) {
                KhachHang khachHang = new KhachHang(new NhanVien(rs.getLong("IdNV")),
                        rs.getString("MaKhachHang"), rs.getString("TenKhachHang"), rs.getString("SDT"),
                        rs.getDate("NgaySinh"),
                        rs.getString("Email"), rs.getBoolean("GioiTinh"),
                        rs.getString("DiaChi"), rs.getInt("Diem"), rs.getInt("CapBac"));
                list.add(khachHang);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
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

    public KhachHang findKHByMaKH(String maKH) {
        try {
            query = "SELECT ID, IdNV , MaKhachHang , TenKhachHang  , SDT , GioiTinh , NgaySinh , Email , DiaChi , Diem , CapBac FROM KHACHHANG \n"
                    + "    WHERE MaKhachHang LIKE ? ";

            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, "%" + maKH + "%");

            rs = pstm.executeQuery();
            List<KhachHang> list = new ArrayList<>();
            if (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setId(rs.getLong("ID"));
                khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachHang.setSdt(rs.getString("SDT"));
                khachHang.setNgaySinh(rs.getDate("NgaySinh"));
                khachHang.setEmail(rs.getString("Email"));
                khachHang.setGioiTinh(rs.getBoolean("GioiTinh"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                khachHang.setDiem(rs.getInt("Diem"));
                khachHang.setCapBac(rs.getInt("CapBac"));
                return khachHang;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<KhachHang> listSearchCbo(int gioiTinh, int page, int limt) {
        try {
            query = "SELECT ID, IdNV , MaKhachHang , TenKhachHang  , SDT , GioiTinh , NgaySinh , Email , DiaChi , Diem , CapBac FROM KHACHHANG \n"
                    + " WHERE GioiTinh = " + gioiTinh + ""
                    + "	order by ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, (page - 1) * limt);
            pstm.setInt(2, limt);
            rs = pstm.executeQuery();
            List<KhachHang> list = new ArrayList<>();
            while (rs.next()) {
                KhachHang khachHang = new KhachHang(new NhanVien(rs.getLong("IdNV")),
                        rs.getString("MaKhachHang"), rs.getString("TenKhachHang"), rs.getString("SDT"),
                        rs.getDate("NgaySinh"),
                        rs.getString("Email"), rs.getBoolean("GioiTinh"),
                        rs.getString("DiaChi"), rs.getInt("Diem"), rs.getInt("CapBac"));
                list.add(khachHang);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
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

    public int getRowCountKH() {
        String countSql = "SELECT COUNT(*) AS totalRows FROM KHACHHANG";
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

    public KhachHang findKhBySDT(String sdt) {
        KhachHang khachHang = new KhachHang();
        try {
            query = "SELECT ID , MaKhachHang , TenKhachHang , SDT , Email , DiaChi , Diem , CapBac   FROM KHACHHANG\n"
                    + "WHERE SDT LIKE '" + sdt + "';";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                khachHang.setId(rs.getLong("ID"));
                khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachHang.setSdt(rs.getString("SDT"));
                khachHang.setEmail(rs.getString("Email"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                khachHang.setDiem(rs.getInt("Diem"));
                khachHang.setCapBac(rs.getInt("CapBac"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return khachHang;
    }

    public int insertKH_BH(KhachHang khachHang) {
        try {
            query = "INSERT INTO  KHACHHANG  ( IdNV ,MaKhachHang , TenKhachHang , SDT) VALUES \n"
                    + "(? , ? , ? ,?)";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, khachHang.getIdNV().getId());
            pstm.setString(2, khachHang.getMaKhachHang());
            pstm.setString(3, khachHang.getTenKhachHang());
            pstm.setString(4, khachHang.getSdt());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int insertAll(KhachHang khachHang) {
        try {
            query = "INSERT INTO KHACHHANG ( IdNV ,  MaKhachHang , TenKhachHang, SDT, NgaySinh , GioiTinh, Email , DiaChi ) \n"
                    + "	VALUES (?,  ? , ? , ?, ? , ?, ? , ?) ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, khachHang.getIdNV().getId());
            pstm.setString(2, khachHang.getMaKhachHang());
            pstm.setString(3, khachHang.getTenKhachHang());
            pstm.setString(4, khachHang.getSdt());
            pstm.setString(5, XDate.toString(khachHang.getNgaySinh(), "MM-dd-yyyy"));
            pstm.setInt(6, khachHang.isGioiTinh() ? 1 : 0);
            pstm.setString(7, khachHang.getEmail());
            pstm.setString(8, khachHang.getDiaChi());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
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

    public int update(KhachHang khachHang) {
        try {
            query = "UPDATE KHACHHANG\n"
                    + "	SET TenKhachHang = ?, SDT = ?, NgaySinh = ? , GioiTinh = ?, Email = ? , DiaChi = ?\n"
                    + "	WHERE MaKhachHang LIKE ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, khachHang.getTenKhachHang());
            pstm.setString(2, khachHang.getSdt());
            pstm.setString(3, XDate.toString(khachHang.getNgaySinh(), "MM-dd-yyyy"));
            pstm.setInt(4, khachHang.isGioiTinh() ? 1 : 0);
            pstm.setString(5, khachHang.getEmail());
            pstm.setString(6, khachHang.getDiaChi());
            pstm.setString(7, khachHang.getMaKhachHang());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
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

    public List<Object> listLSGDByMaKH(String maKh, int page, int limt) {
        try {
            query = "SELECT HD.ID AS ID , HD.MaHoaDon AS MaHoaDon , hd.NgayThanhToan AS NgayThanhToan , \n"
                    + "	HD.ThanhTien AS ThanhTien , HD.TrangThai AS TrangThai , \n"
                    + "	HD.DiemDoi AS DiemDoi , HD.CapBac AS CapBac   FROM HOADON AS HD\n"
                    + "	JOIN KHACHHANG ON KHACHHANG.ID = HD.IdKH\n"
                    + "	WHERE MaKhachHang = ? \n";
            //+ "	order by ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, maKh);
//            pstm.setInt(2, (page - 1) * limt);
//            pstm.setInt(3, limt);
            rs = pstm.executeQuery();
            List<Object> listLSGD = new ArrayList<>();
            int i = 0;
            DecimalFormat decimalFormat = new DecimalFormat("#,##");
            while (rs.next()) {
                int capBacNum = rs.getInt("CapBac");;
                String capBacStr = (capBacNum == 0 ? "Đồng" : (capBacNum == 1 ? "Bạc" : (capBacNum == 2 ? "Vàng" : "Không có")));
                Object[] lSGD = new Object[]{
                    i,
                    rs.getString("MaHoaDon"),
                    rs.getDate("NgayThanhToan"),
                    decimalFormat.format(rs.getFloat("ThanhTien")) + " VNĐ",
                    rs.getInt("DiemDoi"),
                    capBacStr,
                    rs.getInt("TrangThai") == 1 ? "Đã thanh toán" : "Đang cập nhập"
                };
                listLSGD.add(lSGD);
                i++;
            }
            return listLSGD;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
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

    public int getRowCountHDByMaKH(String maKH) {
        query = "SELECT COUNT(*) AS totalRows FROM HOADON \n"
                + "	JOIN KHACHHANG ON KHACHHANG.ID = HOADON.IdKH\n"
                + "	WHERE MaKhachHang = ? ";
        con = DBConnection.getConnect();
        try {
            pstm = con.prepareStatement(maKH);
            pstm.setString(1, maKH);
            rs = stm.executeQuery(query);
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

    public List<Object> formLSGD(String str, String ma) {
        try {
            query = "				 SELECT \n"
                    + "                  HDCT.ID AS IdHDCT , HD.ID  AS IdHD , HD.MaHoaDon AS MaHoaDon  , HD.NgayTao AS NgayTao , HD.TrangThai AS TrangThaiHD , HD.CapBac AS CAPBAC , HD.PhanTramGia AS PhanTramGia, HD.TienPhieuGiam AS TienPhieuGiam , HD.DiemDoi AS DiemDoi , HD.PhuongThucTT AS PhuongThucTT , \n"
                    + "				HD.NgayThanhToan AS NgayThanhToanHD,    HD.TienKhDua AS TienKhDua , HD.TienKhChuyenKhoan AS TienKhChuyenKhoan ,HD.TienThua AS TienThua , HD.ThanhTien AS ThanhTien , HD.HinhThucMua AS  HinhThucMua , HD.TrangThai AS TrangThaiHD ,\n"
                    + "				   CTSP.ID AS IdCTSP,CTSP.MaCTSP AS MaCTSP , CTSP.GiaBan AS GiaBan ,  CTSP.GiaNiemYet  AS GiaNiemYet,\n"
                    + "				   SP.ID AS IDSP ,SP.MaSP AS MASP , SP.TenSP AS TENSP ,\n"
                    + "				   DGG.ID AS IDDGG , DGG.MaDGG AS MADGG ,  DGG.TenDGG AS TENDGG , DGG.Loai AS LOAIDGG , DGG.GiaTri AS GIATRI , DGG.NgayBatDau AS NGAYBD , DGG.NgayKetThuc AS NGAYKT , DGG.MoTa AS MOTA , DGG.TrangThai TRANGTHAIDGG,\n"
                    + "				   NV.ID AS IdNV , NV.HoVaTen AS TenNV , NV.MaNhanVien AS MaNhanVien , \n"
                    + "				  KH.ID AS IdKH , KH.TenKhachHang AS TenKH , KH.MaKhachHang AS  MaKhachHang ,\n"
                    + "				PGG.ID AS IdPGG , PGG.MaPhieu AS  MaPhieu , PGG.TenPhieu AS TenPhieu , PGG.GiaTri AS GiaTri  , PGG.LoaiPhieu AS  LoaiPhieu, PGG.SoLuongPhieu AS SoLuongPhieu  , PGG.DonToiThieu AS DonToiThieu  , PGG.TrangThai AS TrangThaiPGG ,\n"
                    + "				 HDCT.IdCTSP AS IdCTSP , HDCT.SoLuong AS SoLuongHDCT , HDCT.MaDGG AS MaDGGHDCT , HDCT.LoaiDGG AS LoaiGGHDCT , HDCT.GiaTriDGG AS GiaTriHDCT , HDCT.QuyDoiDGGTT AS QuyDoiDGGTT , HDCT.GiaBan AS GiaBanHDCT , HDCT.DonGia AS DonGiaHDCT  , HDCT.ThanhTien AS ThanhtIENHDCT\n"
                    + "              , S.TenSize as ts , M.TenMau as tm , TH.TenThuongHieu as tth\n"
                    + "			  FROM HOADONCHITIET AS HDCT\n"
                    + "			LEFT JOIN HOADON AS HD ON HD.ID = HDCT.IdHoaDon\n"
                    + "			LEFT JOIN CHI_TIET_SAN_PHAM AS CTSP ON CTSP.ID = HDCT.IdCTSP\n"
                    + "			LEFT JOIN SANPHAM AS SP  ON SP.ID = CTSP.ID\n"
                    + "			LEFT JOIN DOT_GIAM_GIA AS DGG ON DGG.ID = CTSP.IdDGG\n"
                    + "			LEFT JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH \n"
                    + "            LEFT JOIN NHANVIEN AS NV ON NV.ID = HD.IdNV\n"
                    + "			LEFT JOIN PHIEU_GIAM_GIA AS PGG ON PGG.ID  = HD.IdPGG\n"
                    + "			LEFT JOIN SIZE AS S ON S.ID = CTSP.IdSize \n"
                    + "			LEFT JOIN THUONGHIEU AS TH ON TH.ID = CTSP.IdThuongHieu  \n"
                    + "			LEFT JOIN MAU AS M ON M.ID = CTSP.IdMau "
                    + "			 WHERE KH.MaKhachHang LIKE '" + str + "' AND MaHoaDon like '" + ma + "' ";

            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            List<Object> listLSGD = new ArrayList<>();
            while (rs.next()) {
                Object[] ls = new Object[]{
                    rs.getObject("MaHoaDon"),
                    rs.getObject("MaCTSP"),
                    rs.getObject("tm"),
                    rs.getObject("ts"),
                    rs.getObject("tth"),
                    rs.getObject("GiaNiemYet"),
                    rs.getObject("GiaBan"),
                    rs.getObject("SoLuongHDCT"),
                    rs.getObject("MaKhachHang"),
                    rs.getObject("TenKH"),
                    rs.getObject("MaHoaDon"),
                    rs.getObject("NgayTao"),
                    rs.getObject("NgayThanhToanHD"),
                    rs.getObject("SoLuongHDCT"),
                    rs.getObject("MaPhieu"),
                    rs.getObject("MADGG"),
                    rs.getObject("CAPBAC"),
                    rs.getObject("DiemDoi"),
                    rs.getObject("CAPBAC"),// GG THEO HD
                    rs.getObject("ThanhTien"), // rs.getObject("MaKhachHang"),
                };
                listLSGD.add(ls);
            }

            return listLSGD;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Object> fake() {
        try {
            query = "	 SELECT \n"
                    + "                  HDCT.ID AS IdHDCT , HD.ID  AS IdHD , HD.MaHoaDon AS MaHoaDon  , HD.NgayTao AS NgayTao , HD.TrangThai AS TrangThaiHD , HD.CapBac AS CAPBAC , HD.PhanTramGia AS PhanTramGia, HD.TienPhieuGiam AS TienPhieuGiam , HD.DiemDoi AS DiemDoi , HD.PhuongThucTT AS PhuongThucTT , \n"
                    + "				HD.NgayThanhToan AS NgayThanhToanHD,    HD.TienKhDua AS TienKhDua , HD.TienKhChuyenKhoan AS TienKhChuyenKhoan ,HD.TienThua AS TienThua , HD.ThanhTien AS ThanhTien , HD.HinhThucMua AS  HinhThucMua , HD.TrangThai AS TrangThaiHD ,\n"
                    + "				   CTSP.ID AS IdCTSP,CTSP.MaCTSP AS MaCTSP , CTSP.GiaBan AS GiaBan ,  CTSP.GiaNiemYet  AS GiaNiemYet,\n"
                    + "				   SP.ID AS IDSP ,SP.MaSP AS MASP , SP.TenSP AS TENSP ,\n"
                    + "				   DGG.ID AS IDDGG , DGG.MaDGG AS MADGG ,  DGG.TenDGG AS TENDGG , DGG.Loai AS LOAIDGG , DGG.GiaTri AS GIATRI , DGG.NgayBatDau AS NGAYBD , DGG.NgayKetThuc AS NGAYKT , DGG.MoTa AS MOTA , DGG.TrangThai TRANGTHAIDGG,\n"
                    + "				   NV.ID AS IdNV , NV.HoVaTen AS TenNV , NV.MaNhanVien AS MaNhanVien , \n"
                    + "				  KH.ID AS IdKH , KH.TenKhachHang AS TenKH , KH.MaKhachHang AS  MaKhachHang ,\n"
                    + "				PGG.ID AS IdPGG , PGG.MaPhieu AS  MaPhieu , PGG.TenPhieu AS TenPhieu , PGG.GiaTri AS GiaTri  , PGG.LoaiPhieu AS  LoaiPhieu, PGG.SoLuongPhieu AS SoLuongPhieu  , PGG.DonToiThieu AS DonToiThieu  , PGG.TrangThai AS TrangThaiPGG ,\n"
                    + "				 HDCT.IdCTSP AS IdCTSP , HDCT.SoLuong AS SoLuongHDCT , HDCT.MaDGG AS MaDGGHDCT , HDCT.LoaiDGG AS LoaiGGHDCT , HDCT.GiaTriDGG AS GiaTriHDCT , HDCT.QuyDoiDGGTT AS QuyDoiDGGTT , HDCT.GiaBan AS GiaBanHDCT , HDCT.DonGia AS DonGiaHDCT  , HDCT.ThanhTien AS ThanhtIENHDCT\n"
                    + "              , S.TenSize , M.TenMau  , TH.TenThuongHieu ,\n"
                    + "			  p.TenShip as tp , p.SDTShip as sdtp , p.GiaShip\n"
                    + "			  FROM HOADONCHITIET AS HDCT\n"
                    + "			LEFT JOIN HOADON AS HD ON HD.ID = HDCT.IdHoaDon\n"
                    + "			LEFT JOIN CHI_TIET_SAN_PHAM AS CTSP ON CTSP.ID = HDCT.IdCTSP\n"
                    + "			LEFT JOIN SANPHAM AS SP  ON SP.ID = CTSP.ID\n"
                    + "			LEFT JOIN DOT_GIAM_GIA AS DGG ON DGG.ID = CTSP.IdDGG\n"
                    + "			LEFT JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH \n"
                    + "            LEFT JOIN NHANVIEN AS NV ON NV.ID = HD.IdNV\n"
                    + "			LEFT JOIN PHIEU_GIAM_GIA AS PGG ON PGG.ID  = HD.IdPGG\n"
                    + "			LEFT JOIN SIZE AS S ON S.ID = CTSP.IdSize \n"
                    + "			LEFT JOIN THUONGHIEU AS TH ON TH.ID = CTSP.IdThuongHieu  \n"
                    + "			LEFT JOIN MAU AS M ON M.ID = CTSP.IdMau \n"
                    + "			LEFT JOIN PHIEUGIAOHANG AS P ON P.IdHoaDon = HD.ID";
            con = DBConnection.getConnect();
            stm = con.createStatement();
            rs = stm.executeQuery(query);

            List<Object> l = new ArrayList<>();
            while (rs.next()) {
                Object[] o = new Object[]{
                    rs.getObject("tp"),
                    rs.getObject("sdtp"),
                    rs.getObject(""),
                    rs.getObject(""),
                    rs.getObject(""),
                    rs.getObject(""),
                    rs.getObject(""),
                    rs.getObject(""),
                    rs.getObject(""),
                    rs.getObject(""),
                    rs.getObject("")

                };
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public BigDecimal getTongTienKH(String ma) {
        try {
            query = "SELECT SUM(ThanhTien) as TT FROM KHACHHANG\n"
                    + "JOIN HOADON AS HD ON HD.IdKH = KHACHHANG.ID\n"
                    + "WHERE HD.TrangThai = 1 AND MaKhachHang LIKE '" + ma + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("TT");
            }
            return BigDecimal.ZERO;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return BigDecimal.ZERO;
        }
    }

    public void updateCapBac(String maKH, int capBac) {
        try {
            query = "UPDATE KHACHHANG\n"
                    + "SET CapBac = ?\n"
                    + "WHERE MaKhachHang LIKE '" + maKH + "' ";

            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, capBac);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDiem(String maKH, int diem) {
        try {
            query = "UPDATE KHACHHANG\n"
                    + "SET Diem = ?\n"
                    + "WHERE MaKhachHang LIKE '" + maKH + "' ";

            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, diem);
            pstm.execute();
        } catch (SQLException ex) {

            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Float getTongChi(String ma) {
        Float tt = 0f;
        try {
            con = DBConnection.getConnect();
            query = "SELECT SUM(ThanhTien) AS TT FROM KHACHHANG\n"
                    + "JOIN HOADON ON KHACHHANG.ID = HOADON.ID\n"
                    + "WHERE MaKhachHang LIKE '" + ma + "' AND   HOADON.TrangThai = 1";
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                tt = rs.getFloat("TT");
                return tt;
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tt;
    }
}
