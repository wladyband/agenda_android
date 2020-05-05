package com.qintess.cadastroaluno.agenda.retrofit;
import com.qintess.cadastroaluno.agenda.services.AlunoService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {
    private Retrofit retrofit;

    public RetrofitInicializador(){
        retrofit = new  Retrofit.Builder().baseUrl("http://192.168.15.4:8080")
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public AlunoService getAlunoService() {
        return retrofit.create(AlunoService.class);
    }
}
