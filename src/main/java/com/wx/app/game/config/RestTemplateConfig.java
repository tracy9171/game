package com.wx.app.game.config;


import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {

    @Value("${searchMaxTotal:10}")
    private int searchMaxTotal;

    @Value("${searchDefaultMaxPerRoute:10}")
    private int searchDefaultMaxPerRoute;

    @Value("${searchValidateAfterInactivity:5000}")
    private int searchValidateAfterInactivity;

    @Value("${searchSocketTimeout:5000}")
    private int searchSocketTimeout;

    @Value("${searchMaxTotal:5000}")
    private int searchConnectTimeout;

    @Value("${searchConnectionRequestTimeout:5000}")
    private int searchConnectionRequestTimeout;

    @Value("${searchEvictIdleConnections:10}")
    private int searchEvictIdleConnections;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(httpRequestFactory());
    }

    public ClientHttpRequestFactory httpRequestFactory(){
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    public HttpClient httpClient(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        //连接池最大的连接数
        connectionManager.setMaxTotal(searchMaxTotal);
        //默认的每个路由上最大的连接数（不能超过连接池总连接数）
        connectionManager.setDefaultMaxPerRoute(searchDefaultMaxPerRoute);
        //空闲永久连接检查间隔
        connectionManager.setValidateAfterInactivity(searchValidateAfterInactivity);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(searchSocketTimeout)//设定获取数据的超时时间，
                .setConnectTimeout(searchConnectTimeout)//设置连接服务器超时时间
                .setConnectionRequestTimeout(searchConnectionRequestTimeout)//设定连接服务器超时时间
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .evictIdleConnections(searchEvictIdleConnections, TimeUnit.SECONDS)
                .build();
    }
}
