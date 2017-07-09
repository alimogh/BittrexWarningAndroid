package duongmh3.bittrexmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import duongmh3.bittrexmanager.service.BittrexCheckInfoIntentService;
import duongmh3.bittrexmanager.service.Util;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbPlaySound;
    private CheckBox cbVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbPlaySound = (CheckBox) findViewById(R.id.cbPlaySound);
        cbPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.setPlaySoundWhenWarning(cbPlaySound.isChecked());
            }
        });
        cbVibrate = (CheckBox) findViewById(R.id.cbVibrate);
        cbVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.setVibrateWhenWarning(cbVibrate.isChecked());
            }
        });

        View btStartWarningService = findViewById(R.id.btStartWarningUiService);
        btStartWarningService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.startServiceWarning(MainActivity.this);
            }
        });
        View btUpdateConfigWarning = findViewById(R.id.btUpdateConfigWarning);
        btUpdateConfigWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BittrexCheckInfoIntentService.getConfigWarningFromCloudAsync(new BittrexCheckInfoIntentService.CallbackGetConfig() {
                    @Override
                    public void onDoneEvent(final boolean success) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (success) {
                                    Toasty.success(getApplicationContext(), "Get config success.", Toast.LENGTH_SHORT, true).show();
                                } else {
                                    Toasty.error(getApplicationContext(), "Get config fail.", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cbPlaySound.setChecked(Util.isPlaySoundWhenWarning());
        cbVibrate.setChecked(Util.isVibrateWhenWarning());
    }
}
