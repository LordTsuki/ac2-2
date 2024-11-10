package com.example.ac2_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cadastro extends AppCompatActivity {

    private EditText editTextRa;
    private EditText editTextName;
    private EditText editTextZip;
    private EditText editTextStreet;
    private EditText editTextComplement;
    private EditText editTextNeighborhood;
    private EditText editTextCity;
    private EditText editTextUf;
    private Button buttonCadastrar;

    private AlunoAPI alunoAPI;
    private ViaCEPAPI viaCEPAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextRa = findViewById(R.id.ra);
        editTextName = findViewById(R.id.name);
        editTextZip = findViewById(R.id.zip);
        editTextStreet = findViewById(R.id.street);
        editTextComplement = findViewById(R.id.complement);
        editTextNeighborhood = findViewById(R.id.neighborhood);
        editTextCity = findViewById(R.id.city);
        editTextUf = findViewById(R.id.uf);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://672e1f68229a881691ef103a.mockapi.io/api/alunos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        alunoAPI = retrofit.create(AlunoAPI.class);
        viaCEPAPI = retrofit.create(ViaCEPAPI.class);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAluno();
            }
        });

        editTextZip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String cep = editTextZip.getText().toString();
                    if (!cep.isEmpty()) {
                        buscarEndereco(cep);
                    }
                }
            }
        });
    }

    private void buscarEndereco(String cep) {
        Call<Endereco> call = viaCEPAPI.getAddress(cep.replace("-", ""));
        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    editTextStreet.setText(endereco.logradouro);
                    editTextComplement.setText(endereco.complemento);
                    editTextNeighborhood.setText(endereco.bairro);
                    editTextCity.setText(endereco.localidade);
                    editTextUf.setText(endereco.uf);
                } else {
                    Toast.makeText(Cadastro.this, "Erro ao buscar endereço", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(Cadastro.this, "Erro na requisição", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cadastrarAluno() {
        String ra = editTextRa.getText().toString();
        String name = editTextName.getText().toString();
        String zip = editTextZip.getText().toString();
        String street = editTextStreet.getText().toString();
        String complement = editTextComplement.getText().toString();
        String neighborhood = editTextNeighborhood.getText().toString();
        String city = editTextCity.getText().toString();
        String uf = editTextUf.getText().toString();

        Aluno aluno = new Aluno(ra, name, zip, street, complement, neighborhood, city, uf);

        Call<Aluno> call = alunoAPI.createAluno(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Cadastro.this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Cadastro.this, "Erro ao cadastrar aluno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(Cadastro.this, "Erro na requisição", Toast.LENGTH_SHORT).show();
            }
        });
    }
}