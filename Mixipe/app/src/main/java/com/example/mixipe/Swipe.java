package com.example.mixipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Swipe extends AppCompatActivity implements CardStackListener {

    BottomNavigationView bottomNavigationView;
    List<Model> list = new ArrayList<>();
    CardStackLayoutManager manager;
    ProgressDialog pd;
    String TAG = "Swipe";
    RecyclerViewAdapter adapter;
    CardStackView stackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        stackView = findViewById(R.id.stack_view);
        getRecipe();


        //Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.swipe);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.swipe:
                        return true;

                    case R.id.liked:
                        startActivity(new Intent(getApplicationContext(), Liked.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }

        });
    }


    void getRecipe() {
        pd = AppController.getDialog(this);
        pd.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                "https://api.spoonacular.com/recipes/random?apiKey=0de4c154135142d3b7adf45dd6b52b83",
                response -> {
                    pd.dismiss();
                    Log.e(TAG, response);
                    try {
                        JSONObject obj1 = new JSONObject(response);
                        JSONArray array = obj1.getJSONArray("recipes");
                        list.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String title = obj.getString("title");
                            String image = obj.getString("image");
                            list.add(0, new Model(title, image));
                        }
                        adapter = new RecyclerViewAdapter(list, Swipe.this);
                        manager =
                                new CardStackLayoutManager(Swipe.this, Swipe.this);
                        manager.setStackFrom(StackFrom.Top);
                        manager.setVisibleCount(3);
                        manager.setTranslationInterval(8.0f);
                        manager.setScaleInterval(0.95f);
                        manager.setSwipeThreshold(0.3f);
                        manager.setMaxDegree(20.0f);
                        manager.setDirections(Direction.HORIZONTAL);
                        manager.setCanScrollHorizontal(true);
                        manager.setCanScrollVertical(true);
                        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
                        manager.setOverlayInterpolator(new LinearInterpolator());
                        stackView.setLayoutManager(manager);
                        stackView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            pd.dismiss();
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, "get-groups");
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction.name().equals("Right")) {
            getRecipe();
        } else if (direction.name().equals("Left")) {
            getRecipe();
        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }


}
