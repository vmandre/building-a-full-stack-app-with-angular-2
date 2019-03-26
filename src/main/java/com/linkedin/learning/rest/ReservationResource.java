package com.linkedin.learning.rest;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.learning.converter.ReservationRequestToReservationEntityConverter;
import com.linkedin.learning.converter.RoomEntityToReservableRoomResponseConverter;
import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.model.response.ReservationResponse;
import com.linkedin.learning.repository.PageableRoomRepositoty;
import com.linkedin.learning.repository.ReservationRepository;
import com.linkedin.learning.repository.RoomRepository;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
public class ReservationResource {
	
	@Autowired
	private PageableRoomRepositoty pageableRoomRepositoty;
	
	@Autowired
	private RoomRepository roomRepository;	
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RoomEntityToReservableRoomResponseConverter converter;

	@Autowired
	private ConversionService conversionService;
	
	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<ReservableRoomResponse> getAvailableRooms(
			@RequestParam(value = "checkin")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate checkin,
			@RequestParam(value = "checkout")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate checkout, Pageable pageable) {
		
		Page<RoomEntity> roomEntityList = pageableRoomRepositoty.findAll(pageable);
		
		return roomEntityList.map(converter::convert);
		
	}
	
	@GetMapping(path = "/{roomId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RoomEntity> getRoomById(
			@PathVariable
			Long roomId) {
		
		Optional<RoomEntity> optional = roomRepository.findById(roomId);
		
		if (optional.isPresent()) {
			return new ResponseEntity<RoomEntity>(optional.get(), HttpStatus.OK);
		} else {
			return null;
		}
		
	}
	
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReservationResponse> createReservation(
			@RequestBody
			ReservationRequest reservationRequest) {
		
		//Save the reservation
		ReservationEntity reservation = conversionService.convert(reservationRequest, ReservationEntity.class);
		reservationRepository.save(reservation);
		
		Optional<RoomEntity> optional = roomRepository.findById(reservationRequest.getRoomId());
		if (optional.isPresent()) {
			RoomEntity roomEntity = optional.get();
			roomEntity.addReservation(reservation);
			roomRepository.save(roomEntity);
			reservation.setRoomEntity(roomEntity);
		}
		
		ReservationResponse reservationResponse = conversionService.convert(reservation, ReservationResponse.class);
		
		return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
	}	
	
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReservableRoomResponse> updateReservation(
			@RequestBody
			ReservationRequest reservationRequest) {
		
		return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.OK);
	}		
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<ReservableRoomResponse> deleteReservation(
			@PathVariable
			long id) {
		
		return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.NO_CONTENT);
	}			

}
