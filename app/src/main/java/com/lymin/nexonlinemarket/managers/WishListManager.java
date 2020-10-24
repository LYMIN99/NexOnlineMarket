package com.lymin.nexonlinemarket.managers;

import com.lymin.nexonlinemarket.model.WishListItem;
import com.lymin.nexonlinemarket.user.UsersRealm;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class WishListManager {

    private RealmConfiguration config;

    public WishListManager() {
        config = new RealmConfiguration.Builder().name("WishList.realm").deleteRealmIfMigrationNeeded().schemaVersion(1).build();
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

    public void addItem(WishListItem item) {
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
        realm.close();
    }

    public void removeItem(int id){
        Realm realm = Realm.getInstance(config);
        WishListItem usersRealm =  realm.where(WishListItem.class).equalTo("id",id).findFirst();
        if (usersRealm !=null){
            realm.beginTransaction();
            usersRealm.deleteFromRealm();
            realm.commitTransaction();
            realm.close();
        }

    }
    public RealmResults<WishListItem> getList(){
        Realm realm = Realm.getInstance(config);
        return realm.where(WishListItem.class).findAll();
    }
}
