package com.example.elasticsearch.demo;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.HashMap;
import java.util.Map;

public class QueryDocByCond {


  public static void main(String[] args) {
    ConnectElasticsearch.connect(SEARCH_BY_CONDITION);
  }


  public static final ElasticsearchTask SEARCH_BY_CONDITION = client -> {
    // 创建搜索请求对象
    SearchRequest request = new SearchRequest();
    request.indices("qa_9b80a8a3a78244c7852d52b927b761ac");
    // 构建查询的请求体
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    Map<String, Float> map = new HashMap<>();
    map.put("properties.question", 0.7f);
    map.put("title", 0.7f);
    map.put("TALabel", 1.0f);
    map.put("keyword", 1.0f);
    map.put("properties.similarquestion", 0.5f);

    Map<String, Float> map2 = new HashMap<>();
    map2.put("properties.slotquestion", 1.0f);

    queryBuilder.should(QueryBuilders.multiMatchQuery("泰泰和香蕉一起吃有什么忌讳或副作 泰毕全 泰泰 香蕉 巴拉啦 ",
       "properties.question", "title"
       , "TALabel", "keyword", "properties.similarquestion").type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fields(map));
    queryBuilder.should(QueryBuilders.multiMatchQuery("药品和食物一起吃有什么忌讳或副作",
       "properties.slotquestion").type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fields(map2));

    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    boolQueryBuilder.filter(QueryBuilders.matchQuery("type", "9b80a8a3a78244c7852d52b927b761ac_7_qaInfo")).must(queryBuilder);
//    queryBuilder.must(QueryBuilders.termQuery("age", "30"));
//    sourceBuilder.query(queryBuilder.must(QueryBuilders.termQuery("age", "30")));
    sourceBuilder.query(boolQueryBuilder);
    request.source(sourceBuilder);
    SearchResponse response = client.search(request, RequestOptions.DEFAULT);
    // 查询匹配
    SearchHits hits = response.getHits();
    System.out.println("took:" + response.getTook());
    System.out.println("timeout:" + response.isTimedOut());
    System.out.println("total:" + hits.getTotalHits());
    System.out.println("MaxScore:" + hits.getMaxScore());
    System.out.println("hits========>>");
    for (SearchHit hit : hits) {
      //输出每条查询的结果信息
      System.out.println(hit.getSourceAsString());
    }
    System.out.println("<<========");
  };


}
