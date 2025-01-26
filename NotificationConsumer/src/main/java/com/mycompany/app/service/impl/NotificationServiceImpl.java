package com.mycompany.app.service.impl;

import com.mycompany.app.config.RabbitMQConfig;
import com.mycompany.app.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    public NotificationServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void sendEmailNotification(String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("mergeasergiu@gmail.com");
        mailMessage.setTo("shopDeposit@example.com");  // Set the recipient email address
        mailMessage.setSubject("Product added");  // Set the email subject
        mailMessage.setSentDate(new Date());
        mailMessage.setText("Hello, Sergiu Mergea\n" + message);  // Set the email content

        emailSender.send(mailMessage);

        logger.info("Email sent to recipient@example.com with message: {}", message);
    }
}
