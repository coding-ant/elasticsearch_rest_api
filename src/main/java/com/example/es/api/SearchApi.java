package com.example.es.api;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.builders.CircleBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Mario on 2018/7/9.
 */
@RestController
@RequestMapping("/search")
public class SearchApi {

    @Autowired
    private SearchRequest searchRequest;
    @Autowired
    private SearchResponse searchResponse;
    @Autowired
    private SearchSourceBuilder searchSourceBuilder;
    @Autowired
    private RestHighLevelClient client;

    BasicHeader basicheader = new BasicHeader("test","test");

    /**
     * 查询全部
     * @return
     */
    @RequestMapping("/all")
    public String searchAll(){
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse =  client.search(searchRequest,basicheader);
            SearchHits hits = searchResponse.getHits();
            return JSONArray.toJSONString(hits);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据某个属性的值进行查询
     * @param name
     * @param text
     * @return
     */
    @RequestMapping("/query")
    public String search(String name,String text){
        searchSourceBuilder.query(QueryBuilders.matchQuery(name,text));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 根据 属性 和属性值短语查询
     * @param name
     * @param text
     * @return
     */
    @RequestMapping("/phrase")
    public String searchPhrase(String name,String text){
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery(name,text));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    @RequestMapping("/phrasePrefix")
    public String phrasePrefix(String name,String text){
        searchSourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery(name,text));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 多字段查询  多个字段匹配同一个值的选项
     * @param text
     * @param names
     * @return
     */
    @RequestMapping("/multi")
    public String multi(String text,String names[]){
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(text,names));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 屏蔽频繁出现的公共词语方面查询。。提高查询准确率
     * @param name
     * @param text
     * @return
     */
    @RequestMapping("/commonTerms")
    public String commonTerms(String name,String text){
        searchSourceBuilder.query(QueryBuilders.commonTermsQuery(name,text).cutoffFrequency((float) 0.001));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 只输入text  后台指定查询某些字段是否含有这个值
     * @param queryString
     * @return
     */
    @RequestMapping("/queryString")
    public String queryString(String queryString){
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(queryString).field("user").field("description"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 待探索。。。。和queryString的区别
     * @param queryString
     * @return
     */
    @RequestMapping("/simpleQueryString")
    public String simpleQueryString(String queryString){
        searchSourceBuilder.query(QueryBuilders.simpleQueryStringQuery(queryString).field("user").field("description"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }


    /**
     * --------------------------term level queries----------------
     */

    /**
     * term
     * @param name
     * @param text
     * @return
     */
    @RequestMapping("/term")
    public String term(String name,String text){
        searchSourceBuilder.query(QueryBuilders.termQuery(name,text));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 匹配多个值
     * @param name
     * @param texts
     * @return
     */
    @RequestMapping("/terms")
    public String terms(String name,String[] texts){
        searchSourceBuilder.query(QueryBuilders.termsQuery(name,texts));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 条件查询。。某个属性在某个范围呢
     * gt 大于 gte 大于等于
     * lt 小于 lte 小于等于
     * boost
     * @param name
     * @return
     */
    @RequestMapping("/range")
    public String range(String name){
        searchSourceBuilder.query(QueryBuilders.rangeQuery(name).gt(20));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 某个字段不为null的所有数据
     * @param name
     * @return
     */
    @RequestMapping("/exists")
    public String exists(String name){
        searchSourceBuilder.query(QueryBuilders.existsQuery(name));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 某个字段前缀为xx的数据
     * @param name
     * @param prefix
     * @return
     */
    @RequestMapping("/prefix")
    public String prefix(String name,String prefix){
        searchSourceBuilder.query(QueryBuilders.prefixQuery(name,prefix));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 通配符查询
     * @param name
     * @param query ki*y
     * @return
     */
    @RequestMapping("/wildcard")
    public String wildcard(String name,String query){
        searchSourceBuilder.query(QueryBuilders.wildcardQuery(name,query));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 正则表达式查询
     * @param name
     * @param query
     * @return
     */
    @RequestMapping("/regexp")
    public String regexp(String name,String query){
        searchSourceBuilder.query(QueryBuilders.regexpQuery(name,query));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 模糊查询
     * @param name
     * @param text
     * @return
     */
    @RequestMapping("/fuzzy")
    public String fuzzy(String name,String text){
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery(name,text));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 查询type下所有数据
     * @param type
     * @return
     */
    @RequestMapping("/type")
    public String type(String type){
        searchSourceBuilder.query(QueryBuilders.typeQuery(type));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * 根据ids查询数据
     */
    @RequestMapping
    public String ids(String[] ids){
        searchSourceBuilder.query(QueryBuilders.idsQuery(ids));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * -------------------compound queries------------
     * 固定score查询
     */
    @RequestMapping("/constantScore")
    public String constantScore(String name,String text){
        searchSourceBuilder.query(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery(name,text)));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * bool 结合查询
     * must and
     * must not
     * should or
     *
     *
     * @return
     */
    @RequestMapping("/bool")
    public String bool(String name,String text){
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(name,text)));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest,basicheader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        return JSONArray.toJSONString(hits);
    }

    /**
     * ----------------------join queries------------------
     */

    /**
     * nested查询
     * @return
     */
    @RequestMapping("/nested")
    public String nested(){
       // searchSourceBuilder.query(QueryBuilders.nestedQuery());
        return null;
    }

    /**
     * 子查询
     * @return
     */
    @RequestMapping("/child")
    public String child(){
        return null;
    }


    /**
     * -------------------geo queries-----------
     */

    /**
     * 地理形状查询
     * @param name
     * @return
     *
     * radius方圆1km
     *
     *  coordinate 当前中心坐标点
     */
    @RequestMapping("/geoShape")
    public String geoShape(String name){
        try {
            searchResponse = client.search(searchRequest,basicheader);
            searchSourceBuilder.query(QueryBuilders.geoShapeQuery(name,new CircleBuilder().radius("1km").coordinate(4.89994,52.37815)));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = null;
            SearchHits hits = searchResponse.getHits();
            return JSONArray.toJSONString(hits);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * span term queries 跨度查询
     */

}
