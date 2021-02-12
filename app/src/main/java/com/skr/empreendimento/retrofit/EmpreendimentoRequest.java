package com.skr.empreendimento.retrofit;

import com.skr.empreendimento.to.FiltroTO;
import com.skr.empreendimento.to.EmpreentimentoTO;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpreendimentoRequest {

    public static void buscarEmpreendimento(CallBackRetrofit callBack, Set<Integer> idsCategoria, Set<Integer> idsTipo, Integer page, Integer max) {
        EmpreendimentoService apiService = new RetrofitConfig().build().create(EmpreendimentoService.class);
        Call<List<EmpreentimentoTO>> call = apiService.callEmpreendimento(idsCategoria, idsTipo, page, max);
        call.enqueue(new Callback<List<EmpreentimentoTO>>() {
            @Override
            public void onResponse(Call<List<EmpreentimentoTO>> call, Response<List<EmpreentimentoTO>> response) {
                callBack.onResponse(call, response, null);
            }

            @Override
            public void onFailure(Call<List<EmpreentimentoTO>> call, Throwable t) {
                callBack.onResponse(call, null, t);
            }
        });
    }

    public static void buscarFiltro(CallBackRetrofit callBack) {
        EmpreendimentoService apiService = new RetrofitConfig().build().create(EmpreendimentoService.class);
        Call<List<FiltroTO>> call = apiService.callFiltro();
        call.enqueue(new Callback<List<FiltroTO>>() {
            @Override
            public void onResponse(Call<List<FiltroTO>> call, Response<List<FiltroTO>> response) {
                callBack.onResponse(call, response, null);
            }

            @Override
            public void onFailure(Call<List<FiltroTO>> call, Throwable t) {
                callBack.onResponse(call, null, t);
            }
        });
    }
}
