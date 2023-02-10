package com.project.project;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AllFragment extends Fragment implements  Adapter.OnItemClickListener {
    public static final String url_dest="imgUrl";
    public static final String city_dest="city";
    public static final String country_dest="country";
    public static final String continent_dest="continent";
    public static final String longitude_dest="longitude";
    public static final String latitude_dest="latitude";
    public static final String cost_dest="cost";
    public static final String description_dest="description";

    RecyclerView recyclerView;
    List<Destination> destinations;
    List<Destination> AsiaArray;
    List<Destination> EuropeArray;
    List<Destination> AfricaArray;
    List<Destination> AmericaArray;
    int buttonContinent=0;
    private static String JSON_URL="https://run.mocky.io/v3/d1a9c002-6e88-4d1e-9f39-930615876bca";
    Adapter adapter;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        recyclerView = view.findViewById(R.id.Destinations_list);
        destinations = new ArrayList<>();
        AsiaArray = new ArrayList<>();
        EuropeArray = new ArrayList<>();
        AfricaArray = new ArrayList<>();
        AmericaArray = new ArrayList<>();
        RestJSON();
        return view;
    }
    private void RestJSON() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject destinationobj = response.getJSONObject(i);
                        Destination destination = new Destination();

                        destination.setCity(destinationobj.getString("city").toString());
                        destination.setCountry(destinationobj.getString("country").toString());
                        destination.setImg(destinationobj.getString("img").toString());
                        destination.setContinent(destinationobj.getString("continent").toString());
                        destination.setLongitude(destinationobj.getString("longitude").toString());
                        destination.setLatitude(destinationobj.getString("latitude").toString());
                        destination.setCost(destinationobj.getString("cost").toString());
                        destination.setDescription(destinationobj.getString("description".toString()));

                        if (destination.getContinent().equals("Asia")) {
                            AsiaArray.add(destination);
                        } else if (destination.getContinent().equals("Europe")) {
                            EuropeArray.add(destination);
                        } else if (destination.getContinent().equals("Africa")) {
                            AfricaArray.add(destination);
                        } else if (destination.getContinent().equals("North America")) {
                            AmericaArray.add(destination);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Button btnAsia = (Button) getView().findViewById(R.id.btnAsia);

                btnAsia.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        buttonContinent=1;
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new Adapter(getContext(), AsiaArray);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(AllFragment.this);

                    }


                });
                Button btnEurope = (Button) getView().findViewById(R.id.btnEurope);
                btnEurope.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonContinent=2;

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new Adapter(getContext(), EuropeArray);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(AllFragment.this);
                    }
                });
                Button btnAfrica=(Button) getView().findViewById(R.id.btnAfrica);
                btnAfrica.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonContinent=3;
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new Adapter(getContext(), AfricaArray);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(AllFragment.this);
                    }
                });
                Button North_America_btn=(Button) getView().findViewById(R.id.North_America_btn);
                North_America_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonContinent=4;
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new Adapter(getContext(), AmericaArray);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(AllFragment.this);
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: "+error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(int position) {

        if (buttonContinent==1) {
            Intent destIntent = new Intent(getActivity(),DestinationActivity.class);

            Destination clickedDestination = AsiaArray.get(position);

            destIntent.putExtra(url_dest, clickedDestination.getImg());
            destIntent.putExtra(city_dest, clickedDestination.getCity());
            destIntent.putExtra(country_dest, clickedDestination.getCountry());
            destIntent.putExtra(continent_dest, clickedDestination.getContinent());
            destIntent.putExtra(latitude_dest, clickedDestination.getLatitude());
            destIntent.putExtra(longitude_dest, clickedDestination.getLongitude());
            destIntent.putExtra(cost_dest, clickedDestination.getCost());
            destIntent.putExtra(description_dest, clickedDestination.getDescription());
            startActivity(destIntent);
        }
        if(buttonContinent==2){
            Intent destIntent2 = new Intent(getActivity(),DestinationActivity.class);

            Destination clickedDestination2 = EuropeArray.get(position);

            destIntent2.putExtra(url_dest, clickedDestination2.getImg());
            destIntent2.putExtra(city_dest, clickedDestination2.getCity());
            destIntent2.putExtra(country_dest, clickedDestination2.getCountry());
            destIntent2.putExtra(continent_dest, clickedDestination2.getContinent());
            destIntent2.putExtra(latitude_dest, clickedDestination2.getLatitude());
            destIntent2.putExtra(longitude_dest, clickedDestination2.getLongitude());
            destIntent2.putExtra(cost_dest, clickedDestination2.getCost());
            destIntent2.putExtra(description_dest, clickedDestination2.getDescription());

            startActivity(destIntent2);
        }

        if(buttonContinent==3){
            Intent destIntent3 = new Intent(getActivity(),DestinationActivity.class);

            Destination clickedDestination3 = AfricaArray.get(position);

            destIntent3.putExtra(url_dest, clickedDestination3.getImg());
            destIntent3.putExtra(city_dest, clickedDestination3.getCity());
            destIntent3.putExtra(country_dest, clickedDestination3.getCountry());
            destIntent3.putExtra(continent_dest, clickedDestination3.getContinent());
            destIntent3.putExtra(latitude_dest, clickedDestination3.getLatitude());
            destIntent3.putExtra(longitude_dest, clickedDestination3.getLongitude());
            destIntent3.putExtra(cost_dest, clickedDestination3.getCost());
            destIntent3.putExtra(description_dest, clickedDestination3.getDescription());

            startActivity(destIntent3);
        }
        if(buttonContinent==4){
            Intent destIntent4 = new Intent(getActivity(),DestinationActivity.class);

            Destination clickedDestination4 = AmericaArray.get(position);

            destIntent4.putExtra(url_dest, clickedDestination4.getImg());
            destIntent4.putExtra(city_dest, clickedDestination4.getCity());
            destIntent4.putExtra(country_dest, clickedDestination4.getCountry());
            destIntent4.putExtra(continent_dest, clickedDestination4.getContinent());
            destIntent4.putExtra(latitude_dest, clickedDestination4.getLatitude());
            destIntent4.putExtra(longitude_dest, clickedDestination4.getLongitude());
            destIntent4.putExtra(cost_dest, clickedDestination4.getCost());
            destIntent4.putExtra(description_dest, clickedDestination4.getDescription());

            startActivity(destIntent4);
        }
    }



}