package com.epam.rd.autotasks.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EpamEmailValidation {

    public static boolean validateEpamEmail(String email) {
        return email != null && email.matches("[a-zA-Z]+_[a-zA-Z]+([1-9][0-9]*)?@epam.com");
    }
}





