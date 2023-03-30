package com.example.elasticsearch.demo;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class DeleteIndex {
  public static void main(String[] args) throws IOException {
    RestHighLevelClient client = ESClientUtil.getClient();
    // 删除索引 - 请求对象
    DeleteIndexRequest request = new DeleteIndexRequest("user2");
    // 发送请求，获取响应
    AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
    // 操作结果
    System.out.println("操作结果 ： " + response.isAcknowledged());
    client.close();
  }
}
