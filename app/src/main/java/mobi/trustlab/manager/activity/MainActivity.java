package mobi.trustlab.manager.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;

import mobi.trustlab.manager.bean.AppInfo;
import mobi.trustlab.manager.R;
import mobi.trustlab.manager.accessibilityservice.AutoInstallAccessibility;
import mobi.trustlab.manager.adapter.MainListAdapter;
import mobi.trustlab.manager.customviews.CommonProgressbar;
import mobi.trustlab.manager.customviews.NestedRadiogroup;
import mobi.trustlab.manager.customviews.ToolbarPopMenu;
import mobi.trustlab.manager.tools.LogHelper;
import mobi.trustlab.manager.tools.CommonTools;


public class MainActivity extends BaseActivity {
    private File path;
    private ArrayList<AppInfo> appInfoList;
    private AppInfo appInfo;

    private ToolbarPopMenu popMenu;
    private RecyclerView rvMainList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mainActivityToolbar;
    private Spinner spOptions;
    private CommonProgressbar progressbar;

    private ArrayAdapter<String> spAdapter;
    private MainListAdapter adapter;
    private boolean isInstalledSelected;
    private boolean isAscend;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    progressbar.hideProgress();
                    adapter = new MainListAdapter(MainActivity.this, appInfoList);
                    rvMainList.setAdapter(adapter);
                    adapter.setOnItemClickListener(new MainListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                    break;
/*                case 1:
                    adapter.notifyDataSetChanged();
                    break;*/
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewData();
        taskRunnable();
    }

    private void taskRunnable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                siftAPKFile(path);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initViewData() {
        initView();
        initData();
    }

    private void initData() {
        appInfoList = new ArrayList();
        path = CommonTools.checkSDCard();
        Log.d("TAG", path + "");
    }

    private void initView() {
        progressbar=new CommonProgressbar(this);
        progressbar.showProgress();
        mainActivityToolbar = getToolbar();
        popMenu = new ToolbarPopMenu(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        rvMainList = (RecyclerView) findViewById(R.id.rv_install);
        rvMainList.setLayoutManager(new LinearLayoutManager(this));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mainActivityToolbar,
                R.string.toggledrawer_open,
                R.string.toggledrawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        spOptions = (Spinner) findViewById(R.id.sp_options);

        String[] optionsArray = getResources().getStringArray(R.array.options);
        spAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, optionsArray);
        spAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spOptions.setAdapter(spAdapter);
        getToolbar().inflateMenu(R.menu.toolbar_menu);
        CheckBox checkBox= (CheckBox) getToolbar().getMenu().findItem(R.id.toolbar_selectall).
                getActionView();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LogHelper.d("check"+b);
                SparseArray sparseArray=adapter.getCheckedMap();
                if (b){
                    for (int i = 0; i < sparseArray.size(); i++) {
                        sparseArray.put(i,true);
                    }
                }else {
                    for (int i = 0; i < sparseArray.size(); i++) {
                        sparseArray.put(i,false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_sort:
                        LogHelper.d("toolbarsort");
                        popMenu.setAnimationStyle(android.R.style.Animation_Dialog);
                        popMenu.showAtLocation(getToolbar(), Gravity.NO_GRAVITY, 100, 150);
                        break;
                }
                return true;
            }
        });
        popMenu.getPopMenuInstalledButton().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LogHelper.d("radiobutton******checked" + b);
                if (b) {
                    isInstalledSelected=true;
                    adapter.getSortByInstalledList(false);
                    adapter.notifyDataSetChanged();
                }
                else isInstalledSelected=false;
            }
        });


        popMenu.getPopMenuSortOptions().setOnCheckedChangeListener(new NestedRadiogroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(NestedRadiogroup group, int checkedId) {
                LogHelper.d("isinstalled******checked"+isInstalledSelected);
                switch (checkedId){
                    case R.id.btn_sortby_az:
                        adapter.getSortByNameList(isInstalledSelected,isAscend);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.btn_sortby_za:
                        adapter.getSortByNameList(isInstalledSelected,!isAscend);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.btn_sortby_asc:
                        adapter.getSortByDateList(isInstalledSelected,isAscend);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.btn_sortby_desc:
                        adapter.getSortByDateList(isInstalledSelected,!isAscend);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.btn_sortby_small:
                        adapter.getSortBySizeList(isInstalledSelected,isAscend);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.btn_sortby_large:
                        adapter.getSortBySizeList(isInstalledSelected,!isAscend);
                        adapter.notifyDataSetChanged();
                        break;
                }

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void siftAPKFile(File path) {
        if (path != null && path.exists()) {
            File file[] = path.listFiles();
            if (file != null) {

                for (File f : file) {
                    if (f.isDirectory()) {
                        siftAPKFile(f);
                    } else {
                        if (f.getAbsolutePath().endsWith("apk")) {
                            PackageManager pm = this.getPackageManager();
                            PackageInfo info = pm.getPackageArchiveInfo(f.getAbsolutePath(),
                                    PackageManager.GET_ACTIVITIES);

                            ApplicationInfo appInfoRev = null;
                            if (info != null) {
                                appInfoRev = info.applicationInfo;
                                appInfoRev.sourceDir = f.getAbsolutePath();
                                appInfoRev.publicSourceDir = f.getAbsolutePath();

                                String packageName = appInfoRev.packageName;
                                String appName = (String) pm.getApplicationLabel(appInfoRev);
                                long date=CommonTools.getApkUpdateTime(info);
                                long size=CommonTools.getFileSize(f);

                                appInfo = new AppInfo(packageName);
                                appInfo.setAppName(appName);
                                appInfo.setAppLastModified(date);
                                appInfo.setAppSize(size);
                                appInfo.setPath(f.getAbsoluteFile().toString());
                                if (info.versionName != null) {
                                    appInfo.setAppVersionName(info.versionName);
                                }
                                try {
                                    pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                                    appInfo.setInstalled(true);
                                } catch (PackageManager.NameNotFoundException e) {
                                    appInfo.setInstalled(false);
                                }

                                appInfo.setAppIcon(appInfoRev.loadIcon(pm), 36, 36,
                                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

                            } else {
                                appInfo = new AppInfo();
                                appInfo.setAppName(f.getPath());
                            }
                            appInfoList.add(appInfo);
                        }
                    }
                }
            }
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_install:

                SparseArray<Boolean> checkBoxStateArray = adapter.getCheckBoxStateArray();

                if (checkBoxStateArray == null) return;

                for (int i = 0; i < checkBoxStateArray.size(); i++) {
                    if (checkBoxStateArray.get(i)) {
                        AutoInstallAccessibility.INVOKE_TYPE = AutoInstallAccessibility.
                                TYPE_INSTALL_APP;
                        manualInstall(i);
                    }
                }
                break;
            default:
                break;
        }

    }

    private void manualInstall(int i) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setDataAndType(Uri.parse("file://" + appInfoList.get(i).getPath()),
                "application/vnd.android.package-archive");
        startActivity(installIntent);
    }


}
