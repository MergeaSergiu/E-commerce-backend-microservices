package com.mycompany.app.service;

import com.mycompany.app.model.OrderMessageDTO;
import jakarta.mail.MessagingException;

public interface NotificationService {

    void sendEmailNotification(String message) throws MessagingException;

    void sendEmailNotificationOrder(OrderMessageDTO orderMessageDTO) throws MessagingException;

}
