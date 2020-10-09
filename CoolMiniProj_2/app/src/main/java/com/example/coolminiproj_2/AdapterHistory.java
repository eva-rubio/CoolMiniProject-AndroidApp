package com.example.coolminiproj_2;

import android.widget.BaseAdapter;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.zip.Inflater;

import java.util.List;

public class AdapterHistory extends BaseAdapter {
    public static final String EXTRA_MESSAGE = "com.example.coolminiproj_2.MESSAGE";

    private Context context;
    List<String> nameOfSearchList;
    List<String> numberOfSearchList;
    LayoutInflater myRowinflter;
    public AdapterHistory(Context context, List<String> nameOfSearchList,List<String> numberOfSearchList ){
        this.context = context;
        this.nameOfSearchList = nameOfSearchList;
        this.numberOfSearchList = numberOfSearchList;
        myRowinflter = (LayoutInflater.from(context));

    }
    @Override
    public int getCount() {
        return nameOfSearchList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = myRowinflter.inflate(R.layout.row_of_recentsearches, null);
        TextView name = (TextView) view.findViewById(R.id.name_of_recentsearch);
        TextView number = (TextView) view.findViewById(R.id.number_results_obtained);
        ImageView searchAgainBtn = (ImageView) view.findViewById(R.id.icon_row_recent);
        ImageView deleteOneRowBtn = (ImageView) view.findViewById(R.id.delete_one_search);

        String queryStr = "Query:  "+ nameOfSearchList.get(i);
        name.setText(queryStr);
        String numbStr = "Results Found:  "+ numberOfSearchList.get(i);
        number.setText(numbStr);
        searchAgainBtn.setImageResource(R.drawable.lupa_search);

        deleteOneRowBtn.setImageResource(R.drawable.minus_delete);
        deleteOneRowBtn.setTag(i);

        deleteOneRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer theIndex = (Integer) v.getTag();
                nameOfSearchList.remove(theIndex.intValue());
                numberOfSearchList.remove(theIndex.intValue());
                notifyDataSetChanged();
            }});
        return view;
    }
}
