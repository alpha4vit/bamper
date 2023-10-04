package by.gurinovich.bamper.models.carsEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "car_model_generations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarModelGeneration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "generation_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "car_model_id", referencedColumnName = "id")
    private CarModel carModel;

    @Column(name = "start_year_of_prod")
    private Date startYearOfProduction;

    @Column(name = "last_year_of_prod")
    private Date endYearOfProduction;

    public CarModelGeneration(CarModel carModel, String name, Date startYearOfProduction, Date endYearOfProduction) {
        this.carModel = carModel;
        this.name = name;
        this.startYearOfProduction = startYearOfProduction;
        this.endYearOfProduction = endYearOfProduction;
    }

    public CarModelGeneration(Integer id, String name, Date startYearOfProduction, Date endYearOfProduction) {
        this.id = id;
        this.name = name;
        this.startYearOfProduction = startYearOfProduction;
        this.endYearOfProduction = endYearOfProduction;
    }
}

