package com.lymin.nexonlinemarket.view.activity.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.databinding.ActivityEditInfoBinding;
import com.lymin.nexonlinemarket.firebases.FirebaseService;
import com.lymin.nexonlinemarket.managers.UsersManager;
import com.lymin.nexonlinemarket.user.UsersModel;
import com.lymin.nexonlinemarket.user.UsersRealm;
import com.lymin.nexonlinemarket.utils.LocationX;
import com.lymin.nexonlinemarket.utils.Tools;
import com.lymin.nexonlinemarket.view.BaseActivity;
import com.lymin.nexonlinemarket.view.bottomsheet.BottomSheetDOB;
import com.lymin.nexonlinemarket.view.bottomsheet.BottomSheetGender;
import com.lymin.nexonlinemarket.view.dialog.DialogX;

import io.realm.Realm;

public class EditInfoActivity extends BaseActivity {

    private ActivityEditInfoBinding binding;
    private LatLng mlatLng ;
    private  FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_info);
        Realm.init(this);
        initToolbar();
        initData();
        initClick();
    }

    private void initData() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UsersRealm user = new UsersManager().getUserInfo(firebaseUser.getUid());

        binding.edFirstName.setText(user.getName());
        binding.edLocation.setText(user.getAddress());
        binding.edGender.setText(user.getGander());
        binding.edDob.setText(user.getDateOfBirth());

    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Tools.setSystemBarColor(this, R.color.grey_10);
        Tools.setSystemBarLight(this);
    }

    private void initClick(){
        binding.edLocation.setOnClickListener(v -> new DialogX(EditInfoActivity.this).showQuestion("Change Location.", "Are you sure you want to change location?", () -> new LocationX(EditInfoActivity.this).getCurrentLocation(new LocationX.OnGetLatLngCallBack() {
            @Override
            public void onSuccess(String address, LatLng latLng) {
                binding.edLocation.setText(address);
                mlatLng = latLng;
            }

            @Override
            public void onPermissionDenied() {

            }
        })));

        binding.edGender.setOnClickListener(v -> new BottomSheetGender(EditInfoActivity.this, gender -> binding.edGender.setText(gender)));

        binding.edDob.setOnClickListener(v -> new BottomSheetDOB(EditInfoActivity.this, dob -> binding.edDob.setText(dob)));



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_done :  saveInfo(); break;
            case android.R.id.home :  finish(); break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveInfo() {

        if (isValid()){
            DialogX dialogX = new DialogX(EditInfoActivity.this);
            dialogX.showProgress();

            new Handler().post(() -> {

        UsersRealm userR = new UsersManager().getUserInfo(firebaseUser.getUid());
        UsersModel user = new UsersModel(
                userR.getId(),
                binding.edFirstName.getText().toString()+" "+binding.edLastName.getText().toString(),
                userR.getProfile(),
                binding.edLocation.getText().toString(),
                binding.edGender.getText().toString(),
                binding.edDob.getText().toString(),
                userR.getAbout(),
                userR.getEmail(),
                "",
                mlatLng.latitude,
                mlatLng.longitude
                );

        FirebaseService.updateUser(user);

        UsersRealm userX = new UsersRealm(
                userR.getId(),
                binding.edFirstName.getText().toString()+" "+binding.edLastName.getText().toString(),
                userR.getProfile(),
                binding.edLocation.getText().toString(),
                binding.edGender.getText().toString(),
                binding.edDob.getText().toString(),
                userR.getAbout(),
                userR.getEmail(),
                "",
                mlatLng.latitude,
                mlatLng.longitude
        );

        new UsersManager().addUser(userX);
        dialogX.dismissX();
        finish();

            });

        }
    }
    private boolean isValid(){
        int b = 0;
        if (TextUtils.isEmpty(binding.edFirstName.getText().toString())){
            binding.edFirstName.setError("Please input first name");
            b++;
        }
        if (TextUtils.isEmpty(binding.edLastName.getText().toString())){
            binding.edFirstName.setError("Please input last name");
            b++;
        }
        if (TextUtils.isEmpty(binding.edPhone1.getText().toString())){
            binding.edFirstName.setError("Please input phone number");
            b++;
        }

        return b == 0;
    }


}