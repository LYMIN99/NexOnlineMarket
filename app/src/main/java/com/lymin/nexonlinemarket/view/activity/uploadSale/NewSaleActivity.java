package com.lymin.nexonlinemarket.view.activity.uploadSale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.adapter.AddPhotoAdapter;
import com.lymin.nexonlinemarket.databinding.ActivityNewSaleBinding;
import com.lymin.nexonlinemarket.firebases.FirebaseService;
import com.lymin.nexonlinemarket.managers.UsersManager;
import com.lymin.nexonlinemarket.model.PhotoUpload;
import com.lymin.nexonlinemarket.model.SaleItem;
import com.lymin.nexonlinemarket.user.UsersRealm;
import com.lymin.nexonlinemarket.utils.LocationX;
import com.lymin.nexonlinemarket.utils.Tools;
import com.lymin.nexonlinemarket.view.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class NewSaleActivity extends BaseActivity {


    private ActivityNewSaleBinding binding;
    private List<PhotoUpload> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_new_sale);
        Realm.init(this);
        list = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.recyclerView.setAdapter(new AddPhotoAdapter(this,list));

        binding.tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
        loadCategory();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadToFirebase();
            }
        });
    }
    private void loadCategory() {
        List<String> listPriceType;
        ArrayAdapter<String> spinnerAdapter;
        listPriceType = new ArrayList<String>(); // List of Items
        listPriceType.add("Mobile Phones");
        listPriceType.add("Tablets");
        listPriceType.add("Computers Desktop");
        listPriceType.add("Computers Laptop");
        listPriceType.add("Motorcycle or Vehicles");
        listPriceType.add("Bikes");
        listPriceType.add("Cars or Auto");
        listPriceType.add("Electrics");
        listPriceType.add("Clothes");
        listPriceType.add("Houses");
        listPriceType.add("Lands");
        listPriceType.add("Jobs");
        listPriceType.add("Services");
        listPriceType.add("Books");
        listPriceType.add("Games");
        listPriceType.add("Toys");
        listPriceType.add("Sports");
        listPriceType.add("Foods or Restaurants");
        listPriceType.add("Pets");

        spinnerAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, listPriceType);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.categoryUpload.setAdapter(spinnerAdapter);

        getCategory();
        loadQuality();

    }
    private void getCategory(){
        binding.categoryUpload.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0 :
                    case 1 :
                        loadphoneType();
                        condition(true);
                        break;
                    case 2 :
                    case 3 :
                        loadComputerType();
                        condition(true);
                        break;
                    case 4 :
                        loadMotorType();
                        condition(true);
                        break;
                    case 5 :
                        loadservice();
                        condition(false);
                        break;
                    case 6 :
                        loadCarType();
                        condition(true);
                        break;
                    case 7 :
                        loadElectricType();
                        condition(true);
                        break;
                    case 8 :
                        loadClothesType();
                        condition(true);
                        break;
                    case 9 :
                    case 10 :
                        loadHouseAndLands();
                        condition(false);
                        break;
                    case 11 :
                        loadJob();
                        condition(false);
                        break;
                    case 12 :
                        loadservice();
                        break;
                    case 13 :
                        loadservice();
                        break;
                    case 14 :
                        loadGame();
                        break;
                    case 15 :
                        loadservice();
                        break;
                    case 16 :
                        loadservice();
                        break;
                    case 17 :
                        loadFood();
                        condition(false);
                        break;
                    case 18 :
                        loadPet();
                        condition(false);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    private void loadQuality() {
        List<String> listQuality;
        ArrayAdapter<String> SpinnerAdapter;
        listQuality = new ArrayList<String>(); // List of Items
        listQuality.add("Used");
        listQuality.add("New");

        SpinnerAdapter = new ArrayAdapter<String>
                (NewSaleActivity.this, android.R.layout.simple_spinner_item, listQuality);
        SpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.spinnerCondition.setAdapter(SpinnerAdapter);
    }

    private void loadphoneType() {
        //Phone
        List<String> phoneType;
        phoneType = new ArrayList<String>(); // List of Items
        phoneType.add("Apple");
        phoneType.add("Samsung");
        phoneType.add("Huewei");
        phoneType.add("Sony");
        phoneType.add("Oppo");
        phoneType.add("LG");
        phoneType.add("Vivo");
        phoneType.add("Nokia");
        phoneType.add("Meizu");
        phoneType.add("OnePlus");
        phoneType.add("BlackBerry");
        phoneType.add("HTC");
        phoneType.add("Acer");
        phoneType.add("Google");
        phoneType.add("Xiaomi");
        phoneType.add("Motorola");
        phoneType.add("Alcatel");
        phoneType.add("Phillip");
        phoneType.add("SingTech");
        phoneType.add("BLU");
        phoneType.add("Vertu");
        phoneType.add("ZTE");
        phoneType.add("Other");
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, phoneType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadComputerType() {
        //Phone
        List<String> computerType;
        computerType = new ArrayList<String>(); // List of Items
        computerType.add("Apple");
        computerType.add("Acer");
        computerType.add("Asus");
        computerType.add("Averatec");
        computerType.add("Dell");
        computerType.add("Fujitsu");
        computerType.add("Gateway");
        computerType.add("HP");
        computerType.add("Itronix");
        computerType.add("Lenovo");
        computerType.add("Panasonic");
        computerType.add("Msi");
        computerType.add("SamSung");
        computerType.add("Sony");
        computerType.add("ToShiba");
        computerType.add("ViewSonic");
        computerType.add("Alcatel");
        computerType.add("Other");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, computerType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadMotorType() {
        //Phone
        List<String> motorType;
        motorType = new ArrayList<String>(); // List of Items
        motorType.add("Honda");
        motorType.add("Yamaha");
        motorType.add("Suzuki");
        motorType.add("Kawazaki");
        motorType.add("KTM");
        motorType.add("Ducati");
        motorType.add("Bajaj");
        motorType.add("KR Motor");
        motorType.add("GPX");
        motorType.add("Keeway");
        motorType.add("Beneli");
        motorType.add("Stallion");
        motorType.add("BMW");
        motorType.add("Other");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, motorType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadClothesType() {
        List<String> arrayType;
        arrayType = new ArrayList<String>(); // List of Items
        arrayType.add("Kid's Clothing");
        arrayType.add("Men's Clothing");
        arrayType.add("Men's Wallets");
        arrayType.add("Men's Accessories");
        arrayType.add("Men'shoes");
        arrayType.add("Women's Clothing");
        arrayType.add("Women's Accessories");
        arrayType.add("Women's Handbags");
        arrayType.add("Women's shoes");
        arrayType.add("Glass");
        arrayType.add("Others");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, arrayType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadGame() {
        List<String> arrayType;
        arrayType = new ArrayList<String>(); // List of Items
        arrayType.add("Game console");
        arrayType.add("Game account");
        arrayType.add("Accessories");
        arrayType.add("Services");
        arrayType.add("Others");


        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, arrayType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadCarType() {
        List<String> carType;
        carType = new ArrayList<String>(); // List of Items

        carType.add("Toyota");
        carType.add("Lexus");
        carType.add("Mazda");
        carType.add("Hyundai");
        carType.add("KIA");
        carType.add("Honda");
        carType.add("Nissan");
        carType.add("Mercedes-Benz");
        carType.add("Land Rover");
        carType.add("Ford");
        carType.add("Chevrolet");
        carType.add("Porshe");
        carType.add("Mini");
        carType.add("Mitshubishi");
        carType.add("Daihatsu");
        carType.add("Volkswagen");
        carType.add("Subaru");
        carType.add("Jaguar");
        carType.add("Acura");
        carType.add("Alfa Romeo");
        carType.add("Aston Martin");
        carType.add("Audi");
        carType.add("Bentley");
        carType.add("BMW");
        carType.add("Bugati");
        carType.add("Suzuki");
        carType.add("Buick");
        carType.add("Cadillac");
        carType.add("Chrysler");
        carType.add("Chitroen");
        carType.add("Deawoo");
        carType.add("Dodge");
        carType.add("Ferrari");
        carType.add("FIAT");
        carType.add("HUMMER");
        carType.add("Infiniti");
        carType.add("Isuzu");
        carType.add("Jeep");
        carType.add("Lamborghini");
        carType.add("Maserati");
        carType.add("McLaren");
        carType.add("Opel");
        carType.add("Pergeot");
        carType.add("Ram");
        carType.add("Ranault");
        carType.add("Roll-Royce");
        carType.add("saab");
        carType.add("smart");
        carType.add("ssang yong");
        carType.add("Tesla");
        carType.add("Volvo");
        carType.add("Other");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, carType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadElectricType() {
        List<String> electricType;
        electricType = new ArrayList<String>(); // List of Items
        electricType.add("Consumer Electronics");
        electricType.add("Cameras");
        electricType.add("TV, Videos, Audios");
        electricType.add("Home Appliances");
        electricType.add("Other");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, electricType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadHouseAndLands() {
        List<String> hlType;
        hlType = new ArrayList<String>(); // List of Items
        hlType.add("For Rent");
        hlType.add("For Sell");
        hlType.add("Other");


        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, hlType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);

    }

    private void loadJob() {
        List<String> jobType;
        jobType = new ArrayList<String>(); // List of Items
        jobType.add("Full-Time");
        jobType.add("Part-Time");
        jobType.add("Internship");
        jobType.add("FreeLancer");
        jobType.add("Contract");
        jobType.add("Volunteer");
        jobType.add("Other");


        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, jobType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadservice() {
        List<String> arrayType;
        arrayType = new ArrayList<String>(); // List of Items
        arrayType.add("Other");


        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, arrayType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }
    private void loadPet() {
        List<String> arrayType;
        arrayType = new ArrayList<String>(); // List of Items
        arrayType.add("Dogs");
        arrayType.add("Cats");
        arrayType.add("Birds");
        arrayType.add("Fish");
        arrayType.add("Pet Food");
        arrayType.add("Pet Accessories");
        arrayType.add("Other");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, arrayType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void loadFood() {
        List<String> arrayType;
        arrayType = new ArrayList<String>(); // List of Items
        arrayType.add("Restaurants");
        arrayType.add("delivery");
        arrayType.add("Other");

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewSaleActivity.this, android.R.layout.simple_spinner_item, arrayType);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.typeUpload.setAdapter(mAdapter);
    }

    private void condition(boolean b){
        if (b){
            binding.tvCondition.setVisibility(View.VISIBLE);
            binding.spinnerCondition.setVisibility(View.VISIBLE);
        }else {
            binding.tvCondition.setVisibility(View.GONE);
            binding.spinnerCondition.setVisibility(View.GONE);
        }
    }


    private void pickImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        } else {
            Intent i = new Intent();
            i.setType("image/*");
            //i.setType("video/*");
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(i, "android.intent.action.SEND_MULTIPLE"), 222);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==222){
            for (int i=0; i<data.getClipData().getItemCount(); i++){
                Uri uri = data.getClipData().getItemAt(i).getUri();
//                String name = data.getClipData().getItemAt(i).getUri().getPath();
                list.add(new PhotoUpload(i,uri));
            }
        }
        binding.recyclerView.setAdapter(new AddPhotoAdapter(NewSaleActivity.this,list));
    }


    List<String> urlList = new ArrayList<>();
    ProgressDialog progressDialog;
    private void uploadToFirebase() {

        progressDialog = new ProgressDialog(NewSaleActivity.this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        int count =0;
        for (PhotoUpload model : list){
            count++;
           // final String name = manager.getImageName(model.getUri().getPath());
            int finalCount = count;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    Uri uri = null;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(NewSaleActivity.this.getContentResolver(),model.getUri());
                        uri = Tools.compressImageUri(NewSaleActivity.this,bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (uri!=null){

                        progressDialog.setMessage("Uploading... ("+ finalCount +"/"+list.size()+")");

                        FirebaseService.uploadImageToFireBaseStorage(NewSaleActivity.this,uri, new FirebaseService.OnCallBack() {
                            @Override
                            public void onUploadSuccess(String imageUrl) {
                                urlList.add(imageUrl);
                                if (finalCount==list.size()){
                                    submit();
                                }
                            }

                            @Override
                            public void onUploadFailed(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });



        }



    }
    private void submit(){
        String ID = UUID.randomUUID().toString();
        UsersRealm user = new UsersManager().getUserInfo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final LatLng[] mlatLng = {new LatLng(0, 0)};
        final String[] maddress = {"unknown"};
        new LocationX(NewSaleActivity.this).getCurrentLocation(new LocationX.OnGetLatLngCallBack() {
            @Override
            public void onSuccess(String address, LatLng latLng) {
                mlatLng[0] = latLng;
                maddress[0] = address;
            }

            @Override
            public void onPermissionDenied() {
            }
        });

        SaleItem saleItem = new SaleItem(
                ID,
                user.getId(),
                urlList.get(0),
                binding.categoryUpload.getSelectedItem().toString(),
                binding.spinnerCondition.getSelectedItem().toString(),
                binding.typeUpload.getSelectedItem().toString(),
                binding.edName.getText().toString(),
                user.getName(),
                binding.edPrice.getText().toString(),
                Tools.getCurrentDate() ,
                Tools.getCurrentTime(),
                user.getAddress(),
                binding.edDescription.getText().toString(),
                "",
                user.getLatitude(),
                user.getLongitude()

        );

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Products").document(user.getId()).getParent()
                .add(saleItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        for (int i=0; i<urlList.size(); i++){
                            String url = urlList.get(i);
                            int finalI = i+1;
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("Image",url);
                            firestore.collection("Products").document(user.getId()).getParent()
                                    .document(documentReference.getId()).collection("Image").add(hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (finalI ==urlList.size()){
                                        Toast.makeText(getApplicationContext(),"Upload success",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        finish();
                                    }
                                }
                            });
                        }




                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //
                        Toast.makeText(getApplicationContext(),"Error :"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


    }
}