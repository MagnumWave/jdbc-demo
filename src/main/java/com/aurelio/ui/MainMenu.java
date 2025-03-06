package com.aurelio.ui;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.println("Bem-vindo ao gerenciador de boards, escolha a opção desejada.");
        var option = -1;
        while(true){
            System.out.println("1 - Criar um novo board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - Excluir um board");
            System.out.println("4 - Sair");
            switch(option){
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida. Informe uma opção do menu.");
            }
        }
    }

    private void createBoard() {
    }

    private void selectBoard() {
    }

    private void deleteBoard() {
    }
}
