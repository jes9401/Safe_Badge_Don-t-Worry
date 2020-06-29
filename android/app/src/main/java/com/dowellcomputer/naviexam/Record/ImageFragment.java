package com.dowellcomputer.naviexam.Record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dowellcomputer.naviexam.R;

public class ImageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_record_helper_frag,container, false );
        ImageView imageView = view.findViewById(R.id.imageView);

        if(getArguments() !=null){
            Bundle args = getArguments();
            imageView.setImageResource(args.getInt("imgRes"));
        }

        return view;
    }
}
