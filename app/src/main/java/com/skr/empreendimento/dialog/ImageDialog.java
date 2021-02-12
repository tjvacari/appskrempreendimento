package com.skr.empreendimento.dialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.skr.empreendimento.EmpreendimentoActivity;
import com.skr.empreendimento.R;

public class ImageDialog {

    private final EmpreendimentoActivity mActivity;
    private final ImageView image;

    public ImageDialog(EmpreendimentoActivity mActivity, ImageView image) {
        this.mActivity = mActivity;
        this.image = image;
    }

    public void show() {
        View dialog = mActivity.getLayoutInflater().inflate(R.layout.dialog_image, null);
        ImageView image = dialog.findViewById(R.id.imageViewDialog_id);
        image.setImageDrawable(this.image.getDrawable());

        new MaterialAlertDialogBuilder(mActivity)
                .setView(dialog)
                .setPositiveButton(R.string.voltar, (DialogInterface dialogInterface, int i) -> {

                }).show();
    }
}
