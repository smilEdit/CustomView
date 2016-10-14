package com.zlf.zview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @创建者 zlf
 * @创建时间 2016/10/12 18:58
 */

public class ViewSort extends LinearLayout implements ViewBaseAction {

    private ListView regionListView;
    private ListView plateListView;
    private ArrayList<String>               groups       = new ArrayList<String>();
    private LinkedList<String>              childrenItem = new LinkedList<String>();
    private SparseArray<LinkedList<String>> children     = new SparseArray<LinkedList<String>>();
    private TextAdapter      plateListViewAdapter;
    private TextAdapter      earaListViewAdapter;
    private OnSelectListener mOnSelectListener;
    private int    tEaraPosition  = 0;
    private int    tBlockPosition = 0;
    private String showString     = "全部类别";

    public ViewSort(Context context) {
        super(context);
        init(context);
    }

    public ViewSort(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void updateShowText(String showArea, String showBlock) {
        if (showArea == null || showBlock == null) {
            return;
        }
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).equals(showArea)) {
                earaListViewAdapter.setSelectedPosition(i);
                childrenItem.clear();
                if (i < children.size()) {
                    childrenItem.addAll(children.get(i));
                }
                tEaraPosition = i;
                break;
            }
        }
        for (int j = 0; j < childrenItem.size(); j++) {
            if (childrenItem.get(j).replace("不限", "").equals(showBlock.trim())) {
                plateListViewAdapter.setSelectedPosition(j);
                tBlockPosition = j;
                break;
            }
        }
        setDefaultSelect();
    }

    private int lastSelected = -1;

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_sort, this, true);
        regionListView = (ListView) findViewById(R.id.listView);
        plateListView = (ListView) findViewById(R.id.listView2);

        String[] arr = new String[]{"全部类别", "食品/饮料", "服饰/配饰", "生活服务", "数码", "艺术品", "交通工具"};
        for (int i = 0; i < arr.length; i++) {
            groups.add(arr[i]);
            LinkedList<String> tItem = new LinkedList<String>();
            for (int j = 0; j < 6; j++) {

                tItem.add(arr[i] + j + "列");

            }
            children.put(i, tItem);
        }

        earaListViewAdapter = new TextAdapter(context, groups,
                R.drawable.choose_sort_item_selector,
                R.drawable.choose_eara_item_selector);
        earaListViewAdapter.setTextSize(16);
        earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
        regionListView.setAdapter(earaListViewAdapter);
        earaListViewAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < children.size()) {
                    childrenItem.clear();
                    childrenItem.addAll(children.get(position));
                    if (lastSelected != -1) {

                    }
                    ((TextView) view).setTextColor(Color.rgb(255, 65, 87));
                    plateListViewAdapter.notifyDataSetChanged();
                }
            }
        });
        if (tEaraPosition < children.size())
            childrenItem.addAll(children.get(tEaraPosition));
        plateListViewAdapter = new TextAdapter(context, childrenItem,
                R.drawable.choose_item_right,
                R.drawable.choose_eara_item_selector);
        plateListViewAdapter.setTextSize(16);
        plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
        plateListView.setAdapter(plateListViewAdapter);
        plateListViewAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int position) {
                showString = childrenItem.get(position);
                if (mOnSelectListener != null) {
                    mOnSelectListener.getValue(showString);
                }
            }
        });
        if (tBlockPosition < childrenItem.size())
            showString = childrenItem.get(tBlockPosition);
        if (showString.contains("不限")) {
            showString = showString.replace("不限", "");
        }
        setDefaultSelect();
    }

    public void setDefaultSelect() {
        regionListView.setSelection(tEaraPosition);
        plateListView.setSelection(tBlockPosition);
    }

    public String getShowText() {
        return showString;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        public void getValue(String showText);
    }

    @Override
    public void hide() {
    }

    @Override
    public void show() {
    }

    @Override
    public void clear() {

    }
}
