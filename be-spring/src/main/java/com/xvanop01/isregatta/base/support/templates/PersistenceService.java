package com.xvanop01.isregatta.base.support.templates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class PersistenceService<E, R extends JpaRepository<E, Integer> & JpaSpecificationExecutor<E>> {

    protected final R repository;

    @Transactional
    public E persist(E entity) {
        log.info("persist: {}", entity);
        return repository.save(entity);
    }

    public E findById(Integer id) {
        log.info("findById: {}", id);
        return repository.findById(id).orElse(null);
    }

    public List<E> findAll() {
        log.info("findAll");
        return repository.findAll();
    }
}
