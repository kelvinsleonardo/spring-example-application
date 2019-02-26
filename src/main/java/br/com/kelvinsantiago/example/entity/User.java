package br.com.kelvinsantiago.example.entity;

import br.com.kelvinsantiago.example.enums.Situation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
@Getter
@Setter
@SequenceGenerator(initialValue = 2,name = "user_seq", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(generator = "user_seq")
    private long id;
    private String name;
    @Column(updatable = false,unique = true)
    private String cpf;
    @Column(unique = true)
    private String mail;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date birth;
    @Column(updatable = false)
    @Enumerated
    private Situation situation = Situation.ACTIVE;
    @OneToMany
    private List<Telephone> telephones;
    @ManyToOne
    private City city;
    @Column(updatable = false)
    private boolean adm;
    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn
    private Image imagem;

}