package _5.IvanHernandez_20240775.Models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@EqualsAndHashCode @ToString
public class BookDTO {

    @JsonProperty("ID_BOOK")
    @Positive
    private Long id;

    @JsonProperty("TITULO")
    @NotBlank(message = "El titulo no debe de ir vacio") //Validamos que no vaya vacio
    @NotEmpty//Validamos que no vaya vacio
    @Size(min = 1, max = 200)//Validamos que tenga un minimo de caracter
    private String title;

    @JsonProperty("ISBN")
    @NotBlank(message = "El ISBN No debe de ir vacio")
    @Size(min = 17, max = 20, message = "El codigo normalizado internacional para libros debe de tener maximo 20 y minimo 17 caracteres ")
    private String isbn;

    @JsonProperty("AÑO_PUBLICACION")
    @Positive(message = "El año debe de ser positivo")
    private Long yearpublication;


    @JsonProperty("GENERO")
    private String gender;

    @JsonProperty("AUTOR_ID")
    @Positive(message = "El ID del autor debe de ser un numero positivo")
    @NotNull(message = "El ID del autor no debe de ser nulo")
    private Long authorid;
}
