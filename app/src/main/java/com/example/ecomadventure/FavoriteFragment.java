package com.example.ecomadventure;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView fvRecyclerView;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        fvRecyclerView = view.findViewById(R.id.favorite_fragment_recyclerview);
        fvRecyclerView.setHasFixedSize(true);
        fvRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        DaoUser daoUser;
        ArrayList<Integer> imagesId = new ArrayList<>();
        ArrayList<Integer> ratings=new ArrayList<>();
        ArrayList<Integer> price=new ArrayList<>();
        ArrayList<String>information=new ArrayList<>();
        ArrayList<String>title=new ArrayList<>();
        ArrayList<String> getFavkey = new ArrayList<>();
        String userName = HomeScreen.getUserName();
        User user = new User(userName);
        daoUser = new DaoUser();
        Query checkUser = daoUser.validateUserCredentials(user);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userName).child(Favorite.class.getSimpleName()).exists()) {
                    Map<String, Map<String, String>> hashmap = (Map<String, Map<String, String>>) snapshot.child(userName).child(Favorite.class.getSimpleName()).getValue();
                    for (Map.Entry<String, Map<String, String>> employeeEntrySet : hashmap.entrySet()) {
                        getFavkey.add(employeeEntrySet.getKey());
                        Map<String, String> valueMap = employeeEntrySet.getValue();
                        Iterator<Map.Entry<String, String>> itr = valueMap.entrySet().iterator();
                        while (itr.hasNext()) {
                            Map.Entry<String, String> entry = itr.next();
                            String key = entry.getKey();
                            String val = entry.getValue();
                            if (key.equals("image"))imagesId.add(Integer.parseInt(val));
                            else if (key.equals("price"))price.add(Integer.parseInt(val));
                            else if (key.equals("ratings"))ratings.add(Integer.parseInt(val));
                            else if (key.equals("title"))title.add(val);
                            else if (key.equals("information"))information.add(val);
                        }
                    }
                    FavoriteFragmentAdapter ffAdapter = new FavoriteFragmentAdapter(view.getContext(),imagesId.stream().mapToInt(i->i).toArray(),
                            price.stream().mapToInt(i->i).toArray(),
                            ratings.stream().mapToInt(i->i).toArray(),
                            Arrays.copyOf(title.toArray(),title.size(),String[].class),Arrays.copyOf(information.toArray(),information.size(),String[].class),getFavkey);
                    fvRecyclerView.setAdapter(ffAdapter);
                    fvRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }
}