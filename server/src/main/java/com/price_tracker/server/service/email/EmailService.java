package com.price_tracker.server.service.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.price_tracker.server.entity.Product;

import java.net.URL;

@Service
public class EmailService {
  private final JavaMailSender mailSender;

  @Autowired
  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendEmail(String to, Product product, Double currentPrice) {
    MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom("pricetracker.app.notifications@gmail.com");
        message.setTo(to);
        message.setSubject("Preisalarm: " + product.getName() + " jetzt für " + currentPrice + " ₺");
        String htmlContent = "<html><body><p>Sehr geehrte/r Nutzer/in,</p><p>wir möchten Sie darüber informieren, dass der Preis für " + product.getName() + " auf " + currentPrice + " ₺ gesunken ist. Dies bedeutet, dass die von Ihnen festgelegten Bedingungen für Ihren Preisverfolger erfüllt wurden und Sie das Produkt jetzt zu einem niedrigeren Preis kaufen können.</p><p style='text-align:center;'><a href='" + product.getUrl() + "'><img src='cid:product_image' style='max-width:300px;display:block;margin:auto;'></a></p><p style='text-align:center;'><a href='" + product.getUrl() + "' style='background-color:#4CAF50;border:none;color:white;padding:15px 32px;text-align:center;text-decoration:none;display:inline-block;font-size:16px;margin-bottom:20px;'>" + "Zum Produkt" + "</a></p><p>Vielen Dank, dass Sie Price Tracker nutzen. Wir wünschen Ihnen viel Spaß beim Einkaufen!</p><p>Price Tracker Team</p></body></html>";
        message.setText(htmlContent, true);
        UrlResource imageResource = new UrlResource(new URL(product.getImage()));
        message.addInline("product_image", imageResource);
      }
    };
    mailSender.send(messagePreparator);
  }
}