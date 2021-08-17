package com.example.firstserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@RestController
public class UserController {

    @Autowired
    MongoTemplate tempate;

    @Autowired
    UserRepository repository;

    @PostMapping("/user")
    public Object addUser(@RequestBody User newUser){

        try{

            if(repository.existsByUserName(newUser.userName)){
                // user already exists
                return new ApiError(HttpStatus.CONFLICT,"Attempting to add already existing user","Invalid Request");
            }
            else{
                repository.insert(newUser);
                System.out.println("\nStatus 200 : Successfully added new user to database\n");
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

        }
        catch(Exception e){
            return new ApiError(HttpStatus.BAD_REQUEST,"Attempting to add user to database","Invalid request");
        }

    }

    @GetMapping("/user/username/{userName}")
    public Object getUserByUserName(@PathVariable("userName") String userName, @RequestParam(value="auth",defaultValue = "noauth") String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {


        String signature = new StringBuffer(secretKey).reverse().toString();
        if(repository.existsBySecretKey(signature)){
            // valid authorization
            User theUser = repository.getUserByUserName(userName);
            System.out.println("\nSTATUS 200 : Successfully acquired user\n");
            return theUser;
        }
        else{
            System.out.println("\nSTATUS 400 : Authorization failed\n");
            return new ApiError(HttpStatus.BAD_REQUEST,"Authorization Error","Error: Signataure passed in request was invalid");
        }

    }

    @GetMapping("/user/apikey/{apiKey}")
    public Object getUserByApiKey(@PathVariable("apiKey") String apiKey, @RequestParam(value="password",defaultValue = "nopass") String password){

        String signature = new StringBuffer(password).reverse().toString();

        if(repository.existsByPassword(signature)){
            // user exists
            System.out.println("\nStatus 200 : User Exists!\n");
            return repository.getUserByApiKey(apiKey);

        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","You provided the incorrect password");
        }

    }

    @GetMapping("/user/userpass")
    public Object getUserViaUsernamePassword(@RequestParam(value="username",defaultValue = "nousername") String userName, @RequestParam(value="password",defaultValue = "nopass") String password){

        if(repository.existsByPassword(password) && repository.existsByUserName(userName)){

            // user exists
            return repository.getUserByUserName(userName);

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid request","Invalid username and password provided");

        }

    }

    @GetMapping("/user/get/apikey/{apiKey}")
    public Object getTheUserByApiKey(@PathVariable("apiKey") String apiKey, @RequestParam(value="password",defaultValue="nopass") String pass){

        if(!pass.equalsIgnoreCase("nopass")){
            // valid pass
            String hashedPass = new StringBuilder(pass).reverse().toString();
            if(repository.existsBySecretKey(hashedPass)){
                // valid user
                if(repository.existsByApiKey(apiKey)){
                    // valid user
                    return repository.getUserByApiKey(apiKey);
                }
                else{
                    return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Api key passed into request is invalid");
                }
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Invalid secret key passed in without valid encryption");
            }
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid request","Must pass in password as secret key in query string");
        }

    }

    @GetMapping("/api/get/secretKey/{theKey}")
    public Object getTheUserBySecretKey(@PathVariable("theKey") String theKey, @RequestParam(value="password",defaultValue ="defpassword") String pass){

        if(!pass.equalsIgnoreCase("defpassword")){
            // valid user
            // hashing apikey
            String hashedPass = new StringBuilder(pass).reverse().toString();

            if(repository.existsByApiKey(hashedPass)){
                // valid user
                return repository.getUserBySecretKey(theKey);
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Invalid api key passed into method header");
            }
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid request","You must pass in a valid passsword in the query string");
        }

    }

    @GetMapping("/user/get/password/{thePassword}")
    public Object getTheUserByPassword(@PathVariable("thePassword") String pass, @RequestParam(value="signature",defaultValue = "defsignature") String signature){

        if(!signature.equalsIgnoreCase("defsignature")){
            // valid request
            String hashedSignature = new StringBuilder(signature).reverse().toString();
            if(repository.existsBySecretKey(hashedSignature)){
                // valid signature
                if(repository.existsByPassword(pass)){
                    // valid user
                    return repository.getUserByPassword(pass);
                }
                else{
                    return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Pass in valid password in request");
                }
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Pass in valid signature into request");
            }
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Pass in valid signature in request as query param");
        }

    }

    @GetMapping("/user/username/{username}/find")
    public Object findUserByName(@PathVariable("username") String userName, @RequestParam(value="password",defaultValue="defpassword") String password){

        User theUser = repository.findByUserName(userName);
        if(theUser != null){
            // user exists
            String hashedPass = new StringBuilder(password).reverse().toString();
            if(hashedPass == theUser.password){
                // correct password
                return theUser;
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid password","The password you passed into the find user by username function is incorrect");
            }
        }
        else{
            System.out.println("\nSTATUS 400: UNABLE TO RETRIEVE USER\n");
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid request","Specified user with username passed does not exist in database");
        }

    }

    @GetMapping("/user/{theKey}/secretKey")
    public Object getBySecretKey(@PathVariable("theKey") String theSecretKey, @RequestParam(value="password",defaultValue="nopass") String password){

        // revere pass to verify
        String hashedPass = new StringBuilder(password).reverse().toString();

        if(repository.existsByPassword(hashedPass)){
            // valid auth
            return repository.findBySecretKey(theSecretKey);
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Password sent in is invalid");
        }

    }

    @GetMapping("/user/find/secret/{secretKey}")
    public Object findTheUserBySecretKey(@PathVariable("secretKey") String secretKey, @RequestParam(value="password",defaultValue = "nopass") String thePassword){

        String hashedPassword = new StringBuilder(thePassword).reverse().toString();

        if(repository.existsBySecretKey(hashedPassword)){
            // valid user
            if(repository.existsBySecretKey(secretKey)){
                // valid user
                return repository.findBySecretKey(secretKey);
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key you passed in the url is not valid");
            }
        }
        else{
            // invalid user
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The user you are requesting does not exist");
        }

    }

    @GetMapping("/user/find/api/{apiKey}")
    public Object findTheUserByApiKey(@PathVariable("apiKey") String apiKey, @RequestParam(value="password",defaultValue = "nopass") String password){

        String hashedPass = new StringBuilder(password).reverse().toString();

        if(repository.existsBySecretKey(hashedPass)){
            // valid user
            if(repository.existsByApiKey(apiKey)){
                // valid user
                return repository.findByApiKey(apiKey);
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The apikey passed into the url is invalid");
            }
        }
        else{
            // invalid user
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The password passed in the query string the request was not valid");
        }

    }

    @GetMapping("/user/find/password/{password}")
    public Object findTheUserByPassword(@PathVariable("password") String password, @RequestParam(value="password",defaultValue = "nopass") String thePassword){

        String hashedPass = new StringBuilder(thePassword).reverse().toString();

        if(repository.existsBySecretKey(hashedPass)){
            // valid user
            if(repository.existsByPassword(password)){
                // valid user
                return repository.findByPassword(password);
            }
            else{
                // invalid user
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The user you are trying to access with the password in the url does not exist");
            }
        }
        else{
            // invalid user
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The password you passed in the query string is invalid");
        }

    }

    @PostMapping("/user/save")
    public Object saveUser(@RequestBody User user, @RequestHeader("password") String pass){

        // verify secretKey

        String hashedSecretKey = new StringBuilder(pass).reverse().toString();

        if(repository.existsBySecretKey(hashedSecretKey)){
            // valid save
            repository.save(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","User does not exist, unable to save user");
        }

    }

    @GetMapping("/user/all")
    public Object getAllUsers(@RequestHeader("password") String pass){

        String hashedKey = new StringBuilder(pass).reverse().toString();

        if(repository.existsBySecretKey(hashedKey)){
            // valid user request
            return repository.findAll();
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Invalid request, must pass in valid hashed secret key");
        }

    }

    @DeleteMapping("/user/remove/username/{theName}")
    public Object removeByUserName(@PathVariable("theName") String theName, @RequestHeader("password") String pass){

        String hashedPass = new StringBuilder(pass).reverse().toString();

        if(repository.existsBySecretKey(hashedPass)){
            return repository.removeByUserName(theName);
        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid request","Invalid secret key passed in");

        }

    }

    @DeleteMapping("/user/remove/apikey/{theKey}")
    public Object removeByApiKey(@PathVariable("theKey") String theKey, @RequestHeader("password") String pass){

        String hashedSecretKey = new StringBuilder(pass).reverse().toString();

        if(repository.existsBySecretKey(hashedSecretKey)){
            // valid request
            return repository.removeByApiKey(theKey);
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid request","Must pass in valid secret key as html header");
        }

    }

    @DeleteMapping("/user/remove/secretKey/{theKey}")
    public Object RemoveBySecretKey(@PathVariable("theKey") String theKey, @RequestHeader("password") String pass){

        String hashedKey = new StringBuilder(pass).reverse().toString();

        if(repository.existsBySecretKey(hashedKey)){
            // TODO : user passes in two separate working secret keys, but do not belong to same user
            // valid user
            if(repository.existsBySecretKey(theKey)){
                // valid user
                return repository.removeBySecretKey(theKey);
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The key you passed into the url was incorrect");
            }
        }
        else{
            // invalid user
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secretkey you passed into the query string was incorrect");
        }

    }

    @DeleteMapping("/user/remove/password/{thePass}")
    public Object RemoveByPassword(@PathVariable("thePass") String thePass, @RequestHeader("password") String pass){

        String hashedKey = new StringBuilder(pass).reverse().toString();

        if(repository.existsBySecretKey(hashedKey)){
            // valid user
            if(repository.existsByPassword(thePass)){
                // valid user
                return repository.removeByPassword(thePass);
            }
            else{
                // invalid user
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The password you passed into the url was invalid");
            }
        }
        else{
            // invalid user
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The password you passed via request header was invalid");
        }

    }

    @GetMapping("/user/count/{userName}")
    public Object countByUserName(@PathVariable("userName") String userName, @RequestParam(value="password",defaultValue = "nopass") String pass){

        String hashedPass = new StringBuilder(pass).reverse().toString();

        if(repository.existsBySecretKey(hashedPass)){
            // valid user
            if(repository.existsByUserName(userName)){
                // user exists
                return repository.countByUserName(userName);
            }
            else{
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Signature was correct, Username does not exist in database, however");
            }
        }
        else{
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Invalid password passed into request parameter");
        }


    }

    @GetMapping("/user/count/apikey/{theKey}")
    public Object CountByApiKey(@PathVariable("theKey") String theKey, @RequestParam(value="password",defaultValue="nopass") String thePass){

        String hashedPass = new StringBuilder(thePass).reverse().toString();

        if(repository.existsBySecretKey(hashedPass) && !thePass.equalsIgnoreCase("nopass")){

            if(repository.existsByApiKey(theKey)){

                // valid user
                return repository.countByApiKey(theKey);

            }
            else{

                // invalid user
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The API key you passed in via url is invalid");

            }

        }
        else{

            // invalid user
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key passed via query string is invalid");

        }

    }

    @GetMapping("/user/count/secretKey/{theKey}")
    public Object countUserBySecretKey(@PathVariable("theKey") String theKey, @RequestParam(value="password",defaultValue="nopass") String password){

        String hashedPass = new StringBuilder(password).reverse().toString();

        if(repository.existsBySecretKey(password) && !password.equalsIgnoreCase("nopass")){

            if(repository.existsBySecretKey(theKey) && theKey.equals(hashedPass)){

                // valid user
                return repository.countBySecretKey(theKey);

            }
            else{

                // invalid user
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The Secret key passed via url is invalid");

            }

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key passed into the call via query string is invalid");

        }

    }

    @GetMapping("/user/count/password/{thePassword}")
    public Object countUserByPassword(@PathVariable("thePassword") String thePass, @RequestParam(value="password",defaultValue="nopass") String password){

        String hashedPass = new StringBuilder(password).reverse().toString();

        if (repository.existsBySecretKey(hashedPass)) {
            // valid user
            if(repository.existsByPassword(thePass)){
                // valid user
                return repository.countByPassword(thePass);
            }
            else{
                // invalid user
                return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The password passed via url is invalid");
            }
        }
        else{

            // invalid user
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key passed via query string is invalid");
        }

    }



}
