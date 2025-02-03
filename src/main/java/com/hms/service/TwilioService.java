package com.hms.service;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public String sendSms(String to, String otp) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(to) , // Recipient's phone number
                new com.twilio.type.PhoneNumber("+15407798570"), // Twilio phone number
                otp
        ).create();

        return message.getSid(); // Returns Message SID for tracking
    }
}
