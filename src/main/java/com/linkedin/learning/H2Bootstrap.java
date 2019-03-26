package com.linkedin.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.repository.RoomRepository;

@Component
public class H2Bootstrap implements CommandLineRunner {
	
	@Autowired
	private RoomRepository roomRepository;

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Bootstraping data:");
		
		roomRepository.save(new RoomEntity(405, "200"));
		roomRepository.save(new RoomEntity(406, "220"));
		roomRepository.save(new RoomEntity(407, "250"));
		
		Iterable<RoomEntity> all = roomRepository.findAll();
		
		System.out.println("Printing out data:");
		all.forEach(room -> System.out.println(room.getRoomNumber()));
	}

}
