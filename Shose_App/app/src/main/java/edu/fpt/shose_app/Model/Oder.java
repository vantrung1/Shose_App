package edu.fpt.shose_app.Model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Oder implements Serializable {
    private String name;
    private int id;
    private int user_id;

    private String address_id;
    private String number;
    private String total;
    private String note;
    private String paymentAmount;
    private int status;
    private String created_at;
    private String updated_at;
    private int oder_id;
    private List<Products_Oder> products;
    private int quantity;

    public Oder(String name, int id, int user_id, String address_id, String number, String total, String note, String paymentAmount, int status, String created_at, String updated_at, int oder_id, List<Products_Oder> products, int quantity) {
        this.name = name;
        this.id = id;
        this.user_id = user_id;
        this.address_id = address_id;
        this.number = number;
        this.total = total;
        this.note = note;
        this.paymentAmount = paymentAmount;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.oder_id = oder_id;
        this.products = products;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getOder_id() {
        return oder_id;
    }

    public void setOder_id(int oder_id) {
        this.oder_id = oder_id;
    }

    public List<Products_Oder> getProducts() {
        return products;
    }

    public void setProducts(List<Products_Oder> products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getTimeUTC(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime utcDateTime = OffsetDateTime.parse(getUpdated_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            // Đặt múi giờ của Việt Nam
            ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");

            // Chuyển đổi múi giờ từ UTC sang Việt Nam
            ZonedDateTime vietnamDateTime = utcDateTime.atZoneSameInstant(vietnamZone);

            // Định dạng đầu ra
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Hiển thị thời gian ở múi giờ Việt Nam
            String vietnamTime = vietnamDateTime.format(formatter);
            System.out.println("Thời gian ở múi giờ Việt Nam: " + vietnamTime);
            return vietnamTime;
        }
        return "";
    }
    public String getTimeUTCCreate(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime utcDateTime = OffsetDateTime.parse(getCreated_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            // Đặt múi giờ của Việt Nam
            ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");

            // Chuyển đổi múi giờ từ UTC sang Việt Nam
            ZonedDateTime vietnamDateTime = utcDateTime.atZoneSameInstant(vietnamZone);

            // Định dạng đầu ra
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Hiển thị thời gian ở múi giờ Việt Nam
            String vietnamTime = vietnamDateTime.format(formatter);
            System.out.println("Thời gian ở múi giờ Việt Nam: " + vietnamTime);
            return vietnamTime;
        }
        return "";
    }
}
