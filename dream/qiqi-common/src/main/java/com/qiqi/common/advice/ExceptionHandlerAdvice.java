package com.qiqi.common.advice;

import com.alibaba.fastjson.JSONException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author QiQiDream
 * @date 2020-06-24 10:51
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    /**
     * 处理自定义异常（RuntimeException异常！）
     */
    @ExceptionHandler(value =RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(RuntimeException e){
        log.error("自定义异常！",e);
        return e.getMessage();
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！",e);
        return "空指针异常";
    }

    /**
     * FastJSON序列化异常
     */
    @ExceptionHandler(value = JSONException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(JSONException e){
        log.error("FastJSON序列化异常！",e);
        return "FastJSON序列化异常";
    }


    /**
     * 错误的SQL语法异常
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(BadSqlGrammarException e){
        log.error("错误的SQL语法异常！",e);
        return "错误的SQL语法异常!";
    }

    /**
     * 请求方法异常
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("不支持请求方法\""+ex.getMethod() + "\"");
        return ResponseEntity.status(status).body( "不支持请求方法\""+ex.getMethod() + "\"" );
    }

    /**
     * 处理其他异常（服务器异常！）
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Exception e){
        log.error("服务器异常！",e);
        return "服务器异常！";
    }
}
