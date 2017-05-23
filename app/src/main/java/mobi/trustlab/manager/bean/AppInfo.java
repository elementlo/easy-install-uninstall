package mobi.trustlab.manager.bean;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import mobi.trustlab.manager.tools.LogHelper;
import mobi.trustlab.manager.tools.CommonTools;

public class AppInfo {
    private String packageName;
    private String appName;
    private int appVersion;
    private String appVersionName;
    private Bitmap appIconBitmap;
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

    public static final int[] ABNORMAL_CHAR_ARRAY_INT = {92, 47, 58, 42, 63, 34, 60, 62, 124};// 9

    public AppInfo(String packageName) {
        this.packageName = packageName;
        appName = "";
        appVersionName = "";
        appIconBitmap = null;
        appIcon = null;
        defaultAndroidIcon = false;
        appSize = 0;
        appSizeStr = "";
        appLastModified = 0;
        appLastModifiedStr = "";
        path = "";
        selected = false;
        installed = false;

        apkInstalled = false;
        appArchived = false;
    }

    public AppInfo() {

    }

    public boolean appEquals(AppInfo appInfo) {
        if (appInfo == null) {
            return false;
        }

        return this.getPackageName().equals(appInfo.getPackageName()) &&
                this.getAppName().equals(appInfo.getAppName()) &&
                this.getAppVersion() == appInfo.getAppVersion() &&
                this.getAppVersionName().equals(appInfo.getAppVersionName()) &&
                this.getAppSize() == appInfo.getAppSize();
    }

    public boolean matches(AppInfo appInfo) {
        if (appInfo == null) {
            return false;
        }

        return this.getPackageName().equals(appInfo.getPackageName()) &&
                this.getAppVersion() == appInfo.getAppVersion() &&
                this.getAppVersionName().equals(appInfo.getAppVersionName());
    }

    public static String APK_FILE_EXT = ".apk";
    public static String ARCHIVED_PROTECTED_APP_FILE_EXT = ".apz";
    public static String TEXT_EXT = ".text";

    public String generateUniqueApkPath(String dir) {
        String path = null;
        String name = null;

        name = (this.getAppName() + this.getPackageName() + "-" + this.getAppVersion() + "-" + this.getAppVersionName());
        path = dir + File.separator + name;
        if (!isNameAvailable(path)) {
            name = this.getPackageName() + "-" + this.getAppVersion() + "-" + this.getAppVersionName();
            path = dir + File.separator + name;
            if (!isNameAvailable(path)) {
                name = this.getPackageName() + "-" + this.getAppVersion();
                path = dir + File.separator + name;
            }
        }
//		return name+APK_FILE_EXT;
        return path + APK_FILE_EXT;
    }

    public boolean isNameAvailable(String path) {
        String fullPath = path + TEXT_EXT;
        File test = new File(fullPath);
        if (test.exists()) {
            return true;
        } else if (test.mkdirs()) {
            test.delete();
            return true;
        } else {
            return false;
        }
    }

    public String generateUniqueApkFileName() {
        return this.getNormalAppName() + "-" + this.getPackageName() + "-" + this.getAppVersion() + "-" + this.getAppVersionName() + APK_FILE_EXT;
    }

    public String generateUniqueApzFileName() {
        return this.getNormalAppName() + "-" + this.getPackageName() + "-" + this.getAppVersion() + "-" + this.getAppVersionName() + ARCHIVED_PROTECTED_APP_FILE_EXT;
    }

    public String getAppNameWithVersion() {
        String appNameWithVersion = this.appName;

        if (appVersionName != null) {
            appNameWithVersion += " " + appVersionName;
        }

        return appNameWithVersion;
    }

//	public void setPackageName(String packageName) {
//		this.packageName = packageName;
//	}

    public String getPackageName() {
        return packageName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppVersionName(String appVersionName) {
        if (appVersionName == null) {
            appVersionName = "N/A";
        } else {
            this.appVersionName = this.filterAbnormalVersionName(this.packageName, appVersionName);
        }
    }

    public String getAppVersionName() {
        LogHelper.d("*****"+appVersionName);
        if (appVersionName!=null){
            if (appVersionName.toLowerCase().startsWith("v")) {
                return appVersionName;
            } else {
                return "v" + appVersionName;
            }
        }
        return "N/A";
    }

    public void setAppIcon(Bitmap appIconBitmap) {
        this.appIconBitmap = appIconBitmap;
        this.appIcon = new BitmapDrawable(appIconBitmap);
    }

    public void setAppIcon(Drawable appIcon, int width, int height, Bitmap defaultIcon) {
        this.appIconBitmap = CommonTools.resizeDrawableBitmap(appIcon, width, height, !isDefaultAndroidIcon());

        if (this.appIconBitmap == null) {
            this.appIconBitmap = defaultIcon;
        }

        this.appIcon = new BitmapDrawable(appIconBitmap);
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public Bitmap getAppIconBitmap() {
        return appIconBitmap;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
        this.appSizeStr = CommonTools.formatSize(appSize);
    }

    public long getAppSize() {
        return appSize;
    }


    public String getAppSizeStr() {
        return appSizeStr;
    }

    public void setAppLastModified(long appLastModified) {
        this.appLastModified = appLastModified;
        this.appLastModifiedStr = CommonTools.formatDate(appLastModified);
    }

    public long getAppLastModified() {
        return appLastModified;
    }

    public String getAppLastModifiedStr() {
        return appLastModifiedStr==null?"0":appLastModifiedStr;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void toogleSelected() {
        this.selected = !this.selected;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setDefaultAndroidIcon(boolean defaultAndroidIcon) {
        this.defaultAndroidIcon = defaultAndroidIcon;
    }

    public boolean isDefaultAndroidIcon() {
        return defaultAndroidIcon;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setApkInstalled(boolean apkInstalled) {
        this.apkInstalled = apkInstalled;
    }

    public boolean isApkInstalled() {
        return apkInstalled;
    }

    public void setAppArchived(boolean appArchived) {
        this.appArchived = appArchived;
    }

    public boolean isAppArchived() {
        return appArchived;
    }

    public boolean isAppProtected() {
        boolean isAppProtected = false;

        if (this.isInstalled()) {
            if (this.getPath().startsWith("/data/app-private/")) {
                isAppProtected = true;
            } else if (this.getPath().startsWith("/mnt/asec/")) {
                File f = new File(this.getPath());
                if (!f.canRead()) {
                    isAppProtected = true;
                }
            }
        } else {
            if (this.getPath().endsWith(ARCHIVED_PROTECTED_APP_FILE_EXT)) {
                isAppProtected = true;
            }
        }

        return isAppProtected;
    }

    private static String JSON_KEY_PACKAGE_NAME = "package_name";
    private static String JSON_KEY_APP_NAME = "app_name";
    private static String JSON_KEY_APP_VERSION_NAME = "app_version_name";
    private static String JSON_KEY_APP_VERSION_CODE = "app_version_code";
    private static String JSON_KEY_APP_SIZE = "app_size";
    private static String JSON_KEY_APP_LAST_MODIFIED = "app_last_modified";

    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(JSON_KEY_PACKAGE_NAME, this.getPackageName());
            jsonObject.put(JSON_KEY_APP_NAME, this.getAppName());
            jsonObject.put(JSON_KEY_APP_VERSION_NAME, this.getAppVersionName());
            jsonObject.put(JSON_KEY_APP_VERSION_CODE, this.getAppVersion());
            jsonObject.put(JSON_KEY_APP_SIZE, this.getAppSize());
            jsonObject.put(JSON_KEY_APP_LAST_MODIFIED, this.getAppLastModified());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static AppInfo fromJSONString(String jsonString) {
        AppInfo appInfo = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            if (jsonObject.has(JSON_KEY_PACKAGE_NAME) &&
                    jsonObject.has(JSON_KEY_APP_NAME) &&
                    jsonObject.has(JSON_KEY_APP_VERSION_NAME) &&
                    jsonObject.has(JSON_KEY_APP_VERSION_CODE) &&
                    jsonObject.has(JSON_KEY_APP_SIZE) &&
                    jsonObject.has(JSON_KEY_APP_LAST_MODIFIED)) {
                appInfo = new AppInfo(jsonObject.getString(JSON_KEY_PACKAGE_NAME));
                appInfo.setAppName(jsonObject.getString(JSON_KEY_APP_NAME));
                appInfo.setAppVersion(jsonObject.getInt(JSON_KEY_APP_VERSION_CODE));
                appInfo.setAppVersionName(jsonObject.getString(JSON_KEY_APP_VERSION_NAME));
                appInfo.setAppSize(jsonObject.getLong(JSON_KEY_APP_SIZE));
                appInfo.setAppLastModified(jsonObject.getLong(JSON_KEY_APP_LAST_MODIFIED));
                appInfo.setInstalled(false);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return appInfo;
    }

    private String filterAbnormalVersionName(String packageName, String versionName) {
//		if (packageName.equals("com.teslacoilsw.flashlight")) {
//			Pattern pattern = Pattern.compile("[0-9\\.]+");
//			Matcher matcher = pattern.matcher(versionName);
//			
//			if (matcher.find()) {
//				versionName = matcher.group(0);
//			}
//		}

        versionName = versionName.replaceAll("[^([a-z][A-Z][0-9]_\\s\\-\\.)]+", " ");
        versionName = versionName.replaceAll("\\s{2,}", " ");//more then one blank merge into one
        versionName = versionName.replaceAll("\\.{2,}", ".");// more then one dot merge into one
        versionName = versionName.trim();

        if (versionName.endsWith(".")) {
            versionName = versionName.substring(0, versionName.length() - 1);
        }

        return versionName;
    }

    private String getNormalAppName() {
        String appName = getAppName();
        if (appName != null) {
            appName = filterAbnormalChar(appName);
            appName = appName.replaceAll("\\s{2,}", " ");//if has more then one blank,merge into one
            appName = appName.trim();
        }
        return appName;
    }

    public static String filterAbnormalChar(String str) {
        for (int i = 0; i < 32; i++) {
            str = str.replace(((char) i + ""), "");
        }

        for (int j : ABNORMAL_CHAR_ARRAY_INT) {
            str = str.replace(((char) j + ""), "");
        }

        return str;
    }

}
