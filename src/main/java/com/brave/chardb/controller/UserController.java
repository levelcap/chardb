package com.brave.chardb.controller;

import com.brave.chardb.model.User;
import com.brave.chardb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<User> getUser() {
        return new ResponseEntity<User>(getCurrentUser(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<User> saveUser(@RequestBody User user) {
        User currentUser = getCurrentUser();
        if (user.getPassword() == null) {
            user.setPassword(currentUser.getPassword());
        } else {
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            String hashedPassword = encoder.encodePassword(user.getPassword(), null);
            user.setPassword(hashedPassword);
        }
        user.setId(currentUser.getId());

        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
