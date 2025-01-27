package com.mycompany.app.service;

import jakarta.mail.MessagingException;

public interface NotificationService {

    void sendEmailNotification(String message) throws MessagingException;

}
