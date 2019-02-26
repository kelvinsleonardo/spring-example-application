package br.com.kelvinsantiago.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_estado")
@SequenceGenerator(initialValue = 2,name = "state_seq", allocationSize = 1)
@Getter
@Setter
public class State {

    @Id
    @GeneratedValue(generator = "state_seq")
    private long id;
    private String initials;

}