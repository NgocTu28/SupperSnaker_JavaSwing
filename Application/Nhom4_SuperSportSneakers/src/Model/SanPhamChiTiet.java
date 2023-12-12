/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Utils.Format;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author manhnt
 */
public class SanPhamChiTiet {

    private Long idSPCT;
    private DotGiamGia_M idDGG;
    private String maSPCT;
    private int soLuong;
    private BigDecimal giaBan;
    private BigDecimal giaNiemYet;
    private int TrangThai;
    private String moTa;
    private MauSac idMau;
    private KichThuoc idKichThuoc;
    private ThuongHieu idThuongHieu;
    private SanPham idSanPham;

    public SanPhamChiTiet() {
    }

    public SanPhamChiTiet(Long idSPCT, String maSPCT, int soLuong, BigDecimal giaBan, BigDecimal giaNiemYet, int TrangThai, String moTa, MauSac idMau, KichThuoc idKichThuoc, ThuongHieu idThuongHieu, SanPham idSanPham) {
        this.idSPCT = idSPCT;
        this.maSPCT = maSPCT;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaNiemYet = giaNiemYet;
        this.TrangThai = TrangThai;
        this.moTa = moTa;
        this.idMau = idMau;
        this.idKichThuoc = idKichThuoc;
        this.idThuongHieu = idThuongHieu;
        this.idSanPham = idSanPham;
    }

    public SanPhamChiTiet(String maSPCT, int soLuong, BigDecimal giaBan, BigDecimal giaNiemYet, int TrangThai, String moTa, MauSac idMau, KichThuoc idKichThuoc, ThuongHieu idThuongHieu, SanPham idSanPham) {
        this.maSPCT = maSPCT;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaNiemYet = giaNiemYet;
        this.TrangThai = TrangThai;
        this.moTa = moTa;
        this.idMau = idMau;
        this.idKichThuoc = idKichThuoc;
        this.idThuongHieu = idThuongHieu;
        this.idSanPham = idSanPham;
    }

    public SanPhamChiTiet(Long idSPCT, DotGiamGia_M idDGG, String maSPCT, int soLuong, BigDecimal giaBan, BigDecimal giaNiemYet, SanPham idSanPham) {
        this.idSPCT = idSPCT;
        this.idDGG = idDGG;
        this.maSPCT = maSPCT;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaNiemYet = giaNiemYet;
        this.idSanPham = idSanPham;
    }

    public SanPhamChiTiet(Long idSPCT, DotGiamGia_M idDGG, String maSPCT, BigDecimal giaBan, BigDecimal giaNiemYet, SanPham idSanPham) {
        this.idSPCT = idSPCT;
        this.idDGG = idDGG;
        this.maSPCT = maSPCT;
        this.giaBan = giaBan;
        this.giaNiemYet = giaNiemYet;
        this.idSanPham = idSanPham;
    }

    public SanPhamChiTiet(Long idSPCT, String maSPCT, int soLuong, BigDecimal giaBan, BigDecimal giaNiemYet, MauSac idMau, KichThuoc idKichThuoc, ThuongHieu idThuongHieu, SanPham idSanPham) {
        this.idSPCT = idSPCT;
        this.maSPCT = maSPCT;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaNiemYet = giaNiemYet;
        this.idMau = idMau;
        this.idKichThuoc = idKichThuoc;
        this.idThuongHieu = idThuongHieu;
        this.idSanPham = idSanPham;

    }

    public SanPhamChiTiet(Long idSPCT, DotGiamGia_M idDGG, String maSPCT, int soLuong, BigDecimal giaBan, BigDecimal giaNiemYet, MauSac idMau, KichThuoc idKichThuoc, ThuongHieu idThuongHieu, SanPham idSanPham) {
        this.idSPCT = idSPCT;
        this.idDGG = idDGG;
        this.maSPCT = maSPCT;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaNiemYet = giaNiemYet;

        this.idMau = idMau;
        this.idKichThuoc = idKichThuoc;
        this.idThuongHieu = idThuongHieu;
        this.idSanPham = idSanPham;
    }

    public Long getIdSPCT() {
        return idSPCT;
    }

    public void setIdSPCT(Long idSPCT) {
        this.idSPCT = idSPCT;
    }

    public DotGiamGia_M getIdDGG() {
        return idDGG;
    }

    public void setIdDGG(DotGiamGia_M idDGG) {
        this.idDGG = idDGG;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public BigDecimal getGiaNiemYet() {
        return giaNiemYet;
    }

    public void setGiaNiemYet(BigDecimal giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public MauSac getIdMau() {
        return idMau;
    }

    public void setIdMau(MauSac idMau) {
        this.idMau = idMau;
    }

    public KichThuoc getIdKichThuoc() {
        return idKichThuoc;
    }

    public void setIdKichThuoc(KichThuoc idKichThuoc) {
        this.idKichThuoc = idKichThuoc;
    }

    public ThuongHieu getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(ThuongHieu idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public SanPham getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(SanPham idSanPham) {
        this.idSanPham = idSanPham;
    }

//    public BigDecimal tinhGiaBan() {
//        if (this.idDGG == null) {
//            return giaBan;
//        }
//        if (this.idDGG != null && idDGG.getTrangThai() == 1) {
//            Integer checkLoai = this.idDGG.getHinhThucDGG();
//            if (checkLoai == 0) {
//                //  giaBan = giaNiemYet - ((idDGG.getGiaTri() / (float) 100) * giaNiemYet);
//                BigDecimal giaGiam = idDGG.getGiaTri().divide(giaNiemYet.multiply(BigDecimal.valueOf(Double.parseDouble("100"))));
//                giaBan = giaNiemYet.subtract(giaGiam);
//                System.out.println("loai 0 ... %: " + giaBan);
//                return giaBan;
//            } else if (checkLoai == 1) {
//                giaBan = giaNiemYet.subtract(idDGG.getGiaTri());
////                    System.out.println("loai 1: VND" + giaBan);
//                return giaBan;
//            }
//
//            return giaBan;
//        } else {
//            giaBan = giaNiemYet;
//            //        System.out.println("yet = ban");
//            return giaBan;
//        }
//    }
//
//    public BigDecimal tinhGiaTriDGG() {
//        Integer checkLoai = this.idDGG.getHinhThucDGG();
//        if (checkLoai == 0) {
//
//            BigDecimal giaGiam = idDGG.getGiaTri().divide(giaNiemYet.multiply(BigDecimal.valueOf(Double.parseDouble("100"))));
//            giaBan = giaNiemYet.subtract(giaGiam);
//            System.out.println("loai 0 ... %: " + giaBan);
//            return giaBan;
//        } else if (checkLoai == 1) {
//            giaBan = giaNiemYet.subtract(idDGG.getGiaTri());
//            return giaBan;
//        } else {
//            return BigDecimal.ZERO;
//        }
//    }
//
//    public BigDecimal khoiPhucGiaBan() {
//        if (this.idDGG == null) {
//            giaBan = giaNiemYet;
//        }
//        return giaBan;
//    }
//
//    public int getLoaiDGG() {
//        if (null == idDGG.getHinhThucDGG()) {
//            return 2;
//        } else {
//            switch (idDGG.getHinhThucDGG()) {
//                case 1:
//                    return 1;
//                case 0:
//                    return 0;
//                default:
//                    return 2;
//            }
//        }
//    }
//
//    public BigDecimal getGiaTriDGG() {
//        if (this.idDGG != null) {
//            return idDGG.getGiaTri();
//        } else {
//            return BigDecimal.ZERO;
//        }
//    }

//    public BigDecimal getGiaGiam() {
//        //  return this.giaNiemYet - tinhGiaBan();
//        return this.giaNiemYet.subtract(tinhGiaBan());
//    }

    public Object[] rowDataSPBH() {

        return new Object[]{
            maSPCT, idSanPham.getTenSanpham(), idMau.getTenMau(), idKichThuoc.getTenSize(), idThuongHieu.getTenThuongHieu(), soLuong, Format.format(giaNiemYet), Format.format(giaBan)
        };
    }

    public Object[] rowDataViewDGG() {
        return new Object[]{
            maSPCT, idSanPham.getTenSanpham(), giaNiemYet, idThuongHieu.getTenThuongHieu(), idMau.getTenMau(), idKichThuoc.getTenSize(), false
        };
    }

    public Object[] rowDataDGG(int index) {
        return new Object[]{
            false, index, maSPCT, idSanPham.getTenSanpham(), giaBan, idThuongHieu.getTenThuongHieu(), idMau.getTenMau(), idKichThuoc.getTenSize()
        };
    }
    
    public Object [] rowTraHang (int i){
        return new Object[]{
            i, maSPCT , idSanPham.getTenSanpham() , idThuongHieu.getTenThuongHieu() ,
            idMau.getTenMau() , idKichThuoc.getTenSize() , soLuong , Format.format(giaBan) , Format.format(giaBan.multiply(new BigDecimal(soLuong)))
        };
    }
    
   

    @Override
    public String toString() {
        return "ChiTietSanPham_M{" + "idSPCT=" + idSPCT + ", idDGG=" + idDGG + ", maSPCT=" + maSPCT + ", soLuong=" + soLuong + ", giaBan=" + giaBan + ", giaNiemYet=" + giaNiemYet + ", TrangThai=" + TrangThai + ", moTa=" + moTa + ", idMau="/* + idMau.getIdMau() + ", idKichThuoc=" + idKichThuoc.getIdSize() + ", idThuongHieu=" + idThuongHieu.getIdThuongHieu() + ", idSanPham=" + idSanPham.getIdSanPham() + */ + '}';
    }

}
