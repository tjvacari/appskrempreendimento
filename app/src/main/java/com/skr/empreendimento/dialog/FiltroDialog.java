package com.skr.empreendimento.dialog;

import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.skr.empreendimento.EmpreendimentoActivity;
import com.skr.empreendimento.R;
import com.skr.empreendimento.retrofit.CallBackRetrofit;
import com.skr.empreendimento.retrofit.EmpreendimentoRequest;
import com.skr.empreendimento.to.FiltroTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;

public class FiltroDialog {

    private EmpreendimentoActivity mActivity;

    // Filtros disponíveis
    private List<FiltroTO> categoriaList;
    private List<FiltroTO> tipoList;

    // Filtros aplicados
    private Set<Integer> idsCategoria;
    private Set<Integer> idsTipo;

    public FiltroDialog(EmpreendimentoActivity mActivity) {
        this.mActivity = mActivity;
        this.categoriaList = new ArrayList<>();
        this.tipoList = new ArrayList<>();
    }

    public void show() {
        requestFiltros();
    }

    /**
     * Faz o request para o webservice recuperando os filtros do banco
     * e separando por tipo (categoria ou tipo)
     */
    private void requestFiltros() {
        categoriaList.clear();
        tipoList.clear();
        String categoria = mActivity.getResources().getString(R.string.categoria);

        EmpreendimentoRequest.buscarFiltro((call, response, throwable) -> {
            if(throwable != null) {
                mActivity.showDialogError(throwable.getMessage());
                return;
            }
            List<FiltroTO> filtroList = (List<FiltroTO>) response.body();
            for (FiltroTO to : filtroList) {
                if(to.getTipo().equals(categoria)) {
                    categoriaList.add(to);
                } else {
                    tipoList.add(to);
                }
            }
            initGUI();
        });
    }

    private void initGUI() {
        View dialog = mActivity.getLayoutInflater().inflate(R.layout.dialog_filtro, null);
        ChipGroup chipGroupCategoria = dialog.findViewById(R.id.chipGroupCategoria_id);
        ChipGroup chipGroupTipo = dialog.findViewById(R.id.chipGroupTipo_id);

        for (FiltroTO to : categoriaList) {
            chipGroupCategoria.addView(chipView(to.getId(), to.getDescricao(), idsCategoria));
        }

        for (FiltroTO to : tipoList) {
            chipGroupTipo.addView(chipView(to.getId(), to.getDescricao(), idsTipo));
        }

        new MaterialAlertDialogBuilder(mActivity)
                .setTitle(R.string.filtros)
                .setView(dialog)
                .setPositiveButton(R.string.aplicar, (DialogInterface dialogInterface, int i) -> {
                    aplicarFiltro(chipGroupTipo.getCheckedChipIds(), chipGroupCategoria.getCheckedChipIds());
                })
                .setNegativeButton(R.string.cancelar, (DialogInterface dialogInterface, int i) -> {

                }).show();
    }

    private View chipView(int id, String descricao, Set<Integer> ids) {
        Chip chip = new Chip(new ContextThemeWrapper(mActivity, R.style.Widget_MaterialComponents_Chip_Choice));
        chip.setText(descricao);
        chip.setId(id);
        chip.setCheckable(true);
        if(ids != null) {
            chip.setChecked(ids.contains(id));
        }
        return chip;
    }

    private void aplicarFiltro(List<Integer> idsTipo, List<Integer> idsCategoria) {
        this.idsCategoria = new HashSet<Integer>(idsCategoria);
        this.idsTipo = new HashSet<Integer>(idsTipo);

        mActivity.aplicarFiltro(this.idsCategoria, this.idsTipo);
    }

}
