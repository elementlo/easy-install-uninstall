package mobi.trustlab.manager.manager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mobi.trustlab.manager.bean.AppInfo;

/**
 * Created by elemenzhang on 2017/4/6.
 */

public class AppManager {
    public static AppInfo getInstalledAppInfo(Context context,
                                              String packageName, Bitmap defaultIcon) throws PackageManager.NameNotFoundException {
        AppInfo appInfo = new AppInfo(packageName);
        PackageManager pm = context.getPackageManager();

        PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
        String appName = packageInfo.applicationInfo.loadLabel(pm).toString().trim();

        appInfo.setAppVersionName(packageInfo.versionName);
        appInfo.setAppVersion(packageInfo.versionCode);
        appInfo.setAppName(appName);

        int size = context.getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
        appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(pm), size, size, defaultIcon);

        File file = new File(packageInfo.applicationInfo.sourceDir);
        if (file.exists()) {
            appInfo.setAppSize(file.length());
            appInfo.setAppLastModified(file.lastModified());
            appInfo.setPath(packageInfo.applicationInfo.sourceDir);
        }

        appInfo.setInstalled(true);

        return appInfo;
    }

    public static List<AppInfo> getInstalledApplist(Context context,Bitmap defaultIcon){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
        List<AppInfo> appInfoList=new ArrayList<>();
        for (PackageInfo packageInfo:packageInfoList){
            boolean isSystemApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;

            if (TextUtils.isEmpty(packageInfo.applicationInfo.sourceDir)) {
                continue;
            }
            if (isSystemApp && (packageInfo.applicationInfo.sourceDir.startsWith("/system/")
                    || packageInfo.applicationInfo.sourceDir.startsWith("/vendor/"))
                    ) {
                continue;
            }
            AppInfo appInfo=null;
            try {
                appInfo = getInstalledAppInfo(context, packageInfo.applicationInfo.packageName, defaultIcon);
            } catch (PackageManager.NameNotFoundException e) {
                // e.printStackTrace();
            } catch (OutOfMemoryError er) {
                System.gc();
            }

            if (appInfo != null) {
                appInfo.setInstalled(true);
                appInfoList.add(appInfo);
            }

        }
        return appInfoList;
    }



    public static class BackupLogger {
        private StringBuilder mBackupLog = null;

        public void startNewLogger() {
            mBackupLog = new StringBuilder();
        }

        public void appendLog(String msg) {
            if (mBackupLog != null) {
                Log.d("BackupLogger", msg);
                mBackupLog.append(msg);
                mBackupLog.append("\n");
            }
        }

        public String stopAndGetBackupLog() {
            if (mBackupLog != null) {
                String log = "\n--start backup log--\n" + mBackupLog.toString() + "\n--end backup log--\n";
                mBackupLog = null;
                return log;
            } else {
                return "no backup log";
            }
        }
    }

}
