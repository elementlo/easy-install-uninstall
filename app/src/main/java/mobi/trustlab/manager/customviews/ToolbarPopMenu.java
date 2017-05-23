package mobi.trustlab.manager.customviews;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import mobi.trustlab.manager.R;

/**
 * Created by elemenzhang on 2017/4/10.
 */

public class ToolbarPopMenu extends PopupWindow {
    private Activity mContext;

    private View view;
    private CheckBox rbInstalled;
    private NestedRadiogroup rgSortOption;


    public ToolbarPopMenu(Activity mcon) {

        this.mContext = mcon;

        this.view = LayoutInflater.from(mContext).inflate(R.layout.toolbar_popmenu, null);

        // 设置外部可点击
        this.setOutsideTouchable(true);


        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);

        // 设置弹出窗体的宽和高
          /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = mContext.getWindow();
        rbInstalled= (CheckBox) view.findViewById(R.id.btn_select_installed);
        rgSortOption= (NestedRadiogroup) view.findViewById(R.id.rg_sort_option);

        WindowManager m = mContext.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth((int) (d.getWidth() * 0.8));

        // 设置弹出窗体可点击
        this.setFocusable(true);

    }

    public CheckBox getPopMenuInstalledButton(){
        return rbInstalled;
    }

    public NestedRadiogroup getPopMenuSortOptions(){
        rgSortOption.clearCheck();
        return rgSortOption;
    }


}
