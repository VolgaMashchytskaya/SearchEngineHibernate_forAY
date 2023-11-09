//package com.example.SearchEngineHibernate.services.SearchSystem;
//
//import com.example.SearchEngineHibernate.dto.app.App;
//import com.example.SearchEngineHibernate.dto.search.SearchResult;
//import com.example.SearchEngineHibernate.services.DataBaseCreation.LemmaServices;
//import org.apache.lucene.morphology.LuceneMorphology;
//import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
//import org.jsoup.Jsoup;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//@Component
//public class AroundSearching {
//    LuceneMorphology luceneMorphology = new RussianLuceneMorphology();
//    @Autowired
//    private App app;
//
//    public ArrayList<SearchResult> finalSearchResult = new ArrayList<>();
//    @Autowired
//    private LemmaServices lemmaServicesItem;
//
//    private int limit = 50;
//    private final Set<String> lemmas = new HashSet<>();
//    HashMap<Integer, String[]> lemmaMap = new HashMap<>();
//
//    public AroundSearching() throws IOException {
//    }
//
//    public ArrayList<SearchResult> searchSystem(String searchText) throws SQLException {
//        ArrayList<Integer> pagesID = new ArrayList<>();
//        ArrayList<String> sitesUrl = new ArrayList<>();
//
//        Connection connection = DriverManager.getConnection(app.getUrl(),
//                app.getUser(), Integer.toString(app.getPass()));
//
//        PreparedStatement preparedStatementUrl = connection.prepareStatement("SELECT url FROM site");
//        ResultSet resultUrl = preparedStatementUrl.executeQuery();
//        while (resultUrl.next()) {
//            String siteUrl = resultUrl.getString(1);
//            sitesUrl.add(siteUrl);
//        }
//
//        // определяем слишком часто упоминающиеся слова
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT SUM(frequency) FROM lemma");
//        ResultSet result = preparedStatement.executeQuery();
//        result.next();
//        int totalFrequency = result.getInt(1);
//        int limitFrequency = totalFrequency * limit / 100;
//        System.out.println(totalFrequency);
//        System.out.println(limitFrequency);
//
//        for (String siteUrl : sitesUrl) {
//            // ищем id леммы в только по выбранному сайту и формируем список лемм м карту лемм
//            for (String word : lemmaServicesItem.textModify(searchText)) {
//                ResultSet resultSet = connection.createStatement().
//                        executeQuery("SELECT lemma.id, lemma, frequency from " +
//                                "lemma join site on lemma.site_id = site.id " +
//                                "where lemma=" + "'" + word + "' and url = '" + siteUrl + "'");
//                while (resultSet.next()) {
//                    int lemmaId = resultSet.getInt(1);
//                    String lemmaName = resultSet.getString(2);
//                    System.out.println("имя леммы " + lemmaName);
//                    String frequency = String.valueOf(resultSet.getInt(3));
//                    String[] lemmaInfoArray = {lemmaName, frequency};
//                    lemmaMap.put(lemmaId, lemmaInfoArray);
//                    lemmas.add(lemmaName);
//                }
//            }
//
//            if (lemmas.size() != lemmaServicesItem.textModify(searchText).size()) {
//                lemmaMap.clear();
//            } else {
//
//                // фильтруем леммы по встречаемости
//                Map<Integer, String[]> unSortedLemmasMap = lemmaMap.entrySet().stream()
//                        .filter(n -> Integer.parseInt(n.getValue()[1]) < limitFrequency).
//                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//                // сортируем леммы по встречаемости
//
//                Map<Integer, String[]> sortedLemmasMap = unSortedLemmasMap.entrySet().stream()
//                        .sorted(Comparator.comparingInt(e -> Integer.parseInt(e.getValue()[1])))
//                        .collect(Collectors.toMap(
//                                Map.Entry::getKey,
//                                Map.Entry::getValue,
//                                (a, b) -> {
//                                    throw new AssertionError();
//                                },
//                                LinkedHashMap::new));
//                System.out.println("КартаЛемм- " + sortedLemmasMap);
//
//                // по первой самой редкой лемме формируем список страниц из индекса
//
//                int firstKey = sortedLemmasMap.entrySet().stream().findFirst().get().getKey();
//                System.out.println("Самая редкая лемма --," + firstKey);
//                ResultSet resultSetForPagesSearching = connection.createStatement().
//                        executeQuery("SELECT page_id from searchindex where lemma_id = '" + firstKey + "'");
//                while (resultSetForPagesSearching.next()) {
//                    int pageID = resultSetForPagesSearching.getInt(1);
//                    pagesID.add(pageID);
//                }
//                System.out.println("Список страниц - " + pagesID);
//
//                // по списку лемм формируем список страниц и удаляем все которых нет в первом списке
//                for (int lemmaId : sortedLemmasMap.keySet()) {
//
//                    ArrayList<Integer> pagesIDcheck = new ArrayList<>();
//
//                    ResultSet resultSetForPageSearching = connection.createStatement().
//                            executeQuery("SELECT page_id from searchindex where lemma_id = '" + lemmaId + "'");
//                    while (resultSetForPageSearching.next()) {
//                        int pageIDcheck = resultSetForPageSearching.getInt(1);
//                        pagesIDcheck.add(pageIDcheck);
//                    }
//
//
//                    Iterator<Integer> idIterator = pagesID.iterator();
//                    while (idIterator.hasNext()) {
//                        int nextId = idIterator.next();
//                        if (!pagesIDcheck.contains(nextId)) {
//                            idIterator.remove();
//                        }
//                    }
//                    System.out.println("Перечень страниц " + pagesID);
//                }
//
//
//                for (int finalPageId : pagesID) {
//                    float summRanks = 0;
//                    String url = "";
//                    String content = "";
//                    ResultSet resultSet = connection.createStatement().
//                            executeQuery("SELECT path, content from page where id='" + finalPageId + "'");
//                    while (resultSet.next()) {
//                        url = resultSet.getString(1);
//                        content = resultSet.getString(2);
//                    }
//                    String pageText = Jsoup.parse(content).text();
//
//                    ArrayList<Integer> indexList = new ArrayList<>();
//                    Set<String> lemmaReverseForms = new HashSet<>();
//
//                    List<String> textSplit = List.of(content.replaceAll("[\\s<>]", " ").
//                            replaceAll("[^А-Яа-я\\s+\\,\\.]", "").
//                            replaceAll("\\+", "").replaceFirst("^[^А-Яа-я]+", "").
//                            replaceAll("[<>]", ",").replaceAll("\\s+", ",").
//                            replaceAll("\\.+", ",").replaceAll(",+", ",").split(","));
//
//                    for (int finalLemmaId : sortedLemmasMap.keySet()) {
//                        ResultSet relevance = connection.createStatement().
//                                executeQuery("SELECT rating from searchindex where page_id ='" +
//                                        finalPageId + "'" + " and lemma_id ='" + finalLemmaId + "'");
//                        while (relevance.next()) {
//                            float rank = relevance.getFloat(1);
//                            summRanks += rank;
//                        }
//                    }
//
//                    for (String finalLemmaName : lemmas) {
//                        for (String lemmaReverseForm : textSplit) {
//                            if (luceneMorphology.getNormalForms(lemmaReverseForm.toLowerCase()).
//                                    get(0).equals(finalLemmaName)) {
//                                lemmaReverseForms.add(lemmaReverseForm);
//                                System.out.println("Слова для поиска -" + lemmaReverseForms);
//                            }
//                        }
//                    }
//
//                    for (String lemmaReverse : lemmaReverseForms) {
//                        int indexStart = 0;
//                        int indexFinish = 0;
//                        Pattern pattern = Pattern.compile("[^а-яA-Я]{1}" + lemmaReverse + "[^а-яA-Я]{1}");
//                        Matcher matcher = pattern.matcher(pageText);
//                        while (matcher.find()) {
//                            indexStart = matcher.start();
//                            indexList.add(indexStart);
//                            indexFinish = matcher.end();
//                            indexList.add(indexFinish);
//                            System.out.println("Слова -" + lemmaReverse + indexStart + indexFinish);
//                        }
//                    }
//                    indexList.sort(Comparator.naturalOrder());
//                    System.out.println("список индексов -" + indexList);
//
//                    String snipper = new String();
//                    int lag = 100;
//                    for (int i = 0; i < indexList.size() - 1; i = i + 2) {
//
//                        int start = indexList.get(i);
//                        int limit = pageText.length();
//                        int finish = ((indexList.get(i + 1) + lag) <= limit) ? indexList.get(i + 1) + lag : limit;
//
//                        String partSnipper = pageText.substring(start, finish);
//                        snipper = snipper + partSnipper;
//                    }
//
//                    for (String lemmaReverseOrigin : lemmaReverseForms) {
//                        String lemmaReverseBold = "<b>" + lemmaReverseOrigin + "</b>";
//                        snipper = snipper.replace(lemmaReverseOrigin, lemmaReverseBold);
//                    }
//
//                    Float absRelevance = summRanks;
//
//
//                    String title = String.valueOf(Jsoup.parse(content).selectFirst("title"));
//
//                    SearchResult searchResult = new SearchResult();
//                    searchResult.setUrl(siteUrl+url);
//                    System.out.println("Формируем объект поиска = " + url);
//                    searchResult.setTitle(title);
//                    System.out.println("Формируем объект поиска = " + title);
//                    searchResult.setSnipper(snipper);
//                    System.out.println("Формируем объект поиска = " + snipper);
//                    searchResult.setRelevance(absRelevance);
//                    finalSearchResult.add(searchResult);
//                }
//
//                if (lemmaMap.isEmpty()) {
//                    SearchResult searchResult = new SearchResult();
//                    searchResult.setUrl(null);
//                    searchResult.setTitle(null);
//                    searchResult.setSnipper("Соответствие не найдено");
//                    searchResult.setRelevance(null);
//                    finalSearchResult.add(searchResult);
//                }
//            }
//        }
//        return finalSearchResult;
//    }
//}




