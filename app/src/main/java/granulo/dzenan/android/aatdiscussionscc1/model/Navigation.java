package granulo.dzenan.android.aatdiscussionscc1.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by dzenang on 30.10.2017.
 */

public class Navigation extends RealmObject {

    @PrimaryKey
    private int id;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
