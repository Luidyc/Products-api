package br.myself.productms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDTO {


    @NotBlank
    private String name;

    @Size(min = 50)
    private String description;

    @Positive
    private double price;
    private boolean available;


}
