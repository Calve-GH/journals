package com.github.calve.service;

import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;

import java.util.List;

import static com.github.calve.web.TransformUtils.getPageable;
import static com.github.calve.web.TransformUtils.getSpecification;

public class ServiceUtils {

    private ServiceUtils() {
        throw new AssertionError("Error calling ServiceUtil constructor.");
    }

    public static Pair<Pageable, Specification<?>> constructPageableSpecification(DataTablesInput dti) {
        return Pair.of(getPageable(dti), getSpecification(dti));
    }

    public static DataTable constructPage(DataTablesInput dti, Page<?> pages, List<?> content) {
        return DataTable.builder()
                .data(content)
                .recordsTotal(pages.getTotalElements())
                .recordsFiltered(pages.getTotalElements())
                .draw(dti.getDraw())
                .start(dti.getStart())
                .build();
    }
}
