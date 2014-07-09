package com.alexvasilkov.foldablelayout.sample.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.NewCleanWeather.HomeActivity;
import com.NewCleanWeather.R;
import com.NewCleanWeather.SplashActivity;
import com.alexvasilkov.foldablelayout.sample.activities.UnfoldableDetailsActivity;

import java.util.Arrays;

public class PaintingsAdapter extends ItemsAdapter<Painting> implements View.OnClickListener {
    public UnfoldInterface unfoldInterface;
    public PaintingsAdapter(Context context) {
        super(context);
        setItemsList(Arrays.asList(Painting.getAllPaintings(context.getResources())));
    }

    @Override
    protected View createView(Painting item, int pos, ViewGroup parent, LayoutInflater inflater) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.image = Views.find(view, R.id.list_item_image);
        vh.image.setOnClickListener(this);
        vh.title = Views.find(view, R.id.list_item_title);
        view.setTag(vh);

        return view;
    }

    @Override
    protected void bindView(Painting item, int pos, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.image.setTag(item);
        vh.image.setBackground(convertView.getContext().getResources().getDrawable(item.getImageId()));
        vh.title.setText(item.getTitle());
    }

    @Override
    public void onClick(View view) {
        if(unfoldInterface!=null){
            unfoldInterface.openDetails(view, (Painting) view.getTag());
        }else{
            if (view.getContext() instanceof UnfoldInterface) {
                UnfoldInterface activity = (UnfoldInterface) view.getContext();
                activity.openDetails(view, (Painting) view.getTag());
            }
        }
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
    }

    public interface UnfoldInterface {
        public void openDetails(View view, Painting painting);
    }
}
