package mobi.trustlab.manager.tools;

import android.content.Context;
import android.widget.Toast;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
/**
 * Created by elemenzhang on 2017/4/6.
 */
    public class ToastUtil {
        private static Toast toast;

        /**
         * 短时间显示  Toast
         *
         * @param context
         * @param sequence
         */
        public static void showShort(Context context, CharSequence sequence) {

            if (toast == null) {
                toast = Toast.makeText(context, sequence, Toast.LENGTH_SHORT);

            } else {
                toast.setText(sequence);
            }
            toast.show();

        }

        /**
         * 短时间显示Toast
         *
         * @param context
         * @param message
         */
        public static void showShort(Context context, int message) {
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                // toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        }

        /**
         * 长时间显示Toast
         *
         * @param context
         * @param message
         */
        public static void showLong(Context context, CharSequence message) {
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                // toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        }

        /**
         * 长时间显示Toast
         *
         * @param context
         * @param message
         */
        public static void showLong(Context context, int message) {
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                //    toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        }

        /**
         * 自定义显示时间
         *
         * @param context
         * @param sequence
         * @param duration
         */
        public static void show(Context context, CharSequence sequence, int duration) {
            if (toast == null) {
                toast = Toast.makeText(context, sequence, duration);
            } else {
                toast.setText(sequence);
            }
            toast.show();

        }

        /**
         * 隐藏toast
         */
        public static void hideToast() {
            if (toast != null) {
                toast.cancel();
            }
        }
    }
