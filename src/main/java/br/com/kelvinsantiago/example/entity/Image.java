package br.com.kelvinsantiago.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_image")
@Getter
@Setter
@SequenceGenerator(initialValue = 2,name = "image_seq", allocationSize = 1)
public class Image {

    @Id
    @GeneratedValue(generator = "image_seq")
    long id;
    private String nome;
    @Lob
    private byte[] bytes;
    private Integer length;
    private String urlDownload;
}
