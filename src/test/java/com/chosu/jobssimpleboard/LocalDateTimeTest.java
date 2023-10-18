package com.chosu.jobssimpleboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTest {



    @DisplayName("1. localdatetime test")
    @Test
    void test_1(){
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println("시스템로그 []: " + localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")));
    }
}
