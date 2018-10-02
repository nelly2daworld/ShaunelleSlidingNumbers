package com.example.shaunelle.assingment1;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    //private TextView mainText;
    // frame width
    private static final int FRAME_W = 300;
    // frame height
    private static final int FRAME_H = 300;
    Bitmap spriteSheet;
    private List<Bitmap> scrambledArray;
    //another array to compare the results
    private List<Bitmap> sortedArray;
    private ImageAdapter imageAdapter;
    GridView gridView;
    public int emptyBitmapPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //change drawables to bitmap
        spriteSheet = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.numbers_sprite_100);
        scrambledArray = new ArrayList<>();
        sortedArray = new ArrayList<>();
        //initializing adapter
        imageAdapter = new ImageAdapter(this, scrambledArray);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Clicked on position = " + position);
                //if position 1 was clicked by user
                //THE LEGAL MOVES INCLUDED HERE
                if (position + 1 == emptyBitmapPosition) {
                    Bitmap swap = scrambledArray.get(position);
                    scrambledArray.set(position, scrambledArray.get(emptyBitmapPosition));
                    scrambledArray.set(emptyBitmapPosition, swap);
                    emptyBitmapPosition = position;//update empty bitmap position to where it was updated
                    imageAdapter.notifyDataSetChanged();
                    checkSorted();//checks everytime the user clicks
                } else if (position - 1 == emptyBitmapPosition) {
                    Bitmap swap = scrambledArray.get(position);
                    scrambledArray.set(position, scrambledArray.get(emptyBitmapPosition));
                    scrambledArray.set(emptyBitmapPosition, swap);
                    emptyBitmapPosition = position;//update empty bitmap position to where it was updated
                    imageAdapter.notifyDataSetChanged();
                    checkSorted();//checks everytime the user clicks
                } else if (position + 3 == emptyBitmapPosition) {
                    Bitmap swap = scrambledArray.get(position);
                    scrambledArray.set(position, scrambledArray.get(emptyBitmapPosition));
                    scrambledArray.set(emptyBitmapPosition, swap);
                    emptyBitmapPosition = position;//update empty bitmap position to where it was updated
                    imageAdapter.notifyDataSetChanged();
                    checkSorted();//checks everytime the user clicks
                } else if (position - 3 == emptyBitmapPosition) {
                    Bitmap swap = scrambledArray.get(position);
                    scrambledArray.set(position, scrambledArray.get(emptyBitmapPosition));
                    scrambledArray.set(emptyBitmapPosition, swap);
                    emptyBitmapPosition = position;//update empty bitmap position to where it was updated
                    imageAdapter.notifyDataSetChanged();
                    checkSorted();//checks everytime the user clicks
                }

            }
        });

        scrambledArray.add(emptyBitmap()); //add the empty bitmap to the array
        //iterate through first row of image
        //Shuffle Array
        for (int i = 1; i < 9; i++) {
            //shuffleArray(scrambledArray);
            //boolean sorted = true; //assume array is sorted until proven otherwise
            //To obtain the green, we will change y to 300 + pixel size
            scrambledArray.add(Bitmap.createBitmap(spriteSheet,
                    i * FRAME_W, 0, FRAME_W, FRAME_H));
            //compare to see if the shuffled array is equal to the original sorted array
            //see of the 2 arrays are the same
        }
        //addAll elements from scrambledArray to the sortedArray
        sortedArray.addAll(scrambledArray);
        Bitmap tempBitmap = sortedArray.get(0);//store the emptybitmap position in temp
        //remove 0th position
        sortedArray.remove(0);
        sortedArray.add(tempBitmap);//add the emptyBitmap to the end of the list
        scrambledArray.clear();
        //addAll elements from sortedArray to the scrambledArray
        scrambledArray.addAll(sortedArray);//to know the blank space should be in the 8th place
        shuffleArray(scrambledArray);//shuffle the board
        for(int i = 0; i < 9; i++)
        {
            if(scrambledArray.get(i).sameAs(sortedArray.get(sortedArray.size() - 1)))
            {
                emptyBitmapPosition = i;//set the index to the emptybitmap to the emptybitmap location
            }
        }
    }
    public void checkSorted()
    {
        for (int i = 1 ; i < 9; i++)
        {
            if(!(scrambledArray.get(i).sameAs(sortedArray.get(i))))
            {
                return;
            }
        }
        scrambledArray.clear();
        for (int i = 1; i < 9; i++)
        {
            scrambledArray.add(Bitmap.createBitmap(spriteSheet,i * FRAME_W, 300 + FRAME_H,
                    FRAME_W, FRAME_H));
        }
        gridView.setEnabled(false);//disables the gridview so I won't be able to click it
        Toast.makeText(getApplicationContext(), "CONGRATULATIONS!", Toast.LENGTH_SHORT).show();
        imageAdapter.notifyDataSetChanged();
    }

    //empty bitmap for the empty space in grid
    public Bitmap emptyBitmap() {
        //create blank bitmap
        int w = FRAME_W, h = FRAME_H;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(w, h, config);//this creates a mutable bitmap
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        p.setColorFilter(f);
        canvas.drawBitmap(bmp, 0, 0, p);
        return bmp;
    }

//LifeCycle Methods
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() was called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() was called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() was called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() was called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() was called");
    }

    //Try to create shuffle function to make it posaible to solve
    static void shuffleArray(List<Bitmap> ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here

        for (int i = 2; i > 0; i--) {
            Random rand = new Random();
            int j = rand.nextInt(9) ;
            int index = rand.nextInt(ar.size() - 1);
            Bitmap a = ar.get(index);
            ar.set(index, ar.get(j));
            ar.set(j,a);
        }
    }
}