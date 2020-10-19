package com.lymin.nexonlinemarket.managers;

import com.lymin.nexonlinemarket.user.UsersRealm;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class UsersManager {

    private RealmConfiguration config;

    public UsersManager() {
        config = new RealmConfiguration.Builder().name("Users.realm").deleteRealmIfMigrationNeeded().schemaVersion(1).build();
        Realm.setDefaultConfiguration(config);
    }

    public void clearAllData() {
        Realm realm = Realm.getInstance(config);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public void addUser(UsersRealm item) {
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
        realm.close();
    }


    public void deleteUser(int id){
        Realm realm = Realm.getInstance(config);
        UsersRealm usersRealm =  realm.where(UsersRealm.class).equalTo("id",id).findFirst();
        if (usersRealm !=null){
            realm.beginTransaction();
            usersRealm.deleteFromRealm();
            realm.commitTransaction();
            realm.close();
        }

    }
    public RealmResults<UsersRealm> getUserList(){
        Realm realm = Realm.getInstance(config);
        return realm.where(UsersRealm.class).findAll();
    }

    public void setName(String name, String id){
        Realm realm = Realm.getInstance(config);
        UsersRealm user = realm.where(UsersRealm.class).equalTo("id",id).findFirst();
        if (user!=null){
            realm.beginTransaction();
            user.setName(name);
            realm.commitTransaction();
            realm.close();
        }
    }
    public void setGander(String gander, String id){
        Realm realm = Realm.getInstance(config);
        UsersRealm user = realm.where(UsersRealm.class).equalTo("id",id).findFirst();
        if (user!=null){
            realm.beginTransaction();
            user.setGander(gander);
            realm.commitTransaction();
            realm.close();
        }
    }
    public void setImageProfile(String profile, String id){
        Realm realm = Realm.getInstance(config);
        UsersRealm user = realm.where(UsersRealm.class).equalTo("id",id).findFirst();
        if (user!=null){
            realm.beginTransaction();
            user.setProfile(profile);
            realm.commitTransaction();
            realm.close();
        }
    }

    public void setLocation(String address,double latitude, double longitude, String id){
        Realm realm = Realm.getInstance(config);
        UsersRealm user = realm.where(UsersRealm.class).equalTo("id",id).findFirst();
        if (user!=null){
            realm.beginTransaction();
            user.setAddress(address);
            user.setLatitude(latitude);
            user.setLongitude(longitude);
            realm.commitTransaction();
            realm.close();
        }
    }

    public UsersRealm getUserInfo(String id){
        Realm realm = Realm.getInstance(config);
        return realm.where(UsersRealm.class).equalTo("id",id).findFirst();
    }

}
