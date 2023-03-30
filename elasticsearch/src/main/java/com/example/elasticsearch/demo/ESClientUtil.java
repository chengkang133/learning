package com.example.elasticsearch.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ESClientUtil {

  public static RestHighLevelClient getClient() {
    // 创建客户端对象
    return new RestHighLevelClient(
       RestClient.builder(new HttpHost("192.168.253.130", 9200, "http")));
  }

}
