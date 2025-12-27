package br.com.alura.scrennmatch.principal;

import br.com.alura.scrennmatch.model.DadosEpisodios;
import br.com.alura.scrennmatch.model.DadosSerie;
import br.com.alura.scrennmatch.model.DadosTemporada;
import br.com.alura.scrennmatch.model.Episodio;
import br.com.alura.scrennmatch.service.ConsumoAPI;
import br.com.alura.scrennmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private ConverteDados converteDados = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8e8b16a3";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para a busca: ");
        var nomeSerie = leitura.nextLine();

        var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i<= dadosSerie.totalTemporadas(); i++){
            json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);

//        for (int i = 0; i < dadosSerie.totalTemporadas(); i++){
//            List<DadosEpisodios> episodiosTemporada = temporadas.get(i).episodios();
//
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        //mesma coisa do acima porem em uma linha
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //flatmap puxa listas dentro de listas
        //strem gera fluxos de dados que ajudam a resumir tudo
        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList()); //coleta os acima em uma outra lista

        // .toList(); -> usar apenas o to list faz com que a listagem seja fixa/imutavel e nao pegue mudanças feitas futuramente

        dadosEpisodios.forEach(System.out::println);

        System.out.println("\nTop 10 episódios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação " + e))
                .limit(10)
                .peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("\nA partir de que ano você deseja ver os episódios?: ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " | Episódio: " + e.getTitulo() +
                                " | Data lançamento: " + e.getDataLancamento().format(formatador)
                ));

    }
}
