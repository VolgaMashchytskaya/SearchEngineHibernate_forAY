package com.example.SearchEngineHibernate.dto.app;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "page")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;

    @Column
    private String path;
    @Column
    private int code;
    @Column
    private String content;

    @OneToMany(mappedBy = "page",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SearchIndex> searchIndexes;
}




