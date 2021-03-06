package exemple2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dorsaf serveur qui attend la connexion d'un client sur un port X. Le
 *         serveur re�oit une requ�te d'un client et lui retourne un message en
 *         HTML Ex�cution: lancer le serveur et ensuite, ouvrez le navigateur et
 *         tapez l'adresse http://localhost:port. Observez le message sur le
 *         navigateur
 */

public class ServeurSocket {
	public static void main(String a[]) throws Exception {
		final int httpd = 8085;
		Socket socketCommunication = null;
		ServerSocket socketServeur = null;
		int compteurConnexion = 0;

		try {

			socketServeur = new ServerSocket(httpd);
			while(true){
			socketCommunication = socketServeur.accept();
			ConnexionClient connexionClient = new ConnexionClient(socketCommunication, compteurConnexion);
			connexionClient.start();
			compteurConnexion++;
		}
		}

		catch (IOException e) {
			System.out.print("Erreur Client" + e.getMessage());
		} finally {
			try {
				if (socketServeur != null)
					socketServeur.close();
				if (socketCommunication != null)
					socketCommunication.close();

			} catch (IOException e) {
				System.out.println("Erreur !" + e.getMessage());
			}
		}
	}
	
}