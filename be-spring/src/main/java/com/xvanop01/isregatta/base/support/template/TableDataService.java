package com.xvanop01.isregatta.base.support.template;

import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpReturnCode;
import com.xvanop01.isregatta.base.support.model.Filter;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * TableDataService
 * Predoha pre servis zabezpecujuci data pre tabulky
 * @param <E> entita obsahujuca pozadovane informacie
 * @param <R> repozitar entity
 * @param <F> model filtra
 * @param <D> DTO pozadovanej odpovede
 * @param <M> mapper z entity na DTO
 * @author 2024 Peter Vano
 */
@RequiredArgsConstructor
public abstract class TableDataService<E, R extends JpaRepository<E, ?> & JpaSpecificationExecutor<E>, F, D,
        M extends TableDataResponseMapper<E, D>> {

    protected final R repository;

    protected final M mapper;

    private Specification<E> specification;

    /**
     * Metoda pre ziskanie triedy filtra a jeho naplnenie
     * @param filterList definovane parametre filtra
     * @return naplneny filter pre TableDataService
     * @throws HttpException zly format, neimplementovany typ hodnoty filtra
     */
    public F createFilter(List<Filter> filterList) throws HttpException {
        if (filterList != null && !filterList.isEmpty()) {
            ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).as(TableDataService.class);
            Class<F> fClass = (Class<F>) resolvableType.resolveGeneric(2); // ziskanie tried filtra
            if (fClass != null) {
                try {
                    F filter = fClass.newInstance();
                    // naplnanie filtra
                    for (Filter f : filterList) {
                        if (f.getValue() == null || f.getValue().isEmpty()) {
                            continue;
                        }
                        Field field = fClass.getDeclaredField(f.getColumn());
                        Class<?> c = field.getType();
                        // mapovanie retazca na pozadovany typ
                        if (c == String.class) {
                            field.set(filter, f.getValue());
                        } else if (field.getType() == LocalDate.class) {
                            field.set(filter, LocalDate.parse(f.getValue()));
                        } else if (field.getType() == Integer.class) {
                            field.set(filter, Integer.valueOf(f.getValue()));
                        } else {
                            throw new HttpException(HttpReturnCode.NOT_IMPLEMENTED,
                                    "Mapping to " + c + " not supported.");
                        }
                    }
                    return filter;
                } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                    throw new HttpException(HttpReturnCode.BAD_REQUEST, e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * Hlavna metoda pre ziskanie zaznamov
     * @param pageable definicia parametrov strany
     * @param filter inicializovany filter
     * @return pozadovane zaznamy
     */
    public Page<D> getFormated(Pageable pageable, Object filter) {
        this.specification = null;
        doFilter(filter);
        Page<E> page = fetch(pageable, filter);
        return page.map(mapper::map);
    }

    /**
     * Predvolena metoda ziskavajuca zaznamy z DB
     * @param pageable definicia parametrov strany
     * @param filter inicializovany filter
     * @return pozadovane zaznamy
     * @implNote pouzit @Override pre specificke poziadavky
     */
    protected Page<E> fetch(Pageable pageable, Object filter) {
        return repository.findAll(this.specification, pageable);
    }

    /**
     * Pouzit @Override pre definiciu specifikacie filtrovania
     * @param filter inicializovany filter
     */
    protected void doFilter(Object filter) {}

    /**
     * Metoda pre spajanie specifikacii
     * @param spec nova specifikacia
     */
    protected void specAnd(Specification<E> spec) {
        if (this.specification == null) {
            this.specification = spec;
        } else {
            this.specification = this.specification.and(spec);
        }
    }

    protected Specification<E> getSpecification() {
        return this.specification;
    }
}
