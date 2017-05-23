package mobi.trustlab.manager.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by elemenzhang on 2017/4/5.
 */

public class CommonTools {
    public static File checkSDCard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return Environment.getExternalStorageDirectory();
        }else {
            return null;
        }
    }

    public static Bitmap resizeDrawableBitmap(Drawable drawable, int width, int height, boolean recycle) {
        Bitmap bitmap = drawableToBitmap(drawable);

        if (bitmap != null) {
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

            if (bitmap != resizedBitmap && recycle) {
                bitmap.recycle();
            }

            return resizedBitmap;
        } else {
            return null;
        }
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        try {
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?
                    Bitmap.Config.ARGB_4444 : Bitmap.Config.RGB_565;
            Bitmap bitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 1),
                    Math.max(drawable.getIntrinsicHeight(), 1),
                    config);
            if (bitmap == null) {
                return null;
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(new Rect(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight()));
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    public static String formatDecimal(double d, int scale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_DOWN).toString();
    }

    public static String formatDate(long time) {
        Date d = new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    public static String formatSize(long size) {
        long g = 1024 * 1024 * 1024;
        long m = 1024 * 1024;
        long k = 1024;
        String ret = "";
        if (size >= g) {
            ret = formatDecimal(((double) size / g), 1) + "GB";
        } else if (size >= m) {
            ret = formatDecimal(((double) size / m), 1) + "MB";
        } else if (size < m) {
            ret = formatDecimal(((double) size / k), 1) + "KB";
        }
        return ret;
    }

    public static long getApkUpdateTime(PackageInfo info) {

        ZipFile zf = null;
        try {
            zf = new ZipFile(info.applicationInfo.sourceDir);
            ZipEntry ze = zf.getEntry("classes.dex");
            return ze.getTime();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zf != null) {
                try {
                    zf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public static long getFileSize(File file)  {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }
}
