package com.barry.ntufood;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class fragViewMenu extends Fragment {
    private SearchView msvSearch;
    private TextView mtvLocation;
    private RecyclerView mrvCategories, mrvFoodItems;
    private CategoryAdapter categoryAdapter;
    private FoodItemAdapter foodAdapter;
    private HashMap<String, Integer> categoryLocations = new HashMap<String, Integer>();


    public fragViewMenu() {
        // Required empty public constructor
    }

    public static fragViewMenu newInstance() {
        fragViewMenu fragment = new fragViewMenu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_menu, container, false);
    }




    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mrvCategories = view.findViewById(R.id.rvCategories);
        mrvFoodItems = view.findViewById(R.id.rvOrders);
        mtvLocation = view.findViewById(R.id.tvLocation);
        mtvLocation.setText(((NTUFood)getActivity()).getCurrentOutlet());


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrvCategories.getContext(), LinearLayoutManager.HORIZONTAL);
        mrvCategories.addItemDecoration(dividerItemDecoration);

        displayMenu("");

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        mrvFoodItems.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mrvFoodItems, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String selectedItem = ((TextView) mrvFoodItems.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.tvDate)).getText().toString();
                NTUFood ntuFood = (NTUFood) getContext();
                ntuFood.setCurrentFood(selectedItem);
                ntuFood.replaceFragment(new fragDisplayFood(),false);
            }
        }));

        mrvCategories.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mrvCategories, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String category = ((TextView) mrvCategories.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.idCategoryName)).getText().toString();
                int location = categoryLocations.get(category);
                smoothScroller.setTargetPosition(location);
                mrvFoodItems.getLayoutManager().startSmoothScroll(smoothScroller);
            }
        }));


        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        msvSearch = (SearchView) view.findViewById(R.id.svSearch);
        fixSearchView(msvSearch);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getActivity().getComponentName());
        msvSearch.setSearchableInfo(searchableInfo);
        msvSearch.setIconifiedByDefault(false);

        msvSearch.setBackgroundColor(Color.parseColor("#FF373434"));

        msvSearch.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                displayMenu(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                displayMenu(query);
                return true;
            }
        });

    }

    private void displayMenu(String query){
        List<FoodItem> foodItems = new ArrayList<>();
        foodItems = ((NTUFood)getActivity()).foodSearch(query);

        List<String> categories = new ArrayList<>();
        String currentCategory = "";
        Integer currentIndex = 0;

        for (FoodItem currentItem : foodItems) {
            if (!currentCategory.equals(currentItem.getCategory())) {
                currentCategory = currentItem.getCategory();
                categories.add(currentCategory);
                categoryLocations.put(currentCategory, currentIndex);
            }
            currentIndex ++;
        }


        categoryAdapter = new CategoryAdapter(categories, getContext());
        mrvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mrvCategories.setAdapter(categoryAdapter);

        foodAdapter = new FoodItemAdapter(foodItems, getContext());
        mrvFoodItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mrvFoodItems.setAdapter(foodAdapter);
    }

    private void fixSearchView(SearchView currentSearch){
        int searchTextId = currentSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText editText = (EditText) currentSearch.findViewById(searchTextId);
        editText.setTextColor(getResources().getColor(R.color.white));
        editText.setHintTextColor(getResources().getColor(R.color.white));

        int searchImgId = currentSearch.getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView searchButton = (ImageView) currentSearch.findViewById(searchImgId);
        searchButton.setImageResource(R.drawable.ic_baseline_search_24);

        int closeButtonId = currentSearch.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) currentSearch.findViewById(closeButtonId);
        closeButton.setImageResource(R.drawable.ic_baseline_close_24);
    }


}