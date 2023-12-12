/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Impl;

import Model.HoaDon;
import Model.SanPhamChiTiet;
import Repository.ChiTietHoaDon_RepositoryM;
import Repository.HoaDon_MRepositoryM;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Utils.Format;
import Utils.XDate;

/**
 *
 * @author manhnt
 */
public class ExcelHoaDon {
    
    HoaDon_MRepositoryM hoaDon_MRepositoryM = new HoaDon_MRepositoryM();
    ChiTietHoaDon_RepositoryM chiTietHoaDon_RepositoryM = new ChiTietHoaDon_RepositoryM();
    
    public boolean export(File file, List<HoaDon> lst) {
        // List<HoaDon> lst = hoaDon_MRepositoryM.getAll_Loc_ALL(ngayBD, ngayKT, 0, 0, 0, 0)
        try {
            FileOutputStream fos = new FileOutputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách hóa đơn");
            int rowNum = 0;
            Row firstRow = sheet.createRow(rowNum++);
            
            Cell idDtct = firstRow.createCell(0);
            idDtct.setCellValue("Mã Hóa Đơn");
            
            Cell maSPCTTitle = firstRow.createCell(1);
            maSPCTTitle.setCellValue("Ngày Tạo");
            
            Cell tengiayTitle = firstRow.createCell(2);
            tengiayTitle.setCellValue("Ngày Thanh Toán");
            
            Cell TenThuongHieuTitle = firstRow.createCell(3);
            TenThuongHieuTitle.setCellValue("Tổng Tiền");
            
            Cell TenSizeTitle = firstRow.createCell(4);
            TenSizeTitle.setCellValue("Mã Nhân Viên");
            
            Cell TenMauTitle = firstRow.createCell(5);
            TenMauTitle.setCellValue("Tên Khách Hàng");
            
            Cell SoLuongTonTitle = firstRow.createCell(6);
            SoLuongTonTitle.setCellValue("Loại hóa đơn");

            Cell GiaBanTitle = firstRow.createCell(7);
            GiaBanTitle.setCellValue("Trạng thái");
//
//            Cell GiaNiemYetTitle = firstRow.createCell(8);
//            GiaNiemYetTitle.setCellValue("Giá Niêm Yết");
//
//            Cell MoTaTitle = firstRow.createCell(9);
//            MoTaTitle.setCellValue("Mô Tả");
//
//            Cell TrangThaiTitle = firstRow.createCell(10);
//            TrangThaiTitle.setCellValue("Trạng Thái");

            for (HoaDon x : lst) {
                Row row = sheet.createRow(rowNum++);
//    CTSP.ID,CTSP.MaCTSP,SP.TenSP,TH.TenThuongHieu,S.TenSize,M.TenMau,CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai
                Cell cell2 = row.createCell(0);
                cell2.setCellValue(x.getMaHoaDon());
                
                Cell cell3 = row.createCell(1);
                cell3.setCellValue(XDate.toString(x.getNgayTao(), "dd-MM-yyyy"));
                
                Cell cell4 = row.createCell(2);
                cell4.setCellValue(x.getNgayThanhToan() == null ? "" : XDate.toString(x.getNgayThanhToan(), "dd-MM-yyyy"));
                
                Cell cell5 = row.createCell(3);
                cell5.setCellValue(Format.format(x.getThanhTien()));
                
                Cell cell6 = row.createCell(4);
                cell6.setCellValue(x.getIdNV().getMaNhanVien());
                
                Cell cell7 = row.createCell(5);
                cell7.setCellValue(x.getIdKH().getMaKhachHang());
                
                Cell cell8 = row.createCell(6);
                cell8.setCellValue(x.getLoai()==0? "Tại quầy":"Đặt hàng" );

                Cell cell9 = row.createCell(7);
                cell9.setCellValue(x.setTrangThaiHD(x.getTrangThai())); // Đặt giá trị kiểu dữ liệu phù hợp
//
//                Cell cell10 = row.createCell(8);
//                cell10.setCellValue(x.getGiaNiemYet().toString()); // Đặt giá trị kiểu dữ liệu phù hợp
//
//                Cell cell11 = row.createCell(9);
//                cell11.setCellValue(x.getMoTa());
//
//                Cell cell12 = row.createCell(10);
//                cell12.setCellValue(x.getTrangThai());
            }
            workbook.write(fos);
            workbook.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
