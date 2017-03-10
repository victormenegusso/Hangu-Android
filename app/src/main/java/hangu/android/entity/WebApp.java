package hangu.android.entity;

/**
 * Created by Victor Menegusso on 07/03/17.
 */

public class WebApp {
    private String name;
    private String url;
    private String httpMethod;
    private STATUS status;

    public WebApp(){
    }

    public WebApp(String name, String url, String httpMethod) {
        this.name = name;
        this.url = url;
        this.httpMethod = httpMethod;
        this.status = STATUS.WAIT_CONNECTION;
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

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public enum STATUS {
        ONLINE("ONLINE"),
        WAIT_CONNECTION("WAIT_CONNECTION"),
        OFFLINE("OFFLINE")
        ;

        private final String text;

        private STATUS(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
