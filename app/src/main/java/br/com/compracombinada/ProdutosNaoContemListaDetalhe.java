package br.com.compracombinada;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterProdutosCompraColetiva;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaListaNaoContemProdutos;
import br.com.compracombinada.model.Produtos;

/**
 * Created by bruno on 21/08/14.
 */
public class ProdutosNaoContemListaDetalhe extends Fragment {

    private View view;
    private ListView listView;
    private ListAdapterProdutosCompraColetiva listAdapterProdutos;
    private Fragment fragment;
    private List<Produtos> produtosQueFaltam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        setHasOptionsMenu(true);

        new AsyncTaskCompraColetivaListaNaoContemProdutos(ProdutosNaoContemListaDetalhe.this).execute(getArguments().getInt("eventoId"));

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("produto", (Produtos) listAdapterProdutos.getItem(i));
                fragment = new ProdutoDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

            }
        });

    return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void retornoAsyncTaskCompraCombinadaProdutosEmFalta(String jsonString) {

        if (!jsonString.equalsIgnoreCase("")) {
            Gson gson = new Gson();
            produtosQueFaltam = new ArrayList<Produtos>();
            produtosQueFaltam = gson.fromJson(jsonString, new TypeToken<List<Produtos>>() {
            }.getType());

            listAdapterProdutos = new ListAdapterProdutosCompraColetiva(this, produtosQueFaltam);
            listView.setAdapter(listAdapterProdutos);

            Collections.sort(produtosQueFaltam, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    Produtos p1 = (Produtos) o1;
                    Produtos p2 = (Produtos) o2;
                    return p1.getProduto().getNome().compareToIgnoreCase(p2.getProduto().getNome());
                }
            });
        }
    }

}


