package com.hues.miris_hues_android.bot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hues.miris_hues_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A Chat Screen Activity
 */
public class ChatActivity extends BaseActivity {
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private String localToken = "";
    private String conversationId = "";
    private String primaryToken = "";
    private String botName = "";

    //keep the last Response MsgId, to check if the last response is already printed or not
    private String lastResponseMsgId = "";

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            pollBotResponses();
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private final int GOOGLE_STT = 1000, MY_UI = 1001;                //requestCode. 구글음성인식, 내가 만든 Activity
    private ArrayList<String> mResult;                                    //음성인식 결과 저장할 list
    private String mSelectedString;                                        //결과 list 중 사용자가 선택한 텍스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        initControls();
        google_STT();

        primaryToken = getMetaData(getBaseContext(), "botPrimaryToken");
        botName = getMetaData(getBaseContext(), "botName").toLowerCase();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        runnable.run();
    }

    @OnClick(R.id.voice)
    public void voice_btn() {
        google_STT();
    }

    public void google_STT() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);            //intent 생성
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());    //호출한 패키지
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");                            //음성인식 언어 설정
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "말을 하세요.");                     //사용자에게 보여 줄 글자

        startActivityForResult(i, GOOGLE_STT);//구글 음성인식 실행
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && (requestCode == GOOGLE_STT || requestCode == MY_UI)) {        //결과가 있으면
            showSelectDialog(requestCode, data);                //결과를 다이얼로그로 출력.
        } else {                                                            //결과가 없으면 에러 메시지 출력
            String msg = null;

            //내가 만든 activity에서 넘어오는 오류 코드를 분류
            switch (resultCode) {
                case SpeechRecognizer.ERROR_AUDIO:
                    msg = "오디오 입력 중 오류가 발생했습니다.";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    msg = "단말에서 오류가 발생했습니다.";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    msg = "권한이 없습니다.";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    msg = "네트워크 오류가 발생했습니다.";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    msg = "일치하는 항목이 없습니다.";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    msg = "음성인식 서비스가 과부하 되었습니다.";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    msg = "서버에서 오류가 발생했습니다.";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    msg = "입력이 없습니다.";
                    break;
            }

            if (msg != null)        //오류 메시지가 null이 아니면 메시지 출력
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    //결과 list 출력하는 다이얼로그 생성
    private void showSelectDialog(int requestCode, Intent data) {
        String key = "";
        if (requestCode == GOOGLE_STT)                    //구글음성인식이면
            key = RecognizerIntent.EXTRA_RESULTS;    //키값 설정
        else if (requestCode == MY_UI)                    //내가 만든 activity 이면
            key = SpeechRecognizer.RESULTS_RECOGNITION;    //키값 설정

        mResult = data.getStringArrayListExtra(key);        //인식된 데이터 list 받아옴.
        String[] result = new String[mResult.size()];            //배열생성. 다이얼로그에서 출력하기 위해
        mResult.toArray(result);                                    //	list 배열로 변환

        //1개 선택하는 다이얼로그 생성
        AlertDialog ad = new AlertDialog.Builder(this).setTitle("선택하세요.")
                .setSingleChoiceItems(result, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectedString = mResult.get(which);        //선택하면 해당 글자 저장
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        messageET.setText(mSelectedString);        //결과 출력
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        messageET.setText("");        //취소버튼 누르면 초기화
                        mSelectedString = null;
                    }
                }).create();
        ad.show();
    }


    public void pollBotResponses() {
        //Toast.makeText(getBaseContext(),
        //       "test",
        //     Toast.LENGTH_SHORT).show();
        String botResponse = "";
        if (conversationId != "" && localToken != "") {
            botResponse = getBotResponse();
            if (botResponse != "") {
                try {
                    JSONObject jsonObject = new JSONObject(botResponse);
                    String responseMsg = "";
                    Integer arrayLength = jsonObject.getJSONArray("activities").length();
                    String msgFrom = jsonObject.getJSONArray("activities").getJSONObject(arrayLength - 1).getJSONObject("from").get("id").toString();
                    String curMsgId = jsonObject.getJSONArray("activities").getJSONObject(arrayLength - 1).get("id").toString();

                    if (msgFrom.trim().toLowerCase().equals(botName)) {
                        if (lastResponseMsgId == "") {
                            responseMsg = jsonObject.getJSONArray("activities").getJSONObject(arrayLength - 1).get("text").toString();
                            AddResponseToChat(responseMsg);
                            lastResponseMsgId = curMsgId;
                        } else if (!lastResponseMsgId.equals(curMsgId)) {
                            responseMsg = jsonObject.getJSONArray("activities").getJSONObject(arrayLength - 1).get("text").toString();
                            AddResponseToChat(responseMsg);
                            lastResponseMsgId = curMsgId;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        handler.postDelayed(runnable, 1000 * 5);
    }


    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("Chat Bot");// Hard Coded
        sayHelloToClient();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                messageET.setText("");
                displayMessage(chatMessage);

                String conversationTokenInfo = startConversation();
                JSONObject jsonObject = null;

                if (conversationTokenInfo != "") {
                    try {
                        jsonObject = new JSONObject(conversationTokenInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //send message to bot and get the response using the api conversations/{conversationid}/activities
                if (jsonObject != null) {
                    try {
                        conversationId = jsonObject.get("conversationId").toString();
                        localToken = jsonObject.get("token").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (conversationId != "") {
                    sendMessageToBot(messageText);
                }

            }
        });
    }

    //Get the bot response by polling a GET to directline API
    private String getBotResponse() {
        //Only for demo sake, otherwise the network work should be done over an asyns task
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String UrlText = "https://directline.botframework.com/v3/directline/conversations/" + conversationId + "/activities";
        URL url = null;
        String responseValue = "";

        try {
            url = new URL(UrlText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            String basicAuth = "Bearer " + localToken;
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");

            int responseCode = urlConnection.getResponseCode(); //can call this instead of con.connect()
            if (responseCode >= 400 && responseCode <= 499) {
                throw new Exception("Bad authentication status: " + responseCode); //provide a more meaningful exception message
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                responseValue = readStream(in);
                Log.w("responseSendMsg ", responseValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return responseValue;
    }

    //sends the message by making it an activity to the bot
    private void sendMessageToBot(String messageText) {
        //Only for demo sake, otherwise the network work should be done over an asyns task
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String UrlText = "https://directline.botframework.com/v3/directline/conversations/" + conversationId + "/activities";
        URL url = null;

        try {
            url = new URL(UrlText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            String basicAuth = "Bearer " + localToken;

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("type", "message");
                jsonObject.put("text", messageText);
                jsonObject.put("from", (new JSONObject().put("id", "user1")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String postData = jsonObject.toString();

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-Length", "" + postData.getBytes().length);
            OutputStream out = urlConnection.getOutputStream();
            out.write(postData.getBytes());

            int responseCode = urlConnection.getResponseCode(); //can call this instead of con.connect()
            if (responseCode >= 400 && responseCode <= 499) {
                throw new Exception("Bad authentication status: " + responseCode); //provide a more meaningful exception message
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String responseValue = readStream(in);
                Log.w("responseSendMsg ", responseValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

    }


    //returns the conversationID
    private String startConversation() {
        //Only for demo sake, otherwise the network work should be done over an asyns task
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String UrlText = "https://directline.botframework.com/v3/directline/conversations";
        URL url = null;
        String responseValue = "";

        try {
            url = new URL(UrlText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            String basicAuth = "Bearer " + primaryToken;
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            responseValue = readStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return responseValue;
    }

    //read the chat bot response
    private String readStream(InputStream in) {
        char[] buf = new char[2048];
        Reader r = null;
        try {
            r = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder s = new StringBuilder();
        while (true) {
            int n = 0;
            try {
                n = r.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (n < 0)
                break;
            s.append(buf, 0, n);
        }

        Log.w("streamValue", s.toString());
        return s.toString();
    }


    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void sayHelloToClient() {

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hello");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    /*
    Add the bot response to chat window
     */
    private void AddResponseToChat(String botResponse) {
        ChatMessage message = new ChatMessage();
        //message.setId(2);
        message.setMe(false);
        message.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        message.setMessage(botResponse);
        displayMessage(message);
    }


    /*
    Get metadata from manifest file against a given key
     */
    public static String getMetaData(Context context, String name) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Metadata", "Unable to load meta-data: " + e.getMessage());
        }
        return null;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Chat Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

