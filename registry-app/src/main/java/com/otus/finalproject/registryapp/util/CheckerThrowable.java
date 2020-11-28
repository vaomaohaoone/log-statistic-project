package com.otus.finalproject.registryapp.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckerThrowable {

    public static void checkThrowable(Throwable throwable) {
        if (throwable.getCause() != null)
            log.error(throwable.getMessage());
    }
}
