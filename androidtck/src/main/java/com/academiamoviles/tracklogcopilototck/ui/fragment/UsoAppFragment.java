package com.academiamoviles.tracklogcopilototck.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.academiamoviles.tracklogcopilototck.R;
import com.academiamoviles.tracklogcopilototck.ui.UsoApp;
import com.academiamoviles.tracklogcopilototck.ui.adapter.UsoAppAdapter;

/**
 * Created by Android on 07/02/2017.
 */

public class UsoAppFragment extends BaseFragment {

    RecyclerView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uso_fragment, container, false);
        listView = (RecyclerView) view.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

        UsoApp objUsoApp = UsoApp.readIS(getActivity());
        UsoAppAdapter adapter = new UsoAppAdapter(objUsoApp.fecha);
        listView.setAdapter(adapter);


        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
