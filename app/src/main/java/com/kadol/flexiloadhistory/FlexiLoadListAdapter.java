package com.kadol.flexiloadhistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nafi on 4/9/2017.
 */

public class FlexiLoadListAdapter extends ArrayAdapter<FlexiLoadUnitCell> {

    public FlexiLoadListAdapter(Context context, ArrayList<FlexiLoadUnitCell> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        FlexiLoadUnitCell currentCell=getItem(position);

        View listItemView=convertView;

        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView date=(TextView) listItemView.findViewById(R.id.date);
        date.setText(currentCell.getmDate());

        Random rand=new Random();
        int r=rand.nextInt(2);
        if(r!=1){
            View monthly=(View) listItemView.findViewById(R.id.monthly_report_container);
            monthly.setVisibility(View.GONE);
        }

        TextView time=(TextView) listItemView.findViewById(R.id.time);
        time.setText(currentCell.getmTime());

        TextView taka=(TextView) listItemView.findViewById(R.id.taka);
        taka.setText(currentCell.getmTaka());

        return listItemView;
    }
}
