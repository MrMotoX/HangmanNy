package com.example.rickard.testfragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MyViewModel mViewModel;
    private Integer attempts;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        attempts = 0;
        mViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button switchFragmentBtn;
        switchFragmentBtn = view.findViewById(R.id.switchFragmentBtn);
        switchFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment();
            }
        });

        return view;
    }

    private void switchFragment() {
        attempts++;
        mViewModel.setButtonText("From Main " + attempts.toString());

        ((MainActivity) getActivity()).setViewPager(1);
    }
}
