package com.pranjal.textrecongnition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class DetailActivity extends AppCompatActivity {

    TextView textViewResult;
    String text;
    ShimmerFrameLayout shimmer;
    LinearLayout linearLayout;
    View v1,v2,v3,v4,v5,v6,v7,v8,v9,v10;
    Button buttonClickAgain,buttonCopy;

    TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Recognised Text");
        setContentView(R.layout.activity_detail);

        textViewResult = findViewById(R.id.textViewResult);
        shimmer = findViewById(R.id.shimmer);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        v6 = findViewById(R.id.v6);
        v7 = findViewById(R.id.v7);
        v8 = findViewById(R.id.v8);
        v9 = findViewById(R.id.v9);
        v10 = findViewById(R.id.v10);
        buttonClickAgain = findViewById(R.id.clickAgain);
        buttonCopy = findViewById(R.id.copy);

        shimmer.startShimmer();

        Gson gson = new Gson();
        InputImage inputImage = gson.fromJson(getIntent().getStringExtra("imageJson"), InputImage.class);

        Task<Text> result = recognizer.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        shimmer.stopShimmer();
                        shimmer.hideShimmer();
                        v1.setVisibility(View.GONE);
                        v2.setVisibility(View.GONE);
                        v3.setVisibility(View.GONE);
                        v4.setVisibility(View.GONE);
                        v5.setVisibility(View.GONE);
                        v6.setVisibility(View.GONE);
                        v7.setVisibility(View.GONE);
                        v8.setVisibility(View.GONE);
                        v9.setVisibility(View.GONE);
                        v10.setVisibility(View.GONE);


                        Text outputText = visionText;
                        text = outputText.getText();
                        textViewResult.setText(text);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(DetailActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        buttonClickAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied data", text);
                clipboard.setPrimaryClip(clip);
            }
        });
    }
}