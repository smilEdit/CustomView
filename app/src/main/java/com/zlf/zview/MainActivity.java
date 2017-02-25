package com.zlf.zview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ZHorizontalNumProgress mProgress;


        private ExpandTabView mExpandtabView;

        private ViewSort    mViewSort;
        private ViewPrice   mViewPrice;
        private ViewAddress mViewAddress;
        private ViewName    mViewName;
        private ArrayList<View> mViewArray = new ArrayList<View>();
        private ArrayList<Double> mViewHeight = new ArrayList<>();


    private static final int MSG_PROGRESS_UPDATE = 0x110;

    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int progress = mProgress.getProgress();
            mProgress.setProgress(++progress);
            if (progress >= 100) {
                mHandler.removeMessages(MSG_PROGRESS_UPDATE);

            }
            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
//        mProgress = (ZHorizontalNumProgress) findViewById(R.id.h_progress);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//        int progress = mProgress.getProgress();
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mProgress.setProgress(++progress);
//                    }
//                });
//            }
//        }, 1000, 100);
                initView();
                initVaule();
                initListener();

    }

        private void initView() {

            mExpandtabView = (ExpandTabView) findViewById(R.id.expandtab_view);
            mViewSort = new ViewSort(this);
            mViewPrice = new ViewPrice(this);
            mViewAddress = new ViewAddress(this);
            mViewName = new ViewName(this);

        }

        private void initVaule() {

            mViewArray.add(mViewSort);
            mViewArray.add(mViewAddress);
            mViewArray.add(mViewPrice);
            mViewArray.add(mViewName);
            ArrayList<String> mTextArray = new ArrayList<String>();
            mTextArray.add("全部");
            mTextArray.add("地区");
            mTextArray.add("价格");
            mTextArray.add("匿名");
            mViewHeight.add(0.7);
            mViewHeight.add(0.71);
            mViewHeight.add(0.25);
            mViewHeight.add(0.3);
            mExpandtabView.setValue(mTextArray,mViewArray,mViewHeight);
            mExpandtabView.setTitle(mViewSort.getShowText(), 0,false);
            mExpandtabView.setTitle(mViewAddress.getShowText(), 1,false);
            mExpandtabView.setTitle(mViewPrice.getShowText(), 2,false);
            mExpandtabView.setTitle(mViewName.getShowText(), 3,false);

        }

        private void initListener() {
            mViewPrice.setOnSelectListener(new ViewPrice.OnSelectListener() {
                @Override
                public void getValue(String distance, String showText) {
                    onRefresh(mViewPrice, showText);
                }
            });
            mViewAddress.setOnSelectListener(new ViewAddress.OnSelectListener() {
                @Override
                public void getValue(String showText) {
                    onRefresh(mViewAddress,showText);
                }
            });
            mViewSort.setOnSelectListener(new ViewSort.OnSelectListener() {
                @Override
                public void getValue(String showText) {
                    onRefresh(mViewSort,showText);
                }
            });
            mViewName.setOnSelectListener(new ViewPrice.OnSelectListener() {
                @Override
                public void getValue(String distance, String showText) {
                    onRefresh(mViewName,showText);
                }
            });

        }

        private void onRefresh(View view, String showText) {

            mExpandtabView.onPressBack();
            int position = getPositon(view);
            if (position >= 0 && !mExpandtabView.getTitle(position).equals(showText)) {
                mExpandtabView.setTitle(showText, position,true);
            }
            Toast.makeText(MainActivity.this, showText, Toast.LENGTH_SHORT).show();

        }

        private int getPositon(View tView) {
            for (int i = 0; i < mViewArray.size(); i++) {
                if (mViewArray.get(i) == tView) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public void onBackPressed() {

            if (!mExpandtabView.onPressBack()) {
                finish();
            }

        }
}
