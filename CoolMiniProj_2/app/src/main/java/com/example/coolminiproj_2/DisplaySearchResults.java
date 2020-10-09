package com.example.coolminiproj_2;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplaySearchResults extends AppCompatActivity {
    private RecyclerView recyclerView_displaySearch;
    private MyBookAdapter mbAdapter;
    private RecyclerView.LayoutManager layoutManager_displaySearch;
    public static String message;
    public static String userInput;
    public static List<String> name_recentSearchList = new ArrayList<String>();
    public static List<String> number_recentSearchList = new ArrayList<String>();


    private AlertDialog myWaitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(" //////////////////// ----INSIDE ON CREATE DE displaySearchResults.java----  ///////////////////");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_results);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        //get all the extras that had previously been added to the intent with 'putExtra()'
        Bundle mySearchBundle = intent.getExtras();
        if(mySearchBundle != null){
            //get the activity that sent this intent.
            Object origin_of_intent = mySearchBundle.get(getString(R.string.origin_of_search_value));

            // if from OneBookInfoActivity:
            if(origin_of_intent != null && !origin_of_intent.toString().equals(getString(R.string.intent_from_main_activity))){
                Object valuePassed = mySearchBundle.get(getString(R.string.intent_that_started_search));
                if(valuePassed != null){
                    message = valuePassed.toString();
                    userInput = valuePassed.toString();
                }
            // if from MainActivity:
            } else {
                Object valuePassed = mySearchBundle.get(getString(R.string.intent_that_started_search));
                if(valuePassed != null){
                    message = valuePassed.toString();
                    userInput = valuePassed.toString();
                }
            }
        }

        TextView textView = findViewById(R.id.display_userQuery);
        textView.setText(message);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DisplaySearchResults.this, R.style.Theme_AppCompat_Dialog)
                .setView(R.layout.progress_circular)
                .setCancelable(false);
        myWaitingDialog = alertBuilder.create();
        myWaitingDialog.show();

        FloatingActionButton fab = findViewById(R.id.floating_search_MyLib);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(DisplaySearchResults.this, MainActivity.class);
                startActivity(myIntent);
            }

        });

        /*Create handle for the RetrofitInstance interface*/
        SearchOpenLibraryService service = RetrofitSearchClientInstance.getRetrofitInstance().create(SearchOpenLibraryService.class);
        Call<QueryBookFullResponse> call = service.getFullSearch(message);

        // enqueue()- makes an asynchronous request
        call.enqueue(new Callback<QueryBookFullResponse>() {
            @Override
            public void onResponse(Call<QueryBookFullResponse> call, Response<QueryBookFullResponse> response) {

                if(response.code() ==500){
                    Toast.makeText(DisplaySearchResults.this, "An Internal Server Error occurred...Please try again!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DisplaySearchResults.this, MainActivity.class);
                    startActivity(intent);
                } else{
                    System.out.println(response);
                    System.out.println("CODE OF RESPONSE--" + response.code());
                    System.out.println(response.body());
                    myWaitingDialog.dismiss();
                    if (response.body() != null) {
                        convertToBooks(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<QueryBookFullResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(DisplaySearchResults.this, "Something went wrong...Please try again!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DisplaySearchResults.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void convertToBooks(QueryBookFullResponse apiResponse){

        Integer amountOfBksFound = apiResponse.getNum_of_resultsFound();
        name_recentSearchList.add(userInput);
        number_recentSearchList.add(amountOfBksFound.toString());

        if(amountOfBksFound == 0){

            Toast.makeText(DisplaySearchResults.this,
                    "Hmm looks like we don't have any books matching your search.. Let's try again!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(DisplaySearchResults.this, MainActivity.class);
            startActivity(intent);

        }else if(apiResponse.getNum_of_resultsFound() == 1){
            List<RetroBook> docsResults = apiResponse.getDocs();
            for (RetroBook abook: docsResults){
                int pos = MyLibraryActivity.findBookByIdKeyInLibrary(abook.getKey_unique());
                if(pos >= 0){
                    abook.setIsSavedinLibrary(true);
                } else {
                    abook.setIsSavedinLibrary(false);
                }
            }

            mbAdapter = new MyBookAdapter(this, apiResponse, docsResults, amountOfBksFound, false);

            Intent intent = new Intent();

            intent.setClass(DisplaySearchResults.this, OneBookInfoActivity.class);

            intent.putExtra("INTENT THAT STARTED THIS ACTIV", "one_result_From_DisplaySearchResults");
            DisplaySearchResults.this.startActivity(intent);

        }else if(apiResponse.getNum_of_resultsFound() > 1){
            List<RetroBook> docsResults = apiResponse.getDocs();


            for (RetroBook abook: docsResults){
                int pos = MyLibraryActivity.findBookByIdKeyInLibrary(abook.getKey_unique());
                if(pos >= 0){
                    abook.setIsSavedinLibrary(true);
                } else{
                    abook.setIsSavedinLibrary(false);
                }
            }

            mbAdapter = new MyBookAdapter(this, apiResponse, docsResults, amountOfBksFound, false);
            layoutManager_displaySearch = new LinearLayoutManager(DisplaySearchResults.this);
            recyclerView_displaySearch = (RecyclerView) findViewById(R.id.recycler_view_display_search);
            recyclerView_displaySearch.setHasFixedSize(true);
            // 'layoutManager_displaySearch' handles the positioning of the items in the list and the scrolling behavior.
            recyclerView_displaySearch.setLayoutManager(layoutManager_displaySearch);
            recyclerView_displaySearch.setAdapter(mbAdapter);
            TextView num_bks_found_tv = findViewById(R.id.book_results_amount);
            num_bks_found_tv.setText(apiResponse.getNum_of_resultsFound().toString());

        }else {
            Toast.makeText(DisplaySearchResults.this,
                    "VEAMOS!", Toast.LENGTH_LONG).show();
        }
        System.out.println("---------------- INSIDE A SEARCH RESPONSE----------------");
        System.out.println(" THE START NUMBER -- " + apiResponse.getStart());
        System.out.println(" AMOUNT OF RESULTS -- " + apiResponse.getNum_of_resultsFound());
    }

    public String getUserInput(){
        return userInput;
    }


}
