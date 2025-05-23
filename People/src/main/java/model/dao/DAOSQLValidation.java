/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mserr
 */
public class DAOSQLValidation {
     private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "people_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public DAOSQLValidation() {
        initializeDatabase(); 
    }

    private void initializeDatabase() {
        createDatabase();      
        createUsersTable();   
        insertDefaultUser();   
    }

    //Crear la BBDD
    private void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            System.out.println("[Éxito] Base de datos creada: " + DB_NAME);
            
        } catch (SQLException ex) {
            System.err.println("[Error] No se pudo crear la base de datos: " + ex.getMessage());
        }
    }

    // Crear tabla usuario
    private void createUsersTable() {
        String sql = 
            "CREATE TABLE IF NOT EXISTS " + DB_NAME + ".users (" +
            "    username VARCHAR(50) PRIMARY KEY," +
            "    password VARCHAR(50) NOT NULL" +
            ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            System.out.println("[Éxito] Tabla 'users' creada");
            
        } catch (SQLException ex) {
            System.err.println("[Error] No se pudo crear la tabla users: " + ex.getMessage());
        }
    }

    // Insertar usuario
    private void insertDefaultUser() {
        String checkSQL = "SELECT COUNT(*) FROM users WHERE username = 'admin'";
        String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = getConnection();
             Statement checkStmt = conn.createStatement();
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
            
            // Verificar si el usuario admin existe
            ResultSet rs = checkStmt.executeQuery(checkSQL);
            rs.next();
            if (rs.getInt(1) == 0) { 
                insertStmt.setString(1, "admin");
                insertStmt.setString(2, "admin123");
                insertStmt.executeUpdate();
                System.out.println("[Éxito] Usuario admin insertado");
            }
            
        } catch (SQLException ex) {
            System.err.println("[Error] No se pudo insertar usuario admin: " + ex.getMessage());
        }
    }

    // Conexion a la BBDD
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
    }

    // Validacion
    public boolean validateCredentials(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // True si las credenciales son válidas
            }
        } catch (SQLException ex) {
            System.err.println("[Error] Validación fallida: " + ex.getMessage());
            return false;
        }
    }
        
}
