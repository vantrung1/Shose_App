package edu.fpt.shose_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import edu.fpt.shose_app.Adapter.Brand_Adapter;
import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.R;


public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerViewBrand;
    ArrayList<Brand> brandArrayList;
    FloatingActionButton floatingActionButton;
    NavigationView navigationView_home;
    DrawerLayout drawerLayout_home;
    Brand_Adapter brand_adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        brandArrayList = new ArrayList<>();

        brand_adapter = new Brand_Adapter(this,brandArrayList);
        initUi();
        initAction();

    }

    private void initAction() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        // Handle home button click
                        Log.d("TAG", "onNavigationItemSelected: Home");
                        return true;
                    case R.id.Favorite:
                        // Handle profile button click
                        Log.d("TAG", "onNavigationItemSelected: Favorite");
                        return true;
                    case R.id.Notifications:
                        // Handle settings button click
                        Log.d("TAG", "onNavigationItemSelected: Notifications");
                        return true;
                    case R.id.Profile:
                        // Handle settings button click
                        Log.d("TAG", "onNavigationItemSelected: Profile");
                        return true;
                }
                return false;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomeActivity.this, MyCartActivity.class);
                startActivity(i);
            }
        });
    }

    private void initUi() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        floatingActionButton = findViewById(R.id.fab_home);
        navigationView_home = findViewById(R.id.navigation);
        drawerLayout_home = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.iconmenu);

        recyclerViewBrand = findViewById(R.id.RecyclerViewHome_brand);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBrand.setLayoutManager(layoutManager);

        recyclerViewBrand.setAdapter(brand_adapter);
        brandArrayList.add(new Brand(1,"nike","","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAhFBMVEX///8REREAAAAODg4ICAgGBgb6+vpqamr8/Pz39/fg4OAvLy/X19fw8PDd3d2Li4uxsbHp6em7u7snJydzc3PCwsKenp6BgYHGxsY/Pz+Tk5NfX18sLCxRUVF8fHzS0tI2Njanp6dMTEw9PT2Hh4cdHR2ZmZlaWlpOTk6srKxkZGQXFxcdh3xcAAAEPElEQVR4nO3a2ZaqMBAF0KbEARQFRHBGnLja//9/l4DYDkxCArjW2U/9YqCoSkKK/vkBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgPZQm76BJAOF31i6wW8sPgaqZ4/4DWee+Y3Fg6Fb+yXPhz6SOZZDVdrcWdDe7PEc0yCb53BVuOcFEW05rwralPgOWE5P9VZBdLTzeBfU4B8tOQ/5OVW3hiw8us75D94nanYh1WbbQxCb3CU6iti0JkR9AcMWNHCXuyC6bqdDNNQHIi5xIYlmIgbOp4ztVRidJMnBYxZQnoxLHXnaxFah6n028YLoJKlLJ66b3yONZKn+ItXMLYtOZtFJrDz5bn5PDiTVXKTKfLlm0YXJi8qT47vZG4vYM6xtJVVGZ/+eOyZI31no1T1iT3Eo8hJ/xpfrNJ54cXluBJYn44aXq2MaaqZ1ekoeW13IGgu+7EAKryj6nVSbT4Yv0bHy3NniV/A+RRczxV1Cc5f+W3SsPFczIZv7M51uj1PQTvsz8vzT08S7r55OLQ0Fo3t7suSKGF236D150ebn1ZA+xqd4SvDejozZcZeQuyg+EUeHZHGNcq5SzZ1sknIXlac0qa/fpfw9Yn6vNCN7IyUnL0zfWhe7+T3b3lPIZ7cYGJc+pUUXvZuJmO7pxg+3QquqoxnmNmXi3a5Aa2FHhzTXvxRKnVOV4lHmxz0746VFx8qTc9+sCJce76H8UuPa+4zSvJWnJfLokMZ/jrBMmfZUL2vixeU5PGvc776AMb3cyKeLgGpamRMvLk9/Vnt5RvqvER4++LEycw5pO95zeW5FHx1SaW8Pn6xivxy458OtdZSXvqHdUPoYm95uqUBLWBnb18xF8zG+VW3vZokW7xFK5GT+5LExlkMW1Nb9wOs6cwtxkbaoK7PtusDEi9O39hosz8gyMUKJtWRf762nmZPdQ2MsP33Xet/Nkm2SIwx3L8sbqYYWMMYj/djfFZt4kS5Nl634VK5kzCaZ7eG/00D4V7HKDAXluaj16JBhnpbC+F47six3ipXlw5MR2tb9TMJeUVFQ3bUfHbJYnCNkbd0W/RNA4FB46ShArqGt+7HcV67i2LtZu9IX4lWk4dGh6WAS8YmQvZu1rjxvOEQYpG/K/T9C+Kk8D1l5Nnt0yLGotpZ26bfGtm4pToUyDdK3u7S3PG/00hG25eiQRysZYRNt3ZJWZUIk2uutL89Y3uHiXVNt3dL8z0IMyrOZtm55aqGOUoRtfmZN32w5uhRNYqNt3UoK7Ylh3+xrVpdXTl6hBuH9Wt+w+aWyM3toRLQyv2x1eeMOKbFUO6zL5uvfHl7IXLz06cPgaLpt6pOYAO4k/IR7t/adWcvPDZ8zRpflxHGOk7PuGl+7cAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAd/gOVsC9keTWc5QAAAABJRU5ErkJggg=="));
        brandArrayList.add(new Brand(2,"nike","","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAhFBMVEX///8REREAAAAODg4ICAgGBgb6+vpqamr8/Pz39/fg4OAvLy/X19fw8PDd3d2Li4uxsbHp6em7u7snJydzc3PCwsKenp6BgYHGxsY/Pz+Tk5NfX18sLCxRUVF8fHzS0tI2Njanp6dMTEw9PT2Hh4cdHR2ZmZlaWlpOTk6srKxkZGQXFxcdh3xcAAAEPElEQVR4nO3a2ZaqMBAF0KbEARQFRHBGnLja//9/l4DYDkxCArjW2U/9YqCoSkKK/vkBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgPZQm76BJAOF31i6wW8sPgaqZ4/4DWee+Y3Fg6Fb+yXPhz6SOZZDVdrcWdDe7PEc0yCb53BVuOcFEW05rwralPgOWE5P9VZBdLTzeBfU4B8tOQ/5OVW3hiw8us75D94nanYh1WbbQxCb3CU6iti0JkR9AcMWNHCXuyC6bqdDNNQHIi5xIYlmIgbOp4ztVRidJMnBYxZQnoxLHXnaxFah6n028YLoJKlLJ66b3yONZKn+ItXMLYtOZtFJrDz5bn5PDiTVXKTKfLlm0YXJi8qT47vZG4vYM6xtJVVGZ/+eOyZI31no1T1iT3Eo8hJ/xpfrNJ54cXluBJYn44aXq2MaaqZ1ekoeW13IGgu+7EAKryj6nVSbT4Yv0bHy3NniV/A+RRczxV1Cc5f+W3SsPFczIZv7M51uj1PQTvsz8vzT08S7r55OLQ0Fo3t7suSKGF236D150ebn1ZA+xqd4SvDejozZcZeQuyg+EUeHZHGNcq5SzZ1sknIXlac0qa/fpfw9Yn6vNCN7IyUnL0zfWhe7+T3b3lPIZ7cYGJc+pUUXvZuJmO7pxg+3QquqoxnmNmXi3a5Aa2FHhzTXvxRKnVOV4lHmxz0746VFx8qTc9+sCJce76H8UuPa+4zSvJWnJfLokMZ/jrBMmfZUL2vixeU5PGvc776AMb3cyKeLgGpamRMvLk9/Vnt5RvqvER4++LEycw5pO95zeW5FHx1SaW8Pn6xivxy458OtdZSXvqHdUPoYm95uqUBLWBnb18xF8zG+VW3vZokW7xFK5GT+5LExlkMW1Nb9wOs6cwtxkbaoK7PtusDEi9O39hosz8gyMUKJtWRf762nmZPdQ2MsP33Xet/Nkm2SIwx3L8sbqYYWMMYj/djfFZt4kS5Nl634VK5kzCaZ7eG/00D4V7HKDAXluaj16JBhnpbC+F47six3ipXlw5MR2tb9TMJeUVFQ3bUfHbJYnCNkbd0W/RNA4FB46ShArqGt+7HcV67i2LtZu9IX4lWk4dGh6WAS8YmQvZu1rjxvOEQYpG/K/T9C+Kk8D1l5Nnt0yLGotpZ26bfGtm4pToUyDdK3u7S3PG/00hG25eiQRysZYRNt3ZJWZUIk2uutL89Y3uHiXVNt3dL8z0IMyrOZtm55aqGOUoRtfmZN32w5uhRNYqNt3UoK7Ylh3+xrVpdXTl6hBuH9Wt+w+aWyM3toRLQyv2x1eeMOKbFUO6zL5uvfHl7IXLz06cPgaLpt6pOYAO4k/IR7t/adWcvPDZ8zRpflxHGOk7PuGl+7cAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAd/gOVsC9keTWc5QAAAABJRU5ErkJggg=="));
        brandArrayList.add(new Brand(3,"nike","","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAhFBMVEX///8REREAAAAODg4ICAgGBgb6+vpqamr8/Pz39/fg4OAvLy/X19fw8PDd3d2Li4uxsbHp6em7u7snJydzc3PCwsKenp6BgYHGxsY/Pz+Tk5NfX18sLCxRUVF8fHzS0tI2Njanp6dMTEw9PT2Hh4cdHR2ZmZlaWlpOTk6srKxkZGQXFxcdh3xcAAAEPElEQVR4nO3a2ZaqMBAF0KbEARQFRHBGnLja//9/l4DYDkxCArjW2U/9YqCoSkKK/vkBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgPZQm76BJAOF31i6wW8sPgaqZ4/4DWee+Y3Fg6Fb+yXPhz6SOZZDVdrcWdDe7PEc0yCb53BVuOcFEW05rwralPgOWE5P9VZBdLTzeBfU4B8tOQ/5OVW3hiw8us75D94nanYh1WbbQxCb3CU6iti0JkR9AcMWNHCXuyC6bqdDNNQHIi5xIYlmIgbOp4ztVRidJMnBYxZQnoxLHXnaxFah6n028YLoJKlLJ66b3yONZKn+ItXMLYtOZtFJrDz5bn5PDiTVXKTKfLlm0YXJi8qT47vZG4vYM6xtJVVGZ/+eOyZI31no1T1iT3Eo8hJ/xpfrNJ54cXluBJYn44aXq2MaaqZ1ekoeW13IGgu+7EAKryj6nVSbT4Yv0bHy3NniV/A+RRczxV1Cc5f+W3SsPFczIZv7M51uj1PQTvsz8vzT08S7r55OLQ0Fo3t7suSKGF236D150ebn1ZA+xqd4SvDejozZcZeQuyg+EUeHZHGNcq5SzZ1sknIXlac0qa/fpfw9Yn6vNCN7IyUnL0zfWhe7+T3b3lPIZ7cYGJc+pUUXvZuJmO7pxg+3QquqoxnmNmXi3a5Aa2FHhzTXvxRKnVOV4lHmxz0746VFx8qTc9+sCJce76H8UuPa+4zSvJWnJfLokMZ/jrBMmfZUL2vixeU5PGvc776AMb3cyKeLgGpamRMvLk9/Vnt5RvqvER4++LEycw5pO95zeW5FHx1SaW8Pn6xivxy458OtdZSXvqHdUPoYm95uqUBLWBnb18xF8zG+VW3vZokW7xFK5GT+5LExlkMW1Nb9wOs6cwtxkbaoK7PtusDEi9O39hosz8gyMUKJtWRf762nmZPdQ2MsP33Xet/Nkm2SIwx3L8sbqYYWMMYj/djfFZt4kS5Nl634VK5kzCaZ7eG/00D4V7HKDAXluaj16JBhnpbC+F47six3ipXlw5MR2tb9TMJeUVFQ3bUfHbJYnCNkbd0W/RNA4FB46ShArqGt+7HcV67i2LtZu9IX4lWk4dGh6WAS8YmQvZu1rjxvOEQYpG/K/T9C+Kk8D1l5Nnt0yLGotpZ26bfGtm4pToUyDdK3u7S3PG/00hG25eiQRysZYRNt3ZJWZUIk2uutL89Y3uHiXVNt3dL8z0IMyrOZtm55aqGOUoRtfmZN32w5uhRNYqNt3UoK7Ylh3+xrVpdXTl6hBuH9Wt+w+aWyM3toRLQyv2x1eeMOKbFUO6zL5uvfHl7IXLz06cPgaLpt6pOYAO4k/IR7t/adWcvPDZ8zRpflxHGOk7PuGl+7cAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAd/gOVsC9keTWc5QAAAABJRU5ErkJggg=="));
        brandArrayList.add(new Brand(4,"nike","","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAhFBMVEX///8REREAAAAODg4ICAgGBgb6+vpqamr8/Pz39/fg4OAvLy/X19fw8PDd3d2Li4uxsbHp6em7u7snJydzc3PCwsKenp6BgYHGxsY/Pz+Tk5NfX18sLCxRUVF8fHzS0tI2Njanp6dMTEw9PT2Hh4cdHR2ZmZlaWlpOTk6srKxkZGQXFxcdh3xcAAAEPElEQVR4nO3a2ZaqMBAF0KbEARQFRHBGnLja//9/l4DYDkxCArjW2U/9YqCoSkKK/vkBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgPZQm76BJAOF31i6wW8sPgaqZ4/4DWee+Y3Fg6Fb+yXPhz6SOZZDVdrcWdDe7PEc0yCb53BVuOcFEW05rwralPgOWE5P9VZBdLTzeBfU4B8tOQ/5OVW3hiw8us75D94nanYh1WbbQxCb3CU6iti0JkR9AcMWNHCXuyC6bqdDNNQHIi5xIYlmIgbOp4ztVRidJMnBYxZQnoxLHXnaxFah6n028YLoJKlLJ66b3yONZKn+ItXMLYtOZtFJrDz5bn5PDiTVXKTKfLlm0YXJi8qT47vZG4vYM6xtJVVGZ/+eOyZI31no1T1iT3Eo8hJ/xpfrNJ54cXluBJYn44aXq2MaaqZ1ekoeW13IGgu+7EAKryj6nVSbT4Yv0bHy3NniV/A+RRczxV1Cc5f+W3SsPFczIZv7M51uj1PQTvsz8vzT08S7r55OLQ0Fo3t7suSKGF236D150ebn1ZA+xqd4SvDejozZcZeQuyg+EUeHZHGNcq5SzZ1sknIXlac0qa/fpfw9Yn6vNCN7IyUnL0zfWhe7+T3b3lPIZ7cYGJc+pUUXvZuJmO7pxg+3QquqoxnmNmXi3a5Aa2FHhzTXvxRKnVOV4lHmxz0746VFx8qTc9+sCJce76H8UuPa+4zSvJWnJfLokMZ/jrBMmfZUL2vixeU5PGvc776AMb3cyKeLgGpamRMvLk9/Vnt5RvqvER4++LEycw5pO95zeW5FHx1SaW8Pn6xivxy458OtdZSXvqHdUPoYm95uqUBLWBnb18xF8zG+VW3vZokW7xFK5GT+5LExlkMW1Nb9wOs6cwtxkbaoK7PtusDEi9O39hosz8gyMUKJtWRf762nmZPdQ2MsP33Xet/Nkm2SIwx3L8sbqYYWMMYj/djfFZt4kS5Nl634VK5kzCaZ7eG/00D4V7HKDAXluaj16JBhnpbC+F47six3ipXlw5MR2tb9TMJeUVFQ3bUfHbJYnCNkbd0W/RNA4FB46ShArqGt+7HcV67i2LtZu9IX4lWk4dGh6WAS8YmQvZu1rjxvOEQYpG/K/T9C+Kk8D1l5Nnt0yLGotpZ26bfGtm4pToUyDdK3u7S3PG/00hG25eiQRysZYRNt3ZJWZUIk2uutL89Y3uHiXVNt3dL8z0IMyrOZtm55aqGOUoRtfmZN32w5uhRNYqNt3UoK7Ylh3+xrVpdXTl6hBuH9Wt+w+aWyM3toRLQyv2x1eeMOKbFUO6zL5uvfHl7IXLz06cPgaLpt6pOYAO4k/IR7t/adWcvPDZ8zRpflxHGOk7PuGl+7cAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAd/gOVsC9keTWc5QAAAABJRU5ErkJggg=="));
        brandArrayList.add(new Brand(5,"nike","","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAhFBMVEX///8REREAAAAODg4ICAgGBgb6+vpqamr8/Pz39/fg4OAvLy/X19fw8PDd3d2Li4uxsbHp6em7u7snJydzc3PCwsKenp6BgYHGxsY/Pz+Tk5NfX18sLCxRUVF8fHzS0tI2Njanp6dMTEw9PT2Hh4cdHR2ZmZlaWlpOTk6srKxkZGQXFxcdh3xcAAAEPElEQVR4nO3a2ZaqMBAF0KbEARQFRHBGnLja//9/l4DYDkxCArjW2U/9YqCoSkKK/vkBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgPZQm76BJAOF31i6wW8sPgaqZ4/4DWee+Y3Fg6Fb+yXPhz6SOZZDVdrcWdDe7PEc0yCb53BVuOcFEW05rwralPgOWE5P9VZBdLTzeBfU4B8tOQ/5OVW3hiw8us75D94nanYh1WbbQxCb3CU6iti0JkR9AcMWNHCXuyC6bqdDNNQHIi5xIYlmIgbOp4ztVRidJMnBYxZQnoxLHXnaxFah6n028YLoJKlLJ66b3yONZKn+ItXMLYtOZtFJrDz5bn5PDiTVXKTKfLlm0YXJi8qT47vZG4vYM6xtJVVGZ/+eOyZI31no1T1iT3Eo8hJ/xpfrNJ54cXluBJYn44aXq2MaaqZ1ekoeW13IGgu+7EAKryj6nVSbT4Yv0bHy3NniV/A+RRczxV1Cc5f+W3SsPFczIZv7M51uj1PQTvsz8vzT08S7r55OLQ0Fo3t7suSKGF236D150ebn1ZA+xqd4SvDejozZcZeQuyg+EUeHZHGNcq5SzZ1sknIXlac0qa/fpfw9Yn6vNCN7IyUnL0zfWhe7+T3b3lPIZ7cYGJc+pUUXvZuJmO7pxg+3QquqoxnmNmXi3a5Aa2FHhzTXvxRKnVOV4lHmxz0746VFx8qTc9+sCJce76H8UuPa+4zSvJWnJfLokMZ/jrBMmfZUL2vixeU5PGvc776AMb3cyKeLgGpamRMvLk9/Vnt5RvqvER4++LEycw5pO95zeW5FHx1SaW8Pn6xivxy458OtdZSXvqHdUPoYm95uqUBLWBnb18xF8zG+VW3vZokW7xFK5GT+5LExlkMW1Nb9wOs6cwtxkbaoK7PtusDEi9O39hosz8gyMUKJtWRf762nmZPdQ2MsP33Xet/Nkm2SIwx3L8sbqYYWMMYj/djfFZt4kS5Nl634VK5kzCaZ7eG/00D4V7HKDAXluaj16JBhnpbC+F47six3ipXlw5MR2tb9TMJeUVFQ3bUfHbJYnCNkbd0W/RNA4FB46ShArqGt+7HcV67i2LtZu9IX4lWk4dGh6WAS8YmQvZu1rjxvOEQYpG/K/T9C+Kk8D1l5Nnt0yLGotpZ26bfGtm4pToUyDdK3u7S3PG/00hG25eiQRysZYRNt3ZJWZUIk2uutL89Y3uHiXVNt3dL8z0IMyrOZtm55aqGOUoRtfmZN32w5uhRNYqNt3UoK7Ylh3+xrVpdXTl6hBuH9Wt+w+aWyM3toRLQyv2x1eeMOKbFUO6zL5uvfHl7IXLz06cPgaLpt6pOYAO4k/IR7t/adWcvPDZ8zRpflxHGOk7PuGl+7cAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAd/gOVsC9keTWc5QAAAABJRU5ErkJggg=="));
        brandArrayList.add(new Brand(6,"adi","","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAhFBMVEX///8REREAAAAODg4ICAgGBgb6+vpqamr8/Pz39/fg4OAvLy/X19fw8PDd3d2Li4uxsbHp6em7u7snJydzc3PCwsKenp6BgYHGxsY/Pz+Tk5NfX18sLCxRUVF8fHzS0tI2Njanp6dMTEw9PT2Hh4cdHR2ZmZlaWlpOTk6srKxkZGQXFxcdh3xcAAAEPElEQVR4nO3a2ZaqMBAF0KbEARQFRHBGnLja//9/l4DYDkxCArjW2U/9YqCoSkKK/vkBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgPZQm76BJAOF31i6wW8sPgaqZ4/4DWee+Y3Fg6Fb+yXPhz6SOZZDVdrcWdDe7PEc0yCb53BVuOcFEW05rwralPgOWE5P9VZBdLTzeBfU4B8tOQ/5OVW3hiw8us75D94nanYh1WbbQxCb3CU6iti0JkR9AcMWNHCXuyC6bqdDNNQHIi5xIYlmIgbOp4ztVRidJMnBYxZQnoxLHXnaxFah6n028YLoJKlLJ66b3yONZKn+ItXMLYtOZtFJrDz5bn5PDiTVXKTKfLlm0YXJi8qT47vZG4vYM6xtJVVGZ/+eOyZI31no1T1iT3Eo8hJ/xpfrNJ54cXluBJYn44aXq2MaaqZ1ekoeW13IGgu+7EAKryj6nVSbT4Yv0bHy3NniV/A+RRczxV1Cc5f+W3SsPFczIZv7M51uj1PQTvsz8vzT08S7r55OLQ0Fo3t7suSKGF236D150ebn1ZA+xqd4SvDejozZcZeQuyg+EUeHZHGNcq5SzZ1sknIXlac0qa/fpfw9Yn6vNCN7IyUnL0zfWhe7+T3b3lPIZ7cYGJc+pUUXvZuJmO7pxg+3QquqoxnmNmXi3a5Aa2FHhzTXvxRKnVOV4lHmxz0746VFx8qTc9+sCJce76H8UuPa+4zSvJWnJfLokMZ/jrBMmfZUL2vixeU5PGvc776AMb3cyKeLgGpamRMvLk9/Vnt5RvqvER4++LEycw5pO95zeW5FHx1SaW8Pn6xivxy458OtdZSXvqHdUPoYm95uqUBLWBnb18xF8zG+VW3vZokW7xFK5GT+5LExlkMW1Nb9wOs6cwtxkbaoK7PtusDEi9O39hosz8gyMUKJtWRf762nmZPdQ2MsP33Xet/Nkm2SIwx3L8sbqYYWMMYj/djfFZt4kS5Nl634VK5kzCaZ7eG/00D4V7HKDAXluaj16JBhnpbC+F47six3ipXlw5MR2tb9TMJeUVFQ3bUfHbJYnCNkbd0W/RNA4FB46ShArqGt+7HcV67i2LtZu9IX4lWk4dGh6WAS8YmQvZu1rjxvOEQYpG/K/T9C+Kk8D1l5Nnt0yLGotpZ26bfGtm4pToUyDdK3u7S3PG/00hG25eiQRysZYRNt3ZJWZUIk2uutL89Y3uHiXVNt3dL8z0IMyrOZtm55aqGOUoRtfmZN32w5uhRNYqNt3UoK7Ylh3+xrVpdXTl6hBuH9Wt+w+aWyM3toRLQyv2x1eeMOKbFUO6zL5uvfHl7IXLz06cPgaLpt6pOYAO4k/IR7t/adWcvPDZ8zRpflxHGOk7PuGl+7cAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAd/gOVsC9keTWc5QAAAABJRU5ErkJggg=="));
        brandArrayList.add(new Brand(7,"vans","","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAhFBMVEX///8REREAAAAODg4ICAgGBgb6+vpqamr8/Pz39/fg4OAvLy/X19fw8PDd3d2Li4uxsbHp6em7u7snJydzc3PCwsKenp6BgYHGxsY/Pz+Tk5NfX18sLCxRUVF8fHzS0tI2Njanp6dMTEw9PT2Hh4cdHR2ZmZlaWlpOTk6srKxkZGQXFxcdh3xcAAAEPElEQVR4nO3a2ZaqMBAF0KbEARQFRHBGnLja//9/l4DYDkxCArjW2U/9YqCoSkKK/vkBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgPZQm76BJAOF31i6wW8sPgaqZ4/4DWee+Y3Fg6Fb+yXPhz6SOZZDVdrcWdDe7PEc0yCb53BVuOcFEW05rwralPgOWE5P9VZBdLTzeBfU4B8tOQ/5OVW3hiw8us75D94nanYh1WbbQxCb3CU6iti0JkR9AcMWNHCXuyC6bqdDNNQHIi5xIYlmIgbOp4ztVRidJMnBYxZQnoxLHXnaxFah6n028YLoJKlLJ66b3yONZKn+ItXMLYtOZtFJrDz5bn5PDiTVXKTKfLlm0YXJi8qT47vZG4vYM6xtJVVGZ/+eOyZI31no1T1iT3Eo8hJ/xpfrNJ54cXluBJYn44aXq2MaaqZ1ekoeW13IGgu+7EAKryj6nVSbT4Yv0bHy3NniV/A+RRczxV1Cc5f+W3SsPFczIZv7M51uj1PQTvsz8vzT08S7r55OLQ0Fo3t7suSKGF236D150ebn1ZA+xqd4SvDejozZcZeQuyg+EUeHZHGNcq5SzZ1sknIXlac0qa/fpfw9Yn6vNCN7IyUnL0zfWhe7+T3b3lPIZ7cYGJc+pUUXvZuJmO7pxg+3QquqoxnmNmXi3a5Aa2FHhzTXvxRKnVOV4lHmxz0746VFx8qTc9+sCJce76H8UuPa+4zSvJWnJfLokMZ/jrBMmfZUL2vixeU5PGvc776AMb3cyKeLgGpamRMvLk9/Vnt5RvqvER4++LEycw5pO95zeW5FHx1SaW8Pn6xivxy458OtdZSXvqHdUPoYm95uqUBLWBnb18xF8zG+VW3vZokW7xFK5GT+5LExlkMW1Nb9wOs6cwtxkbaoK7PtusDEi9O39hosz8gyMUKJtWRf762nmZPdQ2MsP33Xet/Nkm2SIwx3L8sbqYYWMMYj/djfFZt4kS5Nl634VK5kzCaZ7eG/00D4V7HKDAXluaj16JBhnpbC+F47six3ipXlw5MR2tb9TMJeUVFQ3bUfHbJYnCNkbd0W/RNA4FB46ShArqGt+7HcV67i2LtZu9IX4lWk4dGh6WAS8YmQvZu1rjxvOEQYpG/K/T9C+Kk8D1l5Nnt0yLGotpZ26bfGtm4pToUyDdK3u7S3PG/00hG25eiQRysZYRNt3ZJWZUIk2uutL89Y3uHiXVNt3dL8z0IMyrOZtm55aqGOUoRtfmZN32w5uhRNYqNt3UoK7Ylh3+xrVpdXTl6hBuH9Wt+w+aWyM3toRLQyv2x1eeMOKbFUO6zL5uvfHl7IXLz06cPgaLpt6pOYAO4k/IR7t/adWcvPDZ8zRpflxHGOk7PuGl+7cAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAd/gOVsC9keTWc5QAAAABJRU5ErkJggg=="));
        brand_adapter.setBrandSelected(brandArrayList);
        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                drawerLayout_home.openDrawer (GravityCompat.START);
            }
        });
        navigationView_home.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnu_profile:

                        break;
                    case R.id.mnu_home:

                        break;
                    case R.id.mnu_cart:

                        break;
                    case R.id.mnu_favorite:

                        break;
                    case R.id.mnu_orders:

                        break;
                    case R.id.mnu_notifi:

                        break;
                    default:
                }
                drawerLayout_home.closeDrawer(navigationView_home);
                return false;
            }
        });
    }
}