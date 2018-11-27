package com.example.xing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiexingxing
 * @Created by 2018-11-25 12:44 AM.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDTO implements Serializable {

    private String username;

    private String password;
}
