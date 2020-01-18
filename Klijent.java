import java.net.*;
import java.io.*;

public class Klijent{

  // Inicijalizacija
  private Socket uticnica = null;
  private BufferedReader input = null;
  private DataOutputStream out = null;
  private DataInputStream in = null;

  // Konstruktor za IP adresu i port
  public Klijent(String adresa, int port){

    // Uspostavljanje konekcije
    try{

      uticnica = new Socket(adresa, port);
      System.out.println("Konektovan");

      // Uzimanje unosa sa terminala
      input = new BufferedReader(new InputStreamReader(System.in));

      // Slanje izlaza na uticnicu
      out = new DataOutputStream(uticnica.getOutputStream());

      in = new DataInputStream(new BufferedInputStream(uticnica.getInputStream()));
    }
    catch(UnknownHostException u){

      System.out.println(u);
    }
    catch(IOException i){

      System.out.println(i);
    }
    
    System.out.println("Izaberite racunsku operaciju: ");
    System.out.println("~Sabiranje x+y~");
    System.out.println("~Oduzimanje x-y~");
    System.out.println("~Mnozenje x*y~");
    System.out.println("~Deljenje x/y~");
    

    // String za citanje poruke sa unosa
    String line = "";
    String buff = "";

    // Nastavi sa citanjem dok se ne pojavi unos "Over"
    while(!line.equals("Over")){

      try{
        
        System.out.print("Poruka: ");
        line = input.readLine();
        out.writeUTF(line);

        buff = in.readUTF();
        System.out.print("Od servera: ");
        System.out.println(buff);
      }
      catch(IOException i){

        System.out.println(i);
      }
    }

    // Zatvaranje konekcije
    try{

      input.close();
      out.close();
      uticnica.close();
    }
    catch(IOException i){

      System.out.println(i);
    }
  }

  public static void main(String args[]){

    Klijent klijent = new Klijent("127.0.0.1", 5000);
  }
}
