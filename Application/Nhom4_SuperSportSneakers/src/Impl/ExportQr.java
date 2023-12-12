/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Impl;

import Model.HoaDon;
import Model.SanPhamChiTiet;
import Repository.HoaDon_MRepositoryM;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manhnt
 */
public class ExportQr {

    public static Boolean exportQrHD(String pathFolder, String ma) {
        String data = ma;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300, hints);

            // Write to file image
            Path path = FileSystems.getDefault().getPath(pathFolder + "\\" + ma + ".png");
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
        } catch (Exception ex) {
            Logger.getLogger(giayChiTiet_Impl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
