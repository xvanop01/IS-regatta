package com.xvanop01.isregatta.base.support.tableDataService;

import com.xvanop01.isregatta.api.base.model.TableDataResponseDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class TableDataMapper {

    public abstract TableDataResponseDto map(TableDataResponse tableDataResponse);

    public TableDataResponseDto map(Page<? extends Object> page) {
        TableDataResponse response = new TableDataResponse();
        response.setData(page.toList());
        response.setTotal(page.getTotalElements());
        return map(response);
    }
}
