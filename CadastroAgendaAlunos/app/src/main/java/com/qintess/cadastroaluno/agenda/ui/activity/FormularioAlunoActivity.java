package com.qintess.cadastroaluno.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.qintess.cadastroaluno.agenda.asynctask.SalvaAlunoTask;
import com.qintess.cadastroaluno.agenda.model.Aluno;
import com.qintess.cadastroaluno.agenda.retrofit.RetrofitInicializador;
import com.qintess.cadastroaluno.agenda.room.AgendaDatabase;
import com.qintess.cadastroaluno.agenda.room.dao.AlunoDAO;
import com.qintess.cadastroaluno.agenda.ui.cadastroagendaalunos.R;
import com.qintess.cadastroaluno.agenda.validation.Validate;
import com.qintess.cadastroaluno.agenda.validation.ValidateEmail;
import com.qintess.cadastroaluno.agenda.validation.ValidatePhoneWithDDD;
import com.qintess.cadastroaluno.agenda.validation.ValidationPattern;
import com.qintess.cadastroaluno.agenda.validation.formatter.FormatterPhoneWithDDD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qintess.cadastroaluno.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText fieltName;
    private EditText fieltPhoneWithDDD;
    private EditText fieltEmail;
    private AlunoDAO alunoDAO;
    private final List<Validate> validators = new ArrayList<>();
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        AgendaDatabase database = AgendaDatabase.getInstance(this);
        alunoDAO = database.getRoomAlunoDAO();
        inicializacaoDosCampos();
        carregaAluno();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salvar){
            boolean formIsValid = isFormIsValid();
            if(formIsValid) {
                finalizaFormulario();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFormIsValid() {
        boolean formIsValid = true;
        for(Validate validate: validators){
            if(!validate.isValidation()){
                formIsValid = false;
            }
        }
        return formIsValid;
    }


    private void preencheCampos() {
        fieltName.setText(aluno.getNome());
        fieltPhoneWithDDD.setText(aluno.getTelefone());
        fieltEmail.setText(aluno.getEmail());
    }
    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void finalizaFormulario() {
        preencheAluno();
        if (aluno.temIdValido()) {
            alunoDAO.edita(aluno);
        } else {
            salvaAluno(aluno);
            Call call = new RetrofitInicializador().getAlunoService().insere(aluno);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    Log.i("onResponse", "REQUISIÇÃO COM SUCESSO");
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.e("onResponse", "REQUISIÇÃO FALHOU");
                }
            });
        }

    }

    private void salvaAluno(Aluno aluno) {
        new SalvaAlunoTask(alunoDAO, aluno, this::finish).execute();

    }

    private void preencheAluno() {
        String nome = fieltName.getText().toString();
        String telefone = fieltPhoneWithDDD.getText().toString();
        String email = fieltEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }

    private void inicializacaoDosCampos() {
        configureName();
        configurePhoneWithDdd();
        configureEmail();
    }


    private void addValidationPattern(final TextInputLayout textInputCampo) {
        final EditText campo = textInputCampo.getEditText();
        final ValidationPattern validador = new ValidationPattern(textInputCampo);
        validators.add(validador);
        campo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String text = campo.getText().toString();
                if (!hasFocus) {
                    validador.isValidation();
                }
            }
        });
    }
    private void configureName() {
        TextInputLayout textInputNome = findViewById(R.id.activity_formulario_aluno_nome);
        fieltName = textInputNome.getEditText();
        addValidationPattern(textInputNome);
    }


    private void configurePhoneWithDdd() {
        TextInputLayout textInputTelefoneComDdd = findViewById(R.id.activity_formulario_aluno_telefone);
        fieltPhoneWithDDD = textInputTelefoneComDdd.getEditText();
        final ValidatePhoneWithDDD validador = new ValidatePhoneWithDDD(textInputTelefoneComDdd);
        validators.add(validador);
        final FormatterPhoneWithDDD formatting = new FormatterPhoneWithDDD();
        fieltPhoneWithDDD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String phoneWithDDD = fieltPhoneWithDDD.getText().toString();
                if(hasFocus){
                    String phoneWithDDDWithoutFormatting = formatting.remove(phoneWithDDD);
                    fieltPhoneWithDDD.setText(phoneWithDDDWithoutFormatting);
                }else{
                    validador.isValidation();
                }
            }
        });
    }

    private void configureEmail() {
        TextInputLayout textInputEmail = findViewById(R.id.formulario_cadastro_campo_email);
        fieltEmail = textInputEmail.getEditText();
        final ValidateEmail validador = new ValidateEmail(textInputEmail);
        validators.add(validador);
        fieltEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    validador.isValidation();
                }
            }
        });
    }

}
