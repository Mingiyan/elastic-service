package ru.halmg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.halmg.service.ElasticService;

import java.util.List;
import java.util.Map;

@RestController
public class RestFullController {

    private ElasticService elasticService;

    @Autowired
    public RestFullController(ElasticService elasticService) {
        this.elasticService = elasticService;
    }

    @ResponseBody
    @GetMapping(value = "/api/service/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Map<String, Object>> searchQuery(@RequestParam("index") String index,
                                   @RequestParam("type") String type,
                                   @RequestParam("query") String query) {
        return elasticService.search(index, type, query);
    }

    @ResponseBody
    @GetMapping(value = "/api/service/id", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> searchById(@RequestParam("index") String index,
                                          @RequestParam("id") String id) {
        return elasticService.searchById(index, id);
    }
}
