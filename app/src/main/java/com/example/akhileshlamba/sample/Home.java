package com.example.akhileshlamba.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.akhileshlamba.entities.House;
import com.example.akhileshlamba.entities.ResCredientials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Handler handler;
    private TextView tempid;
    private ImageView imageid;
    private TextView addressid;
    private TextClock textClock;
    int count = 0;

    public Home(){
        handler = new Handler();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = (Bundle) intent.getParcelableExtra("profile");
        House house = (House) bundle.getParcelable("house");
        ResCredientials resCredientials = (ResCredientials) bundle.getParcelable("credientials");

        Log.i("House", String.valueOf(house.getResid()));

        tempid = (TextView) findViewById(R.id.tempID);
        imageid = (ImageView) findViewById(R.id.imageTempID);
        textClock = (TextClock) findViewById(R.id.clockID);
        addressid = (TextView) findViewById(R.id.addressID);

        textClock.setFormat12Hour(TextClock.DEFAULT_FORMAT_12_HOUR);

        updateWeather();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
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

        if(id == R.id.nav_camera){

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void updateWeather(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while(!isInterrupted()){
                        final JSONObject json = Weather.getWeatherJSON( "Melbourne");
                        final Bitmap image = Weather.getImageData(json.getJSONArray("weather")
                                .getJSONObject(0).get("icon").toString());

                        if (json != null) {
                            handler.post(new Runnable() {
                                public void run() {
                                    renderWeather(json, image);
                                }
                            });
                        }
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void renderWeather(JSONObject object, Bitmap image){
        try{
            tempid.setText(object.getJSONObject("main").get("temp").toString());
            imageid.setImageBitmap(image);
            addressid.setText("0");
        }catch(Exception e){

        }
    }

}