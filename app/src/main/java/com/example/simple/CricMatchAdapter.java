package com.example.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CricMatchAdapter extends ArrayAdapter<cricMatch> {

    private TextView teamA;
    private TextView teamB;
    private TextView type;
    private TextView date;
    private ImageView image1;
    private ImageView image2;

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
                    R.layout.cric_view,parent,false);
        }
        teamA= listItemView.findViewById(R.id.team1_name);
        teamB= listItemView.findViewById(R.id.team2_name);
        type= listItemView.findViewById(R.id.type);
        date= listItemView.findViewById(R.id.date);
        image1= listItemView.findViewById(R.id.team1_image);
        image2= listItemView.findViewById(R.id.team2_image);

        cricMatch currentCricMatch=getItem(position);

        String oteamA =currentCricMatch.getMteamA();
        teamA.setText(oteamA);

        String oteamB =currentCricMatch.getMteamB();
        teamB.setText(oteamB);

        String odate =currentCricMatch.getMdate();
        date.setText(DateSetter(odate));

        String otype =currentCricMatch.getMtype();
        type.setText(otype);


        image1.setImageResource(R.drawable.india);

        image2.setImageResource(R.drawable.india);

        return listItemView;
    }

    String DateSetter(String date){

       String [] sep =  date.split("T");
        String part1 = sep[0];
        String part2 = sep[1];
    return part1;
    }
}