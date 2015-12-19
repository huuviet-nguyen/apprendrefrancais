package com.example.contact.apprendrefrancais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategorieActivity extends Activity {

    String [] listCategorie  = new String[] {"Story", "Francais" , "Music"};
    ArrayList<String> CategorieList;
    String TAG_CATEGORIE = "categorie";
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
        lv = (ListView) findViewById(R.id.list_categorie);
        CategorieList = new ArrayList<String>();
        for (int i = 0; i < listCategorie.length; ++i) {
            CategorieList.add(listCategorie[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_categorie,R.id.categorie_item ,CategorieList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String categorie = ((TextView) view.findViewById(R.id.categorie_item)).getText()
                        .toString();
                Intent in = new Intent(getApplicationContext(),
                        AllStoriesActivity.class);
                in.putExtra(TAG_CATEGORIE, categorie);
                startActivityForResult(in, 100);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_categorie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
