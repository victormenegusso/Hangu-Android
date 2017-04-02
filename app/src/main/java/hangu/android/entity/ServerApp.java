package hangu.android.entity;

import java.io.Serializable;

/**
 * Created by Victor Menegusso on 11/03/17.
 */

public class ServerApp implements Serializable{

    private int id;
    private String name;
    private String url;
    private String pathProcessStop;
    private String pathProcessStart;
    private HanguSocket hanguSocket;
    private Status status;
    private long checkInPeriod; //

    public ServerApp() {
        status = Status.WAIT_CONNECTION;
        checkInPeriod = 0;
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

    public long getCheckInPeriod() {
        return checkInPeriod;
    }

    public void setCheckInPeriod(long checkInPeriod) {
        this.checkInPeriod = checkInPeriod;
    }
}
