package com.juliapavlenko.validatorwithregex;

public class AppValidatorWithRegex {

    public static void main(String[] args) {
        System.out.println(validateEmails("asd@yandex.ru,dgh@gmail.com"));
        System.out.println(isLessThan100("Amount: 100"));
        System.out.println(isBiggerThan1450("Amount: 1450"));
    }

    public static boolean validateEmails(String data) {
        return data.matches("^(?:(?:[a-zA-Z-0-9]+(?:@gmail.com|@yandex.ru)),?)+$");
    }

    public static boolean isLessThan100(String data) {
        return data.matches("^(?:Amount: )?([0-9]{1,2}|100)(\\.[0-9]{1,2})?$");
    }

    public static boolean isBiggerThan1450(String data) {
        return data.matches("^(?:Amount: )?(?:(?:14[5-9]\\d)|(?:1[5-9]\\d\\d)|(?:[2-9]\\d\\d\\d)|(?:\\d\\d\\d\\d\\d+))(?:\\.[0-9]{1,2})?$");
    }

}