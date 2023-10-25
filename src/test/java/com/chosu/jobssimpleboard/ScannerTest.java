package com.chosu.jobssimpleboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class ScannerTest {




    @DisplayName("1. ")
    @Test
    void test_1(){
        Scanner scanner = new Scanner(System.in);
        String test = scanner.next();
        System.out.println("시스템로그 []: " + test);
    }
}
