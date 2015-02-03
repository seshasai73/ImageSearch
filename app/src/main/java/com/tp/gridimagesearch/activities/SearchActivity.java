package com.tp.gridimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tp.gridimagesearch.EndlessScrollListener;
import com.tp.gridimagesearch.R;
import com.tp.gridimagesearch.SearchSettings;
import com.tp.gridimagesearch.adapters.ImageResultsAdapter;
import com.tp.gridimagesearch.models.ImageResults;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private EditText et_Query;
    private GridView gv_Results;
    private ArrayList<ImageResults> imageResults;
    private ImageResultsAdapter aImageResults;
    private SearchSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        settings = new SearchSettings();
        et_Query = (EditText)findViewById(R.id.et_Query);
        gv_Results = (GridView)findViewById(R.id.gv_Results);
        gv_Results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this,ImageDisplayActivity.class);
                ImageResults result = imageResults.get(position);
                i.putExtra("ImageResults",result);
                startActivity(i);
            }
        });
        gv_Results.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchMoreResults();
                //Toast.makeText(SearchActivity.this,"LOAD MORE",Toast.LENGTH_SHORT).show();
            }
        });
        imageResults = new ArrayList<ImageResults>();
        aImageResults = new ImageResultsAdapter(this,imageResults);
        gv_Results.setAdapter(aImageResults);
    }
    public void onImageSearch(View v)
    {
        if(!isNetworkAvailable())
        {
            Toast.makeText(this, "Check Network Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        settings.query=et_Query.getText().toString();
        settings.page=-1;
        imageResults.clear();
        fetchMoreResults();

        //String query = et_Query.getText().toString();
            //Toast.makeText(this,"Search " + query,Toast.LENGTH_SHORT).show();
        /*String url="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=" + settings.query;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG",response.toString());
                JSONArray imageResultsJSON = null;
                try {
                    imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    //the below
                    //imageResults.addAll(ImageResults.fromJSONArray(imageResultsJSON));
                    //aImageResults.notifyDataSetChanged();
                    // or
                    aImageResults.addAll(ImageResults.fromJSONArray(imageResultsJSON));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
*/
    }
    public void fetchMoreResults()
    {

        String url="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + settings.query;
        url += "&rsz=" + String.valueOf(settings.pageSize);
        settings.page++;
        url += "&start=" + String.valueOf(settings.page*settings.pageSize);
        if(settings.color.length()>0)
            url += "&imgcolor=" + settings.color;
        if(settings.type.length()>0)
            url += "&imgtype=" + settings.type;
        if(settings.size.length()>0)
            url += "&imgsz=" + settings.size;
        if(settings.site.length()>0)
            url += "&as_sitesearch=" + settings.site;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJSON = null;
                try {
                    imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResults.clear();
                    //the below
                    //imageResults.addAll(ImageResults.fromJSONArray(imageResultsJSON));
                    //aImageResults.notifyDataSetChanged();
                    // or
                    aImageResults.addAll(ImageResults.fromJSONArray(imageResultsJSON));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
            Intent i = new Intent(this,SettingsActivity.class);
            i.putExtra("Settings",settings);
            startActivityForResult(i, 101);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 101) {
            if(data != null) {
                settings = (SearchSettings) data.getSerializableExtra("Settings");
                //Toast.makeText(this, settings.site + settings.size + settings.type + settings.color, Toast.LENGTH_SHORT).show();
                onImageSearch(null);
            }
        }
    }
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
