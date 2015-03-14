package com.brave.chardb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brave.chardb.model.Character;
import com.brave.chardb.model.Location;
import com.brave.chardb.repository.CharacterRepository;
import com.brave.chardb.repository.LocationRepository;
import com.brave.chardb.repository.UserRepository;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/search")
public class SearchController extends BaseController {

    @Autowired
    private CharacterRepository characterRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LocationRepository locationRepository;

    @RequestMapping("/character/name/{term}")
    @ResponseBody
    public HttpEntity<List<Character>> getCharacter(@PathVariable("term") String term) {
        List<Character> characters = characterRepository.findByNameLikeIgnoreCase(term);
        for (Character character : characters) {
        	character.setUser(userRepository.findOne(character.getUserId()));
        }
        return new ResponseEntity<List<Character>>(characters, HttpStatus.OK);
    }
 
    @RequestMapping("/location/name/{term}")
    @ResponseBody
    public HttpEntity<List<Location>> getLocoation(@PathVariable("term") String term) {
        List<Location> locations = locationRepository.findByNameLikeIgnoreCase(term);
        for (Location location : locations) {
        	location.setUser(userRepository.findOne(location.getUserId()));
        }
        
        return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
    }
 
}
