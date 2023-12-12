/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Repository.ChiTietDotGiamRepository;
import Repository.DBConnection;
import Repository.DotGiamGia_MRpository;
import Utils.XDate;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manhnt
 */
public class NewClass {

    ChiTietDotGiamRepository chiTietDotGiamRepository = new ChiTietDotGiamRepository();
    private DotGiamGia_MRpository dotGiamGia_MRpository = new DotGiamGia_MRpository();

    public static void main(String[] args) {
        NewClass instance = new NewClass(); // Create an instance of your class
        String date = XDate.toString(new java.util.Date(), "yyyy-MM-dd");
        instance.dotGiamGia_MRpository.updateKT(date);
        List<ChiTietDotGiamGia> listCTDGGKT = instance.chiTietDotGiamRepository.getAllCT_CTDGG_KT();
        for (ChiTietDotGiamGia ctdgg : listCTDGGKT) {
            instance.chiTietDotGiamRepository.updateTrangIDGG_SP(ctdgg.getIdCTSP());
        }

        List<ChiTietDotGiamGia> listCTDGG= instance.chiTietDotGiamRepository.getAllCT_CTDGG();

        for (ChiTietDotGiamGia ctdgg : listCTDGG) {
            instance.chiTietDotGiamRepository.update_SP(ctdgg);
        }
    }

}
