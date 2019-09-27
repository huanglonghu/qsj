package com.example.qsl.net;

import com.example.qsl.bean.ChangePsw;
import com.example.qsl.bean.EditAvatarBody;
import com.example.qsl.bean.JGRegisterBody;
import com.example.qsl.bean.LoginBody;
import com.example.qsl.bean.RegisterBody;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface HttpInterface {

    @POST("/api/v1.0/tokenauth/userlogin")
    Call<ResponseBody> login(@Body LoginBody body);

    @POST("/api/v1.0/sms/sendcodebymobile")
    Call<ResponseBody> getCode(@Body HashMap<String, Object> map);

    @POST("/api/v1.0/tokenauth/register")
    Call<ResponseBody> register(@Body RegisterBody registerBody);

    @GET("/api/v1.0/customer/getalllist")
    Call<ResponseBody> getAllCustomers();

    @GET("/api/v1.0/customerofuser/getalllist")
    Call<ResponseBody> getAllUsers();

    @POST("/api/v1.0/user/jgregister")
    Call<ResponseBody> registerJG(@Body JGRegisterBody jgRegisterBody);


    @GET("/api/v1.0/tokenauth/signout")
    Call<ResponseBody> exit();

    @GET("/android.json")
    Call<ResponseBody> getVersion();

    @Multipart
    @POST("/api/v1.0/user/uploadavatar")
        //1产品图片 2 用户头像 3产品类别图片
    Call<ResponseBody> uploadPicture(@Query("url") String imgurl, @Part MultipartBody.Part file);


    @POST("/api/v1.0/user/updateavatar")
    Call<ResponseBody> editHead(@Body EditAvatarBody editAvatarBody);

    @POST("/api/v1.0/user/updatepassword")
    Call<ResponseBody> editPwd(@Body ChangePsw changePsw);


    @POST("/api/v1.0/user/updatenickname")
    Call<ResponseBody> editNickName(@Body HashMap<String, Object> map);

    @GET("/api/v1.0/customerofuser/create")
    Call<ResponseBody> createChat(@Query("id") int id);


}