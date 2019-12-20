package ru.halmg.service;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ElasticService {

    private Client client;

    @Autowired
    public ElasticService(Client client) {
        this.client = client;
    }

    public String create(Map<String, Object> map, String index, String id) {
        IndexResponse response = client.prepareIndex(index, index, id).setSource(map).get();
        return response.getResult().toString();
    }

    public String delete(String index, String id) {
        DeleteResponse deleteResponse = client.prepareDelete(index, index, id).get();
        return deleteResponse.toString();
    }

    public List<Map<String, Object>> searchAll(String index) {
        SearchResponse searchResponse = client.prepareSearch(index)
                .addSort(SortBuilders.scoreSort())
                .setScroll(new TimeValue(60000))
                .setQuery(QueryBuilders.matchAllQuery())
                .get();
        List<Map<String, Object>> list = new ArrayList<>();
        do {
            Arrays.stream(searchResponse.getHits().getHits()).map(SearchHit::getSourceAsMap).forEach(list::add);
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(60000)).execute().actionGet();
        } while (searchResponse.getHits().getHits().length != 0);
        return list;
    }

    public Map<String, Object> findById(String index, String id) {
        GetResponse response = client.prepareGet(index, index, id).get();
        return response.getSourceAsMap();
    }

    public boolean indexExists(String index) {
        return client.admin().indices().prepareExists(index).get().isExists();
    }

    public void deleteAll(String index) {
        client.admin().indices().delete(new DeleteIndexRequest(index)).actionGet();
    }

    public String getHashCode(String index, String id) {
        GetResponse response = client.prepareGet(index, index, id).get();
        if (response.getSource()== null || response.getSource().get("hash") == null) {
            return null;
        }
        return response.getSource().get("hash").toString();
    }

    public void closeClient() {
        client.close();
    }
}
