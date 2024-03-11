package com.example.springjwt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name="user_info")
public class UserInfoEntity {
    @Id
    @Column(name="USER_ID", updatable = false, unique = true, nullable = false)
    private int USERID;//사용자그룹 ID

    @Column(nullable = false)
    private String PASSWORD;

    @JsonProperty("id")
    @Column(nullable = false, unique = true)
    private String NAME;


}
