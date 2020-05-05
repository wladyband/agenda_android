package com.qintess.cadastroaluno.agenda.services;

import com.qintess.cadastroaluno.agenda.model.Aluno;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface  AlunoService {
    @POST("alunos")
    Call<Void> insere(@Body Aluno aluno);
}
