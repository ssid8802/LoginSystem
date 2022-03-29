package com.LoginSys.LoginSys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import com.LoginSys.LoginSys.TwilioProperties;

@Service
public class PhoneVerificationService {

	private final TwilioProperties twilioProperties;

	@Autowired
	public PhoneVerificationService(TwilioProperties twilioProperties) {
		this.twilioProperties =twilioProperties;
	}
	
	
	//method to send to otp
    public VerificationResult startVerification(String phone) {
        try {
            Verification verification = Verification.creator("VAe25e076b62461217847a944dfbaba4b7", phone, "sms").create();
            if("approved".equals(verification.getStatus())|| "pending".equals(verification.getStatus())) {
			return new VerificationResult(verification.getSid());
			}
        } catch (ApiException exception) {
            return new VerificationResult(new String[] {exception.getMessage()});
        }
        return null;
    }

    //method to verify the otp
    public VerificationResult checkVerification(String phone, String code) {
        try
        {
            VerificationCheck verification = VerificationCheck.creator( "VAe25e076b62461217847a944dfbaba4b7", code).setTo(phone).create();
            if("approved".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
            return new VerificationResult(new String[]{"Invalid code."});
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }
	
}
