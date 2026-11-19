package com.empresa.motores;

import com.empresa.motores.MotorLectura;
import com.empresa.motores.MotorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/motores")
public class MotorController {

    @Autowired
    private MotorRepository repository;

    // 1. Registrar una lectura (para cualquier cantidad de motores)
    @PostMapping
    public ResponseEntity<MotorLectura> registrarLectura(@Valid @RequestBody MotorLectura lectura) {
        MotorLectura guardada = repository.save(lectura);
        return ResponseEntity.ok(guardada);
    }

    // 2. Consultar tendencia completa de un motor específico
    @GetMapping("/tendencia/{nombreMotor}")
    public ResponseEntity<List<MotorLectura>> obtenerTendencia(@PathVariable String nombreMotor) {
        return ResponseEntity.ok(repository.findByNombreMotorOrderByFechaRegistroAsc(nombreMotor));
    }

    // 3. Consultar tendencia filtrada por rango de fechas
    @GetMapping("/tendencia/{nombreMotor}/fechas")
    public ResponseEntity<List<MotorLectura>> obtenerTendenciaPorFechas(
            @PathVariable String nombreMotor,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(
            repository.findByNombreMotorAndFechaRegistroBetweenOrderByFechaRegistroAsc(nombreMotor, inicio, fin)
        );
    }

    // Capturar errores de validación (ej. números negativos) y devolver JSON limpio
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }
}
