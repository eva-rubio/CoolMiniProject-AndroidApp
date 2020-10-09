package com.example.coolminiproj_2;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QueryBookFullResponse {
    @SerializedName("start")
    private Integer start;

    @SerializedName("num_found")
    private Integer num_of_resultsFound;

    @SerializedName("docs")
    private List<RetroBook> docs = null;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum_of_resultsFound() {
        return num_of_resultsFound;
    }

    public void setNum_of_resultsFound(Integer num_of_resultsFound) {
        this.num_of_resultsFound = num_of_resultsFound;
    }

    public List<RetroBook> getDocs() {
        return docs;
    }


    public void setDocs(List<RetroBook> docs) {
        this.docs = docs;
    }



}
