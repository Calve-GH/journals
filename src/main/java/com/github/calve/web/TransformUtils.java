package com.github.calve.web;

import com.github.calve.model.*;
import com.github.calve.to.ExecutorTo;
import com.github.calve.to.MailTo;
import com.github.calve.util.builders.MailBuilder;
import com.github.calve.util.builders.ToBuilder;

import java.util.ArrayList;
import java.util.List;

public class TransformUtils {

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

    public static MailTo getTo(Mail mail) {
        return new ToBuilder().setId(mail.getId())
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setDescription(mail.getDescription())
                .setExecutor(mail.getExecutor().getName())
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
    //refactoring all methods
    public static Request getRequest(MailTo mail, Executor executor) {
        return new MailBuilder().setId(mail.getId())
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .setDoneDate(mail.getDoneDate())
                .setDoneIndex(mail.getDoneIndex())
                .getRequest();
    }

    public static Generic getGeneric(MailTo mail, Executor executor) { // TODO: 04.10.2019
        return new MailBuilder().setId(mail.getId())
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .setDoneDate(mail.getDoneDate())
                .setDoneIndex(mail.getDoneIndex())
                .getGeneric();
    }

    public static Complaint getComplaint(MailTo mail, Executor executor) {// TODO: 04.10.2019
        return new MailBuilder().setId(mail.getId())
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .setDoneDate(mail.getDoneDate())
                .setDoneIndex(mail.getDoneIndex())
                .setDoneResult(mail.getDoneResult())
                .getComplaint();
    }

    public static Foreigner getForeigner(MailTo mail, Executor executor) {
        return new MailBuilder().setId(mail.getId())
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .setProceedingNumber(mail.getProceedingNumber())
                .setDebtor(mail.getDebtor())
                .getForeigner();
    }

    public static Application getApplication(MailTo mail, Executor executor) {
        return new MailBuilder().setId(mail.getId())
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .setDoneDate(mail.getDoneDate())
                .setDoneIndex(mail.getDoneIndex())
                .setWorkDate(mail.getWorkDate())
                .setWorkIndex(mail.getWorkIndex())
                .setAuthority(mail.getAuthority())
                .setProceedingNumber(mail.getProceedingNumber())
                .getApplication();
    }

    public static Info getInfo(MailTo mail, Executor executor) {
        return new MailBuilder().setId(mail.getId())
                .setIncomeDate(mail.getIncomeDate())
                .setIncomeIndex(mail.getIncomeIndex())
                .setCorrespondent(mail.getCorrespondent())
                .setOuterDate(mail.getOuterDate())
                .setOuterIndex(mail.getOuterIndex())
                .setDescription(mail.getDescription())
                .setExecutor(executor)
                .setDoneDate(mail.getDoneDate())
                .setDoneIndex(mail.getDoneIndex())
                .setDoneResult(mail.getDoneResult())
                .getInfo();
    }
}
