package com.example.contact.apprendrefrancais;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllStoriesActivity extends ListActivity {
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    private static String url_all_products = "http://vietnguyen.esy.es/firstapp/ApprendreFrancais/get_all_products.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_STORIES = "stories";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_RECORD = "record";
    private static final String TAG_CATEGORIE = "categorie";
    private static final String TAG_DESCRIPTION = "description";
    String categorieChoice;
    JSONArray products = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stories);
        Intent i = getIntent();
        categorieChoice = i.getStringExtra(TAG_CATEGORIE);
        Log.d("categorie choice", categorieChoice);
        productsList = new ArrayList<HashMap<String, String>>();
        new LoadAllProducts().execute();
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String record = ((TextView) view.findViewById(R.id.record)).getText().toString();
                String description = ((TextView) view.findViewById(R.id.description)).getText().toString();
                Intent in = new Intent(getApplicationContext(), ReadStoryActivity.class);
                in.putExtra(TAG_RECORD, record);
                in.putExtra(TAG_DESCRIPTION,description);
                startActivityForResult(in, 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllStoriesActivity.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
            Log.d("All Stories: ", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    products = json.getJSONArray(TAG_STORIES);
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
                        if (c.getString(TAG_CATEGORIE).equals(categorieChoice)) {
                            String name = c.getString(TAG_NAME);
                            String nameRecord = c.getString(TAG_RECORD);
                            String description = c.getString(TAG_DESCRIPTION);
                            Log.d("description" , description);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(TAG_RECORD, nameRecord);
                            map.put(TAG_NAME, name);
                            map.put(TAG_DESCRIPTION, description );
                            productsList.add(map);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
                    ListAdapter adapter = new SimpleAdapter(AllStoriesActivity.this, productsList, R.layout.list_item, new String[] { TAG_RECORD, TAG_NAME,TAG_DESCRIPTION}, new int[] { R.id.record, R.id.name,R.id.description });
                    setListAdapter(adapter);
        }
    }
}