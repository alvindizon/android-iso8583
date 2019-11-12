package com.alvindizon.androidiso8583.application;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alvindizon.androidiso8583.R;
import com.alvindizon.androidiso8583.databinding.ActivityMainBinding;
import com.github.kpavlov.jreactive8583.IsoMessageListener;
import com.github.kpavlov.jreactive8583.client.Iso8583Client;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;

import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private MessageFactory<IsoMessage> mf = new MessageFactory<>();
//    BufferedOutputStream output;
//    BufferedReader input;
//    Socket socket;
//    RespListenThread listenThread = new RespListenThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initIso8583();

        binding.button.setOnClickListener(v -> new Thread(new SendIsoMessage()).start());
    }

    private void initIso8583() {
        try {
            ConfigParser.configureFromReader(mf,
                    new InputStreamReader(this.getResources().openRawResource(R.raw.config)));
//            mf = ConfigParser.createDefault();
            Log.d(TAG, "init: success loading xml config file.");
            mf.setUseBinaryMessages(true);
//            mf.setTraceNumberGenerator(new SimpleTraceGenerator(1));
//            mf.setAssignDate(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class SendIsoMessage implements Runnable {
        @Override
        public void run() {
            try {
                // Debit/BalInq ISO8583
                // use isoMessage.updateValue if you want to replace a value set in the template
//                IsoMessage isoMessage = mf.newMessage(0x100);
//                isoMessage.setBinaryIsoHeader(hexStringToByteArray("6009880000"));
//                isoMessage.setField(3, new IsoValue<>(IsoType.NUMERIC,"311000", 6 ));
//                isoMessage.setField(4, new IsoValue<>(IsoType.AMOUNT,"0"));
//                isoMessage.setField(5, new IsoValue<>(IsoType.AMOUNT,"0"));
//                isoMessage.setField(7, new IsoValue<>(IsoType.NUMERIC,"0814075724", 10));
//                isoMessage.setField(11, new IsoValue<>(IsoType.NUMERIC,"14", 6));
//                isoMessage.setField(12, new IsoValue<>(IsoType.NUMERIC,"155724", 6));
//                isoMessage.setField(13, new IsoValue<>(IsoType.NUMERIC,"0814", 4));
//                isoMessage.setField(22, new IsoValue<>(IsoType.NUMERIC,"901", 3));
//                isoMessage.setField(24, new IsoValue<>(IsoType.NUMERIC,"988", 3));
//                isoMessage.setField(25, new IsoValue<>(IsoType.NUMERIC,"51", 2));
//                isoMessage.setField(28, new IsoValue<>(IsoType.ALPHA,"D00000000", 9));
//                isoMessage.setField(35, new IsoValue<>(IsoType.ALPHA,"3562860808513007=22061011647613600000", 37));
//                isoMessage.setField(41, new IsoValue<>(IsoType.ALPHA,"50285030", 8));
//                isoMessage.setField(42, new IsoValue<>(IsoType.ALPHA,"869908200200014", 15));
//                isoMessage.setField(43, new IsoValue<>(IsoType.ALPHA,"00000000000000000000000000000000000000000000000000000000000000000000000000000000", 40));
//                isoMessage.setField(50, new IsoValue<>(IsoType.BINARY,"0608", 4));
//                isoMessage.setField(52, new IsoValue<>(IsoType.BINARY,"6e28cbe925858f29", "6e28cbe925858f29".length()));
//                isoMessage.setField(61, new IsoValue<>(IsoType.ALPHA,"15012000000", 11));
                IsoMessage isoMessage = mf.newMessage(0x100);
                isoMessage.setBinaryIsoHeader(hexStringToByteArray("6008770000"));
//                isoMessage.updateValue(3, "100000");
//                isoMessage.updateValue(11, "000040");
//                isoMessage.updateValue(22, "022");
//                isoMessage.updateValue(24, "877");
//                isoMessage.updateValue(25, "00");
//                isoMessage.updateValue(35, "3562860808513007=22061011647613600000");
//                isoMessage.updateValue(41, "10000001");
//                isoMessage.updateValue(42, "000000004000026");

                Log.d(TAG, "sendBalInq: " + isoMessage.debugString());
                
                InetAddress inetAddress = InetAddress.getByName("192.168.20.56");
                InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, 3828);
                Iso8583Client<IsoMessage> client = new Iso8583Client<>(socketAddress, mf);
                client.addMessageListener(new IsoMessageListener<IsoMessage>() {
                    @Override
                    public boolean applies(IsoMessage isoMessage) {
                        Log.d(TAG, "applies");
                        return isoMessage != null;
                    }

                    @Override
                    public boolean onMessage(ChannelHandlerContext ctx, IsoMessage isoMessage) {
                        Log.d(TAG, "onMessage: " + isoMessage.debugString());
                        runOnUiThread(() -> binding.receivedData.append(isoMessage.debugString()));
                        return false;
                    }
                });
                client.init();
                client.connect();
                if(client.isConnected()) {
                    client.send(isoMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

//    class RespListenThread implements Runnable {
//        volatile boolean isRunning = true;
//        @Override
//        public void run() {
//            while(isRunning) {
//                try {
//                    String message = input.readLine();
//                    if(!TextUtils.isEmpty(message)) {
//                        Log.d(TAG, "receive: " + message);
////                        runOnUiThread(() -> binding.receivedData.append(message);
//                    } else {
//                        Log.d(TAG, "disconnected");
//                        break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//        public void stop() {
//            isRunning = false;
//        }
//    }

//    private void testTcpSendListen(IsoMessage isoMessage) throws  Exception{
//        socket = new Socket("192.168.20.56", 3828);
//        output = new BufferedOutputStream(socket.getOutputStream());
//        input= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        output.write(isoMessage.writeData());
//        output.flush();
//        if(!listenThread.isRunning) {
//            new Thread(listenThread).start();
//        }
//    }

}
