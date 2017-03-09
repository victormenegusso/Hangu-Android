package hangu.android.dao;

import java.util.ArrayList;
import java.util.List;

import hangu.android.entity.WebApp;

/**
 * Created by victor menegusso on 08/03/17.
 */

public class WebAppDAO {

    public List<WebApp> list(){
        List<WebApp> webApps = new ArrayList<>();

        webApps.add( new WebApp("globo","www.globo.com","get") );
        webApps.add( new WebApp("google","www.google.com","get") );
        webApps.add( new WebApp("facebook","www.facebook.com","get") );

        return webApps;
    }


}
