package com.skr.empreendimento;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private EmpreendimentoActivity mActivity;
    private LinearLayoutManager layoutManager;

    public PaginationScrollListener(EmpreendimentoActivity mActivity, LinearLayoutManager layoutManager) {
        this.mActivity = mActivity;
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!mActivity.isLoading()) {
            if ((visibleItemCount + firstVisibleItemPosition) >=
                    totalItemCount && firstVisibleItemPosition >= 0) {
                mActivity.buscarEmpreendimento();
            }
        }
    }
}
