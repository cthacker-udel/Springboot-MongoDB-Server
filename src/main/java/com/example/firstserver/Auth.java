package com.example.firstserver;

public class Auth {

    /*

    Testing : Basic test first, secret key will be passed in reverse, then we will re-reverse it, and check to make sure it matches up

     */

    private String signature;

    public Auth(String passedSignature){

        this.signature = passedSignature;

    }



}
