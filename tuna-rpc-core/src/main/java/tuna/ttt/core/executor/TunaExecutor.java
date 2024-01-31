package tuna.ttt.core.executor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tuna.ttt.core.biz.ClientBiz;
import tuna.ttt.core.biz.client.ClientBizClient;
import tuna.ttt.core.server.EmbedServer;
import tuna.ttt.core.util.IpUtil;
import tuna.ttt.core.util.NetUtil;

import java.util.ArrayList;
import java.util.List;

/**
*Description 初始化客户端的各种基础信息
*DATA 2024-01-20
*@Author ttt
*/
public class TunaExecutor {

    private static final Logger log = LoggerFactory.getLogger(TunaExecutor.class);

    private String adminAddresses;

    private String ip;

    private int port;

    public String getAdminAddresses() {
        return adminAddresses;
    }

    public void setAdminAddresses(String adminAddresses) {
        this.adminAddresses = adminAddresses;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    // 初始化netty服务器
    private EmbedServer embedServer = null;
    private void initEmbedServer(String address, String ip, int port) throws Exception {
        port = port>0?port: NetUtil.findAvailablePort(9999);
        ip = (ip!=null&&ip.trim().length()>0)?ip: getIp();

        if (address==null || address.trim().length()==0) {
            String ip_port_address = IpUtil.getIpPort(ip, port);
            log.info("本机的ip地址{}",ip_port_address);
        }
        // start
        embedServer = new EmbedServer();
        embedServer.start(address, port);
    }

    public void start() throws Exception {

        // todo some code need study
        // 和远程服务端建立连接
        initClientBizList(adminAddresses);
        // init executor-server
        // 初始化 netty 服务器
        initEmbedServer(null,ip, port);
    }

    private static List<ClientBiz> clientBizList;  //存放远程主机的信息 集合
    private void initClientBizList(String addresses) throws Exception {
        if (addresses!=null && addresses.trim().length()>0) {
            for (String address: addresses.trim().split(",")) {
                if (address!=null && address.trim().length()>0) {

                    // 初始化远程机器的 ip + accessToken
                    ClientBiz clientBiz = new ClientBizClient(address.trim());
                    if (clientBizList == null) {
                        clientBizList = new ArrayList<ClientBiz>();
                    }
                    // 将 adminBiz添加到 list
                    log.info("将adminBiz 存放到list中=====>{}",clientBiz);
                    clientBizList.add(clientBiz);
                }
            }
        }
    }
}
