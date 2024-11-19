package com.example.ac2_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaAlunosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlunos;
    private AlunoAdapter alunoAdapter;
    private AlunoAPI alunoAPI;
    private List<Aluno> alunoList = new ArrayList<>();
    private Button btnCadastro, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        btnCadastro = findViewById(R.id.buttonCadastro);
        btnUpdate = findViewById(R.id.buttonUpdate);

        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(ListaAlunosActivity.this, Cadastro.class);
            startActivity(intent);
        });

        btnUpdate.setOnClickListener(v -> {
            fetchStudentData();
        });

        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);
        recyclerViewAlunos.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://672e1f68229a881691ef103a.mockapi.io/api/alunos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        alunoAPI = retrofit.create(AlunoAPI.class);

        fetchStudentData();
    }

    private void fetchStudentData() {
        Call<List<Aluno>> call = alunoAPI.getAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful()) {
                    alunoList = response.body();
                    alunoAdapter = new AlunoAdapter(alunoList);
                    recyclerViewAlunos.setAdapter(alunoAdapter);
                } else {
                    Toast.makeText(ListaAlunosActivity.this, "Failed to fetch student data", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListaAlunosActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                Log.e("Network Error", t.getMessage());
            }
        });
    }
}