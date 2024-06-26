package com.example.quiz_appv20;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionTextView, questionTextView, resultTextView;
    Button ansA, ansB, ansC, ansD, btnSubmit, btnSkip, btnRestart;
    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        totalQuestionTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        resultTextView = findViewById(R.id.result_text_view);
        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansC = findViewById(R.id.ans_c);
        ansD = findViewById(R.id.ans_d);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSkip = findViewById(R.id.btn_skip);
        btnRestart = findViewById(R.id.btn_restart);


        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        btnRestart.setOnClickListener(this);


        loadNewQuestion();
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        totalQuestionTextView.setText("Question: " + (currentQuestionIndex + 1) + " / " + totalQuestion);
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        selectedAnswer = "";
        resultTextView.setVisibility(View.GONE);
        resetButtonColors();
    }

    private void resetButtonColors() {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);
    }

    private void finishQuiz() {
        String passStatus = score >= totalQuestion * 0.6 ? "Passed" : "Failed";

        new AlertDialog.Builder(this)
                .setTitle("Quiz Finished")
                .setMessage("Your Score is " + score + " Out of " + totalQuestion + ". " + passStatus)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartQuiz();
                    }
                })
                .setNegativeButton("Exit From App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            handleSubmitButton();
        } else if (view.getId() == R.id.btn_skip) {
            handleSkipButton();
        } else if (view.getId() == R.id.btn_restart) {
            restartQuiz();
        } else {
            handleAnswerButton((Button) view);
        }
    }

    private void handleSubmitButton() {
        if (!selectedAnswer.isEmpty()) {
            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                score++;

            } else {

            }
            resultTextView.setVisibility(View.VISIBLE);
            moveToNextQuestion();
        }
    }

    private void handleSkipButton() {
        moveToNextQuestion();
    }

    private void handleAnswerButton(Button clickedButton) {
        resetButtonColors();
        selectedAnswer = clickedButton.getText().toString();
        clickedButton.setBackgroundColor(Color.YELLOW);
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < totalQuestion) {
            loadNewQuestion();
        } else {
            finishQuiz();
        }
    }
}
