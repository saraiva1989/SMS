package com.danielsaraiva1.sms_send;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class Sms {

    Context context;

    public Sms(Context context) {
        this.context = context;
    }

    public void sendSMS(String numero, String mensagem){
        String no= numero;
        String msg=mensagem;
        Intent intent=new Intent(context.getApplicationContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(context.getApplicationContext(), 0, intent,0);
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(no, null, msg, null,null);

        Toast.makeText(context.getApplicationContext(), context.getString(R.string.messageSendSuccess),
                Toast.LENGTH_LONG).show();
    }

        public ArrayList<SmsModel> getAllSms() {
        ArrayList<SmsModel> lstSms = new ArrayList<SmsModel>();
        SmsModel objSms = new SmsModel();
        Uri message = Uri.parse("content://sms/");

        Cursor cursor = context.getContentResolver().query(message, null,null,null,null);
        int totalSMS = cursor.getCount();
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new SmsModel();
                objSms.setId(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                objSms.setAddress(cursor.getString(cursor
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(cursor.getString(cursor.getColumnIndexOrThrow("body")));
                objSms.setReadState(cursor.getString(cursor.getColumnIndex("read")));
                objSms.setTime(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }
                lstSms.add(objSms);
                cursor.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        cursor.close();

        return lstSms;
    }
}
