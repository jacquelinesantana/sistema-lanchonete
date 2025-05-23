package com.lanchonete.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;

import jakarta.annotation.PostConstruct;

@Configuration
public class MercadoPagoConfig {
	
	@Value("TEST-6329425028869907-042119-fa26ce885dddcc30ed3339e3e89c549f-46313882")
	private String accessToken;
	
	@PostConstruct
	public void init() {
		try {
			MercadoPago.SDK.setAccessToken(accessToken);
		}catch(MPConfException e) {
			e.printStackTrace();
		}
	}

}
