/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radiusserver;



import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import sun.audio.AudioPlayer;

/**
 *
 * @author REVE
 */
public class RadiusServer {
    static int ServerPort=1812;
    static int ServerToClientPort=53031;
    public static int countsend=0;
    public static int countreceive=0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        System.out.println("UDP Radius Server Started Successfully");
        Thread t=new MyThread();
        t.start();

    }

    private static class MyThread extends Thread {

        public MyThread() {
        }

        @Override
        public void run() {
            try{
//                DatagramSocket ds=new DatagramSocket(ServerPort, InetAddress.getByName("localhost"));
                DatagramSocket ds=new DatagramSocket(ServerPort, InetAddress.getByName("191.96.12.12"));
                byte[] b=new byte[2048];

                DatagramPacket dp=new DatagramPacket(b, b.length);
                DatagramPacket dp2 = new DatagramPacket(b, b.length);
                int i=0;
                while(true){
                    ds.receive(dp);
                    String message=new String(dp.getData(),0,dp.getLength());
                    System.out.println(message);
//                    System.out.println(message.length()+" Received at server--> "+message);
                    int len=50;
                    byte[] data = new byte[len];
                    data=Utility.getRandomData(data, len);
                    String hexdata=Utility.bytesToHex(data);
                    data=Utility.getRandomData(data, len);
                    String hexdata1=Utility.bytesToHex(data);
                    data=Utility.getRandomData(data, len);
                    String hexdata2=Utility.bytesToHex(data);
                    int idint=i%256;
                    byte bid=(byte) idint;
                    String id=Utility.byteToHex(bid);
                    System.out.println("id ----> "+id );
                    i++;
                    
                    
                    //Backend coding
//                    String m="306302010004067075626c6963a22102013402010002010030163014060a2b060102010202010601040608003715e6bc000000"+ hexdata;
                    String m="02"+id+"0066e6c459f60763148765355164ac5994140606000000020706000000010806ac1003210906ffffff000a06000000030b097374642e7070700c06000005dc0d06000000014f0603710004501269ff0aab721668cd28256de57573b9bd01077374657665";
                    
                    byte[] b1=Utility.hexStringToByteArray(m);
              

                    dp2.setData(b1);
                    dp2.setAddress(dp.getAddress());
                    dp2.setPort(ServerToClientPort);
                    ds.send(dp2);

//                    System.out.println("------sending from server to ip:port: "+dp.getAddress()+":"+ServerToClientPort);
                    countsend+=1;
                    System.out.println("------------------->  "+countsend);
                }



            }catch(Exception e){

                e.printStackTrace();
            }
        }
        
        
    }
    
}