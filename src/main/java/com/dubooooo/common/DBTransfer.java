package com.dubooooo.common;

import com.dubooooo.entity.ImageEntity;
import com.dubooooo.util.DBUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

import static java.lang.System.out;


public class DBTransfer {

    private static final String url01 = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String url02 = "jdbc:mysql://192.168.76.131:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String user = "root";
    private static final String passwd = "root";

    public static void main(String[] args) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url01);
            config.setUsername("root");
            config.setPassword("root");
            HikariDataSource dataSource = new HikariDataSource(config);
            JdbcTemplate template = new JdbcTemplate(dataSource);
            List<ImageEntity> imageEntities = template.query("select * from image_entity limit 100", new Object[]{}, new BeanPropertyRowMapper<ImageEntity>(ImageEntity.class));
            imageEntities.forEach(e -> out.println(e));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void test01() {
        try {
            DBUtil dbUtil01 = new DBUtil(url01, user, passwd);
            DBUtil dbUtil02 = new DBUtil(url02, user, passwd);
            Connection connection = dbUtil01.connection();
            PreparedStatement preparedStatement01 = connection.prepareStatement("select * from demo.spider_main_entity limit 0,10");
            PreparedStatement preparedStatement02 = connection.prepareStatement("insert into demo.spider_main_entity value (?,?,?,?,?,?,?,?)");
            ResultSet resultSet = preparedStatement01.executeQuery();
            out.println(resultSet);
            ResultSetMetaData metaData = resultSet.getMetaData();
            out.println(metaData);
            int columnCount = metaData.getColumnCount();
            out.println(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                int columnType = metaData.getColumnType(i);
                String columnTypeName = metaData.getColumnTypeName(i);
                String catalogName = metaData.getCatalogName(i);
                out.println("ColumnName:" + columnName);
                out.println("ColumnType:" + columnType);
                out.println("ColumnTypeName:" + columnTypeName);
                out.println("CatalogName:" + catalogName);
            }
            while (resultSet.next()) {
                out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
