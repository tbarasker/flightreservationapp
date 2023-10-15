package com.flight_reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flight_reservation.dto.ReservationRequest;
import com.flight_reservation.entity.Reservation;
import com.flight_reservation.service.ReservationService;

@Controller
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping("/confirmReservation")
	public String confirmReservation(ReservationRequest request, ModelMap modelMap) {
//		System.out.println(request);
//		System.out.println(request.getFlightId());
		Reservation reservationId = reservationService.bookFlight(request);
		modelMap.addAttribute("reservationId", reservationId.getId());
		
		return "confirmReservation";
	}

}
