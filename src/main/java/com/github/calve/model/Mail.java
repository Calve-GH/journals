package com.github.calve.model;

import java.time.LocalDate;

public interface Mail {

    default Integer getId() {
        return 0;
    }

    default LocalDate getIncomeDate() {
        return null;
    }

    default String getIncomeIndex() {
        return "";
    }

    default String getCorrespondent() {
        return "";
    }

    default LocalDate getOuterDate() {
        return null;
    }

    default String getOuterIndex() {
        return "";
    }

    default String getDescription() {
        return "";
    }

    default Executor getExecutor() {
        return null;
    }

    default LocalDate getDoneDate() {
        return null;
    }

    default String getDoneIndex() {
        return "";
    }

    default String getDoneResult() {
        return "";
    }

    default LocalDate getWorkDate() {
        return null;
    }

    default String getWorkIndex() {
        return "";
    }

    default String getAuthority() {
        return "";
    }

    default String getProceedingNumber() {
        return "";
    }

    default String getDebtor() {
        return "";
    }

    default boolean getGenerics() {
        return false;
    }
}
