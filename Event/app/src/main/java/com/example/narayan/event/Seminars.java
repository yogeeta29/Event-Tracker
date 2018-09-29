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

public class Seminars extends Fragment {

        LinearLayout SeminarLayout;
        RelativeLayout SemiView;
        Bitmap bm,blurImg;

        public Seminars() {
            // Required empty public constructor

        }

        View myView,coordinatorView;
        //FirebaseDatabase mDatabase;
        DatabaseReference ref;
        Button addSeminar,reset,back;
        String chkUsrType,chkUsrName;
        ProgressDialog pd;
        //ScrollView coordinatorScroll;
        List<SeminarDetails> seminarsDetailsList;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            myView= inflater.inflate(R.layout.fragment_seminars, container, false);
            SemiView=(RelativeLayout)myView.findViewById(R.id.rlSeminars);

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


            addSeminar=(Button)myView.findViewById(R.id.btnAddSeminars);
            //coordView.addView(blurImg);
            SemiView.setVisibility(View.GONE);

            pd= new ProgressDialog(getActivity());
            pd.setMessage("Loading...... Internet Connection Required");
            pd.setCancelable(false);
            pd.show();

            ref= FirebaseDatabase.getInstance().getReference("seminars");
            //coordinatorScroll=(ScrollView)myView.findViewById(R.id.scrollViewCoordinators);

            seminarsDetailsList=new ArrayList<>();

            SeminarLayout=(LinearLayout)myView.findViewById(R.id.llSeminars);

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
                    SemiView.setVisibility(View.VISIBLE);
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
            SeminarLayout.removeAllViews();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    seminarsDetailsList.clear();
                    for(DataSnapshot coordSnapshot:dataSnapshot.getChildren()){
                        SeminarDetails seminarDetails=coordSnapshot.getValue(SeminarDetails.class);
                        seminarsDetailsList.add(seminarDetails);
                    }
                    SeminarList adapter=new SeminarList(getActivity(),seminarsDetailsList);
                    for(int i=0;i<adapter.getCount();i++)
                    {
                        View items=adapter.getView(i,null,null);
                        SeminarLayout.addView(items);
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
                addSeminar.setVisibility(View.GONE);
            }
        }
    }
