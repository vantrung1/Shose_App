package edu.fpt.shose_app.Model;

import java.io.Serializable;

public class Size implements Serializable {
    private int id;
    private String size;

    public Size(int id, String size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
