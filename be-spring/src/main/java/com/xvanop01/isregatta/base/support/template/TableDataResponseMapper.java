package com.xvanop01.isregatta.base.support.template;

public abstract class TableDataResponseMapper<E, D> {

    public abstract D map(E entity);
}
