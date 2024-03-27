package com.xvanop01.isregatta.base.support.mapper;

import com.xvanop01.isregatta.api.base.model.FilterDto;
import com.xvanop01.isregatta.api.base.model.SortDto;
import com.xvanop01.isregatta.api.base.model.TableDataRequestDto;
import com.xvanop01.isregatta.api.base.model.TableDataResponseDto;
import com.xvanop01.isregatta.base.exception.HttpReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.support.model.Filter;
import com.xvanop01.isregatta.base.support.model.TableDataResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Mapper(componentModel = "spring")
public abstract class TableDataMapper {

    private static final String DIR_ASC = "ASC";

    public PageRequest map(TableDataRequestDto tableDataRequestDto) throws HttpException {
        if (tableDataRequestDto == null) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "Empty request body.");
        }
        SortDto sortDto = tableDataRequestDto.getSortCriteria();
        if (sortDto == null) {
            sortDto = new SortDto();
            sortDto.setColumn("id");
            sortDto.setDirection(DIR_ASC);
        }
        Sort.Direction direction = DIR_ASC.equals(sortDto.getDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortDto.getColumn());
        return PageRequest.of(tableDataRequestDto.getPageNumber(), tableDataRequestDto.getPageSize(), sort);
    }

    public abstract TableDataResponseDto map(TableDataResponse tableDataResponse);

    public TableDataResponseDto map(Page<?> page) {
        TableDataResponse response = new TableDataResponse();
        response.setData(page.toList());
        response.setTotalItems(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        return map(response);
    }

    public abstract Filter map(FilterDto filterDto);

    public abstract List<Filter> mapFilterList(List<FilterDto> filterDtoList);
}
