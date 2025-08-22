package _5.IvanHernandez_20240775.Controller.Book;

import _5.IvanHernandez_20240775.Models.ApiResponse.ApiResponse;
import _5.IvanHernandez_20240775.Models.DTO.BookDTO;
import _5.IvanHernandez_20240775.Service.Book.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ActionsBook")
public class BookController {

    @Autowired
    BookService accesBookService;

    @GetMapping("/GetAllBook")
    public List<BookDTO> getAllBooks(){
        return accesBookService.GetAllListbook();
    }
    @GetMapping("/SearchBook/{id_Book}")
    public ResponseEntity<ApiResponse<BookDTO>> GetBookByID(@PathVariable("id_Book") Long id_Book){
        try {
            BookDTO bookData = accesBookService.getBookByID(id_Book);
            ApiResponse<BookDTO> succesResponse = new ApiResponse<>(false, "Libro Encontrado:", bookData);
            return  new ResponseEntity<>(succesResponse, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/InsertNewBook")
    public ResponseEntity<ApiResponse<BookDTO>> CreateNewBook(@Valid  @RequestBody BookDTO bookDTO){
        try {
            BookDTO newBook = accesBookService.InsertNewBook(bookDTO);
            ApiResponse<BookDTO> succesResponse = new ApiResponse<>(false, "Libro Registrado Exitosamente", newBook);
            return  new ResponseEntity<>(succesResponse, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/UpdateBok/{id_Book}")
    public ResponseEntity<ApiResponse<BookDTO>> Updatedbook(@PathVariable Long id_Book,  @Valid  @RequestBody BookDTO bookDTO){
        try {
            BookDTO updatedbook  = accesBookService.UpdatedBook(id_Book, bookDTO);
            ApiResponse<BookDTO> succesResponse = new ApiResponse<>(true, "Actualizacion Exitosa", updatedbook);
            return  new ResponseEntity<>(succesResponse, HttpStatus.OK);
        }catch(EntityNotFoundException e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }catch (IllegalArgumentException e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/DeleteBook/{id_Book}")
    public ResponseEntity<ApiResponse<Void>> DeleteBook(@PathVariable Long id_Book){
        try {
            boolean Deletebook = accesBookService.DeleteBook(id_Book);
           if(Deletebook){
               ApiResponse<Void> succesResponse = new ApiResponse<>(true, "Eliminacion Exitosa", null);
               return new ResponseEntity<>(succesResponse, HttpStatus.OK);
           }else{
               ApiResponse<Void> succesResponse = new ApiResponse<>(false, "no se pudo eliminar el libro", null);
               return new ResponseEntity<>(succesResponse, HttpStatus.BAD_REQUEST);
           }
        }catch (Exception e){
            ApiResponse<Void> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
