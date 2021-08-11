package com.odre.bookingrestaurantapi01.services;

import com.odre.bookingrestaurantapi01.exceptions.BookingException;
import com.odre.bookingrestaurantapi01.json.PaymentConfirmRest;
import com.odre.bookingrestaurantapi01.json.PaymentIntentRest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface PaymentService {
	
	
	public PaymentIntent paymentIntent(PaymentIntentRest paymentIntentRest) throws StripeException;
	
	public PaymentIntent paymentConfirm(PaymentConfirmRest paymentConfirmRest) throws StripeException, BookingException;
	
	public PaymentIntent paymentCancel(String paymentId) throws StripeException;

}
