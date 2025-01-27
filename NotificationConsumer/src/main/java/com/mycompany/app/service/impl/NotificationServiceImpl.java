package com.mycompany.app.service.impl;

import com.mycompany.app.config.RabbitMQConfig;
import com.mycompany.app.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final TemplateEngine templateEngine;

    public NotificationServiceImpl(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void sendEmailNotification(String message) throws MessagingException {

        Context context = new Context();
        String textMessage = "Hello, Sergiu - Octavian Mergea,\n\n" + message;
        context.setVariable("message", textMessage);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = templateEngine.process("notification_product", context);
        mimeMessageHelper.setSubject("Product added");
        mimeMessageHelper.setTo("shopDeposit@example.com");
        mimeMessageHelper.setText(htmlContent, true);
        mimeMessageHelper.setFrom("mergeasergiu@gmail.com");
        mimeMessageHelper.setSentDate(new Date());
        emailSender.send(mimeMessage);

        logger.info("Email sent to shopDeposit@example.com with message: {}", message);
    }
}
