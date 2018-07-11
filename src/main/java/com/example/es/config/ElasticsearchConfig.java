package com.example.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Mario on 2018/7/3.
 */
@Configuration
public class ElasticsearchConfig {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Bean
    public SearchRequest getSearchRequest(){
        return new SearchRequest();
    }

    @Bean
    public SearchResponse getSearchResponse(){
        return new SearchResponse();
    }
    @Bean
    public SearchSourceBuilder getSearchSourceBuilder(){
        return new SearchSourceBuilder();
    }
    @Bean
    public RestHighLevelClient getClient(){
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        //new HttpHost("localhost", 9201, "http")));
    }
}
