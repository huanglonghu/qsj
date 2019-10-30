# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#实体类
-keep class com.example.qsl.bean.**{*;}
-keep class com.example.qsl.database.entity.**{*;}
#自定义控件
-keep class com.example.qsl.ui.customview.**{*;}
-keepclassmembers class **.R$* {
    public static <fields>;
}
-dontwarn com.android.support**
-keep class com.android.support.**{*;}

#---------------------------------2.第三方库---------------------------------
-dontwarn java.lang.invoke.**
## ---------Retrofit混淆方法---------------
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-keep public interface com.example.qsl.net.HttpInterface{ *; }

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod


#com.jakewharton:disklrucache
-keep class com.jakewharton.**{*;}
-dontwarn com.jakewharton.**


# greendao
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**


#lambda
-dontwarn net.orfjackal.retrolambda**
-keep class net.orfjackal.retrolambda.**{*;}

#com.yanzhenjie:permission
-keep class com.yanzhenjie.**{*;}
-dontwarn com.yanzhenjie.**


#me.zhanghai.android.materialratingbar
-keep class me.zhanghai.android.materialratingbar.**{*;}
-dontwarn me.zhanghai.android.materialratingbar.**


#com.meiqia
-keep class com.meiqia.**{*;}
-dontwarn com.meiqia.**

#com.danikula:videocache
-keep class com.danikula.**{*;}
-dontwarn com.danikula.**


#com.nostra13.universalimageloader
-keep class com.nostra13.universalimageloader.**{*;}
-dontwarn com.nostra13.universalimageloader.**

#wechat
-keep class com.tencent.mm.opensdk.** {
*;
}
-keep class com.tencent.wxop.** {
*;
}
-keep class com.tencent.mm.sdk.** {
*;
}


#facebook
-dontwarn com.facebook.**
-keep class com.facebook.** {*;}
-keep enum com.facebook.**
-keep public interface com.facebook.**

#com.ogaclejapan.smarttablayout
-keep class com.ogaclejapan.smarttablayout.**{*;}
-dontwarn com.ogaclejapan.smarttablayout.**


##me.himanshusoni.chatmessageview
#-keep class me.himanshusoni.chatmessageview.**{*;}
#-dontwarn me.himanshusoni.chatmessageview.**

#百度地图
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**

#webview
-keepattributes Annotation
-keepattributes JavascriptInterface
-keepclassmembers class * {
@android.webkit.JavascriptInterface <methods>;
}
#这个根据自己的project来设置，这个类用来与js交互，所以这个类中的 字段 ，方法， 等尽量保持
-keepclassmembers public class com.example.qsl.ui.customview.URLDrawable{
<fields>;
<methods>;
public *;
private *;
}


-dontoptimize
-dontpreverify
-keepattributes  EnclosingMethod,Signature
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

-dontwarn cn.jmessage.**
-keep class cn.jmessage.**{ *; }

-keepclassmembers class ** {
    public void onEvent*(**);
}

#========================protobuf================================
-keep class com.google.protobuf.** {*;}