package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mynotes.adapters.CategoriesDataAdapters;
import com.example.mynotes.helper.SharedPreferenceHelper;
import com.example.mynotes.helper.VolleySingleton;
import com.example.mynotes.model.Categories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageCategories extends AppCompatActivity {

    static Categories[] categories;
    EditText newCategory;
    Button addCat;
    static Context mContext;
    static RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_categories);
        mContext = ManageCategories.this;
        recyclerView = (RecyclerView) findViewById(R.id.catListView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cats);
        toolbar.setNavigationIcon(R.drawable.back_arrow); // your drawable
        toolbar.setTitle("Manage Categories");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });


        categories = new Categories[0];
        newCategory = findViewById(R.id.newCategory);
        addCat = findViewById(R.id.addCat);
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCategory();
            }
        });
        getCategories();
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addNewCategory() {
        if(newCategory.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Enter Name for new Category",Toast.LENGTH_LONG).show();
            return;
        }
        String url = "http://alllinks.online/andproject/addCategory.php?email="+ SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"username","")+"&name="+ newCategory.getText().toString();
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching your Categories...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pDialog.hide();
                            if(response.getString("status").equals("success"))
                            {

                                //Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_LONG).show();
                                getCategories();
                                newCategory.setText("");
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Some Error occurred",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                // hide the progress dialog

            }
        });

        // Adding request to request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }

    private void getCategories() {
        String url = "http://alllinks.online/andproject/getCategoriesOfUser.php?email="+ SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"username","");
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching your Categories...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pDialog.hide();
                            if(response.getString("status").equals("success"))
                            {

                               // Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_LONG).show();
                                updateCategories(response.getJSONArray("categories"));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Some Error occurred",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Sorry, Some error occurred",Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Sorry, Some error occurred",Toast.LENGTH_LONG).show();
                // hide the progress dialog

            }
        });
        // Adding request to request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }

    private void updateCategories(JSONArray catArr) throws JSONException {
         Categories[] al = new Categories[catArr.length()];
        for (int i = 0; i < catArr.length(); i++) {
            JSONObject row = catArr.getJSONObject(i);
            al[i] = new Categories(row.getString("name"),row.getInt("id"));
        }
        categories = al;
        updateView(categories);
    }

    public static void updateView(Categories[] cats)
    {

        CategoriesDataAdapters adapter = new CategoriesDataAdapters(cats,mContext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }


    public static void updateCategory(final String val, final int id, final int pos)
    {
        String url = "http://alllinks.online/andproject/updateCategory.php?id="+id+"&name="+val;
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Updating Category...");
        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        try {
                            if(response.getString("status").equals("success"))
                            {
                                // Toast.makeText(mContext, response.toString(),Toast.LENGTH_LONG).show();
                                for (int i=0;i<categories.length;i++)
                                {
                                    if(id == categories[i].id)
                                    {
                                        categories[i].name = val;
                                    }
                                }

                            }
                            else
                            {
                                Toast.makeText(mContext,"Some Error occurred", Toast.LENGTH_LONG).show();
                            }

                            updateView(categories);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext,"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        VolleySingleton.getInstance(mContext).addToRequestQueue(jsonObjReq);
    }

    public static void deleteCategory(final int id)
    {
        String url = "http://alllinks.online/andproject/deleteCategory.php?id="+id;
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Deleting Category...");
        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        try {
                            if(response.getString("status").equals("success"))
                            {
                                // Toast.makeText(mContext, response.toString(),Toast.LENGTH_LONG).show();
                                ArrayList<Categories> categoriesArrayList = new ArrayList<Categories>();
                                for (int i=0;i<categories.length;i++)
                                {
                                    if(id == categories[i].id)
                                    {
                                        continue;
                                    }
                                    categoriesArrayList.add(categories[i]);
                                }
                                categories = new Categories[categoriesArrayList.size()];
                                categoriesArrayList.toArray(categories);
                                //listdata = (Categories[]) categoriesArrayList.toArray();

                            }
                            else
                            {
                                Toast.makeText(mContext,"Some Error occurred", Toast.LENGTH_LONG).show();
                            }

                            updateView(categories);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext,"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        VolleySingleton.getInstance(mContext).addToRequestQueue(jsonObjReq);
    }
}
