/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author manhnt
 */
public class Format {

    public static String format(BigDecimal number) {
        DecimalFormat currencyFormatter = new DecimalFormat("###,###,###,##0");
        return currencyFormatter.format(number) + " VNĐ ";
    }
    
     public static String format1(BigDecimal number) {
        DecimalFormat currencyFormatter = new DecimalFormat("###,###,###,##0");
        return currencyFormatter.format(number) ;
    }
}
