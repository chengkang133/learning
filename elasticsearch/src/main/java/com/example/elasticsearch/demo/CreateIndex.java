package com.example.elasticsearch.demo;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class CreateIndex {

  public static void main(String[] args) throws IOException {
    RestHighLevelClient client = ESClientUtil.getClient();

    // 创建索引 - 请求对象
    CreateIndexRequest request = new CreateIndexRequest("user2");
    // 发送请求，获取响应
    CreateIndexResponse response = client.indices().create(request,
       RequestOptions.DEFAULT);
    boolean acknowledged = response.isAcknowledged();
    // 响应状态
    System.out.println("操作状态 = " + acknowledged);

    // 关闭客户端连接
    client.close();
  }

}
