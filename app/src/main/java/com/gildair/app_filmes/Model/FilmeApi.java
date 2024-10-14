package com.gildair.app_filmes.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FilmeApi {
    @GET("filmes.json?alt=media&token=0eeda7c1-ea52-48ca-b238-801596a9f589")
    Call<List<Filme>> getFilmes();
}
