package jp.co.hivelocity.togt;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.rabbitconverter.rabbit.Rabbit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import mm.technomation.tmmtextutilities.mmtext;

public class DisplayActivity extends AppCompatActivity {

    private Bundle extras;
    private Context context;

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
                tvZawgyi.setText(Rabbit.uni2zg(input));
                tvUni.setText(input);
            } else {
                tvZawgyi.setText(input);
                tvUni.setText(Rabbit.zg2uni(input));
            }

            mmtext.prepareView(context, tvZawgyi, mmtext.TEXT_ZAWGYI, true, true);
            mmtext.prepareView(context, tvUni, mmtext.TEXT_UNICODE, true, true);

        }
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
