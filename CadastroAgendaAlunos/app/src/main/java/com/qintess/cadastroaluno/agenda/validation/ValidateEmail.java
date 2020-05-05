package com.qintess.cadastroaluno.agenda.validation;

import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

public class ValidateEmail implements Validate{

    private final TextInputLayout textInputEmail;
    private final EditText fieltEmail;
    private final ValidationPattern validationPattern;

    public ValidateEmail(TextInputLayout textInputEmail) {
        this.textInputEmail = textInputEmail;
        this.fieltEmail = this.textInputEmail.getEditText();
        this.validationPattern = new ValidationPattern(this.textInputEmail);
    }

    private boolean validatePattern(String email){
      if(email.matches(".+@.+\\..+")){
         return true;
      }
        textInputEmail.setError("E-mail inválido");
        return false;
    }

    @Override
    public boolean isValidation(){
        if(!validationPattern.isValidation()) return false;
        String email = fieltEmail.getText().toString();
        if(!validatePattern(email)) return  false;
        return true;
    }
}
