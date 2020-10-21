package com.lymin.nexonlinemarket.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.lymin.nexonlinemarket.R;

import java.util.Objects;

import hari.bounceview.BounceView;

public class DialogX {
    private Context context;
    private TextView tvTitle, tvDesc;
    private TextView btnConfirm, btnCancel;
    private Dialog dialog;
    private LottieAnimationView imageIcon;
    private LottieAnimationView progressBar;
    private LinearLayout lnButton;

    private String title;
    private String description;

    public DialogX(Context context) {
        this.context = context;
        try {
            initialize();
        }catch (Exception e){e.printStackTrace();}

    }

    public void initialize(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR); // before
        dialog.setContentView(R.layout.dialog_x);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        BounceView.addAnimTo(dialog);

        progressBar = dialog.findViewById(R.id.progress_bar);
        imageIcon = dialog.findViewById(R.id.animationView);
        tvTitle = dialog.findViewById(R.id.tv_title);
        tvDesc = dialog.findViewById(R.id.tv_desc);
        btnConfirm = dialog.findViewById(R.id.btn_confirm);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        lnButton = dialog.findViewById(R.id.ln_button_2);

        tvTitle.setText("");
        tvDesc.setText("");

        btnCancel.setOnClickListener(v -> dialog.dismiss());

    }

    public void setTitle(String title){
        this.title = title;
        tvTitle.setText(title);
    }

    public void setMessage(String message){
        this.description = description;
        tvDesc.setText(message);
    }

    public void showX() {
        if (dialog.isShowing()){
            dialog.show();
        }
    }

    public void showSimple(String title, String description){
        progressBar.setVisibility(View.GONE);
        imageIcon.setVisibility(View.GONE);
        lnButton.setVisibility(View.GONE);
        tvDesc.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvDesc.setText(description);

        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    public void showQuestion(String title, String description, OnCallback onCallback){
        progressBar.setVisibility(View.GONE);

//        imageIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_help_outline_24));
//        imageIcon.getDrawable().setTint(context.getResources().getColor(R.color.grey_80));
      //  imageIcon.setAnimationFromUrl("https://assets6.lottiefiles.com/packages/lf20_jlcqWh.json");
        imageIcon.loop(false);
        imageIcon.setAnimation("question.json");
        imageIcon.setVisibility(View.VISIBLE);
        lnButton.setVisibility(View.VISIBLE);
        tvDesc.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvDesc.setText(description);

        btnConfirm.setOnClickListener(v -> {
            onCallback.onConfirmClick();
            dismissX();
                }

        );

        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    public void dismissX(){
        dialog.dismiss();
    }

    public void showError(String title, String description){
        progressBar.setVisibility(View.GONE);
        imageIcon.setAnimation("error.json");
        imageIcon.setVisibility(View.VISIBLE);

        imageIcon.loop(false);
        lnButton.setVisibility(View.GONE);
        tvDesc.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvDesc.setText(description);

        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    public void showWarning(String title, String description, String btnYes, String btnNo, OnCallback onCallback){
        progressBar.setVisibility(View.GONE);

//        imageIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_warning_24));
//        imageIcon.getDrawable().setTint(context.getResources().getColor(R.color.yellow_700));
      //  imageIcon.setAnimationFromUrl("https://assets3.lottiefiles.com/packages/lf20_dVJMow.json");
        imageIcon.setAnimation("warning.json");
        imageIcon.setVisibility(View.VISIBLE);
        lnButton.setVisibility(View.VISIBLE);
        btnConfirm.setText(btnYes);
        btnCancel.setText(btnNo);
        tvDesc.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvDesc.setText(description);

        if (dialog.isShowing()){
            dialog.dismiss();
        }

        btnConfirm.setOnClickListener(v -> {
                    onCallback.onConfirmClick();
                    dismissX();
                }
        );

        dialog.show();

    }
    public void showWarningMsg(String title, String description){
        progressBar.setVisibility(View.GONE);

//        imageIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_warning_24));
//        imageIcon.getDrawable().setTint(context.getResources().getColor(R.color.yellow_700));
     //   imageIcon.setAnimationFromUrl("https://assets3.lottiefiles.com/packages/lf20_dVJMow.json");
        imageIcon.setAnimation("warning.json");
        imageIcon.setVisibility(View.VISIBLE);
        lnButton.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
        btnConfirm.setVisibility(View.VISIBLE);
        btnConfirm.setText("OK");
        tvDesc.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvDesc.setText(description);

        if (dialog.isShowing()){
            dialog.dismiss();
        }

        btnConfirm.setOnClickListener(v -> {
                    dismissX();
                }
        );

        dialog.show();

    }


    public void showSuccess(String title, String description){
        progressBar.setVisibility(View.GONE);
        imageIcon.setAnimation("success.json");
       // imageIcon.setAnimationFromUrl("https://assets7.lottiefiles.com/packages/lf20_rQvUxg.json");
        imageIcon.setVisibility(View.VISIBLE);
//        imageIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_done_outline_24));
//        imageIcon.getDrawable().setTint(context.getResources().getColor(R.color.green_800));

        imageIcon.loop(false);

        lnButton.setVisibility(View.GONE);
        tvDesc.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvDesc.setText(description);

        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        imageIcon.setVisibility(View.GONE);
        lnButton.setVisibility(View.GONE);
        tvDesc.setVisibility(View.GONE);
        tvDesc.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        tvDesc.setText("Please wait");

        if (!dialog.isShowing()){
            dialog.show();
        }

    }

    public void showProgressX(String title){
        progressBar.setVisibility(View.VISIBLE);
        imageIcon.setVisibility(View.GONE);
        lnButton.setVisibility(View.GONE);
        tvDesc.setVisibility(View.GONE);
        tvDesc.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        tvDesc.setText(title);

        dialog.show();
    }


    public interface OnCallback {
        void onConfirmClick();
    }
}
