package com.sparta.doom.fantasticninewebandapi.repositories;

import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesRepository extends MongoRepository<ScheduleDoc, String> {
}
