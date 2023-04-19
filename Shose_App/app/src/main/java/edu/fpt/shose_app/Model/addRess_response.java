package edu.fpt.shose_app.Model;

import java.util.List;

public class addRess_response {
    private int status;
    private List<address> data;

    public addRess_response(int status, List<address> data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<address> getData() {
        return data;
    }

    public void setData(List<address> data) {
        this.data = data;
    }
}
