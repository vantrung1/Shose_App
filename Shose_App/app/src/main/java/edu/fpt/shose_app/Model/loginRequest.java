package edu.fpt.shose_app.Model;

import java.util.List;

public class loginRequest {
    private String status;
    private String message;
    private User data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setUser(User user) {
        this.data = user;
    }

    public loginRequest(String status, String message, User user) {
        this.status = status;
        this.message = message;
        this.data = user;
    }
}
