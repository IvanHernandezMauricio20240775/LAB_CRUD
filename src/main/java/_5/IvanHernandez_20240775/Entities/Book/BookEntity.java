package _5.IvanHernandez_20240775.Entities.Book;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@EqualsAndHashCode @ToString
@Table(name = "LIBROS")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_libros_id")
    @SequenceGenerator(name = "SQ_libros_id", sequenceName = "SQ_libros_id", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITULO", nullable = false)
    private String title;

    @Column(name = "ISBN", nullable = false, unique = true)
    private String isbn;

    @Column(name = "AÑO_PUBLICACION")
    private Long yearpublication;

    @Column(name = "GENERO")
    private String gender;

    @Column(name = "AUTOR_ID", nullable = false)
    private Long authorid;

}
