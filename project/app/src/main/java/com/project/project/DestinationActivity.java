package com.project.project;
import static com.project.project.AllFragment.city_dest;
import static com.project.project.AllFragment.continent_dest;
import static com.project.project.AllFragment.cost_dest;
import static com.project.project.AllFragment.country_dest;
import static com.project.project.AllFragment.description_dest;
import static com.project.project.AllFragment.latitude_dest;
import static com.project.project.AllFragment.longitude_dest;
import static com.project.project.AllFragment.url_dest;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;
import android.widget.TextView;



public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Intent intent = getIntent();
        ToggleButton buttonAttachDesc = findViewById(R.id.attachDesc);
        ToggleButton buttonAttachImage = findViewById(R.id.attachImage);
        ToggleButton buttonAttachMap = findViewById(R.id.attachMap);

        final DescriptionFragment descFragment = new DescriptionFragment();
        final ImageFragment imageFragment = new ImageFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();

        String city = intent.getStringExtra(city_dest);
        TextView cityView = findViewById(R.id.city_detail);
        cityView.setText(city);

        String country=intent.getStringExtra(country_dest);
        String continent=intent.getStringExtra(continent_dest);
        String cost=intent.getStringExtra(cost_dest);

        String longitude=intent.getStringExtra(longitude_dest);
        String latitude=intent.getStringExtra(latitude_dest);


        TextView countryView=findViewById(R.id.country_detail);
        TextView continentView=findViewById(R.id.continent_detail);
        TextView costView=findViewById(R.id.cost_detail);

        countryView.setText(country);
        continentView.setText(continent);
        costView.setText(cost);


        buttonAttachDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = intent.getStringExtra(description_dest);
                Bundle bundle = new Bundle();
                bundle.putString("description", description);
                descFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(descFragment.isAdded()) {
                    fragmentTransaction.remove(descFragment);
                } else {
                    fragmentTransaction.add(R.id.LinearLayoutCompat, descFragment);
                }
                fragmentTransaction.commit();
            }
        });

        buttonAttachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUrl = intent.getStringExtra(url_dest);
                Bundle bundle = new Bundle();
                bundle.putString("img", imageUrl);
                imageFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(imageFragment.isAdded()) {
                    fragmentTransaction.remove(imageFragment);
                } else {
                    fragmentTransaction.add(R.id.LinearLayoutCompat, imageFragment, "ImageFrag");
                }
                fragmentTransaction.commit();
            }
        });

        buttonAttachMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double longitudeValue = Double.parseDouble(longitude);
                double latitudeValue = Double.parseDouble(latitude);

                String uri = "geo:" + latitudeValue + "," + longitudeValue;
                Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapsIntent);
            }
        });

    }
}