package com.price_tracker.server.controller;

import com.price_tracker.server.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailTest {
  private final EmailService emailService;

  @Autowired
  public EmailTest(EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping("/send/{to}")
  public ResponseEntity sendMail(@PathVariable String to) {
    emailService.sendEmail(to, "test mail");
    return ResponseEntity.status(HttpStatus.GONE).build();
  }
}
