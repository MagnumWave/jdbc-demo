package com.aurelio;

import com.aurelio.persistence.migration.MigrationStrategy;
import com.aurelio.ui.MainMenu;

import java.sql.SQLException;

import static com.aurelio.persistence.config.ConnectionConfig.getConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {

//        System.out.println("Olar");
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }
        new MainMenu().execute();

    }
}