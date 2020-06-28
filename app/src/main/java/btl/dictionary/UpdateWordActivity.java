package btl.dictionary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import btl.dictionary.Model.MainModel;

public class UpdateWordActivity extends AppCompatActivity {

    Button btnUpdate, btnBack;
    EditText etTu, etNghia, etPhatam, etLoaitu, etExam;
    MainModel mainModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        // Hiện nút back ở toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getControl();

        mainModel = new MainModel();
        String intent = getIntent().getExtras().getString("id_tu");
        final int id_tu = Integer.parseInt(intent);

        Cursor obj = mainModel.getTubyId(this, id_tu, "tbl_tucuatoi");
        etTu.setText(obj.getString(1));
        etNghia.setText(obj.getString(2));
        etPhatam.setText(obj.getString(5));
        etLoaitu.setText(obj.getString(6));
        etExam.setText(obj.getString(7));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tu = etTu.getText().toString();
                String nghia = etNghia.getText().toString();
                String phatam = etPhatam.getText().toString();
                String loaitu = etLoaitu.getText().toString();
                String vidu = etExam.getText().toString();

                ContentValues values = new ContentValues();
                values.put("tu", tu);
                values.put("nghia", nghia);
                values.put("phatam", phatam);
                values.put("loaitu", loaitu);
                values.put("example", vidu);

                if(tu.isEmpty() || nghia.isEmpty()){
                    Toast.makeText(UpdateWordActivity.this,"Hãy điền từ và nghĩa của từ",Toast.LENGTH_LONG).show();
                }
                else{
                    mainModel.updateWord(UpdateWordActivity.this, id_tu, values);
                    Toast.makeText(UpdateWordActivity.this,"Đã lưu chỉnh sửa",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UpdateWordActivity.this, MyWord.class);
                    startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateWordActivity.this, MyWord.class);
                startActivity(intent);
            }
        });
    }

    public void getControl(){
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnBack = (Button) findViewById(R.id.btnBack);
        etTu = (EditText) findViewById(R.id.etTu);
        etNghia = (EditText) findViewById(R.id.etNghia);
        etPhatam = (EditText) findViewById(R.id.etPhatam);
        etLoaitu = (EditText) findViewById(R.id.etLoaitu);
        etExam = (EditText) findViewById(R.id.etExam);
    }
}
