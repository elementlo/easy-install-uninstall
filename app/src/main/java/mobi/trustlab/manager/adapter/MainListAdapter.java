package mobi.trustlab.manager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import mobi.trustlab.manager.bean.AppInfo;
import mobi.trustlab.manager.R;
import mobi.trustlab.manager.tools.LogHelper;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListViewHolder> {
    private Context context;
    private ArrayList<AppInfo> mainList;
    private SparseArray<Boolean> checkBoxStateArray = new SparseArray();
    private ArrayList<AppInfo> installedList;
    private ArrayList<AppInfo> notInstalledList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public MainListAdapter(Context context, ArrayList mainList) {
        this.context = context;
        this.mainList = mainList;
        initCheckBoxStateArray();
    }

    private void initCheckBoxStateArray() {
        for (int i = 0; i < checkBoxStateArray.size(); i++) {
            checkBoxStateArray.put(i, false);
        }
    }

    @Override
    public MainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainListViewHolder mViewHolder = new MainListViewHolder(LayoutInflater.
                from(context).inflate(R.layout.listview_install_item, parent, false));
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final MainListViewHolder holder, final int position) {
        holder.tvAppName.setText(mainList.get(position).getAppName());
        holder.tvAppVersion.setText(mainList.get(position).getAppVersionName());
        holder.ivAppIco.setBackgroundDrawable(mainList.get(position).getAppIcon());
        holder.tvDateSize.setText(mainList.get(position).getAppLastModifiedStr()+"-"+mainList.
                get(position).getAppSizeStr());
        holder.tvInstalled.setVisibility(View.INVISIBLE);
        if (mainList.get(position).isInstalled()) {
            holder.tvInstalled.setTag(1);
            if ((int) holder.tvInstalled.getTag() == 1)
                holder.tvInstalled.setVisibility(View.VISIBLE);
        }

        holder.cbMainListItemSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxStateArray.put(position, isChecked);
            }
        });
        if (checkBoxStateArray.get(position) == null) {
            checkBoxStateArray.put(position, false);
        }
        holder.cbMainListItemSelected.setChecked(checkBoxStateArray.get(position));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position=holder.getLayoutPosition();
                onItemClickListener.onItemLongClick(view,position);
                return false;
            }
        });
    }

    public SparseArray<Boolean> getCheckBoxStateArray() {
        return checkBoxStateArray;
    }

    public void getSortByInstalledList(Boolean continueSort) {
        installedList = new ArrayList<>();
        notInstalledList = new ArrayList<>();
        for (AppInfo info : mainList) {
            if (info.isInstalled()) {
                installedList.add(info);
            } else {
                notInstalledList.add(info);
            }
        }
        if (!continueSort) {
            installedList.addAll(notInstalledList);
            mainList = installedList;
        }
    }

    public void getSortByNameList(Boolean byInstalled, Boolean isAscend) {
        if (byInstalled) {
            LogHelper.d("已经选了按安装分类，并且再按名字排序");
            getSortByInstalledList(true);
            mainList = new ArrayList<>();
            if (installedList != null) {
                if (isAscend) {
                    Collections.sort(installedList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            return appInfo.getAppName().compareTo(t1.getAppName());
                        }
                    });
                } else {
                    Collections.sort(installedList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            int flag = appInfo.getAppName().compareTo(t1.getAppName());
                            if (flag > 0) {
                                return -1;
                            } else if (flag < 0) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                mainList.addAll(installedList);
            }

            if (notInstalledList != null) {
                if (isAscend) {
                    Collections.sort(notInstalledList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            return appInfo.getAppName().compareTo(t1.getAppName());
                        }
                    });
                } else {
                    Collections.sort(notInstalledList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            int flag = appInfo.getAppName().compareTo(t1.getAppName());
                            if (flag > 0) {
                                return -1;
                            } else if (flag < 0) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                mainList.addAll(notInstalledList);
            }

        } else {
            LogHelper.d("未选按安装分类，并且按名字排序");
            if (isAscend) {
                Collections.sort(mainList, new Comparator<AppInfo>() {
                    @Override
                    public int compare(AppInfo appInfo, AppInfo t1) {
                        return appInfo.getAppName().compareTo(t1.getAppName());
                    }
                });
            } else {
                Collections.sort(mainList, new Comparator<AppInfo>() {
                    @Override
                    public int compare(AppInfo appInfo, AppInfo t1) {
                        int flag = appInfo.getAppName().compareTo(t1.getAppName());
                        if (flag > 0) {
                            return -1;
                        } else if (flag < 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            }

        }

    }

    public void getSortByDateList(Boolean byInstalled, Boolean isAscend) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (byInstalled) {
            LogHelper.d("已经选了按安装分类，并且再按名字排序");
            getSortByInstalledList(true);
            mainList = new ArrayList<>();
            if (installedList != null) {
                if (isAscend) {
                    Collections.sort(installedList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {

                            try {
                                Date date1 = sdf.parse(appInfo.getAppLastModifiedStr());
                                Date date2 = sdf.parse(t1.getAppLastModifiedStr());
                                return date1.compareTo(date2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;

                        }
                    });
                } else {
                    Collections.sort(installedList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            try {
                                Date date1 = sdf.parse(appInfo.getAppLastModifiedStr());
                                Date date2 = sdf.parse(t1.getAppLastModifiedStr());
                                int flag = date1.compareTo(date2);
                                if (flag > 0) {
                                    return -1;
                                } else if (flag < 0) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                }
                mainList.addAll(installedList);
            }

            if (notInstalledList != null) {
                if (isAscend) {
                    Collections.sort(notInstalledList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            try {
                                Date date1 = sdf.parse(appInfo.getAppLastModifiedStr());
                                Date date2 = sdf.parse(t1.getAppLastModifiedStr());
                                return date1.compareTo(date2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                } else {
                    Collections.sort(notInstalledList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            try {
                                Date date1 = sdf.parse(appInfo.getAppLastModifiedStr());
                                Date date2 = sdf.parse(t1.getAppLastModifiedStr());
                                int flag = date1.compareTo(date2);
                                if (flag > 0) {
                                    return -1;
                                } else if (flag < 0) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                }
                mainList.addAll(notInstalledList);
            }

        } else {
            LogHelper.d("未选按安装分类，并且按名字排序");
            if (isAscend) {
                Collections.sort(mainList, new Comparator<AppInfo>() {
                    @Override
                    public int compare(AppInfo appInfo, AppInfo t1) {
                        try {
                            Date date1 = sdf.parse(appInfo.getAppLastModifiedStr());
                            Date date2 = sdf.parse(t1.getAppLastModifiedStr());
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
            } else {
                Collections.sort(mainList, new Comparator<AppInfo>() {
                    @Override
                    public int compare(AppInfo appInfo, AppInfo t1) {
                        try {
                            Date date1 = sdf.parse(appInfo.getAppLastModifiedStr());
                            Date date2 = sdf.parse(t1.getAppLastModifiedStr());
                            int flag = date1.compareTo(date2);
                            if (flag > 0) {
                                return -1;
                            } else if (flag < 0) {
                                return 1;
                            } else {
                                return 0;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
            }
        }
    }

    public void getSortBySizeList(Boolean byInstalled, Boolean isAscend){
        if (byInstalled) {
            LogHelper.d("已经选了按安装分类，并且再按大小排序");
            getSortByInstalledList(true);
            mainList = new ArrayList<>();
            if (installedList != null) {
                if (isAscend) {
                    Collections.sort(installedList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                             if (appInfo.getAppSize()>t1.getAppSize()){
                                 return 1;
                             }else if (appInfo.getAppSize()<t1.getAppSize()){
                                 return -1;
                             }else {
                                 return 0;
                             }
                        }
                    });
                } else {
                    Collections.sort(installedList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            if (appInfo.getAppSize()>t1.getAppSize()){
                                return -1;
                            }else if (appInfo.getAppSize()<t1.getAppSize()){
                                return 1;
                            }else {
                                return 0;
                            }
                        }
                    });
                }
                mainList.addAll(installedList);
            }

            if (notInstalledList != null) {
                if (isAscend) {
                    Collections.sort(notInstalledList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            if (appInfo.getAppSize()>t1.getAppSize()){
                                return 1;
                            }else if (appInfo.getAppSize()<t1.getAppSize()){
                                return -1;
                            }else {
                                return 0;
                            }
                        }
                    });
                } else {
                    Collections.sort(notInstalledList, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
                            if (appInfo.getAppSize()>t1.getAppSize()){
                                return -1;
                            }else if (appInfo.getAppSize()<t1.getAppSize()){
                                return 1;
                            }else {
                                return 0;
                            }
                        }
                    });
                }
                mainList.addAll(notInstalledList);
            }

        } else {
            LogHelper.d("未选按安装分类，并且按大小排序");
            if (isAscend) {
                Collections.sort(mainList, new Comparator<AppInfo>() {
                    @Override
                    public int compare(AppInfo appInfo, AppInfo t1) {
                        if (appInfo.getAppSize()>t1.getAppSize()){
                            return 1;
                        }else if (appInfo.getAppSize()<t1.getAppSize()){
                            return -1;
                        }else {
                            return 0;
                        }
                    }
                });
            } else {
                Collections.sort(mainList, new Comparator<AppInfo>() {
                    @Override
                    public int compare(AppInfo appInfo, AppInfo t1) {
                        if (appInfo.getAppSize()>t1.getAppSize()){
                            return -1;
                        }else if (appInfo.getAppSize()<t1.getAppSize()){
                            return 1;
                        }else {
                            return 0;
                        }
                    }
                });
            }

        }

    }

    public SparseArray<Boolean> getCheckedMap(){
        return checkBoxStateArray;
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    class MainListViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemView;
        TextView tvAppName, tvAppVersion, tvDateSize, tvInstalled;
        ImageView ivAppIco;
        CheckBox cbMainListItemSelected;

        public MainListViewHolder(View itemView) {
            super(itemView);
            tvAppName = (TextView) itemView.findViewById(R.id.tv_appname);
            tvAppVersion = (TextView) itemView.findViewById(R.id.tv_appversion);
            tvDateSize = (TextView) itemView.findViewById(R.id.tv_dateadsize);
            tvInstalled = (TextView) itemView.findViewById(R.id.tv_installed);
            ivAppIco = (ImageView) itemView.findViewById(R.id.iv_appico);
            cbMainListItemSelected = (CheckBox) itemView.findViewById(R.id.cb_mainlist);
        }
    }
}