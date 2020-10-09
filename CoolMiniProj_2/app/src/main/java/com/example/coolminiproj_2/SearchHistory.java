package com.example.coolminiproj_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SearchHistory extends AppCompatActivity {
    ListView myListView;
    public DrawerLayout historyDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar_history = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar_history);

        historyDrawer = findViewById(R.id.drawer_history_layout);

        NavigationView navigationView_history = findViewById(R.id.nav_view_history);

        ActionBarDrawerToggle toggle_history = new ActionBarDrawerToggle(
                this, historyDrawer, toolbar_history, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //toggle.setDrawerArrowDrawable();
        historyDrawer.addDrawerListener(toggle_history);

        toggle_history.syncState();
        navigationView_history.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // Handle navigation view item clicks here.

                int id = menuItem.getItemId();

                if (id == R.id.nav_home) {
                    Intent myIntent = new Intent(SearchHistory.this, MainActivity.class);
                    startActivity(myIntent);
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();

                } else if (id == R.id.nav_recent_searches) {
                    Toast.makeText(getApplicationContext(), "Search History", Toast.LENGTH_LONG).show();
                    historyDrawer.closeDrawers();

                } else if (id == R.id.nav_my_library) {
                    Intent myIntent = new Intent(SearchHistory.this, MyLibraryActivity.class);
                    startActivity(myIntent);
                    Toast.makeText(getApplicationContext(), "My Library", Toast.LENGTH_LONG).show();
                }

                DrawerLayout drawer = findViewById(R.id.drawer_history_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }



        });

        myListView = findViewById(R.id.mylistview);


        AdapterHistory histoyAdapter = new AdapterHistory(getApplicationContext(), DisplaySearchResults.name_recentSearchList, DisplaySearchResults.number_recentSearchList);
        myListView.setAdapter(histoyAdapter);

        FloatingActionButton fab = findViewById(R.id.floating_search_history);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SearchHistory.this, MainActivity.class);
                startActivity(myIntent);
            }

        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_history_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void deleteAllHistory(View view) {
        AlertDialog.Builder oneResult_aDialogBuilder = new AlertDialog.Builder(this, R.style.CustomAlert)
                .setMessage("Are you sure you want to delete all your " + DisplaySearchResults.name_recentSearchList.size() + " recent searches? This action cannot be undone.. ")
                .setTitle("Deleting My Library...")
                .setPositiveButton("Yes. Delete ALL!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DisplaySearchResults.name_recentSearchList.clear();
                    }
                })
                .setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog myAlertDialog = oneResult_aDialogBuilder.create();
        myAlertDialog.show();
    }
    public void goHome(View view){
        Intent myIntent = new Intent(SearchHistory.this, MainActivity.class);
        startActivity(myIntent);
    }
    /*
    * public void sendSearchText(View view) {
        Intent intent = new Intent(this, DisplaySearchResults.class);
        TextView userInput = (TextView) findViewById(R.id.user_input_textField);
        firstUserInput = userInput.getText().toString();

        intent.putExtra("USERINPUT", firstUserInput);
        System.out.println("---------------the search input string----------------------" + firstUserInput);
        startActivity(intent);
    }
    * */

}
