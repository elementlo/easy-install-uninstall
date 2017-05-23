package mobi.trustlab.manager.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by elemenzhang on 2017/4/6.
 */

public class AppInfoBean {
    private String packageName;
    private String appName;
    private int appVersion;
    private String appVersionName;
    private Bitmap appIconBitmap;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public Bitmap getAppIconBitmap() {
        return appIconBitmap;
    }

    public void setAppIconBitmap(Bitmap appIconBitmap) {
        this.appIconBitmap = appIconBitmap;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public boolean isDefaultAndroidIcon() {
        return defaultAndroidIcon;
    }

    public void setDefaultAndroidIcon(boolean defaultAndroidIcon) {
        this.defaultAndroidIcon = defaultAndroidIcon;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public String getAppSizeStr() {
        return appSizeStr;
    }

    public void setAppSizeStr(String appSizeStr) {
        this.appSizeStr = appSizeStr;
    }

    public long getAppLastModified() {
        return appLastModified;
    }

    public void setAppLastModified(long appLastModified) {
        this.appLastModified = appLastModified;
    }

    public String getAppLastModifiedStr() {
        return appLastModifiedStr;
    }

    public void setAppLastModifiedStr(String appLastModifiedStr) {
        this.appLastModifiedStr = appLastModifiedStr;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isApkInstalled() {
        return apkInstalled;
    }

    public void setApkInstalled(boolean apkInstalled) {
        this.apkInstalled = apkInstalled;
    }

    public boolean isAppArchived() {
        return appArchived;
    }

    public void setAppArchived(boolean appArchived) {
        this.appArchived = appArchived;
    }

    private Drawable appIcon;
    private boolean defaultAndroidIcon;
    private long appSize;
    private String appSizeStr;
    private long appLastModified;
    private String appLastModifiedStr;
    private String path;
    private boolean installed;
    private boolean selected;

    private boolean apkInstalled;
    private boolean appArchived;
}
