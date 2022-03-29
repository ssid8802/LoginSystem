package com.LoginSys.LoginSys;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitiazer {

	private final TwilioProperties twilioProperties;

	@Autowired
	public TwilioInitiazer(TwilioProperties twilioProperties)
	{
		this.twilioProperties = twilioProperties;
		Twilio.init("ACa3d617eb007a03ea02268ec603f80115", "0c53a8a72b012939159cef07e756c83a");
	}
}
