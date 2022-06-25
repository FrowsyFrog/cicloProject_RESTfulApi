package com.ciclo.Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ciclo.Dto.CalificacionRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "Calificacion")
public class Calificacion {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalificacion;

    @Column(name="fechaCalificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCalificacion;

    @Column(name="estrellasCalificacion")
    private int estrellasCalificacion;

    @Column(name="descripcionCalificacion")
    private String descripcionCalificacion;
    
    @Column(name = "idCiclovia", nullable = true)
    private Long ciclovia;

    @Column(name = "idParking", nullable = true)
    private Long parking;

    public Calificacion(Ciclovia ciclovia, CalificacionRequestDto calificacionDto) {
        this.fechaCalificacion = new Date();
        this.estrellasCalificacion = calificacionDto.getEstrellasCalificacion();
        this.descripcionCalificacion = calificacionDto.getDescripcionCalificacion();
        this.ciclovia = ciclovia.getIdCiclovia();
    }

    public Calificacion(Parking parking, CalificacionRequestDto calificacionDto) {
        this.fechaCalificacion = new Date();
        this.estrellasCalificacion = calificacionDto.getEstrellasCalificacion();
        this.descripcionCalificacion = calificacionDto.getDescripcionCalificacion();
        this.parking = parking.getId();
    }
}
