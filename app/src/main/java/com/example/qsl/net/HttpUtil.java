package com.example.qsl.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;
import com.example.qsl.bean.BankCardBody;
import com.example.qsl.bean.ChangePsw;
import com.example.qsl.bean.EditAvatarBody;
import com.example.qsl.bean.ErrorBody;
import com.example.qsl.bean.JGRegisterBody;
import com.example.qsl.bean.LoginBody;
import com.example.qsl.bean.RegisterBody;
import com.example.qsl.constant.Constants;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.LogUtil;
import com.example.qsl.widget.NetLoading;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static HttpUtil defauleInstance;
    private OkHttpClient client;
    private Context context;
    private HttpInterface httpInterface;
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new Logger() {
        public void log(String message) {
            LogUtil.log(message);
        }
    });
    HashMap<Call<ResponseBody>, NetLoading> map = new HashMap();
    private NetLoading netLoading;
    private Retrofit retrofit;

    private HttpUtil() {
        Interceptor interceptor = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl oldHttpUrl = request.url();
                Builder builder = request.newBuilder();
                String path = oldHttpUrl.encodedPath();
                if (!(path.contains("/api/v1.0/tokenauth/userlogin") || path.contains("/api/v1.0/tokenauth/register"))) {
                    UserBean userBean = UserOption.getInstance().querryUser();
                    if (userBean != null) {
                        String token = userBean.getToken();
                        if (!TextUtils.isEmpty(token)) {
                            request = builder.addHeader("Authorization", "Bearer " + token).build();
                            LogUtil.log("================" + token);
                        }
                    }
                }
                Response response = chain.proceed(request);
                if (response.code() == 401) {
                    if (UserOption.getInstance().querryUser() != null) {
                        UserOption.getInstance().delteUser();
                        UserObservable.getInstance().notifyObservers(new EventData(4));
                    }
                    Presenter.getInstance().step2Fragment("login");
                }
                return response;
            }
        };
        this.loggingInterceptor.setLevel(Level.BODY);
        this.client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).addInterceptor(this.loggingInterceptor).addInterceptor(interceptor).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).build();
        this.retrofit = new Retrofit.Builder().client(this.client).baseUrl(Constants.baseUrl).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        this.httpInterface = (HttpInterface) this.retrofit.create(HttpInterface.class);
    }

    public static HttpUtil getInstance() {
        HttpUtil httpUtil = defauleInstance;
        if (defauleInstance == null) {
            synchronized (HttpUtil.class) {
                httpUtil = new HttpUtil();
                defauleInstance = httpUtil;
            }
        }
        return httpUtil;
    }

    public void init(Context context) {
        this.context = context;
    }

    public Observable<String> login(LoginBody loginBody) {
        return enqueueCall(this.httpInterface.login(loginBody));
    }

    public Observable<String> getCode(String mobile) {
        String sign = getMD5("forgetpwd=false&mobile=" + mobile + "&key=d909274b05fe427a84ccebf02007a743");
        HashMap<String, Object> map = new HashMap();
        map.put("mobile", mobile);
        map.put("forgetPwd", Boolean.valueOf(false));
        map.put("sign", sign);
        return enqueueCall(this.httpInterface.getCode(map));
    }

    public Observable<String> register(RegisterBody registerBody) {
        return enqueueCall(this.httpInterface.register(registerBody));
    }

    public Observable<String> getAllCustomers() {
        return enqueueCall(this.httpInterface.getAllCustomers());
    }

    public Observable<String> getAllUsers() {
        return enqueueCall(this.httpInterface.getAllUsers());
    }

    public Observable<String> registerJG(JGRegisterBody jgRegisterBody) {
        return enqueueCall(this.httpInterface.registerJG(jgRegisterBody));
    }

    public Observable<String> exit() {
        return enqueueCall(this.httpInterface.exit());
    }

    public Observable<String> getVersion() {
        return enqueueCall(this.httpInterface.getVersion());
    }

    public Observable<String> upload(Part parts, String url) {
        return enqueueCall(this.httpInterface.uploadPicture(url, parts));
    }

    public Observable<String> editHead(EditAvatarBody editAvatarBody) {
        return enqueueCall(this.httpInterface.editHead(editAvatarBody));
    }

    public Observable<String> changePsw(ChangePsw changePsw) {
        return enqueueCall(this.httpInterface.editPwd(changePsw));
    }

    public Observable<String> editNickName(String nickName) {
        HashMap<String, Object> map = new HashMap();
        map.put("nickName", nickName);
        map.put("id", Integer.valueOf(Constants.userId));
        return enqueueCall(this.httpInterface.editNickName(map));
    }


    public Observable<String> setRemark(String remark, int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nickName", remark);
        map.put("id", id);
        Call<ResponseBody> call = httpInterface.setRemark(map);
        return enqueueCall(call);
    }

    public Observable<String> getUserCustomer() {
        Call<ResponseBody> call = httpInterface.getUserCustomer();
        return enqueueCall(call);
    }


    public Observable<String> transferUser(int userId, int customerId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("userId", userId);
        map.put("customerId", customerId);
        Call<ResponseBody> call = httpInterface.transferUser(map);
        return enqueueCall(call);
    }

    public Observable<String> getUserMsg(int userId) {
        Call<ResponseBody> call = httpInterface.getUserMsg(userId);
        return enqueueCall(call);
    }

    public Observable<String> getCustomerMsg(int userId) {
        Call<ResponseBody> call = httpInterface.getCustomerMsg(userId);
        return enqueueCall(call);
    }


    public Observable<String> getQrcodeMsg() {
        Call<ResponseBody> call = httpInterface.getQrcodeMsg();
        return enqueueCall(call);
    }


    public Observable<String> createQrcode(String picture, String describe) {
        HashMap<String, String> map = new HashMap<>();
        map.put("picture", picture);
        map.put("describe", describe);
        Call<ResponseBody> call = httpInterface.createQrcode(map);
        return enqueueCall(call);
    }

    public Observable<String> updateQrcode(String picture, String describe, int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("picture", picture);
        map.put("describe", describe);
        map.put("id", id);
        Call<ResponseBody> call = httpInterface.updateQrcode(map);
        return enqueueCall(call);
    }


    public Observable<String> createBankCardMsg(BankCardBody bankCardBody) {
        Call<ResponseBody> call = httpInterface.createBankCardMsg(bankCardBody);
        return enqueueCall(call);
    }

    public Observable<String> updateBankCardMsg(BankCardBody bankCardBody) {
        Call<ResponseBody> call = httpInterface.updateBankCardMsg(bankCardBody);
        return enqueueCall(call);
    }


    public Observable<String> getAllBank() {
        Call<ResponseBody> call = httpInterface.getAllBank();
        return enqueueCall(call);
    }


    public Observable<String> getAllQrcode() {
        Call<ResponseBody> call = httpInterface.getAllQrcode();
        return enqueueCall(call);
    }


    public Observable<String> createChat(int id) {
        return enqueueCall(this.httpInterface.createChat(id));
    }

    public String getMD5(String str) {
        byte[] digest = null;
        try {
            digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        StringBuilder md5 = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            if ((b & 255) < 16) {
                md5.append("0");
            }
            md5.append(Integer.toHexString(b & 255));
        }
        return md5.toString();
    }

    @NonNull
    private Observable<String> enqueueCall(final Call<ResponseBody> call) {
        this.netLoading = new NetLoading(this.context);
        this.netLoading.show();
        this.map.put(call, this.netLoading);
        return Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                HttpUtil.this.a(observableEmitter, call);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    private void a(final ObservableEmitter<String> observableEmitter, final Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (HttpUtil.this.map.get(call) != null) {
                    ((NetLoading) HttpUtil.this.map.get(call)).cancel();
                    HttpUtil.this.map.remove(null);
                }
                try {
                    if (response.isSuccessful()) {
                        String body = ((ResponseBody) response.body()).string();
                        if (!TextUtils.isEmpty(body)) {
                            ErrorBody errorBody = (ErrorBody) GsonUtil.fromJson(body, ErrorBody.class);
                            if (errorBody.getErrcode() != 0) {
                                Toast.makeText(context, errorBody.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        observableEmitter.onNext(body);
                    } else {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(HttpUtil.this.context, "网络异常,请重试", Toast.LENGTH_SHORT).show();
                if (HttpUtil.this.map.get(call) != null) {
                    map.get(call).cancel();
                    map.remove(null);
                }
            }
        });
    }
}
