package com.govt_exam_preparation.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.govt_exam_preparation.model.ExamModel;
import com.govt_exam_preparation.model.QuizModel;
import com.govt_exam_preparation.model.SubjectModel;

import java.util.ArrayList;

/**
 * Created by wscubetech on 27/6/16.
 */
public class SqlClass {
    SQLiteDatabase db;
    static final String dbName = "exam_preparation.db";
    ContextWrapper wrapper;
    Context context;

    public SqlClass(ContextWrapper wrapper, Context context) {
        this.wrapper = wrapper;
        this.context = context;
    }

    public void createTableQuizQuestions() {
        try {
            db = wrapper.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);

            String createTableQuery = "create table if not exists "
                    + TableQuizModel.TABLE_NAME
                    + " ("
                    + TableQuizModel.QUIZ_ID + " Integer,"
                    + TableQuizModel.QUIZ_QUESTION + " varchar(255),"
                    + TableQuizModel.QUIZ_IMAGE_URL + " varchar(100),"
                    + TableQuizModel.QUIZ_OPTION_1 + " varchar(255),"
                    + TableQuizModel.QUIZ_OPTION_2 + " varchar(255),"
                    + TableQuizModel.QUIZ_OPTION_3 + " varchar(255),"
                    + TableQuizModel.QUIZ_OPTION_4 + " varchar(255),"
                    + TableQuizModel.QUIZ_CORRECT_OPTION + " varchar(1),"
                    + TableQuizModel.QUIZ_FAVORITE + " Integer,"
                    + TableQuizModel.QUIZ_EXAM_ID + " Integer,"
                    + TableQuizModel.QUIZ_EXAM_NAME + " varchar(255),"
                    + TableQuizModel.QUIZ_SUBJECT_ID + " Integer,"
                    + TableQuizModel.QUIZ_SUBJECT_NAME + " varchar(255)"
                    + ");";

            db.execSQL(createTableQuery);
            db.close();
        } catch (Exception e) {
            Log.v("CreateException", "" + e);
        }
    }

    public ContentValues getContentValues(QuizModel quizModel) {
        ContentValues cv = new ContentValues();
        cv.put(TableQuizModel.QUIZ_ID, quizModel.getQuesId());
        cv.put(TableQuizModel.QUIZ_QUESTION, quizModel.getQuesTitle());
        cv.put(TableQuizModel.QUIZ_IMAGE_URL, quizModel.getQuesImage());
        cv.put(TableQuizModel.QUIZ_OPTION_1, quizModel.getQuesOption1());
        cv.put(TableQuizModel.QUIZ_OPTION_2, quizModel.getQuesOption2());
        cv.put(TableQuizModel.QUIZ_OPTION_3, quizModel.getQuesOption3());
        cv.put(TableQuizModel.QUIZ_OPTION_4, quizModel.getQuesOption4());
        cv.put(TableQuizModel.QUIZ_CORRECT_OPTION, quizModel.getQuesCorrectSerialNo());
        cv.put(TableQuizModel.QUIZ_FAVORITE, quizModel.getFavorite() ? 1 : 0);
        cv.put(TableQuizModel.QUIZ_EXAM_ID, quizModel.getExamModel().getExamId());
        cv.put(TableQuizModel.QUIZ_EXAM_NAME, quizModel.getExamModel().getExamName());
        cv.put(TableQuizModel.QUIZ_SUBJECT_ID, quizModel.getSubjectModel().getSubjectId());
        cv.put(TableQuizModel.QUIZ_SUBJECT_NAME, quizModel.getSubjectModel().getSubjectName());
        return cv;
    }

    public void insertIntoTableQuiz(QuizModel quizModel) {
        try {
            db = wrapper.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            db.insert(TableQuizModel.TABLE_NAME, null, getContentValues(quizModel));
            db.close();
        } catch (Exception e) {
            Log.v("ExceptionInsert", "" + e);
        }
    }

    public void updateTableQuiz(QuizModel quizModel) {
        try {
            db = wrapper.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            db.update(TableQuizModel.TABLE_NAME, getContentValues(quizModel), TableQuizModel.QUIZ_ID + "=" + quizModel.getQuesId(), null);
            db.close();
        } catch (Exception e) {
            Log.v("ExceptionInsert", "" + e);
        }
    }

    public void checkToInsertOrUpdate(QuizModel quizModel) {
        try {
            db = wrapper.openOrCreateDatabase(dbName, Context.MODE_PRIVATE,
                    null);
            String query = "select * from "
                    + TableQuizModel.TABLE_NAME
                    + " where " + TableQuizModel.QUIZ_ID + "=" + quizModel.getQuesId();
            Cursor cur = db.rawQuery(query, null);

            if (cur.getCount() > 0) {
                updateTableQuiz(quizModel);
            } else {
                insertIntoTableQuiz(quizModel);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            Log.v("ExceptionCheck", "" + e);
        }
    }

    public Boolean selectQueryCheckFavorite(QuizModel quizModel) {
        try {
            db = wrapper.openOrCreateDatabase(dbName, Context.MODE_PRIVATE,
                    null);
            String query = "select * from "
                    + TableQuizModel.TABLE_NAME
                    + " where "
                    + TableQuizModel.QUIZ_ID + "=" + quizModel.getQuesId() + " and "
                    + TableQuizModel.QUIZ_FAVORITE + "=1";
            Cursor cur = db.rawQuery(query, null);

            if (cur.getCount() > 0) {
                cur.close();
                db.close();
                return true;
            } else {
                cur.close();
                db.close();
                return false;
            }

        } catch (Exception e) {
            Log.v("ExceptionChkFav", "" + e);
        }

        return false;
    }

    public ArrayList<QuizModel> selectQueryAllFavoriteQuiz() {
        ArrayList<QuizModel> arrayQuizModel = new ArrayList<>();
        try {
            db = wrapper.openOrCreateDatabase(dbName, Context.MODE_PRIVATE,
                    null);
            String query = "select * from "
                    + TableQuizModel.TABLE_NAME
                    + " where " + TableQuizModel.QUIZ_FAVORITE + "=1";
            Cursor cur = db.rawQuery(query, null);

            Log.v("Favorites", "" + cur.getCount());

            if (cur.moveToFirst()) {
                do
                {
                    QuizModel quizModel = new QuizModel();
                    quizModel.setQuesId(cur.getInt(0) + "");
                    quizModel.setQuesTitle(cur.getString(1));
                    quizModel.setQuesImage(cur.getString(2));
                    quizModel.setQuesOption1(cur.getString(3));
                    quizModel.setQuesOption2(cur.getString(4));
                    quizModel.setQuesOption3(cur.getString(5));
                    quizModel.setQuesOption4(cur.getString(6));
                    quizModel.setQuesCorrectSerialNo(cur.getString(7));

                    int favorite = cur.getInt(8);
                    quizModel.setFavorite(favorite == 1);

                    ExamModel examModel = new ExamModel();
                    examModel.setExamId(cur.getString(9));
                    examModel.setExamName(cur.getString(10));

                    SubjectModel subjectModel = new SubjectModel();
                    subjectModel.setSubjectId(cur.getString(11));
                    subjectModel.setSubjectName(cur.getString(12));

                    quizModel.setExamModel(examModel);
                    quizModel.setSubjectModel(subjectModel);

                    arrayQuizModel.add(quizModel);
                }
                while (cur.moveToNext());
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            Log.v("ExceptionSelect", "" + e);
        }
        return arrayQuizModel;
    }
}
