package com.brave.chardb.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@PropertySource("classpath:application.properties")
public class MongoConfiguration extends AbstractMongoConfiguration
{
    @Value("${mongodb.uri}")
    String uriString;

    @Value("${mongodb.databasename}")
    String mongodb_databasename;

    @Override
    protected String getDatabaseName()
    {
        return mongodb_databasename;
    }

    @Override
    public Mongo mongo() throws Exception
    {
        MongoClientURI uri = new MongoClientURI(uriString);
        return new MongoClient(uri);
    }
}
