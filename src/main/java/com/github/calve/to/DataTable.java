package com.github.calve.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO: 04.11.2019  friendly with lombok annotations
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTable {
    private int draw;
    private int start;
    private long recordsTotal;
    private long recordsFiltered;

    private List<? extends BaseTo> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<? extends BaseTo> getData() {
        return data;
    }

    public void setData(List<? extends BaseTo> data) {
        this.data = data;
    }
}
