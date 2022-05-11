package com.example.ecomadventure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    SliderView sliderView;
    private RecyclerView bjRecyclerView;

    int[] imagesId = {R.drawable._009_lamborghini_gallardo_lp560_4_spyder_1280x1024,
            R.drawable._009_lamborghini_insecta_concept_design_1280x1024,
            R.drawable.google_lab_1920x1080,
            R.drawable.marvel_doctor_strange_3840x2160,
            R.drawable.tron_lamborghini_aventador_1920x1080};
    int[] price = {3000,2000,4000,5000,7000};
    int[] ratings = {5,3,2,4,1};
    String[] title = {"GOA","MALVAN","MANALI","DEVKUND","PUNE"};
    String[] information = {"BJ in Goa","Bj in Malvan","Bj in Manali","Bj in devkund","bj in pune"};

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

        sliderView = view.findViewById(R.id.banner_image_auto_slider);
        BannerSliderAdapter bannerSliderAdapter = new BannerSliderAdapter(imagesId);
        sliderView.setSliderAdapter(bannerSliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        bjRecyclerView = view.findViewById(R.id.bungee_jumping_recycler_view);
        bjRecyclerView.setHasFixedSize(true);
        bjRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        BungeeJumpingAdapter bungeeJumpingAdapter = new BungeeJumpingAdapter(view.getContext(),imagesId,price,ratings,title,information);
        bjRecyclerView.setAdapter(bungeeJumpingAdapter);
        bjRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }
}