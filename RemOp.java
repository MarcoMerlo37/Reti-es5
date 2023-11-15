import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemOp extends Remote {

	int conta_righe(String nomefile, int num) throws RemoteException;

	int elimina_riga(String nomefile, int num) throws RemoteException;

}