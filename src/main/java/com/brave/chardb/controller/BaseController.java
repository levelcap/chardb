package com.brave.chardb.controller;

import com.brave.chardb.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class BaseController {
    @Autowired
    private HttpSession httpSession;

	protected User getCurrentUser() {
        return (User) httpSession.getAttribute("user");
	}

    protected boolean isLoggedIn() {
        if (getCurrentUser() == null) {
            return false;
        } else {
            return true;
        }
    }
}
