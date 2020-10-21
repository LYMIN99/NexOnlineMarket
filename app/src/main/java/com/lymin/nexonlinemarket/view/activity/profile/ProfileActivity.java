package com.lymin.nexonlinemarket.view.activity.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.databinding.ActivityProfileBinding;
import com.lymin.nexonlinemarket.managers.UsersManager;
import com.lymin.nexonlinemarket.user.UsersRealm;
import com.lymin.nexonlinemarket.utils.Tools;
import com.lymin.nexonlinemarket.view.BaseActivity;
import com.lymin.nexonlinemarket.view.activity.uploadSale.ChooseCategoriesActivity;
import com.lymin.nexonlinemarket.view.activity.uploadSale.NewSaleActivity;
import com.lymin.nexonlinemarket.view.fragments.OnSaleFragment;
import com.lymin.nexonlinemarket.view.fragments.WishListFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ProfileActivity extends BaseActivity {

    private ActivityProfileBinding binding;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);

        setUpWithViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Tools.setSystemBarColor(this, R.color.grey_10);
        Tools.setSystemBarLight(this);

        binding.btnNewSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, NewSaleActivity.class));
            }
        });

        initData();
    }

    private void initData() {
        Realm.init(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UsersRealm user = new UsersManager().getUserInfo(firebaseUser.getUid());

        binding.username.setText(user.getName());
        binding.about.setText(user.getAbout());

        Glide.with(this).load(user.getProfile()).into(binding.image);

    }


    private void setUpWithViewPager(ViewPager viewPager){
        ProfileActivity.SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment( OnSaleFragment.newInstance(),"On sale");
        adapter.addFragment( WishListFragment.newInstance(),"Wish list");
        viewPager.setAdapter(adapter);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    binding.btnNewSale.setVisibility(View.VISIBLE);
                }else {     binding.btnNewSale.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    //Add this code
    private static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_search :  startActivity(new Intent(ProfileActivity.this, EditInfoActivity.class)); break;
            case android.R.id.home :  finish(); break;
        }

        return super.onOptionsItemSelected(item);
    }

}