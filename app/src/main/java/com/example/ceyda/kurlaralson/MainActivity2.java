package com.example.ceyda.kurlaralson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private EditText amountEditText;
    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private TextView resultTextView;
    private TextView kurAdiTextView;
    private TextView alinanKurAdiView;
    private ImageView convertImage;
    private TextView secimBirTextView;
    private TextView secimIkiTextView;

    private double alinanOran;
    private double satilanOran;


    private BottomNavigationView bottomNav;
    private List<DovizViewModel> dovizList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.ikinciSayfa);

        // BottomNavigationView için seçim olay dinleyicisi
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if (item.getItemId() == R.id.birinciSayfa) {

                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish(); // MainActivity2'yi stack'ten kaldırıyoruz
                    return true;
                }else if(item.getItemId() == R.id.ikinciSayfa){
                    //item.setChecked(true);
                    return true;
                }
                else {
                    return false;
                }

            }
        });

        Intent intent = getIntent();
        dovizList = (List<DovizViewModel>) intent.getSerializableExtra("dovizList");

        // XML öğelerini tanımla
        amountEditText = findViewById(R.id.edit_amount_to_convert_value);
        fromCurrencySpinner = findViewById(R.id.convert_from_dropdown_menu);
        toCurrencySpinner = findViewById(R.id.convert_to_dropdown_menu);
        kurAdiTextView = findViewById(R.id.view_amount_to_convert_value);
        resultTextView = findViewById(R.id.conversion_rate);
        alinanKurAdiView = findViewById(R.id.conversion_rate_result);
        secimBirTextView = findViewById(R.id.convert_from_title);
        secimIkiTextView = findViewById(R.id.convert_to_title);
        convertImage = findViewById(R.id.convertImage);


        // Spinner'lar için verileri ayarla
        List<String> kurlar = new ArrayList<>();
        kurlar.add("TRY");
        kurlar.add("USD");
        kurlar.add("EUR");
        kurlar.add("BTC");
        kurlar.add("C");
        kurlar.add("ETH");
        kurlar.add("GA");
        kurlar.add("GAG");
        kurlar.add("GBP");


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kurlar);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencySpinner.setAdapter(spinnerAdapter);
        toCurrencySpinner.setAdapter(spinnerAdapter);

        String selectedCurrency = fromCurrencySpinner.getSelectedItem().toString();
        kurAdiTextView.setText(selectedCurrency);

        String givenCurrency = toCurrencySpinner.getSelectedItem().toString();
        alinanKurAdiView.setText(givenCurrency);

        fromCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Kur adını güncelle
                String selectedCurrency = fromCurrencySpinner.getSelectedItem().toString();
                kurAdiTextView.setText(selectedCurrency);
                performConversion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do here
            }
        });

        toCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Alınan kur adını güncelle
                String givenCurrency = toCurrencySpinner.getSelectedItem().toString();
                alinanKurAdiView.setText(givenCurrency);
                performConversion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do here
            }
        });

        // Dönüşüm miktarı girdiği anda dönüşümü gerçekleştir
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performConversion();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing to do here
            }
        });

        convertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Alınan ve satılan değerleri değiştir
                String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
                String toCurrency = toCurrencySpinner.getSelectedItem().toString();
                fromCurrencySpinner.setSelection(((ArrayAdapter<String>) fromCurrencySpinner.getAdapter()).getPosition(toCurrency));
                toCurrencySpinner.setSelection(((ArrayAdapter<String>) toCurrencySpinner.getAdapter()).getPosition(fromCurrency));


                // Gireceğimiz miktarı alınan değerden satılan değere dönüştür
                String amountString = amountEditText.getText().toString();
                amountString = amountString.replace(',', '.');
                if (!amountString.isEmpty()) {
                    double miktar;
                    try {
                        miktar = Double.parseDouble(amountString);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    // Alınan ve satılan oranları spinnerlara göre kontrol et
                    String fromKur = fromCurrencySpinner.getSelectedItem().toString();
                    String toKur = toCurrencySpinner.getSelectedItem().toString();
                    double donusumSonucu;

                    double alinanOran = alinanDegerAl(dovizList, toKur);
                    double satilanOran = satilanDegerAl(dovizList, fromKur);
                    donusumSonucu = kurDonusumYap(miktar, satilanOran, alinanOran);


                    // DecimalFormat kullanarak sonucu düzenle ve virgül yerine nokta kullan
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.US));
                    String donusumSonucuString = decimalFormat.format(donusumSonucu);
                    resultTextView.setText(donusumSonucuString);
                } else {
                    resultTextView.setText("");
                }
            }
        });


    }

    // Runnable to update data periodically
    private Runnable dataUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            // dovizList güncellendikten sonra, sonuçları gösteren performConversion() metodunu çağırın
            performConversion();

            // Bir sonraki veri güncellemesini belirtilen aralık sonrasında zamanlayın
            //dataUpdateHandler.postDelayed(this, DATA_UPDATE_INTERVAL);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity kapatıldığında dataUpdateRunnable geri çağırımlarını kaldırın
        //dataUpdateHandler.removeCallbacks(dataUpdateRunnable);
    }
    public void performConversion() {

        String amountString = amountEditText.getText().toString();

        // Virgül karakterini nokta karakterine dönüştür
        amountString = amountString.replace(',', '.');



        if (amountString.isEmpty()) {
            resultTextView.setText("");
            return;
        }

        //double miktar = Double.parseDouble(amountString);
        double miktar;
        try {
            miktar = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
        String toCurrency = toCurrencySpinner.getSelectedItem().toString();


        if (dovizList != null) {
            satilanOran = satilanDegerAl(dovizList, fromCurrency);
            alinanOran = alinanDegerAl(dovizList, toCurrency);

            double donusumSonucu = kurDonusumYap(miktar, satilanOran, alinanOran);


            // DecimalFormat kullanarak sonucu düzenle ve virgül yerine nokta kullan
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.US));
            String donusumSonucuString = decimalFormat.format(donusumSonucu);

            resultTextView.setText(donusumSonucuString);
        } else {
            resultTextView.setText("Döviz verileri alınamadı");
        }

    }

    private double kurDonusumYap(double miktar, double fromOran, double toOran) {
        double donusumSonucu = miktar * (fromOran / toOran);
        return donusumSonucu;
    }

    private double satilanDegerAl(List<DovizViewModel> dovizList, String satilanKur) {
        double satilanDeger = 1.0;

        for (DovizViewModel doviz : dovizList) {
            String kurAdi = doviz.getSembol();

            if (kurAdi.equals(satilanKur)) {
                satilanDeger = doviz.getSatis();
                break;
            }
        }

        return satilanDeger;

    }


    private double alinanDegerAl(List<DovizViewModel> dovizList, String alinanKur) {
        double alinanDeger = 1.0;

        for (DovizViewModel doviz : dovizList) {
            String kurAdi = doviz.getSembol();

            if (kurAdi.equals(alinanKur)) {
                alinanDeger = doviz.getAlis();
                break;
            }
        }

        return alinanDeger;
    }

}