package com.example.firstserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll(){

        return employeeRepository.findAll();

    }

    public Employee findByName(String name){

        return employeeRepository.findByName(name);

    }

    public Employee save(Employee employee){

        return employeeRepository.save(employee);

    }

    public void delete(Employee employee){

        employeeRepository.delete(employee);

    }

    public void deleteAll(){

        employeeRepository.deleteAll();

    }


}
