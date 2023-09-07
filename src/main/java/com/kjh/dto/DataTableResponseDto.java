package com.kjh.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class DataTableResponseDto<T> {
    private int draw;
    private int recordsFiltered;
    private List<T> data;

    public DataTableResponseDto(int draw, int recordsFiltered, List<T> data) {
        this.draw = draw;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

}
