package br.com.alura.scrennmatch.service;

public interface IConverteDados {
    // T nao especifica entidade, podendo ser adaptavel se preciso
    //Recebe o json e vai ser usado para converter a classe
    <T> T obterDados(String json, Class<T> classe);
}
