package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {

    @NotBlank(message = "Rua inválida! Por favor, insira a rua do endereço.")
    private String street;

    private String number;

    @NotBlank(message = "CEP inválido! Por favor, insira o CEP do endereço.")
    private String postalCode;

    @NotBlank(message = "Bairro inválido! Por favor, insira o bairro do endereço.")
    private String neighborhood;

    @NotBlank(message = "Cidade Inválida! Por favor, insira a cidade do endereço.")
    private String city;

    @NotBlank(message = "Estado inválido! Por favor, insira o estado do endereço.")
    private String state;


}
