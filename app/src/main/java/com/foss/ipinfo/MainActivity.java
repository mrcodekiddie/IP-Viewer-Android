package com.foss.ipinfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private API mApi;
    private IpInfo ipInfo;
    private RecyclerView recyclerView;
    private List<Data> dataList = new ArrayList<Data>();
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    Call<IpInfo> apiCall;
    DataAdapter adapter;
    int colors[]={Color.BLUE,Color.MAGENTA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("IP Info");


        swipeRefreshLayout = findViewById(R.id.layout_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(colors);


        recyclerView = findViewById(R.id.iRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new DataAdapter(dataList, MainActivity.this);
        recyclerView.setAdapter(adapter);

        mApi = ServiceGenerator.createService(API.class);

        apiCall = mApi.getIpInfo();
        pullData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullData();
            }
        });


    }

    private void pullData()
    {
        apiCall.cancel();
        apiCall.clone().enqueue(new Callback<IpInfo>() {
            @Override
            public void onResponse(Call<IpInfo> call, Response<IpInfo> response) {
                if (response.isSuccessful()) {

                    ipInfo = response.body();
                    dataList.clear();
                    Log.v("kiddo","data clearded");
                    dataList.add(new Data("IP", ipInfo.ip));
                    dataList.add(new Data("Country", ipInfo.country));
                    dataList.add(new Data("Region", ipInfo.region));
                    dataList.add(new Data("City", ipInfo.city));
                    dataList.add(new Data("Location", ipInfo.loc));
                    dataList.add(new Data("Postal", ipInfo.postal));
                    dataList.add(new Data("Organization", ipInfo.org));
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);


                }
            }

            @Override
            public void onFailure(Call<IpInfo> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this)
                        .setCancelable(false)
                        .setTitle("Error")
                        .setMessage(t.getMessage())
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                swipeRefreshLayout.setRefreshing(false);
                                dialog.dismiss();
                            }

                        });
                alert.show();
            }
        });


    }
}
