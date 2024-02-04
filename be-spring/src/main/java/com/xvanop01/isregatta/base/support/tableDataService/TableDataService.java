package com.xvanop01.isregatta.base.support.tableDataService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@RequiredArgsConstructor
public abstract class TableDataService<E, R extends JpaRepository<E, ?> & JpaSpecificationExecutor<E>> {

    protected final R repository;

    @Setter
    protected Specification<E> specification;

    public Page<E> fetch(Pageable pageable) {
        return repository.findAll(this.specification, pageable);
    }
}
