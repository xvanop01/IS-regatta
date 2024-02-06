package com.xvanop01.isregatta.base.support.mapper;

import com.xvanop01.isregatta.api.base.model.SortDto;
import com.xvanop01.isregatta.api.base.model.TableDataRequestDto;
import com.xvanop01.isregatta.api.base.model.TableDataResponseDto;
import com.xvanop01.isregatta.base.support.model.TableDataResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Mapper(componentModel = "spring")
public abstract class TableDataMapper {

    private static final String DIR_ASC = "ASC";

    public PageRequest map(TableDataRequestDto tableDataRequestDto) {
        SortDto sortDto = tableDataRequestDto.getSortCriteria();
        Sort.Direction direction = DIR_ASC.equals(sortDto.getDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortDto.getColumn());
        return PageRequest.of(tableDataRequestDto.getPageNumber(), tableDataRequestDto.getPageSize(), sort);
    }

    public abstract TableDataResponseDto map(TableDataResponse tableDataResponse);

    public TableDataResponseDto map(Page<?> page) {
        TableDataResponse response = new TableDataResponse();
        response.setData(page.toList());
        response.setTotal(page.getTotalElements());
        return map(response);
    }
}
