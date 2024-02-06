package com.xvanop01.isregatta.base.support.service;

import com.xvanop01.isregatta.base.support.template.TableDataService;
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

    public Page<?> getTableData(String name, Pageable pageable) {
        TableDataService<?, ?, ?, ?> service = context.getBean(name, TableDataService.class);
        // TODO specification for filtering
        return service.getFormated(pageable);
    }
}
