package com.otus.finalproject.registryapp.domain.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class JwtResponse implements Serializable {
    private final String jwtToken;
}
