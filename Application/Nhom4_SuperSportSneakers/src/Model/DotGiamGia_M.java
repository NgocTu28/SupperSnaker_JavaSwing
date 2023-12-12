/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Utils.Format;
import Utils.XDate;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author manhnt
 */
public class DotGiamGia_M {

    private Long idDGG;
    private NhanVien idNV;
    private String maDGG;
    private String tenDGG;
    private Integer hinhThucDGG;
    private BigDecimal giaTri;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String moTa;
    private Date ngayTao;
    private Integer trangThai;//---0 SẮP ĐẾN , 1 ĐANG ĐƯỢC ÁP DỤNG , 2 KẾT THÚC ĐỢT GIẢM GIÁ 

    public DotGiamGia_M() {
    }

    public DotGiamGia_M(Long id, NhanVien idNV, String MaDGG, String tenDGG, Integer hinhThucDGG, BigDecimal giaTri, Date ngayBatDau, Date ngayKetThuc, String moTa, Date ngayTao, Integer trangThai) {
        this.idDGG = id;
        this.idNV = idNV;
        this.maDGG = MaDGG;
        this.tenDGG = tenDGG;
        this.hinhThucDGG = hinhThucDGG;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    public DotGiamGia_M(NhanVien idNV, String MaDGG, String tenDGG, Integer hinhThucDGG, BigDecimal giaTri, Date ngayBatDau, Date ngayKetThuc, String moTa, Integer trangThai) {
        this.idNV = idNV;
        this.maDGG = MaDGG;
        this.tenDGG = tenDGG;
        this.hinhThucDGG = hinhThucDGG;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public DotGiamGia_M(Long id, NhanVien idNV, String MaDGG, String tenDGG, Integer hinhThucDGG, BigDecimal giaTri, Date ngayBatDau, Date ngayKetThuc, String moTa, Integer trangThai) {
        this.idDGG = id;
        this.idNV = idNV;
        this.maDGG = MaDGG;
        this.tenDGG = tenDGG;
        this.hinhThucDGG = hinhThucDGG;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public DotGiamGia_M(Long id, String MaDGG, Integer hinhThucDGG, BigDecimal giaTri, Integer trangThai) {
        this.idDGG = id;
        this.maDGG = MaDGG;
        this.hinhThucDGG = hinhThucDGG;
        this.giaTri = giaTri;
        this.trangThai = trangThai;
    }

    public DotGiamGia_M(long idDGG, String maDGG, String tenDGG, Integer hinhThucDGG, BigDecimal giaTri, Date ngayBatDau, Date ngayKetThuc, String moTa, Date ngayTao, int trangThai) {
        this.idDGG = idDGG;
        this.maDGG = maDGG;
        this.tenDGG = tenDGG;
        this.hinhThucDGG = hinhThucDGG;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    public Long getIdDGG() {
        return idDGG;
    }

    public void setIdDGG(Long idDGG) {
        this.idDGG = idDGG;
    }

    public NhanVien getIdNV() {
        return idNV;
    }

    public void setIdNV(NhanVien idNV) {
        this.idNV = idNV;
    }

    public String getMaDGG() {
        return maDGG;
    }

    public void setMaDGG(String maDGG) {
        this.maDGG = maDGG;
    }

    public String getTenDGG() {
        return tenDGG;
    }

    public void setTenDGG(String tenDGG) {
        this.tenDGG = tenDGG;
    }

    public Integer getHinhThucDGG() {
        return hinhThucDGG;
    }

    public void setHinhThucDGG(Integer hinhThucDGG) {
        this.hinhThucDGG = hinhThucDGG;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
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

    public Object[] rowData(int index) {
        return new Object[]{
            index, idNV.getTenNhanVien(), maDGG, tenDGG, hinhThucDGG == 0 ? "Phần trăm % " : " VND ", Format.format1(giaTri) + (hinhThucDGG == 0 ? "( % )" : "( VND )"), XDate.toString(ngayBatDau, "dd-MM-yyyy"),
            XDate.toString(ngayKetThuc, "dd-MM-yyyy"),
            XDate.toString(ngayTao, "dd-MM-yyyy"), moTa
                , trangThai ==0 ? "Sắp đến" : (trangThai ==1 ? "Đang áp dụng" : (trangThai ==2 ?"Hết hạn": ""))};
    }

    @Override
    public String toString() {
        return "DotGiamGia_M{" + "idDGG=" + idDGG + ", idNV=" + idNV + ", maDGG=" + maDGG + ", tenDGG=" + tenDGG + ", hinhThucDGG=" + hinhThucDGG + ", giaTri=" + giaTri + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + ", moTa=" + moTa + ", ngayTao=" + ngayTao + ", trangThai=" + trangThai + '}';
    }

}
