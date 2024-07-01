package com.coworking_service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Удерживает соединение с базой данных PostgreSQL.
 */
public class ConnectionHolder {
    private static volatile ConnectionHolder instance;
    private final Connection connection;

    /**
     * Приватный конструктор для создания экземпляра ConnectionHolder.
     *
     * @throws SQLException если возникает ошибка при установке соединения с базой данных
     */
    private ConnectionHolder () throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/coworking_service", "user", "12345");
    }

    /**
     * Возвращает экземпляр ConnectionHolder (singleton).
     *
     * @return экземпляр ConnectionHolder
     * @throws SQLException если возникает ошибка при установке соединения с базой данных
     */
    public static ConnectionHolder getInstance() throws SQLException {
        ConnectionHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionHolder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionHolder();
                }
            }
        }
        return localInstance;
    }

    /**
     * Возвращает текущее соединение с базой данных.
     *
     * @return текущее соединение с базой данных
     */
    public Connection getConnection() {
        return connection;
    }
}
