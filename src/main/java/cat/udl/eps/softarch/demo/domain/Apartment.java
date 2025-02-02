package cat.udl.eps.softarch.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "apartment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Apartment extends UriEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    private int floor;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String description;
    private ZonedDateTime registrationDate;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Owner owner;

    @OneToOne
    @PrimaryKeyJoinColumn
    private ApartmentDetails detail;
}
