package com.govt_exam_preparation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;


import com.govt_exam_preparation.R;
import com.govt_exam_preparation.adapter.FavoritesAdapter;
import com.govt_exam_preparation.model.QuizModel;
import com.govt_exam_preparation.sql.SqlClass;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavoritesAdapter adapter;
    ArrayList<QuizModel> arrayQuizModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        init();
        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar("Favorites");
        toolbarOperation.showBackButton(true);

        getFavoritesFromLocalSql();
    }

    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    public void getFavoritesFromLocalSql() {
        SqlClass sqlClass = new SqlClass(this, this);
        arrayQuizModel = sqlClass.selectQueryAllFavoriteQuiz();
        adapter = new FavoritesAdapter(this, arrayQuizModel);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setDuration(800);
        recyclerView.setAdapter(animationAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
