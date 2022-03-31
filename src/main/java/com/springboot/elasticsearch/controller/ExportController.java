package com.springboot.elasticsearch.controller;

import com.springboot.elasticsearch.document.Export;
import com.springboot.elasticsearch.search.ExportDTO;
import com.springboot.elasticsearch.service.ExportData;
import com.springboot.elasticsearch.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/export")
public class ExportController {
    private final ExportService service;
    private final ExportData data;

    @Autowired
    public ExportController(ExportService service, ExportData data) {
        this.service = service;
        this.data = data;
    }

    @PostMapping
    public void index(@RequestBody final Export export) {
        service.index(export);
    }

    @PostMapping("/insertData")
    public void insertData(){
        data.insertData();
    }

    @GetMapping
    public Export getById(@RequestParam("id")String id) {
        return service.getById(id);
    }

    @PostMapping("/search")
    public List<Export> search(
            @RequestBody  ExportDTO dto
            ,@RequestParam(name = "keyword",required = true)String searchExport,
            @RequestParam(name ="field",required = false)List<String> fields,
            @RequestParam(name ="page",required =true)Integer page,
            @RequestParam(name = "size", required = true)Integer size
    ) {
        dto.setSearchExport(searchExport);
        dto.setFields(fields);
        dto.setPage(page);
        dto.setSize(size);
        return service.search(dto);
    }
}