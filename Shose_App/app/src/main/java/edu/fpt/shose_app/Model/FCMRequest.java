package edu.fpt.shose_app.Model;

public class FCMRequest {


    private String to;
    private FCMNotification notification;
    // Thêm các trường khác nếu cần

    public FCMRequest() {
    }

    public FCMRequest(String to, FCMNotification notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public FCMNotification getNotification() {
        return notification;
    }

    public void setNotification(FCMNotification notification) {
        this.notification = notification;
    }

    public static class FCMNotification {


        private String title;
        private String body;
        // Thêm các trường khác nếu cần
        public FCMNotification() {
        }
        public FCMNotification(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
// Constructor, getter, setter
    }
}

