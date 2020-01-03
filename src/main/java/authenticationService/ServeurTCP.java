package authenticationService;

import requestHandler.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Serveur TCP de base
 * @author michelkramer
 */
public class ServeurTCP extends Thread{


	/**
	 * Le serveur de socket que l'on va ouvrir
	 */
	private ServerSocket ssg;

	/**
	 * Le port sur lequel ecoute le serveur
	 */
	private Integer port;

	/**
	 * La chaine de handler utilisé par le serveur pour traiter les requetes
	 */
	private RequestHandler rm;


	/**
	 * @param port Le port sur lequel ecoute le serveur
	 * @param rh La chaine de handler utilisé par le serveur pour traiter les requetes
	 * @throws IOException En cas de problème avec le ServerSocket
	 */
	public ServeurTCP(Integer port, RequestHandler rh) throws IOException {
		//on ouvre le port pour le server
		this.ssg = new ServerSocket(port);
		this.rm = rh;
	}

	/**
	 * La logique du thread
	 */
	public void run() {

		while(true) {
			try {
				// On attend une connexion puis on l'accepte
				Socket sss = ssg.accept();
				ClientManager cm = new ClientManager(this.rm, sss);
				// ca demarre
				cm.start();
			}catch (IOException ex){
				System.out.println("Impossible d'ouvrir la connection"+ex.getCause());
			}

		}
	}
}
