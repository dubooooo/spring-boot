package com.dubooooo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();
    private static final String classname = "com.mysql.jdbc.Driver";

    static {
        try {
            System.out.println(Class.forName(classname));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String url;
    private String user;
    private String passwd;

    public DBUtil(String url, String user, String passwd) {
        this.url = url;
        this.user = user;
        this.passwd = passwd;
    }

    public Connection connection() {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(getConnection());
        }
        return THREAD_LOCAL.get();
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
