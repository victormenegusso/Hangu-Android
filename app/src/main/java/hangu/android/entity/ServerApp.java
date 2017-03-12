package hangu.android.entity;

/**
 * Created by Victor Menegusso on 11/03/17.
 */

public class ServerApp {

    private int id;
    private String name;
    private String url;
    private String pathProcessStop;
    private String pathProcessStart;
    private HanguSocket hanguSocket;
    private Status status;

    public ServerApp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPathProcessStop() {
        return pathProcessStop;
    }

    public void setPathProcessStop(String pathProcessStop) {
        this.pathProcessStop = pathProcessStop;
    }

    public String getPathProcessStart() {
        return pathProcessStart;
    }

    public void setPathProcessStart(String pathProcessStart) {
        this.pathProcessStart = pathProcessStart;
    }

    public HanguSocket getHanguSocket() {
        return hanguSocket;
    }

    public void setHanguSocket(HanguSocket hanguSocket) {
        this.hanguSocket = hanguSocket;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
