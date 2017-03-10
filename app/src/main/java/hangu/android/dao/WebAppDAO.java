package hangu.android.dao;

import java.util.ArrayList;
import java.util.List;

import hangu.android.entity.WebApp;

/**
 * Created by Victor Menegusso on 08/03/17.
 */

public class WebAppDAO {

    public List<WebApp> list(){
        List<WebApp> webApps = new ArrayList<>();

        webApps.add( new WebApp("globo","www.globo.com","get") );
        webApps.add( new WebApp("google","www.google.com","get") );
        webApps.add( new WebApp("facebook","www.facebook.com","get") );
        webApps.add( new WebApp("teste","192.168.1.180","get") );

        /*
        webApps.add( new WebApp("teste 2","192.168.1.181","get") );
        webApps.add( new WebApp("teste 3","192.168.1.182","get") );
        webApps.add( new WebApp("teste 4","192.168.1.183","get") );
        webApps.add( new WebApp("teste 5","192.168.1.184","get") );
        webApps.add( new WebApp("teste 6","192.168.1.185","get") );

        webApps.add( new WebApp("gizmodo","www.gizmodo.com.br","get") );
        webApps.add( new WebApp("omgubuntu","www.omgubuntu.com","get") );
        webApps.add( new WebApp("pucpr","www.pucpr.br","get") );
        */
        return webApps;
    }


}
