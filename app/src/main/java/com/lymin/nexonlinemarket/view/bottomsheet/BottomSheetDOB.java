package com.lymin.nexonlinemarket.view.bottomsheet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lymin.nexonlinemarket.R;

public class BottomSheetDOB {

//    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
//    private View bottom_sheet;
    private OnCallBack onCallBack;
    private Context context;

    public BottomSheetDOB(Context context,OnCallBack onCallBack) {
        this.context = context;
        this.onCallBack = onCallBack;
        showBottomSheetDialog();
    }

    private void showBottomSheetDialog() {
//        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        }

        final View view = ((Activity)context).getLayoutInflater().inflate(R.layout.sheet_dob, null);


        DatePicker picker = view.findViewById(R.id.datePicker);

        ((View) view.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month = "";
                String day = "";
                if (picker.getDayOfMonth() < 10) {
                    day = "0" + (picker.getDayOfMonth());
                } else {
                    day = String.valueOf((picker.getDayOfMonth()));
                }
                if (picker.getMonth() + 1 < 10) {
                    month = "0" + (picker.getMonth() + 1);
                } else {
                    month = String.valueOf((picker.getMonth() + 1));
                }
                onCallBack.onClick(day + "/" + month + "/" + picker.getYear());
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
        void onClick(String dob);
    }
}
