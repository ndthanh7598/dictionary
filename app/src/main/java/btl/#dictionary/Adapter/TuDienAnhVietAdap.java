package btl.dictionary.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import btl.dictionary.R;

public class TuDienAnhVietAdap extends CursorAdapter {

    public TuDienAnhVietAdap(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.tudienvietanh_custom, parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTienganh, tvTiengviet;
        ImageView ivLike;
        tvTienganh = (TextView)view.findViewById(R.id.tvTiengAnh);
        tvTiengviet = (TextView)view.findViewById(R.id.tvTiengViet);
        ivLike = (ImageView)view.findViewById(R.id.like);
        tvTienganh.setText(cursor.getString(1));
        tvTiengviet.setText(cursor.getString(2));
        if (cursor.getString(3).equals("no")){
            ivLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star));
        }
        else {
            ivLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_yellow));
        }
    }
}
