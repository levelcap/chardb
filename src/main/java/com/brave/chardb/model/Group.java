package com.brave.chardb.model;

import java.util.List;

/**
 * Created by dcohen on 2/13/15.
 */
public class Group extends Character {
    List<Character> members;

    public List<Character> getMembers() {
        return members;
    }

    public void setMembers(List<Character> members) {
        this.members = members;
    }
}
