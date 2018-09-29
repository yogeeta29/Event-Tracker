package com.example.narayan.event;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class coordinator extends Fragment {

    LinearLayout coordinatorLayout;
    RelativeLayout coordView;
    Bitmap bm,blurImg;
    public coordinator() {
        // Required empty public constructor

    }

    View myView,coordinatorView;
    //FirebaseDatabase mDatabase;
    DatabaseReference ref;
    Button addCoordinator,reset,back;
    String chkUsrType,chkUsrName;
    ProgressDialog pd;
    //ScrollView coordinatorScroll;
    List<CoordinatorDetails> coordinatorDetailsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_coordinator, container, false);
        coordView=(RelativeLayout)myView.findViewById(R.id.rlCoordinator);

        //.............................Bitmap................................

       /* coordView.setDrawingCacheEnabled(true);
        coordView.buildDrawingCache();
        bm=coordView.getDrawingCache();
        int height,width;
        width=coordView.getWidth();
        height=coordView.getHeight();
        blurImg = bm.createScaledBitmap(bm,width,height, true);

        myView.setBackground(new BitmapDrawable(getResources(),blurImg));
        */


        addCoordinator=(Button)myView.findViewById(R.id.btnAddCoordinator);
        //coordView.addView(blurImg);
        coordView.setVisibility(View.GONE);

        pd= new ProgressDialog(getActivity());
        pd.setMessage("Loading...... Internet Connection Required");
        pd.setCancelable(false);
        pd.show();

        ref=FirebaseDatabase.getInstance().getReference("coordinators");
        //coordinatorScroll=(ScrollView)myView.findViewById(R.id.scrollViewCoordinators);

        coordinatorDetailsList=new ArrayList<>();


        coordinatorLayout=(LinearLayout)myView.findViewById(R.id.llCoordinators);

        //---------------------------------Database(To hide stuff)-------------------------------------------------------------------------------
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        DatabaseReference user=myRef.child("Type").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("type");
        //DatabaseReference userName=myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name");

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chkUsrType=dataSnapshot.getValue(String.class).toString();
                //chkUsrName=name;
                //Toast.makeText(getActivity(),chkUsrName,Toast.LENGTH_LONG).show();
                disableStuff(chkUsrType);
                coordView.setVisibility(View.VISIBLE);
                pd.dismiss();
                //setUserActivity(chkUsrName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pd.dismiss();
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });





        return myView;

    }


    @Override
    public void onStart() {
        super.onStart();
        coordinatorLayout.removeAllViews();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                coordinatorDetailsList.clear();
                for(DataSnapshot coordSnapshot:dataSnapshot.getChildren()){
                    CoordinatorDetails coordinatorDetails=coordSnapshot.getValue(CoordinatorDetails.class);
                    coordinatorDetailsList.add(coordinatorDetails);
                }
                CoordinatorList adapter=new CoordinatorList(getActivity(),coordinatorDetailsList);
                for(int i=0;i<adapter.getCount();i++)
                {
                    View items=adapter.getView(i,null,null);
                    coordinatorLayout.addView(items);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //coordinatorLayout=(LinearLayout)myView.findViewById(R.id.llCoordinators);
        //LayoutInflater inflater;
        //coordinatorView=View.inflate(R.layout.coordinator_list,null);
    }

    public void disableStuff(String chkUsrType)
    {
        if (!chkUsrType.equals("admin"))
        {
            addCoordinator.setVisibility(View.GONE);
        }
    }
}
