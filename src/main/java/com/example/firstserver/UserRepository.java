package com.example.firstserver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {

    public User getUserByUserName(String userName);
    public User getUserByApiKey(String apiKey);

    public User findByUserName(String userName);

    public User insert(User user);
    public User save(User user);

    public boolean existsByUserName(String userName);
    public boolean existsByApiKey(String apiKey);

    public List<User> findAll();

    public User removeByUserName(String userName);
    public User removeByApiKey(String apiKey);

    public Integer countByUserName(String userName);

}