package com.brave.chardb.controller;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;
import com.brave.chardb.model.Character;
import com.brave.chardb.model.User;
import com.brave.chardb.repository.CharacterRepository;
import com.brave.chardb.repository.UserRepository;
import com.brave.chardb.services.FeaturedService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dcohen on 2/13/15.
 */
@Controller
public class PageController extends BaseController {
	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	FeaturedService featuredService;

	@RequestMapping("/")
	public String index(Model model) {
		Character character = featuredService.getFeaturedCharacter();
		model.addAttribute("character", character);
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
			model.addAttribute("title", "Character Center - New Character");
			return "edit";
		} else {
			return "login";
		}
	}

	@RequestMapping("/char/{id}")
	public String index(@PathVariable("id") String id, Model model) {
		Character character = characterRepository.findOne(id);
		model.addAttribute("loggedIn", isLoggedIn());
		model.addAttribute("character", character);
		model.addAttribute("title", "Character Center - " + character.getName());
		return "char";
	}

	@RequestMapping("/char/{id}/edit")
	public String edit(@PathVariable("id") String id, Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			User currentUser = getCurrentUser();
			Character existingCharacter = characterRepository.findOne(id);
			if (existingCharacter == null) {
				return "index";
			} else if (!existingCharacter.getUserId().equals(
					currentUser.getId())) {
				model.addAttribute("character", existingCharacter);
				model.addAttribute("title", "Character Center - "
						+ existingCharacter.getName());
				return "char";
			}
			model.addAttribute("character", existingCharacter);
			model.addAttribute("title", "Character Center - Editing "
					+ existingCharacter.getName());
			return "edit";
		}
		return "login";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		model.addAttribute("title", "Character Center - Login");
		return "login";
	}

	@RequestMapping("/user")
	public String getLoggedInUser(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			User user = getCurrentUser();
			if (StringUtils.isEmpty(user.getUsername())) {
				user.setUsername(user.getId());
			}
			model.addAttribute("edit", true);
			model.addAttribute("user", user);
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
		model.addAttribute("edit", false);
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

	@RequestMapping("/c/{id}")
	public String characterShortcut(@PathVariable("id") String id) {
		return "/user";
	}

	@RequestMapping("/s/{id}")
	public String settingShortcut(@PathVariable("id") String id) {
		return "/user";
	}

	@RequestMapping("/u/{id}")
	public String userShortcut(@PathVariable("id") String id) {
		return "/user";
	}

	@RequestMapping("/browse/{cat}")
	public String browseByGenre(@PathVariable("cat") String cat, Model model) {
		Map<String, List<Character>> catMap = new HashMap<String, List<Character>>();
		if (cat.equals("genre")) {
			populateGenreMap(catMap);
			model.addAttribute("browseCriteria", "Browse by Genre");
		} else if (cat.equals("time")) {
			populateTimePeriodMap(catMap);
			model.addAttribute("browseCriteria", "Browse by Time Period");
		}
		model.addAttribute("catMap", catMap);
		return "browse";
	}

	private void addCharactersToModel(Model model, User user) {
		model.addAttribute("loggedIn", isLoggedIn());
		model.addAttribute("characters",
				characterRepository.findByUserId(user.getId()));
	}

	private void populateGenreMap(Map<String, List<Character>> catMap) {
		for (Genre genre : Genre.values()) {
			List<Character> characters = characterRepository.findByGenre(genre);
			if (!CollectionUtils.isEmpty(characters)) {
				catMap.put(genre.toString(), characters);
			}
		}
	}

	private void populateTimePeriodMap(Map<String, List<Character>> catMap) {
		for (TimePeriod timePeriod : TimePeriod.values()) {
			List<Character> characters = characterRepository
					.findByTimePeriod(timePeriod);
			if (!CollectionUtils.isEmpty(characters)) {
				catMap.put(timePeriod.toString(), characters);
			}
		}
	}
}
