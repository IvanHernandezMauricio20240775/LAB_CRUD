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
    @NotBlank(message = "El titulo no debe de ir vacio")
    @NotEmpty
    @Size(min = 1)
    private String title;

    @JsonProperty("ISBN")
    @NotBlank(message = "El ISBN No debe de ir vacio")
    private String isbn;

    @JsonProperty("AÑO_PUBLICACION")
    @Positive(message = "El año debe de ser positivo")
    @Min(value = 4, message = "El año debe de contener 4 digitos")
    @Max(value = 4, message = "El Año debe de llevar maximo 4 digitos")
    private Long yearpublication;


    @JsonProperty("GENERO")
    private String gender;

    @JsonProperty("AUTOR_ID")
    @Positive(message = "El ID del autor debe de ser un numero positivo")
    @NotNull(message = "El ID del autor no debe de ser nulo")
    private Long authorid;
}
