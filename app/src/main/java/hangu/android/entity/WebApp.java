package hangu.android.entity;

/**
 * Created by victor menegusso on 07/03/17.
 */

public class WebApp {
    private String name;
    private String url;
    private String httpMethod;

    public WebApp(){
    }

    public WebApp(String name, String url, String httpMethod) {
        this.name = name;
        this.url = url;
        this.httpMethod = httpMethod;
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
}
