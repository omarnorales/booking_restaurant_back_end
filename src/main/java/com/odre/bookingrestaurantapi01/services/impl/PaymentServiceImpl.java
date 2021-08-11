package com.odre.bookingrestaurantapi01.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.PaymentConfirmRest;
import com.odre.bookingrestaurantapi01.json.PaymentIntentRest;
import com.odre.bookingrestaurantapi01.services.EmailService;
import com.odre.bookingrestaurantapi01.services.PaymentService;
import com.odre.bookingrestaurantapi01.services.ReservationService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Value("${stripe.key.secret}")
	String secretkey;
	
	@Autowired
	private EmailService  emailService;
	
	@Autowired
	ReservationService reservationService;

	public enum Currency {
		USD, EUR;
	}

	public PaymentIntent paymentIntent(PaymentIntentRest paymentIntentRest) throws StripeException {

		Stripe.apiKey = secretkey;

		Map<String, Object> params = new HashMap<>();
		params.put("amount", paymentIntentRest.getPrice());
		params.put("currency", Currency.USD);
		params.put("description", paymentIntentRest.getDescription());

		List<Object> paymentMethodTypes = new ArrayList<>();
		paymentMethodTypes.add("card");

		params.put("payment_method_types", paymentMethodTypes);

		PaymentIntent paymentIntent = PaymentIntent.create(params);

		return paymentIntent;
	}

	public PaymentIntent paymentConfirm(PaymentConfirmRest paymentConfirmRest) throws StripeException, BookingException {

		Stripe.apiKey = secretkey;

		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentConfirmRest.getPaymentId());

		Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		paymentIntent.confirm(params);
		
		
		reservationService.updateReservation(true, paymentConfirmRest.getLocator());
		this.emailService.processSendEmail(paymentConfirmRest.getEmail(), "PAYMENT" , paymentConfirmRest.getName());

		return paymentIntent;
	}

	public PaymentIntent paymentCancel(String paymentId) throws StripeException {
		Stripe.apiKey = secretkey;

		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);

		paymentIntent.cancel();

		return paymentIntent;
	}

}
