package nguyenquyen.quyen.blogviettruyen;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {
    TextView sDangki,sQuenMK;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        link();
        initView();
        eventView();

        FirebaseAuth auth=FirebaseAuth.getInstance();
        Button Dnhap=(Button) findViewById(R.id.BTDN);
        TextView Dki=(TextView) findViewById(R.id.sDangki);
        EditText email=(EditText) findViewById(R.id.email);
        EditText MK= (EditText) findViewById(R.id.MatKhau);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(DangNhap.this, TrangLamViecMain.class));
            finish();
        }

//        Nút đăng kí
        Dki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveDK=new Intent(DangNhap.this,DangKi.class);
                startActivity(MoveDK);
            }
        });

//        Nhập tên đăng nhập
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    email.setError("Bạn chưa nhập tên đăng nhập");
                }else{
                    email.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        Nhập mật khẩu
        MK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    MK.setError("Bạn chưa nhập mật khẩu");
                }else{
                    MK.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        Nút đăng nhập
        Dnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_mail=email.getText().toString();
                String mk=MK.getText().toString();
                if(TextUtils.isEmpty(e_mail)){
                    Toast.makeText(DangNhap.this, "Chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mk)){
                    Toast.makeText(DangNhap.this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(e_mail,mk).addOnCompleteListener(DangNhap.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if (mk.length() < 6) {
                                MK.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(DangNhap.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(DangNhap.this, TrangLamViecMain.class);
                            startActivity(intent);
//                            finish();
                        }
                    }
                });
            }
        });
    }

    private void eventView() {
        sDangki.setPaintFlags(sDangki.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        sDangki.setText("Đăng kí");
        sQuenMK.setPaintFlags(sQuenMK.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        sQuenMK.setText("Quên mật khẩu");
    }

    private void initView() {
    }

    private void link() {
        sDangki = findViewById(R.id.sDangki);
        sQuenMK = findViewById(R.id.sQuenMK);
    }

}
