package br.com.kelvinsantiago.example.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GenerateUtils {

    private static final Set<Integer> IGNORED_CARACTERES = new HashSet<>(Arrays.asList(48, 49, 73, 76, 79, 105, 108, 111));

    public static String encodeBCrypt(String code){
        return new BCryptPasswordEncoder().encode(code);
    }

    public static String generateToken(){
        int numCaracteres = 8;

        StringBuilder code = new StringBuilder(numCaracteres);

        java.util.Random r = new Random();

        for (int i = 0; i < numCaracteres; i++) {
            int randon = r.nextInt(100);

            int codeCarac;

            if (randon < 3) {
                codeCarac = 48 + r.nextInt(10);

            } else if (randon < 6) {
                codeCarac = 65 + r.nextInt(25);

            } else {
                codeCarac = 97 + r.nextInt(25);
            }

            if (IGNORED_CARACTERES.contains(codeCarac)) {
                codeCarac += 2;
            }
            code.append((char) codeCarac);
        }

        return code.toString();
    }
}
