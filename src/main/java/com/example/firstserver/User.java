package com.example.firstserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Document
public class User {

    @Id
    public String userId;

    @Field("APIKEY")
    @Min(1)
    @NotNull
    public String apiKey;

    @Field("SECRETKEY")
    @Min(1)
    @NotNull
    public String secretKey;

    @Field("USERNAME")
    @Min(1)
    @NotNull
    public String userName;

    @Field("PASSWORD")
    @Min(1)
    @NotNull
    public String password;

    public User(String apiKey, String secretKey, String userName, String password){
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.userName = userName;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
