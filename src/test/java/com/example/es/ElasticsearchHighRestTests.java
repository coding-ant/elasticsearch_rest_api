package com.example.es;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mario on 2018/7/3.
 */
public class ElasticsearchHighRestTests {

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")));
                    //new HttpHost("localhost", 9201, "http")));

    @Test
    public void  index(){
        IndexRequest request = new IndexRequest("posts","doc","1");
        Map<String,Object> json = new HashMap<String,Object>();
        json.put("user","Mario");
        json.put("age","24");
        json.put("sex","male");
        request.source(json);
        try {
            BasicHeader basicheader = new BasicHeader("test","test");
            client.index(request, basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据 index type id 获取数据
     */
    @Test
    public void get(){
        GetRequest request = new GetRequest("posts","doc","1");
        BasicHeader basicheader = new BasicHeader("test","test");
        try {
            GetResponse response = client.get(request,basicheader);
            System.out.println(response.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete(){
        DeleteRequest request = new DeleteRequest("posts","doc","1");
        BasicHeader basicheader = new BasicHeader("test","test");
        try {
            DeleteResponse response = client.delete(request,basicheader);
            System.out.println(response.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isExists(){
        GetRequest getRequest = new GetRequest(
                "posts",
                "doc",
                "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        try {
           boolean isexists =  client.exists(getRequest);
            System.out.println(isexists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update(){
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "Steven");
        jsonMap.put("sex", "female");
        jsonMap.put("time",new Date());
        UpdateRequest request = new UpdateRequest("posts", "doc", "1")
                .doc(jsonMap);
        BasicHeader basicheader = new BasicHeader("test","test");
        try {
            client.update(request,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
