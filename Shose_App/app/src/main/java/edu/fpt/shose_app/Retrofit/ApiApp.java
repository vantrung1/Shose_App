package edu.fpt.shose_app.Retrofit;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.OderRequest;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.ProductRequest;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.Model.addRess_response;
import edu.fpt.shose_app.Model.loginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiApp {

    @POST("register")
    Call<User> postUser(@Body User objUser);

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

    @GET("history/{user}-{status}")
    Call<OderRequest> getOder(@Path("user") int user_id,
                              @Path("status") int status);

    @FormUrlEncoded
    @PUT("oderdetail/{id_oder}")
    Call<Oder> putOder(@Path("id_oder") int id,
                              @Field("status") int status);

    @POST("oderdetail")
    @FormUrlEncoded
    Call<MyResponse> create_oder(@Field("user_id") int user_id,
                                 @Field("address_id") int address_id,
                                 @Field("number") int number,
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
}
