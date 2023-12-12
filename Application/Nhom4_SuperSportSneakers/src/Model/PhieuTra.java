/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Utils.Format;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author manhnt
 */
public class PhieuTra {
    
    private Long id;
    private HoaDon idHD ;
    private NhanVien idNV;
    private String maPhieu ;
    private Date ngayTra;
    private BigDecimal tienHoanTra;
    private String ghiChu;
    private Date ngayTao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HoaDon getIdHD() {
        return idHD;
    }

    public void setIdHD(HoaDon idHD) {
        this.idHD = idHD;
    }

    public NhanVien getIdNV() {
        return idNV;
    }

    public void setIdNV(NhanVien idNV) {
        this.idNV = idNV;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public BigDecimal getTienHoanTra() {
        return tienHoanTra;
    }

    public void setTienHoanTra(BigDecimal tienHoanTra) {
        this.tienHoanTra = tienHoanTra;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "PhieuTra{" + "id=" + id + ", idHD=" + idHD + ", idNV=" + idNV + ", maPhieu=" + maPhieu + ", ngayTra=" + ngayTra + ", tienHoanTra=" + tienHoanTra + ", ghiChu=" + ghiChu + ", ngayTao=" + ngayTao + '}';
    }
    
    public Object[] rowData (int i ){
        return new Object[]{
            i, maPhieu , idHD.getMaHoaDon() ,ngayTra , Format.format(tienHoanTra) , ghiChu
        };
    }
    
    
}
