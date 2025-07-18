package br.com.jvictor.TabelaFipe.view;

import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

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

    }
}
