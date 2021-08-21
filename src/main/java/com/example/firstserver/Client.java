package com.example.firstserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Client {

    @Id
    public String id;

    @Field("FIRSTNAME")
    @NotNull
    public String firstName;

    @Field("LASTNAME")
    @NotNull
    public String lastName;

    @Field("USERNAME")
    @NotNull
    public String userName;

    @Field("ASSETS")
    @NotNull
    public BigDecimal assets;


    public Client(String firstName, String lastName, String userName, BigDecimal assets){

        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.assets = assets;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getAssets() {
        return assets;
    }

    public void setAssets(BigDecimal assets) {
        this.assets = assets;
    }
}
