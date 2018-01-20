package com.juliapavlenko.replacer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class AppReplacer {
    private static String HILLEL_WORD = "hillel";
    private static String HILLEL_IS_GOOD_WORDS = "hillel is good";
    private static String GOOD_WORD = "good";
    private static char[] PUNCTUATION_MARKS = {';', ';', '.', '.', ',', '!', '?'};


    public static void main(String[] args) {
        AppReplacer replacer = new AppReplacer();
        System.out.println(replacer.replaceWords("hillel school of programming. I am working in hillel. hillel is bad."));
    }

    public String replaceWords(String text){
        String finishedText = addSpacesBeforePunctuationMarks(text);
        List<String> list = Arrays.asList(finishedText.split(" "));
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i<list.size(); i++) {
            if (list.get(i).equals(HILLEL_WORD)
                && list.size() > (i + 2)
                && list.get(i + 1).equals("is")
                && !(Pattern.matches("\\p{Punct}", list.get(i + 2)))
               ) {
                list.set(i + 2, GOOD_WORD);
            }
            else if (list.get(i).equals(HILLEL_WORD)) { list.set(i, HILLEL_IS_GOOD_WORDS);}
            tmp.append(list.get(i)+" ");
        }
        return removeSpacesBeforePunctuationMarks(tmp.toString());
    }

    public String addSpacesBeforePunctuationMarks(String text) {
        for (char c: PUNCTUATION_MARKS) {
            text = text.replace(String.valueOf(c)," " + c);
        }
        return text;
    }

    public String removeSpacesBeforePunctuationMarks(String text) {
        for (char c: PUNCTUATION_MARKS) {
            text = text.replace(" " + c, String.valueOf(c));
        }
        return text;
    }
}
