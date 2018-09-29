package com.example.narayan.event;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CoordRegistration extends Activity {

    Button regCoordinator;
    EditText fname, lname, emailId, pass;
    private DatabaseReference mDatabase;
    private DatabaseReference coordinatorRef;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;

    String FirstName,LastName,EmailId,Pass;

    //List<CoordinatorDetails> coordinatorDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coord_registration);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        coordinatorRef = mDatabase.child("coordinators");
        firebaseAuth= FirebaseAuth.getInstance();

        pd= new ProgressDialog(this);
        //pd.setMessage("Registering...... Internet Connection Required");
        //pd.setCancelable(false);

        regCoordinator = (Button) findViewById(R.id.btnCoordRegister);
        fname = (EditText) findViewById(R.id.etCoordFname);
        lname = (EditText) findViewById(R.id.etCoordLname);
        emailId = (EditText) findViewById(R.id.etCoordEmail);
        pass = (EditText) findViewById(R.id.etCoordPassword);

        /*
        fname.setText("blaaa");
        lname.setText("hsjdjd");
        email.setText("aaa@gmail.com");
        pass.setText("qwerty12345");
        */

        regCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //boolean test=false;

                //test=validateCoord();

                if (validateCoord()) {

                    registerCoordinator();
                    Toast.makeText(CoordRegistration.this,"Done !!",Toast.LENGTH_LONG).show();
                    clearField();
                }
                else
                {
                    Toast.makeText(CoordRegistration.this, "Please enter all Details", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

   /* public class CoordinatorDetails {
        private String CoordId;
        private String FirstName;
        private String LastName;
        private String EmailId;
        private String Password;
        private long DateCreated;
    }*/

    private boolean validateCoord() {
        boolean result=false;

        String fName=fname.getText().toString();
        String EmailId=emailId.getText().toString();
        String LName=lname.getText().toString();
        String Password=pass.getText().toString();

        if (fName.isEmpty() || LName.isEmpty() || EmailId.isEmpty() || Password.isEmpty()) {
            result=false;
        } else if(Password.length()<=8)
        {
            Toast.makeText(getApplicationContext(),"Password should be more than 8 char",Toast.LENGTH_LONG).show();
        }
        else{
            result= true;
        }

        return result;
    }

    public List<CoordinatorDetails> GetList(){
        List<CoordinatorDetails> coordinatorDetails = new ArrayList<>();
        CoordinatorDetails coordinatorDetails1 = new CoordinatorDetails();
        coordinatorDetails1.setFirstName(fname.getText().toString());
        coordinatorDetails1.setLastName(lname.getText().toString());
        coordinatorDetails1.setEmailId(emailId.getText().toString());
        //coordinatorDetails1.setPassword(pass.getText().toString());

        //Calendar calendar= new GregorianCalendar();
        //coordinatorDetails1.DateCreated= DateFormat.getDateTimeInstance().format(new Date());

        //Calendar calendar1 = GregorianCalendar.getInstance();
        //coordinatorDetails1.setDateCreated(calendar1.getTimeInMillis());
        Date c=Calendar.getInstance().getTime();
        SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy");

        coordinatorDetails1.setDateCreated(df.format(c).toString());
        coordinatorDetails.add(coordinatorDetails1);

        return coordinatorDetails;
    }

    public void registerCoordinator()
    {
        //int i=0;
        pd.setMessage("Registering the User");
        pd.setCancelable(false);
        pd.show();



        FirstName=fname.getText().toString();
        LastName=lname.getText().toString();
        EmailId=emailId.getText().toString();
        Pass=pass.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(emailId.getText().toString().trim(),pass.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            pd.dismiss();
                            Users user =new Users(
                                    FirstName,
                                    EmailId,
                                    Pass
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {

                                        UserType userType = new UserType("coordinator");
                                        FirebaseDatabase.getInstance().getReference("Type")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(userType);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Registering user failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_LONG).show();

                        }
                    }
                });

        for(CoordinatorDetails coord:GetList())
        {
            //String key=coordinatorRef.push().getKey();
            //coord.sete(key);
            String key=coord.getEmailId().replace(".",",");
            coordinatorRef.child(key).setValue(coord);

            //Toast.makeText(CoordRegistration.this,coord.getFirstName(), Toast.LENGTH_LONG).show();
            //i++;
        }

    }
    public void clearField()
    {
        fname.setText(null);
        lname.setText(null);
        emailId.setText(null);
        pass.setText(null);
    }
    public void resetCoordForm(View view)
    {
        EditText fname,lname,emailId,pass;

        fname=(EditText)findViewById(R.id.etCoordFname);
        lname=(EditText)findViewById(R.id.etCoordLname);
        emailId=(EditText)findViewById(R.id.etCoordEmail);
        pass=(EditText)findViewById(R.id.etCoordPassword);

        fname.setText(null);
        lname.setText(null);
        emailId.setText(null);
        pass.setText(null);
    }
    public void BackHome(View view)
    {
        finish();
    }

}
