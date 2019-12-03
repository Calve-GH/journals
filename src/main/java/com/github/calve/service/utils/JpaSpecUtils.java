package com.github.calve.service.utils;

import com.github.calve.util.to.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.calve.util.DateTimeUtil.DATE_TIME_FORMATTER;

public final class JpaSpecUtils {

    private final static String DATE_SOLO_TEMPLATE = "\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*";
    private final static String DATE_YEAR_TEMPLATE = "\\.((?:19|20)\\d{2})\\s*";
    private final static String DATE_RANGE_TEMPLATE = DATE_SOLO_TEMPLATE + "-" + DATE_SOLO_TEMPLATE;
    private final static LocalDate NOW = LocalDate.now();
    //https://stackoverflow.com/questions/9382897/how-to-get-start-and-end-date-of-a-year
    private final static LocalDate FIRST_DAY_OF_YEAR = NOW.with(TemporalAdjusters.firstDayOfYear());
    private final static LocalDate LAST_DAY_OF_YEAR = NOW.with(TemporalAdjusters.lastDayOfYear());

    public JpaSpecUtils() {
        throw new AssertionError("Cannot create instance of util class");
    }

    //refactoring review
    public static Pageable getPageable(DataTablesInput dti) {
        int length = dti.getLength();
        int pageNumber = dti.getStart() / length;
        Sort sort = Sort.by(getSortDirection(dti), getProperty(dti));
        return sort != null ? PageRequest.of(pageNumber, length, sort) : PageRequest.of(pageNumber, length);
    }

    private static Sort.Direction getSortDirection(DataTablesInput dti) {
        return Sort.Direction.fromString(dti.getOrder().get(0).getDir().toUpperCase());
    }

    private static String getProperty(DataTablesInput dti) {
        return dti.getColumns().get(dti.getOrder().get(0).getColumn()).getData();
    }

    private static boolean isSearchValueContainsDateTemplate(DataTablesInput dti, String template) {
        return getSearchValue(dti).matches(template);
    }

    private static String[] parseDateRange(DataTablesInput dti) {
        return getSearchValue(dti).split("-");
    }

    private static String getSearchValue(DataTablesInput dti) {
        return dti.getSearch().getValue();
    }

    private static String getSearchYear(String dirtyDate) {
        return dirtyDate.substring(1, 5);
    }

    public static <T> Specification<T> getSpecification(DataTablesInput dti) {
        if (!getSearchValue(dti).isEmpty()) {
            if (isSearchValueContainsDateTemplate(dti, DATE_YEAR_TEMPLATE)) {
                return getYearSpecification(dti);
            }
            if (isSearchValueContainsDateTemplate(dti, DATE_SOLO_TEMPLATE)) {
                return getDateSpecification(dti);
            }
            if (isSearchValueContainsDateTemplate(dti, DATE_RANGE_TEMPLATE) && isDateRange(dti)) {
                return getDateRangeSpecification(dti);
            }
            return getDefaultSpecification(dti);
        }
        return getCurrentYearSpecification(dti);
    }


    private static boolean isDateRange(DataTablesInput dti) {
        return parseDateRange(dti).length == 2;
    }

    private static <T> Specification<T> getDefaultSpecification(DataTablesInput dti) {
        Specification<T> spec;
        List<SearchCriteria> criteriaList = new ArrayList<>();
        //first column always Date, cannot be sort as a String;
        dti.getColumns().remove(0);
        for (Column column : getSearchableColumns(dti)) {
            criteriaList.add(new SearchCriteria(column.getData(), getSearchValue(dti)));
        }
        spec = new Spec<>(criteriaList);
        return spec;
    }

    public static <T> Specification<T> getDefaultNoDateSpecification(DataTablesInput dti) {
        Specification<T> spec;
        List<SearchCriteria> criteriaList = new ArrayList<>();
        for (Column column : getSearchableColumns(dti)) {
            criteriaList.add(new SearchCriteria(column.getData(), getSearchValue(dti)));
        }
        spec = new Spec<>(criteriaList);
        return spec;
    }

    private static <T> Specification<T> getDateRangeSpecification(DataTablesInput dti) {
        String[] dates = parseDateRange(dti);
        LocalDate from = parseToDate(0, dates);
        LocalDate to = parseToDate(1, dates);
        return getDateDefaultSpecification(dti, from, to);
    }

    private static <T> Specification<T> getCurrentYearSpecification(DataTablesInput dti) {
        return getDateDefaultSpecification(dti, FIRST_DAY_OF_YEAR, LAST_DAY_OF_YEAR);
    }

    private static <T> Specification<T> getYearSpecification(DataTablesInput dti) {
        int searchYear = Integer.parseInt(getSearchYear(getSearchValue(dti)));
        LocalDate from = LocalDate.of(searchYear, 1, 1);
        LocalDate to = from.with(TemporalAdjusters.lastDayOfYear());
        return getDateDefaultSpecification(dti, from, to);
    }

    private static <T> Specification<T> getDateDefaultSpecification(DataTablesInput dti, LocalDate from, LocalDate to) {
        return new SpecDate<>(new DateCriteria(getSearchableDateParameterName(dti), from),
                new DateCriteria(getSearchableDateParameterName(dti), to));
    }

    private static <T> Specification<T> getDateSpecification(DataTablesInput dti) {
        LocalDate date = LocalDate.parse(getSearchValue(dti).trim(), DATE_TIME_FORMATTER);
        return new SpecDate<>(new DateCriteria(getSearchableDateParameterName(dti), date), null);
    }

    private static List<Column> getSearchableColumns(DataTablesInput dti) {
        return dti.getColumns().stream().filter(Column::getSearchable).collect(Collectors.toList());
    }

    private static LocalDate parseToDate(int i, String[] dates) {
        return LocalDate.parse(dates[i].trim(), DATE_TIME_FORMATTER);
    }

    private static String getSearchableDateParameterName(DataTablesInput dti) {
        return dti.getColumns().get(0).getData();
    }
}