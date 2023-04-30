package com.price_tracker.server.service.email;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.net.URL;

@Service
public class EmailService {
  private final JavaMailSender mailSender;

  @Autowired
  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendEmail(String to, String subject) {
    MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom("pricetracker.app.notifications@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText("some text <img src='cid:logo'>", true);
        URL imageUrl = new URL("https://cdn.dsmcdn.com/ty599/product/media/images/20221113/16/213379743/89639851/7/7_org_zoom.jpg");
        UrlResource imageResource = new UrlResource(imageUrl);
        message.addInline("product_image", imageResource);
      }
    };
    mailSender.send(messagePreparator);
  }
}