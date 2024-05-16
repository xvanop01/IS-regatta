package com.xvanop01.isregatta.base.support.model;

import lombok.Data;

/**
 * Filter
 * Definicia filtra z FE pre TableDataService
 * @author 2024 Peter Vano
 */
@Data
public class Filter {

    private String column;

    private String value;
}
