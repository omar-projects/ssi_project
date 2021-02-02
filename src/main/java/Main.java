import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IOException {

        System.out.println("Tapez c pour client ou s pour serveur");



        ServerSocket serverSocket = new ServerSocket(4451);

        System.out.println("le serveur à bien été lancer");
        Socket socket = serverSocket.accept();
        System.out.println("Serveur : un client a bien été connecté");

        // on récupère le flux d'entrée puis la clé publique
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        PublicKey clientPublicKey = (PublicKey) objectInputStream.readObject();
        System.out.println("Serveur : clef publique recupere = "+ clientPublicKey.getN() + " " + clientPublicKey.getE());

        PublicKey serverPublicKey = new PublicKey();
        // on récupère le flux de sortie on écrit dedans la clé public du serveur
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(serverPublicKey);


        while(true) {
            String clientMsg =
        }

    }

}
