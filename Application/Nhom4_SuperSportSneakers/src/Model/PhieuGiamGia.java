package Model;

import Repository.PhieuGiamGiaService;
import java.math.BigDecimal;
import java.util.Date;
import Utils.PhieuGiamGia_TrangThai;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import Utils.Format;

public class PhieuGiamGia {

    
    private NhanVien idNV;
    private Long idPGG;
    private String maPhieu;
    private String tenPhieu;
    private int loaiPhieu;
    private BigDecimal giaTri;
    private int soLuongPhieu;
    private BigDecimal donToiThieu;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Date ngayTao;
    private String moTa;
    private int trangThai;

    public PhieuGiamGia() {
    }

    public PhieuGiamGia(NhanVien idNV, String maPhieu, String tenPhieu, int loaiPhieu, BigDecimal giaTri, int soLuongPhieu, BigDecimal donToiThieu, Date ngayBatDau, Date ngayKetThuc, String moTa, int trangThai) {
        this.idNV = idNV;
        this.maPhieu = maPhieu;
        this.tenPhieu = tenPhieu;
        this.loaiPhieu = loaiPhieu;
        this.giaTri = giaTri;
        this.soLuongPhieu = soLuongPhieu;
        this.donToiThieu = donToiThieu;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public PhieuGiamGia(NhanVien idNV, Long idPGG, String maPhieu, String tenPhieu, int loaiPhieu, BigDecimal giaTri, int soLuongPhieu, BigDecimal donToiThieu, Date ngayBatDau, Date ngayKetThuc, Date ngayTao, String moTa, int trangThai) {
        this.idNV = idNV;
        this.idPGG = idPGG;
        this.maPhieu = maPhieu;
        this.tenPhieu = tenPhieu;
        this.loaiPhieu = loaiPhieu;
        this.giaTri = giaTri;
        this.soLuongPhieu = soLuongPhieu;
        this.donToiThieu = donToiThieu;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayTao = ngayTao;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public NhanVien getIdNV() {
        return idNV;
    }

    public void setIdNV(NhanVien idNV) {
        this.idNV = idNV;
    }

   

    public Long getIdPGG() {
        return idPGG;
    }

    public void setIdPGG(Long idPGG) {
        this.idPGG = idPGG;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getTenPhieu() {
        return tenPhieu;
    }

    public void setTenPhieu(String tenPhieu) {
        this.tenPhieu = tenPhieu;
    }

    public int getLoaiPhieu() {
        return loaiPhieu;
    }

    public void setLoaiPhieu(int loaiPhieu) {
        this.loaiPhieu = loaiPhieu;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }

    public int getSoLuongPhieu() {
        return soLuongPhieu;
    }

    public void setSoLuongPhieu(int soLuongPhieu) {
        this.soLuongPhieu = soLuongPhieu;
    }

    public BigDecimal getDonToiThieu() {
        return donToiThieu;
    }

    public void setDonToiThieu(BigDecimal donToiThieu) {
        this.donToiThieu = donToiThieu;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTrangThaiString() {
        String trangThai = "";
        if (this.trangThai == PhieuGiamGia_TrangThai.sapDienRa) {
            trangThai = "Sắp diễn ra";
        }
        if (this.trangThai == PhieuGiamGia_TrangThai.dangDienRa) {
            trangThai = "Đang diễn ra";
        }
        if (this.trangThai == PhieuGiamGia_TrangThai.daHetHan) {
            trangThai = "Đã hết hạn";
        }
        return trangThai;
    }

    public NhanVien getNhanVien() {
        NhanVien nv = PhieuGiamGiaService.getNhanVien(this.idPGG);
        return nv;
    }

    public Object[] rowDate(int i){
        return new Object[]{
            i , idNV.getTenNhanVien() , maPhieu , tenPhieu , loaiPhieu == 0 ? "%" :"VND",
            giaTri , soLuongPhieu, Format.format(donToiThieu) , ngayBatDau , ngayKetThuc , ngayTao, moTa , getTrangThaiString()
        };
    }
    
    public Object [] rowDataKH_BH (int i){
        return new Object[]{
            i , maPhieu , tenPhieu , loaiPhieu== 0 ?"Phần trăm ( % )": " VNĐ ",   loaiPhieu== 0 ? giaTri + " ( % ) ":Format.format1(giaTri) +" VNĐ " , Format.format(donToiThieu)
        };
    }

    @Override
    public String toString() {
        return "PhieuGiamGia{" + "idNV=" + idNV + ", idPGG=" + idPGG + ", maPhieu=" + maPhieu + ", tenPhieu=" + tenPhieu + ", loaiPhieu=" + loaiPhieu + ", giaTri=" + giaTri + ", soLuongPhieu=" + soLuongPhieu + ", donToiThieu=" + donToiThieu + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + ", ngayTao=" + ngayTao + ", moTa=" + moTa + ", trangThai=" + trangThai + '}';
    }
    
    
}
