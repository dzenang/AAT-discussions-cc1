package granulo.dzenan.android.aatdiscussionscc1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import granulo.dzenan.android.aatdiscussionscc1.model.Navigation;
import io.realm.Realm;

/**
 * Created by dzenang on 26.10.2017.
 */

public class Activity2 extends AppCompatActivity {

    public static final int NAV_OBJECT_KEY = 1;
    public static final String ACTIVITY2_RESULT_KEY = "result";
    public static final String ACTIVITY2_RESULT_DATA = "B1";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2);

        Fragment2 f = new Fragment2();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, f).commit();

        // On click of button3 go back to MainActivity and show AlertDialog
        // Before go back, write data to realm, and pass id of written data
        Button button3 = (Button) findViewById(R.id.button3);

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            Navigation nav = realm.createObject(Navigation.class, NAV_OBJECT_KEY);
            nav.setResult(Activity2.ACTIVITY2_RESULT_DATA);
            realm.commitTransaction();
        } finally {
            realm.close();
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(ACTIVITY2_RESULT_KEY, NAV_OBJECT_KEY);
        setResult(Activity.RESULT_OK, returnIntent);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
