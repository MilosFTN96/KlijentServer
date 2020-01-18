import java.net.*;
import java.io.*;

public class Server{

  // Inicijalizacija
  private Socket uticnica = null;
  private ServerSocket server = null;
  private DataInputStream in = null;
  private DataOutputStream out = null;

  // Konstruktor sa portom
  public Server(int port){

    // Zapocni server i cekaj konekciju
    try{

      server = new ServerSocket(port);
      System.out.println("Server zapoceo...");

      System.out.println("Cekanje klijenta...");

      uticnica = server.accept();
      System.out.println("Klijent prihvacen");

      // Uzimanje unosa od strane klijenta
      in = new DataInputStream(new BufferedInputStream(uticnica.getInputStream()));

      out = new DataOutputStream(uticnica.getOutputStream());

      String line = "";
      String znakovi; 
      String[] brojevi;
      char znak;
      float prvi;
      float drugi;
      float rez = 0;

      // Citanje poruke od klijenta dok ne bude "Over"
      while(!line.equals("Over")){

        try{

          System.out.print("Od klijenta: ");
          line = in.readUTF();
          System.out.println(line);
          brojevi = line.split("[\\+\\-\\*\\/]");
          znakovi = line.replaceAll("\\d+(\\.\\d+)?","");
          prvi = Float.valueOf(brojevi[0]);
          drugi = Float.valueOf(brojevi[1]);
          znak = znakovi.charAt(0);

          if(znak == '+'){

            rez = prvi + drugi;
          }

          if(znak == '-'){

            rez = prvi - drugi;
          }

          if(znak == '*'){

            rez = prvi * drugi;
          }

          if(znak == '/'){

            rez = prvi / drugi;
          }

          out.writeUTF(String.valueOf(rez));
          System.out.println("Rezultat operacije: " + rez);

        }
        catch(IOException i){

          System.out.println(i);
        }
      }

      System.out.println("Zatvaranje konekcije...");

      uticnica.close();
      in.close();
    }
    catch(IOException i){

      System.out.println(i);
    }
  }

  public static void main(String args[]){

    Server server = new Server(5000);
  }
}
