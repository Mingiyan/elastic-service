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
        int listSize = (int) client.prepareSearch(index).get().getHits().getTotalHits();
        SearchResponse response = client.prepareSearch(index).setSize(listSize).get();
        List<Map<String, Object>> list = new ArrayList<>();
        Iterator<SearchHit> searchHitIterator = response.getHits().iterator();
        for (Iterator<SearchHit> iterator = searchHitIterator; iterator.hasNext(); ) {
            SearchHit searchHit = iterator.next();
            list.add(findById(index, searchHit.getId()));
        }
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
