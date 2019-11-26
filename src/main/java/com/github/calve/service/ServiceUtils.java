package com.github.calve.service;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.Mail;
import com.github.calve.to.BaseMailTo;
import com.github.calve.to.DataTable;
import com.github.calve.to.ExecutorTo;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.data.domain.Page;

import java.util.List;

public class ServiceUtils {

    private ServiceUtils() {
        throw new AssertionError("Error calling ServiceUtil constructor.");
    }

    //refactoring mb generics
    public static DataTable constructPage(DataTablesInput dti, Page<? extends Mail> mails) {
        return DataTable.builder()//refactoring generics?!
                .data(getTos(mails))
                .recordsTotal(mails.getTotalElements())
                .recordsFiltered(mails.getTotalElements())
                .draw(dti.getDraw())
                .start(dti.getStart())
                .build();
    }

    public static DataTable constructExecutorsPage(DataTablesInput dti, Page<Executor> executors) {
        return DataTable.builder()//refactoring generics?!
                .data(getExecutorTos(executors))
                .recordsTotal(executors.getTotalElements())
                .recordsFiltered(executors.getTotalElements())
                .draw(dti.getDraw())
                .start(dti.getStart())
                .build();
    }

    private static List<? extends BaseMailTo> getTos(Page<? extends Mail> mails) {
        return TransformUtils.getToList(mails.getContent());
    }

    private static List<ExecutorTo> getExecutorTos(Page<Executor> mails) {
        return TransformUtils.getExecutorsToList(mails.getContent());
    }
}
