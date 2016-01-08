package com.baxian.vinda.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaseSwipListAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baxian.vinda.R;
import com.baxian.vinda.drop.CoverManager;
import com.baxian.vinda.drop.DropCover;
import com.baxian.vinda.drop.WaterDrop;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿qqlistview侧滑删除
 */
public class WaMsgActivity extends AppCompatActivity {

    private List<ApplicationInfo> mAppList;
    private AppAdapter mAdapter;
    private SwipeMenuListView mListView;

    private int mPos;//存放临时position
    private boolean mIsTop = false;
    //三个menu
    SwipeMenuItem openItem;
    SwipeMenuItem readItem;
    SwipeMenuItem deleteItem;
    private List<SwipeMenuItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        CoverManager.getInstance().init(this);
        CoverManager.getInstance().setMaxDragDistance(150);
        CoverManager.getInstance().setExplosionTime(150);
        initView();
    }

    private void initView() {

        mAppList = getPackageManager().getInstalledApplications(0);
        mListView = (SwipeMenuListView) findViewById(R.id.swip_listview);
        mAdapter = new AppAdapter();
        mListView.setAdapter(mAdapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator menuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("置顶");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
                mItems.add(openItem);
                readItem = new SwipeMenuItem(
                        getApplicationContext());

                readItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0xCE)));
                // set item width
                readItem.setWidth(dp2px(90));
                readItem.setTitle("标记未读");
                readItem.setTitleSize(18);
                readItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(readItem);
                mItems.add(readItem);
                // create "delete" item
                deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
                mItems.add(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(menuCreator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);

                switch (index) {
                    case 0:
                        // open
                        // open(item);
//                        mItems.get(0).setTitle("取消置顶");
//                        openItem.setTitle("取消置顶");
//                        menu.getMenuItem(0).setTitle("取消置顶");
//                        Log.i("key", "openItem :" + openItem.getTitle());
                        Log.i("key", "openItem :" + menu.getMenuItems().get(0).getTitle());
//                        Log.i("key", "openItem :" + mItems.get(0).getTitle());
//                        Log.i("key", "openItem :" + menu.getMenuItem(0).getTitle());
                        if (menu.getMenuItems().get(0).getTitle().equals("置顶")) {

                            mPos = position;
                            Log.i("key", "mPos :" + mPos);
                            mAppList.remove(position);
                            mAppList.add(0, item);
                            mListView.getChildAt(0).setBackgroundColor(Color.GRAY);

                        } else {

                            mAppList.remove(0);
                            mAppList.add(mPos, item);
                            mListView.getChildAt(0).setBackgroundColor(Color.parseColor("#EEEEEE"));

                        }
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), position + " readItem click", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        // delete
//					delete(item);
                        mAppList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), position + " item click", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    class AppAdapter extends BaseSwipListAdapter {

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public ApplicationInfo getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_list_app, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            ApplicationInfo item = getItem(position);
            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText(item.loadLabel(getPackageManager()));
            holder.mWater_drop.setText(String.valueOf(position));
//            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(WaMsgActivity.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
//                }
//            });
//            holder.tv_name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(WaMsgActivity.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
//                }
//            });

            holder.mWater_drop.setOnDragCompeteListener(new DropCover.OnDragCompeteListener() {
                @Override
                public void onDrag() {
                    Toast.makeText(WaMsgActivity.this, "remove:" + position, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolder {

            ImageView iv_icon;
            TextView tv_name;
            WaterDrop mWater_drop;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                mWater_drop = (WaterDrop) view.findViewById(R.id.water_drop);
                view.setTag(this);
            }

        }

//        @Override
//        public boolean getSwipEnableByPosition(int position) {
//            if (position % 2 == 0) {
//                return false;
//            }
//            return true;
//        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void open(ApplicationInfo item) {
        // open app
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(item.packageName);
        List<ResolveInfo> resolveInfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(
                    activityPackageName, className);

            intent.setComponent(componentName);
            startActivity(intent);
        }
    }

    private void delete(ApplicationInfo item) {
        // delete app
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.fromParts("package", item.packageName, null));
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_msg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_left) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }
        if (id == R.id.action_right) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
