package com.NewCleanWeather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yrguo on 2014/7/7.
 */
public class DrawerListAdapter extends BaseAdapter{
    public final List<DreawerItem> dreawerItems = new ArrayList<DreawerItem>();
    private final Context context;
    public DrawerListAdapter(Context paramContext)
    {
        context = paramContext;
    }
    @Override
    public int getCount() {
        return dreawerItems.size();
    }

    @Override
    public DreawerItem getItem(int position) {
        return dreawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dreawerItems.get(position).index;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null)
        {
            paramView = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, paramViewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = ((ImageView)paramView.findViewById(R.id.icon));
            viewHolder.textView = ((TextView)paramView.findViewById(R.id.title));
            paramView.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder)paramView.getTag();
        DreawerItem item = getItem(paramInt);
        viewHolder.imageView.setImageResource(item.resId);
        viewHolder.textView.setText(item.text);
        return paramView;
    }
    private static final class ViewHolder
    {
        public ImageView imageView;
        public TextView textView;
    }
    public static final class DreawerItem
    {
        public final long index;
        final int resId;
        public final String text;

        public DreawerItem(long index, int resId, String text)
        {
            this.resId = resId;
            this.index = index;
            this.text = text;
        }
    }
}
