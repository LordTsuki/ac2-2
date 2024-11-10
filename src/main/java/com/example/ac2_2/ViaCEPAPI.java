package com.example.ac2_2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

class Endereco {
    public String cep;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String localidade;
    public String uf;
}

public interface ViaCEPAPI {
    @GET("https://viacep.com.br/ws/{cep}/json/")
    Call<Endereco> getAddress(@Path("cep") String cep);
}