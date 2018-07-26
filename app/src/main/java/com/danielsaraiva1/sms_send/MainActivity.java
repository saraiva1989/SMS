package com.danielsaraiva1.sms_send;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.danielsaraiva1.sms_send.Recycler.LineAdapter;

import junit.framework.Test;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button sendSms;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS
    };

    Sms sms = new Sms(MainActivity.this);
    RecyclerView mRecyclerView;
    private LineAdapter mAdapter;
    int smsSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendSms = findViewById(R.id.btSendSms);

        setupRecycler();

        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidPermission androidPermission = new AndroidPermission(MainActivity.this, MainActivity.this);
                if(!androidPermission.hasPermissions(MainActivity.this, PERMISSIONS)){
                    androidPermission.ConcederPermissao();
                }
                else {
                    sms.sendSMS( getString(R.string.PhoneNumber), getString(R.string.SMSMessage));
                    SmsModel smsModel = new SmsModel();
                    ArrayList<SmsModel> ListaAtualSMS = sms.getAllSms();
                    if (ListaAtualSMS.size() > smsSize) {
                        smsModel.setAddress(getString(R.string.PhoneNumber));
                        smsModel.setMsg(getString(R.string.SMSMessage));
                        mAdapter.updateList(smsModel);
                    }

                }
            }
        });
    }

    private void setupRecycler() {

        ArrayList<SmsModel> ListaSMS = sms.getAllSms();
        smsSize = ListaSMS.size();
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.recycleSms);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        mAdapter = new LineAdapter(ListaSMS);
        mRecyclerView.setAdapter(mAdapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Toast.makeText(this, "Recycle Atualizada " + ListaSMS.size(), Toast.LENGTH_SHORT).show();
    }

}
