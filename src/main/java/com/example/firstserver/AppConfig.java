package com.example.firstserver;


import com.mongodb.ClientSessionOptions;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class AppConfig {

    public MongoClient mongoClient(){

        //ConnectionString connectionString = new ConnectionString();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        MongoClient client = MongoClients.create(settings);

        MongoDatabase database = client.getDatabase("WORKFORCE");

        return client;

    }

    public @Bean
    MongoDatabase mongoDatabase(){

        MongoClient client = mongoClient();

        MongoDatabase database = client.getDatabase("WORKFORCE");

        return database;

    }

    public @Bean
    MongoTemplate mongoTemplate(){

        return new MongoTemplate(mongoClient(),"WORKFORCE");

    }

}
