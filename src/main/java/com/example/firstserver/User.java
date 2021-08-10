package com.example.firstserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;

@Document
public class User {

    @Id
    public String userId;

    @Field("APIKEY")
    @Min(1)
    public String apiKey;

    @Field("SECRETKEY")
    @Min(1)
    public String secretKey;

    @Field("USERNAME")
    @Min(1)
    public String userName;

    @Field("PASSWORD")
    @Min(1)
    public String password;

}
