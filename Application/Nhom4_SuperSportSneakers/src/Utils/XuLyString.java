/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Admin
 */
public class XuLyString {
    public static String formatTien(float giaTri) 
    {
        //long giaTri = this.getGiaTri();
        //100000 => 100.000
        int giaTriInt= Math.round(giaTri);
        String giaTriStr = String.valueOf(giaTriInt);
        ArrayList<String> thousands = new ArrayList<>();
        while (giaTriStr.length() > 3) {
            String thousand = giaTriStr.substring(giaTriStr.length() - 3);
            giaTriStr = giaTriStr.substring(0, giaTriStr.length() - 3);
            thousands.add(thousand);
        }
        if (giaTriStr.length() > 0) {
            thousands.add(giaTriStr);
        }
        Collections.reverse(thousands);
        String giaTriFormated = String.join(".", thousands);
        return giaTriFormated;
    }
    
    public static String formatTien(BigDecimal giaTri) 
    {
        //long giaTri = this.getGiaTri();
        //100000 => 100.000
        String giaTriStr = String.valueOf(giaTri);
        giaTriStr = giaTriStr.replace(".0000", "");
        ArrayList<String> thousands = new ArrayList<>();
        while (giaTriStr.length() > 3) {
            String thousand = giaTriStr.substring(giaTriStr.length() - 3);
            giaTriStr = giaTriStr.substring(0, giaTriStr.length() - 3);
            thousands.add(thousand);
        }
        if (giaTriStr.length() > 0) {
            thousands.add(giaTriStr);
        }
        Collections.reverse(thousands);
        String giaTriFormated = String.join(".", thousands);
        return giaTriFormated;
    }
    
    public static String formatTienToNumBer(String tien){
        tien = tien.replace(".", "");
        return tien;
    }
}
