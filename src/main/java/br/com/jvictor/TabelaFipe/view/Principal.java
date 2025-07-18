package br.com.jvictor.TabelaFipe.view;

import br.com.jvictor.TabelaFipe.model.Dados;
import br.com.jvictor.TabelaFipe.model.Modelos;
import br.com.jvictor.TabelaFipe.service.ConsumoApi;
import br.com.jvictor.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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
    }
}