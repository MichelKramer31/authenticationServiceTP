package authenticationService;

import requestHandler.RequestHandler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


/**
 * Serveur UDP de base
 * @author michelkramer
 */
public class ServeurUDP extends Thread{

	/**
	 * Le port sur lequel ecoute le serveur
	 */
	private Integer port;

	/**
	 * La chaine de handler utilisé par le serveur pour traiter les requetes
	 */
	private RequestHandler rh;

	/**
	 * @param port Le port sur lequel ecoute le serveur
	 * @param rh La chaine de handler utilisé par le serveur pour traiter les requetes
	 */
	ServeurUDP(Integer port, RequestHandler rh){
		this.port = port;
		this.rh = rh;
	}

	/**
	 * La logique du thread
	 */
	 public void run() {

		 DatagramSocket socket = null;
		 try {

		 	//on ouvre le socket
			 socket = new DatagramSocket(this.port);

			 // tampon pour recevoir les données des datagrammes UDP
			 final byte[] tampon = new byte[1024];

			 // objet Java permettant de recevoir un datagramme UDP
			 DatagramPacket dgram = new DatagramPacket(tampon, tampon.length);

			 //on lance la boucle infinie qui va écouter sur le port
			 while (true) {

				 // attente et réception d'un datagramme UDP
				 socket.receive(dgram);

				 // extraction des données
				 String chaine = new String(dgram.getData(), 0, dgram.getLength());

				 //on affiche la chaine pour monitorer un peu
				 System.out.println("Chaine reçue : " + chaine);

				//on construit un objet requete et une entete à partir de notre requete et des infos de notre environement
				 Request request = new Request(new Header(socket.getLocalAddress().toString(), this.port, "UDP"), chaine);

				 //on execute notre requete
				 Result response = rh.execute(request);
				 
				 //Si le requete demandée une réponse
				 if(response.getRequest().requiereResponse()) {

					 //on va transformer et mettre le resultat dans le datagramme
					 byte[] brsp = response.getResult().getBytes();
					 dgram.setData(brsp);
					 //on envoie le datagram au client
					 socket.send(dgram);

					 // on replace la taille du tampon au max
					 // elle a été modifiée lors de la réception
					 dgram.setData(tampon);
				 }
			 }



		 } catch (Exception e) {
			 System.out.println(e.getCause());
		 }
	 }
}
