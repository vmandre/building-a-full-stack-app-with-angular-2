package com.linkedin.learning.repository;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.learning.entity.RoomEntity;

public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

}
