package com.scaler.Notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.Notification.configs.EmailUtil;
import com.scaler.Notification.dtos.NotificationDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class EmailService {

    private ObjectMapper objectMapper;

    public EmailService(ObjectMapper obj)
    {
        this.objectMapper=obj;
    }

    @KafkaListener(topics = "sendEmail",groupId="notificationByEmail")
    public void handleEmailEvent(String message)
    {
        NotificationDTO dto=null;
        try {
            dto=objectMapper.readValue(message, NotificationDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.print("received");

        //sending a mail

        final String fromEmail = "kamalsana5041@gmail.com"; //requires valid gmail id
        final String password = "ghyy mrkb mxrv tzgr"; // correct password for gmail id
        final String toEmail = dto.getTo(); // can be any email id

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail,dto.getSubject(), dto.getBody());

    }

}
