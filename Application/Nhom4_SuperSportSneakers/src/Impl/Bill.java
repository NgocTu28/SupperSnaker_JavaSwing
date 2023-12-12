/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Impl;

import Model.ChiTietHoaDon;
import Model.HoaDon;
import Repository.ChiTietHoaDon_RepositoryM;
import Repository.HoaDon_MRepositoryM;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author manhnt
 */
public class Bill {

    static HoaDon_MRepositoryM hoaDon_MRepositoryM = new HoaDon_MRepositoryM();

    public static Boolean exportPdf(String path, String ma) {
        HoaDon hoaDonDone;
        hoaDonDone = hoaDon_MRepositoryM.pdfHD(ma);
        System.out.println(hoaDonDone.toString());
        List<ChiTietHoaDon> lst = hoaDon_MRepositoryM.pdfHDCT(ma);
        ExportBill exportBill = new ExportBill();
        exportBill.docPDF(hoaDonDone, lst, path, true) ;
            return true;
    }

    public static void exportPdf2(String path, String ma) {
        new Thread() {
            @Override
            public void run() {
                HoaDon hoaDonDone;
                hoaDonDone = hoaDon_MRepositoryM.pdfHD(ma);
                System.out.println(hoaDonDone.toString());
                List<ChiTietHoaDon> lst = hoaDon_MRepositoryM.pdfHDCT(ma);
                ExportBill exportBill = new ExportBill();
                if (exportBill.docPDF(hoaDonDone, lst, path, true) != null) {
                    return;
                } else {
                    return;
                }
            }

        }.start();
    }
}
