package com.example.springjwt.common.login.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {
    private String id;
    private String password;
}
