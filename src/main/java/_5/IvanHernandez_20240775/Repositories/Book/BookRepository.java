package _5.IvanHernandez_20240775.Repositories.Book;

import _5.IvanHernandez_20240775.Entities.Book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Le inidcamos que esta interfaz se comportara como un repositorio
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    //Heredamos a nuestra Interfaz BookRepository todos lso metodos de JpaRepository como son el .save . findAll . finByID, .deleteByID
}
