package _5.IvanHernandez_20240775.Models.ApiResponse;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter @Setter
public class ApiResponse<T> {
 //Creacion de una clase llamada ApiResponse
    private boolean status;

    private String message;

    private T data;

    //Creamos un Constructor de nuestra clase para poder utilizarlo en nuestro controller
    public ApiResponse(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
