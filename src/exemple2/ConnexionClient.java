package exemple2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnexionClient extends Thread {
	Socket socketCommunication;
	PrintWriter out = null; // le flux de sortie de socket
	BufferedReader in = null;
	int compteur;

	ConnexionClient(Socket socketCommunication, int compteur) {
		this.compteur = compteur;
		this.socketCommunication = socketCommunication;
		try {
			out = new PrintWriter(socketCommunication.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socketCommunication.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void run(){
		compteur++;
		String chemin = getEntete();
		if(chemin.equals("/")){
		envoiReponse();
		}else{
			
		}
		fermetureFlux();
	}

	private String getEntete() {

		String s = null;
		String chemin = "";
		try {
			
			while ((s = in.readLine()).compareTo("") != 0) {
				if(s.contains("GET")){
					String[] test = s.split(" ");
					chemin = test[1];
					break;
				}

			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		return chemin;
	}

	/* le serveur prépare une réponse en format HTTP et L'envoie au client */

	private void envoiReponse() {

		// préparation du corps de la réponse
		String corps = "";
		corps += "<html>";
		corps += "<body>";
		corps += "<p>";
		corps += "Hello tout le monde";
		corps += "</p>";
		corps += "</body>";
		corps += "</html>";

		// longueur du corps de la réponse
		int len = corps.length();

		// envoie des entêtes
		out.print("HTTP/1.0 200 OK\r\n");
		out.print("Content-Length: " + len + "\r\n");
		out.print("Content-Type: text/html\r\n\r\n"); // envoie de la ligne vide
		// envoi de la réponse
		out.print(corps);

		out.flush();

	}

	/**
	 * cette méthode ferme le flux
	 */
	void fermetureFlux() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
