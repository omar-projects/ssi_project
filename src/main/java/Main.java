import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IOException {

        System.out.println("Tapez 'c' pour client ou 's' pour serveur");

        Scanner sc = new Scanner ( System.in );

        Character c = sc.next().charAt(0);

        switch (c) {
            case 's':
                createServer();
                break;
            case 'c':
                createClient();
                break;
            default:
                main(args);
                break;
        }

        sc.close();

    }

    public static void createServer() throws IOException, ClassNotFoundException {
        PublicKey serverPublicKey = new PublicKey();
        PrivateKey serverPrivateKey = PrivateKey.generatePrivateKey(serverPublicKey);

        ServerSocket serverSocket = new ServerSocket(4451);

        System.out.println("le serveur à bien été lancer");
        Socket socket = serverSocket.accept();
        System.out.println("Serveur : un client a bien été connecté");

        // on récupère le flux d'entrée puis la clé publique
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        PublicKey clientPublicKey = (PublicKey) objectInputStream.readObject();
        System.out.println("Client : "+ clientPublicKey.getN() + " " + clientPublicKey.getE());

        // on récupère le flux de sortie on écrit dedans la clé public du serveur
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(serverPublicKey);
        System.out.println("Server : "+ serverPublicKey.getE() + " " + clientPublicKey.getN());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(outputStream));


        boolean exit = false;
        while(!exit) {
            String cryptedMsg = bufferedReader.readLine();
            System.out.println("apres lecture du message "+cryptedMsg);
            String decryptedMsg = RSAEncryptionService.decryption(serverPrivateKey, cryptedMsg);
            System.out.println("Client : " + decryptedMsg);
            System.out.println("Server : vous m'avez envoyer " + decryptedMsg);
            printWriter.println(RSAEncryptionService.encrypt("vous m'avez envoyer " + decryptedMsg, clientPublicKey));
            printWriter.flush();
        }

        socket.close();
    }

    private static void createClient() {

        System.out.println("Start Client");
        try {

            Socket clientSocket = new Socket("127.0.0.1",4451);

            PublicKey alicePublicKey = new PublicKey();
            PrivateKey alicePrivateKey = PrivateKey.generatePrivateKey(alicePublicKey);


            // on récupère le flux de sortie on écrit dedans la clé public d'Alice
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(alicePublicKey);



            // on récupère le flux d'entrée puis la clé publique de Bob
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(outputStream));


            PublicKey serverPublicKey = (PublicKey) objectInputStream.readObject();
            System.out.println("Client : cle publique du serveur recupere = "+ serverPublicKey.getN() + " " + serverPublicKey.getE());


            String msg = null;

            while(true) {
                Scanner sc = new Scanner(System.in);
                System.out.print("\033[1;36mAlice >\033[1;37m ");
                msg = sc.nextLine();

                if(msg.equals("exit")){
                    break;
                }

                //envoi de la chaine
                System.out.println("\033[1;36mAlice >\033[1;37m Envoi du message chiffré en cours...");
                printWriter.println(RSAEncryptionService.encrypt(msg, serverPublicKey));
                printWriter.flush();

                //lecture de la reception
                msg = bufferedReader.readLine();


                System.out.println("\033[1;36mAlice >\033[1;37m Message reçu. Déchiffrement en cours...");
                System.out.println("\033[1;33mBob >\033[1;37m " + RSAEncryptionService.decryption(alicePrivateKey,msg));
                System.out.println();
            }

            // close client socket
            clientSocket.close();
            System.out.println("Fermeture du client...");

        } catch (Exception e) {
            System.out.println("Erreur dans le fonctionnement du client !");
        }
    }

}
