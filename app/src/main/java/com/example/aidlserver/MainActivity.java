package com.example.aidlserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aidlserver.bean.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText vNum1, vNum2, vNum3;
    private Button vButtonMenu;
    private Button vButtonMenu1;
    private IMyAidlInterface myAidlInterface;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindRemoteService();
    }

    private void bindRemoteService() {
        // Android 5.0 之后 不允许用 隐式的方式调用服务
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.aidlserver", "com.example.aidlserver.service.ServerRemoteService"));

        bindService(intent, conn, Context.BIND_AUTO_CREATE); //  Context.BIND_AUTO_CREATE 标志不用管服务端的 服务是否已经启动
    }

    private void initView() {
        vNum1 = findViewById(R.id.et_num1);
        vNum2 = findViewById(R.id.et_num2);
        vNum3 = findViewById(R.id.et_num3);
        vButtonMenu = findViewById(R.id.button);
        vButtonMenu1 = findViewById(R.id.button1);
        vButtonMenu.setOnClickListener(this);
        vButtonMenu1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            try {
                int i = myAidlInterface.addNum(Integer.parseInt(vNum1.getText().toString().trim()), Integer.parseInt(vNum2.getText().toString().trim()));
                vNum3.setText(i + "");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.button1) {
            try {
                ArrayList<Person> personArrayList = (ArrayList<Person>) myAidlInterface.addPerson(new Person("张三", 24));
                for (int i = 0; i < personArrayList.size(); i++) {
                    Person person = personArrayList.get(i);
                    Log.e("TAG", person.toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
