package granulo.dzenan.android.aatdiscussionscc1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import granulo.dzenan.android.aatdiscussionscc1.model.Navigation;
import granulo.dzenan.android.aatdiscussionscc1.retrofit.APIService;
import granulo.dzenan.android.aatdiscussionscc1.retrofit.Example;
import granulo.dzenan.android.aatdiscussionscc1.retrofit.Root;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder mBuilder;
    private static final int ACTIVITY2_REQ_CODE = 1;
    private static int mActivity2Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start new activity on button1 click
        Button button1 = (Button) findViewById(R.id.button1);
        final Intent intent = new Intent(this, Activity2.class);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, ACTIVITY2_REQ_CODE);
            }
        });

        // Prepare dialog builder, set common things for both dialogs
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Dialog");
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button, do nothing
            }
        });

        // Button2 to show dialog
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.setMessage("B2");
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });


        // Retrofit
        String baseurl = "http://58489054.ngrok.io/";

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        final Example example = new Example();
        Root root = new Root();
        example.setRoot(root);
        root.setNonRoot("dzenan");

        final APIService service = retrofit.create(APIService.class);
        Button postBtn = (Button) findViewById(R.id.post);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.savePost(example).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Log.i("MainActivity", "Post submitted to api!");
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("MainActivity", "Something went wrong with post operation.");
                    }
                });
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY2_REQ_CODE) {
            if(resultCode == Activity.RESULT_OK){
                mActivity2Result = data.getIntExtra(Activity2.ACTIVITY2_RESULT_KEY, 0);
                if (mActivity2Result != Activity2.NAV_OBJECT_KEY) {
                    Log.e("MainActivity", "Problem getting result data from Activity2");
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check in Realm if we got here from Activity2
        Realm realm = Realm.getDefaultInstance();
        String result = "";
        try {
            realm.beginTransaction();
            Navigation nav = realm.where(Navigation.class).equalTo("id", mActivity2Result).findFirst();
            if (nav != null) {
                result = nav.getResult();
            }
            realm.commitTransaction();
        } finally {
            realm.close();
        }

        if (!result.isEmpty()) {
            mBuilder.setMessage("B1");
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        }
    }

}
