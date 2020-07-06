package app.cntt.cnhp.hpwaterbarcodereader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.ThongTinTieuThu;
import utils.Server;

public class Report extends AppCompatActivity {

    Connection conn;
    private CustomAdapter adapter;
    public ArrayList<ThongTinTieuThu> customlistViewValueArray;
    ListView lstpro;
    String ms_tuyen = "";
    String ms_tk="";
    String ms_bd = "";
    int tongsoluong = 0;
    int soluongchudoc = 0;
    int soluongdadoc = 0;
    TextView tvtiledadoc;
    TextView tvtilechuadoc;
    EditText edtSearchName;
    Button btnSearchName;
    Button btnDocSo;
    String keysearch  = "";
    Spinner spidstimkiem;
    int positiondstk = 0;
    int positionloaitk = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        ms_tuyen = intent.getStringExtra("ms_tuyen");
        ms_tk =  intent.getStringExtra("ms_tk");
        ms_bd =  intent.getStringExtra("ms_bd");

        lstpro = (ListView) findViewById(R.id.lstpro);
        tvtilechuadoc = (TextView) findViewById(R.id.tvtilechuadoc);
        tvtiledadoc = (TextView) findViewById(R.id.tvtiledadoc);
        edtSearchName = (EditText) findViewById(R.id.edtsearchname);
        btnSearchName = (Button) findViewById(R.id.btnsearchname);
        btnDocSo = (Button) findViewById(R.id.btndocso);
        spidstimkiem = (Spinner)findViewById(R.id.spidstimkiem) ;

      //  spidanhsachloc.setOnItemSelectedListener(Report.this);
      //  spidstimkiem.setOnItemSelectedListener(Report.this);
        ArrayList<String> listTT = new ArrayList<String>();
        listTT.add("Danh sách điểm dùng");
        listTT.add("Danh sách chưa đọc");
        listTT.add("Danh sách đã đọc");

        ArrayList<String> listTT1 = new ArrayList<String>();
        listTT1.add("Tên");
        listTT1.add("Danh bạ");


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(Report.this, android.R.layout.simple_spinner_item, listTT1);
        spidstimkiem.setAdapter(dataAdapter1);


        spidstimkiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*switch (position) {
                    case 0:
                        getthongtindiemdung();
                        break;
                    case 1:
                        getthongtindiemdung2();
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                        break;
                }
                */
                positionloaitk = position;

                //getthongtindiemdung(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });





        btnDocSo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new IntentIntegrator(Report.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }


        });



        lstpro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ThongTinTieuThu tttt = customlistViewValueArray.get(i);

                Intent intent = new Intent(Report.this, ThongTinChiTiet.class);
                intent.putExtra("arrlist",customlistViewValueArray );
                intent.putExtra("ttct", tttt);
                intent.putExtra("ms_tuyen", ms_tuyen);
                intent.putExtra("ms_tk", ms_tk);
                startActivity(intent);

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if(item.getItemId() == R.id.chuadoc) {
            Intent intent = new Intent(Report.this, Login.class);
            intent.putExtra("ms_tuyen", ms_tuyen);
            intent.putExtra("ms_tk", ms_tk);
            intent.putExtra("ms_bd", ms_bd);
            intent.putExtra("tongsodiemdung",String.valueOf(tongsoluong) );
            startActivity(intent);
        }

        else if (item.getItemId()==R.id.tuyendoc){
            Intent intent = new Intent(Report.this, TuyenDoc.class);
            intent.putExtra("ms_tuyen", ms_tuyen);
            intent.putExtra("ms_tk", ms_tk);
            intent.putExtra("ms_bd", ms_bd);
            intent.putExtra("tongsodiemdung",String.valueOf(tongsoluong) );
            startActivity(intent);
        }else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }



}
