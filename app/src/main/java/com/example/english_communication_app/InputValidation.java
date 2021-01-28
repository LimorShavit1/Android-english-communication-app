package com.example.english_communication_app;

/**
 * Class check user inputs
 */
public class InputValidation {
    // name not empty, only english characters
    public static boolean isNameValid(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    // phone only numbers, start with "05", and length of 10
    public static boolean isPhoneValid(String phone) {
        return (phone.length() == 10 && phone.matches("05[0-9]*"));
    }

    //email validation
    public static boolean isEmailValid(String email) {
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    }

}
