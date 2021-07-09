package com.andall.sally.supply.controller;

import com.andall.sally.supply.req.SearchReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 2:07 下午 2021/3/31
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @PostMapping("/upload")
    @ResponseBody
    public String upload(SearchReq req,
                         @RequestParam(value = "data", required = false) List<MultipartFile> files) {

        log.info("req -----> {}", req );
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
        }
        return "success";
    }

}
