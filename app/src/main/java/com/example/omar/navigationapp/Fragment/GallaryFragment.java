package com.example.omar.navigationapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omar.navigationapp.Adapter.RecycleAdapter;
import com.example.omar.navigationapp.Model.RecycleModel;
import com.example.omar.navigationapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SaTa on 12/27/2017.
 */

public class GallaryFragment extends Fragment {


   @Nullable
   @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       RecycleModel booh = new RecycleModel("Booh" , R.drawable.booh);
       RecycleModel Tom = new RecycleModel("Tom" , R.drawable.tom);
       RecycleModel Tweety = new RecycleModel("Tweety" , R.drawable.tweety);
       RecycleModel Spider = new RecycleModel("Spider" , R.drawable.spedr);
       RecycleModel Micky = new RecycleModel("Micky" , R.drawable.micky);

       List<RecycleModel> newlist=new ArrayList<RecycleModel>();
       newlist.add(booh);
       newlist.add(Tom);
       newlist.add(Tweety);
       newlist.add(Spider);
       newlist.add(Micky);




       RecycleAdapter adapter = new RecycleAdapter(this ,newlist);
       RecyclerView recyclerView = (RecyclerView)getView().findViewById(R.id.recycleview);
       recyclerView.setAdapter(adapter);
       return inflater.inflate (R.layout.gallary_fragment ,null ) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
