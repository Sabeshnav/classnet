package com.example.classnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class activity_class_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    public String class_name, class_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_main);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_class_main);
        navigationView.setNavigationItemSelectedListener(this);
        class_name = getIntent().getStringExtra("class_name");
        class_id = getIntent().getStringExtra("class_id");
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor;
        prefsEditor = myPrefs.edit();
        prefsEditor.putString("s_class_id", class_id);
        prefsEditor.putString("s_class_name", class_name);
        prefsEditor.commit();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_class, fragment_class_general.class, null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.main_nav_general)
        {
            SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor;
            prefsEditor = myPrefs.edit();
            prefsEditor.putString("s_class_id", class_id);
            prefsEditor.putString("s_class_name", class_name);
            prefsEditor.commit();
            Log.e("home", "yes");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_class, fragment_class_general.class, null)
                    .commit();
        }
        else if(id == R.id.main_nav_online_test)
        {
            SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor;
            prefsEditor = myPrefs.edit();
            prefsEditor.putString("s_class_id", class_id);
            prefsEditor.putString("s_class_name", class_name);
            prefsEditor.commit();
            Log.e("AWAY ", "yes");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_class, fragment_class_test.class, null)
                    .commit();
        }
        else
        {
            SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor;
            prefsEditor = myPrefs.edit();
            prefsEditor.putString("s_class_id", class_id);
            prefsEditor.putString("s_class_name", class_name);
            prefsEditor.commit();
            Log.e("AWAY ", "yes");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_class, fragment_class_my_marks.class, null)
                    .commit();
        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}