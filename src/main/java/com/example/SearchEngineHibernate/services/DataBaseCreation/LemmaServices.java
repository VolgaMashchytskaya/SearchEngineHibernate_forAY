package com.example.SearchEngineHibernate.services.DataBaseCreation;

import com.example.SearchEngineHibernate.dto.app.Page;
import com.example.SearchEngineHibernate.dto.app.Site;
import com.example.SearchEngineHibernate.services.Repositories.PageRepository;
import com.example.SearchEngineHibernate.services.Repositories.SiteRepository;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class LemmaServices {
//
//    @Autowired
//    private SiteRepository siteRepository;
//    @Autowired
//    private PageRepository pageRepository;
//    public static HashMap<String, int []> lemmasMap = new HashMap<>();
//
//    StringBuilder insertLemmaQuery = new StringBuilder();
//
//    LuceneMorphology luceneMorphology = new RussianLuceneMorphology();
//
//    static final Logger logger =  LoggerFactory.getLogger(SiteParsing.class);
//
//    public LemmaServices() throws IOException {
//    }
//
//    public void lemmaCreating() {
//        List<Site> sites = new ArrayList<>();
//        Iterable<Site> siteIterable = siteRepository.findAll();
//        siteIterable.forEach(sites::add);
//
//        try {
//                for (Site site: sites) {
//
//                    List<Page> pages = site.getPages().stream().filter(p -> p.getCode()==200).collect(Collectors.toList());
//
//
//                     for (Page page : pages) {
//                        String content = page.getContent();
//
//                        List<String> textModify = textModify(content);
//
//                        for (String lemma : textModify) {
//                            int [] lemmaMapItem = new int[2];
//                            if (!lemmasMap.containsKey(lemma)) {
//                                lemmaMapItem[0]=site.getId();
//                                lemmaMapItem[1]=1;
//                                lemmasMap.put(lemma, lemmaMapItem);
//                            } else {
//                                lemmaMapItem[1]=lemmaMapItem[1]+1;
//                                lemmasMap.put(lemma, lemmaMapItem);
//                            }
//                        }
//                        }
//                    }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getMessage();
//            logger.error("Ошибка чтения лемм");
//        }
//    }
//    public List<String> textModify(String textForModyfy) {
//        String textModify = Jsoup.parse(textForModyfy).toString().toLowerCase().replaceAll("[\\s<>]", " ").
//                replaceAll("[^а-я\\s+\\,\\.]", "").
//                replaceAll("\\+","").replaceFirst("^[^а-я]+", "").
//                replaceAll("[<>]", ",").replaceAll("\\s+", ",").
//                replaceAll("\\.+", ",").replaceAll(",+", ",");
//        List<String> textModifyArray = List.of(textModify.split(",")).stream().filter(x -> !isParticles(x))
//        .map(x -> luceneMorphology.getNormalForms(x).get(0)).collect(Collectors.toList());
//
//        return textModifyArray;
//
//   }
//
//    public boolean isParticles(String word) {
//        boolean isParticle = false;
//        List<String> wordBaseForms = luceneMorphology.getMorphInfo(word);
//        if (wordBaseForms.toString().contains("МЕЖД") | wordBaseForms.toString().contains("ПРЕДЛ") | wordBaseForms.toString().contains("СОЮЗ")) {
//            isParticle = true;
//        }
//        return isParticle;
//    }

}
