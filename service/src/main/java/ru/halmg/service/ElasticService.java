package ru.halmg.service;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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

    public List<Map<String, Object>> search(String index, String queryType, String query) {
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

    public Map<String, Object> searchById(String index, String id) {
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse response = null;
        try {
            response = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.getSourceAsMap();
    }

    public void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
