package com.example.android.jobsfair;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends Activity {

    private TextView txtTitle;
    private Spinner spnCity, spnCategory, spnSubCategory;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // https://www.mkyong.com/android/android-spinner-drop-down-list-example/
        //addItemsOnSubCategory();
        addListenerOnButton();
        //addListenerOnSpinnerItemSelection();
        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        Resources res = getResources();
        String [] strCategories = res.getStringArray(R.array.category_array);
        String [] strSubcategories = res.getStringArray(R.array.subcategory_array);

        // Using hint on spinner in android.
        //https://android--code.blogspot.ie/2015/08/android-spinner-hint.html

        final ArrayAdapter<String> adapterCat = new ArrayAdapter<String>(this,R.layout.spinner_item,strCategories){
            @Override
            public boolean isEnabled(int position)
            {
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                    ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapterCat);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }@Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnSubCategory = (Spinner) findViewById(R.id.spnSubCategory);
        //ArrayAdapter<CharSequence> adapterSubCat = ArrayAdapter.createFromResource(this, R.array.subcategory_array, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> adapterSubCat = new ArrayAdapter<CharSequence>(this,R.layout.spinner_item,strSubcategories){
            @Override
            public boolean isEnabled(int position)
            {
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapterSubCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSubCategory.setAdapter(adapterSubCat);
        spnSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }@Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onSearchButtonClicked(View view){

    }

    //get the selected dropdown list value
    public void addListenerOnButton() {
        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        spnSubCategory = (Spinner) findViewById(R.id.spnSubCategory);
        btnSubmit = (Button) findViewById(R.id.btnSearch);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(dashboard.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : " + String.valueOf(spnCategory.getSelectedItem()) +
                                "\nSpinner 2 : " + String.valueOf(spnSubCategory.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });

    }
}
