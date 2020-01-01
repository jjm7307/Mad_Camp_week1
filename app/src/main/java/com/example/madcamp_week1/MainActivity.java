package com.example.madcamp_week1;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.madcamp_week1.adapters.ViewPagerAdapter;
import com.example.madcamp_week1.fragments.FragmentContacts;
import com.example.madcamp_week1.fragments.FragmentImage;
import com.example.madcamp_week1.fragments.FragmentTimer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.jetradarmobile.snowfall.SnowfallView;

public class MainActivity extends AppCompatActivity {

  private TabLayout tabLayout;
  private ViewPager viewPager;
  private ImageView snowvillage;
  private SnowfallView snow_effect;
  private LinearLayout background_color;
  private Animation fab_open, fab_close;
  private Boolean isFabOpen = false;
  private Boolean Night = true;
  private FloatingActionButton fab, fab1, fab2, fab3, fab4;
  private static MediaPlayer mp;
  int click = 0;

  private BackPressCloseHandler backPressCloseHandler;

  @Override
  public void onBackPressed() {
    backPressCloseHandler.onBackPressed();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    backPressCloseHandler = new BackPressCloseHandler(this);



    tabLayout = findViewById(R.id.tablayout);
    viewPager = findViewById(R.id.viewpager);
    snow_effect = findViewById(R.id.snoweffect);
    background_color = findViewById(R.id.backcolor);
    snowvillage = findViewById(R.id.snowvillage);


    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

    adapter.addFragment(new FragmentContacts(), "Contacts");
    adapter.addFragment(new FragmentImage(), "Images");
    adapter.addFragment(new FragmentTimer(), "gift box");

    fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
    fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

    fab = findViewById(R.id.fab);
    fab1 = findViewById(R.id.fab1);
    fab2 = findViewById(R.id.fab2);
    fab3 = findViewById(R.id.fab3);
    fab4 = findViewById(R.id.fab4);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        anim();
      }
    });

    fab1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(snow_effect.getVisibility()==View.VISIBLE){
          snow_effect.setVisibility(View.GONE);

        }
        else{
          snow_effect.setVisibility(View.VISIBLE);
        }
      }
    });

    fab2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(Night){
          background_color.setBackgroundColor(0xffeeeeee);
          Night=false;

        }
        else{
          background_color.setBackgroundColor(0xff0e0f0a);
          Night=true;
        }
      }
    });
    fab3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(snowvillage.getVisibility()==View.VISIBLE){
          snowvillage.setVisibility(View.INVISIBLE);

        }
        else{
          snowvillage.setVisibility(View.VISIBLE);
        }
      }
    });

    fab4 .setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if(click == 0){
            mp = MediaPlayer.create(getApplicationContext(), R.raw.jingle_bell_music_file);
            mp.start();
            click=1;
          }
          else{
            mp.stop();
            mp.reset();
            click =0;
          }
      }
    });

    viewPager.setAdapter(adapter);
    viewPager.setOffscreenPageLimit(3);

    tabLayout.setupWithViewPager(viewPager);
  }

  public void anim() {
    if (isFabOpen) {
      fab1.startAnimation(fab_close);
      fab2.startAnimation(fab_close);
      fab3.startAnimation(fab_close);
      fab4.startAnimation(fab_close);
      fab1.setClickable(false);
      fab2.setClickable(false);
      fab3.setClickable(false);
      fab4.setClickable(false);
      isFabOpen = false;
    } else {
      fab1.startAnimation(fab_open);
      fab2.startAnimation(fab_open);
      fab3.startAnimation(fab_open);
      fab4.startAnimation(fab_open);
      fab1.setClickable(true);
      fab2.setClickable(true);
      fab3.setClickable(true);
      fab4.setClickable(true);
      isFabOpen = true;
    }
  }

  public class BackPressCloseHandler{

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context){
      this.activity = context;
    }

    public void onBackPressed(){
      if(System.currentTimeMillis() > backKeyPressedTime + 2000){
        backKeyPressedTime = System.currentTimeMillis();
        showGuide();
        return;
      }
      if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
        activity.finish();
        toast.cancel();
      }
    }

    public void showGuide(){
      toast = Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르시면 종료됩니다. ", Toast.LENGTH_SHORT);
      toast.show();
    }
  }
}