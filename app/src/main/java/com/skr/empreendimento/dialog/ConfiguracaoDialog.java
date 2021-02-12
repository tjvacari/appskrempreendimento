package com.skr.empreendimento.dialog;

import android.content.DialogInterface;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.skr.empreendimento.EmpreendimentoActivity;
import com.skr.empreendimento.R;
import com.skr.empreendimento.retrofit.RetrofitConfig;

public class ConfiguracaoDialog {

    private final EmpreendimentoActivity mActivity;

    public ConfiguracaoDialog(EmpreendimentoActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void show() {
        View dialog = mActivity.getLayoutInflater().inflate(R.layout.dialog_configuracao, null);
        TextInputEditText input = dialog.findViewById(R.id.inputConfig_id);
        input.setText(RetrofitConfig.getUrlBase());

        new MaterialAlertDialogBuilder(mActivity)
                .setTitle(R.string.configuracao)
                .setView(dialog)
                .setPositiveButton(R.string.confirmar, (DialogInterface dialogInterface, int i) -> {
                    String url = input.getText().toString();
                    RetrofitConfig.setUrlBase(url);
                })
                .setNegativeButton(R.string.cancelar, (DialogInterface dialogInterface, int i) -> {

                }).show();
    }
}
