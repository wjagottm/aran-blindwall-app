package com.blacetec.blindwalls;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnBlindWallsAvailable {
    private static final String TAG = "MainActivity";
    private ListView mBlindWallListView;
    private BlindWallAdapter mBlindWallAdapter;
    private BlindwallDBHandler blindwallDBHandler;
    private ArrayList<BlindWall> blindWalls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blindwallDBHandler = new BlindwallDBHandler(getApplicationContext(), "blindWall.db", null, 10);

        String[] urls = new String[] { "https://api.blindwalls.gallery/apiv2/murals" };

        BlindWallConnector getProducts = new BlindWallConnector(this);
        getProducts.execute(urls);

        if (!blindwallDBHandler.isDatabaseEmpty()) {
            blindWalls = blindwallDBHandler.getAllWalls();
        }

        mBlindWallListView = (ListView) findViewById(R.id.blindwall_listview);

        mBlindWallAdapter = new BlindWallAdapter(getApplicationContext(), getLayoutInflater(), blindWalls);

        mBlindWallListView.setAdapter(mBlindWallAdapter);

        mBlindWallListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailedBlindWallActivity.class);
                intent.putExtra("BLINDWALL", blindWalls.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnBlindWallsAvailable(BlindWall blindWall) {
        blindWalls.add(blindWall);
        blindwallDBHandler.addBlindWall(blindWall);
        Log.i(TAG, "Blindwall Added (" + blindWall.getTitle() + ")");
        mBlindWallAdapter.notifyDataSetChanged();
    }
}
