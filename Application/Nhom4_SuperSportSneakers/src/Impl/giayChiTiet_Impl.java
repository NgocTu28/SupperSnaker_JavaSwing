/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Impl;

import Model.KichThuoc;
import Model.MauSac;
import Model.SanPham;
import Model.SanPhamChiTiet;
import Model.ThuongHieu;
import Repository.DBConnection;
import Repository.KichThuoc_Repository;
import Repository.MauSac_Reponsitory;
import Repository.SanPhamCT_Repository;
import Repository.SanPham_Repository;
import Repository.ThuongHieu_Repository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.ERROR;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import static org.apache.poi.ss.usermodel.CellType._NONE;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author vutu8
 */
public class giayChiTiet_Impl {

    SanPhamCT_Repository sanPhamChiTiet_Repo = new SanPhamCT_Repository();
    ThuongHieu_Repository thuongHieu_Repository = new ThuongHieu_Repository();
    KichThuoc_Repository kichThuoc_Repository = new KichThuoc_Repository();
    MauSac_Reponsitory mauSac_Reponsitory = new MauSac_Reponsitory();
    SanPham_Repository sanPham_Repository = new SanPham_Repository();
    public static final int COLUMN_INDEX_IDSPCT = 0;
    public static final int COLUMN_INDEX_MA_GIAY_CT = 1;
    public static final int COLUMN_INDEX_TEN_GIAY = 2;
    public static final int COLUMN_INDEX_THUONG_HIEU = 3;
    public static final int COLUMN_INDEX_KICH_CO = 4;
    public static final int COLUMN_INDEX_MAU_SAC = 5;
    public static final int COLUMN_INDEX_SO_LUONG = 6;
    public static final int COLUMN_INDEX_GIA_BAN = 7;
    public static final int COLUMN_INDEX_GIA_NIEM_YET = 8;
    public static final int COLUMN_INDEX_MO_TA = 9;
    public static final int COLUMN_INDEX_TRANG_THAI = 10;

    public String exportQr(String pathFolder, String ma) {
        SanPhamChiTiet spct = (SanPhamChiTiet) sanPhamChiTiet_Repo.getProductByMa(ma);
        if (spct == null) {
            return "Không tìm thấy điện thoại";
        }
        DecimalFormat moneyFormat = new DecimalFormat("#,###");
//        String data = "Mã điện thoại: " + ctdt.getMaDienThoai()
//                + "\nIMEI: " + ctdt.getImei()
//                + "\nTên điện thoại: " + ctdt.getDienThoai()
//                + "\nHãng: " + ctdt.getHang()
//                + "\nMàu sắc: " + ctdt.getMauSac()
//                + "\nTình trạng: " + (ctdt.getTinhTrang() == 100 ? "Mới" : "Cũ - " + ctdt.getTinhTrang() + "%")
//                + "\nĐơn giá: " + moneyFormat.format(ctdt.getDonGia()) + "VNĐ"
//                + "\nRam: " + ctdt.getRam()
//                + "\nBộ nhớ: " + ctdt.getBoNho()
//                + "\nThời gian bảo hành: " + ctdt.getThoiGianBaoHanh() + " tháng"
//                + "\nTrạng thái: " + (ctdt.getTrangThai() == 0 ? "Đang bán" : ctdt.getTrangThai() == 1 ? "Đã bán" : ctdt.getTrangThai() == 2 ? "Sản phẩm lỗi" : "")
//                + (ctdt.getMoTa() == null ? "" : "\nMô tả: " + ctdt.getMoTa());

        String data = "Mã Giày Chi Tiết:" + spct.getMaSPCT()
                + "\n Tên Giày:" + spct.getIdSanPham().getTenSanpham()
                + "\n Tên Thương Hiệu:" + spct.getIdThuongHieu().getTenThuongHieu()
                + "\n Kích Thước:" + spct.getIdKichThuoc().getTenSize()
                + "\n Màu Sắc" + spct.getIdMau().getTenMau()
                + "\n Số Lượng Tồn" + spct.getSoLuong()
                + "\n Giá Bán" + spct.getGiaBan()
                + "\n Giá Niêm Yết" + spct.getGiaNiemYet()
                + (spct.getMoTa() == null ? "" : "\n Mô Tả" + spct.getMoTa())
                + "\n Trạng Thái" + spct.getTrangThai();
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200, hints);

            // Write to file image
            Path path = FileSystems.getDefault().getPath(pathFolder + "\\" + spct.getMaSPCT() + "-" + spct.getIdSanPham().getTenSanpham() + ".png");
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
        } catch (Exception ex) {
            Logger.getLogger(giayChiTiet_Impl.class.getName()).log(Level.SEVERE, null, ex);
            return "Lỗi hệ thống. Không thể export";
        }
        return "Tải thành công";
    }

    public boolean export(File file) {
        List<SanPhamChiTiet> lst = sanPhamChiTiet_Repo.getToAll();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách chi tiết giày");
            int rowNum = 0;
            Row firstRow = sheet.createRow(rowNum++);

            Cell idDtct = firstRow.createCell(0);
            idDtct.setCellValue("ID SPCT");

            Cell maSPCTTitle = firstRow.createCell(1);
            maSPCTTitle.setCellValue("Mã Giày Chi Tiết");

            Cell tengiayTitle = firstRow.createCell(2);
            tengiayTitle.setCellValue("Tên Giày");

            Cell TenThuongHieuTitle = firstRow.createCell(3);
            TenThuongHieuTitle.setCellValue("Thương Hiệu");

            Cell TenSizeTitle = firstRow.createCell(4);
            TenSizeTitle.setCellValue("Kích Cỡ");

            Cell TenMauTitle = firstRow.createCell(5);
            TenMauTitle.setCellValue("Màu Sắc");

            Cell SoLuongTonTitle = firstRow.createCell(6);
            SoLuongTonTitle.setCellValue("Số Lượng");

            Cell GiaBanTitle = firstRow.createCell(7);
            GiaBanTitle.setCellValue("Giá Bán");

            Cell GiaNiemYetTitle = firstRow.createCell(8);
            GiaNiemYetTitle.setCellValue("Giá Niêm Yết");

            Cell MoTaTitle = firstRow.createCell(9);
            MoTaTitle.setCellValue("Mô Tả");

            Cell TrangThaiTitle = firstRow.createCell(10);
            TrangThaiTitle.setCellValue("Trạng Thái");

            for (SanPhamChiTiet x : lst) {
                Row row = sheet.createRow(rowNum++);
//    CTSP.ID,CTSP.MaCTSP,SP.TenSP,TH.TenThuongHieu,S.TenSize,M.TenMau,CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai
                Cell cell2 = row.createCell(0);
                cell2.setCellValue(x.getIdSPCT());

                Cell cell3 = row.createCell(1);
                cell3.setCellValue(x.getMaSPCT());

                Cell cell4 = row.createCell(2);
                cell4.setCellValue(x.getIdSanPham().getTenSanpham());

                Cell cell5 = row.createCell(3);
                cell5.setCellValue(x.getIdThuongHieu().getTenThuongHieu());

                Cell cell6 = row.createCell(4);
                cell6.setCellValue(x.getIdKichThuoc().getTenSize());

                Cell cell7 = row.createCell(5);
                cell7.setCellValue(x.getIdMau().getTenMau());

                Cell cell8 = row.createCell(6);
                cell8.setCellValue(x.getSoLuong());

                Cell cell9 = row.createCell(7);
                cell9.setCellValue(x.getGiaBan().toString()); // Đặt giá trị kiểu dữ liệu phù hợp

                Cell cell10 = row.createCell(8);
                cell10.setCellValue(x.getGiaNiemYet().toString()); // Đặt giá trị kiểu dữ liệu phù hợp

                Cell cell11 = row.createCell(9);
                cell11.setCellValue(x.getMoTa());

                Cell cell12 = row.createCell(10);
                cell12.setCellValue(x.getTrangThai());
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

    public boolean exportMau(File file) {
        List<SanPhamChiTiet> lst = sanPhamChiTiet_Repo.getToAll();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách chi tiết điện thoại");
            int rowNum = 0;
            Row firstRow = sheet.createRow(rowNum++);

            Cell idDtct = firstRow.createCell(0);
            idDtct.setCellValue("ID SPCT");

            Cell maSPCTTitle = firstRow.createCell(1);
            maSPCTTitle.setCellValue("Mã Giày Chi Tiết");

            Cell tengiayTitle = firstRow.createCell(2);
            tengiayTitle.setCellValue("Tên Giày");

            Cell TenThuongHieuTitle = firstRow.createCell(3);
            TenThuongHieuTitle.setCellValue("Thương Hiệu");

            Cell TenSizeTitle = firstRow.createCell(4);
            TenSizeTitle.setCellValue("Kích Cỡ");

            Cell TenMauTitle = firstRow.createCell(5);
            TenMauTitle.setCellValue("Màu Sắc");

            Cell SoLuongTonTitle = firstRow.createCell(6);
            SoLuongTonTitle.setCellValue("Số Lượng");

            Cell GiaBanTitle = firstRow.createCell(7);
            GiaBanTitle.setCellValue("Giá Bán");

            Cell GiaNiemYetTitle = firstRow.createCell(8);
            GiaNiemYetTitle.setCellValue("Giá Niêm Yết");

            Cell MoTaTitle = firstRow.createCell(9);
            MoTaTitle.setCellValue("Mô Tả");

            Cell TrangThaiTitle = firstRow.createCell(10);
            TrangThaiTitle.setCellValue("Trạng Thái");

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

    private static Object getCellValue(Cell cell) {
//        CellType cellType = cell.getCellTypeEnum();
        CellType cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }

    public String importFile(File file) {
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        String ketQua;

        try {
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                if (nextRow.getRowNum() == 0 || nextRow.getRowNum() == 1) {
                    continue;
                }

                Iterator<Cell> cellIterator = nextRow.cellIterator();

                SanPhamChiTiet spct = new SanPhamChiTiet();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    Object cellValue = getCellValue(cell);

                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case COLUMN_INDEX_IDSPCT:
                            if (cell.getCellType() == CellType.BLANK) {
                                ketQua = "IDSPCT không được để trống - dòng: " + (cell.getRowIndex() + 1);
                                return ketQua;
                            }
                            try {
                                Long idSPCT = ((Double) getCellValue(cell)).longValue();
                                if (idSPCT <= 0) {
                                    ketQua = "IDSPCT phải là số nguyên dương - dòng: " + (cell.getRowIndex() + 1);
                                    return ketQua;
                                }
                                spct.setIdSPCT(idSPCT);
                            } catch (Exception e) {
                                e.printStackTrace();
                                ketQua = "IDSPCT phải là số - dòng: " + (cell.getRowIndex() + 1);
                                return ketQua;
                            }
                            break;
                        case COLUMN_INDEX_MA_GIAY_CT:
                            if (cell.getCellType() == CellType.BLANK) {
                                ketQua = "Mã giày chi tiết không được để trống - dòng: " + (cell.getRowIndex() + 1);
                                return ketQua;
                            }
                            spct.setMaSPCT((String) getCellValue(cell));
                            break;
                        case COLUMN_INDEX_TEN_GIAY:
                            String sanPhamStr = (String) getCellValue(cell);
                            if (!sanPhamStr.isEmpty()) {
                                SanPham sp = sanPham_Repository.findSanPhamByName(sanPhamStr);
                                if (sp == null) {
                                    SanPham newSanPham = new SanPham();
                                    newSanPham.setTenSanpham(sanPhamStr);
                                    sanPham_Repository.addSanPham(newSanPham);
                                    sp = sanPham_Repository.findSanPhamByName(sanPhamStr);
                                }
                                spct.setIdSanPham(sp);
                            }
                        case COLUMN_INDEX_THUONG_HIEU:
                            // Xử lý cột THUONG_HIEU
                            String thuongHieuStr = (String) getCellValue(cell);
                            if (!thuongHieuStr.isEmpty()) {
                                ThuongHieu thuongHieu = thuongHieu_Repository.findThuongHieuByName(thuongHieuStr);
                                if (thuongHieu == null) {
                                    ThuongHieu newThuongHieu = new ThuongHieu();
                                    newThuongHieu.setTenThuongHieu(thuongHieuStr);
                                    thuongHieu_Repository.addThuongHieu(newThuongHieu);
                                    thuongHieu = thuongHieu_Repository.findThuongHieuByName(thuongHieuStr);
                                }
                                spct.setIdThuongHieu(thuongHieu);
                            }
                            break;
                        case COLUMN_INDEX_KICH_CO:
                            String kichCoStr = String.valueOf(getCellValue(cell)); // Assuming getCellValue returns a Double
                            if (!kichCoStr.isEmpty()) {
                                try {
                                    Float kichCoFloat = Float.parseFloat(kichCoStr);

                                    KichThuoc kichCo = kichThuoc_Repository.findKichCoByName(kichCoFloat);

                                    if (kichCo == null) {
                                        KichThuoc newKichCo = new KichThuoc();
                                        newKichCo.setTenSize(kichCoFloat);

                                        kichThuoc_Repository.addKichThuoc(newKichCo);

                                        kichCo = kichThuoc_Repository.findKichCoByName(kichCoFloat);
                                    }

                                    spct.setIdKichThuoc(kichCo);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace(); // Handle the case where kichCoStr is not a valid float
                                }
                            }

                            break;
                        case COLUMN_INDEX_MAU_SAC:
                            // Xử lý cột MAU_SAC
                            String mauSacStr = (String) getCellValue(cell);
                            if (!mauSacStr.isEmpty()) {
                                MauSac mauSac = mauSac_Reponsitory.findMauSacByName(mauSacStr);

                                if (mauSac == null) {
                                    // If the repository method expects a String parameter, pass mauSacStr directly
                                    MauSac newMauSac = new MauSac();
                                    newMauSac.setTenMau(mauSacStr);
                                    mauSac_Reponsitory.addMauSac(newMauSac);

                                    // Retrieve the newly added MauSac
                                    mauSac = mauSac_Reponsitory.findMauSacByName(mauSacStr);
                                }

                                // Check if mauSac is still null and handle accordingly
                                if (mauSac != null) {
                                    spct.setIdMau(mauSac);
                                } else {
                                    // Handle the case where mauSac is still null
                                    // You might want to log an error or take appropriate action
                                    System.err.println("Failed to retrieve or add MauSac for: " + mauSacStr);
                                }
                            }

                            break;
                        case COLUMN_INDEX_SO_LUONG:
                            if (cell.getCellType() == CellType.BLANK) {
                                ketQua = "Số lượng không được để trống - dòng: " + (cell.getRowIndex() + 1);
                                return ketQua;
                            }
                            try {
                                Integer soLuong = ((Double) getCellValue(cell)).intValue();
                                if (soLuong <= 0) {
                                    ketQua = "Số lượng phải là số nguyên không âm - dòng: " + (cell.getRowIndex() + 1);
                                    return ketQua;
                                }
                                spct.setSoLuong(soLuong);
                            } catch (Exception e) {
                                e.printStackTrace();
                                ketQua = "Số lượng phải là số nguyên - dòng: " + (cell.getRowIndex() + 1);
                                return ketQua;
                            }
                            break;

                        /////////// Mạnh sua Float
                        case COLUMN_INDEX_GIA_BAN:
                            if (cellValue instanceof String) {
                                try {
                                    BigDecimal giaBan = BigDecimal.valueOf(Double.parseDouble((String) cellValue));
                                    if (giaBan.compareTo(BigDecimal.ZERO) < 0) {
                                        ketQua = "Giá bán không được nhỏ hơn 0 - dòng: " + (cell.getRowIndex() + 1);
                                        return ketQua;
                                    }
                                    spct.setGiaBan(giaBan);
                                } catch (NumberFormatException e) {
                                    ketQua = "Giá bán phải là số - dòng: " + (cell.getRowIndex() + 1);
                                    e.printStackTrace();
                                    return ketQua;
                                }
                            } else if (cellValue instanceof Double) {
                                BigDecimal giaBan = BigDecimal.valueOf((Double) cellValue);
                                if (giaBan.compareTo(BigDecimal.ZERO) < 0) {
                                    ketQua = "Giá bán không được nhỏ hơn 0 - dòng: " + (cell.getRowIndex() + 1);
                                    return ketQua;
                                }
                                spct.setGiaBan(giaBan);
                            } else {
                                ketQua = "Giá bán không hợp lệ - dòng: " + (cell.getRowIndex() + 1);
                                return ketQua;
                            }

                            break;
                        case COLUMN_INDEX_GIA_NIEM_YET:
                            // Xử lý cột GIA_NIEM_YET
                            if (cell.getCellType() == CellType.BLANK) {
                                ketQua = "Giá niêm yết không được để trống - dòng: " + (cell.getRowIndex() + 1);
                                return ketQua;
                            }

                            try {
                                BigDecimal giaNiemYet;
                                if (cellValue instanceof String) {
                                    giaNiemYet = BigDecimal.valueOf(Double.parseDouble((String) cellValue));
                                } else if (cellValue instanceof Double) {
                                    giaNiemYet = BigDecimal.valueOf((Double) cellValue);
                                } else {
                                    ketQua = "Giá niêm yết không hợp lệ - dòng: " + (cell.getRowIndex() + 1);
                                    return ketQua;
                                }

                                if (giaNiemYet.compareTo(BigDecimal.ZERO) < 0) {
                                    ketQua = "Giá niêm yết không được nhỏ hơn 0 - dòng: " + (cell.getRowIndex() + 1);
                                    return ketQua;
                                }

                                spct.setGiaNiemYet(giaNiemYet);
                            } catch (NumberFormatException e) {
                                ketQua = "Giá niêm yết phải là số - dòng: " + (cell.getRowIndex() + 1);
                                e.printStackTrace();
                                return ketQua;
                            }

                            break;
                        case COLUMN_INDEX_MO_TA:
                            spct.setMoTa((String) getCellValue(cell));
                            break;
                        case COLUMN_INDEX_TRANG_THAI:
                            // Xử lý cột TRANG_THAI
                            String trangThaiStr = getCellValue(cell).toString(); // Convert to String
                            if (!trangThaiStr.isEmpty()) {
                                Integer trangThai = -1;
                                if (trangThaiStr.equals("Đang bán")) {
                                    trangThai = 0;
                                } else if (trangThaiStr.equals("Đã bán")) {
                                    trangThai = 1;
                                } else {
                                    trangThai = 2;
                                }
                                spct.setTrangThai(trangThai); // Set the parsed Integer value
                            }

                            break;
                        default:
                            break;
                    }
                }
                listSPCT.add(spct);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ketQua = "Lỗi đọc file: " + e.getMessage();
            return ketQua;
        }
        sanPhamChiTiet_Repo.insertSPCT(listSPCT);
        // TODO: Lưu danh sách sản phẩm chi tiết vào cơ sở dữ liệu
        ketQua = "Import file thành công";
        return ketQua;
    }

    
    Connection connect = DBConnection.getConnect();

    public List<SanPhamChiTiet> getAllResponse() {

        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = "select  CTSP.ID,CTSP.MaCTSP,SP.TenSP,TH.TenThuongHieu,S.TenSize,M.TenMau,CTSP.SoLuongTon, CTSP.GiaBan, CTSP.GiaNiemYet, CTSP.MoTa, CTSP.TrangThai , CTSP.ID as ID from CHI_TIET_SAN_PHAM as CTSP\n"
                + "join MAU as M on M.ID = CTSP.IdMau\n"
                + "join SIZE as S on S.ID = CTSP.IdSize\n"
                + "join THUONGHIEU as TH on TH.ID = CTSP.IdThuongHieu\n"
                + "join SANPHAM as SP on SP.ID = CTSP.IdSP\n";

        try {
            PreparedStatement pstm = connect.prepareCall(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString("TenSP"));
                MauSac mauSac = new MauSac(rs.getString("TenMau"));
                ThuongHieu thuongHieu = new ThuongHieu(rs.getString("TenThuongHieu"));
                KichThuoc kichThuoc = new KichThuoc(rs.getFloat("TenSize"));

                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(rs.getLong("ID"), rs.getString("MaCTSP"), rs.getInt("SoLuongTon"), rs.getBigDecimal("GiaBan"), rs.getBigDecimal("GiaNiemYet"), rs.getInt("TrangThai"), rs.getString("MoTa"), mauSac, kichThuoc, thuongHieu, sanPham);
                list.add(sanPhamChiTiet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

}
