package com.hashedin.product.kyeazy.exceptions.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExceptionResponse
{

        private int status;
        private String message;
        private long timeStamp;
}


