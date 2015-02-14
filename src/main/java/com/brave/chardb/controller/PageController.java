package com.brave.chardb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brave.chardb.model.Character;
import com.brave.chardb.model.User;
import com.brave.chardb.repository.CharacterRepository;

/**
 * Created by dcohen on 2/13/15.
 */
@Controller
public class PageController extends BaseController {
    @Autowired
    private CharacterRepository characterRepository;

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/{id}")
    public String index(@PathVariable("id") String id, Model model) {
        Character character = characterRepository.findOne(id);
        model.addAttribute("character", character);
        model.addAttribute("title", "CharDB - " + character.getName());
        return "char";
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, Model model) {
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
            model.addAttribute("title", "CharDB - Editing " + existingCharacter.getName());
            return "edit";
        }
        return "login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
