package com.jspBay.web.DTO;

import javax.validation.constraints.NotNull;


/**
 * Created by sunit on 3/19/17.
 */

public class NewUserDTO {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String confirmpassword;
    @NotNull
    private String email;

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
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
}
