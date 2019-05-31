package com.example.komunikacja_sieciowa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String www;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListeners();

    }

    public void addListeners()
    {
        Button buttonGetFileInfo = findViewById(R.id.buttonGetInfo);
        buttonGetFileInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView url = findViewById(R.id.editTextURL);
                www = url.getText().toString();
                GetFileInfo fileInfo = new GetFileInfo(MainActivity.this);
                fileInfo.execute(new String[] {www});

            }
        });

    }

    public void setFileSizeLabel(String value)
    {
        TextView fileSize = findViewById(R.id.textViewFileSizeValue);
        fileSize.setText(value);
    }

    public void setFileTypeLabel(String value)
    {
        TextView fileType = findViewById(R.id.textViewFileTypeValue);
        fileType.setText(value);
    }

}
