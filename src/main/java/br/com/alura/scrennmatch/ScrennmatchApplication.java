package br.com.alura.scrennmatch;

import br.com.alura.scrennmatch.model.DadosSerie;
import br.com.alura.scrennmatch.service.ConsumoAPI;
import br.com.alura.scrennmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrennmatchApplication implements CommandLineRunner {

    //usando jackson, equivalente ao gson, porem mais pratico para o maven. Fica no pom.xml nas dependencias.
    public static void main(String[] args) {
        SpringApplication.run(ScrennmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumoAPI = new ConsumoAPI();
        var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=hannibal&y=2013&apikey=8e8b16a3");
        System.out.println(json);

        ConverteDados converteDados = new ConverteDados();
        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);
    }
}
