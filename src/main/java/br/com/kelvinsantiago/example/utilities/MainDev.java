package br.com.kelvinsantiago.example.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainDev {
    public static void main(String[] arg) {
        System.out.println(new BCryptPasswordEncoder().encode("123456a"));
    }
}
