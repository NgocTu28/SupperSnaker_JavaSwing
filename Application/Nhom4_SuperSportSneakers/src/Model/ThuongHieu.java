/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author manhnt
 */
public class ThuongHieu {

    private Long idThuongHieu;
    private String maThuongHieu;
    private String tenThuongHieu;
    private int trangThai;

    public ThuongHieu() {
    }

    public ThuongHieu(Long idThuongHieu, String maThuongHieu, String tenThuongHieu, int trangThai) {
        this.idThuongHieu = idThuongHieu;
        this.maThuongHieu = maThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
        this.trangThai = trangThai;
    }

    public ThuongHieu(String maThuongHieu, String tenThuongHieu, int trangThai) {
        this.maThuongHieu = maThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
        this.trangThai = trangThai;
    }

    public ThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public ThuongHieu(Long idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public ThuongHieu(Long idThuongHieu, String maThuongHieu, String tenThuongHieu) {
        this.idThuongHieu = idThuongHieu;
        this.maThuongHieu = maThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
    }

    public ThuongHieu(Long idThuongHieu, String tenThuongHieu) {
        this.idThuongHieu = idThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
    }

    public Long getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(Long idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public String getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(String maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return tenThuongHieu + "";
    }
    
}
