package com.brave.chardb.repository;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;
import com.brave.chardb.model.Character;
import com.brave.chardb.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepository extends MongoRepository<Character, String> {
    List<Character> findByUserId(String userId);
    List<Character> findByNameLikeIgnoreCase(String name);
    List<Group> findByGenre(Genre genre);
    List<Group> findByTimePeriod(TimePeriod timePeriod);
}