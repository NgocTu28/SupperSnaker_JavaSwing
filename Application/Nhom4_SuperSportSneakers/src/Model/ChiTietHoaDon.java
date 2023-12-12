/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.util.Date;
import Utils.Format;
import java.util.List;
import org.bridj.cpp.com.IID;

/**
 *
 * @author manhnt
 */
public class ChiTietHoaDon {

    private Long id;
    private HoaDon IdHoaDon;
    private SanPhamChiTiet IdCTSP;
    private Integer soLuong;
    private String maDGG;
    private Integer loaiDGG;
    private BigDecimal giaTriDGG;
    private BigDecimal quyDoiDGGTT;
    private BigDecimal GiaBan;
    private BigDecimal DonGia;
    private BigDecimal thanhTien;
    private Date ngayTao;
    private Integer trangThai;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(HoaDon IdHoaDon, SanPhamChiTiet IdCTSP, Integer soLuong) {
        this.IdHoaDon = IdHoaDon;
        this.IdCTSP = IdCTSP;
        this.soLuong = soLuong;
    }

    public ChiTietHoaDon(HoaDon IdHoaDon, SanPhamChiTiet IdCTSP, Integer soLuong, String maDGG, Integer loaiDGG, BigDecimal giaTriDGG, BigDecimal quyDoiDGGTT, BigDecimal GiaBan, BigDecimal DonGia, Integer trangThai) {
        this.IdHoaDon = IdHoaDon;
        this.IdCTSP = IdCTSP;
        this.soLuong = soLuong;
        this.maDGG = maDGG;
        this.loaiDGG = loaiDGG;
        this.giaTriDGG = giaTriDGG;
        this.quyDoiDGGTT = quyDoiDGGTT;
        this.GiaBan = GiaBan;
        this.DonGia = DonGia;
        this.trangThai = trangThai;
    }

    public ChiTietHoaDon(HoaDon IdHoaDon, SanPhamChiTiet IdCTSP, Integer soLuong, String maDGG, Integer loaiDGG, BigDecimal giaTriDGG, BigDecimal quyDoiDGGTT, BigDecimal GiaBan, BigDecimal DonGia, BigDecimal thanhTien) {
        this.IdHoaDon = IdHoaDon;
        this.IdCTSP = IdCTSP;
        this.soLuong = soLuong;
        this.maDGG = maDGG;
        this.loaiDGG = loaiDGG;
        this.giaTriDGG = giaTriDGG;
        this.quyDoiDGGTT = quyDoiDGGTT;
        this.GiaBan = GiaBan;
        this.DonGia = DonGia;
        this.thanhTien = thanhTien;

    }

    public ChiTietHoaDon(Long id, HoaDon IdHoaDon, SanPhamChiTiet IdCTSP, Integer soLuong, String maDGG, Integer loaiDGG, BigDecimal giaTriDGG, BigDecimal quyDoiDGGTT, BigDecimal GiaBan, BigDecimal DonGia, BigDecimal thanhTien) {
        this.id = id;
        this.IdHoaDon = IdHoaDon;
        this.IdCTSP = IdCTSP;
        this.soLuong = soLuong;
        this.maDGG = maDGG;
        this.loaiDGG = loaiDGG;
        this.giaTriDGG = giaTriDGG;
        this.quyDoiDGGTT = quyDoiDGGTT;
        this.GiaBan = GiaBan;
        this.DonGia = DonGia;
        this.thanhTien = thanhTien;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HoaDon getIdHoaDon() {
        return IdHoaDon;
    }

    public void setIdHoaDon(HoaDon IdHoaDon) {
        this.IdHoaDon = IdHoaDon;
    }

    public SanPhamChiTiet getIdCTSP() {
        return IdCTSP;
    }

    public void setIdCTSP(SanPhamChiTiet IdCTSP) {
        this.IdCTSP = IdCTSP;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaDGG() {
        return maDGG;
    }

    public void setMaDGG(String maDGG) {
        this.maDGG = maDGG;
    }

    public Integer getLoaiDGG() {
        return loaiDGG;
    }

    public void setLoaiDGG(Integer loaiDGG) {
        this.loaiDGG = loaiDGG;
    }

    public BigDecimal getGiaTriDGG() {
        return giaTriDGG;
    }

    public void setGiaTriDGG(BigDecimal giaTriDGG) {
        this.giaTriDGG = giaTriDGG;
    }

    public BigDecimal getQuyDoiDGGTT() {
        return quyDoiDGGTT;
    }

    public void setQuyDoiDGGTT(BigDecimal quyDoiDGGTT) {
        this.quyDoiDGGTT = quyDoiDGGTT;
    }

    public BigDecimal getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(BigDecimal GiaBan) {
        this.GiaBan = GiaBan;
    }

    public BigDecimal getDonGia() {
        return DonGia;
    }

    public void setDonGia(BigDecimal DonGia) {
        this.DonGia = DonGia;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

//    public Float setGiamGia() {
//        if (IdCTSP.getIdDGG() != null && IdCTSP.getIdDGG().getTrangThai() == 1) {
//            if (IdCTSP.getIdDGG().getLoai() == 0) {
//                return IdCTSP.getIdDGG().getGiaTri();
//            } else if (IdCTSP.getIdDGG().getLoai() == 1) {
//                return IdCTSP.getGiaNiemYet() - IdCTSP.tinhGiaBan();
//            }
//        }
//        return 0f;
//    }
//    public BigDecimal setThanhTien2() {
//        BigDecimal thanhTien = BigDecimal.valueOf(Long.parseLong(soLuong + "")).multiply(IdCTSP.tinhGiaBan());
//        //soLuong * IdCTSP.tinhGiaBan();
//        System.err.println("TT : " + thanhTien);
//        return thanhTien;
//    }
    public Object[] rowDataGioHang(int i) {
        String str = "";
        if (loaiDGG != null) {
            if (loaiDGG == 0) {
                str = " ( " + (giaTriDGG == null ? 0 : giaTriDGG) + " %)  ";
            } else if (loaiDGG == 1) {//(IdCTSP.getGiaNiemYet() - IdCTSP.tinhGiaBan()) + 
                str = " VND ";
            }
        } else {
            str = "Không có";
        }

        return new Object[]{
            i,
            IdCTSP.getMaSPCT(),
            IdCTSP.getIdSanPham().getTenSanpham(),
            DonGia,
            soLuong,
            Format.format1((DonGia.subtract(GiaBan))) + str,
            Format.format(GiaBan),
            Format.format(thanhTien)

        };
    }

    public Object[] rowDataHD(int index) {
        return new Object[]{
            index, IdCTSP.getMaSPCT(), IdCTSP.getIdSanPham().getTenSanpham(), soLuong, Format.format(GiaBan), Format.format(thanhTien)
        };
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" + "id=" + id + ", IdHoaDon=" + IdHoaDon + ", IdCTSP=" + IdCTSP + ", soLuong=" + soLuong + ", maDGG=" + maDGG + ", loaiDGG=" + loaiDGG + ", giaTriDGG=" + giaTriDGG + ", quyDoiDGGTT=" + quyDoiDGGTT + ", GiaBan=" + GiaBan + ", DonGia=" + DonGia + ", thanhTien=" + thanhTien + ", ngayTao=" + ngayTao + ", trangThai=" + trangThai + '}';
    }

    public Object[] rowDataTraHang(int i) {
        return new Object[]{
            i,
            IdCTSP.getMaSPCT(),
            IdCTSP.getIdSanPham().getTenSanpham(),
            soLuong,
            Format.format(GiaBan),
            Format.format(thanhTien)
        };
    }

}
