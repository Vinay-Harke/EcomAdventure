package com.example.ecomadventure;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DaoUser {

    private final DatabaseReference databaseReference;

    public DaoUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    public Task<Void> add(User user) {

        return databaseReference.child(user.getName()).setValue(user);
    }

    public Task<Void> addFavorites(Favorite favObj,String childName) {
        String tempChild = childName.toLowerCase().replaceAll("\\s", "");
        return databaseReference.child(favObj.getName()).child(Favorite.class.getSimpleName()).child(tempChild).setValue(favObj);
    }

    public Task<Void> removeFavorite(String username,String childName){
        return databaseReference.child(username).child(Favorite.class.getSimpleName()).child(childName).removeValue();
    }
    public Query validateUserCredentials(User user) {
        return databaseReference.orderByChild("name").equalTo(user.getName());
    }

    public DatabaseReference getRefToCheckIfUserExists(String username) {
        return databaseReference.child(username);
    }

    public Task<Void> addTransaction(Transactions transactions) {
        return databaseReference.child(transactions.getUsername()).child(Transactions.class.getSimpleName()).child(transactions.getTransactionId()).setValue(transactions);
    }
}

