package com.example.ceyda.kurlaralson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.DecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.util.List;

import retrofit2.Callback;

public class DovizAdapter extends BaseAdapter implements Serializable {

    private List<DovizViewModel> dovizList;
    private Context context;
    public DovizAdapter(Context context, List<DovizViewModel> dovizList) {

        this.context = context;
        this.dovizList = dovizList;

    }
    @Override
    public int getCount() {
        return dovizList.size();
    }

    @Override
    public Object getItem(int position) {
        return dovizList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        DovizViewModel doviz = dovizList.get(position);

        //View üzerinden metin bileşenleri alır, metin görüntüleme bileşenlerine bağlanır
        TextView dovizAdiTextView = view.findViewById(R.id.dovizAdiTextView);
        TextView degisimSatisTextView = view.findViewById(R.id.degisimSatisTextView);
        TextView degisimAlisTextView = view.findViewById(R.id.degisimAlisTextView);
        TextView alisTextView = view.findViewById(R.id.alisTextView);
        TextView satisTextView = view.findViewById(R.id.satisTextView);
        ImageView durumSatis = view.findViewById(R.id.usdDurumSatis);
        ImageView durumAlis = view.findViewById(R.id.usdDurumAlis);

        dovizAdiTextView.setText(doviz.getSembol());

        //Satış değişim
        double satisDegeri = doviz.getSatisFark();
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        String satisString = decimalFormat.format(satisDegeri); // YFark'ı 0'dan sonra 2 basamak gösteren bir String'e dönüştürme

        //Alış değişim
        double alisDegeri = doviz.getAlisFark();
        DecimalFormat decimalFormat2 = new DecimalFormat("0.0000");
        String alisString = decimalFormat2.format(alisDegeri); // YFark'ı 0'dan sonra 2 basamak gösteren bir String'e dönüştürme


        if(doviz.getYFark() == doviz.getSatis()){
            // Eğer fark değeri sıfıra eşitse, durumAlis, durumSatis ve degisimTextView'ı görünmez yap.
            alisTextView.setText(String.valueOf(doviz.getAlis()));
            satisTextView.setText(String.valueOf(doviz.getSatis()));

            durumAlis.setVisibility(View.INVISIBLE);
            durumSatis.setVisibility(View.INVISIBLE);
            degisimSatisTextView.setVisibility(View.INVISIBLE);
            degisimAlisTextView.setVisibility(View.INVISIBLE);
        }
        else{
            alisTextView.setText(String.valueOf(doviz.getAlis()));
            satisTextView.setText(String.valueOf(doviz.getSatis()));

            if(doviz.getSatisFark() > 0){
                degisimSatisTextView.setText(satisString);
                degisimSatisTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
            }
            else if(doviz.getSatisFark() < 0){
                degisimSatisTextView.setText(satisString);
                degisimSatisTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
            else{
                degisimSatisTextView.setText(satisString);
                degisimSatisTextView.setTextColor(ContextCompat.getColor(context, R.color.orange));
            }

            if(doviz.getAlisFark() > 0){
                degisimAlisTextView.setText(alisString);
                degisimAlisTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
            }
            else if(doviz.getAlisFark() < 0){
                degisimAlisTextView.setText(alisString);
                degisimAlisTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
            }else{
                degisimAlisTextView.setText(alisString);
                degisimAlisTextView.setTextColor(ContextCompat.getColor(context, R.color.orange));
            }

            if(doviz.getSatisFark() > 0){
                durumSatis.setImageResource(R.drawable.increase);
            }else if(doviz.getSatisFark()  < 0){
                durumSatis.setImageResource(R.drawable.decrease);
            }else{
                durumSatis.setImageResource(R.drawable.stabil);
            }

            if(doviz.getAlisFark()  > 0){
                durumAlis.setImageResource(R.drawable.increase);
            }
            else if(doviz.getAlisFark()  < 0){
                durumAlis.setImageResource(R.drawable.decrease);
            }else{
                durumSatis.setImageResource(R.drawable.stabil);
            }
        }
        return view;
    }
}
