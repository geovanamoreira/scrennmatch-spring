package br.com.alura.scrennmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Esse record pega e modela os dados que querem ser trazidos da api, nao faz metodos nem nada, só coisa basica e pequena
//Meio que dao apelidos pros nomes da api
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {
}
