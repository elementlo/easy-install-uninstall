package mobi.trustlab.manager.customviews;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import mobi.trustlab.manager.tools.LogHelper;

/**
 * Created by elemenzhang on 2017/4/11.
 */

public class CommonProgressbar  {
    private ProgressBar progressBar;
    private Activity context;

    public CommonProgressbar(Activity context){
        this.context=context;
        progressBar=new ProgressBar(context,null,android.R.attr.progressBarStyleInverse);
        FrameLayout.LayoutParams frameLayout=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        frameLayout.gravity= Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
        progressBar.setLayoutParams(frameLayout);


        FrameLayout root= (FrameLayout) context.findViewById(android.R.id.content);
        root.addView(progressBar);

    }

    public void showProgress(){
        LogHelper.d("showprogressbar");
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        FrameLayout rootContainer = (FrameLayout) context.findViewById(android.R.id.content);
        progressBar.setVisibility(View.GONE);
        rootContainer.removeView(progressBar);

    }
}
