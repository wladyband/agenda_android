package com.qintess.cadastroaluno.agenda.asynctask;

import android.os.AsyncTask;

import com.qintess.cadastroaluno.agenda.model.Aluno;
import com.qintess.cadastroaluno.agenda.room.dao.AlunoDAO;
import com.qintess.cadastroaluno.agenda.ui.adapter.ListaAlunosAdapter;

public class RemoveAlunoTask extends AsyncTask<Void, Void, Void> {

    private final AlunoDAO dao;
    private final ListaAlunosAdapter adapter;
    private final Aluno aluno;

    public RemoveAlunoTask(AlunoDAO dao, ListaAlunosAdapter adapter, Aluno aluno) {
        this.dao = dao;
        this.adapter = adapter;
        this.aluno = aluno;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.remove(aluno);
        return null;
    }

    @Override
    protected void onPostExecute(Void  aVoid) {
        super.onPostExecute(aVoid);
        adapter.remove(aluno);
    }
}

