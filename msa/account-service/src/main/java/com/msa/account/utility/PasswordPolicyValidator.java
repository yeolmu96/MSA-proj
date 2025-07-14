package com.msa.account.utility;
/*
비밀번호 정책
✅ 최소 8자 이상
✅ 영문, 숫자 반드시 포함
✅ 필요시 특수문자 포함 권장
 */
public class PasswordPolicyValidator {

    public static void validate(String password){
        if(password == null || password.length() < 8){
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 합니다.");
        }

        boolean hasLetter = password.matches("[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");

        if(!hasLetter && !hasDigit){
            throw new IllegalArgumentException("비밀번호에는 영문자와 숫자가 모두 포함되어야 합니다.");
        }
    }
}
