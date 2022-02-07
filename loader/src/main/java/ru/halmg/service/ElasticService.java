package ru.halmg.service;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ElasticService {

    private RestHighLevelClient client;

    @Autowired
    public ElasticService(RestHighLevelClient client) {
        this.client = client;
    }

    public String create(Map<String, Object> map, String index, String id) {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(id);
        indexRequest.source(map);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexResponse.toString();
    }

    public String delete(String index, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleteResponse.toString();
    }

    public List<Map<String, Object>> searchAll(String index) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> list = new ArrayList<>();
        Arrays.stream(searchResponse.getHits().getHits()).map(SearchHit::getSourceAsMap).forEach(list::add);
        return list;
    }

    public boolean indexExists(String index) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        try {
            return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteAll(String index) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        try {
            client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHashCode(String index, String id) {
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse response = null;
        try {
            response = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getSource()== null || response.getSource().get("hash") == null) {
            return null;
        }
        return response.getSource().get("hash").toString();
    }

    public void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
