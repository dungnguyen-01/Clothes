package com.clothes.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class productDTO {

    private String name;
    private String image;
    private Double total;
    private Integer qty;

}
