package com.example.aidlserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.aidlserver.IMyAidlInterface;
import com.example.aidlserver.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class ServerRemoteService extends Service {
    private List<Person> mPersonList = new ArrayList<>();

    public ServerRemoteService() {
    }

    private Binder mBinder = new IMyAidlInterface.Stub() {
        @Override
        public int addNum(int num1, int num2) throws RemoteException {
            return num1 + num2;
        }

        @Override
        public List<Person> addPerson(Person person) throws RemoteException {
            mPersonList.add(person);
            return mPersonList;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
