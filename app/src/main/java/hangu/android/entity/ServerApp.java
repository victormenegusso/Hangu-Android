package hangu.android.entity;

/**
 * Created by Victor Menegusso on 11/03/17.
 */

public class ServerApp {

    private String name;
    private String host;
    private int port;
    private String pathProcessStop;
    private String pathProcessStart;
    private Status status;

    public ServerApp() {
    }

    public ServerApp(String name, String host, int port, String pathProcessStop, String pathProcessStart) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.pathProcessStop = pathProcessStop;
        this.pathProcessStart = pathProcessStart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
