package com.xvanop01.isregatta.base.support.model;

import lombok.Data;

import java.util.List;

/**
 * TableDataResponse
 * Format dat tabuliek pre FE
 * @author 2024 Peter Vano
 */
@Data
public class TableDataResponse {

    private List<?> data;

    private Long totalItems;

    private Integer totalPages;
}
