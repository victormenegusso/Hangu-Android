package hangu.android.entity;

/**
 * Created by Victor Menegusso on 12/03/17.
 */

public enum Status {
    ONLINE("ONLINE"),
    WAIT_CONNECTION("WAIT_CONNECTION"),
    OFFLINE("OFFLINE")
    ;

    private final String text;

    private Status(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
