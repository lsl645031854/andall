package com.andall.sally.supply.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @Auther: lsl
 * @Date: 2020/12/8 15:46
 * @Description:
 */
@SuppressWarnings("all")
public class HttpsUtil {
    private static final String CHARSET = "UTF-8";

    public static final String post(final String url, String json, final Map<String, String> headerMap) {
        CloseableHttpClient httpClient = createSSLClientDefault();
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000/* Integer.parseInt(PROPERTY_HELPER.getProperty("CONNECT_TIMEOUT")) */)
                .setConnectionRequestTimeout(Integer
                        .parseInt(/* PROPERTY_HELPER.getProperty("CONNECTION_REQUEST_TIMEOUT") */"5000"))
                .setSocketTimeout(Integer.parseInt(/* PROPERTY_HELPER.getProperty("SOCKET_TIMEOUT") */"5000"))
                .build();
        post.setConfig(requestConfig);

        CloseableHttpResponse response = null;

        // post.setHeader("Content-Type", "application/json");
        StringBuilder headersb = new StringBuilder("");
        if(null != headerMap && !headerMap.isEmpty()) {
            Iterator<Entry<String, String>> iterator = headerMap.entrySet().iterator();
            while(iterator.hasNext()) {
                Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                post.addHeader(key, value);
                headersb.append(key).append("=").append(value);
            }
        }


        StringBuilder sb = new StringBuilder("");

        if (null != json ) {
            StringEntity entity = new StringEntity(json, Charset.forName(CHARSET));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
        }

        String result = "";
        try {
            response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, CHARSET);
                }
            }

        } catch (IOException ex) {

        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {

                }
            }
        }

        return result;
    }



    private static CloseableHttpClient createSSLClientDefault() {

        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //忽略证书认证
                @Override
                public boolean isTrusted(X509Certificate[] xcs, String string){
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (KeyManagementException ex) {
            ex.printStackTrace();
        }
        return HttpClients.createDefault();
    }
}
