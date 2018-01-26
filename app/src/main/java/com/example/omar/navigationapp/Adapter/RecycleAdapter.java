package com.example.omar.navigationapp.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.navigationapp.Fragment.GallaryFragment;
import com.example.omar.navigationapp.Model.RecycleModel;
import com.example.omar.navigationapp.R;

import java.util.List;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by SaTa on 1/4/2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private List<RecycleModel>input ;
   GallaryFragment context ;

    public RecycleAdapter(GallaryFragment Context , List<RecycleModel>RecycleModel){
        this.context = Context;
        this.input= RecycleModel;

    }

    public class NavViewHolder extends RecyclerView.ViewHolder {

        TextView KartonName ;
        ImageView KartonImg ;

        public NavViewHolder(View recyclerowlayout) {
            super(recyclerowlayout);

            KartonName = (TextView)recyclerowlayout.findViewById(R.id.rowtxt);
            KartonImg = (ImageView)recyclerowlayout.findViewById(R.id.rowimg);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.recycle_row ,parent , false);
        return new NavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.d(TAG , "Elements" + position + "set" );

        RecycleModel data = input.get(position);
        NavViewHolder Show = (NavViewHolder)holder ;
        Show.KartonImg.setImageResource(data.Image);
        Show.KartonName.setText(data.Name);


    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    }


