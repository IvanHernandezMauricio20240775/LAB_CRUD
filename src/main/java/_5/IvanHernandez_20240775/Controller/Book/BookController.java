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

    @Autowired //Inyectamos
    BookService accesBookService; //Creamos un Objeto de tipo BookSevice para obtener acceso a todos los metodos de nuestra clase Book nService

    //metodo GET
    @GetMapping("/GetAllBook")
    public List<BookDTO> getAllBooks(){
        return accesBookService.GetAllListbook(); //retornaremos lo que nos retorne nuestro metodo GetAllListbook para postMan
    }
    @GetMapping("/SearchBook/{id_Book}")
    public ResponseEntity<ApiResponse<BookDTO>> GetBookByID(@PathVariable("id_Book") Long id_Book){
        try {
            BookDTO bookData = accesBookService.getBookByID(id_Book);
            ApiResponse<BookDTO> succesResponse = new ApiResponse<>(false, "Libro Encontrado:", bookData);
            return  new ResponseEntity<>(succesResponse, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); //En caso de no encontrarse el ID  que buscabas lanzaremos ese estado
        }catch (Exception e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/InsertNewBook")
    public ResponseEntity<ApiResponse<BookDTO>> CreateNewBook(@Valid  @RequestBody BookDTO bookDTO){
        try {
            BookDTO newBook = accesBookService.InsertNewBook(bookDTO);
            ApiResponse<BookDTO> succesResponse = new ApiResponse<>(false, "Libro Registrado Exitosamente", newBook);  //enviamos valores a nuestro constructor de la calse Apiresponse
            return  new ResponseEntity<>(succesResponse, HttpStatus.CREATED); //en caso de hacer la insercion correctamente retormnamos un estado de 201
        }catch(IllegalArgumentException e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);//En caso de  hacer un BadRequest osea enviar mal los datos
        }catch (Exception e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);//En caso de obtener un error que no podemos controlar y sea interno del servidor
        }
    }


    @PutMapping("/UpdateBok/{id_Book}") //enviamos junto con el endpoint debera de rellenarse un parametro por eso colocamos @Pathvariable porque el id que coloquemos en nuestro endpoint llegara a caer a ese pathVariable este debe ede llamarse igual que en el EndPoint
    public ResponseEntity<ApiResponse<BookDTO>> Updatedbook(@PathVariable Long id_Book,  @Valid  @RequestBody BookDTO bookDTO){
        try {
            BookDTO updatedbook  = accesBookService.UpdatedBook(id_Book, bookDTO);
            ApiResponse<BookDTO> succesResponse = new ApiResponse<>(true, "Actualizacion Exitosa", updatedbook);
            return  new ResponseEntity<>(succesResponse, HttpStatus.OK); //En caso de una actualizacion exitosa retornamos un OK osea codigo 200
        }catch(EntityNotFoundException e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); //en caso de no encontrarse un usuario mandamos un codigo NOT FOUND
        }catch (IllegalArgumentException e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); //en caso de no haber hecho bien el envio del JSON retornamos un estaod de BAD_REQUEST
        }catch (Exception e){
            ApiResponse<BookDTO> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); //En caso de no poder manejar el Error y sea interno del servidorr
        }
    }

    @DeleteMapping("/DeleteBook/{id_Book}")
    public ResponseEntity<ApiResponse<Void>> DeleteBook(@PathVariable Long id_Book){
        try {
            boolean Deletebook = accesBookService.DeleteBook(id_Book);
           if(Deletebook){
               ApiResponse<Void> succesResponse = new ApiResponse<>(true, "Eliminacion Exitosa", null);
               return new ResponseEntity<>(succesResponse, HttpStatus.OK); //en caso la eliminacion sea exitosa
           }else{
               ApiResponse<Void> succesResponse = new ApiResponse<>(false, "no se pudo eliminar el libro", null);
               return new ResponseEntity<>(succesResponse, HttpStatus.BAD_REQUEST); //en caso hagamos mal la soliccitud
           }
        }catch (Exception e){
            ApiResponse<Void> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); //en caso de no poder manejar el errorr
        }
    }


}
