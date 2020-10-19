package com.lymin.nexonlinemarket.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lymin.nexonlinemarket.adapter.CountriesListAdapter;
import com.lymin.nexonlinemarket.R;

import java.util.Objects;

import hari.bounceview.BounceView;

public class DialogAllCountryCode {
    private Context context;
    private Dialog dialog;
    private ListView listView;
    private CountriesListAdapter adapter;
    private String[] recourseList;

    public DialogAllCountryCode(Context context) {
        this.context = context;
        initialize();
    }

    public void initialize(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR); // before
        dialog.setContentView(R.layout.dialog_list_all_country);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        BounceView.addAnimTo(dialog);

        listView = dialog.findViewById(R.id.listView);
        recourseList=context.getResources().getStringArray(R.array.CountryCodes);
        adapter = new CountriesListAdapter(context, recourseList);
        listView.setAdapter(adapter);

    }
    public void show(OnCallBack onCallBack){
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] g=recourseList[i].split(",");
                String pngName = g[1].trim().toLowerCase();
                int d = context.getResources().getIdentifier("drawable/" + pngName, null, context.getPackageName());
                onCallBack.onSelected(d,g[0]);
                dialog.dismiss();
            }
        });


    }
    public interface OnCallBack{
        void onSelected(int flag,String code);
    }


}
