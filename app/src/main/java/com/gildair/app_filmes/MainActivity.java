package com.gildair.app_filmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gildair.app_filmes.Adapter.AdapterFilme;
import com.gildair.app_filmes.Model.Filme;
import com.gildair.app_filmes.Model.FilmeApi;
import com.gildair.app_filmes.OnClick.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView_filmes;
    private AdapterFilme adapterFilme;
    private List<Filme> filmeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponentes();
        filmeList = new ArrayList<>();

        //Eventos de Click da RecyclerView
        recyclerView_filmes.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView_filmes,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), Detalhes_filme.class);
                        intent.putExtra("capa", filmeList.get(position).getCapa());
                        intent.putExtra("titulo", filmeList.get(position).getTitulo());
                        intent.putExtra("descricao", filmeList.get(position).getDescricao());
                        intent.putExtra("elenco", filmeList.get(position).getElenco());
                        intent.putExtra("video", filmeList.get(position).getVideo());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));


        //Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://firebasestorage.googleapis.com/v0/b/app-delivery-83fe0.appspot.com/o/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        //Iniciar a RetroFit
        FilmeApi filmeApi = retrofit.create(FilmeApi.class);
        Call<List<Filme>> call = filmeApi.getFilmes();
        call.enqueue(new Callback<List<Filme>>() {
            @Override
            public void onResponse(Call<List<Filme>> call, Response<List<Filme>> response) {
                if (response.code() != 200) {
                    return;
                }
                List<Filme> filmes = response.body();
                for (Filme filme : filmes) {
                    filmeList.add(filme);
                }

                adapterFilme = new AdapterFilme(getApplicationContext(), filmeList);
                recyclerView_filmes.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                recyclerView_filmes.setHasFixedSize(true);
                recyclerView_filmes.setAdapter(adapterFilme);
            }

            @Override
            public void onFailure(Call<List<Filme>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void IniciarComponentes() {
        recyclerView_filmes = findViewById(R.id.recyclerView_filmes);
    }
}