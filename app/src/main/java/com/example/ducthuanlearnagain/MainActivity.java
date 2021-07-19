package com.example.ducthuanlearnagain;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;

import com.example.ducthuanlearnagain.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends Activity {


    ActivityMainBinding viewBinding;
    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    SharedPreferences sharedPreferences;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        viewBinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(viewBinding.getRoot());
        addSong();
        sharedPreferences = getSharedPreferences("dataPosition",MODE_PRIVATE);
        position = sharedPreferences.getInt("position",0);
        createMediaPlayer();
        mediaPlayer.seekTo(sharedPreferences.getInt("progress",0));

        animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_rotate);



        viewBinding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mediaPlayer.isPlaying()){
                   mediaPlayer.pause();
                   viewBinding.btnPlay.setImageResource(R.drawable.ic_icon_play);
                   viewBinding.imDis.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_alpha));
               }
               else{

                   mediaPlayer.start();
                   viewBinding.btnPlay.setImageResource(R.drawable.ic_pause_ic);
                   viewBinding.imDis.startAnimation(animation);
               }
                saveData();
               setTimeTotal();
               updateTimeSong();

            }
        });
        viewBinding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                viewBinding.btnPlay.setImageResource(R.drawable.ic_icon_play);
                mediaPlayer.release();
                createMediaPlayer();
            }
        });
        viewBinding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                position++;
                if(position==arraySong.size()){
                    position=0;
                }
                createMediaPlayer();
                mediaPlayer.start();
                viewBinding.btnPlay.setImageResource(R.drawable.ic_pause_ic);
                saveData();
                setTimeTotal();
                updateTimeSong();
                viewBinding.imDis.startAnimation(animation);
            }
        });
        viewBinding.btnRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                position--;
                if(position<0){
                    position=arraySong.size()-1;
                }
                createMediaPlayer();
                mediaPlayer.start();
                viewBinding.btnPlay.setImageResource(R.drawable.ic_pause_ic);
                setTimeTotal();
                updateTimeSong();
                viewBinding.imDis.startAnimation(animation);
            }
        });

        viewBinding.seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(viewBinding.seekBarSong.getProgress());
            }
        });


    }



    private void updateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                viewBinding.txtTimeNow.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                viewBinding.seekBarSong.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        viewBinding.btnForward.callOnClick();
                    }
                });
                saveData();
                handler.postDelayed(this,500);
            }
        },100);

    }

    private void setTimeTotal(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        viewBinding.txtTimeTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration())+"");
        viewBinding.seekBarSong.setMax(mediaPlayer.getDuration());
    }

    private void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position",position);
        editor.putInt("progress",mediaPlayer.getCurrentPosition());
        editor.commit();
    }

    private void createMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        viewBinding.txtTitle.setText(arraySong.get(position).getName());

    }

    private void addSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Anh vẫn luôn là lý do - Erik",R.raw.anhluonlalydo));
        arraySong.add(new Song("Đố anh đoán được - Bích Phương",R.raw.doanhdoandc));
        arraySong.add(new Song("Màu nước mắt - Erik",R.raw.maunuocmat));
        arraySong.add(new Song("Năm ấy - Đức Phúc ",R.raw.namay));
        arraySong.add(new Song("Nắm đôi bàn tay - Kay Trần",R.raw.namdoibantay));
        arraySong.add(new Song("Phố đã lên đèn - Huyền Tâm Môn [Cucak Remix] ",R.raw.phodalenden));
        arraySong.add(new Song("Sau tất cả - erik",R.raw.sautatca));
        arraySong.add(new Song("Yêu em dại khờ - Lou Hoàng",R.raw.yeuemdaikho));
    }


}
