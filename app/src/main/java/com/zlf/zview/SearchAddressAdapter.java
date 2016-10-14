package com.zlf.zview;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @创建者 zlf
 * @创建时间 2016/10/14 9:16
 */

public class SearchAddressAdapter extends CommonAdapter<String> {

    private List<String> mTextList;

    public SearchAddressAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.mTextList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, String o, int position) {
        holder.setText(R.id.tv_item_sheng,mTextList.get(position));
    }
}
