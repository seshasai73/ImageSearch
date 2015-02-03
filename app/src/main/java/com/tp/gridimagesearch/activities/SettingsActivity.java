package com.tp.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.tp.gridimagesearch.R;
import com.tp.gridimagesearch.SearchSettings;

public class SettingsActivity extends ActionBarActivity {

    private Spinner sp_ImageSize;
    private Spinner sp_ColorFilter;
    private Spinner sp_ImageType;
    private EditText et_Site;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sp_ImageSize = (Spinner)findViewById(R.id.sp_ImageSize);
        sp_ColorFilter = (Spinner)findViewById(R.id.sp_ColorFilter);
        sp_ImageType = (Spinner)findViewById(R.id.sp_ImageType);
        et_Site    = (EditText)findViewById(R.id.et_Site);
        SearchSettings settings = (SearchSettings)getIntent().getSerializableExtra("Settings");
        if(settings.size.length() == 0)
            sp_ImageSize.setSelection(0);
        else
            setSpinnerValue(sp_ImageSize,settings.size);

        if(settings.color.length() == 0)
            sp_ColorFilter.setSelection(0);
        else
            setSpinnerValue(sp_ColorFilter,settings.color);

        if(settings.type.length() == 0)
            sp_ImageType.setSelection(0);
        else
            setSpinnerValue(sp_ImageType,settings.type);

        et_Site.setText(settings.site);
    }

    public void setSpinnerValue(Spinner spinner,String value)
    {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }
    public void onSaveClicked(View view)
    {
        SearchSettings settings =new SearchSettings();
        String color = sp_ColorFilter.getSelectedItem().toString();
        String image_type = sp_ImageType.getSelectedItem().toString();
        String image_size = sp_ImageSize.getSelectedItem().toString();
        String site = et_Site.getText().toString();
        if ("ANY".compareTo(color) == 0) settings.color = "";
        else settings.color = color;
        if ("ANY".compareTo(image_type) == 0) settings.type = "";
        else settings.type = image_type;
        if ("ANY".compareTo(image_size) == 0) settings.size = "";
        else settings.size = image_size;
        settings.site = site;
        Intent i = new Intent();
        i.putExtra("Settings",settings);
        setResult(RESULT_OK,i);
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
