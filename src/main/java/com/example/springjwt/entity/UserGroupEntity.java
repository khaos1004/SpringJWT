package com.example.springjwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "usergroup")
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupEntity {
    @Id
    @Column(name = "USERGROUP_ID")
    private String userGroupId;

    @Column(name = "NAME")
    private String name;

}
