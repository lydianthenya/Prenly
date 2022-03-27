package com.lydia.prenly;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.lydia.prenly.adapter.HeadlineAdapter;
import com.lydia.prenly.models.Headline;
import com.lydia.prenly.utils.Constants;
import com.lydia.prenly.utils.MyPreferences;
import com.lydia.prenly.utils.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HeadlineAdapter adapter;
    private List<Headline> newsList;
    String TAG = MainActivity.class.getSimpleName();
    ImageView im_drawer;
    DrawerLayout drawer;
    MyPreferences pref;
    TextView txtTopic;
    String url = "";
    SwipeRefreshLayout mySwipeRefreshLayout;
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = new MyPreferences(MainActivity.this);

        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        newsList.clear();
                        getNews();
                    }
                }
        );


        txtTopic = findViewById(R.id.txt_topic);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        newsList = new ArrayList<>();
        adapter = new HeadlineAdapter(this, newsList);

        txtTopic.setText(pref.getSelectedTopic());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        im_drawer = (ImageView) findViewById(R.id.im_drawer);

        im_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_client_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);


        getNews();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Headline selected_url = newsList.get(position);

                pref.setSelectedUrl(selected_url.getUrl());

                Intent i = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(i);


            }

            @Override
            public void onLongClick(View view, int position) {

                Headline selected_url = newsList.get(position);

                pref.setSelectedUrl(selected_url.getUrl());

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_visit);
                dialog.setCancelable(false);
                Button okbtn = (Button) dialog.findViewById(R.id.dialogButtonOK);
                Button cancelBtn = (Button) dialog.findViewById(R.id.dialog_cancel);
                TextView remarks = (TextView) dialog.findViewById(R.id.tv_response_id);
                String author = selected_url.getAuthor();
                if (author.equals(null)||author.equals("")||author.equals("null")){
                 remarks.setText(   Html.fromHtml("Author : "+getString(R.string.unknown)+"<font color=\"#65BCBF\">" +"\n"+"\n"+" Source : "+selected_url.getName()));

                }else {
                    remarks.setText(Html.fromHtml(getString(R.string.author_name)+": "+selected_url.getAuthor()+"<font color=\"#65BCBF\">" +"\n"+"\n"+" Source : "+selected_url.getName()));
                }



                // if button is clicked, close the custom dialog
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String page_url = selected_url.getUrl();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(page_url));

                        startActivity(browserIntent);
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();



                    }
                });


                dialog.show();

            }
        }));


    }

    public void sports(View view) {
        finish();
        startActivity(getIntent());

        pref.setSelectedTopic(getString(R.string.sports));

        txtTopic.setText(pref.getSelectedTopic());
    }

    public void home(View view) {
        finish();
        startActivity(getIntent());

        pref.setSelectedTopic(getString(R.string.default_topic));

        txtTopic.setText(pref.getSelectedTopic());
    }


    public void business(View view) {
        finish();
        startActivity(getIntent());
        pref.setSelectedTopic(getString(R.string.business));
        txtTopic.setText(pref.getSelectedTopic());
    }

    public void politics(View view) {
        finish();
        startActivity(getIntent());

        pref.setSelectedTopic(getString(R.string.politics));

        txtTopic.setText(pref.getSelectedTopic());
    }

    public void world(View view) {
        finish();
        startActivity(getIntent());


        pref.setSelectedTopic(getString(R.string.world));
        txtTopic.setText(pref.getSelectedTopic());
    }

    public void fashion(View view) {
        finish();
        startActivity(getIntent());


        pref.setSelectedTopic(getString(R.string.fashion));

        txtTopic.setText(pref.getSelectedTopic());
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public void getNews() {

        mySwipeRefreshLayout.setRefreshing(true);
        if (pref.getSelectedTopic().equals(getString(R.string.default_topic))) {

            url = Constants.TOP_HEADLINES;
        } else {
            url = Constants.BASE_URL + "q=" + pref.getSelectedTopic() + "&" + "apiKey=" + Constants.API_KEY;

        }
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                JSONArray array = null;

                try {
                    if (response.getString("status").equals("ok")) {

                        mySwipeRefreshLayout.setRefreshing(false);
                        array = new JSONArray(response.getString("articles"));

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            JSONObject source = new JSONObject(object.getString("source"));


                            Headline a = new Headline(source.getString("name"), object.getString("publishedAt"), object.getString("title"), object.getString("urlToImage"), object.getString("url"), object.getString("description"),object.getString("author"));
                            newsList.add(a);

                            adapter.notifyDataSetChanged();
                        }

                    } else if (response.getString("status").equals("error")) {

                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_alert);
                        dialog.setCancelable(false);
                        Button okbtn = (Button) dialog.findViewById(R.id.dialogButtonOK);
                        TextView remarks = (TextView) dialog.findViewById(R.id.tv_response_id);
                        remarks.setText(response.getString("message"));


                        // if button is clicked, close the custom dialog
                        okbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                            }
                        });

                        dialog.show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mySwipeRefreshLayout.setRefreshing(false);
                VolleyLog.d("Error", "Error: " + error.getMessage());

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_alert);
                dialog.setCancelable(false);
                Button okbtn = (Button) dialog.findViewById(R.id.dialogButtonOK);
                TextView remarks = (TextView) dialog.findViewById(R.id.tv_response_id);
                remarks.setText(getString(R.string.error_occured));


                // if button is clicked, close the custom dialog
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCancelable(false);
        Button okbtn = (Button) dialog.findViewById(R.id.dialogBtnOK);
        Button cancelBtn = (Button) dialog.findViewById(R.id.dialogBtnCancel);
        TextView txtBody = dialog.findViewById(R.id.tv_response_id);
        txtBody.setText("Are you sure you want to exit the application?");
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finishAffinity();

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();


    }

    public void instagram(View view) {

        String YourPageURL =getString(R.string.instagram);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

        startActivity(browserIntent);
    }
    public void linkedIn(View view) {

        String YourPageURL =getString(R.string.linkedin);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

        startActivity(browserIntent);
    }

    public void youtube(View view) {

        String YourPageURL =getString(R.string.youtube);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

        startActivity(browserIntent);
    }

    public void facebook(View view) {

        String YourPageURL =getString(R.string.facebook);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

        startActivity(browserIntent);
    }

    public void twitter(View view) {

        String YourPageURL =getString(R.string.twitter);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

        startActivity(browserIntent);
    }

}
