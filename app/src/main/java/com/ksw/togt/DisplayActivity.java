package com.ksw.togt;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.rabbitconverter.rabbit.Rabbit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import mm.technomation.tmmtextutilities.mmtext;

public class DisplayActivity extends AppCompatActivity {

    private Bundle extras;
    private Context context;
    private String shareMessage = "";

    @Bind(R.id.tvZawgyi)
    TextView tvZawgyi;
    @Bind(R.id.tvUni)
    TextView tvUni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        context = this;
        ButterKnife.bind(this);

        extras = getIntent().getExtras();

        if (extras != null) {
            String input = extras.getString("input");

            if (detector(input) == 1) { // for unicode
                String uni2zg = Rabbit.uni2zg(input);
                tvZawgyi.setText(uni2zg);
                tvUni.setText(input);

                shareMessage = "[Unicode]\n";
                shareMessage += input;
                shareMessage += "[Zawgyi]\n";
                shareMessage += uni2zg + "\n\n";

            } else {
                String zg2uni = Rabbit.zg2uni(input);
                tvZawgyi.setText(input);
                tvUni.setText(zg2uni);

                shareMessage = "[Zawgyi]\n";
                shareMessage += input + "\n";
                shareMessage += "[Unicode]\n";
                shareMessage += zg2uni;
            }

            mmtext.prepareView(context, tvZawgyi, mmtext.TEXT_ZAWGYI, true, true);
            mmtext.prepareView(context, tvUni, mmtext.TEXT_UNICODE, true, true);

            Log.e("Message", shareMessage);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share Via"));
        } else if (item.getItemId() == R.id.copy) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = ClipData.newPlainText("copy", shareMessage);
            cm.setPrimaryClip(cd);
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public int detector(String string) {
        String isMyanmar = "[က-အ]+";
        Pattern isMyanmarPattern = Pattern.compile(isMyanmar);
        Matcher isMyanmarMatcher = isMyanmarPattern.matcher(string);
        if (!isMyanmarMatcher.find()) {
            return 0;
        }

        String uni =
                "[ဃငဆဇဈဉညတဋဌဍဎဏဒဓနဘရဝဟဠအ]်|ျ[က-အ]ါ|ျ[ါ-း]|[^\\1031]စ် |\\u103e|\\u103f|\\u1031[^\\u1000-\\u1021\\u103b\\u1040\\u106a\\u106b\\u107e-\\u1084\\u108f\\u1090]|\\u1031$|\\u100b\\u1039|\\u1031[က-အ]\\u1032|\\u1025\\u102f|\\u103c\\u103d[\\u1000-\\u1001]";
        Pattern pattern = Pattern.compile(uni);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return 1;
        }
        return 2;
    }
}
