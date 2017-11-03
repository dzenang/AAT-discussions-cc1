package granulo.dzenan.android.aatdiscussionscc1.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dzenang on 30.10.2017.
 */

public class CC1Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialization of Realm
        Realm.init(this);
        // Change default Realm name default.realm and set our configuration as default
        RealmConfiguration config = new RealmConfiguration.Builder().name("navigation.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
