package com.brave.chardb.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brave.chardb.model.Character;
import com.brave.chardb.model.User;
import com.brave.chardb.repository.CharacterRepository;
import com.brave.chardb.repository.UserRepository;

/**
 * Created by dcohen on 2/13/15.
 */
@Controller
public class PageController extends BaseController {
    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping("/")
    public String index(Model model) {
    	model.addAttribute("loggedIn", isLoggedIn());
        return "index";
    }

    @RequestMapping("/new")
    public String newCharacter(Model model) {
    	model.addAttribute("loggedIn", isLoggedIn());
    	if (isLoggedIn()) {
    		Character character = new Character();
    		character.setId(UUID.randomUUID().toString());
    		character.setUserId(getCurrentUser().getId());
    		character.setUrl("/images/blank.png");
    		model.addAttribute("character", character);
            model.addAttribute("title", "CharDB - New Character");
    		return "edit";
    	} else {
    		return "login";
    	}
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, Model model) {
    	model.addAttribute("loggedIn", isLoggedIn());
        if (isLoggedIn()) {
            User currentUser = getCurrentUser();
            Character existingCharacter = characterRepository.findOne(id);
            if (existingCharacter == null) {
                return "index";
            } else if (!existingCharacter.getUserId().equals(currentUser.getId())) {
            	model.addAttribute("character", existingCharacter);
                model.addAttribute("title", "CharDB - " + existingCharacter.getName());
                return "char";
            }
            model.addAttribute("character", existingCharacter);
            model.addAttribute("title", "CharDB - Editing " + existingCharacter.getName());
            return "edit";
        }
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model) {
    	model.addAttribute("loggedIn", isLoggedIn());
    	model.addAttribute("title", "CharDB - Login");
        return "login";
    }
    
	@RequestMapping("/user")
	public String getLoggedInUser(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			model.addAttribute("user", getCurrentUser());
			model.addAttribute("title", "CharDB - User");
			addCharactersToModel(model, getCurrentUser());
			return "user";
		} else {
			return "login";
		}
	}
	
	@RequestMapping("/user/{id}")
	public String getUser(@PathVariable("id") String id, Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		model.addAttribute("title", "CharDB - User");
		addCharactersToModel(model, user);
		return "user";
	}
	
	@RequestMapping("/register")
	public String registrationPage(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		return "register";
	}
	
    @RequestMapping("/{id}")
    public String index(@PathVariable("id") String id, Model model) {
        Character character = characterRepository.findOne(id);
        model.addAttribute("loggedIn", isLoggedIn());
        model.addAttribute("character", character);
        model.addAttribute("title", "CharDB - " + character.getName());
        return "char";
    }
    
	private void addCharactersToModel(Model model, User user) {
		model.addAttribute("loggedIn", isLoggedIn());
		model.addAttribute("characters", characterRepository.findByUserId(user.getId()));
	}
}
