package com.example.ecomadventure;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAdventureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAdventureFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView myAdventuresRecyclerView;

    public MyAdventureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAdventureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAdventureFragment newInstance(String param1, String param2) {
        MyAdventureFragment fragment = new MyAdventureFragment();
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
        view = inflater.inflate(R.layout.fragment_my_adventure, container, false);

        myAdventuresRecyclerView = view.findViewById(R.id.my_adventure_recyclerview);
        myAdventuresRecyclerView.setHasFixedSize(true);
        myAdventuresRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<Integer> imageId = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Integer> noOfTicket = new ArrayList<>();
        ArrayList<Integer> billAmount = new ArrayList<>();
        ArrayList<String> transactionID = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<String> allTransactionList = new ArrayList<>();
        String userName = HomeScreen.getUserName();
        User user = new User(userName);
        DaoUser daoUser = new DaoUser();
        Query checkUser = daoUser.validateUserCredentials(user);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userName).child(Transactions.class.getSimpleName()).exists()) {
                    Map<String, Map<String, String>> hashmap = (Map<String, Map<String, String>>) snapshot.child(userName).child(Transactions.class.getSimpleName()).getValue();
                    for (Map.Entry<String, Map<String, String>> transactionEntrySet : hashmap.entrySet()){
                        allTransactionList.add(transactionEntrySet.getKey());
                        Map<String, String> valueMap = transactionEntrySet.getValue();
                        Iterator<Map.Entry<String, String>> itr = valueMap.entrySet().iterator();
                            while (itr.hasNext()) {
                            Map.Entry<String, String> entry = itr.next();
                            String key = entry.getKey();
                            String val = entry.getValue();
                            if (key.equals("imageId"))imageId.add(Integer.parseInt(val));
                            else if (key.equals("billAmount"))billAmount.add(Integer.parseInt(val));
                            else if (key.equals("noOfTicket"))noOfTicket.add(Integer.parseInt(val));
                            else if (key.equals("title"))title.add(val);
                            else if (key.equals("status"))status.add(val);
                            else if(key.equals("transactionId")) transactionID.add(val);
                        }
                    }
                }
                MyAdventureAdapter myAdventureAdapter = new MyAdventureAdapter(view.getContext(),imageId.stream().mapToInt(i->i).toArray(),
                        Arrays.copyOf(title.toArray(),title.size(),String[].class),Arrays.copyOf(status.toArray(),status.size(),String[].class),noOfTicket.stream().mapToInt(i->i).toArray(),
                        billAmount.stream().mapToInt(i->i).toArray(), Arrays.copyOf(transactionID.toArray(),transactionID.size(),String[].class));
                myAdventuresRecyclerView.setAdapter(myAdventureAdapter);
                myAdventuresRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}