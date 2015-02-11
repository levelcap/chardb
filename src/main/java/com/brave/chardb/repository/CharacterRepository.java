package com.brave.chardb.repository;

import com.brave.chardb.model.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepository extends MongoRepository<Character, String> {
    List<Character> findByUserId(String userId);
}