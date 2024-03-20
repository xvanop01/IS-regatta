package com.xvanop01.isregatta.base.support.service;

import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.support.model.Filter;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableDataServiceProvider {

    private final ApplicationContext context;

    public Page<?> getTableData(String name, Pageable pageable, List<Filter> filter) throws HttpException {
        TableDataService<?, ?, ?, ?, ?> service = context.getBean(name, TableDataService.class);
        return service.getFormated(pageable, service.createFilter(filter));
    }
}
