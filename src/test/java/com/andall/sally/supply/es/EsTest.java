package com.andall.sally.supply.es;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Author: lsl
 * @Description:
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
                .source(XContentType.JSON,"name", "tom", "age", "21"));
        request.add(new IndexRequest("test").id("8")
                .source(XContentType.JSON,"name", "rose", "age", "22"));
        request.add(new IndexRequest("test").id("9")
                .source(XContentType.JSON,"name", "mary", "age", "23"));
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
}
