package com.msa.account.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptionUtility {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encode(String rawPassword){
        return encoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword){
        return encoder.matches(rawPassword, encodedPassword);
    }
}
