package com.example.madcamp_week1.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.madcamp_week1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class FragmentTimer extends Fragment {
    private View view;
    TextView textView;
    CountDownTimer countDownTimer;


    int MAX_KEY = 5;
    ImageView key_ar[] = new ImageView[MAX_KEY];
    int flag;


    final int TOTAL_COUNT = 60 * 1000; //총 시간 60초
    final int COUNT_DOWN_INTERVAL = 1000;  //시간 간격 1초

    LottieAnimationView animationView;

    //오타..
    //배경색을 없애기위해, AlertDialog 객체 사용
//    private CustomeDialogue customeDialogue;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    int random;
    double dValue;
    TextView custom_text;

    String arr[] ={
            "You need a mint. Like, bad",
            "Someone ahead of you in line will pay with a cheek.",
            "Psst! They're being paid to love you",
            "Error 404: Fortune not found",
            "You just ate cat",
            "This cookie fell on the ground.",
            "Pigeon poop burns the retina for 13 hours. You will learn this the hard way. ",
            "",""
    };

    private RelativeLayout container2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag_timer, container, false);

        builder = new AlertDialog.Builder(getActivity());
        final View layout = inflater.inflate(R.layout.activity_custome_dialogue, (ViewGroup)view.findViewById(R.id.layout_root));
        custom_text = layout.findViewById(R.id.text_fortune);
        ImageView button_share = layout.findViewById(R.id.button_share);
        ImageView button_screen_shot = layout.findViewById(R.id.button_screen_shot);
        RelativeLayout relative = layout.findViewById(R.id.layout_root);

        //save 버튼에 스크린샷으로 저장할 레이아웃 설정
        container2 = (RelativeLayout)layout.findViewById(R.id.layout_root);

        //dialog 초기 텍스트 설정
        custom_text.setText("HIHI");
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Log.v("check 2 : ", "hi");
            }
        });

        //save 버튼 리스너
        button_screen_shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container2.buildDrawingCache();
                Bitmap captureView = container2.getDrawingCache();
                FileOutputStream fos;
                String strFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/CAPTURE";
                File folder = new File(strFolderPath);
                if(!folder.exists()){
                    folder.mkdirs();
                }
                String strFilePath = strFolderPath + "/" + System.currentTimeMillis() + ".png";
                File fileCacheItem = new File(strFilePath);

                try{
                    fos = new FileOutputStream(fileCacheItem);
                    captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }finally {
                    Toast.makeText(getActivity(), "캡처완료!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //share 버튼
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container2.buildDrawingCache();
                Bitmap captureView = container2.getDrawingCache();
                FileOutputStream fos;
                String strFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/CAPTURE";
                File folder = new File(strFolderPath);
                if(!folder.exists()){
                    folder.mkdirs();
                }
                String strFilePath = strFolderPath + "/" + System.currentTimeMillis() + ".png";
                File fileCacheItem = new File(strFilePath);

                try{
                    fos = new FileOutputStream(fileCacheItem);
                    captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    Log.v("check 4 ss : ", "hi");
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }finally {
                    Toast.makeText(getActivity(), "캡처완료!", Toast.LENGTH_SHORT).show();
                }

                Intent share = new Intent(Intent.ACTION_SEND_MULTIPLE);
                Uri uri = Uri.fromFile(fileCacheItem);
                Log.v("sharing_intent_name: ", uri.toString());
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT, uri.toString());
                share.setType("image/*");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(share, "Share image using"));
                alertDialog.dismiss();
                Log.v("check 3 ss : ", "hi");
            }
        });

        //builder 객체를 사용하는 alertdialog 띄우기
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        //ImageView 배열에 각 key 의 값들 전달 및 초기화
        key_ar[0] = view.findViewById(R.id.key_1);
        key_ar[1] = view.findViewById(R.id.key_2);
        key_ar[2] = view.findViewById(R.id.key_3);
        key_ar[3] = view.findViewById(R.id.key_4);
        key_ar[4] = view.findViewById(R.id.key_5);


        //배열의 가장 마지막 요소의  index 를 point 하는 flag 변수
        flag = 4;

        //타이머 표시하는 textView
        textView = view.findViewById(R.id.textView);

        //선물열기 이벤트, 이미지 클릭시
        animationView = view.findViewById(R.id.gift_anim);
        animationView.setAnimation("gift_anim.json");
        animationView.loop(false);
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag >=0) {
                    animationView.setEnabled(false);
                    animationView.playAnimation();
                    if (flag == 4){
                        textView.setVisibility(View.VISIBLE);
                    }
                    key_ar[flag].setVisibility(View.INVISIBLE);
                    flag--;
                    Log.v("flag : ", String.valueOf(flag));
                    if (flag == 3) {
                        countDownTimer();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dValue = Math.random();
                            random = (int)(dValue*7);

                            custom_text.setText(arr[random]);
                            //custom dialogue 실행
                           // customeDialogue.show(getFragmentManager(), CustomeDialogue.TAG_EVENT_DIALOG);
                            alertDialog.show();
                            Log.v("check 1  : ", "hi");

                            animationView.setEnabled(true);
                        }
                    }, 2400);
                }
                else{
                    Toast.makeText(getActivity(), "지금은 열 수 없어요!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    /*public abstract class OnSingleClickListner implements View.OnClickListener{
        private static final long MIN_CLICK_INTERVAL = 3000;
        private long mLastClickTime = 0;

        public abstract void onSingleClick(View v);

        @Override
        public void onClick(View v) {
            long currentClickTime = SystemClock.uptimeMillis();
            long elapsedTime = currentClickTime - mLastClickTime;
            mLastClickTime = currentClickTime;

            if(elapsedTime > MIN_CLICK_INTERVAL){
                onSingleClick(v);
            }
        }
    }*/

    public void countDownTimer(){
        countDownTimer = new CountDownTimer(TOTAL_COUNT, COUNT_DOWN_INTERVAL){
            @Override
            public void onTick(long millisUntilFinished) {
                long remain_count = millisUntilFinished / 1000;     //종료까지 남은 시간
                Log.d("remain_count : ", remain_count +"");
                textView.setText(String.format("%02d",(remain_count / 60)) + " : " + String.format("%02d",(remain_count - ((remain_count / 60) * 60))));
            }
            @Override
            public void onFinish() {
                flag++;
                key_ar[flag].setVisibility(View.VISIBLE);
                Log.v("flag : ", String.valueOf(flag));
                if(flag < 4){
                    countDownTimer();
                }
                else{textView.setVisibility(View.INVISIBLE);}
            }
        }.start();
    }
}