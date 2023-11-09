//package com.example.SearchEngineHibernate.controllers;
//
//import com.example.SearchEngineHibernate.dto.app.App;
//import com.example.SearchEngineHibernate.dto.search.AppError;
//import org.jsoup.Jsoup;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.sql.*;
//
//
//@RestController
//public class PostController {
//
//    @Autowired
//    private App app;
//
//    @Autowired
//    private AppError appError;
//
//    public PostController() throws SQLException {
//    }
//
//    @PostMapping("/api/indexPage")
//    public AppError indexPageIndexingController(@RequestBody String url) throws SQLException, IOException {
//        url = url.replace("url=", "").replace("%2F","/").replace("%3A",":");
//
//        Connection connection = DriverManager.getConnection(app.getUrl(), app.getUser(), Integer.toString(app.getPass()));
//        if (!siteVerification(url)) {
//            appError.setResult(false);
//            appError.setErrorMess("Данная страница находится за пределами сайтов, " +
//                    "указанных в конфигурационном файле");
//        } else {
//            if (pageVerification(url)) {
//                System.out.println("Обновление страницы начинается");
//                System.out.println(url);
//                System.out.println(pageVerification(url));
//                org.jsoup.Connection.Response response = Jsoup.connect(url).userAgent("Chrome/4.0.249.0 Safari/532.5")
//                        .referrer("http://www.google.com").execute();
//                System.out.println("Обновление страницы продолжается");
//                int statusCode = response.statusCode();
//                System.out.println(statusCode);
//
//                if (statusCode == 200) {
//                    String html = Jsoup.connect(url).userAgent("Chrome/4.0.249.0 Safari/532.5")
//                            .referrer("http://www.google.com").get().html().replaceAll("\\s+", " ").
//                            replaceAll("'", "");
//
//                    System.out.println(html);
//                    System.out.println(pageUrlExtract(url));
//
//                    String sql = "UPDATE page SET content = ? WHERE path = '" + pageUrlExtract(url) +"'";
//
//                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                    preparedStatement.setString(1,html);
//
//
//                    System.out.println("Обновление страницы завершено успешно");
//
//                } else {
//                    appError.setResult(false);
//                    appError.setErrorMess("Ошибка подключения. Попробуйте еще раз.");
//                }
//            } else {
//                System.out.println("Индексация страницы начинается");
//                org.jsoup.Connection.Response response = Jsoup.connect(url).userAgent("Chrome/4.0.249.0 Safari/532.5")
//                        .referrer("http://www.google.com").execute();
//                int statusCode = response.statusCode();
//
//                if (statusCode == 200) {
//                    String html = Jsoup.connect(url).userAgent("Chrome/4.0.249.0 Safari/532.5")
//                            .referrer("http://www.google.com").get().html().replaceAll("\\s+", " ").
//                            replaceAll("'", "");
//
//                    String site = siteUrlExtract(url);
//                    ResultSet id = connection.createStatement().executeQuery("SELECT id from site where url= " + site);
//                    int siteID = id.getInt(1);
//
//                    String sql = "INSERT into page(site_id, path, code, content) VALUES ( ?,?,?,?)";
//
//                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                    preparedStatement.setInt(1,siteID);
//                    preparedStatement.setString(2,pageUrlExtract(url));
//                    preparedStatement.setInt(3,200);
//                    preparedStatement.setString(4,html);
//
//                    System.out.println("Индексация страницы окончена");
//
//                } else {
//                    appError.setResult(false);
//                    appError.setErrorMess("Ошибка подключения. Попробуйте еще раз.");
//                }
//            }
//
//        }
//        return appError;
//    }
//
//
//    public boolean siteVerification(String url) throws SQLException {
//        boolean result;
//        Connection connection = DriverManager.getConnection(app.getUrl(), app.getUser(), Integer.toString(app.getPass()));
//        String site = siteUrlExtract(url);
//        ResultSet resultSet = connection.createStatement().executeQuery("SELECT url from site where url = '"+ site+ "'");
//
//        result = resultSet.next();
//        return result;
//
//    }
//
//    public boolean pageVerification(String url) throws SQLException {
//        boolean result;
//        Connection connection = DriverManager.getConnection(app.getUrl(), app.getUser(), Integer.toString(app.getPass()));
//
//        String site = siteUrlExtract(url);
//        String page = pageUrlExtract(url);
//        ResultSet resultSet = connection.createStatement().executeQuery("SELECT path from page join site " +
//                "on site.id = page.site_id where path= '"+ page + "'"+ "and url ='" + site +"'");
//
//        result = resultSet.next();
//        return result;
//    }
//
//    public String siteUrlExtract (String url) throws SQLException {
//        String siteUrl = url.replace("http://","").replace("https://","").
//                replace("http://www.","").replace("https://www.","").
//                replace("www.","").split("/")[0];
//        return siteUrl;
//    }
//
//    public String pageUrlExtract (String url) throws SQLException {
//        String siteUrl = url.replace("http://","").replace("https://","").
//                replace("http:// www.","").replace("https:// www.","").
//                replace("www.","").split("/")[0];
//
//        String pageUrl = url.replace("http://","").replace("http:// www.","").
//                replace("www.","").replace(siteUrl,"");
//
//
//        return pageUrl;
//    }
//}
