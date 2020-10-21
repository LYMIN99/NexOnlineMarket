package com.lymin.nexonlinemarket.view.bottomsheet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lymin.nexonlinemarket.R;

public class BottomSheetGender {

//    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
//    private View bottom_sheet;
    private Context context;
    private OnCallBack onCallBack;

    public BottomSheetGender(Context context,OnCallBack onCallBack) {
        this.context = context;
        this.onCallBack = onCallBack;
        showBottomSheetDialog();
    }

    private void showBottomSheetDialog() {
//        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        }

        final View view = ((Activity)context).getLayoutInflater().inflate(R.layout.sheet_gender, null);


        ((View) view.findViewById(R.id.male)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallBack.onClick("Male");
                mBottomSheetDialog.dismiss();
            }
        });


        ((View) view.findViewById(R.id.female)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallBack.onClick("Female");
                mBottomSheetDialog.dismiss();
            }
        });

        ((View) view.findViewById(R.id.other)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallBack.onClick("Other");
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    public interface OnCallBack{
        void onClick(String gender);
    }
}
