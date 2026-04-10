package kz.enu.hospitalmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Валидация қателерін өңдеу
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex,
                                             RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Бірінші қатені алу
        String firstError = errors.values().iterator().next();
        redirectAttributes.addFlashAttribute("error", firstError);

        return "redirect:/register";
    }

    /**
     * Бизнес логика қателерін өңдеу
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());

        // Қатені консольда көрсету
        System.err.println("Қате: " + ex.getMessage());

        return "redirect:/register";
    }

    /**
     * Барлық басқа қателерді өңдеу
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Жүйелік қате орын алды: " + ex.getMessage());
        ex.printStackTrace();
        return "redirect:/login";
    }
}