//package com.example.SearchEngineHibernate.services.DataBaseCreation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//@Component
//public class ComboIndexingMethods {
//
//    @Autowired
//    SearchIndexCreating searchIndexCreation;
//
//    @Autowired
//    LemmaTableCreating lemmaTableCreating;
//
//    @Autowired
//    IndexTableCreating indexTableCreating;
//
//    public void allPagesIndexing() throws SQLException, IOException {
//
//        searchIndexCreation.searchIndexCreation();
//        lemmaTableCreating.lemmaTableCreating();
//        indexTableCreating.indexTableCreating();
//    }
//
//    public void soloPageIndexing() throws SQLException, IOException {
//
//    }
//
//}