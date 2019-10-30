package com.example.qsl.net;

import com.example.qsl.bean.BankCardBody;
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
    @GET("/api/v1.0/customerofuser/create")
    Call<ResponseBody> createChat(@Query("id") int i);

    @POST("/api/v1.0/user/updateavatar")
    Call<ResponseBody> editHead(@Body EditAvatarBody editAvatarBody);

    @POST("/api/v1.0/user/updatenickname")
    Call<ResponseBody> editNickName(@Body HashMap<String, Object> hashMap);

    @POST("/api/v1.0/user/updatepassword")
    Call<ResponseBody> editPwd(@Body ChangePsw changePsw);

    @GET("/api/v1.0/tokenauth/signout")
    Call<ResponseBody> exit();

    @GET("/api/v1.0/customer/getalllist")
    Call<ResponseBody> getAllCustomers();

    @GET("/api/v1.0/customerofuser/getalllist")
    Call<ResponseBody> getAllUsers();

    @POST("/api/v1.0/sms/sendcodebymobile")
    Call<ResponseBody> getCode(@Body HashMap<String, Object> hashMap);

    @GET("/android.json")
    Call<ResponseBody> getVersion();

    @POST("/api/v1.0/tokenauth/userlogin")
    Call<ResponseBody> login(@Body LoginBody loginBody);

    @POST("/api/v1.0/tokenauth/register")
    Call<ResponseBody> register(@Body RegisterBody registerBody);

    @POST("/api/v1.0/user/jgregister")
    Call<ResponseBody> registerJG(@Body JGRegisterBody jGRegisterBody);

    @POST("/api/v1.0/user/uploadavatar")
    @Multipart
    Call<ResponseBody> uploadPicture(@Query("url") String str, @Part MultipartBody.Part part);

    @POST("/api/v1.0/customerofuser/setusernickname")
    Call<ResponseBody> setRemark(@Body HashMap<String, Object> map);


    @GET("/api/v1.0/customer/getusercustomer")
    Call<ResponseBody> getUserCustomer();

    @POST("/api/v1.0/customerofuser/updateusercustomer")
    Call<ResponseBody> transferUser(@Body HashMap<String, Integer> map);


    @GET("/api/v1.0/user/get")
    Call<ResponseBody> getUserMsg(@Query("id") int id);

    @GET("/api/v1.0/customer/get")
    Call<ResponseBody> getCustomerMsg(@Query("id") int id);


    @GET("/api/v1.0/customer/getcustomercollectioninfo")
    Call<ResponseBody> getQrcodeMsg();


    @POST("/api/v1.0/qrcode/create")
    Call<ResponseBody> createQrcode(@Body HashMap<String, String> map);

    @POST("/api/v1.0/qrcode/update")
    Call<ResponseBody> updateQrcode(@Body HashMap<String, Object> map);


    @POST("/api/v1.0/bankcard/create")
    Call<ResponseBody> createBankCardMsg(@Body BankCardBody bankCardBody);

    @POST("/api/v1.0/bankcard/update")
    Call<ResponseBody> updateBankCardMsg(@Body BankCardBody bankCardBody);

    @POST("/api/v1.0/bankcard/getalllist")
    Call<ResponseBody> getAllBank();

    @POST("/api/v1.0/qrcode/getalllist")
    Call<ResponseBody> getAllQrcode();


}
