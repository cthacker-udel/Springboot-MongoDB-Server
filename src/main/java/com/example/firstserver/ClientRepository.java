package com.example.firstserver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ClientRepository extends MongoRepository<Client,String> {

    Client getClientByFirstName(String firstName);

    Client getClientByLastName(String lastName);

    Client getClientByUserName(String userName);

    Client getClientByAssets(BigDecimal assets);

    /// ^^ individual client methods ^^

    List<Client> getClientsByFirstName(String firstName);

    List<Client> getClientsByLastName(String lastName);

    List<Client> getClientsByUserName(String userName);

    Client insertClient(Client client);

    Client saveClient(Client client);

    boolean existsByFirstName(String firstName);

    boolean existsByLastName(String lastName);

    boolean existsByUserName(String userName);


}
