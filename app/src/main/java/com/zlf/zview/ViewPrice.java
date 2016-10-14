package com.zlf.zview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * @创建者 zlf
 * @创建时间 2016/10/12 17:15
 */

public class ViewPrice extends RelativeLayout implements ViewBaseAction{
    private ListView mListView;
    private final String[] items = new String[] { "价格从高到低", "价格从低到高" };//显示字段
    private final String[] itemsVaule = new String[] { "1", "2"};//隐藏id
    private OnSelectListener mOnSelectListener;
    private TextAdapter      adapter;
    private String           mDistance;
    private String showText = "价格排序";
    private Context mContext;

    public String getShowText() {
        return showText;
    }

    public ViewPrice(Context context) {
        super(context);
        init(context);
    }

    public ViewPrice(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewPrice(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_price, this, true);
        mListView = (ListView) findViewById(R.id.listView);
        adapter = new TextAdapter(context, items, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
        adapter.setTextSize(16);
        if (mDistance != null) {
            for (int i = 0; i < itemsVaule.length; i++) {
                if (itemsVaule[i].equals(mDistance)) {
                    adapter.setSelectedPositionNoNotify(i);
                    showText = items[i];
                    break;
                }
            }
        }
        mListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                if (mOnSelectListener != null) {
                    showText = items[position];
                    mOnSelectListener.getValue(itemsVaule[position], items[position]);
                }
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        public void getValue(String distance, String showText);
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
