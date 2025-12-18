package br.com.alura.scrennmatch.service;

import tools.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{

    //vai converter qualquer tipo de dado para o json e mapear, deixando bonitinho quando exibir
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    //pega e converte de modo generico, sem especificar e pondendo se adaptar
    public <T> T obterDados(String json, Class<T> classe) {
        return mapper.readValue(json, classe);
    }
}
