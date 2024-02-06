package com.xvanop01.isregatta.base.support.template;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@RequiredArgsConstructor
public abstract class TableDataService<E, R extends JpaRepository<E, ?> & JpaSpecificationExecutor<E>, D,
        M extends TableDataResponseMapper<E, D>> {

    protected final R repository;

    protected final M mapper;

    @Setter
    protected Specification<E> specification;

    public Page<D> getFormated(Pageable pageable) {
        Page<E> page = fetch(pageable);
        return page.map(mapper::map);
    }

    protected Page<E> fetch(Pageable pageable) {
        return repository.findAll(this.specification, pageable);
    }
}
