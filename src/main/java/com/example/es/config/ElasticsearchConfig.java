package com.example.es.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.search.SimpleQueryStringQueryParser;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Mario on 2018/7/3.
 */
@Configuration
public class ElasticsearchConfig {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Value("${elasticsearch.ip}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private String port;

    @Value("${elasticsearch.cluster.name}")
    private String clustername;

    @Value("${elasticsearch.pool}")
    private String poolSize;

    public static Logger getLogger() {
        return logger;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public String getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(String poolSize) {
        this.poolSize = poolSize;
    }

    @Bean
    public TransportClient init(){
        logger.info("初始化开始");
        TransportClient transportClient  = null;
        Settings settings = Settings.builder()
                .put("cluster.name", clustername).build();
        //配置信息Settings自定义,下面设置为EMPTY
        transportClient = new PreBuiltTransportClient(settings);
        TransportAddress transportAddress = null;
        try {
            transportAddress = new TransportAddress(InetAddress.getByName(hostname), Integer.valueOf(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        transportClient.addTransportAddresses(transportAddress);
        return transportClient;
    }
}
