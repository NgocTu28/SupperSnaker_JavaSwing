package Impl;

import Model.ChiTietHoaDon;
import Model.HoaDon;
import Utils.Format;
import Utils.XDate;
import com.itextpdf.barcodes.Barcode1D;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.text.pdf.PdfContentByte;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.barcodes.Barcode1D;
import com.itextpdf.barcodes.Barcode39;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.barcodes.qrcode.EncodeHintType;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonpt_ph19600
 */
public class ExportBill {

    public String docPDF(HoaDon hoaDon, List<ChiTietHoaDon> lstHdct, String path, boolean open) {
        Document document;

        String output = path + "\\" + hoaDon.getMaHoaDon() + ".pdf";
        System.out.println("PATH: " + output);
        System.out.println("HD " + hoaDon.toString());
        try {
            try {
                PdfFont fontTitle = PdfFontFactory.createFont("unicode.ttf", com.itextpdf.text.pdf.BaseFont.IDENTITY_H);
                Date date = new Date();
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                String timeNow = hour + ":" + min + ":" + second + "\t" + day + "/" + month + "/" + year;
                PdfWriter pdfWriter = new PdfWriter(output);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                pdfDocument.addNewPage();

                ImageData imageData = ImageDataFactory.create(getClass().getResource("/raven/icon/png/logo3.png"));
                Image imageLogo = new Image(imageData);
                imageLogo.setHeight(50f).setWidth(65f);
//                System.out.println("Impl.ExportBill.docPDF()  " + getClass().getResource("/src/qrbill/" + hoaDon.getQr()));
//                ImageData imageQr = ImageDataFactory.create(getClass().getResource("/qrbill/" + hoaDon.getQr()));
//                System.out.println("C:\\Users\\manhnt\\Desktop\\SuperSport-Sneakers\\Application\\Nhom4_SuperSportSneakers\\Qrbill\\" + hoaDon.getQr());
//                ImageData imageQr = ImageDataFactory.create(getClass().getResource("C:\\Users\\manhnt\\Desktop\\SuperSport-Sneakers\\Application\\Nhom4_SuperSportSneakers\\Qrbill\\" + hoaDon.getQr()));
//
//                Image imageOr = new Image(imageQr);
//                imageOr.setHeight(50f).setWidth(65f);

                document = new Document(pdfDocument);
                float columnWith[] = {80, 1000};
                Table tableHeader = new Table(columnWith).setBorder(Border.NO_BORDER).setHeight(60f).setAutoLayout();

                tableHeader.setBackgroundColor(new DeviceRgb(1, 181, 204));
                tableHeader.addCell(new Cell().add(imageLogo).setBorder(Border.NO_BORDER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE).setMarginTop(5f));

                tableHeader.addCell(new Cell().add("Hóa Đơn Shop Bán Giày Sneakers")
                        .setFontColor(new DeviceRgb(255, 255, 255)).setFontSize(16f)
                        .setBold()
                        .setMarginLeft(15f)
                        .setFont(fontTitle)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setBorder(Border.NO_BORDER));

                SimpleDateFormat sdf = new SimpleDateFormat("kk:mm dd/MM/yyyy");
                Paragraph infoCostumer = new Paragraph("Thông tin khách hàng");
                infoCostumer.setFont(fontTitle).setBold().setMarginTop(15f);
                Paragraph nameCos = null;
                Paragraph maHD = null;
                Paragraph purchaseTime = null;
                Paragraph phoneNumber = null;
                Paragraph khachLe = null;
                //    Paragraph qr = null;
                if (hoaDon.getIdKH().getCapBac() == 3) {
                    khachLe = new Paragraph("Khách lẻ");
                    khachLe.setFont(fontTitle).setBold().setFontSize(9f);
//                    qr = new Paragraph("QR hóa đơn : ");
//                    qr.setFont(fontTitle).setBold().setFontSize(9f);
                    maHD = new Paragraph("Mã HD :\t" + hoaDon.getMaHoaDon());
                } else {
                    nameCos = new Paragraph("Họ tên:\t" + hoaDon.getIdKH().getTenKhachHang());
                    nameCos.setFont(fontTitle).setFontSize(9f);
                    maHD = new Paragraph("Mã HD :\t" + hoaDon.getMaHoaDon());
                    maHD.setFont(fontTitle).setFontSize(9f);
                    purchaseTime = new Paragraph("Thời gian:\t" + XDate.toString(hoaDon.getNgayThanhToan(), "hh:mm a dd-MM-yyyy"));
                    purchaseTime.setFont(fontTitle).setFontSize(9f);

                    phoneNumber = new Paragraph("Số điện thoại:\t" + hoaDon.getIdKH().getSdt());
                    phoneNumber.setFont(fontTitle).setFontSize(9f);
//                    qr = new Paragraph("QR hóa đơn : ");
//                    qr.setFont(fontTitle).setFontSize(9f);
                }

                Paragraph nhanVienThanhToan = new Paragraph("Nhân viên thanh toán:\t" + hoaDon.getIdNV().getMaNhanVien() + "-" + hoaDon.getIdNV().getTenNhanVien());
                nhanVienThanhToan.setFont(fontTitle).setBold().setMarginTop(9f);

                document.add(tableHeader);
                document.add(infoCostumer);
                if (nameCos != null) {
                    document.add(maHD);
                    document.add(nameCos);
                    document.add(purchaseTime);
                    document.add(phoneNumber);

                } else {
                    document.add(maHD);
                    document.add(khachLe);

                }
                // document.add(qr);
                float columnWithBarcode[] = {1000f};
//                tableHeader.addCell(new Cell().add(imageOr).setBorder(Border.NO_BORDER)
//                        .setVerticalAlignment(VerticalAlignment.MIDDLE).setMarginTop(5f));
//
//                Table tableQr = new Table(columnWithBarcode)
//                        .setMarginTop(2f)
//                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
//                        .setTextAlignment(TextAlignment.LEFT)
//                        .setBorder(Border.NO_BORDER);
//
//                tableQr.addCell(new Cell().add(imageOr)
//                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
//                        .setTextAlignment(TextAlignment.LEFT)
//                        .setBorder(Border.NO_BORDER))
//                        .setMarginLeft(2f);

                //   document.add(tableQr);
                document.add(nhanVienThanhToan);

                Paragraph listProducts = new Paragraph("Sản phẩm");
                listProducts.setFont(fontTitle).setBold().setMarginTop(25f).setMarginBottom(-10);

                document.add(listProducts);

                float columnWithTableContent[] = {100, 350, 400, 250, 200, 300, 150,
                    350, 250};
                Table tableContent = new Table(columnWithTableContent)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setBorder(Border.NO_BORDER).setMarginTop(15f);

                tableContent.addCell(new Cell()
                        .add("STT").setBackgroundColor(new DeviceRgb(1, 181, 204))
                        .setFont(fontTitle).setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
                tableContent.addCell(new Cell().add("Mã sản phẩm")
                        .setBackgroundColor(new DeviceRgb(1, 181, 204))
                        .setFont(fontTitle).setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
                tableContent.addCell(new Cell().add("Tên sản phẩm")
                        .setBackgroundColor(new DeviceRgb(1, 181, 204))
                        .setFont(fontTitle).setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
                tableContent.addCell(new Cell().add("Thương hiệu")
                        .setBackgroundColor(new DeviceRgb(1, 181, 204))
                        .setFont(fontTitle).setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
                tableContent.addCell(new Cell().add("Màu")
                        .setBackgroundColor(new DeviceRgb(1, 181, 204)).setFont(fontTitle)
                        .setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
                tableContent.addCell(new Cell().add("Size")
                        .setBackgroundColor(new DeviceRgb(1, 181, 204)).setFont(fontTitle)
                        .setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
                tableContent.addCell(new Cell().add("Đơn giá ( VND )")
                        .setBackgroundColor(new DeviceRgb(1, 181, 204)).setFont(fontTitle)
                        .setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
                tableContent.addCell(new Cell().add("Giá bán ( VNĐ )")
                        .setBackgroundColor(new DeviceRgb(1, 181, 204)).setFont(fontTitle)
                        .setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
                tableContent.addCell(new Cell().add("Số lượng")
                        .setBackgroundColor(new DeviceRgb(1, 181, 204)).setFont(fontTitle)
                        .setBold().setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBorder(Border.NO_BORDER));
//                tableContent.addCell(new Cell().add("Giá bán(VNĐ)")
//                        .setFontSize(9)
//                        .setBackgroundColor(new DeviceRgb(1, 181, 204)).setFont(fontTitle)
//                        .setBold().setFontColor(Color.WHITE)
//                        .setBorder(Border.NO_BORDER));

                DecimalFormat df = new DecimalFormat("#,###");

                int totalMoney = 0;
                int count = 1;
                for (int i = 0; i < lstHdct.size(); i++) {
                    tableContent.addCell(new Cell().add(String.valueOf(i + 1)).setBorder(Border.NO_BORDER).setFontSize(9));
                    tableContent.addCell(new Cell().add(lstHdct.get(i).getIdCTSP().getMaSPCT()).setBorder(Border.NO_BORDER).setFontSize(9));
                    tableContent.addCell(new Cell().add(lstHdct.get(i).getIdCTSP().getIdSanPham().getTenSanpham()).setBorder(Border.NO_BORDER).setFontSize(9));
                    tableContent.addCell(new Cell().add(lstHdct.get(i).getIdCTSP().getIdThuongHieu().getTenThuongHieu()).setBorder(Border.NO_BORDER).setFontSize(9));
                    tableContent.addCell(new Cell().add(lstHdct.get(i).getIdCTSP().getIdMau().getTenMau()).setBorder(Border.NO_BORDER).setFontSize(9));
                    tableContent.addCell(new Cell().add(lstHdct.get(i).getIdCTSP().getIdKichThuoc().getTenSize() + "").setBorder(Border.NO_BORDER).setFontSize(9));
                    tableContent.addCell(new Cell().add(Format.format1(lstHdct.get(i).getDonGia()) + "").setBorder(Border.NO_BORDER).setFontSize(9));
                    tableContent.addCell(new Cell().add(Format.format1(lstHdct.get(i).getGiaBan()) + "").setBorder(Border.NO_BORDER).setFontSize(9));
                    tableContent.addCell(new Cell().add(lstHdct.get(i).getSoLuong() + "").setBorder(Border.NO_BORDER).setFontSize(9));

                }

                document.add(tableContent);
                float coulumnWithFotter[] = {100, 300, 600, 350, 350};
                Table tableFotter = new Table(coulumnWithFotter)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setBorder(Border.NO_BORDER);
                tableFotter.addCell(new Cell().setBackgroundColor(new DeviceRgb(1, 181, 204)).setBorder(Border.NO_BORDER));
                tableFotter.addCell(new Cell().setBackgroundColor(new DeviceRgb(1, 181, 204)).setBorder(Border.NO_BORDER));
                tableFotter.addCell(new Cell().setBackgroundColor(new DeviceRgb(1, 181, 204)).setBorder(Border.NO_BORDER));
                tableFotter.addCell(new Cell().add(
                        "Tổng tiền:" + "\n"
                        + "Giảm cấp bậc :\n "
                        + "Phiếu Giảm : \n"
                        + "Điểm đổi :\n "
                        + "Tiền sau giảm : \n"
                        + "Tiền chuyển khoản : \n"
                        + "Tiền mặt : "//(hoaDon.getPhuongThucTT() == 0 ? "Tiền mặt" : hoaDon.getPhuongThucTT() == 1 ? "\nChuyển khoản:" : "Kết hợp")
                        + "\nTổng tiền khách đưa:"
                        + "\nTrả lại:")
                        .setFont(fontTitle)
                        .setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBold()
                        .setBackgroundColor(new DeviceRgb(1, 181, 204))
                        .setBorder(Border.NO_BORDER)
                );
                BigDecimal tongTienKhachDua = new BigDecimal(BigInteger.ZERO);
                if (hoaDon.getPhuongThucTT() == 0) {
                    tongTienKhachDua = hoaDon.getTienKhDua();
                } else if (hoaDon.getPhuongThucTT() == 1) {
                    tongTienKhachDua = hoaDon.getTienKhChuyenKhoan();
                } else {
                    tongTienKhachDua = hoaDon.getTienKhDua().add(hoaDon.getTienKhChuyenKhoan());
                }
                int capBac = 0;
                if(hoaDon.getCapBac()==0){
                    capBac =0 ;
                }else if(hoaDon.getCapBac()==1){
                    capBac =3 ;
                }else if(hoaDon.getCapBac()==2){
                    capBac =8 ;
                }else{
                    capBac = 0;
                }
                tableFotter.addCell(new Cell().add(
                        "" + df.format(hoaDon.getTongTienSP()) + " VNĐ"
                        + "\n" + capBac + " % "
                        + "\n" + (hoaDon.getIdPGG() == null ? "" : (hoaDon.getIdPGG().getLoaiPhieu() == 0 ? hoaDon.getPhanTramGG() + " % " : df.format(hoaDon.getTienPhieuGiam()) + " VNĐ"))
                        + "\n" + (df.format(hoaDon.getDiemDoi().multiply(new BigDecimal("5000")))) + " VNĐ"
                        + "\n" + Format.format(hoaDon.getThanhTien())
                        + "\n" + (df.format(hoaDon.getTienKhChuyenKhoan()) + " VNĐ")
                        + "\n" + (df.format(hoaDon.getTienKhDua()) + " VNĐ")
                        + "\n" + Utils.Format.format(tongTienKhachDua)
                        + "\n" + Utils.Format.format(hoaDon.getTienThua())
                )
                        .setFont(fontTitle)
                        .setFontColor(Color.WHITE)
                        .setFontSize(9)
                        .setBold()
                        .setBackgroundColor(new DeviceRgb(1, 181, 204))
                        .setBorder(Border.NO_BORDER)
                );

                document.add(tableFotter);

                document.close();
                System.out.println("Create a PDF file sussecess");
                if (Desktop.isDesktopSupported() && open) {
                    Desktop desktop = Desktop.getDesktop();
                    File file = new File(output);
                    if (file.exists()) {
                        desktop.open(file);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                e.getMessage();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return null;
        }
        return output;
    }

//    private <BarcodeEAN extends Barcode1D> Image createBarCode(PdfDocument pdfDocument, String code, Class<BarcodeEAN> barcodeClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
//        BarcodeEAN barCodeObject = barcodeClass.getConstructor(PdfDocument.class).newInstance(pdfDocument);
//        barCodeObject.setCode(code);
//        return new Image(barCodeObject.createFormXObject(pdfDocument));
//    }
}
