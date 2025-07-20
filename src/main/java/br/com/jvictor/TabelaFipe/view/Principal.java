package br.com.jvictor.TabelaFipe.view;

import br.com.jvictor.TabelaFipe.model.Dados;
import br.com.jvictor.TabelaFipe.model.Modelos;
import br.com.jvictor.TabelaFipe.model.Veiculo;
import br.com.jvictor.TabelaFipe.service.ConsumoApi;
import br.com.jvictor.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){
        var menu = """
                |--- OPÇÕES ---|
                |              |
                | 1-Carro      |
                | 2-Moto       |
                | 3-Caminhão   |
                |              |
                |--------------|
                
                Digite uma das opções para consulta:
                """;

        System.out.println(menu);
        var opcao = scanner.nextLine();
        String address;

        if (opcao.toLowerCase().contains("carr")){
            address = URL_BASE + "carros/marcas";
        }else if (opcao.toLowerCase().contains("mot")){
            address = URL_BASE + "motos/marcas";
        }else {
            address = URL_BASE + "caminhoes/marcas";
        }

        var json = consumoApi.obterDados(address);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nInforme o codigo da marca para consulta: ");
        var codigoMarca = scanner.nextLine();

        address = address + "/" + codigoMarca + "/modelos";
        json = consumoApi.obterDados(address);

        var modeloList = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos da marca: ");
        modeloList.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite o modelo do veiculo que deseja: ");
        var nomeVeiculo = scanner.nextLine();

        List<Dados> modelosFiltrados = modeloList.modelos().stream()
                .filter(m-> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nPor favor digite o codigo do modelo para buscar os valores de avaliação: ");
        var codigoModelo = scanner.nextLine();

        address = address + "/" + codigoModelo + "/anos";
        json = consumoApi.obterDados(address);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var addressYear = address + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(addressYear);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nVeiculos filtrados com avaliações por anuais: ");

        veiculos.forEach(System.out::println);
        
    }
}