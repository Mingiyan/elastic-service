package ru.halmg.service;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ElasticService {

    private Client client;

    @Autowired
    public ElasticService(Client client) {
        this.client = client;
    }

    public List<Map<String, Object>> search(String index, String queryType, String query) {
        SearchResponse searchResponse = client.prepareSearch(index)
                .addSort(SortBuilders.scoreSort())
                .setScroll(new TimeValue(60000))
                .setQuery(QueryBuilders.matchQuery(queryType, query))
                .get();
        List<Map<String, Object>> list = new ArrayList<>();
        do {
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                list.add(hit.getSourceAsMap());
            }
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(60000)).execute().actionGet();
        } while (searchResponse.getHits().getHits().length != 0);
        return list;
    }

    public Map<String, Object> searchById(String index, String id) {
        GetResponse response = client.prepareGet(index, index, id).get();
        return response.getSourceAsMap();
    }

    public void closeClient() {
        client.close();
    }
}
