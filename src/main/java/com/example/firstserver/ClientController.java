package com.example.firstserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    ClientRepository repository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/client/add")
    public Object addClient(@RequestBody Client client, @RequestHeader("Authorization") String secretKey){

        String hashedPass = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedPass)){
            // valid user

            repository.save(client);

            System.out.println("\n---STATUS 200 : USER SAVED IN DATABASE---\n");

            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key passed into the function header is invalid");

        }

    }

    @PostMapping("/client/save")
    public Object saveClient(@RequestBody Client client, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){
            // valid user

            repository.insert(client);
            System.out.println("-- STATUS 200 : CLIENT SAVED --\n");
            return client;

        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The key passed via auth header is invalid");
        }

    }

    @GetMapping("/client/first")
    public Object getTheClientByFirstName(@RequestParam(value="firstname",defaultValue = "noname") String firstName, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){
            // valid user
            if(firstName.equalsIgnoreCase("noname")){
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Must supply first name as argument in query string");
            }
            return repository.getClientByFirstName(firstName);
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The user you are requesting does not exist");
        }

    }

    @GetMapping("/client/last")
    public Object getTheClientByLastName(@RequestParam(value="lastname",defaultValue = "noname") String lastName, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){
            // user exists
            if(lastName.equalsIgnoreCase("noname")){
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Must supply last name in query string");
            }
            else{
                return repository.getClientByLastName(lastName);
            }
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The user you are trying to retrieve does not exists[secret key error]");
        }

    }

    @GetMapping("/client/user")
    public Object getTheClientByUserName(@RequestParam(value="username",defaultValue = "nousername") String userName, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){
            // valid user
            if(repository.existsByUserName(userName)){
                // valid user
                return repository.getClientByUserName(userName);
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The username passed via query string is invalid");
            }
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The key you passed via header is invalid");
        }


    }

    @GetMapping("/client/assets")
    public Object getTheClientByAssets(@RequestParam(value="assets",defaultValue = "noassets") BigDecimal assets, @RequestHeader("Authorization") String secretKey){

        String hashedPass = new StringBuilder(secretKey).reverse().toString();

        if (userRepository.existsBySecretKey(hashedPass)) {
            // valid user
            return repository.getClientByAssets(assets);

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid request","The secret key passed into the function is invalid");

        }


    }

    @GetMapping("/client/all/firstName")
    public Object getTheClientsByFirstName(@RequestParam(value="firstname",defaultValue="noname") String firstName, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){

            if(!firstName.equalsIgnoreCase("noname")){
                // valid request

                if(repository.existsByFirstName(firstName)){
                    // valid user
                    return repository.getClientsByFirstName(firstName);
                }
                else{

                    return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The first name passed into the request does not exist in the database");

                }

            }
            else{

                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The first name passed into the function is invalid");

            }


        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key passed via request header is invalid");

        }


    }

    @GetMapping("/client/all/lastName")
    public Object getTheClientsByLastName(@RequestParam(value="lastname",defaultValue = "noname") String lastName, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(!userRepository.existsBySecretKey(hashedKey)){

            if(repository.existsByLastName(lastName)){

                return repository.getClientsByLastName(lastName);

            }
            else{

                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The last name passed into this request is invalid");

            }

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key passed via request header is invalid");

        }


    }

    @GetMapping("/client/all/userName")
    public Object getTheClientsByUserName(@RequestParam(value="username",defaultValue = "noname") String userName, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){

            if(repository.existsByUserName(userName)){
                // valid request

                return repository.getClientsByUserName(userName);

            }
            else{

                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The username passed via query string is invalid");

            }

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key passed via request header is invalid");

        }



    }

    @GetMapping("/client/exists/firstname")
    public Object doesClientExistFirstName(@RequestParam(value="firstname",defaultValue = "noname") String firstname, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){

            // valid user
            if(repository.existsByFirstName(firstname)){
                // valid user
                return repository.existsByFirstName(firstname);
            }
            else{

                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The user you are searching for via firstname in query string is invalid");

            }

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST, "Invalid Request", "The secret key passed via request header is invalid");

        }


    }

    @GetMapping("/client/exists/lastname")
    public Object doesClientExistLastName(@RequestParam(value="lastname",defaultValue = "noname") String lastName, @RequestHeader("Authorization") String secretKey){

        String hashedKey = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){

            if(repository.existsByLastName(lastName)){
                // valid user
                return repository.existsByLastName(lastName);
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The user you are searching for via lastname in query string is invalid");
            }

        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The user you are searching for via secret key is invalid");
        }

    }

    @GetMapping("/client/exists/username")
    public Object doesClientExistUserName(@RequestParam(value="username",defaultValue="noname") String userName, @RequestHeader("Authorization") String auth){

        String hashedKey = new StringBuilder(auth).reverse().toString();

        if(userRepository.existsBySecretKey(hashedKey)){

            // valid user
            if(repository.existsByUserName(userName)){
                // valid user
                return repository.existsByUserName(userName);
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The username you are searching for is invalid");
            }

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Authentication Failed: the secret key passed via header is invalid");

        }


    }

}
