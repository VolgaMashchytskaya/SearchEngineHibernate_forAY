//package com.example.SearchEngineHibernate.controllers;
//
//
//import com.example.SearchEngineHibernate.dto.app.App;
//import com.example.SearchEngineHibernate.dto.search.AppError;
//import com.example.SearchEngineHibernate.services.DataBaseCreation.ComboIndexingMethods;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//
//
//@RestController
//public class IndexingController {
//
//
//    @Autowired
//    AppError appError;
//
//    @Autowired
//    private App app;
//
//    @Autowired
//    private ComboIndexingMethods comboIndexingMethods;
//
//    private boolean stop = false;
//
//    public void indexing() throws SQLException, IOException {
//        if (!stop) {
//            comboIndexingMethods.allPagesIndexing();
//        }
//        else return;
//    }
//
//    @GetMapping("api/startIndexing")
//    public AppError startIndexingController() throws SQLException, IOException {
//
//        ArrayList<String> statuses = new ArrayList<>();
//
//               PreparedStatement preparedStatement = connection.prepareStatement("SELECT status FROM site");
//        ResultSet result = preparedStatement.executeQuery();
//        while (result.next()) {
//            String status = result.getString(1);
//            statuses.add(status);
//        }
//
//
//        if (!statuses.contains("INDEXING")) {
//            indexing();
//        } else {
//        appError.setResult(false);
//        appError.setErrorMess("Индексация уже запущена");
//    }
//        return appError;
//
//   }
//
//    @GetMapping("api/stopIndexing")
//    public AppError stopIndexingController() throws SQLException, IOException {
//
//        ArrayList<String> statuses = new ArrayList<>();
//
//        Connection connection = DriverManager.getConnection(app.getUrl(), app.getUser(), Integer.toString(app.getPass()));
//
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT status FROM site");
//        ResultSet result = preparedStatement.executeQuery();
//        while (result.next()) {
//            String status = result.getString(1);
//            statuses.add(status);
//        }
//
//
//        if (statuses.contains("INDEXING")) {
//            stop=true;
//            indexing();
//        } else {
//            appError.setResult(false);
//            appError.setErrorMess("Индексация не запущена");
//        }
//        return appError;
//
//    }
//
//}
