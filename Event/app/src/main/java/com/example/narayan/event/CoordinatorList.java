package com.example.narayan.event;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CoordinatorList extends ArrayAdapter<CoordinatorDetails>{
    private Activity context;
    private List<CoordinatorDetails>coordinatorDetailsList;

    public CoordinatorList(Activity context,List<CoordinatorDetails>coordinatorDetailsList){
        super(context,R.layout.coordinator_list,coordinatorDetailsList);
        this.context=context;
        this.coordinatorDetailsList=coordinatorDetailsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItems=inflater.inflate(R.layout.coordinator_list,null,true);

        TextView fname,lname,date;

        fname=(TextView)listViewItems.findViewById(R.id.semiName);
        lname=(TextView)listViewItems.findViewById(R.id.tvCoordListLname);
        date=(TextView)listViewItems.findViewById(R.id.tvDateAdded);
        CoordinatorDetails coordinatorDetails=coordinatorDetailsList.get(position);
        fname.setText(coordinatorDetails.getFirstName());
        lname.setText(coordinatorDetails.getLastName());
        date.setText("Added:- "+coordinatorDetails.getDateCreated());

        return listViewItems;
    }
}
