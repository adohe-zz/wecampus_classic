package com.westudio.wecampus.ui.square;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.view.LoadingFooter;

/**
 * Created by martian on 13-12-10.
 */
public class SearchListAttacher {

    public ImageView emptyView;

    public ProgressBar progressBar;

    public LoadingFooter loadingFooter;

    public Integer page = 1;

    public enum Status {
        NO_INPUT, NO_RESULT, LOADING, LOADING_MORE, LOADING_END
    }

    public void setStatus(Status status) {
        if (status == Status.NO_INPUT) {
            progressBar.setVisibility(View.GONE);
            emptyView.setImageResource(R.drawable.search_no_input);
            emptyView.setVisibility(View.VISIBLE);
            loadingFooter.setState(LoadingFooter.State.Idle);
        } else if (status == Status.LOADING) {
            progressBar.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            loadingFooter.setState(LoadingFooter.State.Idle);
        } else if (status == Status.NO_RESULT) {
            progressBar.setVisibility(View.GONE);
            emptyView.setImageResource(R.drawable.search_no_result);
            emptyView.setVisibility(View.VISIBLE);
            loadingFooter.setState(LoadingFooter.State.Idle);
        } else if (status == Status.LOADING_MORE) {
            loadingFooter.setState(LoadingFooter.State.Loading);
        } else if (status == Status.LOADING_END) {
            loadingFooter.setState(LoadingFooter.State.TheEnd);
        }
    }
}
