package gkemon.binarygeek.com.voicetotext;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class SpeechRecognizerService extends Service{
    private static final String TAG ="GK" ;
    private SpeechRecognizerManager mSpeechManager;

    public SpeechRecognizerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {



        if(mSpeechManager==null)
        {
            SetSpeechListenerForContinuesListening();
        }
        else if(!mSpeechManager.ismIsListening())
        {
            mSpeechManager.destroy();
            SetSpeechListenerForContinuesListening();
        }


        return START_STICKY;

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    private void SetSpeechListenerForContinuesListening()
    {
        mSpeechManager=new SpeechRecognizerManager(this, new SpeechRecognizerManager.onResultsReady() {
            @Override
            public void onResults(ArrayList<String> results) {



                if(results!=null && results.size()>0)
                {

                    if(results.size()==1)
                    {


                        if(results.get(0).equals("hello music")){

                            Toast.makeText(SpeechRecognizerService.this,"Hello music",Toast.LENGTH_LONG).show();
                            Intent i = SpeechRecognizerService.this.getPackageManager().getLaunchIntentForPackage("binarygeek.tutorfinder");
                            SpeechRecognizerService.this.startActivity(i);

                        }


                    }
                    else {


                        if (results.size() > 5) {
                            results = (ArrayList<String>) results.subList(0, 5);
                        }
                        for (String result : results) {
                            if(result.equals("hello music")){

                                Toast.makeText(SpeechRecognizerService.this,"Hello music",Toast.LENGTH_LONG).show();

                                Intent i = SpeechRecognizerService.this.getPackageManager().getLaunchIntentForPackage("binarygeek.tutorfinder");
                                SpeechRecognizerService.this.startActivity(i);

                            }


                        }


                    }
                }
            }
        });
    }


}