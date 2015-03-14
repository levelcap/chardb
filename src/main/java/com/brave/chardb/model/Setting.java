package com.brave.chardb.model;

import java.util.List;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;

/**
 * Created by dcohen on 2/13/15.
 */
public class Setting extends BaseDoc {
    private TimePeriod timePeriod;
    private Genre genre;
    List<Character> members;    

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    
    public List<Character> getMembers() {
        return members;
    }

    public void setMembers(List<Character> members) {
        this.members = members;
    }
}
