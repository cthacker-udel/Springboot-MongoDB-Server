package com.example.firstserver;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String> {

    Employee findByName(String name);

    List<Employee> findAll();

    Employee save(Employee employee);

    void delete(Employee e);

    void deleteAll();

}

