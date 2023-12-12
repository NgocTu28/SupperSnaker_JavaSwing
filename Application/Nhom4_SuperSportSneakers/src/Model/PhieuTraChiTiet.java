/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author manhnt
 */
public class PhieuTraChiTiet {

    private Long id;
    private PhieuTra idPT;
    private String maCTSP;
    private String tenSP;
    private String mau;
    private Float size;
    private String thuongHieu;
    private Integer soLuong;
    private Date ngayTao;

    public PhieuTraChiTiet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhieuTra getIdPT() {
        return idPT;
    }

    public void setIdPT(PhieuTra idPT) {
        this.idPT = idPT;
    }

    public String getMaCTSP() {
        return maCTSP;
    }

    public void setMaCTSP(String maCTSP) {
        this.maCTSP = maCTSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "PhieuTraChiTiet{" + "id=" + id + ", idPT=" + idPT + ", maCTSP=" + maCTSP + ", tenSP=" + tenSP + ", mau=" + mau + ", size=" + size + ", thuongHieu=" + thuongHieu + ", soLuong=" + soLuong + ", ngayTao=" + ngayTao + '}';
    }

    
    public Object[]rowData (int i ){
        return new Object[]{
            i , maCTSP , tenSP , mau ,thuongHieu , size , soLuong
        };
    }
}
