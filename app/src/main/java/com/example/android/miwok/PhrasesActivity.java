/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
   private MediaPlayer mediaPlayer;
   private AudioManager audioManager;
   private MediaPlayer.OnCompletionListener onCompletionListener= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
  private   AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener  = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||i== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                // pausing media player
                if(mediaPlayer!=null){
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);

                }


            }
            else if(i== AudioManager.AUDIOFOCUS_GAIN){
                // resume media player
                if(mediaPlayer!=null){
                    mediaPlayer.start();
                }

            } else if(i==AudioManager.AUDIOFOCUS_LOSS){
                // releasing the media player
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);



        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?", "minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinna oyaase'na",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michaksas?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.", "kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "aanas'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes I'm coming.", "haa’ aanam",R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming.", "aanam",R.raw.phrase_im_coming));
        words.add(new Word("Let's go.", "yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "anni'nem",R.raw.phrase_come_here));

        WordAdapter listAdapter = new WordAdapter(this,words,R.color.category_phrases);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word= words.get(i);
                int resourseId = word.getmAudioResourseId();
               releaseMediaPlayer();

               int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
               if(result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                   mediaPlayer = MediaPlayer.create(PhrasesActivity.this, resourseId);
                   mediaPlayer.start();
                   mediaPlayer.setOnCompletionListener(onCompletionListener);
               }

            }
        });
    }
    public void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer= null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
