package com.brave.chardb.controller;

import com.brave.chardb.model.Character;
import com.brave.chardb.model.User;
import com.brave.chardb.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/character")
public class CharacterController extends BaseController {

    @Autowired
    private CharacterRepository characterRepository;

    @RequestMapping("/{id}")
    @ResponseBody
    public HttpEntity<Character> getCharacter(@PathVariable("id") String id) {
        Character character = characterRepository.findOne(id);
        return new ResponseEntity<Character>(character, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<Character> saveCharacter(@RequestBody Character character) {
        if (isLoggedIn()) {
            User currentUser = getCurrentUser();
            Character existingCharacter = characterRepository.findOne(character.getId());
            if (existingCharacter != null) {
                if (existingCharacter.getUserId().equals(currentUser.getId())) {
                    character = characterRepository.save(character);
                    return new ResponseEntity<Character>(character, HttpStatus.OK);
                } else {
                    return new ResponseEntity<Character>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                character.setUserId(currentUser.getId());
                character = characterRepository.save(character);
                return new ResponseEntity<Character>(character, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Character>(HttpStatus.UNAUTHORIZED);
        }
    }
}
