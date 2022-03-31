package com.springboot.elasticsearch.search;


import java.util.List;

public class ExportDTO extends PagedRequestDTO{
    private List<String> fields;
    private String searchExport;

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getSearchExport() {
        return searchExport;
    }

    public void setSearchExport(String searchExport) {
        this.searchExport = searchExport;
    }

}
