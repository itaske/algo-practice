import java.net.NetPermission;
import java.net.NetworkInterface;
import java.net.SocketException;

public class Main {

    public static void main(String[] args) throws SocketException {
	// write your code here
        while(NetworkInterface.getNetworkInterfaces().hasMoreElements()){
            NetworkInterface networkInterface = NetworkInterface.getNetworkInterfaces().nextElement();
//            System.out.println(networkInterface);
            NetPermission n = new NetPermission(networkInterface.getName());
            System.out.println(n.getActions());
        }

    }
}
