package _5.IvanHernandez_20240775.Service.Book;

import _5.IvanHernandez_20240775.Entities.Book.BookEntity;
import _5.IvanHernandez_20240775.Models.DTO.BookDTO;
import _5.IvanHernandez_20240775.Repositories.Book.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j //para obtener accesso a los log.
@Service //le indicamos que esta clase se comportara como un service
public class BookService {

    @Autowired
    BookRepository accesBookRepository; //Sobrescrivimos la interfaz bookRepository y creamos un objeto para acceder a todos los metodos de JPARepository

    public List<BookDTO> GetAllListbook(){

        List<BookEntity> book = accesBookRepository.findAll(); //Creamos una lista de tipo entity que se llenara con los datos de la base

        return  book.stream()
                .map(this::ConvertBookToDTO). //convertimos esa lista entity a DTO y llena para retornarlo al controller
                collect(Collectors.toList());
    }

    public BookDTO InsertNewBook(BookDTO bookDTO){
        if(bookDTO.getTitle().trim().isEmpty() || bookDTO.getIsbn().trim().isEmpty() || bookDTO.getAuthorid() == null){
            throw  new IllegalArgumentException("El titulo, ISBN, ID del autor no deben de ir vacios o nulos");
        }
        try {
            BookEntity NewBook = ConvertBookToEntity(bookDTO); //Creamos un objeto de tipo Entity el cual tendra el contenido del JSON en formato Entity
            BookEntity savedBook = accesBookRepository.save(NewBook); //Creamos un objteo de tipo entity que el cual tendra todos los datos una vez insertados y insertamos el objeto que anteriormente creamos
            return  ConvertBookToDTO(savedBook); //Ese objeto una vez insertado lo retornamos pero como DTO es por eso que ocupamos el Evento ConvertToDTO
        }catch (Exception e){
            log.error("Error al registra el libro" + e.getMessage());
            throw new IllegalArgumentException("Error al registrar el usuario"+ e.getMessage());
        }
    }

    public BookDTO UpdatedBook(Long id_book, BookDTO bookDTO){
        try {
            BookEntity existingBook = accesBookRepository.findById(id_book).orElseThrow(()-> new RuntimeException("Libro con ID no encontrado" + id_book));
            //creamos un nuevo objeto de tipo Entity existingBook el cual validara si existe ese Libro antes de mandarlo a actualizar si no existe activara automaticamente la alerta RuntimeException

            existingBook.setTitle(bookDTO.getTitle());
            existingBook.setIsbn(bookDTO.getIsbn());
            existingBook.setGender(bookDTO.getGender());
            existingBook.setYearpublication(bookDTO.getYearpublication());
            existingBook.setAuthorid(bookDTO.getAuthorid());

            BookEntity updatingBook = accesBookRepository.save(existingBook);

            return  ConvertBookToDTO(updatingBook);

        }catch (Exception e){
            log.error("Error al actualizar el libro " + e.getMessage());
            throw new IllegalArgumentException("Error al actualizar el libro" + e.getMessage());
        }
    }

    public boolean DeleteBook(Long Id_Book){
        try {
            //primero verificamos si existe el ID de ese Libro antes de mandarlo a eliminar
            if(accesBookRepository.existsById(Id_Book)){
                accesBookRepository.deleteById(Id_Book); //si existe eliminara el libro y si lo logro retornara un mensaje de que se elimino con exito y un true
                log.info("El libro a sido eliminado exitosamente: "+ Id_Book);
                return  true;
            }else {
                log.warn("libro no encontrado"); //si no encontro el registro lanzara este mensaje
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            log.error("Error Al eliminar el Libro" + e.getMessage()); //si ocurrio un error que no pudimos controllar lanzara este errorr
            throw  new EmptyResultDataAccessException("Error al Eliminar el Libro", +1);
        }
    }

    public BookDTO getBookByID(Long id_Book){
        Optional<BookEntity> BookOptional = accesBookRepository.findById(id_Book);
        //Creamos un objeto de tipo optional llamado BookOptional para poder obtener acceso al evento .isPresent y validar si esta presente ese registro en la base de datos
        if(BookOptional.isPresent()){
            return  ConvertBookToDTO(BookOptional.get()); //retornamos ese objeto con BookOptional.get convertidpo a DTO ya que era en formato entity
        }else{
            throw new EntityNotFoundException("No se encontro el Libro con ID" +id_Book + "para eliminar");
        }
    }



    //Metodo para convertir el Book en formato Entity a DTO para poder enviarlo a nuestro Controlador
    private BookDTO ConvertBookToDTO(BookEntity bookEntity){
        BookDTO dto = new BookDTO(); //Creamos un Objeto de tipo DTO

        //Rellenamos todos los campos
        dto.setId(bookEntity.getId());
        dto.setTitle(bookEntity.getTitle());
        dto.setIsbn(bookEntity.getIsbn());
        dto.setGender(bookEntity.getGender());
        dto.setYearpublication(bookEntity.getYearpublication());
        dto.setAuthorid(bookEntity.getAuthorid());

        return  dto; //retornamos todos los campos ya llenos con valores que nos dio la base y asi es como convertimos un entity a DTO
    }

    //Metodo para convertir el Book en formato DTo a Entity para poder enviarlo a la Base de Datos
    private BookEntity ConvertBookToEntity(BookDTO dto){
        BookEntity bookEntity = new BookEntity(); //Creamos un Objeto de tipo Entity

        //Rellenamos todos los campos Entity con los valors que nos dio el JSON
        bookEntity.setTitle(dto.getTitle());
        bookEntity.setIsbn(dto.getIsbn());
        bookEntity.setGender(dto.getGender());
        bookEntity.setYearpublication(dto.getYearpublication());
        bookEntity.setAuthorid(dto.getAuthorid());

        return  bookEntity; //retornamos el objeto entity ya lleno para poder insertarlo en la base
    }



}
