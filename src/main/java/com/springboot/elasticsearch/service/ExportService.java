package com.springboot.elasticsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.elasticsearch.document.Export;
import com.springboot.elasticsearch.helper.Indices;
import com.springboot.elasticsearch.search.ExportDTO;
import com.springboot.elasticsearch.search.util.SearchUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ExportService {
    private static final Logger LOG = LoggerFactory.getLogger(ExportService.class);
    public static final ObjectMapper MAPPER = new ObjectMapper();

    private final RestHighLevelClient client;

    @Autowired
    public ExportService(RestHighLevelClient client) {
        this.client = client;
    }

    public List<Export> search(final ExportDTO dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
                Indices.EXPORT_INDEX,dto);
        if (request == null) {
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            final SearchHit[] searchHits = response.getHits().getHits();
            final List<Export> export = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                export.add(
                        MAPPER.readValue(hit.getSourceAsString(), Export.class)
                );
            }

            return export;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Boolean index(final Export export){
        try {
            final String exportAsString = MAPPER.writeValueAsString(export);
            final IndexRequest request = new IndexRequest(Indices.EXPORT_INDEX);
            request.id(export.getId());
            request.source(exportAsString, XContentType.JSON);

            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.OK);

        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
    public Export getById(final String id ){
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.EXPORT_INDEX, id),
                    RequestOptions.DEFAULT
            );
            if (documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }

            return MAPPER.readValue(documentFields.getSourceAsString(), Export.class);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

}
