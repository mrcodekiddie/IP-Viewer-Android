package com.foss.ipinfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar_main_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("IP Info");


        recyclerView = findViewById(R.id.iRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        final DataAdapter adapter = new DataAdapter(dataList, MainActivity.this);
        recyclerView.setAdapter(adapter);

        mApi = ServiceGenerator.createService(API.class);

        Call<IpInfo> apiCall = mApi.getIpInfo();


        apiCall.clone().enqueue(new Callback<IpInfo>() {
            @Override
            public void onResponse(Call<IpInfo> call, Response<IpInfo> response) {
                if (response.isSuccessful()) {

                    ipInfo = response.body();
                    dataList.add(new Data("IP", ipInfo.ip));
                    dataList.add(new Data("Country", ipInfo.country));
                    dataList.add(new Data("Region", ipInfo.region));
                    dataList.add(new Data("City", ipInfo.city));
                    dataList.add(new Data("Location", ipInfo.loc));
                    dataList.add(new Data("Postal", ipInfo.postal));
                    dataList.add(new Data("Organization", ipInfo.org));
                    adapter.notifyDataSetChanged();


                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this)
                            .setCancelable(false)
                            .setTitle("Error")
                            .setMessage("Check your internet connection")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();

                }
            }

            @Override
            public void onFailure(Call<IpInfo> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });


    }
}
