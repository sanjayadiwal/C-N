import java.net.*;
import java.io.*;
import java.util.Scanner;
public class client {
	public static void main ( String args[]) throws IOException
	{
		Socket c=new Socket("192.168.56.103",2000);
		InputStream is=c.getInputStream();
		DataInputStream dis=new DataInputStream(is);
		OutputStream os=c.getOutputStream();
		DataOutputStream dout=new DataOutputStream(os);
		String st=new String(dis.readUTF());
		System.out.println(st);
		st=dis.readUTF();
		System.out.println(st);
		st="none";
		Scanner inp=new Scanner(System.in);
		String str= new String();
		//dout.writeUTF(str);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String filename;
		while (true)
		{
			str=inp.next();
			switch(str)
			{
				case "Get" :
				dout.writeUTF(str);
				System.out.println("Enter the File Name You want to Get from the server");
				filename=br.readLine();
				dout.writeUTF(filename);
				String rep=dis.readUTF();
				System.out.println(rep);
				if(rep.equals("File Found"))
				{
					FileOutputStream fout=new FileOutputStream(filename);
					int ch;
					String temp;
					do
					{
						temp=dis.readUTF();
						ch=Integer.parseInt(temp);
						if(ch!=-1)
						fout.write(ch);
					}	
					while (ch!=-1);
					System.out.println("File "+filename+" Successfully Transfered");
					System.out.println("Request For: Get/Put/Disconnect");
					fout.close();
				}
				else System.out.println("Request For: Get/Put/Disconnect");
				break;
			
				case "Put" :
				dout.writeUTF(str);
				System.out.println("Enter the File Name You want to Put on the server");
				filename=br.readLine();
				File f=new File(filename);
				if(!f.exists())
				{
					System.out.println("File not Found");
					System.out.println("Request For: Get/Put/Disconnect");
					dout.writeUTF("FN");
					
				}
				else
				{
					dout.writeUTF("F");
					dout.writeUTF(filename);
					FileInputStream fin=new FileInputStream(f);
					int ch;
					do
					{
						ch=fin.read();
						dout.writeUTF(String.valueOf(ch));
					}
					while(ch!=-1);
					fin.close();
					System.out.println("File "+filename+" Successfully Transfered");
				}
				//System.out.println(dis.readUTF());
				break;
				
				case "Disconnect" :
				dout.writeUTF(str);
				System.out.println(dis.readUTF());
				break;
				default :
				dout.writeUTF(str);
				System.out.println(dis.readUTF());
				break;
				
			}
			//st=dis.readUTF();
			//System.out.println(st);
			if (str.equals("Disconnect"))
			{
				break;
			}
		}
		dis.close();
		dout.close();
		is.close();
		c.close();
	}
}
