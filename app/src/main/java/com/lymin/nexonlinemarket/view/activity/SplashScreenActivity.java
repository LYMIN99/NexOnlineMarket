package com.lymin.nexonlinemarket.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.constance.Constants;
import com.lymin.nexonlinemarket.firebases.FirebaseService;
import com.lymin.nexonlinemarket.managers.UsersManager;
import com.lymin.nexonlinemarket.tools.SharePreferenceX;
import com.lymin.nexonlinemarket.user.UsersRealm;
import com.lymin.nexonlinemarket.view.BaseActivity;

import io.realm.Realm;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Realm.init(this);
        FirebaseService.getAllUser();
        if (firebaseUser != null) {

            new SharePreferenceX(SplashScreenActivity.this).savePreferences(Constants.SharePref.currentUserID,firebaseUser.getUid());

            UsersRealm usersRealm = new UsersManager().getUserInfo(firebaseUser.getUid());
            if (usersRealm !=null) {
                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
//                if (usersRealm.getName().equals("")) {
//                    startActivity(new Intent(SplashScreenActivity.this, StepperWizardColor.class));
//                } else {
//                    startActivity(new Intent(SplashScreenActivity.this, MenuDrawerWhite.class));
//                }
            }

        }  else {
            startActivity(new Intent(SplashScreenActivity.this, PhoneLoginActivity.class));
        }


        finish();
    }
}