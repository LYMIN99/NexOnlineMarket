package com.lymin.nexonlinemarket.utils;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationX {

    private LatLng myLatLng;
    private Context context;
    private ProgressDialog dialogX;
    public LocationX(Context context) {
        this.context = context;
        dialogX = new ProgressDialog(context);
    }

    //Check if internet is present or not
    public void getCurrentLocation(OnGetLatLngCallBack onCallBack) {
        dialogX.setMessage("Getting current location...");
        dialogX.show();
        // final LatLng[] myLatLng = {null};
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            onCallBack.onPermissionDenied();
            dialogX.dismiss();
        } else {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager!=null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
                Log.d("GetMyLatLng", "GPS_PROVIDER: OFF ");
            }
            else {
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
                fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        // Logic to handle location object
                        myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        String myAddress =  getAddress(location.getLatitude(), location.getLongitude());
                        onCallBack.onSuccess(myAddress,myLatLng);

                    }
                });
            }
            dialogX.dismiss();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("No", (dialog, id) -> {dialog.cancel();});
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String address1 ="";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size()!=0) {
                Address obj = addresses.get(0);
                address1 = obj.getAddressLine(0);
            }

            //   Log.d(TAG, "getAddress: "+add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return address1;
    }

    public interface OnGetLatLngCallBack{
        void onSuccess(String address,LatLng latLng);

        void onSuccess(String address, com.google.type.LatLng latLng);

        void onPermissionDenied();
    }
}
