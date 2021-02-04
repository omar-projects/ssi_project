import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IOException {

        System.out.println("Tapez 'c' pour client ou 's' pour serveur");

        Scanner sc = new Scanner ( System.in );

        Character c = sc.next().charAt(0);

        String port;
        switch (c) {
            case 's':
                System.out.println("Saisissez le port du communication :");
                sc = new Scanner ( System.in );
                port = sc.nextLine();
                createServer(Integer.parseInt(port));
                break;
            case 'c':
                System.out.println("Saisissez l'adresse IP du serveur :");
                sc = new Scanner ( System.in );
                String ip = sc.nextLine();

                System.out.println("Saisissez le port du communication :");
                sc = new Scanner ( System.in );
                port = sc.nextLine();

                createClient(ip, Integer.parseInt(port));
                break;
            default:
                main(args);
                break;
        }

        sc.close();

    }

    /**
     * La méthode qui permet de créer un serveur, après la création le serveur est en écoute sur port
     * @param port : le port d'écoute du serveur
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void createServer(int port) throws IOException, ClassNotFoundException {
        PublicKey serverPublicKey = new PublicKey();
        PrivateKey serverPrivateKey = PrivateKey.generatePrivateKey(serverPublicKey);

        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("le serveur à bien été lancer - Port : " + port);
        Socket socket = serverSocket.accept();
        System.out.println("Serveur(Bob)  : un client a bien été connecté");
        System.out.println();

        // on récupère le flux d'entrée puis la clé publique
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        PublicKey clientPublicKey = (PublicKey) objectInputStream.readObject();
        System.out.println("Client(Alice) : "+ clientPublicKey.getN() + " " + clientPublicKey.getE());
        System.out.println();
        System.out.println("Serveur(Bob)  : La clé publique du client a bien été récupéré, je lui envoie la mienne ...");
        System.out.println();

        // on récupère le flux de sortie on écrit dedans la clé public du serveur
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(serverPublicKey);
        System.out.println("Serveur(Bob)  : "+ serverPublicKey.getN() + " " + clientPublicKey.getE());
        System.out.println();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(outputStream));

        boolean exit = false;
        while(!exit) {
            String cryptedMsg = bufferedReader.readLine();
//            afficcher le message reçu avant de le décrypté
//            System.out.println("message crypté = "+cryptedMsg);
            System.out.println("Serveur(Bob)  : Message reçu. Il sera déchiffré et affiché");
            String decryptedMsg = RSAEncryptionService.decryption(serverPrivateKey, cryptedMsg);

            System.out.println("Client(Alice) : " + decryptedMsg);
            if(decryptedMsg.equals("exit")){
                exit = true;
                continue;
            }

            System.out.println("Serveur(Bob)  : Vous m'avez envoyer " + decryptedMsg);
            printWriter.println(RSAEncryptionService.encrypt("vous m'avez envoyer " + decryptedMsg, clientPublicKey));
            printWriter.flush();
            System.out.println();
        }

        System.out.println("exit serveur");
        System.exit(0);

        socket.close();
    }

    /**
     * Création du client qui va se connecter au serveur de l'adresse donnée et envoie des message chiffré sur le port donnée
     * @param ip : adresse ip du serveur au quel le client va se connecter
     * @param port
     */
    private static void createClient(String ip, int port) {
        System.out.println("Start Client - "+ip+" - "+port);
        try {
            Socket clientSocket = new Socket(ip,port);

            // création de la paire clé publique clé privée
            PublicKey clientPublicKey = new PublicKey();
            PrivateKey clientPrivateKey = PrivateKey.generatePrivateKey(clientPublicKey);

            System.out.println("Client(Alice) : 'J'envoie ma clé public au serveur ...");
            // on récupère le flux de sortie on écrit dedans la clé public du client (Alice)
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(clientPublicKey);
            System.out.println("Client(Alice) : "+ clientPublicKey.getN() + " " + clientPublicKey.getE());
            System.out.println();


            // on récupère le flux d'entrée qui contient la clé publique du serveur (Bob)
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(outputStream));


            PublicKey serverPublicKey = (PublicKey) objectInputStream.readObject();
            System.out.println("Serveur(Bob)  : "+ serverPublicKey.getN() + " " + serverPublicKey.getE());
            System.out.println("Client(Alice) : La clé publique du serveur a bien été recupéré ");
            System.out.println();

            String msg = null;

            boolean exit = false;
            while(!exit) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Client(Alice) : ");
                msg = sc.nextLine();

                //envoi d'un message
                System.out.println("Client(Alice) : Un message chiffré a été envoyer au serveur");
                printWriter.println(RSAEncryptionService.encrypt(msg, serverPublicKey));
                printWriter.flush();

                if(msg.equals("exit")){
                    exit = true;
                    continue;
                }
                //lire un message reçu
                msg = bufferedReader.readLine();

                System.out.println("Client(Alice) : Message reçu. Il sera déchiffré et affiché");
                System.out.println("Serveur(Bob): " + RSAEncryptionService.decryption(clientPrivateKey,msg));
                System.out.println();
            }

            // close client socket
            clientSocket.close();
            System.out.println("exit client");
            System.exit(0);

        } catch (Exception e) {
            System.out.println("Erreur dans le fonctionnement du client !");
        }
    }

}
