package com.example.SearchEngineHibernate;

import com.example.SearchEngineHibernate.dto.app.App;
import com.example.SearchEngineHibernate.dto.statistics.StatisticsData;
import com.example.SearchEngineHibernate.services.DataBaseCreation.FieldTableCreating;
import com.example.SearchEngineHibernate.services.DataBaseCreation.SiteTableCreating;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication()
public class SearchEngineHibernateApplication {

	public static void main(String[] args) throws SQLException, IOException {
		SpringApplication.run(SearchEngineHibernateApplication.class, args);
	}

}
