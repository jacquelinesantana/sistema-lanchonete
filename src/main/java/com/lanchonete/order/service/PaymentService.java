package com.lanchonete.order.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;




@Service
public class PaymentService {

	 public String createPayment(BigDecimal value, String description, String email) throws MPException {
	        Preference preference = new Preference();

	        Item item = new Item()
	                .setTitle(description)
	                .setQuantity(1)
	                .setUnitPrice(value.floatValue());

	        Payer payer = new Payer();
	        payer.setEmail(email);

	        preference.appendItem(item);
	        preference.setPayer(payer);

	        preference.save();
	        return preference.getInitPoint();
	    }
}
