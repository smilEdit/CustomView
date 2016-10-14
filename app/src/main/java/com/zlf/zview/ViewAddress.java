package com.zlf.zview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @创建者 zlf
 * @创建时间 2016/10/12 17:15
 */

public class ViewAddress extends RelativeLayout implements ViewBaseAction {
    private String[] mZhixiaArr = new String[]{"北京","上海","天津","重庆"};
    private String[] mShengArr = new String[]{"黑龙江", "吉林", "辽宁", "河北", "山西", "青海", "山东", "河南", "江苏", "安徽", "浙江", "福建",
            "江西", "湖南", "湖北", "广东", "台湾", "海南", "甘肃", "陕西", "四川", "贵州", "云南"};
    private String[] mZizhiArr = new String[]{"内蒙古", "新疆", "西藏", "宁海", "广西"};
    private String[] mTeBie = new String[]{"香港","澳门"};
    private OnSelectListener mOnSelectListener;
    private String showText = "全部地区";
    private Context      mContext;

    private List<String> mShengList = new ArrayList<>();
    private List<String> mZizhiList = new ArrayList<>();
    private List<String> mZhixiaList = new ArrayList<>();
    private List<String> mTebieList = new ArrayList<>();

    private RecyclerView mRvSheng;
    private RecyclerView mRvZizhi;
    private RecyclerView mRvTebie;
    private RecyclerView mRvZhixia;
    private TextView mTvClear;

    public String getShowText() {
        return showText;
    }

    public ViewAddress(Context context) {
        this(context, null);
    }

    public ViewAddress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewAddress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_address, this, true);
        mRvZhixia = (RecyclerView) findViewById(R.id.rv_address_zhixia);
        mRvSheng = (RecyclerView) findViewById(R.id.rv_address_sheng);
        mRvZizhi = (RecyclerView) findViewById(R.id.rv_address_zizhi);
        mRvTebie = (RecyclerView) findViewById(R.id.rv_address_tebie);
        mTvClear = (TextView) findViewById(R.id.tv_search_clear);
        initList(mZhixiaArr,mZhixiaList);
        initList(mShengArr,mShengList);
        initList(mZizhiArr,mZizhiList);
        initList(mTeBie,mTebieList);

        initRv(mRvZhixia, mZhixiaList);
        initRv(mRvSheng, mShengList);
        initRv(mRvZizhi, mZizhiList);
        initRv(mRvTebie, mTebieList);
    }

    private void initList(String[] addressArr,List<String> addressList) {
        for (int i = 0; i < addressArr.length; i++) {
            addressList.add(addressArr[i]);
        }
    }

    private void initRv(RecyclerView rv, final List<String>addressList) {
        rv.setLayoutManager(new GridLayoutManager(mContext, 5));
        SearchAddressAdapter shengAdapter = new SearchAddressAdapter(mContext, R.layout.item_address_text, addressList);
        rv.setAdapter(shengAdapter);
        shengAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mOnSelectListener != null) {
                    showText = addressList.get(position);
                    ((TextView)view).setTextColor(Color.rgb(255,65,87));
                    mOnSelectListener.getValue(showText);
                }
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mTvClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
