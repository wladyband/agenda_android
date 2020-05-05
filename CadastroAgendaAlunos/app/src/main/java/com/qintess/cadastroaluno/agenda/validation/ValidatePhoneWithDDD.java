package com.qintess.cadastroaluno.agenda.validation;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import com.qintess.cadastroaluno.agenda.validation.formatter.FormatterPhoneWithDDD;


public class ValidatePhoneWithDDD implements Validate{

    public static final String TELEFONE_TEM_QUE_TER_10_A_11_DÍGITOS = "Telefone tem que ter 10 a 11 dígitos";
    private final TextInputLayout textInputPhoneWithDDD;
    private final EditText fieltPhoneWithDDD;
    private final ValidationPattern validationPattern;
    private final FormatterPhoneWithDDD formatting = new FormatterPhoneWithDDD();

    public ValidatePhoneWithDDD(TextInputLayout textInputPhoneWithDDD) {
        this.textInputPhoneWithDDD = textInputPhoneWithDDD;
        this.fieltPhoneWithDDD = textInputPhoneWithDDD.getEditText();
        this.validationPattern = new ValidationPattern(textInputPhoneWithDDD);
    }

    private boolean validateBetweenTenOrElevenDigits(String phoneWithDDD) {
        int digits = phoneWithDDD.length();
        if (digits < 10 || digits > 11) {
            textInputPhoneWithDDD.setError(TELEFONE_TEM_QUE_TER_10_A_11_DÍGITOS);
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidation() {
        if (!validationPattern.isValidation()) return false;
        String phoneWithDDD = fieltPhoneWithDDD.getText().toString();
        String telefoneSemFormato = formatting.remove(phoneWithDDD);
        if (!validateBetweenTenOrElevenDigits(telefoneSemFormato)) return false;
        addFormatter(telefoneSemFormato);
        return true;
    }

    private void addFormatter(String phoneWithDDD) {
        String phoneWithDDDFormatter = formatting.formatter(phoneWithDDD);
        fieltPhoneWithDDD.setText(phoneWithDDDFormatter);
    }

}
