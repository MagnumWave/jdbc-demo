package com.aurelio.ui;

import com.aurelio.dto.BoardColumnInfoDTO;
import com.aurelio.persistence.entity.BoardEntity;
import com.aurelio.persistence.entity.CardEntity;
import com.aurelio.service.CardService;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Scanner;

import static com.aurelio.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class BoardMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    private final BoardEntity entity;

    public void execute() {
        try {
            System.out.printf("Bem vindo ao board %s, selecione a opção desejada\n", entity.getId());
            var option = -1;
            while (option != 9) {
                System.out.println("1 - Criar um novo card");
                System.out.println("2 - Mover um card");
                System.out.println("3 - Bloquear um card");
                System.out.println("4 - Desbloquear um card");
                System.out.println("5 - Cancelar um card");
                System.out.println("6 - Ver board");
                System.out.println("7 - Ver coluna com cards");
                System.out.println("8 - Ver card");
                System.out.println("9 - Voltar para o menu anterior");
                System.out.println("10 - Sair");
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                    case 9 -> System.out.println("Voltando para o menu anterior");
                    case 10 -> System.exit(0);
                    default -> System.out.println("Opção inválida. Informe uma opção do menu.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

    }

    private void createCard() throws SQLException {
        var card = new CardEntity();
        System.out.println("Informe o título do card");
        card.setTitle(scanner.next());
        System.out.println("Informe a descrição do card");
        card.setDescription(scanner.next());
        card.setBoardColumn(entity.getInitialColumn());
        try(var connection = getConnection()){
            new CardService(connection).insert(card);
        }
    }

    private void moveCardToNextColumn() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a próxima coluna");
        var cardId = scanner.nextLong();
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        try(var connection = getConnection()){
            new CardService(connection).moveToNextColumn(cardId, boardColumnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void blockCard() throws SQLException {
        System.out.println("Informe o id do card que será bloqueado");
        var cardId = scanner.nextLong();
        System.out.println("Informe o motivo do bloqueio do card");
        var reason = scanner.next();
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        try(var connection = getConnection()){
            new CardService(connection).block(cardId, reason, boardColumnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void unblockCard() throws SQLException {
        System.out.println("Informe o id do card que será desbloqueado");
        var cardId = scanner.nextLong();
        System.out.println("Informe o motivo do desbloqueio do card");
        var reason = scanner.next();
        try(var connection = getConnection()){
            new CardService(connection).unblock(cardId, reason);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void cancelCard() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a coluna de cancelamento");
        var cardId = scanner.nextLong();
        var cancelColumn = entity.getCancelColumn();
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        try(var connection = getConnection()){
            new CardService(connection).cancel(cardId, cancelColumn.getId(), boardColumnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void showBoard() throws SQLException {
    }

    private void showColumn() throws SQLException {
    }

    private void showCard() throws SQLException {
    }
}
