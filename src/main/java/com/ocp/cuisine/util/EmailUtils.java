package com.ocp.cuisine.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMessage(String to, String subject, String text, List<String> list){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ahihihaine@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if (!list.isEmpty()){
            message.setCc(getCCArray(list));
        }
        javaMailSender.send(message);
    }

    private String[] getCCArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for (int i = 0; i < ccList.size(); i++){
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    public void forgotMail(String to, String subject, String passWord) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("ahihihaine@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMessage = "<p><b>Your Login details for Cuisine Ocean Park</b><br><b>Email: </b>" + to + "<br><b>Password: </b>" + passWord + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMessage, "text/html");
        javaMailSender.send(message);
    }
}

