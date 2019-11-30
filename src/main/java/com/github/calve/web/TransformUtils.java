package com.github.calve.web;

import com.github.calve.model.Mail;
import com.github.calve.model.common.Incoming;
import com.github.calve.model.common.Outgoing;
import com.github.calve.model.email.Inbox;
import com.github.calve.model.email.Sent;
import com.github.calve.model.etc.Contact;
import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.*;
import com.github.calve.to.*;
import com.github.calve.to.email.EmailTo;
import com.github.calve.util.builders.EmailBuilder;
import com.github.calve.util.builders.MailBuilder;
import com.github.calve.util.builders.ToBuilder;
import com.github.calve.util.to.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TransformUtils {

    private final static String DATE_SOLO_TEMPLATE = "\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*";
    private final static String DATE_YEAR_TEMPLATE = "\\.((?:19|20)\\d{2})\\s*";
    private final static String DATE_RANGE_TEMPLATE = DATE_SOLO_TEMPLATE + "-" + DATE_SOLO_TEMPLATE;
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static LocalDate NOW = LocalDate.now();
    //https://stackoverflow.com/questions/9382897/how-to-get-start-and-end-date-of-a-year
    private final static LocalDate FIRST_DAY_OF_YEAR = NOW.with(TemporalAdjusters.firstDayOfYear());
    private final static LocalDate LAST_DAY_OF_YEAR = NOW.with(TemporalAdjusters.lastDayOfYear());

    public TransformUtils() {
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
        LocalDate date = LocalDate.parse(getSearchValue(dti).trim(), DTF);
        return new SpecDate<>(new DateCriteria(getSearchableDateParameterName(dti), date), null);
    }

    private static List<Column> getSearchableColumns(DataTablesInput dti) {
        return dti.getColumns().stream().filter(Column::getSearchable).collect(Collectors.toList());
    }

    private static LocalDate parseToDate(int i, String[] dates) {
        return LocalDate.parse(dates[i].trim(), DTF);
    }

    private static String getSearchableDateParameterName(DataTablesInput dti) {
        return dti.getColumns().get(0).getData();
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

    public static List<EmailTo> getEmailTos(List<? extends Mail> list) {
        List<EmailTo> result = new ArrayList<>();
        for (Mail mail : list) {
            result.add(getEmailTo(mail));
        }
        return result;
    }

    public static List<BaseMailTo> getBaseToList(List<? extends Outgoing> list) {
        List<BaseMailTo> result = new ArrayList<>();
        for (Outgoing mail : list) {
            result.add(getBaseMailTo(mail));
        }
        return result;
    }

//    public static List<ExecutorTo> getExecutorsToList(List<? extends Executor> list) {
//        List<ExecutorTo> result = new ArrayList<>();
//        for (Executor executor : list) {
//            result.add(getExecutorTo(executor));
//        }
//        return result;
//    }

//    public static ExecutorTo getExecutorTo(Executor executor) {
//        return new ExecutorTo(executor.getId(), executor.getName(), executor.isEnabled());
//    }

//    public static ContactTo getContactTo(Contact contact) {
//        return new ContactTo(contact.getId(), contact.getAlias(), contact.getEmail());
//    }

//    public static Executor getExecutorFromTo(ExecutorTo to) {
//        return new Executor(to.getId(), to.getName(), to.isEnabled());
//    }
//    public static Contact getContactFromTo(ContactTo to) {
//        return new Contact(to.getId(), to.getName(), to.getEmail());
//    }

    private static ToBuilder getBaseToBuilder(Mail mail) {
        return new ToBuilder().setId(mail.getId())
                .setOuterDate(mail.getOuterDate())
                .setCorrespondent(mail.getCorrespondent())
                .setDescription(mail.getDescription())
                .setExecutor(mail.getExecutor().getName());
    }

    public static EmailTo getEmailTo(Mail mail) {
        return new ToBuilder().setId(mail.getId())
                .setGenIndex(mail.getGenIndex())
                .setContact(mail.getContact().getAlias())
                .setIncomeDate(mail.getIncomeDate())
                .setAnswer(mail.getAnswer())
                .setDescription(mail.getDescription())
                .getEmailTo();
    }

    public static BaseMailTo getBaseMailTo(Outgoing mail) {
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
                .setGenerics(mail.getGenerics())
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

    public static Outgoing getOutgoing(BaseMailTo mail, Executor executor) {
        return new MailBuilder().setId(mail.getId())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setGenIndex(mail.getGenIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .getOutgoing();
    }

    public static Incoming getIncoming(BaseMailTo mail, Executor executor) {
        return new MailBuilder().setId(mail.getId())
                .setGenIndex(mail.getGenIndex())
                .setOuterDate(mail.getOuterDate())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .getIncoming();
    }

    public static Inbox getInbox(EmailTo email, Contact contact) {
        return new EmailBuilder().setId(email.getId())
                .setGenIndex(email.getGenIndex())
                .setContact(contact)
                .setDate(email.getDate())
                .setAnswer(email.getAnswer())
                .setDescription(email.getDescription())
                .getInbox();
    }

    public static Sent getSent(EmailTo email, Contact contact) {
        return new EmailBuilder().setId(email.getId())
                .setDate(email.getDate())
                .setContact(contact)
                .setDescription(email.getDescription())
                .getSent();
    }
}