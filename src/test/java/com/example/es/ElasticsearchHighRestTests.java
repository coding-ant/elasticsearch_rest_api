package com.example.es;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.Build;
import org.elasticsearch.Version;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.rankeval.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

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
        IndexRequest request = new IndexRequest("user","admin");
        Map<String,Object> json = new HashMap<>();
        json.put("user","dd");
        json.put("age","99");
        json.put("sex","male");
        json.put("decription","dd is a good man");
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
    public void getbyindices(){
        GetIndexRequest request = new GetIndexRequest();
        BasicHeader basicheader = new BasicHeader("test","test");
        //GetIndexResponse response = client.indices().

    }

    @Test
    public void delete(){
        DeleteRequest request = new DeleteRequest("posts","doc","2");
        BasicHeader basicheader = new BasicHeader("test","test");
        try {
            DeleteResponse response = client.delete(request,basicheader);
            System.out.println(response.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 索引删除
     */
    @Test
    public void deleteindices(){
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        BasicHeader basicheader = new BasicHeader("test","test");
        try {
            DeleteIndexResponse response = client.indices().delete(request,basicheader);
            System.out.println(response.isAcknowledged());
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

    @Test
    public void mutiBulk(){
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("posts", "doc", "1")
                .source(XContentType.JSON,"field", "foo"));
        request.add(new IndexRequest("posts", "doc", "2")
                .source(XContentType.JSON,"field", "bar"));
        request.add(new IndexRequest("posts", "doc", "3")
                .source(XContentType.JSON,"field", "baz"));
        BasicHeader basicheader = new BasicHeader("test","test");
        try {
            request.timeout(TimeValue.timeValueMinutes(2));
            request.timeout("2m");
            request.timeout(TimeValue.timeValueMinutes(2));
            request.timeout("2m");
            request.waitForActiveShards(2);
            request.waitForActiveShards(ActiveShardCount.ALL);

            BulkResponse responses = client.bulk(request,basicheader);

            /**
             *
             */
           /* ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkResponse) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            };
            client.bulkAsync(request, listener);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mutiGet(){
        MultiGetRequest request = new MultiGetRequest();

        request.add(new MultiGetRequest.Item(
                "index",
                "type",
                "example_id"));
        request.add(new MultiGetRequest.Item("index", "type", "another_id"));

        //optional argument
        request.add(new MultiGetRequest.Item("index", "type", "example_id")
                .fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));
        String[] includes = new String[] {"foo", "*r"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);
        request.add(new MultiGetRequest.Item("index", "type", "example_id")
                .fetchSourceContext(fetchSourceContext));

    }



    @Test
    public void ping(){
        BasicHeader basicheader = new BasicHeader("test","test");
        try {
            boolean flag  = client.ping(basicheader);
            System.out.println(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void info(){
        BasicHeader basicheader = new BasicHeader("test","test");
        try {
            MainResponse response = client.info(basicheader);
            ClusterName clusterName = response.getClusterName();
            String clusterUuid = response.getClusterUuid();
            String nodeName = response.getNodeName();
            Version version = response.getVersion();
            Build build = response.getBuild();
            System.out.println(clusterName.toString());
            System.out.println(clusterUuid);
            System.out.println(nodeName);
            System.out.println(version);
            System.out.println(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void rank(){
        BasicHeader basicheader = new BasicHeader("test","test");

        try {
            EvaluationMetric metric = new PrecisionAtK();
            List<RatedDocument> ratedDocs = new ArrayList<>();
            ratedDocs.add(new RatedDocument("posts", "1", 1));
            SearchSourceBuilder searchQuery = new SearchSourceBuilder();
            searchQuery.query(QueryBuilders.matchQuery("user", "kimchy"));
            RatedRequest ratedRequest =
                    new RatedRequest("kimchy_query", ratedDocs, searchQuery);
            List<RatedRequest> ratedRequests = Arrays.asList(ratedRequest);
            RankEvalSpec specification =
                    new RankEvalSpec(ratedRequests, metric);
            RankEvalRequest request =
                    new RankEvalRequest(specification, new String[] { "posts" });
            RankEvalResponse response = client.rankEval(request,basicheader);
            System.out.println(response.getEvaluationResult());
            System.out.println(response.getPartialResults());
            Map<String, EvalQueryQuality> partialResults =
                    response.getPartialResults();
            EvalQueryQuality evalQuality =
                    partialResults.get("kimchy_query");
            System.out.println(evalQuality.getId());

            double qualityLevel = evalQuality.getQualityLevel();
            List<RatedSearchHit> hitsAndRatings = evalQuality.getHitsAndRatings();
            RatedSearchHit ratedSearchHit = hitsAndRatings.get(0);
            assertEquals("2", ratedSearchHit.getSearchHit().getId());
           // assertFalse(ratedSearchHit.getRating().isPresent());
            MetricDetail metricDetails = evalQuality.getMetricDetails();
            String metricName = metricDetails.getMetricName();
            assertEquals(PrecisionAtK.NAME, metricName);
            PrecisionAtK.Detail detail = (PrecisionAtK.Detail) metricDetails;
            assertEquals(1, detail.getRelevantRetrieved());
            assertEquals(3, detail.getRetrieved());
            System.out.println(qualityLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * -----------------------查询Query DSL------------------
     */
    @Test
    public void searchAll(){
        BasicHeader basicheader = new BasicHeader("test","test");
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery()).size(30);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse =  client.search(searchRequest,basicheader);
            System.out.println(searchResponse);
            SearchHits hits = searchResponse.getHits();
            for(SearchHit hit : hits.getHits()){
               // System.out.println(hit.getSourceAsMap()+"-------------as map");
                System.out.println(hit.getSourceAsString()+"------------as string");
               // System.out.println(hit.getSourceRef()+"-------------as ref");
            }
            System.out.println(hits.getTotalHits());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
