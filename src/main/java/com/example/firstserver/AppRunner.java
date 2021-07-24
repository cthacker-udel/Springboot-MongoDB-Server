package com.example.firstserver;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        //System.out.println("Collection Exists? " + mongoTemplate.collectionExists("Employees"));
        //MongoCollection<Document> employeeCollection = mongoTemplate.getCollection("Employees");

        //BasicDBObject basicDBObject = new BasicDBObject();
        //basicDBObject.append("#00001",new Employee("Cameron Thacker","Software Engineer",10,20,1998));

        //employeeCollection.insertOne(new Employee("Cameron Thacker","Software Engineer",10,20,1998).pojoToDoc());

        //Criteria criteria = Criteria.where("Name").is("Cameron Thacker");
        //Query query = Query.query(criteria);
        //Employee employee = mongoTemplate.findOne(query,Employee.class);
        //int x = 10;


        //Map<String,Object> employeesToAdd = new LinkedHashMap<>();
        //employeesToAdd.put("#00001",new Employee("Cameron Thacker","Software Engineer",10,20,1998));

        //employeeCollection.insertOne(new Document(basicDBObject));
        //Employee newEmployee = new Employee("Cameron Thacker","Software Engineer",10,20,1998);
        //System.out.println(mongoTemplate.insert(newEmployee).toString());
    }
}


