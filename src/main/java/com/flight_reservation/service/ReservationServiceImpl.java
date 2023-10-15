
package com.flight_reservation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flight_reservation.dto.ReservationRequest;
import com.flight_reservation.entity.Flight;
import com.flight_reservation.entity.Passenger;
import com.flight_reservation.entity.Reservation;
import com.flight_reservation.repository.FlightRepository;
import com.flight_reservation.repository.PassengerRepository;
import com.flight_reservation.repository.ReservationRepository;
import com.flight_reservation.utilities.EmailUtil;
import com.flight_reservation.utilities.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private PassengerRepository passengerRepo;
	
	@Autowired
	private FlightRepository flightRepo;
	
	@Autowired
	private ReservationRepository reservationRepo;
	
	@Autowired
	private PDFGenerator pdfGenerator;
	
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public Reservation bookFlight(ReservationRequest request) {
		
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getFirstName());
		passenger.setMiddleName(request.getMiddleName());
		passenger.setLastName(request.getLastName());
		passenger.setEmail(request.getEmail());
		passenger.setPhone(request.getPhone());
		passengerRepo.save(passenger);
		
		long flightId = request.getFlightId();
		Optional<Flight> findById = flightRepo.findById(flightId);
		Flight flight = findById.get();
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(passenger);
		reservation.setCheckedIn(false);
		reservation.setNumberOfBags(0);
		
		String filePath = "C:\\stscode\\flight_reservation\\tickets\\reservation"+reservation.getId()+".pdf";
		
		reservationRepo.save(reservation);
		
		pdfGenerator.generateItinerary(reservation, filePath);
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		
		return reservation;
	}

}
