package com.example.coolminiproj_2;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.annotations.SerializedName;
import java.util.Comparator;
import java.util.List;

// http://openlibrary.org/search.json?q=the+lord+of+the+rings

// http://openlibrary.org/query.json?query={"type": "/type/edition", "authors": "/authors/OL1A", "title": null, "limit": 2}

// http://openlibrary.org/query.json?type=/type/edition&authors=/authors/OL26320A&title=

// http://openlibrary.org/search.json?author=tolkien

/**
 * interface Comparable -
 *      - This interface imposes a total ordering on the objects of each class that implements it.
 *      - This ordering is referred to as the class's natural ordering, and the class's compareTo method is referred to as its natural comparison method.
 *      - Lists (and arrays) of objects that implement this interface can be sorted automatically by Collections#sort(List)
 *
 *      - The Comparable interface only allows to sort ONE property.
 * */

public class RetroBook implements Comparable<RetroBook>{
    public static final String unknown = "Unknown";


    //for SEARCH (1) : title_suggest
    @SerializedName("title_suggest")
    private String title_suggest_book;

    @SerializedName("has_fulltext")
    private Boolean hasFulltext;

    @SerializedName("subtitle")
    private String subtitle;

    //for SEARCH (3): cover_i
    @SerializedName("cover_i")
    private Integer cover_id;

    //for SEARCH: (4) isbn_list  (a list)
    @SerializedName("isbn")
    private List<String> isbn_list = null;

    //for SEARCH (8): author_name  (a list, with 1 entry (index 0))
    @SerializedName("author_name")
    private List<String> author_name = null;

    @SerializedName("subject")
    private List<String> subjects = null;

    @SerializedName("key")
    private String key_unique;

    @SerializedName("publisher")
    private List<String> publisher = null;

    //for SEARCH (15): author_name  (a list, with 1 entry (index 0))
    @SerializedName("author_key")
    private List<String> author_OLkey = null;

    //for SEARCH (17): title
    @SerializedName("title")
    private String title_book;

    @SerializedName("first_publish_year")
    private Integer first_publish_year = null;

    @SerializedName("edition_count")
    private Integer edition_count;


    @SerializedName("cover_edition_key")
    private String cover_edition_key;


    @SerializedName("first_sentence")
    private List<String> first_sentence_list = null;


    private int positionAtInitialSearch;
    private Boolean isSavedInLibrary;


    public RetroBook(String title_suggest_book, Boolean hasFulltext, String subtitle, Integer cover_id,
                     List<String> isbn_list, List<String> author_name, List<String> subjects, String key_unique,
                     List<String> author_OLkey, String title_book, Integer first_publish_year, Integer edition_count, String cover_edition_key,
                     List<String> first_sentence_list, int positionAtInitialSearch, Boolean isSavedInLibrary) {
        this.title_suggest_book = title_suggest_book;
        this.hasFulltext = hasFulltext;
        this.subtitle = subtitle;
        this.cover_id = cover_id;
        this.isbn_list = isbn_list;
        this.author_name = author_name;
        this.subjects = subjects;
        this.key_unique = key_unique;
        this.author_OLkey = author_OLkey;
        this.title_book = title_book;
        this.first_publish_year = first_publish_year;
        this.edition_count = edition_count;
        this.cover_edition_key = cover_edition_key;
        this.first_sentence_list = first_sentence_list;
        this.positionAtInitialSearch = positionAtInitialSearch;
        this.isSavedInLibrary = isSavedInLibrary;

    }

    /**
     * To show in the card when displaying the search: (bellow the id s)
     * -- coverImage
     * -- title_tv
     * -- author_tv
     * -- first_publish_year_tv
     * -- subtitle_tv
     */
//-------------------------------Dealing with the Cover Img:---------------------------------------------------
    public String getBest_cover_pic() {
        String theOL_key_coverIDurl = "http://covers.openlibrary.org/b/olid/";
        String largePic = "-L.jpg?default=false";
        String theISBN_cover_url = "http://covers.openlibrary.org/b/isbn_list/";
        String cute404pic = "drawable://" + R.drawable.cute_404_lupa;

        if (this.getCover_edition_key() != null) {
            return theOL_key_coverIDurl + this.getCover_edition_key() + largePic;
        } else if (this.getFirstIsbn() != null) {
            return theISBN_cover_url + this.getFirstIsbn() + largePic;
        } else {
            return cute404pic;
        }
    }

    public String getCover_edition_key() {
        return cover_edition_key;
    }

    public String getBest_author_pic(){
        String theOL_key_authorIDurl = "http://covers.openlibrary.org/a/olid/";
        String largePic = "-L.jpg?default=false";
        String cute404pic = "drawable://" + R.drawable.cute_404_lupa;
        if (this.getFirstAuthor_OLkey() != null) {
            return theOL_key_authorIDurl + this.getFirstAuthor_OLkey() + largePic;
        }else {
            return cute404pic;
        }
    }
//-------------------------------^^^^Dealing with the Cover Img^^^^---------------------------------------------------


    public List<String> getPublisherList() {
        return publisher;
    }

    /**
     * Gets the first Publisher from the list of Publishers this book has (given by the API).
     * @return the first Publisher of this book's Publisher list.
     * null if list is null of first index is null
     */
    public String getFirtsPublisher_fromList() {
        if (publisher == null || publisher.get(0) == null) {
            return unknown;
        } else {
            return publisher.get(0);
        }
    }

    public Integer getEdition_count() {
        return edition_count;
    }

    public String getTitle_suggest_book() {
        return title_suggest_book;
    }

    public Boolean getHasFulltext() {
        return hasFulltext;
    }

    /**
     * @return the subtitle of this book. 'Unknown' if null o empty string
     * */
    public String getSubtitle() {
        if (subtitle == null || subtitle == "") {
            return unknown;
        }

        return subtitle;
    }

    public List<String> get_listOf_Isbn() {
        return isbn_list;
    }

    /**
     * Gets the first ISBN from the list of ISBNs this list has (given by the API).
     * @return the first isbn of this book's isbn list.
         *  null if list is null of first index is null
     */
    public String getFirstIsbn() {
        if (isbn_list == null || isbn_list.get(0) == null) {
            return null;
        } else {
            return isbn_list.get(0);
        }
    }

    /**
     * Compares the given ISBN to all other isnbs in the list that tis book has.
     *
     * @return null if no match was found.
     */
    public String getMatchingIsbn(String isbnToMatch) {
        if (isbn_list != null) {
            for (String oneIsbn : isbn_list) {
                if (oneIsbn.equalsIgnoreCase(isbnToMatch)) {
                    return oneIsbn;
                }
            }
        }
        return null;
    }

//------------------------------- - AUTHOR - ---------------------------------------------------


    public List<String> getAuthor_list_names() {
        return author_name;
    }

    /**
     * Returns the first author as string from the list provided by the API.
     *
     * @return 'unknown' if list is null of first index is null
     */
    public String getFirstAuthor() {
        if (author_name == null || author_name.get(0) == null) {
            return unknown;
        } else {
            return author_name.get(0);
        }
    }

    public List<String> getAuthor_OLkey() {
        return author_OLkey;
    }

    public String getFirstAuthor_OLkey(){
        if (author_OLkey == null || author_OLkey.get(0) == null) {
            return unknown;
        } else {
            return author_OLkey.get(0);
        }
    }
    public List<String> getSubjects() {
        return subjects;
    }

    public String getKey_unique() {
        return key_unique;
    }
    public String getTitle_book() {
        return title_book;
    }

    public String getFirst_publish_year_asString() {
        if (first_publish_year == null) {
            return unknown;
        } else {
            return first_publish_year.toString();
        }
    }

    public List<String> getFirst_sentence_list() {
        return first_sentence_list;
    }

    public int getPositionAtInitialSearch() {
        return positionAtInitialSearch;
    }

    public Boolean getIsSavedinLibrary() {
        return isSavedInLibrary;
    }


//-----------------------SETTERS------------------------------------------------------------------------------------------------

    public void setPositionAtInitialSearch(int positionAtInitialSearch) {
        this.positionAtInitialSearch = positionAtInitialSearch;
    }


    public void setIsSavedinLibrary(Boolean savedinLibrary) {
        isSavedInLibrary = savedinLibrary;
    }

    public void setPublisher(List<String> publisher) {
        this.publisher = publisher;
    }

    public void setEdition_count(Integer edition_count) {
        this.edition_count = edition_count;
    }

    public void setTitle_suggest_book(String title_suggest_book) {
        this.title_suggest_book = title_suggest_book;
    }

    public void setHasFulltext(Boolean hasFulltext) {
        this.hasFulltext = hasFulltext;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setCover_id(Integer cover_id) {
        this.cover_id = cover_id;
    }

    public void setIsbn_list(List<String> isbn_list) {
        this.isbn_list = isbn_list;
    }

    public void setAuthor_name(List<String> author_name) {
        this.author_name = author_name;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setKey_unique(String key_unique) {
        this.key_unique = key_unique;
    }

    public void setAuthor_OLkey(List<String> author_OLkey) {
        this.author_OLkey = author_OLkey;
    }

    public void setTitle_book(String title_book) {
        this.title_book = title_book;
    }

    public void setCover_edition_key(String cover_edition_key) {
        this.cover_edition_key = cover_edition_key;
    }

    public void setFirst_publish_year(Integer first_publish_year) {
        this.first_publish_year = first_publish_year;
    }

    public void setFirst_sentence_list(List<String> first_sentence_list) {
        this.first_sentence_list = first_sentence_list;
    }



    /**
     * Compares its 'edition_count' property for the RetroBook List to be sort in descending order.
     * The 'compareTo()' method is referred to as its natural comparison method.
     * */
    // https://www.mkyong.com/java/java-object-sorting-example-comparable-and-comparator/
    @Override
    public int compareTo(RetroBook bkToCompare) {
        int edition_amount_toCompare = ((RetroBook)bkToCompare).getEdition_count();
        // in descending order.
        return edition_amount_toCompare - this.getEdition_count();
    }

    public static Comparator<RetroBook> titleAlphabeticalComparator = new Comparator<RetroBook>() {
        @Override
        public int compare(RetroBook b1, RetroBook b2) {
            String titleBook1 = b1.getTitle_book();
            String titleBook2 = b2.getTitle_book();

            //In ascending /alphabetical order

            return titleBook1.compareToIgnoreCase(titleBook2);
        }
    };
}
