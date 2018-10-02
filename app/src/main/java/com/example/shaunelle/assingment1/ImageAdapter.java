package com.example.shaunelle.assingment1;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

//new class
public class ImageAdapter extends BaseAdapter {
    private Context myContext;
    private List<Bitmap> myList;

    public ImageAdapter(Context c, List l) {
        myContext = c;
        myList = l;
    }

    public int getCount() {
        return myList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
//create new ImageView for item referenced by the Adapter


    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(myContext);
            //sets the height and width for the View
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            //declares that images should be cropped toward the center
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(myList.get(position));
        return imageView;
    }
}
