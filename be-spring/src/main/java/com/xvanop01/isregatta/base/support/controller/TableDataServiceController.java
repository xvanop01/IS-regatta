package com.xvanop01.isregatta.base.support.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xvanop01.isregatta.api.base.TableDataServiceApi;
import com.xvanop01.isregatta.api.base.model.TableDataRequestDto;
import com.xvanop01.isregatta.api.base.model.TableDataResponseDto;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.base.support.mapper.TableDataMapper;
import com.xvanop01.isregatta.base.support.model.Filter;
import com.xvanop01.isregatta.base.support.service.TableDataServiceProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class TableDataServiceController implements TableDataServiceApi {

    private final TableDataServiceProvider tableDataServiceProvider;
    private final TableDataMapper tableDataMapper;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<TableDataResponseDto> getTableDataByServiceName(String tableDataRequestDto,
           String serviceName) {
        // TODO log.info()
        try {
            TableDataRequestDto requestDto = objectMapper.readValue(tableDataRequestDto, TableDataRequestDto.class);
            PageRequest pageRequest = tableDataMapper.map(requestDto);
            List<Filter> filterList = tableDataMapper.mapFilterList(requestDto.getFilterCriteria());
            Page<?> page = tableDataServiceProvider.getTableData(serviceName, pageRequest, filterList);
            TableDataResponseDto dto = tableDataMapper.map(page);
            return ResponseEntity.ok(dto);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
