/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.util.Date;
import Utils.Format;
/**
 *
 * @author manhnt
 */
public class HoaDon {

    private Long id;
    private PhieuGiamGia IdPGG;
    private NhanVien IdNV;
    private KhachHang IdKH;
    private String maHoaDon;
    private Integer capBac;
    private Float phanTramGG;//	PhanTramGia INT NULL , --- CẤP BẬC + PHIẾU THEO % ++ Doi thanh FLoat
    private BigDecimal tienPhieuGiam;
    private BigDecimal diemDoi; //	DiemDoi INT NULL, -- tiền khách hàng từ đó lấy ra tiền 1 diem = 10k => MONEY
    private Integer phuongThucTT;
    private BigDecimal tongTienSP;
    private BigDecimal tienKhDua;
    private BigDecimal tienKhChuyenKhoan;
    private BigDecimal tienThua;
    private BigDecimal thanhTien;
    private Integer loai;
    private Date ngayTao;
    private Date ngayThanhToan;
    private boolean hinhThucMua;
    private String qr;
    private Integer trangThai;

    public HoaDon() {
    }

    public HoaDon(NhanVien IdNV, KhachHang IdKH, String maHoaDon, Integer capBac, Float phanTramGG, BigDecimal tienPhieuGiam, BigDecimal diemDoi, Integer phuongThucTT, BigDecimal tienKhDua, BigDecimal tienKhChuyenKhoan, BigDecimal tienThua, BigDecimal thanhTien, Date ngayThanhToan, boolean hinhThucMua, Integer trangThai) {
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.maHoaDon = maHoaDon;
        this.capBac = capBac;
        this.phanTramGG = phanTramGG;
        this.tienPhieuGiam = tienPhieuGiam;
        this.diemDoi = diemDoi;
        this.phuongThucTT = phuongThucTT;
        this.tienKhDua = tienKhDua;
        this.tienKhChuyenKhoan = tienKhChuyenKhoan;
        this.tienThua = tienThua;
        this.thanhTien = thanhTien;
        this.ngayThanhToan = ngayThanhToan;
        this.hinhThucMua = hinhThucMua;
        this.trangThai = trangThai;
    }

    public HoaDon(PhieuGiamGia IdPGG, NhanVien IdNV, KhachHang IdKH, String maHoaDon) {
        this.IdPGG = IdPGG;
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.maHoaDon = maHoaDon;
    }

   

    public HoaDon(Long id, PhieuGiamGia IdPGG, NhanVien IdNV, KhachHang IdKH, Integer trangThai) {
        this.id = id;
        this.IdPGG = IdPGG;
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.trangThai = trangThai;
    }

    public HoaDon(Long id, NhanVien IdNV, KhachHang IdKH, Integer trangThai) {
        this.id = id;
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.trangThai = trangThai;
    }

    public HoaDon(Long id,PhieuGiamGia IdPGG, NhanVien IdNV, KhachHang IdKH, String maHoaDon,Date ngayTao ,Integer trangThai) {
        this.id = id;
        this.IdPGG = IdPGG;
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.maHoaDon = maHoaDon;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
    }

    public HoaDon(PhieuGiamGia IdPGG, NhanVien IdNV, KhachHang IdKH, String maHoaDon, Integer capBac, Float phanTramGG, BigDecimal tienPhieuGiam, BigDecimal diemDoi, Integer phuongThucTT, BigDecimal TienKhDua, BigDecimal TienKhChuyenKhoan, BigDecimal TienThua, BigDecimal ThanhTien, boolean hinhThucMua, Integer trangThai) {
        this.IdPGG = IdPGG;
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.maHoaDon = maHoaDon;
        this.capBac = capBac;
        this.phanTramGG = phanTramGG;
        this.tienPhieuGiam = tienPhieuGiam;
        this.diemDoi = diemDoi;
        this.phuongThucTT = phuongThucTT;
        this.tienKhDua = TienKhDua;
        this.tienKhChuyenKhoan = TienKhChuyenKhoan;
        this.tienThua = TienThua;
        this.thanhTien = ThanhTien;
        this.hinhThucMua = hinhThucMua;
        this.trangThai = trangThai;
    }

    public HoaDon(PhieuGiamGia IdPGG, NhanVien IdNV, KhachHang IdKH, String maHoaDon, Integer capBac, Float phanTramGG, BigDecimal tienPhieuGiam, BigDecimal diemDoi, Integer phuongThucTT, BigDecimal TienKhDua, BigDecimal TienKhChuyenKhoan, BigDecimal TienThua, BigDecimal ThanhTien, Date NgayThanhToan, boolean hinhThucMua, Integer trangThai) {
        this.IdPGG = IdPGG;
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.maHoaDon = maHoaDon;
        this.capBac = capBac;
        this.phanTramGG = phanTramGG;
        this.tienPhieuGiam = tienPhieuGiam;
        this.diemDoi = diemDoi;
        this.phuongThucTT = phuongThucTT;
        this.tienKhDua = TienKhDua;
        this.tienKhChuyenKhoan = TienKhChuyenKhoan;
        this.tienThua = TienThua;
        this.thanhTien = ThanhTien;
        this.ngayThanhToan = NgayThanhToan;
        this.hinhThucMua = hinhThucMua;
        this.trangThai = trangThai;
    }

    public HoaDon(Long id, PhieuGiamGia IdPGG, NhanVien IdNV, KhachHang IdKH, String maHoaDon, Integer capBac, Float phanTramGG, BigDecimal tienPhieuGiam, BigDecimal diemDoi, Integer phuongThucTT, BigDecimal tienKhDua, BigDecimal tienKhChuyenKhoan, BigDecimal tienThua, BigDecimal thanhTien, boolean hinhThucMua, Integer trangThai) {
        this.id = id;
        this.IdPGG = IdPGG;
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.maHoaDon = maHoaDon;
        this.capBac = capBac;
        this.phanTramGG = phanTramGG;
        this.tienPhieuGiam = tienPhieuGiam;
        this.diemDoi = diemDoi;
        this.phuongThucTT = phuongThucTT;
        this.tienKhDua = tienKhDua;
        this.tienKhChuyenKhoan = tienKhChuyenKhoan;
        this.tienThua = tienThua;
        this.thanhTien = thanhTien;
        this.hinhThucMua = hinhThucMua;
        this.trangThai = trangThai;
    }

    public HoaDon(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTongTienSP() {
        return tongTienSP;
    }

    public void setTongTienSP(BigDecimal tongTienSP) {
        this.tongTienSP = tongTienSP;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public Integer getLoai() {
        return loai;
    }

    public void setLoai(Integer loai) {
        this.loai = loai;
    }

    public PhieuGiamGia getIdPGG() {
        return IdPGG;
    }

    public void setIdPGG(PhieuGiamGia IdPGG) {
        this.IdPGG = IdPGG;
    }

    public NhanVien getIdNV() {
        return IdNV;
    }

    public void setIdNV(NhanVien IdNV) {
        this.IdNV = IdNV;
    }

    public KhachHang getIdKH() {
        return IdKH;
    }

    public void setIdKH(KhachHang IdKH) {
        this.IdKH = IdKH;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Integer getCapBac() {
        return capBac;
    }

    public void setCapBac(Integer capBac) {
        this.capBac = capBac;
    }

    public Float getPhanTramGG() {
        return phanTramGG;
    }

    public void setPhanTramGG(Float phanTramGG) {
        this.phanTramGG = phanTramGG;
    }

    public BigDecimal getTienPhieuGiam() {
        return tienPhieuGiam;
    }

    public void setTienPhieuGiam(BigDecimal tienPhieuGiam) {
        this.tienPhieuGiam = tienPhieuGiam;
    }

    public BigDecimal getDiemDoi() {
        return diemDoi;
    }

    public void setDiemDoi(BigDecimal diemDoi) {
        this.diemDoi = diemDoi;
    }

    public Integer getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(Integer phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }

    public BigDecimal getTienKhDua() {
        return tienKhDua;
    }

    public void setTienKhDua(BigDecimal TienKhDua) {
        this.tienKhDua = TienKhDua;
    }

    public BigDecimal getTienKhChuyenKhoan() {
        return tienKhChuyenKhoan;
    }

    public void setTienKhChuyenKhoan(BigDecimal TienKhChuyenKhoan) {
        this.tienKhChuyenKhoan = TienKhChuyenKhoan;
    }

    public BigDecimal getTienThua() {
        return tienThua;
    }

    public void setTienThua(BigDecimal TienThua) {
        this.tienThua = TienThua;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal ThanhTien) {
        this.thanhTien = ThanhTien;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.ngayTao = NgayTao;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date NgayThanhToan) {
        this.ngayThanhToan = NgayThanhToan;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isHinhThucMua() {
        return hinhThucMua;
    }

    public void setHinhThucMua(boolean hinhThucMua) {
        this.hinhThucMua = hinhThucMua;
    }

    public String setTrangThaiHD(int trangThaiIP) {
        switch (trangThaiIP) {
            case 0:
                return "Chờ thanh toán";
            case 1:
                return "Đã thanh toán";
            case 2:
                return "Đang giao";
            case 3:
                return "Thanh toán trước";
            case 4:
                return "Đã hủy";
            default:
                return "Đang nghĩ";
        }
    }

    public Object[] rowDataHDBH(int i) {
        return new Object[]{
            i ,maHoaDon, ngayTao, IdNV.getMaNhanVien(), IdKH.getMaKhachHang(), this.setTrangThaiHD(trangThai)
        };
    }

    public Object[] rowDataPGH (int i){
        return new Object[]{
            i , maHoaDon ,ngayTao , IdKH.getTenKhachHang()
        };
    }
    
    public Object [] rowData_HD (int i){
        
        return new Object[]{
            i , maHoaDon , IdNV.getMaNhanVien() , IdKH.getMaKhachHang() , Format.format(thanhTien) , loai== 0 ? "Tại quầy" :"Đặt hàng" , ngayTao , ngayThanhToan, setTrangThaiHD(trangThai)
        };
    }
    @Override
    public String toString() {
        return "HoaDon{" + "id=" + id + ", IdPGG=" + IdPGG + ", IdNV=" + IdNV + ", IdKH=" + IdKH + ", maHoaDon=" + maHoaDon + ", capBac=" + capBac + ", phanTramGG=" + phanTramGG + ", tienPhieuGiam=" + tienPhieuGiam + ", diemDoi=" + diemDoi + ", phuongThucTT=" + phuongThucTT + ", TienKhDua=" + tienKhDua + ", TienKhChuyenKhoan=" + tienKhChuyenKhoan + ", TienThua=" + tienThua + ", ThanhTien=" + thanhTien + ", NgayTao=" + ngayTao + ", NgayThanhToan=" + ngayThanhToan + ", hinhThucMua=" + hinhThucMua + ", trangThai=" + trangThai + '}';
    }
    
    
}
