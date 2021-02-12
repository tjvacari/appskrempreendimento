package com.skr.empreendimento.retrofit;

import retrofit2.Call;
import retrofit2.Response;

public interface CallBackRetrofit {

    void onResponse(Call call, Response response, Throwable t);

}
