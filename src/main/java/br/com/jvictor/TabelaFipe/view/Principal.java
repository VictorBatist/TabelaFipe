package br.com.jvictor.TabelaFipe.view;

import br.com.jvictor.TabelaFipe.service.ConsumoApi;
import br.com.jvictor.TabelaFipe.service.ConverteDados;

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
        var marcas = conversor.obterLista(json, List.class);
    }
}
