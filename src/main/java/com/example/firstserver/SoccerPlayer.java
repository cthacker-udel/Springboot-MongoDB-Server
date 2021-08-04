package com.example.firstserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
public class SoccerPlayer {

    @Id
    public String playerId;

    @Field("FirstName")
    @Size(min=1,max=30)
    @NotNull
    public String firstName;

    @Field("LastName")
    @Size(min=1,max=30)
    @NotNull
    public String lastName;

    @Field("DOB")
    @Size(min=1,max=30)
    @NotNull
    public String DOB;

    @Field("Position")
    @Size(min=1,max=30)
    @NotNull
    public String position;

    @Field("YellowCards")
    @Size(min=0)
    @NotNull
    public Integer yellowCards;

    @Field("RedCards")
    @Size(min=0)
    @NotNull
    public Integer redCards;

    public SoccerPlayer(String firstName, String lastName, String DOB, String position,Integer redCards, Integer yellowCards){
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.position = position;
        this.redCards = redCards;
        this.yellowCards = yellowCards;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        position = position;
    }

    @Override
    public String toString(){

        return String.format("\n-=-=PLAYER %s-=-=\nFirst Name : %s\nLast Name : %s\nDOB : %s\nPosition : %s\nYellow Cards : %d\nRed Cards : %d",this.playerId,this.firstName,this.lastName, this.DOB,this.position,this.yellowCards,this.redCards);

    }
}
