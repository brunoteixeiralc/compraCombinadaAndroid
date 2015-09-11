package br.com.compracombinada;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import br.com.compracombinada.adpater.SpinnerAdapter;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaAddListaProdutoCotacao;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaAddProduto;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaMarca_Familia;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Divisao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Familia;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Local;
import br.com.compracombinada.model.Marca;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.UsuarioSingleton;
import br.com.compracombinada.util.SpinnerEnum;

/**
 * Created by bruno on 21/08/14.
 */
public class AdicionarProduto extends android.support.v4.app.Fragment implements TextWatcher {

    private View view;
    private EditText nome;
    private EditText descricaoProduto;
    private EditText quantidade;
    private EditText edtPreco;
    private Button btnFoto;
    private ImageView foto;
    private Spinner marca;
    private Spinner familia;
    private Spinner divisao;
    private Marca marcaSelecionada;
    private Divisao divisaoSelecionada;
    private Familia familiaSelecionada;
    private List<Object> marcas;
    private List<Object> familias;
    private List<Object> divisoes;
    private int TAKE_PHOTO_CODE = 0;
    private static int count=0;
    private Button btnSalvar;
    private Bitmap bp;
    private Fragment fragment;
    private List<Produtos> listProdutos;
    private Produto p;
    private Produtos ps;
    private Double valorTotalDouble;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.adicionar_produto, container, false);

        nome = (EditText) view.findViewById(R.id.nome_produto);
        descricaoProduto = (EditText) view.findViewById(R.id.desc_produto);
        quantidade = (EditText) view.findViewById(R.id.quant);
        edtPreco = (EditText) view.findViewById(R.id.reais);
        edtPreco.addTextChangedListener(this);
        foto = (ImageView) view.findViewById(R.id.foto);

        listProdutos = new ArrayList<Produtos>();
        listProdutos.addAll((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva"));

        valorTotalDouble = (Double) getArguments().get("valorTotal");

//        marca = (Spinner) view.findViewById(R.id.marca);
//        marca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                marcaSelecionada = (Marca) adapterView.getItemAtPosition(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        familia = (Spinner) view.findViewById(R.id.familia);
//        familia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                familiaSelecionada = (Familia) adapterView.getItemAtPosition(i);
//                quantidade.setHint(familiaSelecionada.getMedida().equalsIgnoreCase("unidade") ? "Quantidade" : "Gramas");
//                edtPreco.setHint(familiaSelecionada.getMedida().equalsIgnoreCase("unidade") ? "Seu preço (R$)" : "Preço (R$) de 1 KG");
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        divisao = (Spinner) view.findViewById(R.id.divisao);
//        divisao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                divisaoSelecionada = (Divisao) adapterView.getItemAtPosition(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/compracombinada/";
        File newdir = new File(dir);
        newdir.mkdirs();

        btnFoto = (Button) view.findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            }
        });

        btnSalvar = (Button) view.findViewById(R.id.btnAdicionar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                p = new Produto();
//                p.setNome(nome.getText().toString());
//                p.setDescricao(descricaoProduto.getText().toString());
//                p.setFamilia(familiaSelecionada);
//                p.setMarca(marcaSelecionada);
//                p.setDivisao(divisaoSelecionada);
//                p.setAtivo(false);

                p = new Produto();
                p.setNome(nome.getText().toString());
                p.setDescricao(descricaoProduto.getText().toString());
                p.setFamilia(new Familia());
                p.getFamilia().setId(1);
                p.setMarca(new Marca());
                p.getMarca().setId(1);
                p.setDivisao(new Divisao());
                p.getDivisao().setId(1);
                p.setAtivo(false);

                if (bp != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    p.setFoto(encoded);
                }

                new AsyncTaskCompraColetivaAddProduto(AdicionarProduto.this).execute(p);

            }
        });

        //new AsyncTaskCompraColetivaMarca_Familia(AdicionarProduto.this).execute();

        return view;
    }

    public void retornoAsyncTaskCompraCombinadaMarcas_Familia(List<String> jsons){

        marcas = new ArrayList<Object>();
        familias = new ArrayList<Object>();
        divisoes = new ArrayList<Object>();

        Gson gson = new Gson();
        marcas = gson.fromJson(jsons.get(0), new TypeToken<List<Marca>>() {
        }.getType());

        gson = new Gson();
        familias = gson.fromJson(jsons.get(1), new TypeToken<List<Familia>>() {
        }.getType());

        gson = new Gson();
        divisoes = gson.fromJson(jsons.get(2), new TypeToken<List<Divisao>>() {
        }.getType());

        SpinnerAdapter spinnerAdpMarca = new SpinnerAdapter(marcas,AdicionarProduto.this.getActivity(), SpinnerEnum.MARCA);
        marca.setAdapter(spinnerAdpMarca);

        SpinnerAdapter spinnerAdpFamilia = new SpinnerAdapter(familias,AdicionarProduto.this.getActivity(),SpinnerEnum.FAMILIA);
        familia.setAdapter(spinnerAdpFamilia);

        SpinnerAdapter spinnerAdpDivisao = new SpinnerAdapter(divisoes,AdicionarProduto.this.getActivity(),SpinnerEnum.DIVISAO);
        divisao.setAdapter(spinnerAdpDivisao);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){

            Log.d("Camera", "Foto salva");
            bp = (Bitmap) data.getExtras().get("data");
            foto.setVisibility(View.VISIBLE);
            foto.setImageBitmap(bp);

        }
    }

    public void retornoAsyncTaskCompraCombinadaAddProduto(String jsonResposta){

        if(!jsonResposta.isEmpty()) {

            ps = new Produtos();
            ps.setProduto(p);
            ps.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            ps.setUsuarioNome(UsuarioSingleton.getInstance().getUsuario().getNome());
            if(p.getFamilia().getMedida().equalsIgnoreCase("quilo")){
                ps.setPrecoKG(edtPreco.getText().toString());
            }else{
                ps.setPreco(edtPreco.getText().toString());
            }
            ps.setNaoContem(false);

            listProdutos.add(ps);

            valorTotalDouble = valorTotalDouble + Double.parseDouble(edtPreco.getText().toString().replace(",","."));

            Bundle bundle = new Bundle();
            bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutos);
            bundle.putDouble("valorTotal", valorTotalDouble);

            if(getArguments().getString("fragment").equalsIgnoreCase("listaDetalheCompraColetiva")){
                ps.setAdicionado("pre-merge");
                fragment = new ListaDetalheCompraColetiva();
            }else{
                ps.setAdicionado("pos-merge");
                fragment = new CotacoesListaDetalhe();
            }

            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).addToBackStack(null)
                    .commit();

            //mandar o preço com "." ao invés de "," pois do outro lado é um float
            Produtos psTemp = ps;
            psTemp.setPreco(edtPreco.getText().toString().replace(",", "."));
            psTemp.setLista((Lista) getArguments().getSerializable("listaCotacao"));
            psTemp.getProduto().setId(Integer.parseInt(jsonResposta));

            //adicionar o produto na lista de cotação
            new AsyncTaskCompraColetivaAddListaProdutoCotacao(AdicionarProduto.this.getActivity()).execute(psTemp);


        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarProduto.this.getActivity());
            builder.setMessage("Ocorreu um erro para adicionar o produto")
                    .setTitle("Compra Combinada");
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    private String current = "";

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {

        if (!s.toString().equals(current) && !s.toString().isEmpty()) {
            edtPreco.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[R$,.]", "");

            DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));

            BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100));
            String formatted = precoFinal.format(parsed);

            current = formatted;
            edtPreco.setText(current);
            edtPreco.setSelection(formatted.length());

            edtPreco.addTextChangedListener(this);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}