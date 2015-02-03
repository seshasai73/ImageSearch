package com.tp.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by seshasa on 2/1/15.
 */
public class ImageResults implements Serializable{
    //public static final long serialVersionUID =
    public String fullUrl;
    public String thumbUrl;
    public String title;
    public String height;
    public String width;

    public ImageResults(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.height = json.getString("height");
            this.width = json.getString("width");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<ImageResults> fromJSONArray(JSONArray array) {
        ArrayList<ImageResults> results = new ArrayList<ImageResults>();
        try {
            for (int i = 0; i < array.length(); i++) {

                results.add(new ImageResults(array.getJSONObject(i)));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }
}
