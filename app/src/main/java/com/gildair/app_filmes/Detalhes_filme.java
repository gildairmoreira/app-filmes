package com.gildair.app_filmes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class Detalhes_filme extends AppCompatActivity {
    private ImageView dt_capaFilme, dt_playVideo;
    private TextView dt_nomeFilme, dt_descricaoFilme, dt_elencoFilme;
    private Toolbar toolbar_detalhes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_filme);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponentes();
        getSupportActionBar().hide();

        String capa = getIntent().getStringExtra("capa");
        String titulo = getIntent().getStringExtra("titulo");
        String descricao = getIntent().getStringExtra("descricao");
        String elenco = getIntent().getStringExtra("elenco");
        String video = getIntent().getStringExtra("video");

        String stVideo = video;

        Glide.with(getApplicationContext()).load(capa).into(dt_capaFilme);
        dt_nomeFilme.setText(titulo);
        dt_descricaoFilme.setText(descricao);
        dt_elencoFilme.setText(elenco);


        //  Toolbar
        toolbar_detalhes.setNavigationOnClickListener(view -> finish());

        dt_playVideo.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Video.class);
            intent.putExtra("video", stVideo);
            startActivity(intent);
        });

    }
    public void IniciarComponentes() {
        dt_capaFilme = findViewById(R.id.dt_capaFilme);
        dt_playVideo = findViewById(R.id.dt_playVideo);
        dt_nomeFilme = findViewById(R.id.dt_nomeFilme);
        dt_descricaoFilme = findViewById(R.id.dt_descricaoFilme);
        dt_elencoFilme = findViewById(R.id.dt_elencoFilme);
        toolbar_detalhes = findViewById(R.id.toolbar_detalhes);
    }
}