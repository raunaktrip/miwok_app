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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    MediaPlayer  mMediaPlayer;
    // audiofocus
    AudioManager audioManager ;
   MediaPlayer.OnCompletionListener mMediaPlayerOnCompleteListener= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

            releaseMediaPlayer();

        }
    };

   AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener  = new AudioManager.OnAudioFocusChangeListener() {
       @Override
       public void onAudioFocusChange(int i) {
           if(i== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||i== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
               // pausing media player
               if(mMediaPlayer!=null){
                   mMediaPlayer.pause();
                   mMediaPlayer.seekTo(0);
               }



           }
           else if(i== AudioManager.AUDIOFOCUS_GAIN){
               // resume media player
               if(mMediaPlayer!=null){
                   mMediaPlayer.start();
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

         audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);


         final ArrayList<Word> words = new ArrayList<Word>();

        // initializing the String array
        words.add(new Word("one","lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","oyylisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","temmokka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));


        // adding textViews dynamically
        WordAdapter itemAdapter = new WordAdapter(this, words,R.color.category_numbers);
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Word word = words.get(i);
               int resourseId = word.getmAudioResourseId();
                releaseMediaPlayer();
               // getting audio focus
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){



                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,resourseId);
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mMediaPlayerOnCompleteListener);

                }



            }
        });

    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            // abandoning the onfocus change listener
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
    }
}
