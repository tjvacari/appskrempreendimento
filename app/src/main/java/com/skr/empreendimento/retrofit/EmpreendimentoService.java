package com.skr.empreendimento.retrofit;

import com.skr.empreendimento.to.FiltroTO;
import com.skr.empreendimento.to.EmpreentimentoTO;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EmpreendimentoService {

    @GET("empreendimento")
    public Call<List<EmpreentimentoTO>> callEmpreendimento(@Query("idsCategoria") Set<Integer> idsCategora, @Query("idsTipo") Set<Integer> idsTipo, @Query("page") Integer page, @Query("max") Integer max);

    @GET("filtro")
    public Call<List<FiltroTO>> callFiltro();

}
