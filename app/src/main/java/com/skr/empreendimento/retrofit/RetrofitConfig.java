package com.skr.empreendimento.retrofit;

import android.content.SharedPreferences;

import com.skr.empreendimento.EmpreendimentoActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static final String BASE_URL = "http://192.168.0.13:8080/skr/empreendimento/";
    private static final String KEY = "URL_EMP";

    public Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl(getUrlBase())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static String getUrlBase() {
        return  EmpreendimentoActivity.getPreferences().getString(KEY, BASE_URL);
    }

    public static void setUrlBase(String url) {
        SharedPreferences.Editor edit = EmpreendimentoActivity.getPreferences().edit();
        edit.putString(KEY, url);
        edit.apply();
    }
}
