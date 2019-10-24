package com.example.bluetoothdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button enable,paireddevices,discoverdevices;
BluetoothAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intialising the bluetooth
        adapter=BluetoothAdapter.getDefaultAdapter();
        enable=findViewById(R.id.OnBluetooth);
        IntentFilter statefilter =new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver,statefilter);
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(adapter==null)
                {
                    Toast.makeText(MainActivity.this, "Not Suppourted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!adapter.isEnabled())
                    {
                        //we are requesting the bluetooth by using implicit intent
                        Intent n=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(n,1);
                    }
                    if(adapter.isEnabled())
                        Toast.makeText(MainActivity.this, "Bluetooth is Already Enabled", Toast.LENGTH_SHORT).show();
                }


            }

        });
    }
    //we use this thing in broadcast receiver because these will be running in the background always.
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action=intent.getAction();
            Toast.makeText(MainActivity.this, "Inside Receiver", Toast.LENGTH_SHORT).show();
            if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
            {
                int state=intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
                switch (state)
                {
                    case BluetoothAdapter.STATE_ON:
                        Toast.makeText(MainActivity.this, "Bluetooth is on", Toast.LENGTH_SHORT).show(); break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Toast.makeText(MainActivity.this, "Turning On", Toast.LENGTH_SHORT).show(); break;
                    case BluetoothAdapter.STATE_OFF:
                        Toast.makeText(MainActivity.this, "Bluetooth is Off", Toast.LENGTH_SHORT).show(); break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Toast.makeText(MainActivity.this, "Turning off", Toast.LENGTH_SHORT).show(); break;
                }
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "Bluetooth is Turned On", Toast.LENGTH_SHORT).show();
            if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, "Denied Permission", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
