package mobi.trustlab.manager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import mobi.trustlab.manager.R;
import mobi.trustlab.manager.customviews.CommonProgressbar;

/**
 * Created by elemenzhang on 2017/4/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = BaseActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private TextView tvTitle;
    private Spinner spOptions;

    private onTitleClickListener onTitleClickListener;
    private ArrayAdapter<String > spAdapter;

    interface onTitleClickListener{

        void onTitleClick();
    }
    public void setOnTitleClickListener(onTitleClickListener onTitleClickListener){
        this.onTitleClickListener=onTitleClickListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
/*            case R.id.tv_title:
                onTitleClickListener.onTitleClick();
                break;*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        spOptions= (Spinner) findViewById(R.id.sp_options);

        String[] optionsArray=getResources().getStringArray(R.array.options);
        spAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,optionsArray);
        spAdapter.setDropDownViewResource(R.layout.spinner_item);
        spOptions.setAdapter(spAdapter);
       /*
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("Sub Title");
        */
        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }

/*        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
/*        if(null != getToolbar() && isShowBacking()){
            showBack();
        }*/
    }


    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
/*    private void showBack(){
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.mipmap.icon_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }*/

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }

    /**
     * this activity layout res
     * 设置layout布局,在子类重写该方法.
     *
     * @return res layout xml id
     */
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy...");


    }
}
