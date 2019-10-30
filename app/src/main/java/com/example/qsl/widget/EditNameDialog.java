package com.example.qsl.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsl.Interface.ClickSureListener;
import com.example.qsl.R;
import com.example.qsl.base.QSLApplication;
import com.example.qsl.databinding.LayoutRemarkBinding;

public class EditNameDialog extends Dialog {

    private LayoutRemarkBinding binding;
    private Context context;

    public EditNameDialog(@NonNull Context context, ClickSureListener clickSureListener) {
        super(context);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_remark, null, false);
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remark = binding.etGroupName.getText().toString();
                if (TextUtils.isEmpty(remark)) {
                    Toast.makeText(context, "请输入备注名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                clickSureListener.click(remark);
                dismiss();


            }
        });
        setContentView(binding.getRoot());
        setCancelable(false);
    }


    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = 4 * QSLApplication.getApplication().getWindownWidth() / 5;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setWindowAnimations(R.style.popupStyle);
        getWindow().setAttributes(layoutParams);
    }


}
