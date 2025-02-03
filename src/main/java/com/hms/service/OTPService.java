package com.hms.service;

import com.hms.util.OTPUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

public class OTPService {
    @Autowired
    private Service service;
    @Autowired
    private OTPUtil otpUtil;
    @Autowired
    private TwilioService twilioService;
    private final Map<String,OTPData> otpStorage = new HashMap<>();
    private static final long OTP_EXPIRY_TIME = 5*60*1000;

//    public static final String ACCOUNT_SID = "your_twilio_account_sid";
//    public static final String AUTH_TOKEN = "your_twilio_auth_token";
//    public static final String FROM_NUMBER = "your_twilio_phone_number";

//    static{
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//    }
    public String generateAndSendOTP(String mobileNumber){
        String otp = otpUtil.generateOtp();
        otpStorage.put(mobileNumber, new OTPData(otp, System.currentTimeMillis() + OTP_EXPIRY_TIME));
        twilioService.sendSms(mobileNumber,"your otp number is:"+otp);
        return otp;
    }
    public boolean validateOTP(String mobileNumber,String otp){
        OTPData storedOTPData = otpStorage.get(mobileNumber);

        if(storedOTPData==null){
            return false;
        }
        if(System.currentTimeMillis()>storedOTPData.getExpiryTime()){
            otpStorage.remove(mobileNumber);
            return false;
        }
        if(storedOTPData.getOtp().equals(otp)){
            otpStorage.remove(mobileNumber);
            return true;
        }
        return false;
    }



}
