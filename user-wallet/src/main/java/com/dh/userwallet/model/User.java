package com.dh.userwallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String cvu;
    private String alias;

}
