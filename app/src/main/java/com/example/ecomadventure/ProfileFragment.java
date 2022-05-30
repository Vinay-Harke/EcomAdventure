package com.example.ecomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button changePwdBtn;
    private View view;
    private DaoUser daoUser;
    private String username,email,phoneNo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView usernameTxtView,emailTextView,phoneNoTxtView;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        changePwdBtn = view.findViewById(R.id.profile_fragment_change_password_btn);

        usernameTxtView = view.findViewById(R.id.profile_fragment_username);
        phoneNoTxtView = view.findViewById(R.id.profile_fragment_phone);
        emailTextView = view.findViewById(R.id.profile_fragment_email);

        username = HomeScreen.getUserName();
        daoUser = new DaoUser();
        User user = new User(username);

        Query checkUser = daoUser.validateUserCredentials(user);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    phoneNo = snapshot.child(username).child("phone").getValue(String.class);
                    email = snapshot.child(username).child("email").getValue(String.class);
                    assignData(email,phoneNo);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        usernameTxtView.setText(username);
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SetNewPasswordScreen.class);
                intent.putExtra("username",username);
                ((HomeScreen)getActivity()).startActivity(intent);
            }
        });
        return view;
    }

    private void assignData(String email, String phoneNo) {
        emailTextView.setText(email);
        phoneNoTxtView.setText(phoneNo);
    }
}