package com.example.es.util;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mario on 2018/7/3.
 */
@Component
public class ElasticsearchUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtils.class);

    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }


    /**
     * 创建索引
     * @param index
     * @return
     */
    public static boolean createIndex(String index) {
        if (!isIndexExist(index)) {
            LOGGER.info("Index is not exits!");
        }
        CreateIndexResponse indexresponse = client.admin().indices().prepareCreate(index).execute().actionGet();
        LOGGER.info("执行建立成功？" + indexresponse.isAcknowledged());

        return indexresponse.isAcknowledged();
    }

    /**
     * 检查索引是否存在
     * @param index
     * @return
     */
    private static boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()) {
            LOGGER.info("Index [" + index + "] is exist!");
        } else {
            LOGGER.info("Index [" + index + "] is not exist!");
        }
        return inExistsResponse.isExists();
    }


    public static int addSource(String index,String type){
        Map<String,Object> json = new HashMap<String,Object>();
        json.put("user","Mario");
        json.put("age","24");
        json.put("sex","male");
        IndexResponse response = client.prepareIndex(index,type).setSource(json).get();
        RestStatus status = response.status();
        return status.getStatus();

    }

    /**
     * 获取某个document
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static String getDoc(String index,String type,String id){
        GetResponse response = client.prepareGet(index,type,id).get();
        Map<String,Object> result = response.getSource();
        return JSONObject.toJSONString(result);
    }
}
