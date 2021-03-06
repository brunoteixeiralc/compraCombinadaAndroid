package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.R;
import br.com.compracombinada.fragment.Eventos;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaEventos extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private Eventos ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaEventos(Eventos ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), ctx.getResources().getString(R.string.app_name), "Pesquisando seus eventos...", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getEventosUsuario(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaEventos(jsonString);
        progressDialog.dismiss();

    }

}
