/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Model.HoaDon;
import Model.PhieuGiamGia;
import Model.ThongKe;
import Model.ThongKe_PhieuGiamGia_SoLan;
import Repository.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ThongKeRespository {
    public ArrayList<ThongKe> getAllThongKe(){
        ArrayList<ThongKe> listTK= new ArrayList<>();
        Connection conn= DBConnection.getConnect();
        String sql="select HOADON.MaHoaDon, PHIEU_GIAM_GIA.MaPhieu,HOADON.NgayTao from HOADON left JOIN PHIEU_GIAM_GIA ON HOADON.IdPGG = PHIEU_GIAM_GIA.ID group by HOADON.MaHoaDon,PHIEU_GIAM_GIA.MaPhieu,HOADON.NgayTao ";
        try {
            PreparedStatement pr= conn.prepareStatement(sql);
            ResultSet rs= pr.executeQuery();
            while(rs.next()){
                ThongKe tk= new ThongKe();
                //hd.setIdHD(rs.getLong(1));   
                HoaDon hd= new HoaDon();
                PhieuGiamGia pgg= new PhieuGiamGia();
                pgg.setMaPhieu(rs.getString(2));
                hd.setMaHoaDon(rs.getString(1));
                hd.setNgayTao(rs.getDate(3));
                tk.setMaHD(hd);
                tk.setMaPhieu(pgg);
                tk.setNgayTaoHD(hd);
                
                listTK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTK;
    }
    
    public ArrayList<ThongKe_PhieuGiamGia_SoLan> getCount(){
        ArrayList<ThongKe_PhieuGiamGia_SoLan> listTKSP= new ArrayList<>();
               Connection conn= DBConnection.getConnect();

        String sql="select PHIEU_GIAM_GIA.id, PHIEU_GIAM_GIA.MaPhieu, PHIEU_GIAM_GIA.TenPhieu, COUNT(HOADON.id) as 'SoLanSuDung' from PHIEU_GIAM_GIA left join  HOADON on PHIEU_GIAM_GIA.id = HOADON.IdPGG group by PHIEU_GIAM_GIA.id, PHIEU_GIAM_GIA.MaPhieu, PHIEU_GIAM_GIA.TenPhieu";
        try {
            PreparedStatement pr= conn.prepareStatement(sql);
            ResultSet rs= pr.executeQuery();
            while(rs.next()){
//                ThongKe tk= new ThongKe();
//                //hd.setIdHD(rs.getLong(1));   
//                //HoaDon hd= new HoaDon();
//                PhieuGiamGia pgg= new PhieuGiamGia();
//                pgg.setMaPhieu(rs.getString(1));
//                pgg.setTenPhieu(rs.getString(2));
//                //hd.setMaHD(rs.getString(1));
//                //hd.setNgayTaoHD(rs.getDate(3));
//                //tk.setMaHD(hd);
//                tk.setMaPhieu(pgg);
//                tk.setTenPhieu(pgg);
//                //tk.setNgayTaoHD(hd);
//                
//                listTK.add(tk);
                String maPhieu = rs.getString("MaPhieu");
                String tenPhieu = rs.getString("TenPhieu");
                int soLanSuDung = rs.getInt("SoLanSuDung");
                ThongKe_PhieuGiamGia_SoLan tksp = new ThongKe_PhieuGiamGia_SoLan(maPhieu, tenPhieu, soLanSuDung);
                listTKSP.add(tksp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTKSP;
    }
}
