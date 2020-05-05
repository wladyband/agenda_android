package com.qintess.cadastroaluno.agenda.asynctask;

import android.os.AsyncTask;

abstract class BaseAlunoTask extends AsyncTask<Void, Void, Void> {

    private final FinalizadaListener listener;

    BaseAlunoTask(FinalizadaListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizada();
    }


    public interface FinalizadaListener {
        void quandoFinalizada();
    }

}
