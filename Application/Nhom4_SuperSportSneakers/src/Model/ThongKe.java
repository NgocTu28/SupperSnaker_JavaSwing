/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.HoaDon;
import Model.PhieuGiamGia;

/**
 *
 * @author Admin
 */
public class ThongKe {
    private HoaDon maHD;
    private PhieuGiamGia maPhieu;
    private HoaDon ngayTaoHD;
    private PhieuGiamGia tenPhieu;
    //private int soLanSuDung;

    public ThongKe() {
    }

    public ThongKe(HoaDon maHD, PhieuGiamGia maPhieu, HoaDon ngayTaoHD, PhieuGiamGia tenPhieu) {
        this.maHD = maHD;
        this.maPhieu = maPhieu;
        this.ngayTaoHD = ngayTaoHD;
        this.tenPhieu = tenPhieu;
    }

    public HoaDon getMaHD() {
        return maHD;
    }

    public void setMaHD(HoaDon maHD) {
        this.maHD = maHD;
    }

    public PhieuGiamGia getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(PhieuGiamGia maPhieu) {
        this.maPhieu = maPhieu;
    }

    public HoaDon getNgayTaoHD() {
        return ngayTaoHD;
    }

    public void setNgayTaoHD(HoaDon ngayTaoHD) {
        this.ngayTaoHD = ngayTaoHD;
    }

    public PhieuGiamGia getTenPhieu() {
        return tenPhieu;
    }

    public void setTenPhieu(PhieuGiamGia tenPhieu) {
        this.tenPhieu = tenPhieu;
    }

    
}
