package com.example.qsl.fragment.chat;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;

import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.base.QSLApplication;
import com.example.qsl.util.LogUtil;

import java.util.ArrayList;

public class EmotionKeyboard {
    private static final String SHARE_PREFERENCE_NAME = "EmotionKeyboard";
    private static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height";
    private FragmentManager fm;
    private ArrayList<BaseFragment> fragments;
    private int index1 = -1;
    private boolean isShowSoftInput;
    private Activity mActivity;
    private View mContentView;
    private EditText mEditText;
    private View mEmotionLayout;
    private InputMethodManager mInputManager;
    private SharedPreferences sp;

    private EmotionKeyboard() {
    }

    public static EmotionKeyboard with(Activity activity) {
        EmotionKeyboard emotionInputDetector = new EmotionKeyboard();
        emotionInputDetector.mActivity = activity;
        emotionInputDetector.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        emotionInputDetector.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return emotionInputDetector;
    }

    public EmotionKeyboard bindToContent(View contentView) {
        this.mContentView = contentView;
        return this;
    }

    public EmotionKeyboard fragments(ArrayList<BaseFragment> fragments, FragmentManager fm) {
        this.fragments = fragments;
        this.fm = fm;
        return this;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public EmotionKeyboard bindToEditText(EditText editText) {
        this.mEditText = editText;
        this.mEditText.requestFocus();
        this.mEditText.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1 && EmotionKeyboard.this.mEmotionLayout.isShown()) {
                    hideEmotionLayout(true);
                }
                return false;
            }
        });
        return this;
    }

    public EmotionKeyboard bindToEmotionButton(View emotionButton, final int index) {
        emotionButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!mEmotionLayout.isShown()) {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showEmotionLayout(index);
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout(index);
                    }
                    EmotionKeyboard.this.index1 = index;
                } else if (index == EmotionKeyboard.this.index1) {
                    lockContentHeight();
                    hideEmotionLayout(true);
                    unlockContentHeightDelayed();
                } else {
                    index1 = index;
                    showEmotionLayout(index);
                }
            }
        });
        return this;
    }

    public EmotionKeyboard setEmotionView(View emotionView) {
        this.mEmotionLayout = emotionView;
        return this;
    }

    public EmotionKeyboard build() {
        this.mActivity.getWindow().setSoftInputMode(19);
        setKeyBoardHeightListener();
        hideSoftInput();
        return this;
    }

    public boolean interceptBackPress() {
        if (!this.mEmotionLayout.isShown()) {
            return false;
        }
        hideEmotionLayout(false);
        return true;
    }

    private void showEmotionLayout(int index) {
        int softInputHeight = getKeyBoardHeight();
        hideSoftInput();
        LogUtil.log("==========softInputHeight===========" + softInputHeight);
        this.fm.beginTransaction().replace(R.id.botoomLyoutContainer, (Fragment) this.fragments.get(index)).commit();
        this.mEmotionLayout.getLayoutParams().height = softInputHeight;
        this.mEmotionLayout.setVisibility(View.VISIBLE);
    }

    private void hideEmotionLayout(boolean showSoftInput) {
        if (this.mEmotionLayout.isShown()) {
            this.mEmotionLayout.setVisibility(View.GONE);
            if (this.index1 != -1) {
                this.fm.beginTransaction().remove((Fragment) this.fragments.get(this.index1)).commit();
            }
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    private void lockContentHeight() {
        LayoutParams params = (LayoutParams) this.mContentView.getLayoutParams();
        params.height = this.mContentView.getHeight();
        params.weight = 0.0f;
    }

    private void unlockContentHeightDelayed() {
        this.mEditText.postDelayed(new Runnable() {
            public void run() {
                ((LayoutParams) EmotionKeyboard.this.mContentView.getLayoutParams()).weight = 1.0f;
            }
        }, 200);
    }

    private void showSoftInput() {
        this.mEditText.requestFocus();
        this.mEditText.post(new Runnable() {
            public void run() {
                EmotionKeyboard.this.mInputManager.showSoftInput(EmotionKeyboard.this.mEditText, 0);
            }
        });
    }

    private void hideSoftInput() {
        this.mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private boolean isSoftInputShown() {
        return isShowSoftInput;
    }

    public void setKeyBoardHeightListener() {
        final View decorView = mActivity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int keyboardHeight = QSLApplication.getApplication().getWindowHeight() - rect.bottom;
                if (keyboardHeight > Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    sp.edit().putInt(EmotionKeyboard.SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, keyboardHeight).apply();
                    ((LayoutParams) mEmotionLayout.getLayoutParams()).weight = 0.0f;
                    mEmotionLayout.getLayoutParams().height =getKeyBoardHeight();
                    isShowSoftInput = true;
                    return;
                }
                isShowSoftInput = false;
            }
        });
    }

    @TargetApi(17)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        }
        return 0;
    }

    public int getKeyBoardHeight() {
        return sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 781);
    }
}
