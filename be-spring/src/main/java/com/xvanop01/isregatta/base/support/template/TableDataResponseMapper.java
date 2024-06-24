package com.xvanop01.isregatta.base.support.template;

/**
 * TableDataResponseMapper
 * Predloha mapperu zabezpecujuca funkcionalitu prekladu entity na DTO
 * @param <E> entita
 * @param <D> DTO
 * @author 2024 Peter Vano
 */
public abstract class TableDataResponseMapper<E, D> {

    public abstract D map(E entity);
}
