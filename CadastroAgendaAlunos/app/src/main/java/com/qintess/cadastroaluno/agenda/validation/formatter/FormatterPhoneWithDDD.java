package com.qintess.cadastroaluno.agenda.validation.formatter;

public class FormatterPhoneWithDDD {

    public String formatter(String phoneWithDDD) {
        return phoneWithDDD
                .replaceAll("([0-9]{2})([0-9]{4,5})([0-9]{4})","($1) $2 - $3");
    }

    public String remove(String phoneWithDDD) {
        return phoneWithDDD
                .replace("(", "")
                .replace(")", "")
                .replace(" ", "")
                .replace("-", "");
    }

}
