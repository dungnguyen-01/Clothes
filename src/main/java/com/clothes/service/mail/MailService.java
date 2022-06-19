package com.clothes.service.mail;

import com.clothes.repository.Account;
import com.clothes.repository.Share;

import javax.mail.MessagingException;

public interface MailService {

    void  send(Mail mail) throws MessagingException;

    void addToQueue(Mail mail);

    void sendShare(Share share);

    void sendActiveAccount(Account account);

    void sendPasswordToken(String token, String email);
}
