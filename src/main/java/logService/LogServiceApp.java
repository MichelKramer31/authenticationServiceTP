package logService;

import authenticationService.ServeurTCP;
import requestHandler.LogRequestHandler;
import requestHandler.RequestHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;

public class LogServiceApp {

    public static void main(String[] args) {

        //Le port sur lequel le serveur TCP qui gère les log va écouter
        final Integer TCPLogPort = 3244;


        //on essai de lancer le serveur de log
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier json", "logService", "json");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Les logs seront placés dans le fichier suivant : " +
                    chooser.getSelectedFile().getName());
        }

        // on prépare le handler qui traite les logs
        RequestHandler logHandler = new LogRequestHandler(chooser.getSelectedFile().getPath());

        try {
            // on lance le serveur de log avec le fichier selectionné par l'utilisateur
            ServeurTCP tcpLog = new ServeurTCP(TCPLogPort, logHandler);
            tcpLog.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
