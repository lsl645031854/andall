package com.andall.sally.supply.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RestTemplateUtils {

    private static Logger log = LoggerFactory.getLogger(RestTemplateUtils.class);

    /**
     * 极光专用restTemplate
     */
    private static RestTemplate restTemplate;
    //幂等控制 rediskey 格式：JPUSH:regId:notification的hashcode
    private static final String JPUSH_IDEMPOTENT = "JPUSH:%s:%s";
    private static final int EXPIRE_TIME = 60;//s

    public static void setRestTemplate(RestTemplate restTemplate) {
        RestTemplateUtils.restTemplate = restTemplate;
    }

    public static <T> T get(String url,Class<T> t){
        return execute(url,null,()-> {
            ResponseEntity<T> forEntity = restTemplate.getForEntity(url, t);
            return forEntity.getBody();
        });
    }

    public static <T> T get(String url,Class<T> t,Map<String ,?> params){
        return execute(url,params,()-> {
            ResponseEntity<T> forEntity = restTemplate.getForEntity(url, t, params);
            return forEntity.getBody();
        });
    }

    /**
     * get请求带header
     * @param url url
     * @param t 响应类型
     * @param headersMap headersMap
     * @return 响应
     */
    public static <T> T getWithHeader(String url, Class<T> t, Map<String ,String> headersMap) {
        return execute(url,headersMap,()-> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            setHeads(headers, headersMap);

            HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(null, headers);
            ResponseEntity<T> forEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, t);
            return forEntity.getBody();
        });
    }

    /**
     * post请求带header
     */
    public static <T> T postWithHeader(String url, Class<T> t, LinkedHashMap<String, Object> contentMap, Map<String ,String> headersMap) {
        return execute(url,contentMap,()-> {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            setHeads(httpHeaders, headersMap);

            HttpEntity<Object> objectHttpEntity = new HttpEntity<>(contentMap, httpHeaders);
            ResponseEntity<T> forEntity = restTemplate.postForEntity(url, objectHttpEntity, t);

            return forEntity.getBody();
        });
    }

    private static void setHeads(HttpHeaders headers, Map<String, String> headersMap) {
        headersMap.forEach(headers::add);
    }

    public static <T> T post(String url, Class<T> t, JSONObject param){
        return execute(url,param,()->{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> objectHttpEntity = new HttpEntity<>(param,httpHeaders);
            ResponseEntity<T> forEntity = restTemplate.postForEntity(url, objectHttpEntity, t);
            return forEntity.getBody();
        });
    }

    /**
     * 执行器
     * @param supplier 执行器
     * @param <T>
     * @return
     */
    private static <T> T execute(String url,Object param,Supplier<T> supplier){
        try{
            T t = supplier.get();
            log.info("极光接口==>{},请求参数==>{},响应结果==>{}",url, JSON.toJSONString(param), JSON.toJSONString(t));
            return t;
        }catch (Exception e){
            log.error("调用极光接口异常==>"+url+",参数==>"+ JSON.toJSONString(param),e);
            return null;
        }
    }

    public static void main(String[] args) {
        String url = "http://www.baidu.com";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String url2 = builder.queryParam("name", "zgd").build().encode().toString();
        System.out.println("url2 = " + url2);//http://www.baidu.com?name=zgd

        Map<String,Object> param = Maps.newHashMap();
        param.put("name","zzz");
        param.put("age",123);
        param.forEach(builder::queryParam);
        String url3 = builder.build().encode().toString(); //http://www.baidu.com?name=zgd&name=zzz&age=123
        System.out.println("url3 = " + url3);
    }

}
