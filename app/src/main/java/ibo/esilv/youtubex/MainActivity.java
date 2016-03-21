package ibo.esilv.youtubex;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

import ibo.esilv.youtubex.dataClass.YoutubeResult;
import ibo.esilv.youtubex.dataClass.items;

public class MainActivity extends AppCompatActivity {

    private YoutubeResult myResult;
    private RecyclerView mRecyclerView;
    private ResultListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> suggestionList;
    private int resultCount;
    private String request;

    private SimpleCursorAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.myLayout);

        String[] from = new String[] { "KEY" };
        int[] to = new int[] { android.R.id.text1 };
        sAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, from, to, 1);

        suggestionList = readPref();
        resultCount = 10;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String last = prefs.getString(Config.KEY_LASTQUERY,"trend");
        doRequest(last);
        Toast.makeText(MainActivity.this, "Use the search button in action bar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);
        searchView.setSuggestionsAdapter(sAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                String suggestion = getSuggestion(position);
                searchView.setQuery(suggestion, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                // Your code here
                return true;
            }

            private String getSuggestion(int position) {
                Cursor cursor = (Cursor) sAdapter.getItem(position);
                String suggest1 = cursor.getString(cursor.getColumnIndex("KEY"));
                return suggest1;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!suggestionList.contains(query))
                {
                    writePref(query);
                }
                query = query.replace(" ","%20");
                request=query;
                doRequest(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s);
                return false;
            }
        });

        return true;
    }


    private void doRequest(String request)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+request+"&key=AIzaSyBNjXhKeFCO1DoNri3KxiYTYKXWB6fftOQ&type=video&maxResults="+resultCount;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        toList(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(stringRequest);
    }

    private void toList(String json)
    {
        Gson gson = new Gson();
        myResult = gson.fromJson(json, YoutubeResult.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.myList);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        // specify an adapter (see also next example)
        mAdapter = new ResultListAdapter((ArrayList<items>)myResult.getItems(),mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new ResultListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(resultCount<40)
                {
                    resultCount += 10;
                    doRequest(request);
                }

            }
        });

    }


    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "KEY" });
        for (int i=0; i<suggestionList.size(); i++) {
            if (suggestionList.get(i).toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, suggestionList.get(i)});
        }
        sAdapter.changeCursor(c);
    }

    public ArrayList<String> readPref()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String json = prefs.getString(Config.KEY_LIST,null);
        Gson gson =new Gson();
        if(json != null)
        {
            return gson.fromJson(json, stringList.class);
        }
        else
        {
            return new ArrayList<>();
        }
    }

    public void writePref(String newItem)
    {
        suggestionList.add(newItem);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Config.KEY_LIST);

        Gson gson = new Gson();
        String json = gson.toJson(suggestionList);
        editor.putString(Config.KEY_LIST, json);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(request != null)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(Config.KEY_LASTQUERY);
            editor.putString(Config.KEY_LASTQUERY, request);
            editor.commit();
        }

    }
}
