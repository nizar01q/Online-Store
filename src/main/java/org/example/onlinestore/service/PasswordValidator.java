package org.example.onlinestore.service;
import java.util.regex.Pattern;

public class PasswordValidator {
    String lengthPattern = ".{8,}";
    String upperCasePattern = ".*[A-Z].*";
    String lowerCasePattern = ".*[a-z].*";
    String digitPattern = ".*[0-9].*";
    String specialCharPattern = ".*[!@#$%^&*].*";

    public boolean validatePassword(String password){
        if (!Pattern.matches(lengthPattern, password)){
            System.out.println("Password must be at least 8 characters long");
            return false;
        }
        if(!Pattern.matches(upperCasePattern, password)){
            System.out.println("Password must contain at least one uppercase letter");
            return false;
        }
        if(!Pattern.matches(lowerCasePattern, password)){
            System.out.println("Password must contain at least one lowercase letter");
            return false;
        }
        if(!Pattern.matches(digitPattern, password)){
            System.out.println("Password must contain at least one number");
            return false;
        }
        if(Pattern.matches(specialCharPattern, password)){
            System.out.println("Password must contain at least one special character");
            return false;
        }
    return true;
    }
}
