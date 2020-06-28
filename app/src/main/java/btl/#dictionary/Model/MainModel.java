package btl.dictionary.Model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainModel {
    public SQLiteDatabase database;

    public MainModel() {

    }

    // Lấy ra tất cả các từ có trong bảng từ theo ASC
    public Cursor getAll(Activity activity){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu ORDER BY UPPER(tu) ASC", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Lấy ra tất cả các từ có trong bảng từ theo DESC
    public Cursor getAllDesc(Activity activity){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu ORDER BY UPPER(tu) DESC", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Lấy ra tất cả các từ có trong bảng từ của tôi theo ASC
    public Cursor getMyWord(Activity activity){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tucuatoi ORDER BY UPPER(tu) ASC", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if (cursor.getCount() >0) {
            return cursor;
        }
        else{
            return null;
        }
    }

    // Lấy ra từ theo id, theo bảng
    public Cursor getTubyId(Activity activity, int id, String bang){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM " + bang + " WHERE _id = '" + id + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Update thời gian tra từ
    public void updateTime(Activity activity, int id, String solantra){
        database = Database.initDatabase(activity);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        ContentValues values = new ContentValues();
        int solan;
        solan = Integer.parseInt(solantra);
        solan++;
        values.put("solantra", solan);
        values.put("thoigiantra", strDate);
        database.update("tbl_tu", values,"_id = " + id, null);
    }

    // Lấy ra 7 từ tra gần nhất
    public ArrayList getListHistory(Activity activity){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu ORDER BY solantra DESC, thoigiantra DESC, UPPER(tu) ASC LIMIT 7", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        ArrayList<String> arr = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            arr.add(cursor.getString(1));
            Log.e("pr", cursor.getString(9));
        }
        return arr;
    }

    // Tìm kiếm
    public ArrayList searchInMain(Activity activity, String word){
        database = Database.initDatabase(activity);
        if (word.equals("")){
            return null;
        }
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu WHERE tu LIKE '%"+word+"%'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        ArrayList<String> arr = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            arr.add(cursor.getString(1));
        }
        return arr;
    }

    public Cursor getTuByTu(Activity activity, String tu){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu WHERE tu LIKE '" + tu + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getTuTVByTu(Activity activity, String nghia){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu WHERE nghia LIKE '" + nghia + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getTucuatoiByTu(Activity activity, String tu){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tucuatoi WHERE tu = '" + tu + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Thêm từ
    public void insertWord(Activity activity, ContentValues values){
        database = Database.initDatabase(activity);
        database.insert("tbl_tucuatoi",null, values);
    }

    // Lấy ra tất cả từ yêu thích
    public Cursor getTuQuanTrong(Activity activity, String order){
        database = Database.initDatabase(activity);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu WHERE trangthai LIKE 'yes' ORDER BY UPPER(tu) " + order, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void updateTrangthai(Activity activity, int id, String tt){
        database = Database.initDatabase(activity);
        ContentValues values = new ContentValues();
        values.put("trangthai", tt);
        database.update("tbl_tu", values,"_id = " + id, null);
    }

    // Sửa từ
    public void updateWord(Activity activity, int id, ContentValues values){
        database = Database.initDatabase(activity);
        database.update("tbl_tucuatoi", values,"_id = " + id, null);
    }

    // Xoá từ
    public void delWord(Activity activity, int id){
        database = Database.initDatabase(activity);
        database.delete("tbl_tucuatoi","_id = " + id, null);
    }

    public Cursor searchWord(Activity activity, String word){
        database = Database.initDatabase(activity);
        if (word.equals("")){
            return this.getAll(activity);
        }
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu WHERE tu LIKE '%"+word+"%'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor searchMyWord(Activity activity, String word){
        database = Database.initDatabase(activity);
        if (word.equals("")){
            return this.getMyWord(activity);
        }
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tucuatoi WHERE tu LIKE '%"+word+"%'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor searchWordLike(Activity activity, String word){
        database = Database.initDatabase(activity);
        if (word.equals("")){
            return getTuQuanTrong(activity, "ASC");
        }
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_tu WHERE tu LIKE '%"+word+"%' AND trangthai LIKE 'yes'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Dịch câu
    public Cursor transParagraph(Activity activity, String loai, String para){
        database = Database.initDatabase(activity);
        if (para.equals("")){
            return null;
        }
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_cauthuongdung WHERE "+loai+" LIKE '"+para+"'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if (cursor.getCount()==0){
            return null;
        }
        return cursor;
    }
}
