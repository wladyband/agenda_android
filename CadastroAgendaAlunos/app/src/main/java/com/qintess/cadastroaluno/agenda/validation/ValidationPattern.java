package com.qintess.cadastroaluno.agenda.validation;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ValidationPattern implements Validate{

    private static final String CAMPO_OBRIGATÓRIO = "Campo Obrigatório";
    private final TextInputLayout textInputCampo;
    private final EditText campo;

    public ValidationPattern(TextInputLayout textInputCampo) {
        this.textInputCampo = textInputCampo;
        this.campo = this.textInputCampo.getEditText();
    }

    private boolean validationFieldRequired() {
        String text = campo.getText().toString();
        if (text.isEmpty()) {
            textInputCampo.setError(CAMPO_OBRIGATÓRIO);
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidation() {
        if (!validationFieldRequired()) return false;
        removeErro();
        return true;
    }

    private void removeErro() {
        textInputCampo.setError(null);
        textInputCampo.setErrorEnabled(false);
    }

}
