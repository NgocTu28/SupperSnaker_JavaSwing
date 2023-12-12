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
import Model.NhanVien;
import Model.PhieuGiamGia;
import Model.SanPham;
import Model.SanPhamChiTiet;
import Model.ThuongHieu;
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

/**
 *
 * @author manhnt
 */
public class HoaDon_MRepositoryM {

    private String query = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private Connection con = null;

    public void updateSP() {
        try {
            query = "update CHI_TIET_SAN_PHAM\n"
                    + "set GiaBan = 0 \n"
                    + "where GiaBan <=0";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public HoaDon pdfHD(String ma) {
        HoaDon hoaDon = new HoaDon();
        try {
            query = "SELECT \n"
                    + "\n"
                    + "KH.MaKhachHang , KH.TenKhachHang , KH.SDT ,  KH.CapBac as cb , KH.ID AS IDKH , \n"
                    + "NV.MaNhanVien , NV.HoVaTen , \n"
                    + "SPCT.MaCTSP , SP.TenSP ,M.TenMau , S.TenSize , TH.TenThuongHieu ,\n"
                    + "HDCT.DonGia , HDCT.GiaBan , HDCT.SoLuong , \n"
                    + " HD.MaHoaDon , HD.PhuongThucTT ,HD.TongTienSP  ,  HD.TienPhieuGiam , HD.PhanTramGia , \n"
                    + "HD.CapBac , hd.QR , hd.DiemDoi , \n"
                    + "HD.TienKhDua , HD.TienKhChuyenKhoan , HD.ThanhTien ,HD.TienThua ,HD.NgayThanhToan , \n"
                    + "PGG.LoaiPhieu\n"
                    + "\n"
                    + "FROM HOADON AS HD\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH\n"
                    + "JOIN NHANVIEN AS NV ON NV.ID = HD.IdNV\n"
                    + "JOIN HOADONCHITIET AS HDCT ON HDCT.IdHoaDon = HD.ID\n"
                    + "JOIN CHI_TIET_SAN_PHAM AS SPCT ON SPCT.ID = HDCT.IdCTSP\n"
                    + "JOIN SANPHAM AS SP on SP.ID = SPCT.IdSP\n"
                    + "JOIN THUONGHIEU AS TH ON TH.ID = SPCT.IdThuongHieu\n"
                    + "JOIN SIZE AS S ON S.ID = SPCT.IdSize\n"
                    + "JOIN MAU AS M ON M.ID = SPCT.IdMau\n"
                    + "LEFT JOIN PHIEU_GIAM_GIA AS PGG ON PGG.ID = HD.IdPGG\n"
                    + "WHERE HD.MaHoaDon = '" + ma + "' ;";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                khachHang.setSdt(rs.getString("SDT"));
                khachHang.setCapBac(rs.getInt("cb"));
                khachHang.setId(rs.getLong("IDKH"));
                System.out.println(khachHang.toString());
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
                phieuGiamGia.setLoaiPhieu(rs.getInt("LoaiPhieu"));
                SanPham sanPham = new SanPham();
                sanPham.setTenSanpham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac();
                mauSac.setTenMau(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu();
                thuongHieu.setTenThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc();
                kichThuoc.setTenSize(rs.getFloat("TenSize"));

                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                sanPhamChiTiet.setIdKichThuoc(kichThuoc);
                sanPhamChiTiet.setIdMau(mauSac);
                sanPhamChiTiet.setIdSanPham(sanPham);
                sanPhamChiTiet.setIdThuongHieu(thuongHieu);
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                chiTietHoaDon.setIdCTSP(sanPhamChiTiet);
                hoaDon.setIdKH(khachHang);
                hoaDon.setIdNV(nhanVien);
                hoaDon.setIdPGG(phieuGiamGia);
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                System.out.println("Repository.HoaDon_MRepositoryM.pdfHD()" + rs.getString("QR"));
                hoaDon.setQr(rs.getString("QR"));
                hoaDon.setTienThua(rs.getBigDecimal("TienThua"));
                hoaDon.setDiemDoi(rs.getBigDecimal("DiemDoi"));
                hoaDon.setPhuongThucTT(rs.getInt("PhuongThucTT"));
                hoaDon.setTongTienSP(rs.getBigDecimal("TongTienSP"));
                hoaDon.setTienPhieuGiam(rs.getBigDecimal("TienPhieuGiam"));
                hoaDon.setPhanTramGG(rs.getFloat("PhanTramGia"));
                hoaDon.setCapBac(rs.getInt("CapBac"));
                hoaDon.setTienKhDua(rs.getBigDecimal("TienKhDua"));
                hoaDon.setTienKhChuyenKhoan(rs.getBigDecimal("TienKhChuyenKhoan"));
                hoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                hoaDon.setNgayThanhToan(rs.getDate("NgayThanhToan"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hoaDon;
    }

    public List<ChiTietHoaDon> pdfHDCT(String ma) {
        List<ChiTietHoaDon> list = new ArrayList<>();

        try {
            query = "SELECT \n"
                    + "\n"
                    + "KH.MaKhachHang , KH.TenKhachHang , KH.SDT ,  KH.CapBac as cb ,KH.ID AS IDKH ,  \n"
                    + "NV.MaNhanVien , NV.HoVaTen , \n"
                    + "SPCT.MaCTSP , SP.TenSP ,M.TenMau , S.TenSize , TH.TenThuongHieu ,\n"
                    + "HDCT.DonGia , HDCT.GiaBan , HDCT.SoLuong , \n"
                    + "HD.PhuongThucTT ,HD.TongTienSP  ,  HD.TienPhieuGiam , HD.PhanTramGia , \n"
                    + "HD.CapBac , \n"
                    + "HD.TienKhDua , HD.TienKhChuyenKhoan , HD.ThanhTien ,\n"
                    + "PGG.LoaiPhieu\n"
                    + "\n"
                    + "FROM HOADON AS HD\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH\n"
                    + "JOIN NHANVIEN AS NV ON NV.ID = HD.IdNV\n"
                    + "JOIN HOADONCHITIET AS HDCT ON HDCT.IdHoaDon = HD.ID\n"
                    + "JOIN CHI_TIET_SAN_PHAM AS SPCT ON SPCT.ID = HDCT.IdCTSP\n"
                    + "JOIN SANPHAM AS SP on SP.ID = SPCT.IdSP\n"
                    + "JOIN THUONGHIEU AS TH ON TH.ID = SPCT.IdThuongHieu\n"
                    + "JOIN SIZE AS S ON S.ID = SPCT.IdSize\n"
                    + "JOIN MAU AS M ON M.ID = SPCT.IdMau\n"
                    + "LEFT JOIN PHIEU_GIAM_GIA AS PGG ON PGG.ID = HD.IdPGG\n"
                    + "WHERE HD.MaHoaDon = '" + ma + "' ;";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                khachHang.setSdt(rs.getString("SDT"));
                khachHang.setCapBac(rs.getInt("cb"));
                khachHang.setId(rs.getLong("IDKH"));
                System.out.println(khachHang.toString());
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
                phieuGiamGia.setLoaiPhieu(rs.getInt("LoaiPhieu"));
                SanPham sanPham = new SanPham();
                sanPham.setTenSanpham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac();
                mauSac.setTenMau(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu();
                thuongHieu.setTenThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc();
                kichThuoc.setTenSize(rs.getFloat("TenSize"));

                sanPhamChiTiet.setMaSPCT(rs.getString("MaCTSP"));
                sanPhamChiTiet.setIdKichThuoc(kichThuoc);
                sanPhamChiTiet.setIdMau(mauSac);
                sanPhamChiTiet.setIdSanPham(sanPham);
                sanPhamChiTiet.setIdThuongHieu(thuongHieu);
                chiTietHoaDon.setGiaBan(rs.getBigDecimal("GiaBan"));
                chiTietHoaDon.setDonGia(rs.getBigDecimal("DonGia"));
                chiTietHoaDon.setSoLuong(rs.getInt("SoLuong"));
                chiTietHoaDon.setIdCTSP(sanPhamChiTiet);

//                hoaDon.setPhuongThucTT(rs.getInt("PhuongThucTT"));
//                hoaDon.setTongTienSP(rs.getBigDecimal("TongTienSP"));
//                hoaDon.setTienPhieuGiam(rs.getBigDecimal("TienPhieuGiam"));
//                hoaDon.setPhanTramGG(rs.getFloat("PhanTramGia"));
//                hoaDon.setCapBac(rs.getInt("CapBac"));
//                hoaDon.setTienKhDua(rs.getBigDecimal("TienKhDua"));
//                hoaDon.setTienKhChuyenKhoan(rs.getBigDecimal("TienKhChuyenKhoan"));
//                hoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                list.add(chiTietHoaDon);
            }

        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int updateHD(HoaDon hoaDon) {
        try {
            query = " UPDATE HOADON  SET IdNV = ? , IdKH = ? , CapBac = ? , TienPhieuGiam = ? , PhanTramGia = ? , DiemDoi = ? ,\n"
                    + "		PhuongThucTT = ? , TienKhDua = ?  , TienKhChuyenKhoan = ? , TienThua = ? , ThanhTien = ? ,\n"
                    + "		NgayThanhToan = ? , TrangThai = 1 \n"
                    + "		WHERE MaHoaDon like ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, hoaDon.getIdNV().getId());
            pstm.setLong(2, hoaDon.getIdKH().getId());
            pstm.setInt(3, hoaDon.getCapBac());
            pstm.setBigDecimal(4, hoaDon.getTienPhieuGiam());
            pstm.setFloat(5, hoaDon.getPhanTramGG());

            pstm.setBigDecimal(6, hoaDon.getDiemDoi());
            pstm.setInt(7, hoaDon.getPhuongThucTT());
            pstm.setBigDecimal(8, hoaDon.getTienKhDua());
            pstm.setBigDecimal(9, hoaDon.getTienKhChuyenKhoan());
            pstm.setBigDecimal(10, hoaDon.getTienThua());
            pstm.setBigDecimal(11, hoaDon.getThanhTien());
            pstm.setString(12, XDate.toString(hoaDon.getNgayThanhToan(), "yyyy-MM-dd HH:mm:ss.SSS"));

            pstm.setString(13, hoaDon.getMaHoaDon());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public void updateHDBy(KhachHang khachHang, String maHD) {
        try {
            query = "UPDATE HOADON\n"
                    + "SET IdKH = ? \n"
                    + "WHERE MaHoaDon LIKE ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, khachHang.getId());
            pstm.setString(2, maHD);
            pstm.execute();

        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getRowCountHD() {
        String countSql = "SELECT COUNT(*) AS totalRows FROM HOADON";
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

    public int create(HoaDon hoaDon) {
        try {
            query = " INSERT INTO HOADON( IdNV , IdKH, MaHoaDon , QR ) VALUES \n"
                    + "	(? , ? , ? , ?) ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            // pstm.setObject(1, hoaDon.getIdPGG().getIdPGG());
            pstm.setLong(1, hoaDon.getIdNV().getId());
            pstm.setLong(2, hoaDon.getIdKH().getId());
            pstm.setString(3, hoaDon.getMaHoaDon());
            pstm.setString(4, hoaDon.getQr());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<HoaDon> getAllHDByTrangThai2(int trangThai, Long idNv) {
        List<HoaDon> listHD = new ArrayList<>();
        try {

            query = "SELECT HD.ID AS IDHD , KH.ID AS IDKH , NV.ID AS IDNV ,\n"
                    + "HD.NgayTao , HD.TrangThai , HD.MaHoaDon ,  \n"
                    + "NV.HoVaTen , NV.MaNhanVien , \n"
                    + "KH.TenKhachHang , KH.MaKhachHang\n"
                    + "FROM HOADON AS HD\n"
                    + "JOIN NHANVIEN AS NV ON NV.ID = HD.IdNV\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH\n"
                    + "WHERE HD.TrangThai = " + trangThai + "  AND  NV.ID =  " + idNv + " "
                    + " ORDER BY HD.NgayTao DESC";;
            con = DBConnection.getConnect();
            pstm = con.prepareCall(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setId(rs.getLong("IDKH"));
                khachHang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IDNV"));
                nhanVien.setTenNhanVien(rs.getString("HoVaTen"));
                nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));

                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getLong("IDHD"));
                hoaDon.setIdKH(khachHang);
                hoaDon.setIdNV(nhanVien);
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                hoaDon.setNgayTao(rs.getDate("NgayTao"));
                hoaDon.setTrangThai(rs.getInt("TrangThai"));
                listHD.add(hoaDon);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietHoaDon_RepositoryM.class.getName()).log(Level.SEVERE, null, ex);

        }
        return listHD;
    }

    public Integer getHoaDonTrao(int trangThai, Long idNv) {
        Integer slHD = 0;
        try {

            query = "SELECT COUNT (*) as SLHD\n"
                    + "FROM HOADON AS HD\n"
                    + "JOIN NHANVIEN AS NV ON NV.ID = HD.IdNV\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH\n"
                    + "WHERE HD.TrangThai = " + trangThai + " AND NV.ID = " + idNv + "";
            con = DBConnection.getConnect();
            pstm = con.prepareCall(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                slHD = rs.getInt("SLHD");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietHoaDon_RepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return slHD;
    }
//   

    public Long findIDByMaHD(String maHoaDon) {
        Long id = 0L;
        try {
            query = "SELECT ID FROM HOADON WHERE MaHoaDon LIKE '" + maHoaDon + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                id = rs.getLong("ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void updateTTHD(String maHD, int trangThai) {
        try {
            query = "UPDATE HOADON\n"
                    + "SET TrangThai = ?\n"
                    + "WHERE MaHoaDon LIKE  ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, trangThai);
            pstm.setString(2, maHD);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateTTHD2(String maHD, int trangThai) {
        try {
            query = "UPDATE HOADON\n"
                    + "SET TrangThai = ? , TienKhDua = ThanhTien ,  NgayThanhToan = getdate()\n"
                    + "WHERE MaHoaDon LIKE  ? ;";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, trangThai);
            pstm.setString(2, maHD);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getIDHDByMaHD(String maHD) {
        try {
            query = "SELECT ID FROM HoaDon \n"
                    + " where MaHoaDon Like  '" + maHD + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public Long getPPGByMaHD(String ma) {
        Long tt = null;
        try {
            query = "SELECT IdPGG FROM HOADON\n"
                    + "	WHERE MaHoaDon LIKE '" + ma + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                tt = rs.getLong("IdPGG");
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tt;
    }

    public void updateIdDGGInHDByMaHD(String maHd, PhieuGiamGia giamGia) {
        try {
            query = "UPDATE HOADON\n"
                    + "SET IdPGG = ? , PhanTramGia = ? , TienPhieuGiam = ?\n"
                    + "WHERE MaHoaDon LIKE ? ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setLong(1, giamGia.getIdPGG());
            if (giamGia.getLoaiPhieu() == 0) {
                pstm.setObject(2, giamGia.getGiaTri());
                pstm.setObject(3, 0);
            } else if (giamGia.getLoaiPhieu() == 1) {

                pstm.setObject(2, 0);
                pstm.setObject(3, giamGia.getGiaTri());

            }
            pstm.setObject(4, maHd);

            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int thanh_toanHD(HoaDon hoaDon) {
        try {
            query = "UPDATE HOADON\n"
                    + "SET  CapBac = ?  ,\n"
                    + "DiemDoi = ? , PhuongThucTT = ? , TienKhDua = ? , TienKhChuyenKhoan = ? ,\n"
                    + "TienThua = ?  , NgayThanhToan = ? , TrangThai = ? , ThanhTien = ?, TongTienSP = ?  \n"
                    + " WHERE MaHoaDon LIKE ? ";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);

            pstm.setObject(1, hoaDon.getCapBac());
            pstm.setObject(2, hoaDon.getDiemDoi());
            pstm.setObject(3, hoaDon.getPhuongThucTT());
            pstm.setObject(4, hoaDon.getTienKhDua());
            pstm.setObject(5, hoaDon.getTienKhChuyenKhoan());
            pstm.setObject(6, hoaDon.getTienThua());
            pstm.setObject(7, hoaDon.getNgayThanhToan());
            pstm.setObject(8, hoaDon.getTrangThai());
            pstm.setObject(9, hoaDon.getThanhTien());
            pstm.setObject(10, hoaDon.getTongTienSP());
            pstm.setObject(11, hoaDon.getMaHoaDon());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public PhieuGiamGia getPGGByID_BH(Long id) {
        PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
        try {
            con = DBConnection.getConnect();
            query = "SELECT ID , MaPhieu , TenPhieu , LoaiPhieu , GiaTri , SoLuongPhieu , DonToiThieu  FROM PHIEU_GIAM_GIA\n"
                    + "WHERE ID = " + id + " ";
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                phieuGiamGia.setIdPGG(rs.getLong("ID"));
                phieuGiamGia.setMaPhieu(rs.getString("MaPhieu"));
                phieuGiamGia.setTenPhieu(rs.getString("TenPhieu"));
                phieuGiamGia.setLoaiPhieu(rs.getInt("LoaiPhieu"));
                phieuGiamGia.setGiaTri(rs.getBigDecimal("GiaTri"));
                phieuGiamGia.setSoLuongPhieu(rs.getInt("SoLuongPhieu"));
                phieuGiamGia.setDonToiThieu(rs.getBigDecimal("DonToiThieu"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phieuGiamGia;
    }

    public void updateLoaiHD(Long ma, int htt, int tt) {
        try {
            query = "UPDATE HOADON\n"
                    + "SET HinhThucMua = ? , TrangThai = ?  \n"
                    + "WhERE ID = ?";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, htt);
            pstm.setInt(2, tt);
            pstm.setLong(3, ma);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<HoaDon> getAlHD_HD(String tt, int page, int limt) {
        List<HoaDon> list = new ArrayList<>();
        try {
            query = "SELECT HD.ID , MaHoaDon , MaNhanVien , KH.MaKhachHang ,  KH.SDT , HD.ThanhTien , HD.HinhThucMua  , HD.NgayTao , HD.TrangThai , HD.NgayThanhToan FROM HOADON AS HD \n"
                    + "JOIN NHANVIEN AS NV ON NV.ID = HD.IdNV\n"
                    + "JOIN KHACHHANG AS KH ON KH.ID = HD.IdKH \n"
                    + " WHERE MaKhachHang like ? or MaNhanVien like ? or kh.SDT like ? or  HD.MaHoaDon like  ? "
                    + " order by HD.ID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";

            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setString(1, "%" + tt + "%");
            pstm.setString(2, "%" + tt + "%");
            pstm.setString(3, "%" + tt + "%");
            pstm.setString(4, "%" + tt + "%");
            pstm.setInt(5, (page - 1) * limt);
            pstm.setInt(6, limt);
            rs = pstm.executeQuery();
            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));
                KhachHang khachHang = new KhachHang();
                khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getLong("ID"));
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                hoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                hoaDon.setLoai(rs.getInt("HinhThucMua"));
                hoaDon.setNgayTao(rs.getDate("NgayTao"));
                hoaDon.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                hoaDon.setTrangThai(rs.getInt("TrangThai"));
                hoaDon.setIdKH(khachHang);
                hoaDon.setIdNV(nhanVien);
                list.add(hoaDon);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<HoaDon> getAll_Loc(Date ngayBD, Date ngayKT, int loaiHD, int trangThai, int page, int lmit) {
        List<HoaDon> list = new ArrayList<>();

        List<String> wheres = new ArrayList<>();
        String where = "";
        try {
            query = "SELECT HD.id AS IdHD, NV.id AS IdNV, KH.ID AS IdKH, KH.MaKhachHang,\n"
                    + "NV.MaNhanVien , MaHoaDon , ThanhTien , HinhThucMua , HD.NgayTao , NgayThanhToan , HD.TrangThai\n"
                    + "      \n"
                    + "FROM HoaDon AS HD\n"
                    + "JOIN NhanVien AS NV ON NV.id = HD.IdNV\n"
                    + "JOIN KhachHang AS KH ON KH.ID = HD.IdKH ";
            con = DBConnection.getConnect();

            if (ngayBD == null && ngayKT == null && loaiHD == -1 && trangThai == -1) {
                query += "order by IdHD DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                pstm = con.prepareStatement(query);
                pstm.setInt(1, (page - 1) * lmit);
                pstm.setInt(2, lmit);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setId(rs.getLong("IdNV"));
                    nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));

                    KhachHang khachHang = new KhachHang();
                    khachHang.setId(rs.getLong("IdKH"));

                    khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setId(rs.getLong("IdHD"));
                    hoaDon.setIdKH(khachHang);
                    hoaDon.setIdNV(nhanVien);
                    hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                    hoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                    hoaDon.setLoai(rs.getInt("HinhThucMua"));
                    hoaDon.setNgayTao(rs.getDate("NgayTao"));
                    hoaDon.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                    hoaDon.setTrangThai(rs.getInt("TrangThai"));
                    list.add(hoaDon);
                }
                return list;

            }

            if (ngayBD != null && ngayKT != null) {
                wheres.add("( HD.NgayTao BETWEEN  ? AND ?  ) ");
            }
            if (loaiHD != -1) {
                wheres.add(" HinhThucMua  = ?");
            }
            if (trangThai != -1) {
                wheres.add("HD.TrangThai = ? ");
            }
            int count = 0;
            for (int i = 0; i < wheres.size(); i++) {
                int checkLast = (wheres.size() - 1);
                where += wheres.get(i);
                if (i != checkLast && wheres.size() != 1) {
                    where += " AND ";
                }
                count++;
            }

            query += "WHERE " + where + " order by IdHD DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";;
            System.out.println(query);
            pstm = con.prepareStatement(query);

            if (ngayBD == null && ngayKT == null && loaiHD == -1 && (trangThai != -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 6);
                pstm.setInt(1, trangThai);
                pstm.setInt(2, (page - 1) * lmit);
                pstm.setInt(3, lmit);

            }

            if (ngayBD == null && ngayKT == null && loaiHD != -1 && trangThai == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 7);
                pstm.setInt(1, loaiHD);
                pstm.setInt(2, (page - 1) * lmit);
                pstm.setInt(3, lmit);

            }

            if (ngayBD == null && ngayKT == null && loaiHD != -1 && trangThai != -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 8);
                pstm.setInt(1, loaiHD);
                pstm.setInt(2, trangThai);
                pstm.setInt(3, (page - 1) * lmit);
                pstm.setInt(4, lmit);

            }

            if (ngayBD != null && ngayKT != null && loaiHD != -1 && (trangThai != -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 1);
                pstm.setString(1, XDate.toString(ngayBD, "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(ngayKT, "MM-dd-yyyy"));
                pstm.setInt(3, loaiHD);
                pstm.setInt(4, trangThai);
                pstm.setInt(5, (page - 1) * lmit);
                pstm.setInt(6, lmit);

            }
            if (ngayBD != null && ngayKT != null && loaiHD != -1 && trangThai == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 2);
                pstm.setString(1, XDate.toString(ngayBD, "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(ngayKT, "MM-dd-yyyy"));
                pstm.setInt(3, loaiHD);
                pstm.setInt(4, (page - 1) * lmit);
                pstm.setInt(5, lmit);

                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 2);
            }
            if (ngayBD != null && ngayKT != null && trangThai != -1 && loaiHD == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 3);
                pstm.setString(1, XDate.toString(ngayBD, "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(ngayKT, "MM-dd-yyyy"));
                pstm.setInt(3, trangThai);
                pstm.setInt(4, (page - 1) * lmit);
                pstm.setInt(5, lmit);

            }
            if (ngayBD != null && ngayKT != null && loaiHD == -1 && (trangThai == -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 4);

                pstm.setString(1, XDate.toString(ngayBD, "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(ngayKT, "MM-dd-yyyy"));
                pstm.setInt(3, (page - 1) * lmit);
                pstm.setInt(4, lmit);

            }

            rs = pstm.executeQuery();
            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IdNV"));
                nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));

                KhachHang khachHang = new KhachHang();
                khachHang.setId(rs.getLong("IdKH"));

                khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getLong("IdHD"));
                hoaDon.setIdKH(khachHang);
                hoaDon.setIdNV(nhanVien);
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                hoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                hoaDon.setLoai(rs.getInt("HinhThucMua"));
                hoaDon.setNgayTao(rs.getDate("NgayTao"));
                hoaDon.setTrangThai(rs.getInt("TrangThai"));
                list.add(hoaDon);
            }
            return list;

        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<HoaDon> getAll_Loc_ALL(Date ngayBD, Date ngayKT, int loaiHD, int trangThai) {
        List<HoaDon> list = new ArrayList<>();

        List<String> wheres = new ArrayList<>();
        String where = "";
        try {
            query = "SELECT HD.id AS IdHD, NV.id AS IdNV, KH.ID AS IdKH, KH.MaKhachHang,\n"
                    + "NV.MaNhanVien , MaHoaDon , ThanhTien , HinhThucMua , HD.NgayTao , NgayThanhToan , HD.TrangThai\n"
                    + "      \n"
                    + "FROM HoaDon AS HD\n"
                    + "JOIN NhanVien AS NV ON NV.id = HD.IdNV\n"
                    + "JOIN KhachHang AS KH ON KH.ID = HD.IdKH ";
            con = DBConnection.getConnect();

            if (ngayBD == null && ngayKT == null && loaiHD == -1 && trangThai == -1) {
                //  query += "order by IdHD DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                pstm = con.prepareStatement(query);
//                pstm.setInt(1, (page - 1) * lmit);
//                pstm.setInt(2, lmit);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setId(rs.getLong("IdNV"));
                    nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));

                    KhachHang khachHang = new KhachHang();
                    khachHang.setId(rs.getLong("IdKH"));

                    khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setId(rs.getLong("IdHD"));
                    hoaDon.setIdKH(khachHang);
                    hoaDon.setIdNV(nhanVien);
                    hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                    hoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                    hoaDon.setLoai(rs.getInt("HinhThucMua"));
                    hoaDon.setNgayTao(rs.getDate("NgayTao"));
                    hoaDon.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                    hoaDon.setTrangThai(rs.getInt("TrangThai"));
                    list.add(hoaDon);
                }
                return list;

            }

            if (ngayBD != null && ngayKT != null) {
                wheres.add("( HD.NgayTao BETWEEN  ? AND ?  ) ");
            }
            if (loaiHD != -1) {
                wheres.add(" HinhThucMua  = ?");
            }
            if (trangThai != -1) {
                wheres.add("HD.TrangThai = ? ");
            }
            int count = 0;
            for (int i = 0; i < wheres.size(); i++) {
                int checkLast = (wheres.size() - 1);
                where += wheres.get(i);
                if (i != checkLast && wheres.size() != 1) {
                    where += " AND ";
                }
                count++;
            }

            query += "WHERE " + where;
            System.out.println(query);
            pstm = con.prepareStatement(query);

            if (ngayBD == null && ngayKT == null && loaiHD == -1 && (trangThai != -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 6);
                pstm.setInt(1, trangThai);

            }

            if (ngayBD == null && ngayKT == null && loaiHD != -1 && trangThai == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 7);
                pstm.setInt(1, loaiHD);

            }

            if (ngayBD == null && ngayKT == null && loaiHD != -1 && trangThai != -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 8);
                pstm.setInt(1, loaiHD);
                pstm.setInt(2, trangThai);

            }

            if (ngayBD != null && ngayKT != null && loaiHD != -1 && (trangThai != -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 1);
                pstm.setString(1, XDate.toString(ngayBD, "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(ngayKT, "MM-dd-yyyy"));
                pstm.setInt(3, loaiHD);
                pstm.setInt(4, trangThai);

            }
            if (ngayBD != null && ngayKT != null && loaiHD != -1 && trangThai == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 2);
                pstm.setString(1, XDate.toString(ngayBD, "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(ngayKT, "MM-dd-yyyy"));
                pstm.setInt(3, loaiHD);

                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 2);
            }
            if (ngayBD != null && ngayKT != null && trangThai != -1 && loaiHD == -1) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 3);
                pstm.setString(1, XDate.toString(ngayBD, "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(ngayKT, "MM-dd-yyyy"));
                pstm.setInt(3, trangThai);

            }
            if (ngayBD != null && ngayKT != null && loaiHD == -1 && (trangThai == -1)) {
                System.out.println("Repository.PhieuGiamGiaService.getListLoc()" + 4);

                pstm.setString(1, XDate.toString(ngayBD, "MM-dd-yyyy"));
                pstm.setString(2, XDate.toString(ngayKT, "MM-dd-yyyy"));

            }

            rs = pstm.executeQuery();
            while (rs.next()) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getLong("IdNV"));
                nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));

                KhachHang khachHang = new KhachHang();
                khachHang.setId(rs.getLong("IdKH"));

                khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getLong("IdHD"));
                hoaDon.setIdKH(khachHang);
                hoaDon.setIdNV(nhanVien);
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                hoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                hoaDon.setLoai(rs.getInt("HinhThucMua"));
                hoaDon.setNgayTao(rs.getDate("NgayTao"));
                hoaDon.setTrangThai(rs.getInt("TrangThai"));
                list.add(hoaDon);
            }
            return list;

        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Integer getSoLuongGH(String maHD, String maSP) {
        Integer sl = 0;
        try {

            query = "SELECT SUM(HDCT.SoLuong) as SL FROM HOADONCHITIET AS HDCT \n"
                    + "JOIN HOADON AS HD ON HD.ID =  HDCT.IdHoaDon\n"
                    + "JOIN CHI_TIET_SAN_PHAM AS CTSP  ON CTSP.ID = HDCT.IdCTSP\n"
                    + "WHERE CTSP.MaCTSP LIKE '" + maSP + "' AND MaHoaDon LIKE '" + maHD + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                sl = rs.getInt("SL");

            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sl;
    }

    public Integer getSoLuongTon(String ma) {
        Integer sl = 0;
        try {
            query = "select SoLuongTon from CHI_TIET_SAN_PHAM\n"
                    + "where MaCTSP like '" + ma + "' ;";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                sl = rs.getInt("SoLuongTon");
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sl;
    }

    public void updateSLGH(Long id, Integer soLuong) {
        try {
            query = "UPDATE HOADONCHITIET\n"
                    + "SET  SoLuong = ?\n"
                    + "WHERE IdCTSP = '" + id + "'";
            con = DBConnection.getConnect();
            pstm = con.prepareStatement(query);
            pstm.setInt(1, soLuong);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTrangThaiHD(String maHD) {
        try {
            con = DBConnection.getConnect();
            query = "SELECT TrangThai FROM HOADON\n"
                    + "WHERE MaHoaDon LIKE '" + maHD + "' ";
            pstm = con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("TrangThai");
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_MRepositoryM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
