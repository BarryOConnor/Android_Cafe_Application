package com.barry.ntufood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;


public class NTUFood extends AppCompatActivity {
    private String mCurrentOutlet;
    private String mCurrentTable;
    private FoodItem mCurrentFood;
    private User mCurrentUser;
    private ProgressBar mProgressbar;
    private List<FoodItem> mMenu;
    private Button mbtnHome;
    private Order mCurrentOrder = new Order();
    private boolean isAdmin = false;
    private Timestamp mPastOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressbar = findViewById(R.id.pbShowLoading);
        mbtnHome = findViewById(R.id.btnHome);

        mbtnHome.setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            @Override
            public void onClick(View view) {
                if (isAdmin) {
                    //replaceFragment(new fragAdminHome(),true);
                } else {
                    replaceFragment(new fragCustHome(),true);
                }

            }
        });

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFrame, fragLogin.newInstance())
                    .commit();
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }*/



    public void replaceFragment(Fragment fragment, boolean remove) {
        String[] result = fragment.toString().split("[{]");
        if(remove){
            getSupportFragmentManager().popBackStack(result[0],0);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFrame, fragment, fragment.toString())
                    .addToBackStack(result[0])
                    .commit();
        }
    }

    public ViewGroup getRootLayout() {
        return findViewById(R.id.parent);
    }

    public User getCurrentUser(){
        return mCurrentUser;
    }

    public void setCurrentUser(User currentUser){
        mCurrentUser = currentUser;
        mCurrentOrder.setUserID(currentUser.getUid());
    }

    public String getCurrentOutlet(){
        return mCurrentOutlet;
    }

    public void setCurrentOutlet(String outlet){
        mCurrentOrder.setOutlet(outlet);
        mCurrentOutlet = outlet;
    }

    public String getCurrentTable(){
        return mCurrentTable;
    }

    public void setCurrentTable(String table){
        mCurrentOrder.setTable(table);
        mCurrentTable = table;
    }

    public FoodItem getCurrentFood(){
        return mCurrentFood;
    }

    public void setCurrentFood(String currentFood){

        for (FoodItem currentItem : mMenu) {
            if (currentItem.getName().equals(currentFood)) {
                mCurrentFood = currentItem;
                return;
            }
        }
        return;
    }

    public List<FoodItem> getMenu(){
        return mMenu;
    }
    public void setMenu(List<FoodItem> menu){
        mMenu = menu;
    }

    public boolean isMenuEmpty(){
        return mMenu.isEmpty();
    }

    public List<FoodItem> foodSearch(String query){
        List<FoodItem> returnList = new ArrayList<>();

        for (FoodItem currentItem : mMenu) {
            if (currentItem.getOutlet().contains(mCurrentOutlet) && currentItem.getName().toLowerCase().contains(query.toLowerCase())) {
                returnList.add(currentItem);
            }
        }

        return returnList;
    }

    public void showLoadingState(boolean visible){
        if (visible){
            mProgressbar.setVisibility(View.VISIBLE);
        } else {
            mProgressbar.setVisibility(View.INVISIBLE);
        }
    }

    public void showHome(boolean show){
        if (show){
            mbtnHome.setVisibility(View.VISIBLE);
        } else {
            mbtnHome.setVisibility(View.INVISIBLE);
        }
    }

    public void addToOrder(OrderItem item) {
        this.mCurrentOrder.addItem(item);
    }

    public void removeFromOrder(int item, double price) {
        this.mCurrentOrder.removeItem(item, price);
    }

    public Order getCurrentOrder() {
        return this.mCurrentOrder;
    }

    public void resetOrder() {
        this.mCurrentOrder = new Order();
    }

    public Timestamp getPastOrder() {
        return mPastOrder;
    }

    public void setPastOrder(Timestamp mPastOrder) {
        this.mPastOrder = mPastOrder;
    }
}