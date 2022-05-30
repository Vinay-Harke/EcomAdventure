package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.PaymentResultListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    SliderView sliderView;
    private RecyclerView bjRecyclerView;
    private RecyclerView skiingRecyclerView;
    private RecyclerView cavingRecyclerView;
    private RecyclerView desertCampingRecyclerView;

    int[] imagesId = {R.drawable.bj_delhi,R.drawable.bj_goa,R.drawable.bj_malvan};
    int[] price = {3500,3000,3000};
    int[] ratings = {3,4,4};
    String[] information = {"Wanderlust is the provider for this sport. All the equipment is imported from Japan and all staff are very skilled."
                            ,"One of the most thrillig and adventures sport apart from all the water sports that you should definitely try."
            ,"Bungee Jumping takes place in an adventure park called Della Adventures. The equipments attached at a safe height."};
    String[] title = {"BUNGEE JUMPING IN DELHI","BUNGEE JUMPING IN GOA","LONAVALA"};

    int [] skiingImageId ={R.drawable.pachghani,R.drawable.lavasa,R.drawable.hollant_beach_goa};
    int[] skiingPrice = {3000,6400,7500};
    int[] skiingRatings = {3,4,5};
    String[]  skiingInformation = {"Deriving its name from the five hills surrounding it, Panchgani is a popular hill station.",
                                    "Known as India's newest hill station, the Lavasa Corporation is constructing this private city.",
                                    "A Picture-Perfect Destination! The curvy bay lined with rustic boats, the clean, golden sand.."};
    String[] skiingTitle = {"PACHGANI","LAVASA","HOLLANT BEACH: GOA"};

    int [] cavingImageId = {R.drawable.panchmarhi_caves,R.drawable.ajantha_and_ellora,R.drawable.belum_caves};
    int [] cavingPrice = {3000,7500,5000};
    int [] cavingRatings = {3,5,4};
    String [] cavingInformation = {"Pachmarhi caves are believed to be the caves that were once used as shelter by the Pandava Brothers and their wife",
                                    "Ajanta and Ellora caves, considered to be one of the finest examples of ancient rock-cut caves, are located near Aurangabad.",
                                    "Belum caves are naturally made underground caves, which extend over 3km and are 46 meters deep."};
    String [] cavingTitle = {"PACHMARHI CAVES","AJANTHA & ELLORA CAVES","BELUM CAVES"};

    int [] campingImageId = {R.drawable.damodar_desert_camp,R.drawable.solang_valley_manali,R.drawable.spiti_valley_himachal_pradesh};
    int [] campingPrice = {4300,4000,7000};
    int [] campingRatings = {3,3,4};
    String [] campingInformation = {"If you are looking for a camping experience that is a bit more peaceful and away from the crowd.",
                                    "One of the best camping sites in India, Solang Valley in Manali attracts visitors from the far ends of the world.",
                                    "First on our list is, Spiti valley nestled in the Keylong district of Himachal Pradesh. It is one of the best camping site."};
    String [] campingTitle = {"DAMODARA DESERT CAMP","SOLANG VALLEY, MANALI","SPITI VALLEY, HIMACHAL PRADESH"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList<String> getFavkey = new ArrayList<>();
        String userName = HomeScreen.getUserName();
        User user = new User(userName);
        DaoUser daoUser = new DaoUser();
        Query checkUser = daoUser.validateUserCredentials(user);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userName).child(Favorite.class.getSimpleName()).exists()) {
                    Map<String, Map<String, String>> hashmap = (Map<String, Map<String, String>>) snapshot.child(userName).child(Favorite.class.getSimpleName()).getValue();
                    for (Map.Entry<String, Map<String, String>> employeeEntrySet : hashmap.entrySet()) {
                         getFavkey.add(employeeEntrySet.getKey());
                        }
                    }
                    view = setHomeFragment(view,getFavkey);
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }
    private View setHomeFragment(View view,ArrayList<String> getFavkey) {
        sliderView = view.findViewById(R.id.banner_image_auto_slider);
        BannerSliderAdapter bannerSliderAdapter = new BannerSliderAdapter(imagesId);
        sliderView.setSliderAdapter(bannerSliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        bjRecyclerView = view.findViewById(R.id.bungee_jumping_recycler_view);
        bjRecyclerView.setHasFixedSize(true);
        bjRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        BungeeJumpingAdapter bungeeJumpingAdapter = new BungeeJumpingAdapter(view.getContext(),imagesId,price,ratings,title,information,getFavkey);
        bjRecyclerView.setAdapter(bungeeJumpingAdapter);
        bjRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));


        skiingRecyclerView = view.findViewById(R.id.skiing_recycler_view);
        skiingRecyclerView.setHasFixedSize(true);
        skiingRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        BungeeJumpingAdapter skiingAdapter = new BungeeJumpingAdapter(view.getContext(),skiingImageId,skiingPrice,skiingRatings,skiingTitle,skiingInformation,getFavkey);
        skiingRecyclerView.setAdapter(skiingAdapter);
        skiingRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));



        cavingRecyclerView = view.findViewById(R.id.caving_recycler_view);
        cavingRecyclerView.setHasFixedSize(true);
        cavingRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        BungeeJumpingAdapter cavingAdapter = new BungeeJumpingAdapter(view.getContext(),cavingImageId,cavingPrice,cavingRatings,cavingTitle,cavingInformation,getFavkey);
        cavingRecyclerView.setAdapter(cavingAdapter);
        cavingRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        desertCampingRecyclerView = view.findViewById(R.id.desert_camping_recycler_view);
        desertCampingRecyclerView.setHasFixedSize(true);
        desertCampingRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        BungeeJumpingAdapter desertCampingAdapter = new BungeeJumpingAdapter(view.getContext(),campingImageId,campingPrice,campingRatings,campingTitle,campingInformation,getFavkey);
        desertCampingRecyclerView.setAdapter(desertCampingAdapter);
        desertCampingRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }
}