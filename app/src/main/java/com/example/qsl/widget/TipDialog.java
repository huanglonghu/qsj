package com.example.qsl.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.example.qsl.Interface.ClickSureListener;
import com.example.qsl.R;
import com.example.qsl.base.QSLApplication;
import com.example.qsl.databinding.LayoutTipBinding;

public class TipDialog extends Dialog {

    private LayoutTipBinding binding;

    public TipDialog(Context context, ClickSureListener clickSureListener, String title) {
        super(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_tip, null, false);
        binding.setTitle(title);
        binding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                clickSureListener.clickSure();
            }
        });
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        setContentView(binding.getRoot());
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = 8 * QSLApplication.getApplication().getWindownWidth() / 10;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setWindowAnimations(R.style.popupStyle);
        getWindow().setAttributes(layoutParams);
    }


}
