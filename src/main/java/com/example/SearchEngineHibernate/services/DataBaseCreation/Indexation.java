package com.example.SearchEngineHibernate.services.DataBaseCreation;


import com.example.SearchEngineHibernate.dto.app.*;
import com.example.SearchEngineHibernate.services.Repositories.FieldRepository;
import com.example.SearchEngineHibernate.services.Repositories.LemmaRepository;
import com.example.SearchEngineHibernate.services.Repositories.SiteRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Indexation {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private LemmaRepository lemmaRepository;

    @Autowired
    private LemmaServices lemmaServices;

    @Autowired
    private SiteRepository siteRepository;
    static final Logger logger = LoggerFactory.getLogger(SiteParsing.class);

    public void indexCreating() throws SQLException, IOException {

        List<Site> sites = new ArrayList<>();
        Iterable<Site> iterable = siteRepository.findAll();
        iterable.forEach(sites::add);

        List<Field> fields = new ArrayList<>();
        Iterable<Field> iterableField = fieldRepository.findAll();
        iterableField.forEach(fields::add);

        List<Lemma> lemmas = new ArrayList<>();
        Iterable<Lemma> iterableLemma = lemmaRepository.findAll();
        iterableLemma.forEach(lemmas::add);

        HashMap<String, Float> fieldWeight = (HashMap<String, Float>) fields.stream().collect(Collectors.toMap(Field::getSelector, Field::getWeight));

        for (Site site : sites) {


                List<Page> pages = site.getPages().stream().filter(p -> p.getCode() == 200).collect(Collectors.toList());

                for (Page page : pages) {

                    Document document = Jsoup.parse(page.getContent());
                    String title = document.title();
                    String body = page.getContent().replaceAll(title, "");

                    for (Lemma lemma : lemmas) {
                        String lemmaItem = lemma.getLemmaName();

                        int countLemmaInTitle = (int) lemmaServices.textModify(title).stream().filter(x -> x.equals(lemmaItem)).count();
                        int countLemmaInBody = (int) lemmaServices.textModify(body).stream().filter(x -> x.equals(lemmaItem)).count();

                        float rank = (float) (countLemmaInTitle * fieldWeight.get("title") + countLemmaInBody * fieldWeight.get("body"));
                        if (rank != 0) {
                            SearchIndex searchIndex = new SearchIndex();
                            searchIndex.setRating(rank);
                            searchIndex.setLemma(lemma);
                            searchIndex.setPage(page);
                        }
                    }

                    System.out.println("Индексация страницы закончена");
                    System.out.println("Индексация сайта продолжается...");

                }

                System.out.println("Индексация сайта окончена...");
            }

        }
    }





