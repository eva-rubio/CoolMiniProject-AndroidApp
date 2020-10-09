package com.example.coolminiproj_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static android.view.View.INVISIBLE;


/**
 * THE ACTIVITY LIFECYCLE:
 *
 *  (1) onCreate() -    ~ Gets called when the system creates my activity.
 *                      ~ Where essential components are initialized.
 *           > As onCreate() exits, the activity enters the Started state, and the activity becomes visible to the user.
 *
 *  (2) onStart() -     ~ Contains lo que equivale a los preparativos finales de la actividad para llegar al primer plano y ser interactivo.
 *  this method is where the app initializes the code that maintains the UI.
 *
 *  (3) onResume() -    ~ The system invokes this callback just before the activity starts interacting with the user.
 *                      ~ Right now, the activity is at the top of the activity stack, and captures all user input.
 *                      ~ Most of an app’s core functionality is implemented in the onResume() method.
 *
 *  (4) onPause() -     ~ Called by the system when the activity loses focus and enters a Paused state.
 *                      ~ When the system calls onPause() for my activity, it technically means your activity is still partially visible,
 *                          but most oftenly, means the user is leaving the activity, and the activity will soon enter the Stopped or Resumed state.
 *                      ~ Once onPause() finishes executing, the next callback is either onStop() or onResume(), depending on what happens after the activity enters the Paused state.
 *                      ~ Ex: This state occurs when the user taps the Back or Recents button.
 *
 *  (5) onStop() -     ~ Called by the system when the activity is no longer visible to the user.
 *                     ~ May happen because: - the activity is being destroyed,
 *                                           - a new activity is starting,
 *                                           - or an existing activity is entering a Resumed state and is covering the stopped activity.
 *                     ~  In all of these cases, the stopped activity is no longer visible at all.
 *
 *                     ~ The next callback that the system calls is either:
 *                          * onRestart(), if the activity is coming back to interact with the user,
 *                          * or onDestroy() if this activity is completely terminating.
 *
 * (6) onRestart()     ~ The system invokes this callback when an activity in the Stopped state is about to restart.
 *                     ~ It restores the state of the activity from the time that it was stopped.
 *                     ~ This callback is always followed by onStart().
 *
 *
 * (7) onDestroy()     ~ The system invokes this callback before an activity is destroyed.
 *                     ~ This callback is the final one that the activity receives.
 *                     ~ Usually implemented to ensure that all of an activity’s resources are released when the activity, or the process containing it, is destroyed.
 *
 * */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public String firstUserInput;
    public DrawerLayout mainDrawer;

    /**
     * A callback function that is called when the system creates this activity.
     * Here is where the essential components of the activity get initialized.
     * Ex: - here is where the views are created and data is bind to lists.
     *     - Where setContentView() is called to define the layout for the activity's user interface.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets the user interface layout for this activity
        setContentView(R.layout.activity_main);

        Toolbar toolbar_main = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);

         mainDrawer = findViewById(R.id.drawer_layout_main);

         NavigationView navigationView = findViewById(R.id.nav_view_main);

         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
         this, mainDrawer, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

         //toggle.setDrawerArrowDrawable();
         mainDrawer.addDrawerListener(toggle);


         toggle.syncState();
         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                     // Handle navigation view item clicks here.

                     int id = menuItem.getItemId();

                     if (id == R.id.nav_home) {
                         Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                         mainDrawer.closeDrawers();

                     } else if (id == R.id.nav_recent_searches) {

                         Intent myIntent = new Intent(MainActivity.this, SearchHistory.class);
                         startActivity(myIntent);
                         Toast.makeText(getApplicationContext(), "Recent Searches", Toast.LENGTH_LONG).show();

                     } else if (id == R.id.nav_my_library) {
                         Intent myIntent = new Intent(MainActivity.this, MyLibraryActivity.class);
                         Toast.makeText(getApplicationContext(), "My Library", Toast.LENGTH_LONG).show();
                         startActivity(myIntent);
                     }
                     DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
                     drawer.closeDrawer(GravityCompat.START);
                     return true;
                 }



         });


        final EditText userInput = (EditText) findViewById(R.id.user_input_textField);

        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent(MainActivity.this, DisplaySearchResults.class);
                    String searchString = userInput.getText().toString();

                    //String fortmattedStr = searchString.replace(' ', '+');
                    intent.putExtra(getString(R.string.intent_that_started_search), searchString);
                    intent.putExtra(getString(R.string.origin_of_search_value), getString(R.string.intent_from_main_activity));
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });
    }
    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DisplaySearchResults.class);
        int theID = v.getId();
        String textOnBtn;
        MaterialButton btnPresed = (MaterialButton) findViewById(theID);
        textOnBtn = btnPresed.getText().toString();

        intent.putExtra(getString(R.string.intent_that_started_search), textOnBtn);
        intent.putExtra(getString(R.string.origin_of_search_value), getString(R.string.intent_from_main_activity));

        startActivity(intent);
    }

    /**
     * Called when the user taps the Search button
     */
    public void sendSearchText(View view) {
        Intent intent = new Intent(this, DisplaySearchResults.class);
        EditText userInput = (EditText) findViewById(R.id.user_input_textField);
        firstUserInput = userInput.getText().toString();

        intent.putExtra(getString(R.string.intent_that_started_search), firstUserInput);
        System.out.println("---------------the search input string----------------------" + firstUserInput);
        intent.putExtra(getString(R.string.origin_of_search_value), getString(R.string.intent_from_main_activity));

        startActivity(intent);
    }

    public void switchController(View view){
        Switch subjectsSwitch = (Switch) findViewById(R.id.switch1_subjects);
        Switch authorsSwitch = (Switch) findViewById(R.id.switch2_authors);
        Switch titlesSwitch = (Switch) findViewById(R.id.switch3_titles);

        MaterialButton b_a1 = (MaterialButton) findViewById(R.id.one_author_btn);
        MaterialButton b_a2 = (MaterialButton) findViewById(R.id.two_author_btn);
        MaterialButton b_a3 = (MaterialButton) findViewById(R.id.three_author_btn);
        //     MaterialButton b_a4 = (MaterialButton) findViewById(R.id.four_author_btn);
        MaterialButton b_t1 = (MaterialButton) findViewById(R.id.one_title_btn);
        MaterialButton b_t2 = (MaterialButton) findViewById(R.id.two_title_btn);
        MaterialButton b_t3 = (MaterialButton) findViewById(R.id.three_title_btn);
        //      MaterialButton b_t4 = (MaterialButton) findViewById(R.id.four_title_btn);
        MaterialButton b_s1 = (MaterialButton) findViewById(R.id.one_subject_btn);
        MaterialButton b_s2 = (MaterialButton) findViewById(R.id.two_subject_btn);
        MaterialButton b_s3 = (MaterialButton) findViewById(R.id.three_subject_btn);
         //MaterialButton b_s4 = (MaterialButton) findViewById(R.id.four_subject_btn);

        if(!subjectsSwitch.isChecked()){
            b_s1.setVisibility(INVISIBLE);
            b_s2.setVisibility(INVISIBLE);
            b_s3.setVisibility(INVISIBLE);
        }else{
            b_s1.setVisibility(View.VISIBLE);
            b_s2.setVisibility(View.VISIBLE);
            b_s3.setVisibility(View.VISIBLE);
        }
        if(!authorsSwitch.isChecked()){
            b_a1.setVisibility(INVISIBLE);
            b_a2.setVisibility(INVISIBLE);
            b_a3.setVisibility(INVISIBLE);
        }else{
            b_a1.setVisibility(View.VISIBLE);
            b_a2.setVisibility(View.VISIBLE);
            b_a3.setVisibility(View.VISIBLE);
        }
        if(!titlesSwitch.isChecked()){
            b_t1.setVisibility(INVISIBLE);
            b_t2.setVisibility(INVISIBLE);
            b_t3.setVisibility(INVISIBLE);
        }else{
            b_t1.setVisibility(View.VISIBLE);
            b_t2.setVisibility(View.VISIBLE);
            b_t3.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
     int id = item.getItemId();


       switch (id){
           case R.id.action_about_booktrotter:
               AlertDialog.Builder abt_booktrotter_aDialogBuilder = new AlertDialog.Builder(this, R.style.CustomAlert)
                       .setMessage("In your search for your new adventure, BookTrotter is here to assist! By introducing a query, we'll help you find everything you might need!")
                       .setTitle("About BookTrotter")
                       .setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                              }
                       });
               AlertDialog myAlertDialog = abt_booktrotter_aDialogBuilder.create();
               myAlertDialog.show();

               return true;
           default:
               return super.onOptionsItemSelected(item);
       }


    }

    /**
     *  For generating random meaningful english words.
     *  http://app.aspell.net/create?defaults=en_US
     * */



}
