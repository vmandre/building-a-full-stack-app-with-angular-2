package com.linkedin.learning.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.linkedin.learning.converter.ReservationEntityToReservationResponseConverter;
import com.linkedin.learning.converter.ReservationRequestToReservationEntityConverter;
import com.linkedin.learning.converter.RoomEntityToReservableRoomResponseConverter;

@Component
public class ConversionConfig {
	
	private Set<Converter> getConverters() {
		Set<Converter> converters = new HashSet<Converter>();
		converters.add(new RoomEntityToReservableRoomResponseConverter());
		converters.add(new ReservationRequestToReservationEntityConverter());
		converters.add(new ReservationEntityToReservationResponseConverter());
		
		return converters;
	}
	
	@Bean
	public ConversionService conversionService() {
		ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
		bean.setConverters(getConverters());
		bean.afterPropertiesSet();
		
		return bean.getObject();
		
	}	

	
}
