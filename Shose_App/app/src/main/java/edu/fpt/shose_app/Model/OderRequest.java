package edu.fpt.shose_app.Model;

import java.util.ArrayList;

public class OderRequest {
    private boolean success;
    private String message;
    private ArrayList<Oder> result;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Oder> getResult() {
        return result;
    }

    public void setResult(ArrayList<Oder> result) {
        this.result = result;
    }
}
