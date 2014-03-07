/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisster;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author hades
 */
public class ServerCuaca {
     public static void main(String[] args) throws IOException {

    

        int port = 4444;
        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) { 
            while (listening) {
	            new ServerCuacaThread(serverSocket.accept()).start();
                    
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
     }
}
  class ServerCuacaThread extends Thread {
    private Socket socket = null;
     private  ObjectOutputStream out;
     private  ObjectInputStream in;
     private BufferedReader fis = null;
     private  PesanCuaca pesan =null;
    public ServerCuacaThread(Socket socket) {
             super("ServerCuacaThread");
        this.socket = socket;
           try {
            fis = new BufferedReader(new FileReader("C:\\Users\\hades\\Documents\\NetBeansProjects\\Sisster\\src\\sisster\\WeatherForecast.txt"));
            System.out.println("Server is running");
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
       
    }
    
    public void run() {
        try{
                    System.out.println("onok client");
       in  = new ObjectInputStream(socket.getInputStream());
      try {
                pesan = (PesanCuaca) in.readObject();
                System.out.println("From client: " + pesan.getString() );    
               
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFound: " + ex.getMessage());
            } 
        out = new ObjectOutputStream(socket.getOutputStream()); 
        while(pesan.getString() !=null){
      if(pesan.getString().equals("semua")){
            
                
                
                out.writeObject(new PesanCuaca(fis.readLine()));
                out.flush();
                }
          
            
        }
        out.close();
        in.close();
        socket.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}


