package com.project.project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SortFragment extends Fragment {
    ImageView imageView ;
    RecyclerView recyclerView;
    List<Destination> destinations;
    private Button sortAscButton;
    private Button sortDescButton;
    private static String JSON_URL="https://run.mocky.io/v3/d1a9c002-6e88-4d1e-9f39-930615876bca";
    sortAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        sortAscButton = view.findViewById(R.id.sortAscending);
        sortDescButton = view.findViewById(R.id.sortDescending);
        imageView = view.findViewById(R.id.imageView);
        recyclerView = view.findViewById(R.id.all_list);
        destinations = new ArrayList<>();
        extractDestination();
        return view;
    }



    private void extractDestination() {


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject destinationobj = response.getJSONObject(i);
                        Destination destination = new Destination();
                        destination.setCity(destinationobj.getString("city").toString());
                        destination.setCost(destinationobj.getString("cost").toString());
                        destinations.add(destination);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                sortAscButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ascending));
                        Collections.sort(destinations, new CostAscComparator());
                        adapter.notifyDataSetChanged();
                    }
                });

                sortDescButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.descending));
                        Collections.sort(destinations, new CostDescComparator());
                        adapter.notifyDataSetChanged();
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new sortAdapter(getContext(), destinations);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: "+error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
    public static class CostAscComparator implements Comparator<Destination>  {
        @Override
        public int compare(Destination d1, Destination d2) {
            int cost1 = Integer.parseInt(d1.getCost());
            int cost2 = Integer.parseInt(d2.getCost());
            return cost1 - cost2;
        }
    }

    public static class CostDescComparator implements Comparator<Destination> {
        @Override
        public int compare(Destination d1, Destination d2) {
            int cost1 = Integer.parseInt(d1.getCost());
            int cost2 = Integer.parseInt(d2.getCost());
            return cost2 - cost1;
        }
    }

}