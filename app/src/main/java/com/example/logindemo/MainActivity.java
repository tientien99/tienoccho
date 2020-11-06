package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewLink;
    private Button buttonLogin;
    private Button buttonRegister;
    private MyDataBase myDataBase;
    private EditText editTextUser;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLink = findViewById(R.id.txtLink);
        textViewLink.setText(Html.fromHtml(getString(R.string.link)));
        textViewLink.setMovementMethod(LinkMovementMethod.getInstance());

        buttonLogin = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.btnRegister);

        editTextUser = findViewById(R.id.edtUserName);
        editTextPassword = findViewById(R.id.edtPassword);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityRegister.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataBase = Room.databaseBuilder(getApplicationContext(), MyDataBase.class, "project.db").allowMainThreadQueries().build();
                UserDAO userDAO = myDataBase.createUserDAO();
                List<User> list = userDAO.getAllUser();
                User user = new User();
                String username = editTextUser.getText().toString();
                String password = editTextPassword.getText().toString();
                for (User u : list) {
                    if (username.trim().equals(u.getUsername()) && password.trim().equals(u.getPassword())) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

//    public boolean checkUserInfo(){
//        myDataBase = Room.databaseBuilder(getApplicationContext(),MyDataBase.class,"project.db").allowMainThreadQueries().build();
//        UserDAO userDAO = myDataBase.createUserDAO();
//        List<User> list = userDAO.getAllUser();
//        User user = new User();
//        String username = user.getUsername().toUpperCase().toString();
//        String password = user.getPassword().toString();
//        for (User u :
//             list) {
//            if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
//
//            }
//        }
//        return false;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        myDataBase = Room.databaseBuilder(getApplicationContext(), MyDataBase.class, "project.db").allowMainThreadQueries().build();
        UserDAO userDAO = myDataBase.createUserDAO();
        List<User> list = userDAO.getAllUser();
        for (User u : list
        ) {
            getNameGender(u.isGender());
            System.out.println(u.getUsername());
        }
    }

    public void getNameGender(boolean a) {
        User user = new User();
        if (a) {
            System.out.println("Nam");
        } else {
            System.out.println("Nữ");
        }
    }
}