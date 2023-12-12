/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Utils.Format;
import Utils.XDate2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author tabah
 */
public class ThongKeHDResponsitory {
    
    public float getDoanhThuToday() {
        String sql = "select SUM(ThanhTien) as N'Tổng Tiền' from HOADON \n"
                + "where TrangThai not in (0,4) and NgayTao=GETDATE()";
        float thanhtien = 0;
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                thanhtien = rs.getFloat(1);
            }
            rs.close();
            stm.close();
            con.close();
            return thanhtien;
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
            return 0;
        }
    }

    public float getDoanhThuThisMonth() {
        String sql = "select SUM(ThanhTien) as N'Tổng Tiền' from HOADON \n"
                + "where TrangThai not in (0,4) and Month(NgayTao)=Month(GETDATE())";
        float thanhtien = 0;
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                thanhtien = rs.getFloat(1);
            }
            rs.close();
            stm.close();
            con.close();
            return thanhtien;
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
            return 0;
        }
    }

    public int gettotalinvoicetoday() {
        String sql = "select count(*) from HOADON\n"
                + "where NgayTao = GETDATE()";
        int total = 0;
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
        }
        return total;
    }

    public int gettotalinvoicethismonth() {
        String sql = "select count(*) from HOADON\n"
                + "where Month(NgayTao)=Month(GETDATE())";
        int total = 0;
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
        }
        return total;
    }

    public int gettotalcustomersthismonth() {
        String sql = "select count(*) from KHACHHANG\n"
                + "where Month(NgayTao)=Month(GETDATE())";
        int total = 0;
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
        }
        return total;
    }

    public int gettotalcustomerstoday() {
        String sql = "select count(*) from KHACHHANG\n"
                + "where NgayTao=GETDATE()";
        int total = 0;
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
        }
        return total;
    }

    public int gettotalsptoday() {
        String sql = "select count(*) from HOADONCHITIET\n"
                + "where NgayTao = GETDATE()";
        int total = 0;
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
        }
        return total;
    }

    public int gettotalspthismonth() {
        String sql = "select count(*) from HOADONCHITIET\n"
                + "where Month(NgayTao)=Month(GETDATE())";
        int total = 0;
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
        }
        return total;
    }

    public List<Vector> gettop5nv() {
        String sql = "select top 5 with ties NHANVIEN.MaNhanVien,NHANVIEN.HoVaTen,SUM(HOADON.ThanhTien) as N'Doanh Số' from HOADON\n"
                + "join NHANVIEN on HOADON.IdNV=NHANVIEN.ID\n"
                + "where HOADON.TrangThai not in (0,4) and Month(HOADON.NgayTao)=Month(GETDATE())\n"
                + "group by NHANVIEN.MaNhanVien,NHANVIEN.HoVaTen\n"
                + "order by SUM(HOADON.ThanhTien) desc";
        List<Vector> lst = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            int i = 0;
            while (rs.next()) {
                i++;
                Vector<Object> nv = new Vector<>();
                nv.add(i);
                nv.add(rs.getString(1));
                nv.add(rs.getString(2));
                nv.add(Format.format(rs.getBigDecimal(3)));
//                nv.add(rs.getFloat(3));
                lst.add(nv);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
            return null;
        }
        return lst;
    }

    public List<Vector> gettop5kh() {
        String sql = "select  top 5 with ties KHACHHANG.MaKhachHang,KHACHHANG.TenKhachHang,SUM(HOADON.ThanhTien) as 'Total' from HOADON\n"
                + "join KHACHHANG on HOADON.IdKH=KHACHHANG.ID\n"
                + "group by KHACHHANG.MaKhachHang,KHACHHANG.TenKhachHang\n"
                + "order by SUM(HOADON.ThanhTien) desc";
        List<Vector> lst = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            int i = 0;
            while (rs.next()) {
                i++;
                Vector<Object> kh = new Vector<>();
                kh.add(i);
                kh.add(rs.getString(1));
                kh.add(rs.getString(2));
                 kh.add(Format.format(rs.getBigDecimal(3)));
                lst.add(kh);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
            return null;
        }
        return lst;
    }

    public List<Vector> getHoadon(Date start, Date end) {
        String sql = "select HOADON.MaHoaDon,KHACHHANG.TenKhachHang,HOADON.ThanhTien,HOADON.NgayTao,HOADON.NgayThanhToan from HOADON\n"
                + "join KHACHHANG on KHACHHANG.ID=HOADON.IdKH where HOADON.TrangThai not in (0,4) ";
                
        int check = 0;
        boolean only_end = false;
        if (start != null) { //Nếu có ngày bắt đầu
            check++;
            sql += "and HOADON.NgayTao>? ";
            if (end != null) { //Nếu có thêm ngày kết thúc
                check++;
                sql += "and HOADON.NgayTao<?";
            }
        } else if (end != null) { //Nếu chỉ có ngày kết thúc
            only_end = true;
            sql += " and HOADON.NgayTao<? ";
            check++;
        }
        sql+= " order by HOADON.NgayTao DESC";
        List<Vector> lst = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            if (check > 1) {
                stm.setDate(1, XDate2.convertUtilDateToSqlDate(start));
                stm.setDate(2, XDate2.convertUtilDateToSqlDate(end));
            } else if (check == 1 && only_end) {
                stm.setDate(1, XDate2.convertUtilDateToSqlDate(end));
            } else if (check == 1 && only_end == false) {
                stm.setDate(1, XDate2.convertUtilDateToSqlDate(start));
            }
            ResultSet rs = stm.executeQuery();
            int i = 0;
            while (rs.next()) {
                i++;
                Vector<Object> hd = new Vector<>();
                hd.add(i);
                hd.add(rs.getString(1));
                hd.add(rs.getString(2));
                hd.add(rs.getFloat(3));
                Date datecreated = rs.getDate(4);
                Date datepayment = rs.getDate(5);
                hd.add(datecreated);
                hd.add(datepayment);
                lst.add(hd);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn");
            return null;
        }
        return lst;
    }

    public double[][] getdatatousechart() {
        String sql = "select DAY(NgayTao) as N'Tháng',SUM(THANHTIEN) as 'Doanh Thu' from HOADON\n"
                + "where TrangThai not in (0,4) and Month(NgayTao)=Month(GETDATE()) and Year(NgayTao)=year(getdate()) \n"
                + "group by DAY(NgayTao)";
        try {
            // Tạo một đối tượng YearMonth
            YearMonth yearMonth = YearMonth.of(2023, 11);
            // Lấy số ngày trong tháng
            int daysInMonth = yearMonth.lengthOfMonth();
            double[][] lst= new  double[daysInMonth][2];
            Connection con = DBConnection.getConnect();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            for(int i=0;i<daysInMonth;i++){
                lst[i][0]=i+1;
                lst[i][1]=0;
            }
            while (rs.next()) {
               int i=rs.getInt(1)-1;
               lst[i][1]=rs.getDouble(2);
            }
            rs.close();
            stm.close();
            con.close();
            return lst;
        } catch (SQLException e) {
            return null;
        }
        
    }
}
