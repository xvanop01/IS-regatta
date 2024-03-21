package com.xvanop01.isregatta.base.support.template;

import com.xvanop01.isregatta.base.exception.Http400ReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.support.model.Filter;
import java.lang.reflect.Field;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@RequiredArgsConstructor
public abstract class TableDataService<E, R extends JpaRepository<E, ?> & JpaSpecificationExecutor<E>, F, D,
        M extends TableDataResponseMapper<E, D>> {

    protected final R repository;

    protected final M mapper;

    private Specification<E> specification;

    public F createFilter(List<Filter> filterList) throws HttpException {
        if (filterList != null && !filterList.isEmpty()) {
            ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).as(TableDataService.class);
            Class<F> fClass = (Class<F>) resolvableType.resolveGeneric(2);
            if (fClass != null) {
                try {
                    F filter = fClass.newInstance();
                    for (Filter f : filterList) {
                        Field field = fClass.getDeclaredField(f.getColumn());
                        field.set(filter, f.getValue());
                    }
                    return filter;
                } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                    throw new HttpException(Http400ReturnCode.BAD_REQUEST, e.getMessage());
                }
            }
        }
        return null;
    }

    public Page<D> getFormated(Pageable pageable, Object filter) {
        this.specification = null;
        doFilter(filter);
        Page<E> page = fetch(pageable, filter);
        return page.map(mapper::map);
    }

    protected Page<E> fetch(Pageable pageable, Object filter) {
        return repository.findAll(this.specification, pageable);
    }

    protected void doFilter(Object filter) {}

    protected void specAnd(Specification<E> spec) {
        if (this.specification == null) {
            this.specification = spec;
        } else {
            this.specification = this.specification.and(spec);
        }
    }
}
