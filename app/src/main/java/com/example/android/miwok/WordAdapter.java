package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourseId;

    public WordAdapter(Activity context, ArrayList<Word> words,int resourceId){
        super(context,0,words);
        mColorResourseId = resourceId;


    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View listItemView = convertView;
       if(listItemView==null){
           listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
       }
       Word currentWord = getItem(position);
        TextView defalutTextView =(TextView) listItemView.findViewById(R.id.word_english);
        defalutTextView.setText(currentWord.getmDefaultTranslation());

        TextView miwokTextView =(TextView) listItemView.findViewById(R.id.word_miwok);
        miwokTextView.setText(currentWord.getmMiwokTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_list);

        if(currentWord.hasImage()){
            imageView.setImageResource(currentWord.getnImageResourseId());
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }
        View textContainer = listItemView.findViewById(R.id.text_container);
        int Color = ContextCompat.getColor(getContext(),mColorResourseId);
        textContainer.setBackgroundColor(Color);
        View img_Container = listItemView.findViewById(R.id.layout_play_button);

        img_Container.setBackgroundColor(Color);



        return listItemView;
    }
}
