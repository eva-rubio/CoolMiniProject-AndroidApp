package com.example.coolminiproj_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyLibraryActivity extends AppCompatActivity {
    public Toolbar toolbar_library;
    public static List<RetroBook> savedBooks = new ArrayList<RetroBook>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FloatingActionButton fabLibrary;
        RecyclerView recyclerView_myLibrary;
        MyBookAdapter mbAdapter;
        RecyclerView.LayoutManager layoutManager_myLibrary;
        TextView numOfSavedBooks;
        RelativeLayout relative404;
        MaterialButton search404btn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);

        toolbar_library = findViewById(R.id.toolbar_library);
        setSupportActionBar(toolbar_library);

        //toolbar_library.setLogo(R.drawable.mylibrary_icon);

        fabLibrary = findViewById(R.id.floating_search_MyLib);
        fabLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MyLibraryActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        numOfSavedBooks = (TextView) findViewById(R.id.numOfSavedBooks);
        int amountsaved = savedBooks.size();
        numOfSavedBooks.setText(String.valueOf(amountsaved));

        recyclerView_myLibrary = (RecyclerView) findViewById(R.id.my_recycler_view_library);
        relative404 = (RelativeLayout) findViewById(R.id.relative_404);
        search404btn = (MaterialButton) findViewById(R.id.search_btn_if404);


        if (amountsaved == 0) {
            recyclerView_myLibrary.setVisibility(View.GONE);
            search404btn.setVisibility(View.VISIBLE);
            search404btn.setText(R.string.lets_search);
            search404btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(MyLibraryActivity.this, MainActivity.class);
                    startActivity(myIntent);
                }
            });
            relative404.setVisibility(View.VISIBLE);
            fabLibrary.hide();

        } else if (amountsaved != 0 || savedBooks != null) {
            relative404.setVisibility(View.GONE);
            fabLibrary.show();
            mbAdapter = new MyBookAdapter(this, true);

            // 'layoutManager_myLibrary' handles the positioning of the items in the list and the scrolling behavior.
            layoutManager_myLibrary = new LinearLayoutManager(MyLibraryActivity.this);

            recyclerView_myLibrary.setLayoutManager(layoutManager_myLibrary);
            recyclerView_myLibrary.setAdapter(mbAdapter);
            recyclerView_myLibrary.setVisibility(View.VISIBLE);
        }
    }

    /**Checks if the key provided has already been saved.
     * @param oneKey - the unique id to find in 'savedBooks'
     *
     * @return the int position in which this book is in the 'savedBooks' list. (-10 if not saved)*/
    public static int findBookByIdKeyInLibrary(String oneKey) {

        for (int i = 0; i < MyLibraryActivity.savedBooks.size(); i++) {
            String theKeyInLib = MyLibraryActivity.savedBooks.get(i).getKey_unique();
            if (theKeyInLib.equals(oneKey))
                return i;
        }
        return -10;
    }

    public void deleteAllLibrary(View view) {
        AlertDialog.Builder oneResult_aDialogBuilder = new AlertDialog.Builder(this, R.style.CustomAlert)
                .setMessage("Are you sure you want to delete all your " + savedBooks.size() + " saved book(s)? This action cannot be undone.. ")
                .setTitle("Delete My Library")
                .setPositiveButton("Yes. Delete ALL!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savedBooks.clear();

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
    public void refreshLib(View v){
        LinearLayout libLayout= (LinearLayout) findViewById(R.id.linearLayout_insideMyLibrary);
        libLayout.invalidate();
        libLayout.postInvalidate();

    }

}