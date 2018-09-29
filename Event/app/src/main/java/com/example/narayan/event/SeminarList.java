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

class SeminarList extends ArrayAdapter<SeminarDetails> {

    private Activity context;
    private List<SeminarDetails> seminarDetailsList;

    public SeminarList(Activity context,List<SeminarDetails>seminarDetailsList){
        super(context, R.layout.seminars_list,seminarDetailsList);
        this.context=context;
        this.seminarDetailsList=seminarDetailsList;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItems=inflater.inflate(R.layout.seminars_list,null,true);

        TextView name,lname,date;

        name=(TextView)listViewItems.findViewById(R.id.semiName);
        //lname=(TextView)listViewItems.findViewById(R.id.tvCoordListLname);
        date=(TextView)listViewItems.findViewById(R.id.SemiDate);
        SeminarDetails seminarDetails=seminarDetailsList.get(position);
        name.setText(seminarDetails.getSemiName());
        //lname.setText(seminarDetails.getLastName());
        date.setText(seminarDetails.getSemiDate());

        return listViewItems;
    }
}
