//package com.example.SearchEngineHibernate.controllers;
//
//import com.example.SearchEngineHibernate.dto.app.App;
//import com.example.SearchEngineHibernate.dto.search.AppError;
//import com.example.SearchEngineHibernate.dto.search.SearchResult;
//import com.example.SearchEngineHibernate.dto.search.SearchingResult;
//import com.example.SearchEngineHibernate.dto.statistics.SitesList;
//import com.example.SearchEngineHibernate.services.SearchSystem.AroundSearching;
//import com.example.SearchEngineHibernate.services.SearchSystem.LocalSearching;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//
//
//@RestController
//public class SearchController {
//
//    @Autowired
//    AppError appError;
//    @Autowired
//    AroundSearching aroundSearching;
//
//    @Autowired
//    LocalSearching localSearching;
//    @Autowired
//    SearchingResult searchingResult;
//
//    @Autowired
//    SearchResult searchResult;
//    @Autowired
//    private App app;
//
//    @Autowired
//    private SitesList sitesList;
//
//    private boolean stop = false;
//
//    @GetMapping("/api/search")
//    public SearchingResult searchController(@RequestParam (required = false, defaultValue = "null")  String query, @RequestParam (required = false) String site,
//                                            @RequestParam (required = false, defaultValue = "0") int offset,
//                                            @RequestParam (required = false, defaultValue = "20") int limit ) throws SQLException, IOException {
//
//        ArrayList <SearchResult> searchResults = new ArrayList<>();
//        System.out.println(query);
//        System.out.println(site);
//        System.out.println(offset);
//        System.out.println(limit);
//
//        searchingResult.setAppError(appError);
//        searchingResult.setResult(searchResults);
//
//        ArrayList<String> statuses = new ArrayList<>();
//        String status = new String();
//        Connection connection = DriverManager.getConnection(app.getUrl(), app.getUser(), Integer.toString(app.getPass()));
//
//        if (query.equals("null")) {
//            appError.setResult(false);
//            appError.setErrorMess("Задан пустой поисковый запрос");
//        } else {
//            if (site != null) {
//                site = site.replace("https://","");
//                PreparedStatement preparedStatement = connection.prepareStatement("SELECT status FROM site where url ='" + site + "'");
//                ResultSet result = preparedStatement.executeQuery();
//                while (result.next()) {
//                    status = result.getString(1);
//                    System.out.println("Статус сайта " + status);
//                }
//                if (status.equals("INDEXING")) {
//                    appError.setResult(false);
//                    appError.setErrorMess("Запущена индексация сайта. Повторите запрос через 10 минут");
//                } else if (status.equals("FAILED")) {
//                    appError.setResult(false);
//                    appError.setErrorMess("Ошибка индексации. Запустите индексацию.");
//                } else if (status.equals("INDEXED")) {
//
//                    searchResults = localSearching.searchSystem(query,site);
//                    searchingResult.setResult(searchResults);
//                    System.out.println("Результат поиска -" + searchResults.get(0).getSnipper());
//                    System.out.println("Запрос -" + query);
//                    System.out.println("Сайт - "+ site);
//
//                }
//            } else {
//                PreparedStatement preparedStatement = connection.prepareStatement("SELECT status FROM site");
//                ResultSet result = preparedStatement.executeQuery();
//                while (result.next()) {
//                    status = result.getString(1);
//                    statuses.add(status);
//                }
//                if (statuses.contains("INDEXING")) {
//                    appError.setResult(false);
//                    appError.setErrorMess("Запущена индексация. Повторите запрос через 10 минут");
//                } else if (statuses.contains("FAILED")) {
//                    appError.setResult(false);
//                    appError.setErrorMess("Ошибка индексации. Запустите индексацию.");
//                } else if (status.equals("INDEXED")) {
//                    searchResults = aroundSearching.searchSystem(query);
//                    searchingResult.setResult(searchResults);
//                }
//            }
//        }
//        System.out.println("ИТОГ " + searchingResult.getAppError());
//        System.out.println("ИТОГ2 " + searchingResult.getResult());
//        return searchingResult;
//
//    }
//}