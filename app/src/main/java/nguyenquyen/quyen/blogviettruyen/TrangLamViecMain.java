package nguyenquyen.quyen.blogviettruyen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class TrangLamViecMain extends AppCompatActivity {
    Button btndangxuat;
    TrangLamViecHelper trangLamViecHelper;
    ListView lv;
    ArrayList<TrangLamViec> arrayList;
    TrangLamViecAdapter adapter;
    EditText nhapGhiChu;
    Button addGhiChu;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_lam_viec_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

//        Ánh xạ
        lv=(ListView) findViewById(R.id.lv);
        arrayList= new ArrayList<>();
        adapter=new TrangLamViecAdapter(this,R.layout.trang_lam_viec,arrayList);
        lv.setAdapter(adapter);
        nhapGhiChu=(EditText) findViewById(R.id.nhapgc);
        addGhiChu=(Button) findViewById(R.id.addgc);
        auth=FirebaseAuth.getInstance();
        btndangxuat=(Button) findViewById(R.id.dx);

//        Nút ghi chú
        addGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ghichu=nhapGhiChu.getText().toString().trim();
                if(TextUtils.isEmpty(ghichu)){
                    Toast.makeText(TrangLamViecMain.this, "Bạn chưa nhập ghi chú", Toast.LENGTH_SHORT).show();
                    return;
                }
                trangLamViecHelper.QueryData("INSERT INTO NoiDung VALUES(null,'"+ghichu+"')");
                actionGetData();
            }
        });

//        Tạo database
        trangLamViecHelper =new TrangLamViecHelper(this,"Nội dung",null,1);

//        Khi mới tạo database thì trong database chưa có gì cả
//        -> Tạo bảng
        trangLamViecHelper.QueryData("Create table if not exists NoiDung(Id INTEGER PRIMARY KEY AUTOINCREMENT,TenNoiDung VARCHAR(200))");

//        Thêm dữ liệu
//        trangLamViecHelper.QueryData("INSERT INTO NoiDung VALUES(null,'Ghi chu 1')");

//        Hiển thị/Truy xuất dữ liệu
        actionGetData();

        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(TrangLamViecMain.this, DangNhap.class);
                startActivity(intent);
            }
        });

    }

    public void DiaLogUpdate(String ten, int id){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);
        EditText edtSua= (EditText) dialog.findViewById(R.id.edtSua);
        Button btnSua= (Button) dialog.findViewById(R.id.btnSua);
        Button btnXoa= (Button) dialog.findViewById(R.id.btnHuy);

        edtSua.setText(ten);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi= edtSua.getText().toString().trim();
                if(TextUtils.isEmpty(tenMoi)){
                    Toast.makeText(TrangLamViecMain.this, "Bạn chưa sửa lại ghi chú", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                trangLamViecHelper.QueryData("UPDATE NoiDung SET TenNoiDung = '"+tenMoi+"'WHERE Id = '"+id+"'");
                dialog.dismiss();
                actionGetData();
            }
        });
        dialog.show();
    }

    public void DiaLogDelete(String ten,int id){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Bạn có đồng ý xóa "+ten+" không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                trangLamViecHelper.QueryData("Delete from NoiDung Where Id='"+id+"'");
                actionGetData();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void actionGetData() {
        Cursor data=trangLamViecHelper.getData("SELECT * FROM NoiDung");
        arrayList.clear();
        while(data.moveToNext()){ //Move to next để di chuyển con trỏ đến hàng tiếp theo
            String ten=data.getString(1);
            int id=data.getInt(0);
//          Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
            arrayList.add(new TrangLamViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }

}