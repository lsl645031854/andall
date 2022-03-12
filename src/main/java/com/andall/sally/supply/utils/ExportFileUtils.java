package com.andall.sally.supply.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;

/**
 * @author xubangbang
 * @date 2021/7/19 20:02
 */
public class ExportFileUtils {


    /**
     *
     * @param outputStream
     * @return
     */
    public static ResponseEntity export(ByteArrayOutputStream outputStream) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + System.currentTimeMillis() + ".xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel;charset=utf-8");
        return new ResponseEntity(outputStream.toByteArray(), headers, HttpStatus.OK);
    }


}
