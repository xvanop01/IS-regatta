package com.xvanop01.isregatta.base.support.controller;

import com.xvanop01.isregatta.api.base.TableDataServiceControllerApi;
import com.xvanop01.isregatta.api.base.model.TableDataRequestDto;
import com.xvanop01.isregatta.api.base.model.TableDataResponseDto;
import com.xvanop01.isregatta.base.support.mapper.TableDataMapper;
import com.xvanop01.isregatta.base.support.service.TableDataServiceProvider;
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
public class TableDataServiceController implements TableDataServiceControllerApi {

    private final TableDataServiceProvider tableDataServiceProvider;
    private final TableDataMapper tableDataMapper;

    @Override
    public ResponseEntity<TableDataResponseDto> getTableDataByServiceName(String serviceName,
           TableDataRequestDto tableDataRequestDto) {
        // TODO log.info()
        PageRequest pageRequest = tableDataMapper.map(tableDataRequestDto);
        Page<?> page = tableDataServiceProvider.getTableData(serviceName, pageRequest);
        TableDataResponseDto dto = tableDataMapper.map(page);
        return ResponseEntity.ok(dto);
    }
}
