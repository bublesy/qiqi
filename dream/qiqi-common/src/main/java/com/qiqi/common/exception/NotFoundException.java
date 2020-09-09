package com.qiqi.common.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QiQiDream
 * @date 2020-06-24 15:09
 */

@RestController
@RequestMapping("/error")
public class NotFoundException {

    @GetMapping("")
    public String notFound() {
        return "接口不存在";
    }
}
