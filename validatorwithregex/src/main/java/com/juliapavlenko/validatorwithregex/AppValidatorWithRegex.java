package com.juliapavlenko.validatorwithregex;

public class AppValidatorWithRegex {

    public static void main(String[] args) {
        PositiveValidateEmailTestCase();
        NegativeValidateEmailTestCase();
        PositiveIsLessThan100TestCase();
        NegativeIsLessThan100TestCase();
        PositiveIsBiggerThan1450TestCase();
        NegativeIsBiggerThan1450TestCase();
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

    public static void PositiveValidateEmailTestCase(){
        if (validateEmails("asd@yandex.ru,dgh@gmail.com")){
            System.out.println("PositiveValidateEmailTestCase: ok");
        } else {
            System.out.println("PositiveValidateEmailTestCase: not ok");
        }
    }
    public static void NegativeValidateEmailTestCase(){
        if (validateEmails("asd@mail.ru,dgh@gmail.com")){
            System.out.println("NegativeValidateEmailTestCase: not ok");
        } else {
            System.out.println("NegativeValidateEmailTestCase: ok");
        }
    }
    public static void PositiveIsLessThan100TestCase(){
        if (isLessThan100("90")){
            System.out.println("PositiveIsLessThan100TestCase: ok");
        } else {
            System.out.println("PositiveIsLessThan100TestCase: not ok");
        }
    }
    public static void NegativeIsLessThan100TestCase(){
        if (isLessThan100("101")){
            System.out.println("NegativeIsLessThan100TestCase: not ok");
        } else {
            System.out.println("NegativeIsLessThan100TestCase: ok");
        }
    }
    public static void PositiveIsBiggerThan1450TestCase(){
        if (isBiggerThan1450("1450")){
            System.out.println("PositiveIsBiggerThan1450TestCase: ok");
        } else {
            System.out.println("PositiveIsBiggerThan1450TestCase: not ok");
        }
    }
    public static void NegativeIsBiggerThan1450TestCase(){
        if (isBiggerThan1450("1449")){
            System.out.println("NegativeIsBiggerThan1450TestCase: not ok");
        } else {
            System.out.println("NegativeIsBiggerThan1450TestCase: ok");
        }
    }

}