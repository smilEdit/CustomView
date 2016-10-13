//package com.zhaioto.sale.widget;
//
//import android.content.Context;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//
//import com.zhaioto.sale.R;
//import com.zhaioto.sale.adapter.TextAdapter;
//import com.zhy.adapter.recyclerview.CommonAdapter;
//import com.zhy.adapter.recyclerview.base.ViewHolder;
//import com.zlf.zview.ViewBaseAction;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * @创建者 zlf
// * @创建时间 2016/10/12 17:15
// */
//
//public class ViewAddress extends RelativeLayout implements ViewBaseAction {
//	private ListView mListView;
//	//    private final String[] items = new String[] { "价格从高到低", "价格从低到高" };//显示字段
//	//    private final String[] itemsVaule = new String[] { "1", "2"};//隐藏id
//	private OnSelectListener mOnSelectListener;
//	private TextAdapter      adapter;
//	private String           mDistance;
//	private String showText = "全部地区";
//	private Context mContext;
//	private RecyclerView mRecyclerView;
//	private List<String> mTextList;
//
//	public String getShowText() {
//		return showText;
//	}
//
//	public ViewAddress(Context context) {
//		this(context, null);
//	}
//
//	public ViewAddress(Context context, AttributeSet attrs) {
//		this(context, attrs, 0);
//	}
//
//	public ViewAddress(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		init(context);
//	}
//
//	private void init(Context context) {
//		mContext = context;
//		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		inflater.inflate(R.layout.view_address, this, true);
//		mTextList = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			mTextList.add("省份"+i);
//		}
//		mRecyclerView = (RecyclerView)findViewById(R.id.rv_address_sheng);
//
//		mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,5));
//		mRecyclerView.setAdapter(new CommonAdapter<String>(mContext,R.layout.item_address_text,mTextList) {
//			@Override
//			protected void convert(ViewHolder holder, String o, int position) {
//				holder.setText(R.id.tv_item_sheng,mTextList.get(position));
//			}
//		});
//		//        setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
//		//        mListView = (ListView) findViewById(R.id.listView);
//		//        adapter = new TextAdapter(context, items, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
//		//        adapter.setTextSize(DensityUtil.dip2px(mContext,12));
//		//        if (mDistance != null) {
//		//            for (int i = 0; i < itemsVaule.length; i++) {
//		//                if (itemsVaule[i].equals(mDistance)) {
//		//                    adapter.setSelectedPositionNoNotify(i);
//		//                    showText = items[i];
//		//                    break;
//		//                }
//		//            }
//		//        }
//		//        mListView.setAdapter(adapter);
//		//        adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
//		//
//		//            @Override
//		//            public void onItemClick(View view, int position) {
//		//
//		//                if (mOnSelectListener != null) {
//		//                    showText = items[position];
//		//                    mOnSelectListener.getValue(itemsVaule[position], items[position]);
//		//                }
//		//            }
//		//        });
//	}
//
//	public void setOnSelectListener(OnSelectListener onSelectListener) {
//		mOnSelectListener = onSelectListener;
//	}
//
//	public interface OnSelectListener {
//		public void getValue(String distance, String showText);
//	}
//
//	@Override
//	public void hide() {
//
//	}
//
//	@Override
//	public void show() {
//
//	}
//
//}
