/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static String USER = "sa";
    public static String PASS = "123";
    public static String URL = "jdbc:sqlserver://localhost;databaseName= SuperSportSneakers ;user=sa ;password=123; encrypt=true;trustServerCertificate=true;";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Connection getConnect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public static void main(String[] args) {
        Connection con1 = getConnect();
        if (con1 != null) {
            System.out.println("DONE");
        } else {
            System.out.println("FAIL");
        }
    }

}
