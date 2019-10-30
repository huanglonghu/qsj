package com.example.qsl.util;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import java.lang.reflect.Field;

public class RudenessScreenHelper {
    private ActivityLifecycleCallbacks activityLifecycleCallbacks;
    private float designWidth = 720.0f;
    private Application mApplication;

    public static void resetDensity(Context context, float designWidth) {
        if (context != null) {
            Point size = new Point();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
            context.getResources().getDisplayMetrics().xdpi = (((float) size.x) / designWidth) * 72.0f;
            DisplayMetrics metrics = getMetricsOnMiui(context.getResources());
            if (metrics != null) {
                metrics.xdpi = (((float) size.x) / designWidth) * 72.0f;
            }
        }
    }

    public static void restoreDensity(Context context) {
        context.getResources().getDisplayMetrics().setToDefaults();
        DisplayMetrics metrics = getMetricsOnMiui(context.getResources());
        if (metrics != null) {
            metrics.setToDefaults();
        }
    }

    private static DisplayMetrics getMetricsOnMiui(Resources resources) {
        if (!"MiuiResources".equals(resources.getClass().getSimpleName()) && !"XResources".equals(resources.getClass().getSimpleName())) {
            return null;
        }
        try {
            Field field = Resources.class.getDeclaredField("mTmpMetrics");
            field.setAccessible(true);
            return (DisplayMetrics) field.get(resources);
        } catch (Exception e) {
            return null;
        }
    }

    public static float dp2px(Context context, float value) {
        return TypedValue.applyDimension(1, value, context.getResources().getDisplayMetrics());
    }

    public static float pt2px(Context context, float value) {
        return TypedValue.applyDimension(3, value, context.getResources().getDisplayMetrics());
    }

    public RudenessScreenHelper(Application application, float width) {
        this.mApplication = application;
        this.designWidth = width;
        this.activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                RudenessScreenHelper.resetDensity(RudenessScreenHelper.this.mApplication, RudenessScreenHelper.this.designWidth);
                RudenessScreenHelper.resetDensity(activity, RudenessScreenHelper.this.designWidth);
            }

            public void onActivityStarted(Activity activity) {
                RudenessScreenHelper.resetDensity(RudenessScreenHelper.this.mApplication, RudenessScreenHelper.this.designWidth);
                RudenessScreenHelper.resetDensity(activity, RudenessScreenHelper.this.designWidth);
            }

            public void onActivityResumed(Activity activity) {
                RudenessScreenHelper.resetDensity(RudenessScreenHelper.this.mApplication, RudenessScreenHelper.this.designWidth);
                RudenessScreenHelper.resetDensity(activity, RudenessScreenHelper.this.designWidth);
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            public void onActivityDestroyed(Activity activity) {
            }
        };
    }

    public void activate() {
        resetDensity(this.mApplication, this.designWidth);
        this.mApplication.registerActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
    }

    public void inactivate() {
        restoreDensity(this.mApplication);
        this.mApplication.unregisterActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
    }
}
