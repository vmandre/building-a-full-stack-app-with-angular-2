package com.linkedin.learning.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.model.request.ReservationRequest;

@Component
public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, ReservationEntity> {

	@Override
	public ReservationEntity convert(ReservationRequest source) {
		
		ReservationEntity entity = new ReservationEntity();
		entity.setCheckin(source.getCheckin());
		entity.setCheckout(source.getCheckout());
		
		if (null != source.getId()) {
			entity.setId(source.getId());
		}
		
		return entity;
	}

}
