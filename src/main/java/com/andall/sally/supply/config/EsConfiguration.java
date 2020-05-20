package com.andall.sally.supply.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 4:38 下午 2020/3/5
 */
@Configuration
public class EsConfiguration {

    private static String esHost = "127.0.0.1";  // 集群地址，多个用,隔开
    private static int esPort = 9200;  // 使用的端口号
    private static int connectTimeOut = 5000; // 连接超时时间
    private static int socketTimeOut = 30000; // 连接超时时间
    private static int connectionRequestTimeOut = 5000; // 获取连接的超时时间

    private static ArrayList<HttpHost> hostList = null;
    private static String schema = "http"; // 使用的协议

    private static int maxConnectNum = 100; // 最大连接数
    private static int maxConnectPerRoute = 100; // 最大路由连接数

    static {
        hostList = new ArrayList<>();
        String[] hostStrs = esHost.split(",");
        for (String host : hostStrs) {
            hostList.add(new HttpHost(host, esPort, schema));
        }
    }

    @Bean(name = "client")
    public RestHighLevelClient highLevelClient() {
        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
        return new RestHighLevelClient(builder
                //  异步httpclient连接延时配置
                .setRequestConfigCallback(requestConfigBuilder -> {
                    requestConfigBuilder.setConnectTimeout(connectTimeOut);
                    requestConfigBuilder.setSocketTimeout(socketTimeOut);
                    requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                    return requestConfigBuilder;
                })
                // 异步httpclient连接数配置
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.setMaxConnTotal(maxConnectNum);
                    httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                    return httpClientBuilder;
                })
        );
    }

}
