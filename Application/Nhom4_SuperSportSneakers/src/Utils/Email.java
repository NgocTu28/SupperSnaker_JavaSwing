/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author tabah
 */
public class Email {

    private static final String username = "haotbph32696@fpt.edu.vn";
    private static final String pass = "kxig hkeg rccc uirz";
    public static String email = null;

    public static void clear() {
        Email.email = null;
    }
//    public static void setEmail(String email){
//        Email.email=email;
//    }

    public static String getEmail() {
        return Email.email;
    }

    public static String sendMailCode(String codeConfirm, String toEmail) {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, pass);
                }
            };
            Session s = Session.getInstance(p, authenticator);
            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(username));
            //String to = "tabahao01628905972@gmail.com";
            InternetAddress[] toAddresses = InternetAddress.parse(toEmail);
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            String subject = "Hệ Thống Bán Giày Sneakers gửi mã đổi mật khẩu";
            String body = "Mã đăng nhập là: " + codeConfirm;
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
            return "Gửi mail thành công";

        } catch (MessagingException e) {
            return "Gửi mail thất bại";
        }
    }

    public static String sendMailwithFile(String toEmail, String noiDung, String tieuDeNoiDung, String path) {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, pass);
                }
            };
            Session s = Session.getInstance(p, authenticator);
            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(username));
            //String to = "tabahao01628905972@gmail.com";
            InternetAddress[] toAddresses = InternetAddress.parse(toEmail);
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            String subject = "Hệ Thống Bán Giày Sneakers gửi " + tieuDeNoiDung;
            String body = noiDung;
            //Đính kèm file
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(body, "text/html; charset=utf-8");
            msg.setSubject(subject);
            msg.setText(body);
            MimeBodyPart filepart = new MimeBodyPart();
            File file = new File(path);
            FileDataSource fds = new FileDataSource(file);
            filepart.setDataHandler(new DataHandler(fds));
            filepart.setFileName(file.getName());
            // Tạo phần ghép phần content và đính file
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(contentPart);
            multipart.addBodyPart(filepart);
            msg.setContent(multipart);
            Transport.send(msg);
            return "Gửi mail thành công";

        } catch (MessagingException e) {
            return "Gửi mail thất bại";
        }
    }

    public static void sendFile(String toEmail, String noiDung, String tieuDeNoiDung, String path, String path2) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Properties p = new Properties();
                    p.put("mail.smtp.auth", "true");
                    p.put("mail.smtp.starttls.enable", "true");
                    p.put("mail.smtp.host", "smtp.gmail.com");
                    p.put("mail.smtp.port", 587);
                    Authenticator authenticator = new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, pass);
                        }
                    };
                    Session s = Session.getInstance(p, authenticator);
                    Message msg = new MimeMessage(s);
                    msg.setFrom(new InternetAddress(username));
                    //String to = "tabahao01628905972@gmail.com";
                    InternetAddress[] toAddresses = InternetAddress.parse(toEmail);
                    msg.setRecipients(Message.RecipientType.TO, toAddresses);
                    String subject = "Hệ Thống Bán Giày Sneakers gửi " + tieuDeNoiDung;
                    String body = noiDung;
                    //Đính kèm file
                    MimeBodyPart contentPart = new MimeBodyPart();
                    contentPart.setContent(body, "text/html; charset=utf-8");
                    msg.setSubject(subject);
                    msg.setText(body);
                    MimeBodyPart filepart = new MimeBodyPart();
                    File file = new File(path);
                    FileDataSource fds = new FileDataSource(file);
                    filepart.setDataHandler(new DataHandler(fds));
                    filepart.setFileName(file.getName());
                    // file 2

                    MimeBodyPart filepart2 = new MimeBodyPart();
                    File file2 = new File(path2);
                    FileDataSource fds2 = new FileDataSource(file2); // Sửa: Sử dụng file2 thay vì file
                    filepart2.setDataHandler(new DataHandler(fds2)); // Sửa: Sử dụng fds2 thay vì fds
                    filepart2.setFileName(file2.getName());
                    // Tạo phần ghép phần content và đính file
                    MimeMultipart multipart = new MimeMultipart();
                    multipart.addBodyPart(contentPart);
                    multipart.addBodyPart(filepart);
                   multipart.addBodyPart(filepart2); 
                    msg.setContent(multipart);
                    Transport.send(msg);
                    JOptionPane.showMessageDialog(null, "TT gửi email");

                } catch (MessagingException e) {
                    JOptionPane.showMessageDialog(null, "Lỗi gửi email");
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
