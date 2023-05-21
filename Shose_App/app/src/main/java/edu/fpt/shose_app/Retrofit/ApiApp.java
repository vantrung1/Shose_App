package edu.fpt.shose_app.Retrofit;

import java.util.ArrayList;
import java.util.List;

import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.FeedBack;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.OderRequest;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.ProductRequest;
import edu.fpt.shose_app.Model.RatingModel;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.Model.addRess_response;
import edu.fpt.shose_app.Model.address;
import edu.fpt.shose_app.Model.loginRequest;
import edu.fpt.shose_app.Model.serverRepest;
import edu.fpt.shose_app.Utils.Const;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiApp {

    @GET("search")
    Call<List<Product>> searchProducts(@Query("q") String query);

    @GET("products/{id}")
    Call<List<Product>> getProduct(@Path("id") int id);

    @POST("register")
    Call<serverRepest> postUser(@Body User objUser);

    @GET("brands")
    Call<ArrayList<Brand>> getAllBrand();

    @GET("products/branch/{id}")
    Call<ProductRequest> getApiProductById(@Path("id") int id);

    @GET("sizes/product/{id}")
    Call<SizeRequest> getQuantitySize(@Path("id") int id);

    @POST("login")
    @FormUrlEncoded
    Call<loginRequest> _logGin(@Field("email") String email,
                               @Field("password") String password);

    @GET("history/{user}/{status}")
    Call<OderRequest> getOder(@Path("user") int user_id,
                              @Path("status") int status);

    @FormUrlEncoded
    @PUT("oderdetail/{oder_id}")
    Call<Oder> putOder(@Path("oder_id") int id,
                       @Field("status") int status);
    @FormUrlEncoded
    @PUT("oderdetail/{oder_id}")
    Call<Oder> huydon(@Path("oder_id") int id,
                       @Field("status") int status,
                      @Field("note") String note);

    @POST("oderdetail")
    @FormUrlEncoded
    Call<MyResponse> create_oder(@Field("user_id") int user_id,
                                 @Field("address_id") String address_id,
                                 @Field("number") String number,
                                 @Field("total") String total,
                                 @Field("note") String note,
                                 @Field("paymentAmount") String paymentAmount,
                                 @Field("status") String status,
                                 @Field("products") String products,
                                 @Field("quantity") int quantity);

    public class MyResponse {
        private String status;
        private String message;
        private String id_oder;

        public MyResponse(String status, String message, String id_oder) {
            this.status = status;
            this.message = message;
            this.id_oder = id_oder;
        }

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

        public String getId_oder() {
            return id_oder;
        }

        public void setId_oder(String id_oder) {
            this.id_oder = id_oder;
        }
    }

    @GET("address/{user_id}")
    Call<addRess_response> getallAdess(@Path("user_id") int user_id);

    @GET("products")
    Call<ArrayList<Product>> getallProduct();

    @POST("send-otp")
    @FormUrlEncoded
    Call<User> _forgotpassword(@Field("email") String email);

    @Multipart
    @POST("ratings")
    Call<FeedBack> _feedBack(@Part(Const.KEY_USER_ID) RequestBody user_id,
                             @Part(Const.KEY_PRODUCT_ID) RequestBody product_id,
                             @Part(Const.KEY_STAR) RequestBody star,
                             @Part List<MultipartBody.Part> image,
                             @Part(Const.KEY_CONTENT) RequestBody content
    );
    @PUT("users/{id}")
    Call<loginRequest> _updateUser(@Path("id") int id,@Body User user);
    @POST("address")
    @FormUrlEncoded
    Call<serverRepest> themdiachi(@Field("user_id")int id,@Field("desc") String de);
    @GET("ratings/{id}")

    Call<RatingModel> getRating(@Path("id") String id);

    @PUT("users/{id}")
    @FormUrlEncoded
    Call<loginRequest> _updateUserPhone(@Path("id") int id,@Field("phoneNumber") String user);
    @GET("oder_status/{status}")
    Call<OderRequest> getOderAll(@Path("status") int status);
}
