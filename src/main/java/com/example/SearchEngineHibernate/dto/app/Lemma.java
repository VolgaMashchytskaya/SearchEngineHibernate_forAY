package com.example.SearchEngineHibernate.dto.app;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "lemma")
public class Lemma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;

    @Column
    private String lemmaName;

    @Column
    private int frequency;

    @OneToMany(mappedBy = "lemma",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SearchIndex> searchIndices;
}



