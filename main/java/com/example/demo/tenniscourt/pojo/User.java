package com.example.demo.tenniscourt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

    private int id;

    private String name;

    private String password;
    private int leftcost;

}
