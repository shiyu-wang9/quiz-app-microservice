package com.shiyu.quiz_service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor // what's this
public class Response {
    private Integer id;
    private String response;
}
