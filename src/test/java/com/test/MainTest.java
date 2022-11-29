package com.test;

import book.SQLUtils;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void test1(){
        SQLUtils.doSqlWork(bookMapper -> {
            bookMapper.getBorrowList().forEach(System.out::println);
        });
    }
}
