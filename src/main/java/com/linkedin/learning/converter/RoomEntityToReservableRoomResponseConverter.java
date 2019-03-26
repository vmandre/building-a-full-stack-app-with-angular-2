package com.linkedin.learning.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.model.Self;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.rest.ResourceConstants;

@Component
public class RoomEntityToReservableRoomResponseConverter
		implements Converter<RoomEntity, ReservableRoomResponse> {

	@Override
	public ReservableRoomResponse convert(RoomEntity source) {
		ReservableRoomResponse reservableRoomResponse = new ReservableRoomResponse();
		
		reservableRoomResponse.setRoomNumber(source.getRoomNumber());
		reservableRoomResponse.setPrice(Integer.valueOf(source.getPrice()));
		
		Self self = new Self();
		self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
		
		Links links = new Links();
		links.setSelf(self);
		
		reservableRoomResponse.setLinks(links);
		
		return reservableRoomResponse;
	}

}
