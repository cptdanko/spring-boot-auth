package com.mydaytodo.auth.methods.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private final String jwttoken;

}
