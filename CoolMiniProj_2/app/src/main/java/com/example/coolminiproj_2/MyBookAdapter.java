
package com.example.coolminiproj_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Search examples:
 * - A Tale of Two Cities
 * - The Lord of the Rings
 * - Harry Potter
 * - The Hobbit
 * - The Da Vinci Code
 * - Dan Brown
 * - Pinocchio
 * - The Alchemist
 * <p>
 * rafa:
 * - 1984 Orwell
 * - Animal Farm G Orwell
 */

/**
 * The adapter is the link between the 'RecyclerView' and the data we want to display(to list).
 *      - It creates las views que son required ('bkTitle', 'bkAuthor' ...).
 *      - También crea los 'links' entre dichos 'views' y el data del libro en si.
 *          (ex: ata el título del libro con la view llamada 'bkTitle').
 * An Adapter class is what adds the content/info from a data source to the RecyclerView or ListView.
 * This class customizes our RecyclerView.
 *      -  for DisplaySearchResults:
 *              - The data source: 'list_of_books'.
 *              - The RecyclerView: 'recyclerView_displaySearch'.
 *      -  for MyLibrary:
 *                 - The data source: 'MyLibrary.savedBooks'.
 *  *              - The RecyclerView: 'recyclerView_myLibrary'.
 *
 * Tiene tres métodos:
 *      1. onCreateViewHolder - Inflates the layout and creates a new view Holder.
 *      2. onBindViewHolder - Binds the data to the view holder (^ just created).
 *      3. getItemCount - the size of the data to be displayed.
 * */
public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.MyViewBookHolder> {

    public static final String EXTRA_BK_ADAPTER_MANY = "MANY_results_From_MyBookAdapter";
    private static final String TAG = "MyActivity";

    private static QueryBookFullResponse apiResponse; // full body
    private static Integer amountOfResults;
    private List<RetroBook> list_of_books;

    private Context context;
    private Boolean fromLibrary;

    private int positionOf_book_modified;
    private boolean isItLiked;

    /**
     * Static access to the full list of books given by the search query
     */
    public static List<RetroBook> get_allBooksInSearch(QueryBookFullResponse tooMuchInfoHere) {
        return tooMuchInfoHere.getDocs();
    }

    /**
     * Provides static access to the data model.
     */
    public static RetroBook getOneBook(int position) {
        Log.i(TAG, "----------------MyBookAdapter.getOneBook() — get the position " + position);
        RetroBook bkToProduce = apiResponse.getDocs().get(position);
        if (bkToProduce.getIsSavedinLibrary() == null) {
            bkToProduce.setIsSavedinLibrary(false);
        }
        return bkToProduce;
    }

    /**
     * Constructor for 'DisplaySearchResults' data.
     */
    public MyBookAdapter(Context context, QueryBookFullResponse apiResponse, List<RetroBook> list_of_books, Integer amountOfResults, Boolean fromLibrary) {
        this.context = context;
        this.apiResponse = apiResponse;
        this.list_of_books = list_of_books;
        this.amountOfResults = amountOfResults;
        this.fromLibrary = fromLibrary;
    }

    /**
     * Constructor for 'My Library' data.
     */
    public MyBookAdapter(Context context, Boolean fromLibrary) {
        this.context = context;
        this.fromLibrary = fromLibrary;
    }

    /**
     * Inflates the layout and creates a new view Holder.
     * (1st) Method required by 'MyBookAdapter' class.
     * Create new views (invoked by the layout manager).
     *
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     */
    @Override
    public MyBookAdapter.MyViewBookHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.one_card_book, parent, false);

        final MyViewBookHolder vbh = new MyViewBookHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            // the compiler essentially auto-generates this:
            // private View superSecretCopyOfParent;
            //public View.OnClickListener(View parent){
            //    this.superSecretCopyOfParent = parent;
            //}
            @Override
            public void onClick(View v) {
                if (fromLibrary) {
                    Intent intentFromLibrary = new Intent(parent.getContext(), OneBookInfoActivity.class);
                    intentFromLibrary.putExtra("INTENT THAT STARTED THIS ACTIV", vbh.bkLibraryPosition);
                    intentFromLibrary.putExtra("FROM-LIB value of vbh.bkPosition", vbh.bkPosition);
                    intentFromLibrary.putExtra("FROM-LIB value of vbh.bkLibraryPosition", vbh.bkLibraryPosition);
                    intentFromLibrary.putExtra("FROM-LIB value of vbh.the_key_unique", vbh.the_key_unique);
                    System.out.println("******************** TO GO FROM LIB TO INSIDE A BOOK, vbh.the_key_unique : " + vbh.the_key_unique);

                    intentFromLibrary.putExtra("BOOLEAN FROM LIBRARY", fromLibrary);

                    intentFromLibrary.putExtra("STRING FROM LIBRARY", "yes, from Library");

                    Log.i(TAG, "----------------MyBookAdapter.MyViewBookHolder onCreateViewHolder() — get the vbh.bkPosition " + vbh.bkLibraryPosition);

                    parent.getContext().startActivity(intentFromLibrary);

                } else {
                    Intent intentFromDisplaySearch = new Intent(parent.getContext(), OneBookInfoActivity.class);

                    intentFromDisplaySearch.putExtra("INTENT THAT STARTED THIS ACTIV", vbh.bkPosition);
                    intentFromDisplaySearch.putExtra("FROM-DISPLAY value of vbh.bkPosition", vbh.bkPosition);
                    intentFromDisplaySearch.putExtra("FROM-DISPLAY value of vbh.bkLibraryPosition", vbh.bkLibraryPosition);

                    System.out.println("******************** TO GO FROM DISPLAYSEARCH TO INSIDE A BOOK, vbh.the_key_unique : " + vbh.the_key_unique);

                    intentFromDisplaySearch.putExtra("BOOLEAN FROM LIBRARY", fromLibrary);

                    intentFromDisplaySearch.putExtra("STRING FROM LIBRARY", "NO, from DisplaySearchResults.java");

                    Log.i(TAG, "----------------MyBookAdapter.MyViewBookHolder onCreateViewHolder() — get the vbh.bkPosition " + vbh.bkPosition);

                    parent.getContext().startActivity(intentFromDisplaySearch);
                }
            }
        });
        return vbh;
    }

    /**
     * Binds the data to the view holder that was just created by 'onCreateViewHolder'.
     * (2nd) Method required by 'MyBookAdapter' class.
     *
     * Replace the contents of a view (invoked by the layout manager)
     * - get element from your dataset at this position
     * - replace the contents of the view with that element
     *
     * Called by RecyclerView to display the data at the specified position.
     * Update the RecyclerView.ViewHolder contents with the item at the given position
     *  and also sets up some private fields to be used by RecyclerView.
     */
    @Override
    public void onBindViewHolder(MyViewBookHolder holder, int position) {

        //holder.setDataInTheirView(list_of_books.get(position), holder.getAdapterPosition());

        if (fromLibrary) {
            holder.setDataInTheirView(MyLibraryActivity.savedBooks.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.bkLibraryPosition = holder.getAdapterPosition();
            MyLibraryActivity.savedBooks.get(holder.getAdapterPosition()).setPositionAtInitialSearch(holder.getAdapterPosition());
            Log.i(TAG, "----------FROM LIBRARY------MyBookAdapteronBindViewHolder() — get the holder.bkLibraryPosition " + holder.bkLibraryPosition);

        } else {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            holder.setDataInTheirView(list_of_books.get(holder.getAdapterPosition()), holder.getAdapterPosition());

            holder.bkPosition = holder.getAdapterPosition();
            list_of_books.get(holder.getAdapterPosition()).setPositionAtInitialSearch(holder.getAdapterPosition());
            Log.i(TAG, "----------------MyBookAdapteronBindViewHolder() — get the holder.bkPosition " + holder.bkPosition);

        }
    }

    /**
     * Return the size of my dataset (invoked by the layout manager)
     * (3rd) Method required by 'MyBookAdapter' class.
     */
    @Override
    public int getItemCount() {
        if (fromLibrary) {
            return MyLibraryActivity.savedBooks.size();
        }
        return list_of_books.size();
    }

//************************************************************************************************************************************************************************
    /**
     * 'Holder' el contenedor.
     *      - El cual 'contiente' one_card_book, donde dentro está la info visual de un único libro.
     *      - El 'holder' es 'one_card_book' que tiene dentro las views ('bkTitle', 'bkAuthor'....)
     *
     * Provides a reference to the views for each data item(for each book).
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder.
     *
     *  A 'ViewHolder' is required to hold on to the views.
     *  It references todas las views dentro de 'one_card_book' para cada uno de los libros que va a display luego
     */
    public class MyViewBookHolder extends RecyclerView.ViewHolder {
        private int bkPosition;
        private int bkLibraryPosition;
        private String the_key_unique;
        private final View bkView;
        private TextView bkTitle;
        private TextView bkAuthor;
        private TextView bkFirstPublYear;
        private ImageView bkCoverImg;
        private CheckBox loveBtn;
        private ImageButton shareBtn;
        private RatingBar starBar;

        public MyViewBookHolder(View oneBookView) {
            super(oneBookView);
            bkView = oneBookView;

            bkTitle = bkView.findViewById(R.id.title_tv);
            bkAuthor = bkView.findViewById(R.id.author_tv);
            bkFirstPublYear = bkView.findViewById(R.id.first_publish_year_tv);
            //bkSubtitle = bkView.findViewById(R.id.subtitle_tv);
            loveBtn = bkView.findViewById(R.id.action_save_toMyLibrary_CardBtn);
            shareBtn = bkView.findViewById(R.id.share_card_btn);
            bkCoverImg = bkView.findViewById(R.id.coverImage);

            starBar = bkView.findViewById(R.id.ratingbar_forcard);
        }

        private void setDataInTheirView(final RetroBook oneBook, final int adapterPos) {
            bkTitle.setText(oneBook.getTitle_book());
            bkAuthor.setText(oneBook.getFirstAuthor());
            bkFirstPublYear.setText(oneBook.getFirst_publish_year_asString());
            the_key_unique = oneBook.getKey_unique();

            Glide
                    .with(context)
                    .load(oneBook.getBest_cover_pic())
                    .error(Glide.with(context).load(R.drawable.cute_404_lupa))
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(bkCoverImg);

            int editionNum = oneBook.getEdition_count();
            if (editionNum <= 10) {
                starBar.setRating(1);
            } else if (editionNum <= 20) {
                starBar.setRating(2);
            } else if (editionNum <= 30) {
                starBar.setRating(3);
            } else if (editionNum <= 40) {
                starBar.setRating(4);
            } else if (editionNum >= 50) {
                starBar.setRating(5);
            } else {
                starBar.setRating(0);
            }

            if (oneBook.getIsSavedinLibrary()) {
                loveBtn.setChecked(true);
                loveBtn.setBackgroundResource(R.drawable.full_heart_same_color);
            } else {
                loveBtn.setChecked(false);
                loveBtn.setBackgroundResource(R.drawable.empty_heart);
            }
            loveBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                /**
                 * Called when the checked state of a compound button has changed.
                 *
                 * @param buttonView The compound button view whose state has changed.
                 * @param isChecked  The new checked state of buttonView.
                 */
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    positionOf_book_modified = adapterPos;

                    if (buttonView.isPressed()) {
                        //we check to see if it was pressed to save or unsave the book:
                        if (isChecked) {
                            isItLiked = true;
                            loveBtn.setBackgroundResource(R.drawable.full_heart_same_color);

                            syncBookListsAndViews();
                            Toast.makeText(context, oneBook.getTitle_book() + " added to My Library", Toast.LENGTH_SHORT).show();

                            MyLibraryActivity.savedBooks.add(oneBook);

                        } else {
                            isItLiked = false;
                            loveBtn.setBackgroundResource(R.drawable.empty_heart);
                            syncBookListsAndViews();
                            Toast.makeText(context, oneBook.getTitle_book() + " removed from My Library.", Toast.LENGTH_SHORT).show();
                            if (MyLibraryActivity.savedBooks != null && MyLibraryActivity.savedBooks.contains(oneBook)) {
                                MyLibraryActivity.savedBooks.remove(oneBook);
                                /** void notifyItemRemoved (int position):
                                        - Notify any registered observers that the item previously located at 'position' has been removed from the data set.
                                        - The items previously located at and after 'position' may now be found at: 'oldPosition - 1'.
                                    This is a structural change event. Representations of other existing items in the data set are still considered up to date and will not be rebound, though their positions may be altered.*/
                                notifyItemRemoved(adapterPos);
                            }
                        }
                    }
                }
            });
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareBkIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareBkIntent.setType("text/plain");
                    String stringToShare = "You much check out: " + bkTitle.getText() + ", by " + bkAuthor.getText();
                    shareBkIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
                    shareBkIntent.putExtra(android.content.Intent.EXTRA_TEXT, stringToShare);
                    context.startActivity(Intent.createChooser(shareBkIntent, "Choose how to share this awesome book!"));
                }
            });
        }
    }
//************************************************************************************************************************************************************************

    private void syncBookListsAndViews() {
        //if its liked, but it has NOT been saved

        if (fromLibrary) {
            // if it has been liked (red heart) AND the book in 'savedBooks' list has the field set as FALSE:
            // - we set the field to TRUE and then we notifyItemChanged
            if (isItLiked && !MyLibraryActivity.savedBooks.get(positionOf_book_modified).getIsSavedinLibrary()) {
                MyLibraryActivity.savedBooks.get(positionOf_book_modified).setIsSavedinLibrary(true);
                //Notify any registered observers that the item at position has changed.
                notifyItemChanged(positionOf_book_modified);

            } else if (!isItLiked && MyLibraryActivity.savedBooks.get(positionOf_book_modified).getIsSavedinLibrary()) {
                MyLibraryActivity.savedBooks.get(positionOf_book_modified).setIsSavedinLibrary(false);
                //notifyItemChanged(positionOf_book_modified);
                /**'notifyItemRangeChanged(int positionStart, int itemCount)'.
                 *      Notify any registered observers that the itemCount items starting at position positionStart have changed.
                 * */
                notifyItemRangeChanged(positionOf_book_modified, MyLibraryActivity.savedBooks.size());
            }
        } else {

            //notifyItemChanged(positionOf_book_modified);
            if (isItLiked && !list_of_books.get(positionOf_book_modified).getIsSavedinLibrary())
                list_of_books.get(positionOf_book_modified).setIsSavedinLibrary(true);
            else if (!isItLiked && list_of_books.get(positionOf_book_modified).getIsSavedinLibrary()) {
                list_of_books.get(positionOf_book_modified).setIsSavedinLibrary(false);
                // notifyItemChanged(positionOf_book_modified);
            }
        }
    }

    public void onStarSort(View view) {
        Collections.sort(list_of_books);
        notifyDataSetChanged();
    }
}
