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
public class KhachHang {

    private Long id;
    private NhanVien idNV;
    private String maKhachHang;
    private String tenKhachHang;
    private String sdt;
    private Date ngaySinh;
    private boolean gioiTinh;
    private String email;
    private String diaChi;
    private Integer diem;
    private Integer capBac;
    private Date ngayTao;
    private Boolean trangThai;

    public KhachHang() {
    }

    public KhachHang(Long id, NhanVien idNV, String maKhachHang, String tenKhachHang, String sdt, Date ngaySinh, boolean gioiTinh, String email, String diaChi, Integer diem, Integer capBac) {
        this.id = id;
        this.idNV = idNV;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.diaChi = diaChi;
        this.diem = diem;
        this.capBac = capBac;
    }

    public KhachHang(Long id, String maKhachHang, String tenKhachHang) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
    }

    public KhachHang(NhanVien idNV, String maKhachHang, String tenKhachHang, String sdt, Date ngaySinh, boolean gioiTinh, String email, String diaChi) {
        this.idNV = idNV;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.diaChi = diaChi;
    }

    public KhachHang(Long id, String maKhachHang, String tenKhachHang, Integer diem, Integer capBac) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.diem = diem;
        this.capBac = capBac;

    }

    public KhachHang(NhanVien idNV, String maKhachHang, String tenKhachHang, String sdt, Date ngaySinh, String email, boolean gioiTinh, String diaChi, Integer diem, Integer capBac) {
        this.idNV = idNV;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.diem = diem;
        this.capBac = capBac;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NhanVien getIdNV() {
        return idNV;
    }

    public void setIdNV(NhanVien idNV) {
        this.idNV = idNV;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Integer getDiem() {
        return diem;
    }

    public void setDiem(Integer diem) {
        this.diem = diem;
    }

    public Integer getCapBac() {
        return capBac;
    }

    public void setCapBac(Integer capBac) {
        this.capBac = capBac;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    public Float setGiamGiaCapBac(int cB) {
        if (cB == 0 || cB == 3) {
            return 0f;
        } else if (cB == 1) {
            return 3f;
        } else {
            return 8f;
        }
    }

    public Object[] rowData(int index) {
        return new Object[]{
            index, maKhachHang, tenKhachHang, gioiTinh ? "Nam" : "Nữ", sdt, diaChi, email, ngaySinh, diem, capBac == 0 ? "Đồng" : (capBac == 1 ? "Bạc" : (capBac == 2 ? "Vàng" : "Không có"))
        };
    }

    public Object[] rowData2() {
        return new Object[]{
            maKhachHang, tenKhachHang, sdt, email, gioiTinh ? "Nam" : "Nữ", diaChi
        };
    }

    @Override
    public String toString() {
        return "KhachHang{" + "id=" + id + ", idNV=" + idNV + ", maKhachHang=" + maKhachHang + ", tenKhachHang=" + tenKhachHang + ", sdt=" + sdt + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", email=" + email + ", diaChi=" + diaChi + ", diem=" + diem + ", capBac=" + capBac + ", ngayTao=" + ngayTao + ", trangThai=" + trangThai + '}';
    }



    public KhachHang getThongTin() {
        return new KhachHang(id, maKhachHang, tenKhachHang, diem, capBac);
    }

}
