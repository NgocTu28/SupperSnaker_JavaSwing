/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;

/**
 *
 * @author manhnt
 */
public class ChiTietDotGiamGia {
    private Long id;
    private DotGiamGia_M idDGG;
    private Long idCTSP;
    private String msSP;
    private BigDecimal donGia;
    private BigDecimal donGiaConLai;
    private Float giaTriGiam;
    private Integer trangThai;

    public ChiTietDotGiamGia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DotGiamGia_M getIdDGG() {
        return idDGG;
    }

    public void setIdDGG(DotGiamGia_M idDGG) {
        this.idDGG = idDGG;
    }

    public Long getIdCTSP() {
        return idCTSP;
    }

    public void setIdCTSP(Long idCTSP) {
        this.idCTSP = idCTSP;
    }

    public String getMsSP() {
        return msSP;
    }

    public void setMsSP(String msSP) {
        this.msSP = msSP;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getDonGiaConLai() {
        return donGiaConLai;
    }

    public void setDonGiaConLai(BigDecimal donGiaConLai) {
        this.donGiaConLai = donGiaConLai;
    }

    public Float getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(Float giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
