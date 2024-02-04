package com.xvanop01.isregatta.base.support.tableDataService;

import lombok.Data;

import java.util.List;

@Data
public class TableDataResponse {

    private List<?> data;

    private Long total;
}
