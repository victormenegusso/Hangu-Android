package hangu.android.entity;

/**
 * Created by Victor Menegusso on 07/03/17.
 *
 * status -> Indicates the status of the application
 * checkInPeriod ->  Is the check period, if the value is 0 does not have automatic check.
 *
 */

public class WebApp {
    private int id;
    private String name;
    private String url;
    private String httpMethod;
    private Status status;
    private long checkInPeriod; //

    public WebApp(){
        this.status = Status.WAIT_CONNECTION;
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

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
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
