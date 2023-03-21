import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Recepteur {
    public static void main(String[] args) throws IOException {
        // Adresse multicast et port
        InetAddress group = InetAddress.getByName("230.0.0.0");
        int port = 4444;

        // Création de la socket multicast
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        byte[] buffer = new byte[1024];

        // Réception des données
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String filename = new String(packet.getData(), 0, packet.getLength());

        // Création d'un fichier avec le même nom que le fichier reçu
        FileOutputStream fileStream = new FileOutputStream("C:\\Users\\imadb\\OneDrive\\Bureau\\Etudes\\M1-S2\\Réseau\\Q2\\Recepteur\\" + filename);

            
            // Réception des paquets UDP et écriture dans le fichier de destination
            while (true) {
                socket.receive(packet);
                fileStream.write(packet.getData(), 0, packet.getLength());
                if (packet.getLength() < buffer.length) {
                    break; // Fin de transmission
                }
            }

        fileStream.close();
        System.out.println("Fichier reçu : " + filename);
    }
}
