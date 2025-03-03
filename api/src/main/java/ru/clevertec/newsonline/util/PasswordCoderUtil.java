package ru.clevertec.newsonline.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCoderUtil {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder()
                .encode("password"));
    }
}
