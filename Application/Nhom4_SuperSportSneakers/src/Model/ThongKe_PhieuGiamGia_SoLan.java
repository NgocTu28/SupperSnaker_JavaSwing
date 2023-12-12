package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class ThongKe_PhieuGiamGia_SoLan {
    private String maPhieu;
    private String tenPhieu;
    private int soLanSuDung;

    public ThongKe_PhieuGiamGia_SoLan() {
    }

    public ThongKe_PhieuGiamGia_SoLan(String maPhieu, String tenPhieu, int soLanSuDung) {
        this.maPhieu = maPhieu;
        this.tenPhieu = tenPhieu;
        this.soLanSuDung = soLanSuDung;
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

    public int getSoLanSuDung() {
        return soLanSuDung;
    }

    public void setSoLanSuDung(int soLanSuDung) {
        this.soLanSuDung = soLanSuDung;
    }
    
    
}
