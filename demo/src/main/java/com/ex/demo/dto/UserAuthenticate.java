package com.ex.demo.dto;


/**
 * Represents authentication request
 */
public class UserAuthenticate {

    private String userType;

    private String username;

    private String userpass;


    public UserAuthenticate() {
    }

    public UserAuthenticate(String username, String userpass) {
        this.username = username;
        this.userpass = userpass;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return this.userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getUserType(){
        return this.userType;
    }

    public void setUserType (String userType){
        this.userType = userType;
    }

}
