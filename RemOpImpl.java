import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemOpImpl extends UnicastRemoteObject implements RemOp{

    public RemOpImpl() throws RemoteException{
        super();
    }

    public int conta_righe(String nomefile, int num) throws RemoteException{

        File file = new File(nomefile);
        if  (!file.exists())    throw new RemoteException();
        int count = 0;
        int numParole = 0;
        
        try{
            FileReader reader = new FileReader(nomefile);
            BufferedReader buf = new BufferedReader(reader);
            String riga = null;
            while   ((riga = buf.readLine()) != null){
                numParole=0;
                for (int i = 0; i < riga.length(); i++){
                    if  (riga.charAt(i) == ' ' || riga.charAt(i) == '\n'|| riga.charAt(i) == '\r'){
                        if  (i != riga.length() - 1){
                            i++;
                            while   ((riga.charAt(i) == ' ' || riga.charAt(i) == '\n'|| riga.charAt(i) == '\r') && i != riga.length() - 1)  i++;
                            numParole++;
                            i--;
                        }
                        else{
                            numParole++;
                        }
                    }
                }
                System.out.println("Numero parole nella riga: "+numParole);
                if  (numParole > num)   count++;
                /*String arrayRiga[] = riga.split(" ");
                if   (arrayRiga.length > num)  count++;*/
            }
            buf.close();
            return count;
        }
        catch   (IOException e){
            throw new RemoteException();
        }
    }

    public int elimina_riga(String nomefile, int num) throws RemoteException{
        return 2;
    }

    public static void main(String[] args){
        final int REGISTRYPORT = 1099;
		String registryHost = "localhost";
		String serviceName = "RemOp";
        String completeName = "//" + registryHost + ":" + REGISTRYPORT + "/" + serviceName;
        try {
			RemOpImpl serverRMI = new RemOpImpl();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
    }
}