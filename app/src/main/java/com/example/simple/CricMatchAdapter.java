package com.example.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CricMatchAdapter extends ArrayAdapter<cricMatch> {

    private TextView teamA;
    private TextView teamB;
    private TextView type;
    private TextView date;
    private  int r=0;

    public CricMatchAdapter(Context context, List<cricMatch> cricMatches)
    {
        super(context, 0, cricMatches);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(
                    R.layout.cric_list_item,parent,false);
        }
        teamA=(TextView) listItemView.findViewById(R.id.team1);
        teamB=(TextView) listItemView.findViewById(R.id.team2);
        type=(TextView) listItemView.findViewById(R.id.type);
        date=(TextView) listItemView.findViewById(R.id.date);

        cricMatch currentCricMatch=getItem(position);
        String oteamA =currentCricMatch.getMteamA();
        teamA.setText(oteamA);
        String oteamB =currentCricMatch.getMteamB();
        teamB.setText(oteamB);
        String odate =currentCricMatch.getMdate();
        date.setText(odate);
        String otype =currentCricMatch.getMtype();
        type.setText(otype);





        return listItemView;
    }
}
