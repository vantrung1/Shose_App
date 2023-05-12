package edu.fpt.shose_app.Retrofit;



import edu.fpt.shose_app.Model.FCMRequest;
import edu.fpt.shose_app.Model.NotiResponse;
import edu.fpt.shose_app.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostNotifi {
    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AAAA3KjfRJs:APA91bFow1QjVu60e0qe2mA_7rv9F0yHFpA0X4K-f49MbNGfkgzNrQoGGmRFTLqHWeiolqaNDdMBXXD67NaXUx3IENZU5-opehl2Q5PFKCe3hX4PBhlaxUJmN6fJ2Hw0V8CqCXBO47JR"
    })
    @POST("fcm/send")
    Call<NotiResponse> sendNotification(@Body FCMRequest fcmRequest);
}

