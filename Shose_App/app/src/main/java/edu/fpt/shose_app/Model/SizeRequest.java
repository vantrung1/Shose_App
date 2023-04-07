package edu.fpt.shose_app.Model;

import java.util.List;

public class SizeRequest {
    private String status;
    private List<SizeQuantity> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SizeQuantity> getData() {
        return data;
    }

    public void setData(List<SizeQuantity> data) {
        this.data = data;
    }

    public SizeRequest(String status, List<SizeQuantity> data) {
        this.status = status;
        this.data = data;
    }

    public class SizeQuantity {
        private String size;
        private String quantity;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
// getters and setters
    }
}
