package com.example.ac2_2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AlunoAPI {
    @GET("aluno")
    Call<List<Aluno>> getAlunos();

    @POST("aluno")
    Call<Aluno> createAluno(@Body Aluno aluno);
}