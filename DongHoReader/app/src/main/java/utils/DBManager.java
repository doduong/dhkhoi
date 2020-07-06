package utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.google.zxing.common.StringUtils;

import java.util.ArrayList;

import app.cntt.cnhp.hpwaterbarcodereader.Login;
import app.cntt.cnhp.hpwaterbarcodereader.NhapSoBangTay;
import model.DiemDungKhongDoc;
import model.DiemDungMatMang;
import model.DongHoNoi;

public class DBManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "cnhp";
    private static final String TABLE_NAME = "dh_mnoi_temp";
    private static final String TABLE_KHONG_DOC = "DSKhongDoc";
    private static final String TABLE_MATMANG = "DSMatMang";
    private static final String MS_MNOI = "ms_mnoi";
    private static final String TEN_KH = "ten_kh";
    private static final String DIA_CHI = "dia_chi";
    private static final String MS_TK = "ms_tk";
    private static final String MS_TTRANG = "ms_ttrang";
    private static final String CHI_SO_CU = "chi_so_cu";
    private static final String CHI_SO_MOI = "chi_so_moi";
    private static final String NGAY_DOC_CU = "ngay_doc_cu";
    private static final String NGAY_DOC_MOI = "ngay_doc_moi";
    private static final String SO_TTHU = "so_tthu";
    //them vao bang mat mang
    private static final String TIEUTHU3T = "tieu_thu_3t";
    private static final String TIEUTHUTB = "tieu_thu_tb";
    private static final String SERI_DONG_HO = "seri_dh";
    private static final String MDSD = "mdsd";
    private static final String MA_NGUYEN_NHAN = "ma_nguyen_nhan";
    private static final String TEXT_NGUYEN_NHAN = "text_nguyen_nhan";
    private static final String TEXT_SO_DIEN_THOAI = "dien_thoai";
    private static final String KHONG_DOC_DUOC = "khong_doc_duoc";
    private static final String GHI_CHU = "ghi_chu";




    private static int VERSION = 1;
    //Tên cột bảng tạm lưu không đọc được
    private static final String MS_MNOI1 = "ms_mnoi";
    private static final String MS_TUYEN = "ms_tuyen";
    private static final String MS_TK1 = "ms_tk";
    private static final String MS_TINHTRANG = "ms_ttrang";
    private static final String TEN_KH1 = "ten_kh";
    private static final String NGAY = "ngay";
    private static final String ANH = "anh";
    //-------------------------------------------------
    private static final String CO_CHI_SO_MOI = "co_chi_so_moi";

    private String SQLQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+" ("+
            MS_MNOI + " integer primary key, " +
            MS_TUYEN + " TEXT, " +
            TEN_KH + " TEXT, " +
            DIA_CHI + " TEXT, " +
            MS_TK + " integer, "+
            MS_TTRANG + " integer, " +
            CHI_SO_CU + " integer, " +
            CHI_SO_MOI + " integer, " +
            NGAY_DOC_CU + " TEXT, " +
            NGAY_DOC_MOI + " TEXT, " +
            SO_TTHU + " integer ) ";

    private String SQLQuery_Create_DSKhongDoc = "CREATE TABLE IF NOT EXISTS " + TABLE_KHONG_DOC+" ("+
            MS_MNOI1 + " integer primary key, " +
            TEN_KH1 + " TEXT, " +
            MS_TUYEN + " integer, " +
            MS_TK1 + " integer, " +
            MS_TINHTRANG + " integer, "+
            NGAY + " TEXT, " +
            ANH + " BLOB  ) ";

    private String SQLQuery_MatMang = "CREATE TABLE IF NOT EXISTS " + TABLE_MATMANG+" ("+
            MS_MNOI + " integer primary key, " +
            TEN_KH + " TEXT, " +
            DIA_CHI + " TEXT, " +
            MS_TK + " integer, "+
            MS_TTRANG + " integer, " +
            CHI_SO_CU + " integer, " +
            CHI_SO_MOI + " integer, " +
            NGAY_DOC_CU + " TEXT, " +
            NGAY_DOC_MOI + " TEXT, " +
            SO_TTHU + " integer, " +
            MS_TUYEN +" integer, " +
            CO_CHI_SO_MOI + " integer, " +
            TIEUTHU3T + " TEXT, " +
            TIEUTHUTB + " TEXT, " +
            SERI_DONG_HO + " TEXT, " +
            MDSD + " TEXT, " +
            MA_NGUYEN_NHAN + " integer, " +
            TEXT_NGUYEN_NHAN + " TEXT, " +
            TEXT_SO_DIEN_THOAI + " TEXT, " +
            KHONG_DOC_DUOC + " integer, " +
            GHI_CHU + " TEXT, " +
            ANH + " BLOB  ) ";




    public DBManager(Context context) {
        super(context, DB_NAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
        sqLiteDatabase.execSQL(SQLQuery_Create_DSKhongDoc);
        sqLiteDatabase.execSQL(SQLQuery_MatMang);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_KHONG_DOC);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MATMANG);
        this.onCreate(sqLiteDatabase);

    }

    public int nhapso(DongHoNoi dhn){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("dhn.getSo_tthu()", String.valueOf(dhn.getSo_tthu()));

        try{
            db.beginTransaction();
        values.put(MS_MNOI, dhn.getMs_mnoi());
        values.put(MS_TUYEN, dhn.getMs_tuyen());
        values.put(TEN_KH, dhn.getTenkh() );
        values.put(DIA_CHI, dhn.getDiachi());
        values.put(MS_TK, dhn.getMs_tk());
        values.put(MS_TTRANG, dhn.getMs_ttrang());
        values.put(CHI_SO_CU, dhn.getChi_so_cu());
        values.put(CHI_SO_MOI, dhn.getChi_so_moi());
        values.put(NGAY_DOC_CU, dhn.getNgay_doc_cu());
        values.put(NGAY_DOC_MOI, dhn.getNgay_doc_moi());
        values.put(SO_TTHU, dhn.getSo_tthu());
        db.insertWithOnConflict(TABLE_NAME, null, values , SQLiteDatabase.CONFLICT_REPLACE);
        db.setTransactionSuccessful();

        }catch(SQLiteException e){
            return 0;
        }finally{
            db.endTransaction(); //Commit (if everything ok) or rollback (if any error occured).
            db.close(); //Close databse;
        }
        return 1;
    }

    public ArrayList<DongHoNoi> getAllDongHoNoi(int ms_tuyen){
        String tuyen = String.valueOf(ms_tuyen);
        ArrayList<DongHoNoi> list = new ArrayList<DongHoNoi>();
        String selectQuery =  "SELECT * FROM " + TABLE_NAME+" WHERE " +MS_TUYEN+ "=" +tuyen +" ORDER BY " +TEN_KH ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c  = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do {
                DongHoNoi dh = new DongHoNoi();
                dh.setMs_mnoi(c.getInt(0));
                dh.setMs_tuyen(c.getInt(1));
                dh.setTenkh(c.getString(2));
                dh.setDiachi(c.getString(3));
                dh.setMs_tk(c.getInt(4));
                dh.setMs_ttrang(c.getInt(5));
                dh.setChi_so_cu(c.getInt(6));
                dh.setChi_so_moi(c.getInt(7));
                dh.setNgay_doc_cu(c.getString(8));
                dh.setNgay_doc_moi(c.getString(9));
                dh.setSo_tthu(c.getInt(10));
                list.add(dh);

            } while (c.moveToNext());

        }
        db.close();
        return list;
    }

    public int deleteTableTemp(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,null, null);

    }

    public int deleteTableKhongDocDuoc(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_KHONG_DOC,null, null);

    }

    public int deleteTableMatMang(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MATMANG,null, null);

    }

    //Thêm trường hợp không đọc được

    public int ThemKhongDocDuoc(DiemDungKhongDoc ddkd) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into DSKhongDoc values (?,?,?,?,?,?,?)";

        try{
            db.beginTransaction();
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, ddkd.getMs_mnoi());
            statement.bindString(2, ddkd.getTen_kh());
            statement.bindLong(3, ddkd.getMs_tuyen());
            statement.bindLong(4, ddkd.getMs_tk());
            statement.bindLong(5, ddkd.getMs_ttrang());
            statement.bindString(6, ddkd.getNgay());
            statement.bindBlob(7, ddkd.getAnh());
            statement.executeInsert();
            db.setTransactionSuccessful();

        }catch(SQLiteException e){
            return 0;
        }finally{
            db.endTransaction(); //Commit (if everything ok) or rollback (if any error occured).
            db.close(); //Close databse;
        }
        return 1;
    }

    //bang tam truong hop khong doc duoc
    public ArrayList<DiemDungKhongDoc> getAllDiemDungKhongDoc(){

        ArrayList<DiemDungKhongDoc> list = new ArrayList<DiemDungKhongDoc>();
        String selectQuery =  "SELECT * FROM " + TABLE_KHONG_DOC;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c  = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do {
                DiemDungKhongDoc ddkd = new DiemDungKhongDoc();
                ddkd.setMs_mnoi(c.getInt(0));
                ddkd.setTen_kh(c.getString(1));
                ddkd.setMs_tuyen(c.getInt(2));
                ddkd.setMs_tk(c.getInt(3));
                ddkd.setMs_ttrang(c.getInt(4));
                ddkd.setNgay(c.getString(5))  ;
                ddkd.setAnh(c.getBlob(6));  ;

                list.add(ddkd);

            } while (c.moveToNext());

        }
        db.close();
        return list;
    }

    //Bang mat mang

    public int ThemDiemDungMatMang(DiemDungMatMang ddmm){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            db.beginTransaction();
            values.put(MS_MNOI, ddmm.getMs_mnoi());
            values.put(TEN_KH, ddmm.getTenkh() );
            values.put(DIA_CHI, ddmm.getDiachi());
            values.put(MS_TK, ddmm.getMs_tk());
            values.put(MS_TTRANG, ddmm.getMs_ttrang());
            values.put(CHI_SO_CU, ddmm.getChi_so_cu());
            values.put(CHI_SO_MOI, ddmm.getChi_so_moi());
            values.put(NGAY_DOC_CU, ddmm.getNgay_doc_cu());
            values.put(NGAY_DOC_MOI, ddmm.getNgay_doc_moi());
            values.put(SO_TTHU, ddmm.getSo_tthu());
            values.put(MS_TUYEN, ddmm.getMs_tuyen());
            values.put(CO_CHI_SO_MOI,ddmm.getCo_chi_so_moi());
            values.put(TIEUTHU3T, ddmm.getTieu_thu_3t());
            values.put(TIEUTHUTB, ddmm.getTieu_thu_tb());
            values.put(SERI_DONG_HO, ddmm.getSeri_dh());
            values.put(MDSD, ddmm.getMdsd());
            values.put(MA_NGUYEN_NHAN, ddmm.getMa_nguyen_nhan());
            values.put(TEXT_NGUYEN_NHAN, ddmm.getTextNguyenNhan());
            values.put(TEXT_SO_DIEN_THOAI, ddmm.getDien_thoai());
            values.put(KHONG_DOC_DUOC, ddmm.getKhongdocduoc());
            values.put(GHI_CHU, ddmm.getGhi_chu());
            values.put(ANH, ddmm.getAnh());
            db.insertWithOnConflict(TABLE_MATMANG, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.setTransactionSuccessful();
        }catch(SQLiteException e){
            Log.d("ERROR",e.getMessage());
            return 0;
        }finally{
            db.endTransaction(); //Commit (if everything ok) or rollback (if any error occured).
            db.close(); //Close databse;
        }
        return 1;
    }


    public ArrayList<DiemDungMatMang> getAllDiemDungMatMang(int ms_tuyen){
        String tuyen = String.valueOf(ms_tuyen);
        ArrayList<DiemDungMatMang> list = new ArrayList<DiemDungMatMang>();
        String selectQuery =  "SELECT * FROM " + TABLE_MATMANG + " WHERE "+MS_TUYEN+" = "+tuyen;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c  = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do {
                DiemDungMatMang ddmm = new DiemDungMatMang();
                ddmm.setMs_mnoi(c.getInt(0));
                ddmm.setTenkh(c.getString(1));
                ddmm.setDiachi(c.getString(2));
                ddmm.setMs_tk(c.getInt(3));
                ddmm.setMs_ttrang(c.getInt(4));
                ddmm.setChi_so_cu(c.getString(5));
                ddmm.setChi_so_moi(c.getString(6));
                ddmm.setNgay_doc_cu(c.getString(7));
                ddmm.setNgay_doc_moi(c.getString(8));
                ddmm.setSo_tthu(c.getString(9));
                ddmm.setMs_tuyen(c.getInt(10));
                ddmm.setCo_chi_so_moi(c.getInt(11));
                ddmm.setTieu_thu_3t(c.getString(12));
                ddmm.setTieu_thu_tb(c.getString(13));
                ddmm.setSeri_dh(c.getString(14));
                ddmm.setMdsd(c.getString(15));
                ddmm.setMa_nguyen_nhan(c.getInt(16));
                ddmm.setTextNguyenNhan(c.getString(17));
                ddmm.setDien_thoai(c.getString(18));
                ddmm.setKhongdocduoc(c.getInt(19));
                ddmm.setGhi_chu(c.getString(20));
                ddmm.setAnh(c.getBlob(21));

                list.add(ddmm);

            } while (c.moveToNext());

        }
        db.close();
        return list;
    }

    public Cursor getDiemDungMatMang(String i, int ms_tuyen) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
         cursor = db.rawQuery("SELECT * FROM DSMatMang Where MS_MNOI = "+ i + " AND "+ MS_TUYEN + " = " + ms_tuyen , null);
        if(cursor.getCount()>0){
            cursor.close();
            return cursor;

        }else{
            cursor.close();
            return null;
        }


    }

    public int  DeleteDiemDungMatMang (String  i){
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.delete(TABLE_MATMANG, MS_MNOI + " = " + i, null);
        } catch (SQLiteException e) {
            return 0;
        }
        database.close();
        return 1;

    }
    //End bảng mất mạng

    public boolean checkDiemDungMatMang(String i) {
        SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery("SELECT * FROM DSMatMang Where MS_MNOI = "+ i + " LIMIT 1 ", null);
            cursor.moveToFirst();

            // cursor.getInt(0) is 1 if column with value exists
            if (cursor.getInt(0) == 1) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }

    }

}
