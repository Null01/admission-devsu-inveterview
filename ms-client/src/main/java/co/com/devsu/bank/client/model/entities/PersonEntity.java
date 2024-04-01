package co.com.devsu.bank.client.model.entities;

import co.com.devsu.bank.client.constants.type.GenderType;
import co.com.devsu.bank.client.constants.type.IdentificationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "person", schema = "clients")
@EntityListeners(AuditingEntityListener.class)
public class PersonEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "phone", unique = true)
    private BigInteger phone;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Basic
    @Column(name = "birth_date")
    private Date birthDate;
    @Basic
    @Column(name = "identification_number")
    private String identificationNumber;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "identification_type")
    private IdentificationType identificationType;

    @JsonIgnore
    @OneToOne(mappedBy = "person")
    private ClientEntity client;

    @LastModifiedDate
    @Basic
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
