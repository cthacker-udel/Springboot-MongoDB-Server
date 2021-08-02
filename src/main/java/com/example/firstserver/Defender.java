package com.example.firstserver;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Defender extends SoccerPlayer{

    @Size(min=0)
    @NotNull
    public Integer Tackles;

    @Size(min=0)
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
