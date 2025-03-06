package com.aurelio.ui;

import com.aurelio.persistence.entity.BoardColumnEntity;
import com.aurelio.persistence.entity.BoardColumnKindEnum;
import com.aurelio.persistence.entity.BoardEntity;
import com.aurelio.service.BoardService;

import static com.aurelio.persistence.config.ConnectionConfig.getConnection;
import static com.aurelio.persistence.entity.BoardColumnKindEnum.INITIAL;
import static com.aurelio.persistence.entity.BoardColumnKindEnum.PENDING;
import static com.aurelio.persistence.entity.BoardColumnKindEnum.FINAL;
import static com.aurelio.persistence.entity.BoardColumnKindEnum.CANCEL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu board.");
        entity.setName(scanner.next());
        System.out.println("Seu board terá coluna além de 3 padrões? Se sim, informe quantas. Se não, digite '0'.");
        var additionalColumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board.");
        var initialColumnName = scanner.next();
        var initialColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        for(int i = 0; i < additionalColumns; i++){
            System.out.println("Informe o nome da coluna de tarefa pendente do board.");
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumn(pendingColumnName, PENDING, i+1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final do board.");
        var finalColumnName = scanner.next();
        var finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board.");
        var cancelColumnName = scanner.next();
        var cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
        }
    }

    private void selectBoard() {
    }

    private void deleteBoard() {
    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}
