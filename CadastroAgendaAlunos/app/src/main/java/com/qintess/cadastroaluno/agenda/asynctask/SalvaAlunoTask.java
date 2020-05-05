package com.qintess.cadastroaluno.agenda.asynctask;

import com.qintess.cadastroaluno.agenda.model.Aluno;
import com.qintess.cadastroaluno.agenda.room.dao.AlunoDAO;


public class SalvaAlunoTask extends BaseAlunoTask {

    private final AlunoDAO alunoDAO;
    private final Aluno aluno;



    public SalvaAlunoTask(AlunoDAO alunoDAO,
                          Aluno aluno,
                           FinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.aluno = aluno;


    }

    @Override
    protected Void doInBackground(Void... voids) {
        int alunoId = alunoDAO.salva(aluno).intValue();

        return null;
    }


}
