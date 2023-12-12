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
public class NhanVien {

    private Long id;
    private String MaNhanVien;
    private String HovaTen;  
    private  String Password;
    private  String CCCD;
    private String Email;
    private String DiaChi;
    private String SDT;
    private Date ngaysinh;
    private Date ngaytao;
    private boolean vaitro;
    private boolean trangthailamviec;

    public NhanVien() {
    }

    public NhanVien(Long id) {
        this.id = id;
    }

    public NhanVien(Long id, String tenNhanVien) {
        this.id = id;
        this.HovaTen = tenNhanVien;
    }

    public NhanVien(Long id, String maNhanVien, String tenNhanVien) {
        this.id = id;
        this.MaNhanVien = maNhanVien;
        this.HovaTen = tenNhanVien;
    }
    
    public NhanVien(String MaNhanVien, String HovaTen, String Password, String CCCD, String Email, String DiaChi, String SDT, Date ngaysinh, Date ngaytao, boolean vaitro, boolean trangthailamviec) {
        this.MaNhanVien = MaNhanVien;
        this.HovaTen = HovaTen;
        this.Password = Password;
        this.CCCD = CCCD;
        this.Email = Email;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.ngaysinh = ngaysinh;
        this.ngaytao = ngaytao;
        this.vaitro = vaitro;
        this.trangthailamviec = trangthailamviec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.MaNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return HovaTen;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.HovaTen = tenNhanVien;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }

    public boolean isVaitro() {
        return vaitro;
    }

    public void setVaitro(boolean vaitro) {
        this.vaitro = vaitro;
    }

    public boolean isTrangthailamviec() {
        return trangthailamviec;
    }

    public void setTrangthailamviec(boolean trangthailamviec) {
        this.trangthailamviec = trangthailamviec;
    }

    
    @Override
    public String toString() {
        return "NhanVien_M{" + "id=" + id + ", maNhanVien=" + MaNhanVien + ", tenNhanVien=" + HovaTen + '}';
    }

}
