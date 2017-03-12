package hangu.android.entity;

/**
 * Created by Victor Menegusso on 12/03/17.
 */
public class HanguSocket {
    private int id;
    private String host;
    private int port;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
