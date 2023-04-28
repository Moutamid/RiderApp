package com.moutamid.riderapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moutamid.riderapp.R;
import com.moutamid.riderapp.database.RoomDB;
import com.moutamid.riderapp.databinding.FragmentMapsBinding;
import com.moutamid.riderapp.models.JourneysModel;
import com.moutamid.riderapp.models.UserModel;
import com.moutamid.riderapp.utlis.Constants;

import java.util.List;

public class MapsFragment extends Fragment {
    Marker markerA = null;
    Marker markerB = null;
    RoomDB database;
    String markerA_Name = "";
    String markerB_Name = "";
    FragmentMapsBinding binding;
    int coins = 0;
    Location loc1 = new Location("");
    Location loc2 = new Location("");
    float distanceInMeters;
    private OnMapReadyCallback callback = googleMap -> {

        LatLng hungry = new LatLng(47.5333, 21.6333);
        LatLng parkolo = new LatLng(47.521024288430404, 21.62947502487398);
        LatLng kossuth = new LatLng(47.531415874646726, 21.624710724874703);
        LatLng malompark = new LatLng(47.54208223607607, 21.619344651863127);
        LatLng arena = new LatLng(47.54571354304601, 21.64295856720483);
        LatLng decathlon = new LatLng(47.543754716462416, 21.593888096040097);
        LatLng egyetem = new LatLng(47.55174025908643, 21.62166138069917);

        googleMap.addMarker(new MarkerOptions().position(parkolo).title("Vasutallomas parkolo Debrecen"));
        googleMap.addMarker(new MarkerOptions().position(kossuth).title("kossuth ter tram station Debrecen"));
        googleMap.addMarker(new MarkerOptions().position(malompark).title("Malompark Debrecen"));
        googleMap.addMarker(new MarkerOptions().position(arena).title("Fönix Arena Debrecen"));
        googleMap.addMarker(new MarkerOptions().position(decathlon).title("Decathlon Debrecen"));
        googleMap.addMarker(new MarkerOptions().position(egyetem).title("Egyetem ter, Debrecen"));

        googleMap.setMaxZoomPreference(20f);
        googleMap.setMinZoomPreference(12f);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(hungry));


        googleMap.setOnMarkerClickListener(marker -> {
            if (markerA == null) {
                // First marker selected
                markerA = marker;
                markerA_Name = marker.getTitle();
                loc1.setLatitude(markerA.getPosition().latitude);
                loc1.setLongitude(markerA.getPosition().longitude);
                Stash.put(Constants.START, markerA_Name);
                binding.message.setText("Select Second Marker");
            } else if (markerB == null) {
                // Second marker selected
                if (marker == markerA){
                    binding.message.setText("Please Select another location... You select the same location as the start location");
                } else {
                    markerB = marker;
                    markerB_Name = marker.getTitle();
                    loc2.setLatitude(markerB.getPosition().latitude);
                    loc2.setLongitude(markerB.getPosition().longitude);
                    Stash.put(Constants.END, markerB_Name);
                    distanceInMeters = loc1.distanceTo(loc2);
                    binding.message.setText("Both markers selected, You can now start your journey ");
                }
            } else {
                // Two markers already selected, clear selections
                markerA = null;
                markerB = null;
                markerA_Name = "";
                markerB_Name = "";
                loc1 = new Location("");
                loc2 = new Location("");
                binding.message.setText("Markers cleared! ... Select First Marker");
                //Toast.makeText(requireContext(), "Markers cleared", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(getLayoutInflater(), container, false);

        database = RoomDB.getInstance(requireContext());

        String username = Stash.getString(Constants.USERNAME);
        List<UserModel> user = database.userDAO().getUser(username.trim());
        UserModel userModel = user.get(user.size()-1);
        coins = user.get(user.size()-1).getCoins();

        String s = Stash.getString(Constants.START);
        String e = Stash.getString(Constants.END);
        if (!s.isEmpty() && !e.isEmpty()) {
            binding.message.setText("Your Journey is Started. You can end your journey any time");
            binding.start.setVisibility(View.GONE);
            binding.end.setVisibility(View.VISIBLE);
        }

        binding.start.setOnClickListener(v -> {
            if (markerA!=null && markerB!=null) {
                String a = distanceInMeters+"";
                String cc = a.substring(0, 2);
                int c = Integer.parseInt(cc);
                Stash.put("C", c);
                new AlertDialog.Builder(requireContext())
                        .setTitle("Do you want to start this Journey?")
                        .setMessage("This Journey cost "+ cc +" coins.")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            if (coins >= 5) {
                                database.userDAO().update(userModel.getID(), (coins-c) );
                                new Handler().postDelayed(() -> {
                                    dialog.dismiss();
                                    binding.message.setText("Your Journey is Started. You can end your journey any time");
                                    binding.start.setVisibility(View.GONE);
                                    binding.end.setVisibility(View.VISIBLE);
                                }, 500);

                            } else {
                                dialog.dismiss();
                                Toast.makeText(requireContext(), "You don't have enough coins", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                Toast.makeText(requireContext(), "Please Select your markers first", Toast.LENGTH_SHORT).show();
            }
        });

        binding.end.setOnClickListener(v -> {
            String start = Stash.getString(Constants.START);
            String end = Stash.getString(Constants.END);
            int c = Stash.getInt("C");
            JourneysModel journeysModel = new JourneysModel(userModel.getID(), start, end, c);
            database.journeyDAO().insert(journeysModel);
            Toast.makeText(requireContext(), "Journey Ended", Toast.LENGTH_SHORT).show();
            binding.message.setText("Select First Marker");
            binding.start.setVisibility(View.VISIBLE);
            binding.end.setVisibility(View.GONE);
            Stash.clear(Constants.START);
            Stash.clear(Constants.END);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}