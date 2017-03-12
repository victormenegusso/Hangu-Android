package hangu.android.dao;

import java.util.ArrayList;
import java.util.List;

import hangu.android.entity.ServerApp;

/**
 * Created by Victor Menegusso on 12/03/17.
 */

public class ServerAppDAO {
    public List<ServerApp> list(){
        List<ServerApp> serverApps = new ArrayList<>();

        serverApps.add( new ServerApp("tomcat","192.168.25.178",8080,"/home/victor/Downloads/apache-tomcat-8.5.11/bin/shutdown.sh","/home/victor/Downloads/apache-tomcat-8.5.11/bin/startup.sh") );

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
        return serverApps;
    }
}
