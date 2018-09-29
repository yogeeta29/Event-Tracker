package com.example.narayan.event;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstFragment extends Fragment {
    //Activity first_layout;

    CardView createEvents,collegeEvents,banking;

    View myView;

    String chkUsrType,chkUsrName;
    ProgressDialog pd;
    LinearLayout firstLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.first_layout, container, false);

        banking=(CardView)myView.findViewById(R.id.cvBanking);
        createEvents = (CardView)myView.findViewById(R.id.createEvents);
        //collegeEvents = (CardView)myView.findViewById(R.id.collegeEvents);

        firstLayout=(LinearLayout)myView.findViewById(R.id.llFirstLayout);


        /*createEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateEvents.class);
                startActivity(intent);
            }
        });
        collegeEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollegeEvents.class);
                startActivity(intent);
            }
        });
        return myView;*/

        firstLayout.setVisibility(View.GONE);
        pd= new ProgressDialog(getActivity());
        pd.setMessage("Loading...... Internet Connection Required");
        pd.setCancelable(false);
        pd.show();

        //.................................... Database...................................................................
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        DatabaseReference user=ref.child("Type").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("type");
        DatabaseReference userName=ref.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name");

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chkUsrType=dataSnapshot.getValue(String.class).toString();
                //chkUsrName=name;
                //Toast.makeText(getActivity(),chkUsrName,Toast.LENGTH_LONG).show();
                disableCards(chkUsrType);
                firstLayout.setVisibility(View.VISIBLE);
                pd.dismiss();
                //setUserActivity(chkUsrName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pd.dismiss();
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        // just to display name of the user
        userName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chkUsrName=dataSnapshot.getValue(String.class).toString();
                setUserActivity(chkUsrName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return myView;
    }

    public void setUserActivity(String usr)
    {
        Toast.makeText(getActivity(),"Welcome "+usr,Toast.LENGTH_LONG).show();
    }

    public void disableCards(String type)
    {
        //type=type.trim();
        banking.setVisibility(View.GONE);
        if(type.equals("student"))
        {
            banking.setVisibility(View.GONE);
            createEvents.setVisibility(View.GONE);
        }
        //banking.setVisibility(View.GONE);
    }

}
