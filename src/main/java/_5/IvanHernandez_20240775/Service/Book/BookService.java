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

@Slf4j
@Service
public class BookService {

    @Autowired
    BookRepository accesBookRepository; //Sobrescrivimos la interfaz bookRepository y creamos un objeto para acceder a todos los metodos de JPARepository

    public List<BookDTO> GetAllListbook(){

        List<BookEntity> book = accesBookRepository.findAll(); //Creamos una lista de tipo entity que se llenara

        return  book.stream()
                .map(this::ConvertBookToDTO).
                collect(Collectors.toList());
    }

    public BookDTO InsertNewBook(BookDTO bookDTO){
        if(bookDTO.getTitle().trim().isEmpty() || bookDTO.getIsbn().trim().isEmpty() || bookDTO.getAuthorid() == null){
            throw  new IllegalArgumentException("El titulo, ISBN, ID del autor no deben de ir vacios o nulos");
        }
        try {
            BookEntity NewBook = ConvertBookToEntity(bookDTO);
            BookEntity savedBook = accesBookRepository.save(NewBook);
            return  ConvertBookToDTO(savedBook);
        }catch (Exception e){
            log.error("Error al registra el libro" + e.getMessage());
            throw new IllegalArgumentException("Error al registrar el usuario"+ e.getMessage());
        }
    }

    public BookDTO UpdatedBook(Long id_book, BookDTO bookDTO){
        try {
            BookEntity existingBook = accesBookRepository.findById(id_book).orElseThrow(()-> new RuntimeException("Libro con ID no encontrado" + id_book));


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
            if(accesBookRepository.existsById(Id_Book)){
                accesBookRepository.deleteById(Id_Book);
                log.info("El libro a sido eliminado exitosamente: "+ Id_Book);
                return  true;
            }else {
                log.warn("libro no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            log.error("Error Al eliminar el Libro" + e.getMessage());
            throw  new EmptyResultDataAccessException("Error al Eliminar el Libro", +1);
        }
    }

    public BookDTO getBookByID(Long id_Book){
        Optional<BookEntity> BookOptional = accesBookRepository.findById(id_Book);

        if(BookOptional.isPresent()){
            return  ConvertBookToDTO(BookOptional.get());
        }else{
            throw new EntityNotFoundException("No se encontro el Libro con ID" +id_Book + "para eliminar");
        }
    }



    //Metodo para convertir el Book en formato Entity a DTO para poder enviarlo a nuestro Controlador

    private BookDTO ConvertBookToDTO(BookEntity bookEntity){
        BookDTO dto = new BookDTO();

        dto.setId(bookEntity.getId());
        dto.setTitle(bookEntity.getTitle());
        dto.setIsbn(bookEntity.getIsbn());
        dto.setGender(bookEntity.getGender());
        dto.setYearpublication(bookEntity.getYearpublication());
        dto.setAuthorid(bookEntity.getAuthorid());

        return  dto;
    }

    //Metodo para convertir el Book en formato DTo a Entity para poder enviarlo a la Base de Datos
    private BookEntity ConvertBookToEntity(BookDTO dto){
        BookEntity bookEntity = new BookEntity();

        bookEntity.setTitle(dto.getTitle());
        bookEntity.setIsbn(dto.getIsbn());
        bookEntity.setGender(dto.getGender());
        bookEntity.setYearpublication(dto.getYearpublication());
        bookEntity.setAuthorid(dto.getAuthorid());

        return  bookEntity;
    }



}
