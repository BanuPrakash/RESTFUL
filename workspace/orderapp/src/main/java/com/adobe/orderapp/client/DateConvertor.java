package com.adobe.orderapp.client;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

//@Component
public class DateConvertor extends AbstractHttpMessageConverter<Date> {
    @Override
    protected boolean supports(Class<?> clazz) {
        return false;
    }

    // takes String from QueryParam / request body
    @Override
    protected Date readInternal(Class<? extends Date> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    // takes Date and write as String format
    @Override
    protected void writeInternal(Date date, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
