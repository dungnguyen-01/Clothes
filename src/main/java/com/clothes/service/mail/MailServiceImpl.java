package com.clothes.service.mail;

import com.clothes.repository.Account;
import com.clothes.repository.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class MailServiceImpl implements MailService{

    @Autowired
    JavaMailSender sender;
    @Override
    public void send(Mail mail) throws MessagingException {
        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText(), true);

        String from = mail.getFrom();
        if(from == null || from.trim().length() == 0) {
            from = "Web Color <nguyenvandung1025@gmail.com>";
        }
        if(!from.contains("<")) {
            from = "%s <%s>".formatted(from, from);
        }
        helper.setFrom(from);
        helper.setReplyTo(from);

        String cc = mail.getCc();
        if(cc != null && cc.trim().length() > 0) {
            helper.setCc(cc);
        }

        String bcc = mail.getBcc();
        if(bcc != null && bcc.trim().length() > 0) {
            helper.setBcc(bcc);
        }

        String files = mail.getAttachments();
        if(files != null && files.trim().length() > 0) {
            Stream.of(files.split("[,;]+")).forEach(filename -> {
                try {
                    File file = new File(filename);
                    helper.addAttachment(file.getName(), file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        sender.send(msg);
    }

    List<Mail> queue = new ArrayList<>();

    @Override
    public void addToQueue(Mail mail) {
        queue.add(mail);
    }

    @Scheduled(fixedDelay = 2000)
    public void sendingScheduler() {
        while(!queue.isEmpty()) {
            Mail mail = queue.remove(0);
            try {
                this.send(mail);
                System.out.println("Send success: ");
            } catch (Exception e) {
                System.out.println("Send failed: " );
                e.printStackTrace();
            }
        }
    }
    @Override
    public void sendShare(Share share) {
        String url = "http://localhost:8080/product/detail/"+share.getProduct().getPid();
        String text = share.getText();
        text += "<hr><a href='%s'>Xem chi tiet</a>".formatted(url) ;
        try {
            Mail mail = new Mail(share.getReceiver(), share.getSubject(), text);
            this.addToQueue(mail);
            System.out.println("thanh cong ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendActiveAccount(Account account) {
        String url = "http://localhost:8080/account/active/"+account.getId();
        String text = "<hr><a href='%s'>Click để kích hoạt tài khoản</a>".formatted(url) ;
        try {
            Mail mail = new Mail(account.getEmail(),"Welcome! kích hoạt tài khoản", text);
            this.addToQueue(mail);
            System.out.println("gửi mail active thanh cong ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendPasswordToken(String token, String email) {
        String text = "Token code: " + token;
        try {
            Mail mail = new Mail(email,"Mã token lấy lại mật khẩu (Color Shop)", text);
            this.addToQueue(mail);
            System.out.println("gửi mail active thanh cong ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
