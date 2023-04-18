package edu.fpt.shose_app.Model;

import java.util.ArrayList;

public class OderRequest {
    private String status;
    private ArrayList<Oder> data;

    public OderRequest(String status, ArrayList<Oder> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Oder> getData() {
        return data;
    }

    public void setData(ArrayList<Oder> data) {
        this.data = data;
    }
}
