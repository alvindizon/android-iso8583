package com.alvindizon.androidiso8583.features.iso8583;

import android.content.Context;
import android.util.Log;

import com.alvindizon.androidiso8583.R;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;
import com.solab.iso8583.parse.ConfigParser;

import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ISO8583Helper {
    private static final String TAG = ISO8583Helper.class.getSimpleName();

    private final Context context;
    private MessageFactory<IsoMessage> mf = new MessageFactory<>();

    @Inject
    public ISO8583Helper(Context context) {
        this.context = context;
    }

    public void init(boolean useConfigFile) {
        try {
            if(useConfigFile) {
//                File xmlFile = FileUtils.createFileFromInputStream(context.getResources().openRawResource(R.raw.config), xmlFilePath);
                ConfigParser.configureFromReader(mf,
                        new InputStreamReader(context.getResources().openRawResource(R.raw.config)));
                Log.d(TAG, "init: success loading xml config file.");
            } else {
                mf = ConfigParser.createDefault();
                Log.d(TAG, "init: creating default MessageFactory");
            }
            mf.setTraceNumberGenerator(new SimpleTraceGenerator(1));
            mf.setAssignDate(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() {
        IsoMessage message = mf.newMessage(0x100);

    }
}
