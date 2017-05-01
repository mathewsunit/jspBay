package com.jspBay.application.DTO;

import com.jspBay.application.domain.User;

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

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
    }

    public UserDTO(User user, boolean bool) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public UserDTO(){};
}
