package com.example.ceyda.kurlaralson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ceyda.kurlaralson.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView dovizListView;
    private DovizAdapter dovizAdapter;
    private Context context;
    private Handler handler;
    private Runnable runnable;

    private double eskiUsdSatis;
    private double eskiEurSatis;
    private double eskiGbpSatis;
    private double eskiGaSatis;
    private double eskiCSatis;
    private double eskiGagSatis;
    private double eskiBtcSatis;
    private double eskiEthSatis;

    private double eskiUsdAlis;
    private double eskiEurAlis;
    private double eskiGbpAlis;
    private double eskiGaAlis;
    private double eskiCAlis;
    private double eskiGagAlis;
    private double eskiBtcAlis;
    private double eskiEthAlis;


    private List<DovizViewModel> dovizList = new ArrayList<>();

    private Doviz dovizData;
    //public Doviz doviz;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.birinciSayfa);

        // BottomNavigationView için seçim olay dinleyicisi
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                if (item.getItemId() == R.id.ikinciSayfa) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("dovizList", (Serializable) dovizList);
                    startActivity(intent);
                    return true;

                } else if (item.getItemId() == R.id.birinciSayfa) {
                    //item.setChecked(true);

                    return true;
                } else {
                    return false;
                }
            }
        });


        dovizListView = findViewById(R.id.listView);
        handler = new Handler();
        context = this;

        // Verileri ilk kez yükle
        loadData();

        // Belirli bir süre sonra verileri güncelle
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                loadData();
                handler.postDelayed(runnable, 500000); // 450 saniye
            }
        }, 500000); // 450 saniye


    }

    /*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Runnable'ı durdur
        handler.removeCallbacks(runnable);
    }
*/

    @Override
    protected void onResume() {
        super.onResume();

        // İkinci sayfadan buraya geri döndüğümüzde birinciSayfa seçili olacak.
        bottomNav.setSelectedItemId(R.id.birinciSayfa);
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.genelpara.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Doviz> call = apiService.getDoviz();
        call.enqueue(new Callback<Doviz>() {
            @Override
            public void onResponse(Call<Doviz> call, Response<Doviz> response) {
                if (response.isSuccessful()) {
                    dovizList.clear();
                    Doviz doviz = response.body();
                    if (doviz != null) {

                        dovizData = doviz;


                        dovizList.add(new DovizViewModel("USD", doviz.getUsd().getAlis(), doviz.getUsd().getSatis(), //sembol, alış, satış
                                doviz.calculateDifference(eskiUsdSatis, doviz.getUsd().getSatis()), //Yfark
                                doviz.calculateDifference(eskiUsdAlis, doviz.getUsd().getAlis()), //alışfark
                                doviz.calculateDifference(eskiUsdSatis, doviz.getUsd().getSatis()), //satışfark
                                doviz.getUsd().getDegisim()));

                        dovizList.add(new DovizViewModel("EUR", doviz.getEur().getAlis(), doviz.getEur().getSatis(),
                                doviz.calculateDifference(eskiEurSatis, doviz.getEur().getSatis()),
                                doviz.calculateDifference(eskiEurAlis, doviz.getEur().getAlis()),
                                doviz.calculateDifference(eskiEurSatis, doviz.getEur().getSatis()),
                                doviz.getEur().getDegisim()));

                        dovizList.add(new DovizViewModel("BTC", doviz.getBtc().getAlis(), doviz.getBtc().getSatis(),
                                doviz.calculateDifference(eskiBtcSatis, doviz.getBtc().getSatis()),
                                doviz.calculateDifference(eskiBtcAlis, doviz.getBtc().getAlis()),
                                doviz.calculateDifference(eskiBtcSatis, doviz.getBtc().getSatis()),
                                doviz.getBtc().getDegisim()));

                        dovizList.add(new DovizViewModel("C", doviz.getC().getAlis(), doviz.getC().getSatis(),
                                doviz.calculateDifference(eskiCSatis, doviz.getC().getSatis()),
                                doviz.calculateDifference(eskiCAlis, doviz.getC().getAlis()),
                                doviz.calculateDifference(eskiCSatis, doviz.getC().getSatis()),
                                doviz.getC().getDegisim()));

                        dovizList.add(new DovizViewModel("ETH", doviz.getEth().getAlis(), doviz.getEth().getSatis(),
                                doviz.calculateDifference(eskiEthSatis, doviz.getEth().getSatis()),
                                doviz.calculateDifference(eskiEthAlis, doviz.getEth().getAlis()),
                                doviz.calculateDifference(eskiEthSatis, doviz.getEth().getSatis()),
                                doviz.getEth().getDegisim()));

                        dovizList.add(new DovizViewModel("GA", doviz.getGa().getAlis(), doviz.getGa().getSatis(),
                                doviz.calculateDifference(eskiGaSatis, doviz.getGa().getSatis()),
                                doviz.calculateDifference(eskiGaAlis, doviz.getGa().getAlis()),
                                doviz.calculateDifference(eskiGaSatis, doviz.getGa().getSatis()),
                                doviz.getGa().getDegisim()));

                        dovizList.add(new DovizViewModel("GAG", doviz.getGag().getAlis(), doviz.getGag().getSatis(),
                                doviz.calculateDifference(eskiGagSatis, doviz.getGag().getSatis()),
                                doviz.calculateDifference(eskiGagAlis, doviz.getGag().getAlis()),
                                doviz.calculateDifference(eskiGagSatis, doviz.getGag().getSatis()),
                                doviz.getGag().getDegisim()));

                        dovizList.add(new DovizViewModel("GBP", doviz.getGbp().getAlis(), doviz.getGbp().getSatis(),
                                doviz.calculateDifference(eskiGbpSatis, doviz.getGbp().getSatis()),
                                doviz.calculateDifference(eskiGbpAlis, doviz.getGbp().getAlis()),
                                doviz.calculateDifference(eskiGbpSatis, doviz.getGbp().getSatis()),
                                doviz.getGbp().getDegisim()));

                        dovizAdapter = new DovizAdapter(getApplicationContext(), dovizList);
                        dovizListView.setAdapter(dovizAdapter);

                        // Eski alış ve satış değerlerini güncelle
                        eskiUsdSatis = doviz.getUsd().getSatis();
                        eskiEurSatis = doviz.getEur().getSatis();
                        eskiCSatis = doviz.getC().getSatis();
                        eskiEthSatis = doviz.getEth().getSatis();
                        eskiGaSatis = doviz.getGa().getSatis();
                        eskiGagSatis = doviz.getGag().getSatis();
                        eskiGbpSatis = doviz.getGbp().getSatis();
                        eskiBtcSatis = doviz.getBtc().getSatis();

                        eskiUsdAlis = doviz.getUsd().getAlis();
                        eskiEurAlis = doviz.getEur().getAlis();
                        eskiEthAlis = doviz.getEth().getAlis();
                        eskiBtcAlis = doviz.getBtc().getAlis();
                        eskiGagAlis = doviz.getGag().getAlis();
                        eskiCAlis = doviz.getC().getAlis();
                        eskiGaAlis = doviz.getGa().getAlis();
                        eskiGbpAlis = doviz.getGbp().getAlis();

                        // Diğer döviz birimlerinin değerlerini ve satış farklarını loga yazdır
                        Log.d("MainActivity", "USD Satış: " + doviz.getUsd().getSatis());
                        Log.d("MainActivity", "USD Alış: " + doviz.getUsd().getAlis());
                        Log.d("MainActivity", "USD Satış Farkı: " + doviz.getUsd().getDegisim());
                        Log.d("MainActivity", "USD Eski Satış: " + eskiUsdSatis);

                        Log.d("MainActivity", "EUR Satış: " + doviz.getEur().getSatis());
                        Log.d("MainActivity", "EUR Alış: " + doviz.getEur().getAlis());
                        Log.d("MainActivity", "EUR Satış Farkı: " + doviz.getEur().getDegisim());
                        Log.d("MainActivity", "EUR Eski Satış: " + eskiEurAlis);
                    } else {
                        Toast.makeText(MainActivity.this, "Döviz verileri alınamadı", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("MainActivity", "API request failed");
                }
            }
            @Override
            public void onFailure(Call<Doviz> call, Throwable t) {
                Log.d("MainActivity", "API request failed: " + t.getMessage());
            }
        });
    }

}
