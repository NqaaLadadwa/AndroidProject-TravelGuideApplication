package com.project.project;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class HomeFragment extends Fragment {
    private TextView cityTv, countryTv, continentTv, longitudeTv, latitudeTv, costTv, descriptionTv;
    private ImageView imageV;
    private static String JSON_URL="https://run.mocky.io/v3/d1a9c002-6e88-4d1e-9f39-930615876bca";
    String userEmail,preferredContinent;
    DataBaseHelper dataBaseHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            userEmail = getArguments().getString("EMAIL");
            dataBaseHelper = new DataBaseHelper(getActivity(), "TravelDestination", null, 1);
            preferredContinent = dataBaseHelper.getContinent(userEmail);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cityTv = view.findViewById(R.id.city);
        countryTv = view.findViewById(R.id.country);
        continentTv = view.findViewById(R.id.continent);
        longitudeTv = view.findViewById(R.id.longitude);
        latitudeTv = view.findViewById(R.id.latitude);
        costTv = view.findViewById(R.id.cost);
        descriptionTv = view.findViewById(R.id.description);
        imageV = view.findViewById(R.id.image_view);
        RESTJSON();
        return view;
    }

    private void RESTJSON() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {
                        List<JSONObject> destinations = new ArrayList<JSONObject>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject destinationobj = response.getJSONObject(i);
                                Destination destination = new Destination();
                                if (destinationobj.getString("continent").equals(preferredContinent)) {

                                    destinations.add(destinationobj);
                                }
                            }

                            if (!destinations.isEmpty()) {
                                Random random = new Random();
                                int randomIndex = random.nextInt(destinations.size());
                                JSONObject randomCountry = destinations.get(randomIndex);

                                cityTv.setText(randomCountry.getString("city"));
                                countryTv.setText(randomCountry.getString("country"));
                                continentTv.setText(randomCountry.getString("continent"));
                                longitudeTv.setText(randomCountry.getString("longitude"));
                                latitudeTv.setText(randomCountry.getString("latitude"));
                                costTv.setText(randomCountry.getString("cost"));
                                descriptionTv.setText(randomCountry.getString("description"));
                                Glide.with(getContext()).load(randomCountry.getString("img")).into(imageV);
                            }
                        } catch (JSONException e) {
                            // handle error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                    }
                });


        queue.add(jsonArrayRequest);
    }
}






