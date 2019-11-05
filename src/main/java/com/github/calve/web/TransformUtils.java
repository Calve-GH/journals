package com.github.calve.web;

import com.github.calve.model.*;
import com.github.calve.to.*;
import com.github.calve.util.to.*;
import com.github.calve.util.builders.MailBuilder;
import com.github.calve.util.builders.ToBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TransformUtils {

    private final static String date_templ = "\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*";
    private final static String date_range_tmpl = date_templ + "-" + date_templ;
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public TransformUtils() {
        throw new AssertionError("Cannot create instance of util class");
    }

    //refactoring review
    public static Pageable getPageable(DataTablesInput dti) {
        int page = dti.getStart() / dti.getLength();
        Sort sort = Sort.by(
                Sort.Direction.fromString(dti.getOrder().get(0).getDir().toUpperCase()),
                dti.getColumns().get(dti.getOrder().get(0).getColumn()).getData());
        return sort != null ? PageRequest.of(page, dti.getLength(), sort) :
                PageRequest.of(page, dti.getLength());
    }


    public static <T> Specification<T> getSpecification(DataTablesInput dti) {
        Specification<T> spec = null;
        if (!dti.getOrder().isEmpty()) {
            if (!dti.getSearch().getValue().isEmpty()) {
                if (dti.getSearch().getValue().matches(date_templ)) {
                    LocalDate date = LocalDate.parse(dti.getSearch().getValue().trim(), DTF);
                    String parameterName = dti.getColumns().get(0).getData();
                    return new SpecDate<>(new DateCriteria(parameterName, date), null);
                }
                if (dti.getSearch().getValue().matches(date_range_tmpl)) {
                    String[] dates = dti.getSearch().getValue().split("-");
                    if (dates.length == 2) {
                    String parameterName = dti.getColumns().get(0).getData();
                        LocalDate from = LocalDate.parse(dates[0].trim(), DTF);
                        LocalDate to = LocalDate.parse(dates[1].trim(), DTF);
                        return new SpecDate<>(new DateCriteria(parameterName, from), new DateCriteria(parameterName, to));
                    }
                } else {
                    List<SearchCriteria> criteriaList = new ArrayList<>();
                    dti.getColumns().remove(0);
                    for (Column column : dti.getColumns().stream().filter(Column::getSearchable).collect(Collectors.toList())) {
                        criteriaList.add(new SearchCriteria(column.getData(), dti.getSearch().getValue()));
                    }
                    spec = new Spec<>(criteriaList);
                }
            }
        }
        return spec;
    }

    public static String clearExecutorName(String name) {
        return name.trim().toLowerCase();
    }

    public static List<MailTo> getToList(List<? extends Mail> list) {
        List<MailTo> result = new ArrayList<>();
        for (Mail mail : list) {
            result.add(getTo(mail));
        }
        return result;
    }

    public static List<BaseMailTo> getBaseToList(List<? extends OutgoingMail> list) {
        List<BaseMailTo> result = new ArrayList<>();
        for (OutgoingMail mail : list) {
            result.add(getBaseMailTo(mail));
        }
        return result;
    }

    public static List<ExecutorTo> getExecutorsToList(List<? extends Executor> list) {
        List<ExecutorTo> result = new ArrayList<>();
        for (Executor executor : list) {
            result.add(getExecutorTo(executor));
        }
        return result;
    }

    public static ExecutorTo getExecutorTo(Executor executor) {
        return new ExecutorTo(executor.getId(), executor.getName(), executor.isEnabled());
    }

    public static Executor getExecutorFromTo(ExecutorTo to) {
        return new Executor(to.getId(), to.getName(), to.isEnabled());
    }

    private static ToBuilder getBaseToBuilder(Mail mail) {
        return new ToBuilder().setId(mail.getId())
                .setOuterDate(mail.getOuterDate())
                .setCorrespondent(mail.getCorrespondent())
                .setDescription(mail.getDescription())
                .setExecutor(mail.getExecutor().getName());
    }

    public static BaseMailTo getBaseMailTo(OutgoingMail mail) {
        return getBaseToBuilder(mail)
                .setOuterIndex(mail.getOuterIndex())
                .setGenIndex(mail.getGenIndex())
                .getBaseMailTo();
    }

    public static MailTo getTo(Mail mail) {
        return getBaseToBuilder(mail)
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setOuterIndex(mail.getOuterIndex())
                .setDoneDate(mail.getDoneDate())
                .setDoneIndex(mail.getDoneIndex())
                .setDoneResult(mail.getDoneResult())
                .setProceedingNumber(mail.getProceedingNumber())
                .setDebtor(mail.getDebtor())
                .setWorkDate(mail.getWorkDate())
                .setWorkIndex(mail.getWorkIndex())
                .setAuthority(mail.getAuthority())
                .getMailTo();
    }

    private static MailBuilder getBaseBuilder(MailTo mail, Executor executor) {
        return new MailBuilder().setId(mail.getId())
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setDescription(mail.getDescription())
                .setExecutor(executor);
    }

    private static MailBuilder getDoneBuilder(MailTo mail, Executor executor) {
        return getBaseBuilder(mail, executor)
                .setDoneDate(mail.getDoneDate())
                .setDoneIndex(mail.getDoneIndex());
    }

    public static Request getRequest(MailTo mail, Executor executor) {
        return getDoneBuilder(mail, executor)
                .getRequest();
    }

    public static Generic getGeneric(MailTo mail, Executor executor) { // TODO: 04.10.2019
        return getDoneBuilder(mail, executor)
                .getGeneric();
    }

    public static Complaint getComplaint(MailTo mail, Executor executor) {// TODO: 04.10.2019
        return getDoneBuilder(mail, executor)
                .setDoneResult(mail.getDoneResult())
                .getComplaint();
    }

    public static Foreigner getForeigner(MailTo mail, Executor executor) {
        return getBaseBuilder(mail, executor)
                .setProceedingNumber(mail.getProceedingNumber())
                .setDebtor(mail.getDebtor())
                .getForeigner();
    }

    public static Application getApplication(MailTo mail, Executor executor) {
        return getDoneBuilder(mail, executor)
                .setWorkDate(mail.getWorkDate())
                .setWorkIndex(mail.getWorkIndex())
                .setAuthority(mail.getAuthority())
                .setProceedingNumber(mail.getProceedingNumber())
                .getApplication();
    }

    public static Info getInfo(MailTo mail, Executor executor) {
        return getBaseBuilder(mail, executor)
                .setDoneDate(mail.getDoneDate())
                .setDoneIndex(mail.getDoneIndex())
                .setDoneResult(mail.getDoneResult())
                .getInfo();
    }

    public static OutgoingMail getOutgoing(BaseMailTo mail, Executor executor) {
        return new MailBuilder().setId(mail.getId())
                .setOuterDate(mail.getOuterDate())
                .setGenIndex(mail.getGenIndex())
                .setProceedingNumber(mail.getOuterIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .getOutgoing();
    }
}