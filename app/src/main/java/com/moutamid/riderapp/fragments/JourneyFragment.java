package com.moutamid.riderapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxn.stash.Stash;
import com.moutamid.riderapp.R;
import com.moutamid.riderapp.adapters.JourneyAdapter;
import com.moutamid.riderapp.database.RoomDB;
import com.moutamid.riderapp.databinding.FragmentJourneyBinding;
import com.moutamid.riderapp.models.JourneysModel;
import com.moutamid.riderapp.models.UserModel;
import com.moutamid.riderapp.utlis.Constants;

import java.util.ArrayList;
import java.util.List;

public class JourneyFragment extends Fragment {
    FragmentJourneyBinding binding;
    RoomDB database;

    List<JourneysModel> list;

    public JourneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJourneyBinding.inflate(getLayoutInflater(), container, false);

        database = RoomDB.getInstance(requireContext());

        list = new ArrayList<>();
        String username = Stash.getString(Constants.USERNAME);
        List<UserModel> user = database.userDAO().getUser(username.trim());
        UserModel userModel = user.get(user.size()-1);
        list = database.journeyDAO().getAll(userModel.getID());

        binding.recycler.setHasFixedSize(false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        JourneyAdapter adapter = new JourneyAdapter(requireContext(), list);
        binding.recycler.setAdapter(adapter);

        return binding.getRoot();
    }
}