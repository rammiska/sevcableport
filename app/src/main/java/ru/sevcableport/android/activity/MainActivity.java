package ru.sevcableport.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import ru.sevcableport.android.R;
import ru.sevcableport.android.SevkabelApplication;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        findViewById(R.id.indoor_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIndoorActivity();
            }
        });

        findViewById(R.id.events_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEventsActivity();
            }
        });

        findViewById(R.id.residents_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startResidentsActivity();
            }
        });

        findViewById(R.id.residents_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startResidentsActivity();
            }
        });

        findViewById(R.id.food_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodActivity();
            }
        });

        findViewById(R.id.history_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHistoryActivity();
            }
        });

        final SevkabelApplication application = (SevkabelApplication) getApplication();

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d(TAG, "requirements fulfilled");
                                application.enableBeaconNotifications();
                                return null;
                            }
                        },
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override
                            public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e(TAG, "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e(TAG, "requirements error: " + throwable);
                                return null;
                            }
                        });
    }

    public void onIndoorClick(View view) {
        startIndoorActivity();
    }

    private void startIndoorActivity() {
        Intent intent = new Intent(this, IndoorActivity.class);
        startActivity(intent);
    }

    public void onEventsClick(View view) {
        startEventsActivity();
    }

    private void startEventsActivity() {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void onResidentsClick(View view) {
        startResidentsActivity();
    }

    private void startResidentsActivity() {
        Intent intent = new Intent(this, ResidentsActivity.class);
        startActivity(intent);
    }

    public void onFoodClick(View view) {
        startFoodActivity();
    }

    private void startFoodActivity() {

    }

    public void onHistoryClick(View view) {
        startHistoryActivity();
    }

    private void startHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
