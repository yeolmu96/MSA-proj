package com.msa.account.utility;

public class TokenUtility {

    public static String extractToken(String token){
        if(token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }

        return token;
    }
}
