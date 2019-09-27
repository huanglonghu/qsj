package com.example.qsl.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.qsl.bean.ChangePsw;
import com.example.qsl.bean.EditAvatarBody;
import com.example.qsl.bean.ErrorBody;
import com.example.qsl.bean.JGRegisterBody;
import com.example.qsl.bean.LoginBody;
import com.example.qsl.bean.RegisterBody;
import com.example.qsl.constant.Constants;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.LogUtil;
import com.example.qsl.widget.NetLoading;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private HttpInterface httpInterface;
    private HttpLoggingInterceptor loggingInterceptor;
    private Retrofit retrofit;
    private OkHttpClient client;
    private NetLoading netLoading;

    private HttpUtil() {
        //打印retrofit日志
        loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                LogUtil.log(message);
            }
        });

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl oldHttpUrl = request.url();
                Request.Builder builder = request.newBuilder();
                String path = oldHttpUrl.encodedPath();
                if (!path.contains("/api/v1.0/tokenauth/userlogin") && !path.contains("/api/v1.0/tokenauth/register")) {
                    UserBean userBean = UserOption.getInstance().querryUser();
                    if (userBean != null) {
                        String token = userBean.getToken();
                        if (!TextUtils.isEmpty(token)) {
                            request = builder.addHeader("Authorization", "Bearer " + token).build();
                            LogUtil.log("================" + token);
                        }
                    }
                }
                okhttp3.Response response = chain.proceed(request);
                if (response.code() == 401) {
                    UserBean userBean = UserOption.getInstance().querryUser();
                    if (userBean != null) {
                        UserOption.getInstance().delteUser();
                        //UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_LOGINOUT));
                    }
                    Presenter.getInstance().step2Fragment("login");
                }

                return response;


            }
        };
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).addInterceptor(interceptor)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder().client(client).baseUrl(Constants.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        httpInterface = retrofit.create(HttpInterface.class);
    }

    private static HttpUtil defauleInstance;

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

    private Context context;

    public void init(Context context) {
        this.context = context;
    }


    public Observable<String> login(LoginBody loginBody) {
        Call<ResponseBody> call = httpInterface.login(loginBody);
        return enqueueCall(call);
    }

    public Observable<String> getCode(String mobile) {
        String s = "forgetpwd=" + false + "&mobile=" + mobile + "&key=" + "d909274b05fe427a84ccebf02007a743";
        String sign = getMD5(s);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("forgetPwd", false);
        map.put("sign", sign);
        Call<ResponseBody> call = httpInterface.getCode(map);
        return enqueueCall(call);
    }

    public Observable<String> register(RegisterBody registerBody) {
        Call<ResponseBody> call = httpInterface.register(registerBody);
        return enqueueCall(call);
    }

    public Observable<String> getAllCustomers() {
        Call<ResponseBody> call = httpInterface.getAllCustomers();
        return enqueueCall(call);
    }

    public Observable<String> getAllUsers() {
        Call<ResponseBody> call = httpInterface.getAllUsers();
        return enqueueCall(call);
    }

    public Observable<String> registerJG(JGRegisterBody jgRegisterBody) {
        Call<ResponseBody> call = httpInterface.registerJG(jgRegisterBody);
        return enqueueCall(call);
    }


    public Observable<String> exit() {
        Call<ResponseBody> call = httpInterface.exit();
        return enqueueCall(call);
    }


    public Observable<String> getVersion() {
        Call<ResponseBody> call = httpInterface.getVersion();
        return enqueueCall(call);
    }


    public Observable<String> upload(MultipartBody.Part parts, String url) {
        Call<ResponseBody> call = httpInterface.uploadPicture(url, parts);
        return enqueueCall(call);
    }


    public Observable<String> editHead(EditAvatarBody editAvatarBody) {
        Call<ResponseBody> call = httpInterface.editHead(editAvatarBody);
        return enqueueCall(call);
    }

    public Observable<String> changePsw(ChangePsw changePsw) {
        Call<ResponseBody> call = httpInterface.editPwd(changePsw);
        return enqueueCall(call);
    }


    public Observable<String> editNickName(String nickName) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nickName", nickName);
        map.put("id", Constants.userId);
        Call<ResponseBody> call = httpInterface.editNickName(map);
        return enqueueCall(call);
    }


    public Observable<String> createChat(int id) {
        Call<ResponseBody> call = httpInterface.createChat(id);
        return enqueueCall(call);
    }


    public String getMD5(String str) {
        byte[] digest = null;
        try {
            digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder md5 = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            if ((b & 0xFF) < 0x10)
                md5.append("0");
            md5.append(Integer.toHexString(b & 0xFF));
        }
        return md5.toString();
    }


    HashMap<Call<ResponseBody>, NetLoading> map = new HashMap<>();

    @NonNull
    private Observable<String> enqueueCall(final Call<ResponseBody> call) {
        netLoading = new NetLoading(context);
        netLoading.show();
        map.put(call, netLoading);
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                a(observableEmitter, call);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    private void a(final ObservableEmitter<String> observableEmitter, final Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                if (map.get(call) != null) {
                    NetLoading netLoading = map.get(call);
                    netLoading.cancel();
                    netLoading = null;
                    map.remove(netLoading);
                }
                //  {"Errcode":1002,"Message":"用户名或密码错误！","Data":null}
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        if (!TextUtils.isEmpty(body)) {
                            ErrorBody errorBody = GsonUtil.fromJson(body, ErrorBody.class);
                            int errcode = errorBody.getErrcode();
                            if (errcode != 0) {
                                Toast.makeText(context, errorBody.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        observableEmitter.onNext(body);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call1, Throwable t) {
                Toast.makeText(context, "网络异常,请重试", Toast.LENGTH_SHORT).show();
                if (map.get(call) != null) {
                    NetLoading netLoading = map.get(call);
                    netLoading.cancel();
                    netLoading = null;
                    map.remove(netLoading);
                }
            }
        });
    }


}
