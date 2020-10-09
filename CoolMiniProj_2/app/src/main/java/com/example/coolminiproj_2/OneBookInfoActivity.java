package com.example.coolminiproj_2;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;

import com.bumptech.glide.Glide;

public class OneBookInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private CollapsingToolbarLayout collapsingToolbar;

    private RetroBook bookToDisplay;
    private int index_position_ofBookToDisplay = 0;
    private String userSearch = DisplaySearchResults.userInput;

    private Context context;
    private TextView title_inside;
    private TextView author_inside;
    private String authorString_name_inside;
    private TextView subtitle_inside;
    private TextView editionCount_inside;
    private TextView first_publish_inside;
    private TextView isbn_inside;
    private TextView name_first_publisher_inside;

    //for the author card:
    private TextView author_name_Incard;
    private ImageView author_pic_Incard;
    private ImageView searchAuthBtn;
    public static final String unknown = "Unknown";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("----------OneBookInfoActivity // onCreate -------------------------------------------------------");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_one_book_info);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Collapsing Toolbar layout to the screen
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_my_toolbar);


        FloatingActionButton fab = findViewById(R.id.floating_search_inside);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(OneBookInfoActivity.this, MainActivity.class);
                startActivity(myIntent);
            }

        });

        Intent intent = getIntent();
        //get all the extras that had previously been added to the intent with 'putExtra()'
        Bundle myBundle = intent.getExtras();
        if (myBundle != null) {
            Object valuePassed = myBundle.get("INTENT THAT STARTED THIS ACTIV");
            System.out.println("-------valuePassed:  " + valuePassed);
            System.out.println("-------valuePassed.toString() :  " + valuePassed);


                if (valuePassed != null && !valuePassed.toString().equals("one_result_From_DisplaySearchResults")) {

                    Object origin = myBundle.get("STRING FROM LIBRARY");
                    if (origin != null && origin.toString().equals("yes, from Library")) {

                        Object vbh_the_key_unique = myBundle.get("FROM-LIB value of vbh.the_key_unique");

                        index_position_ofBookToDisplay = MyLibraryActivity.findBookByIdKeyInLibrary(vbh_the_key_unique.toString());
                        if(index_position_ofBookToDisplay>= 0){
                            bookToDisplay = MyLibraryActivity.savedBooks.get(index_position_ofBookToDisplay);
                        }
                    }else{
                        index_position_ofBookToDisplay = (int) valuePassed;
                        bookToDisplay = MyBookAdapter.getOneBook(index_position_ofBookToDisplay);
                    }
                } else {
                    index_position_ofBookToDisplay = 0;
                    bookToDisplay = MyBookAdapter.getOneBook(index_position_ofBookToDisplay);
                    myAlertDialog();
                }

        }


        System.out.println("---------------- INSIDE ONE BOOK INFO ACTIV---------------");

        collapsingToolbar.setTitle(bookToDisplay.getTitle_book());

        //collapsingToolbar
        ImageView theBkPic = (ImageView) findViewById(R.id.coverImage);
        String urlBest = bookToDisplay.getBest_cover_pic();

        Glide
                .with(this)
                .load(urlBest)
                .error(Glide.with(this).load(R.drawable.pretty_hardcover_book))
                .centerCrop()
                .placeholder(R.drawable.pretty_hardcover_book)
                .into(theBkPic);
/*
* URL url_value = null;
        try {
            url_value = new URL(urlBest);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            Bitmap picBitmap =
                    BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
            getDominantColor(picBitmap);
            System.out.println("--------------------------------------------------------"+getDominantColor(picBitmap));
        } catch (IOException e) {
            e.printStackTrace();
        }
* */
        /*
        * if(theBkPic.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.pretty_hardcover_book).getConstantState())){
            collapsingToolbar.setExpandedTitleColor(Color.BLACK);
        }else{
            collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        }
        System.out.println("---------------------"+theBkPic.getDrawable().getConstantState());
        System.out.println("---------------------"+getResources().getDrawable(R.drawable.pretty_hardcover_book).getConstantState());
        * */

        final Typeface badScriptTypeFc = ResourcesCompat.getFont(getApplicationContext(), R.font.bad_script);

        collapsingToolbar.setCollapsedTitleTypeface(badScriptTypeFc);
        collapsingToolbar.setExpandedTitleTypeface(badScriptTypeFc);

        //Views for the book details:

        title_inside = (TextView) findViewById(R.id.title_bk_inside);
        title_inside.setText(bookToDisplay.getTitle_book());

        author_inside = (TextView) findViewById(R.id.author_bk_inside);
        authorString_name_inside = bookToDisplay.getFirstAuthor();
        author_inside.setText(authorString_name_inside);

        subtitle_inside = (TextView) findViewById(R.id.subtitle_bk_inside);
        TextView subtitle_label = (TextView) findViewById(R.id.subtitle_label_inside);

        if(bookToDisplay.getSubtitle().equalsIgnoreCase(unknown)){
            subtitle_inside.setVisibility(View.GONE);
            subtitle_label.setVisibility(View.GONE);
        }else{
            subtitle_inside.setVisibility(View.VISIBLE);
            subtitle_label.setVisibility(View.VISIBLE);
            subtitle_inside.setText(bookToDisplay.getSubtitle());
        }


        editionCount_inside = (TextView) findViewById(R.id.edition_bk_inside);
        editionCount_inside.setText(bookToDisplay.getEdition_count().toString());

        first_publish_inside = (TextView) findViewById(R.id.year_first_publish_bk_inside);
        first_publish_inside.setText(bookToDisplay.getFirst_publish_year_asString());

        isbn_inside = (TextView) findViewById(R.id.isbn_bk_inside);
        isbn_inside.setText(correctIsbnToDisplay(bookToDisplay));

        name_first_publisher_inside = (TextView) findViewById(R.id.name_main_publisher_bk_inside);
        TextView lable_name_publisher = (TextView) findViewById(R.id.name_main_publisher_label_inside);
        if(bookToDisplay.getFirtsPublisher_fromList().equalsIgnoreCase(unknown)){
            name_first_publisher_inside.setVisibility(View.GONE);
            lable_name_publisher.setVisibility(View.GONE);
        } else {
            name_first_publisher_inside.setVisibility(View.VISIBLE);
            lable_name_publisher.setVisibility(View.VISIBLE);
            name_first_publisher_inside.setText(bookToDisplay.getFirst_publish_year_asString());
        }

        //Views inside Author Card:
        author_name_Incard = (TextView) findViewById(R.id.author_bk_card_inside);
        author_name_Incard.setText(authorString_name_inside);
        searchAuthBtn = (ImageView) findViewById(R.id.search_author_bk_card_inside);


        if(authorString_name_inside.equalsIgnoreCase(getString(R.string.unknown_value) )){
            searchAuthBtn.setImageDrawable(getDrawable(R.drawable.search_author_white_greyedout));
            searchAuthBtn.setEnabled(false);
        }
        else{
            searchAuthBtn.setImageDrawable(getDrawable(R.drawable.search_author_white));
            searchAuthBtn.setOnClickListener(this);
            searchAuthBtn.setEnabled(true);

        }



        author_pic_Incard = (ImageView) findViewById(R.id.author_pic_bk_card_inside);
        String bestAuthUrl = bookToDisplay.getBest_author_pic();
        Glide
                .with(this)
                .load(bestAuthUrl)
                .error(Glide.with(this).load(R.drawable.paper_text_coffee))
                .centerCrop()
                .placeholder(R.drawable.paper_text_coffee)
                .into(author_pic_Incard);





    }


    private String correctIsbnToDisplay(RetroBook bookToDisplayy) {
        if (bookToDisplayy.getMatchingIsbn(userSearch) != null) {
            return bookToDisplayy.getMatchingIsbn(userSearch);
        }
        return bookToDisplayy.getFirstIsbn();
    }

    private void myAlertDialog() {
        String userSearch = DisplaySearchResults.userInput;

        AlertDialog.Builder oneResult_aDialogBuilder = new AlertDialog.Builder(this, R.style.CustomAlert)
                .setMessage("Only one result found matching your search: " + userSearch + "! Continue reading or search again! ")
                .setTitle("Dialog Box")
                .setPositiveButton("New Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(OneBookInfoActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog myAlertDialog = oneResult_aDialogBuilder.create();
        myAlertDialog.show();
    }

    //https://stackoverflow.com/questions/12408431/how-can-i-get-the-average-colour-of-an-image
    public static int getDominantColor(Bitmap bitmap) {
        if (null == bitmap) return Color.TRANSPARENT;

        int redBucket = 0;
        int greenBucket = 0;
        int blueBucket = 0;
        int alphaBucket = 0;

        boolean hasAlpha = bitmap.hasAlpha();
        int pixelCount = bitmap.getWidth() * bitmap.getHeight();
        int[] pixels = new int[pixelCount];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int y = 0, h = bitmap.getHeight(); y < h; y++) {
            for (int x = 0, w = bitmap.getWidth(); x < w; x++) {
                int color = pixels[x + y * w]; // x + y * width
                redBucket += (color >> 16) & 0xFF; // Color.red
                greenBucket += (color >> 8) & 0xFF; // Color.greed
                blueBucket += (color & 0xFF); // Color.blue
                if (hasAlpha) alphaBucket += (color >>> 24); // Color.alpha
            }
        }
        return Color.argb((hasAlpha) ? (alphaBucket / pixelCount) : 255,
                redBucket / pixelCount, greenBucket / pixelCount, blueBucket / pixelCount);
    }

    //https://stackoverflow.com/questions/1855884/determine-font-color-based-on-background-color
    @ColorInt
    public static int getContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;

        int d;
        if (a < 0.5) {
            d = 0; // bright colors - black font
        } else {
            d = 255; // dark colors - white font
        }
        return Color.rgb(d, d, d);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intentAuth_card_inside = new Intent(this, DisplaySearchResults.class);
        //the next activity uses 'EXTRA_AUTH_CARD_NAME' (the key), to retrieve the text value.
        intentAuth_card_inside.putExtra(getString(R.string.intent_that_started_search), authorString_name_inside);
        intentAuth_card_inside.putExtra(getString(R.string.origin_of_search_value), getString(R.string.intent_from_onebookinfo_activity_authorname));

        startActivity(intentAuth_card_inside);

    }
}
