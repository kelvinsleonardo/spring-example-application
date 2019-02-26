package br.com.kelvinsantiago.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_telephone")
@SequenceGenerator(initialValue = 2,name = "telephone_seq", allocationSize = 1)
@Getter
@Setter
public class Telephone {

    @Id
    @GeneratedValue(generator = "telephone_seq")
    private long id;
    private String number;
    private String ddd;

}