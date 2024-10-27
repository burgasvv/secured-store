package org.burgas.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(
            mappedBy = "authority",
            cascade = {MERGE, PERSIST}
    )
    private List<Identity>identities = new ArrayList<>();
}
