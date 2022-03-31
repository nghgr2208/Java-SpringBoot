package com.springboot.elasticsearch.search.util;

import com.springboot.elasticsearch.search.ExportDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SearchUtil {
    private SearchUtil(){}

    public static SearchRequest buildSearchRequest(final String indexName,final ExportDTO dto){
        try{
            final int page = dto.getPage();
            final int size = dto.getSize();
            final int from = page <=0 ? 0:page * size;
            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .from(from)
                    .size(size)
                    .postFilter(getQueryBuilder(dto));
            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        }catch (final Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private static QueryBuilder getQueryBuilder(final ExportDTO dto) {
        if (dto == null) {
            return null;
        }

        final List<String> fields = dto.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }

        if (fields.size() > 1) {
            final MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchExport())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);

            fields.forEach(queryBuilder::field);

            return queryBuilder;
        }

        return fields.stream()
                .findFirst()
                .map(field ->
                        QueryBuilders.matchQuery(field, dto.getSearchExport())
                                .operator(Operator.AND))
                .orElse(null);
    }

}
