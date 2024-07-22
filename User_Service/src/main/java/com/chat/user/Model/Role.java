package com.chat.user.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "role")
public class Role {

    @Id
    private int id;
    private String name;
}
