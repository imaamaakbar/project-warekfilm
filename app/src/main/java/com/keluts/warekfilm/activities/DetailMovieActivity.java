package com.keluts.warekfilm.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.keluts.warekfilm.R;
import com.keluts.warekfilm.model.ModelMovie;
import com.keluts.warekfilm.networking.ApiEndpoint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailMovieActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvTitle, tvName, tvRelease, tvPopularity, tvOverview;
    ImageView imgCover, imgPhoto;
    String NameFilm, ReleaseDate, Popularity, Overview, Cover, Thumbnail, movieURL;
    int Id;
    ModelMovie modelMovie;

    FloatingActionButton trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgCover = findViewById(R.id.imgCover);
        imgPhoto = findViewById(R.id.imgPhoto);

        tvTitle = findViewById(R.id.tvTitle);
        tvName = findViewById(R.id.tvName);

        tvRelease = findViewById(R.id.tvRelease);
        tvPopularity = findViewById(R.id.tvPopularity);
        tvOverview = findViewById(R.id.tvOverview);




        modelMovie = (ModelMovie) getIntent().getSerializableExtra("detailMovie");
        if (modelMovie != null) {

            Id = modelMovie.getId();
            NameFilm = modelMovie.getTitle();

            ReleaseDate = modelMovie.getReleaseDate();
            Popularity = modelMovie.getPopularity();
            Overview = modelMovie.getOverview();
            Cover = modelMovie.getBackdropPath();
            Thumbnail = modelMovie.getPosterPath();
            movieURL = ApiEndpoint.URLFILM + "" + Id;

            tvTitle.setText(NameFilm);
            tvName.setText(NameFilm);

            tvRelease.setText(ReleaseDate);
            tvPopularity.setText(Popularity);
            tvOverview.setText(Overview);
            tvTitle.setSelected(true);
            tvName.setSelected(true);
            trailer = findViewById(R.id.trailer);


            Glide.with(this)
                    .load(ApiEndpoint.URLIMAGE + Cover)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCover);

            Glide.with(this)
                    .load(ApiEndpoint.URLIMAGE + Thumbnail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPhoto);



        }

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.youtube.com/results?search_query=Trailer+"+NameFilm));
                startActivity(intent);
            }


        });


    }


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
