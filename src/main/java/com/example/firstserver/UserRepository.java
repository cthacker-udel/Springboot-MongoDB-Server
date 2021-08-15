package com.example.firstserver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {

    public User getUserByUserName(String userName);
    public User getUserByApiKey(String apiKey);
    public User getUserBySecretKey(String secretKey);
    public User getUserByPassword(String password);

    public User findByUserName(String userName);
    public User findBySecretKey(String secretKey);
    public User findByApiKey(String apiKey);
    public User findByPassword(String password);

    public User insert(User user);
    public User save(User user);

    public boolean existsByUserName(String userName);
    public boolean existsByApiKey(String apiKey);
    public boolean existsBySecretKey(String secretKey);
    public boolean existsByPassword(String password);

    public List<User> findAll();

    public User removeByUserName(String userName);
    public User removeByApiKey(String apiKey);
    public User removeBySecretKey(String secretKey);
    public User removeByPassword(String password);

    public Integer countByUserName(String userName);
    public Integer countByApiKey(String apiKey);
    public Integer countBySecretKey(String secretKey);
    public Integer countByPassword(String password);

}
