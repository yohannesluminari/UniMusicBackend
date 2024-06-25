//package it.luminari.UniMuiscBackend.exceptions;
//
//import java.io.IOException;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.time.LocalDateTime;
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice
//public class ExceptionsHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected ResponseEntity<ExceptionResponseDTO> handleException(Exception e) {
//        ExceptionResponseDTO payload = new ExceptionResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
//        ResponseEntity<ExceptionResponseDTO> responseEntity = new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
//        return responseEntity;
//    }
//
//    @ExceptionHandler(IOException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected ResponseEntity<ExceptionResponseDTO> handleIOException(IOException e) {
//        ExceptionResponseDTO payload = new ExceptionResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
//        ResponseEntity<ExceptionResponseDTO> responseEntity = new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
//        return responseEntity;
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected ResponseEntity<ExceptionsListResponseDTO> handleBadRequestException(BadRequestException e) {
//        ExceptionsListResponseDTO payload = new ExceptionsListResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now(), e.getExceptionsList());
//        ResponseEntity<ExceptionsListResponseDTO> responseEntity = new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
//        return responseEntity;
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    protected ResponseEntity<ExceptionResponseDTO> handleNotFoundException(NotFoundException e) {
//        ExceptionResponseDTO payload = new ExceptionResponseDTO(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
//        ResponseEntity<ExceptionResponseDTO> responseEntity = new ResponseEntity<>(payload, HttpStatus.NOT_FOUND);
//        return responseEntity;
//    }
//
//    @ExceptionHandler(NoContentException.class)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    protected ResponseEntity<ExceptionResponseDTO> handleNoContentException(NoContentException e) {
//        ExceptionResponseDTO payload = new ExceptionResponseDTO(e.getMessage(), HttpStatus.NO_CONTENT, LocalDateTime.now());
//        ResponseEntity<ExceptionResponseDTO> responseEntity = new ResponseEntity<>(payload, HttpStatus.NO_CONTENT);
//        return responseEntity;
//    }
//
//    @ExceptionHandler(UnauthorizedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    protected ResponseEntity<ExceptionResponseDTO> handleUnauthorizedException(UnauthorizedException e) {
//        ExceptionResponseDTO payload = new ExceptionResponseDTO(e.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now());
//        ResponseEntity<ExceptionResponseDTO> responseEntity = new ResponseEntity<>(payload, HttpStatus.UNAUTHORIZED);
//        return responseEntity;
//    }
//
//    @ExceptionHandler(FileSizeExceededException.class)
//    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
//    protected ResponseEntity<ExceptionResponseDTO> handleFileSizeExceededDimension(FileSizeExceededException e) {
//        ExceptionResponseDTO payload = new ExceptionResponseDTO(e.getMessage(), HttpStatus.PAYLOAD_TOO_LARGE, LocalDateTime.now());
//        ResponseEntity<ExceptionResponseDTO> responseEntity = new ResponseEntity<>(payload, HttpStatus.PAYLOAD_TOO_LARGE);
//        return responseEntity;
//    }
//}
