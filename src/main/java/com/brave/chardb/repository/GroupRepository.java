package com.brave.chardb.repository;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;
import com.brave.chardb.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {
    List<Group> findByUserId(String userId);
    List<Group> findByGenre(Genre genre);
    List<Group> findByTimePeriod(TimePeriod timePeriod);
}