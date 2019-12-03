package com.github.calve.util.excel;

import com.github.calve.model.common.Incoming;
import com.github.calve.model.common.Outgoing;
import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.*;
import com.github.calve.to.excel.BaseMailTo;
import com.github.calve.to.excel.MailTo;
import com.github.calve.util.builders.MailBuilder;

public final class TransformUtils {

    public TransformUtils() {
        throw new AssertionError("Cannot create instance of util class");
    }

    public static String clearExecutorName(String name) {
        return name.trim().toLowerCase();
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
}