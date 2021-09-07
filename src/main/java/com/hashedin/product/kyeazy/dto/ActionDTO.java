package com.hashedin.product.kyeazy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActionDTO {
    private Integer id;
    private boolean success;
    private String message;

}
