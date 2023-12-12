/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author manhnt
 */
public class KichThuoc {

    private Long idSize;
    private String maSize;
    private float tenSize;
    private int trangThai;

    public KichThuoc() {
    }

    public KichThuoc(float tenSize) {
        this.tenSize = tenSize;
    }

    public KichThuoc(Long idSize) {
        this.idSize = idSize;
    }

    public KichThuoc(Long idSize, float tenSize) {
        this.idSize = idSize;
        this.tenSize = tenSize;
    }

    public KichThuoc(Long idSize, String maSize, float tenSize) {
        this.idSize = idSize;
        this.maSize = maSize;
        this.tenSize = tenSize;
    }

    public KichThuoc(Long idSize, String maSize, float tenSize, int trangThai) {
        this.idSize = idSize;
        this.maSize = maSize;
        this.tenSize = tenSize;
        this.trangThai = trangThai;
    }

    public KichThuoc(String maSize, float tenSize, int trangThai) {
        this.maSize = maSize;
        this.tenSize = tenSize;
        this.trangThai = trangThai;
    }


    public Long getIdSize() {
        return idSize;
    }

    public void setIdSize(Long idSize) {
        this.idSize = idSize;
    }

    public String getMaSize() {
        return maSize;
    }

    public void setMaSize(String maSize) {
        this.maSize = maSize;
    }

    public float getTenSize() {
        return tenSize;
    }

    public void setTenSize(float tenSize) {
        this.tenSize = tenSize;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return  tenSize +"";
    }


    

    
}
