import java.io.*;
import java.net.*;

public class EmetteurMulticast {
    public static void main(String[] args) {
        try {
            // Création d'un socket multicast sur le port 4444
            InetAddress groupe = InetAddress.getByName("230.0.0.0");
            MulticastSocket socket = new MulticastSocket(4444);
            socket.joinGroup(groupe);

            // Récupération du nom du fichier à envoyer
            String nomFichier = "tp3.txt";
            File file = new File(nomFichier);

            // Envoi du nom de fichier aux récepteurs
            byte[] nomFichierBytes = nomFichier.getBytes();
            DatagramPacket packet = new DatagramPacket(nomFichierBytes, nomFichierBytes.length, groupe, 4444);
            socket.send(packet);

            // Envoi du contenu du fichier
            FileInputStream fileStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileStream.read(buffer)) != -1) {
                packet = new DatagramPacket(buffer, bytesRead, groupe, 4444);
                socket.send(packet);
            }
            fileStream.close();

            // Fermeture du socket
            socket.leaveGroup(groupe);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
