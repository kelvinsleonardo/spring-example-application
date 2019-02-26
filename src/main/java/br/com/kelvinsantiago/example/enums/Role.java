package br.com.kelvinsantiago.example.enums;

public enum Role {

    ADMIN,
    USER;

    public String getName() {
        return "ROLE_" + name();
    }
}
