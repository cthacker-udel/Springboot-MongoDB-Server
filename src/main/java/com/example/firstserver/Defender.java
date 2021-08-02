package com.example.firstserver;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
public class Defender extends SoccerPlayer{

    @NotNull
    public Integer Tackles;

    @NotNull
    public Integer Steals;

    public Defender(String FirstName, String LastName, String DOB, String Position, Integer RedCards, Integer YellowCards, Integer Tackles, Integer Steals) {
        super(FirstName, LastName, DOB, Position, RedCards, YellowCards);
        this.Tackles = Tackles;
        this.Steals = Steals;
    }


    public Integer getTackles() {
        return Tackles;
    }

    public void setTackles(Integer tackles) {
        Tackles = tackles;
    }

    public Integer getSteals() {
        return Steals;
    }

    public void setSteals(Integer steals) {
        Steals = steals;
    }
}
