package com.example.firstserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
public class SoccerPlayer {

    @Id
    public String playerId;

    @Size(min=1,max=30)
    @NotNull
    public String FirstName;

    @Size(min=1,max=30)
    @NotNull
    public String LastName;

    @Size(min=1,max=30)
    @NotNull
    public String DOB;

    @Size(min=1,max=30)
    @NotNull
    public String Position;

    @Size(min=0)
    @NotNull
    public Integer YellowCards;


    @Size(min=0)
    @NotNull
    public Integer RedCards;

    public SoccerPlayer(String FirstName, String LastName, String DOB, String Position,Integer RedCards, Integer YellowCards){
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.DOB = DOB;
        this.Position = Position;
        this.RedCards = RedCards;
        this.YellowCards = YellowCards;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    @Override
    public String toString(){

        return String.format("\n-=-=PLAYER %s-=-=\nFirst Name : %s\nLast Name : %s\nDOB : %s\nPosition : %s\nYellow Cards : %d\nRed Cards : %d",this.playerId,this.FirstName,this.LastName, this.DOB,this.Position,this.YellowCards,this.RedCards);

    }
}
