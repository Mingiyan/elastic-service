package ru.halmg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.halmg.service.ElasticService;

@RestController
public class RestFullController {

    private ElasticService elasticService;

    @Autowired
    public RestFullController(ElasticService elasticService) {
        this.elasticService = elasticService;
    }

    @ResponseBody
    @GetMapping("/")
    public String welcome() {
        return "Welcome to Elastic Service.<br> " +
                "Add /api/service to the link and the parameters 'index', 'type', 'query' for search<br>" +
                "Example: <strong>/api/service?index=employee&type=name&query=${username}</strong><br>" +
                "queryType options: id, code, name, department, position, grade, vacancyId, city.";
    }

    @ResponseBody
    @GetMapping(value = "/api/service", produces = {MediaType.APPLICATION_JSON_VALUE})
    public <T> T searchQuery(@RequestParam("index") String index,
                             @RequestParam("type") String type,
                             @RequestParam("query") String query) {
        if ("id".equals(type)) {
            return (T) elasticService.searchById(index, query);
        } else {
            return (T) elasticService.search(index, type, query);
        }
    }
}
