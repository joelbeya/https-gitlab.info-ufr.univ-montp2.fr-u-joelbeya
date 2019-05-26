package com.example.easycourse;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import butterknife.ButterKnife;


public class Final_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SearchView sv;
    private static final String TAG = Final_MainActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_activity_main);

        ButterKnife.bind(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        sv = findViewById(R.id.mSearch);
        RecyclerView rv = findViewById(R.id.myRecycler);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        final Final_Adapter finalAdapter = new Final_Adapter(this, getEleves());
        rv.setAdapter(finalAdapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                finalAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ArrayList<Eleve> getEleves() {
        ArrayList<Eleve> eleven = new ArrayList<>();
        Eleve e;
        e = new Eleve();
        e.setName("Ander HERERA");
        e.setPos("Premiere");
        e.setImg(R.drawable.herera);
        eleven.add(e);

        e = new Eleve();
        e.setName("David De Geaa");
        e.setPos("Terminal");
        e.setImg(R.drawable.degea);
        eleven.add(e);

        e = new Eleve();
        e.setName("Michael CARRICK");
        e.setPos("CP2");
        e.setImg(R.drawable.carrick);
        eleven.add(e);

        e = new Eleve();
        e.setName("Juan MATA");
        e.setPos("Terminal S");
        e.setImg(R.drawable.mata);
        eleven.add(e);

        e = new Eleve();
        e.setName("Premiere");
        e.setPos("5");
        e.setImg(R.drawable.costa);
        eleven.add(e);

        e = new Eleve();
        e.setName("OSCAR");
        e.setPos("Seconde");
        e.setImg(R.drawable.oscar);
        eleven.add(e);

        e = new Eleve();
        e.setName("Michael CARRICK");
        e.setPos("CP2");
        e.setImg(R.drawable.carrick);
        eleven.add(e);

        e = new Eleve();
        e.setName("Juan MATA");
        e.setPos("Terminal S");
        e.setImg(R.drawable.mata);
        eleven.add(e);

        e = new Eleve();
        e.setName("Diego COSTA");
        e.setPos("Premiere");
        e.setImg(R.drawable.costa);
        eleven.add(e);

        e = new Eleve();
        e.setName("OSCAR");
        e.setPos("Seconde");
        e.setImg(R.drawable.oscar);
        eleven.add(e);


        e = new Eleve();
        e.setName("Michael CARRICK");
        e.setPos("CP2");
        e.setImg(R.drawable.carrick);
        eleven.add(e);

        e = new Eleve();
        e.setName("Juan MATA");
        e.setPos("Terminal S");
        e.setImg(R.drawable.mata);
        eleven.add(e);

        return  eleven;
        }
    }
