package btl.dictionary.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import btl.dictionary.ChiTietTu;
import btl.dictionary.Object.tudien;
import btl.dictionary.R;

public class TuQuanTrongAdap extends ArrayAdapter<tudien> {
    Activity context;
    int resource;
    List<tudien> objects;
    IOChildItemClick ioChildItemClick;
    TextToSpeech textToSpeech;

    public TuQuanTrongAdap(@NonNull Activity context, int resource, @NonNull List<tudien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public void registerItemClick(IOChildItemClick ioChildItemClick){
        this.ioChildItemClick = ioChildItemClick;
    }

    public void unRegisterItemClick(){
        this.ioChildItemClick = null;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        textToSpeech = new TextToSpeech(this.context.getApplication(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH); //set language
                }
            }
        });

        if (convertView == null) {
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.tuquantrong_custome, null);

            ViewHolderr viewHolderr = new ViewHolderr();
            
            viewHolderr.tvTienganh = convertView.findViewById(R.id.tvTiengAnh);
            viewHolderr.tvTiengviet = convertView.findViewById(R.id.tvTiengViet);
            viewHolderr.ibSpeak = convertView.findViewById(R.id.loa);
            viewHolderr.item = convertView.findViewById(R.id.item);

            convertView.setTag(viewHolderr);
        }
        final ViewHolderr viewHolderr = (ViewHolderr) convertView.getTag();

        //lay ra doi tuong tudien tai position
        final tudien tudien = objects.get(position);
        //hien thi cac gia tri tudien len view
        viewHolderr.tvTienganh.setText(tudien.getTentu());
        viewHolderr.tvTiengviet.setText(tudien.getNghia());

        //lang nghe su kien cua cac nut lenh
        viewHolderr.ibSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = viewHolderr.tvTienganh.getText().toString();
                textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        viewHolderr.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tudien td = getItem(position);
                int idtu = td.getId();
                Intent intent = new Intent(context, ChiTietTu.class);
                intent.putExtra("id_tu", String.valueOf(idtu));
                intent.putExtra("bang", "tbl_tu");
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolderr{
        //khai bao cac doi tuong view trong adapter
        TextView tvTienganh, tvTiengviet;
        ImageButton ibSpeak;
        LinearLayout item;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public tudien getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objects.indexOf(objects.get(position));
    }
}
