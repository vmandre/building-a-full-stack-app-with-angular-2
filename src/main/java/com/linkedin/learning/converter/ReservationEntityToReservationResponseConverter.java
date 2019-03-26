package com.linkedin.learning.converter;

import org.springframework.core.convert.converter.Converter;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.response.ReservationResponse;

public class ReservationEntityToReservationResponseConverter implements Converter<ReservationEntity, ReservationResponse> {

	@Override
	public ReservationResponse convert(ReservationEntity source) {
		ReservationResponse response = new ReservationResponse();
		
		response.setCheckin(source.getCheckin());
		response.setCheckout(source.getCheckout());
		
		if (null != source.getRoomEntity()) {
			response.setId(source.getRoomEntity().getId());
		}
		
		return response;
	}

}
