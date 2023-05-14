package edu.fpt.shose_app.Model;

public class starmodel {
    int id;
    String star;

    public starmodel() {
    }

    public starmodel(int id, String star) {
        this.id = id;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
