package com.empresa.motores;

import com.empresa.motores.MotorLectura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MotorRepository extends JpaRepository<MotorLectura, Long> {
    
    // Obtener tendencia histórica de un motor ordenada por fecha
    List<MotorLectura> findByNombreMotorOrderByFechaRegistroAsc(String nombreMotor);

    // Obtener tendencia por motor en un rango de fechas específico
    List<MotorLectura> findByNombreMotorAndFechaRegistroBetweenOrderByFechaRegistroAsc(
            String nombreMotor, LocalDateTime inicio, LocalDateTime fin);
}

