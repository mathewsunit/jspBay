package com.jspBay.web.DTO;

import javax.validation.constraints.NotNull;


/**
 * Created by sunit on 3/19/17.
 */

public class UserDTO {

    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String email;

    public UserDTO(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public UserDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public UserDTO(String userName) {
        this.userName = userName;
    }
}
