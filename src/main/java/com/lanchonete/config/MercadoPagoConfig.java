package com.lanchonete.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;

import jakarta.annotation.PostConstruct;

@Configuration
public class MercadoPagoConfig {
	
	@Value("bearer APP_USR-4934588586838432-XXXXXXXX-241983636")
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
