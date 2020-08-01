package com.example.tokyo2020.Fragments;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tokyo2020.R;

public class ContactUsFragment extends Fragment {

    View view;
    private final String SUPPORT_NUMBER = "647-540-8326";
    private final String SUPPORT_MESSAGE = "Hello, I have a question! Please call me at ";
    private final String SUPPORT_MAIL = "VedangNilotpal.Yagnik@GeorgeBrown.ca";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        //Call Store
        Button supportCallBtn = (Button) view.findViewById(R.id.supportCallBtn);
        supportCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSupport();
            }
        });

        //Send SMS
        Button sendSmsButton = (Button) view.findViewById(R.id.supportSendSmsBtn);
        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });

        //Send Email
        Button sendEmailButton = (Button) view.findViewById(R.id.supportSendEmailBtn);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        return this.view;
    }

    private void callSupport() {
        String formattedPhoneNumber = "tel:" + SUPPORT_NUMBER;
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse(formattedPhoneNumber));
        if (i.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(i);
        }
        else {
            Log.d("CALL_SUPPORT", "ERROR: Cannot find app that matches ACTION_CALL intent");
        }
    }

    private void sendSMS() {
        EditText customerPhoneNumber = (EditText) view.findViewById(R.id.customerPhoneNumber);
        String customerNumber = customerPhoneNumber.getText().toString();
        String formattedPhoneNumber = "smsto:" + SUPPORT_NUMBER;
        String message = SUPPORT_MESSAGE + customerNumber;
        String scAddress = null;
        PendingIntent sentIntent = null;
        PendingIntent deliveryIntent = null;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(SUPPORT_NUMBER, scAddress, message, sentIntent, deliveryIntent);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse(formattedPhoneNumber));
        i.putExtra("sms_body", message);

        if(i.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(i);
        } else {
            Log.d("MSG_SUPPORT", "ERROR: Cannot find app that matches ACTION_SEND intent");
        }
    }

    private void sendEmail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+ SUPPORT_MAIL));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("EMAIL_SUPPORT", "ERROR: Cannot find app that matches ACTION_SENDTO intent");
        }
    }
}