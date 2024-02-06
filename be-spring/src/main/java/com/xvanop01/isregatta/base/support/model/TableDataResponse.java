package com.xvanop01.isregatta.base.support.model;

import lombok.Data;

import java.util.List;

@Data
public class TableDataResponse {

    private List<?> data;

    private Long total;
}
