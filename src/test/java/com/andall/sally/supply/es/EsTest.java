package com.andall.sally.supply.es;

import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lsl
 * @Description:
 *         keyword类型：不分词的文本数据类型
 *         text类型：支持分词的文本数据类型
 *
 *         matchPhraseQuery：不会对关键词进行分词搜索
 *         matchQuery：会进行分词搜索
 *         termQuery：完全匹配 term做精确查询可以用它来处理数字，布尔值，日期以及文本。查询数字时问题不大，但是当查询字符串时会有问题
 * @Date: Created on 5:29 下午 2020/3/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void existsIndex() throws IOException {
        // 判断索引是否存在.
        GetIndexRequest request = new GetIndexRequest("test");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    public void createIndex() throws IOException {
        //
        CreateIndexRequest request = new CreateIndexRequest("test");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        // "name", "rose", "age", 22, "createTime", "2019-08-10 07:55:55", "sex", "woman"
        Map<String, Object> name = new HashMap<>();
        name.put("type", "keyword");
        Map<String, Object> age = new HashMap<>();
        age.put("type", "integer");
        Map<String, Object> createTime = new HashMap<>();
        createTime.put("type", "date");
        Map<String, Object> sex = new HashMap<>();
        sex.put("type", "keyword");

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", name);
        properties.put("age", age);
        properties.put("createTime", createTime);
        properties.put("sex", sex);

        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(mapping);

        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void deleteIndex() throws Exception {
        DeleteIndexRequest request = new DeleteIndexRequest("test");
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void insertDocument() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.field("name", "李帅领");
        builder.field("mobile", "18053606726");
        builder.field("msg", "day day up");
        builder.endObject();
        IndexRequest request = new IndexRequest("test").source(builder);
        request.id("4");
        IndexResponse index = client.index(request, RequestOptions.DEFAULT);
        System.out.println(index.status());
    }

    @Test
    public void getDocument() throws Exception {
        GetRequest getRequest = new GetRequest(
                "test",
                "4");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        System.out.println(sourceAsMap);
    }

    @Test
    public void existDoc() throws Exception {
        GetRequest getRequest = new GetRequest(
                "test",
                "4");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    public void updateDoc() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.timeField("updated", new Date());
            builder.field("reason", "daily update");
            builder.field("msg", "good good study");
        }
        builder.endObject();
        UpdateRequest request = new UpdateRequest(
                "test",
                "4").doc(builder);
        UpdateResponse updateResponse = client.update(
                request, RequestOptions.DEFAULT);
        if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("更新成功");
        }
    }

    @Test
    public void deleteDoc() throws Exception {
        DeleteRequest request = new DeleteRequest(
                "test",
                "4");
        DeleteResponse deleteResponse = client.delete(
                request, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            System.out.println("删除成功");
        }
    }

    @Test
    public void bulkDoc() throws Exception {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("test").id("7")
                .source(XContentType.JSON,"name", "tom", "age", 21, "createTime", new Date(), "sex", "man"));
        request.add(new IndexRequest("test").id("8")
                .source(XContentType.JSON,"name", "rose", "age", 22, "createTime", DateUtils.addDays(new Date(), 3), "sex", "woman"));
        request.add(new IndexRequest("test").id("9")
                .source(XContentType.JSON,"name", "mary", "age", 23, "createTime", DateUtils.addDays(new Date(), 10), "sex", "woman"));
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
    }

    @Test
    public void multiGet() throws Exception {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item(
                "test",
                "7"));
        request.add(new MultiGetRequest.Item("test", "8"));
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        MultiGetItemResponse[] responses = response.getResponses();
        Arrays.stream(responses).forEach(resp -> {
            GetResponse response1 = resp.getResponse();
            Map<String, Object> sourceAsMap = response1.getSourceAsMap();
            System.out.println(sourceAsMap);
        });
    }

    @Test
    public void count() throws Exception {
        CountRequest countRequest = new CountRequest("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        countRequest.source(searchSourceBuilder);
        CountResponse countResponse = client
                .count(countRequest, RequestOptions.DEFAULT);
        long count = countResponse.getCount();
        System.out.println(count);
    }

    @Test
    public void test() throws IOException {
        SearchRequest searchRequest = new SearchRequest("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }

    }

    @Test
    public void testCollapse() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("test");

        sourceBuilder.query(QueryBuilders.matchAllQuery());

        CollapseBuilder collapseBuilder = new CollapseBuilder("sex");
        sourceBuilder.collapse(collapseBuilder);

        sourceBuilder.fetchSource("sex", "age");

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }

    }

    @Test
    public void testCardinality() throws IOException {
        // 基数统计
        SearchRequest searchRequest = new SearchRequest("test");
        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("sexAgg").field("sex");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(cardinalityAggregationBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Cardinality cardinality = searchResponse.getAggregations().get("sexAgg");
        System.out.println(cardinality.getValue());

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }

    }

    @Test
    public void  testIn() throws IOException {
        SearchRequest searchRequest = new SearchRequest("test");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//        boolQueryBuilder.must(QueryBuilders.termsQuery("sex", Collections.singletonList("man")));
        boolQueryBuilder.mustNot(QueryBuilders.termsQuery("sex.keyword", Arrays.asList("11", "22")));
        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void testRange() throws IOException {
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("createTime")
                .from("2020-07-15 00:00:01").to("2020-07-16 00:00:01");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(rangequerybuilder);

        SearchRequest searchRequest = new SearchRequest("test");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void testTermsAggregation() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregationBuild = AggregationBuilders.terms("by_sex").field("sex");
        aggregationBuild.subAggregation(AggregationBuilders.avg("avg_age")
                .field("age"));

        sourceBuilder.aggregation(aggregationBuild);

        SearchRequest searchRequest = new SearchRequest("test");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();

        Terms byCompanyAggregation = aggregations.get("by_sex");
        Terms.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("man");
        Avg averageAge = elasticBucket.getAggregations().get("avg_age");
        double avg = averageAge.getValue();
        System.out.println("woman平均年龄："+avg);
    }
}
