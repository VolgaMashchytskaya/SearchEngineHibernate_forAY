//package com.example.SearchEngineHibernate.services.DataBaseCreation;
//
//import com.example.SearchEngineHibernate.dto.app.App;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ForkJoinPool;
//
//@Component
//public class SearchIndexCreating {
//    @Autowired
//    private App app;
//
//    @Autowired
//    private SiteParsing siteParsing;
//    public static HashMap <Integer, String> sitesMap = new HashMap<>();
//
//    public void searchIndexCreation() throws SQLException, IOException {
//
//
//        ResultSet resultSet = connection.createStatement().executeQuery("SELECT id, url from site");
//
//        while (resultSet.next()) {
//            int id = resultSet.getInt(1);
//            String url =  resultSet.getString(2);
//            String fullurl = "https://" + url;
//            sitesMap.put(id , fullurl);
//    }
//
//            connection.createStatement().execute("CREATE TABLE if not exists page(" +
//                    "id INT NOT NULL AUTO_INCREMENT, " +
//                    "site_id INT NOT NULL, " +
//                    "path TEXT NOT NULL, " +
//                    "code INT NOT NULL, " +
//                    "content MEDIUMTEXT NOT NULL, " +
//                    "PRIMARY KEY(id), KEY(path(500)))");
//            connection.createStatement().execute("TRUNCATE page");
//
//            System.out.println("Парсинг сайтов начинается. Ждите..");
//
//        for (Map.Entry<Integer, String> url: sitesMap.entrySet()) {
//
//            String baseUrl = url.getValue();
//            int baseUrlID = url.getKey();
//
//            SiteMapper siteMapper = new SiteMapper(new SiteNode(baseUrl));
//            SiteParsing siteParsing = new SiteParsing();
//            ForkJoinPool forkJoinPool = new ForkJoinPool();
//
//            forkJoinPool.invoke(siteMapper);
//
//        }
//
//        System.out.println("Парсинг сайтов закончен.");
//
//        }
//    }


