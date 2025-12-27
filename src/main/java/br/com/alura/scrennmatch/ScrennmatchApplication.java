package br.com.alura.scrennmatch;

import br.com.alura.scrennmatch.model.Episodio;
import br.com.alura.scrennmatch.principal.Principal;
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
        Principal principal = new Principal();
        principal.exibeMenu();

    }
}
