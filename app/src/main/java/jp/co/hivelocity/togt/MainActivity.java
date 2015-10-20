package jp.co.hivelocity.togt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.txtInput)
    EditText txtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnNext)
    public void goToDisplay() {
        String input = txtInput.getText().toString();
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(MainActivity.this, "Enter text!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            intent.putExtra("input", input);
            startActivity(intent);
        }
    }
}
