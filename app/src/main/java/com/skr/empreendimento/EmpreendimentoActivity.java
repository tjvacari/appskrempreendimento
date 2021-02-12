package com.skr.empreendimento;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skr.empreendimento.dialog.ConfiguracaoDialog;
import com.skr.empreendimento.dialog.FiltroDialog;
import com.skr.empreendimento.retrofit.EmpreendimentoRequest;
import com.skr.empreendimento.to.EmpreentimentoTO;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EmpreendimentoActivity extends AppCompatActivity {

    private static Context contextOfApplication;

    private FiltroDialog mDialog;
    private EmpreendimentoAdapter mAdapter;

    private boolean isLoading = false;

    // filtros selecionados
    private Set<Integer> idsCategoria;
    private Set<Integer> idsTipo;

    // paginação
    private Integer page = 0;
    private Integer max = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_empreendimento);

        initGUI();
        buscarEmpreendimento();
    }

    private void initGUI() {
        {
            RecyclerView mRecyclerView = findViewById(R.id.recycler_id);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mAdapter = new EmpreendimentoAdapter(new ArrayList<EmpreentimentoTO>(), this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addOnScrollListener(new PaginationScrollListener(this, mLinearLayoutManager));
        }

        {
            mDialog = new FiltroDialog(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.configuracao_id:
                new ConfiguracaoDialog(this).show();
                return true;
            case R.id.filtro_id:
                mDialog.show();
                return true;
            default:
                return false;
        }
    }

    public void buscarEmpreendimento() {
        isLoading = true;
        new Thread( () -> {
            EmpreendimentoRequest.buscarEmpreendimento((call, response, throwable) -> {
                if(throwable != null) {
                    showDialogError(throwable.getMessage());
                    return;
                }
                List<EmpreentimentoTO> empreendimentoList = (List<EmpreentimentoTO>) response.body();
                if(page == 0) {
                    updateAdapter(empreendimentoList);
                } else {
                    incrementAdapter(empreendimentoList);
                }
                page += 1;
                isLoading = false;
            }, idsCategoria, idsTipo, page, max);
        }).run();
    }

    public void showDialogError(String message) {
        this.runOnUiThread(() -> {
            new AlertDialog.Builder(EmpreendimentoActivity.this)
                    .setTitle(R.string.erro)
                    .setMessage(message)
                    .show();
        });
    }

    private void updateAdapter(final List<EmpreentimentoTO> empreendimentoList) {
        this.runOnUiThread(() -> mAdapter.update(empreendimentoList));
    }

    private void incrementAdapter(final List<EmpreentimentoTO> empreendimentoList) {
        this.runOnUiThread(() -> mAdapter.increment(empreendimentoList));
    }

    public void aplicarFiltro(Set<Integer> idsCategoria, Set<Integer> idsTipo) {
        this.idsCategoria = idsCategoria;
        this.idsTipo = idsTipo;
        this.page = 0;
        buscarEmpreendimento();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public static SharedPreferences getPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
    }
}