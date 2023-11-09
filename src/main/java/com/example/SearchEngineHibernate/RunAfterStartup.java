package com.example.SearchEngineHibernate;

import com.example.SearchEngineHibernate.dto.app.App;
import com.example.SearchEngineHibernate.dto.app.Field;
import com.example.SearchEngineHibernate.services.DataBaseCreation.*;
import com.example.SearchEngineHibernate.services.Repositories.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class RunAfterStartup {

    @Autowired
    private FieldTableCreating fieldTableCreating;

    @Autowired
    private SiteTableCreating siteTableCreating;

    @Autowired
    private App app;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws IOException, SQLException {
        System.out.println(app.getSites().keySet());
        System.out.println("Hello");
        //siteTableCreating.siteTableCreating();
        //fieldTableCreating.fieldTableCreating();
    }
}


