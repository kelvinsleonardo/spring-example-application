package br.com.kelvinsantiago.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_municipio")
@Getter
@Setter
@SequenceGenerator(initialValue = 2,name = "city_seq", allocationSize = 1)
public class City {

    @Id
    @GeneratedValue(generator = "city_seq")
    private long id;
    private String name;
    @OneToOne
    private State state;
}