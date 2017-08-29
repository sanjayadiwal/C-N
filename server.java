import java.net.*;
import java.io.*;
public class server {
	public static void main (String args[]) throws IOException
	{
		ServerSocket s=new ServerSocket(2000);
		Socket sok=s.accept();
		OutputStream out= sok.getOutputStream();
		DataOutputStream dout=new DataOutputStream(out);
		InputStream in=sok.getInputStream();
		DataInputStream din=new DataInputStream(in);
		dout.writeUTF("You have connected to Server");
		dout.writeUTF("Requst for: Get/Put/Disconnect");
		String filename;
		while (true)
		{
			String str=new String(din.readUTF());
			switch(str)
			{
				case "Get" :
				//dout.writeUTF("You want file");
				filename=din.readUTF();
				File f = new File(filename);
				if(!f.exists())
				{
					dout.writeUTF("File Not Found");
				}
				else
				{
					dout.writeUTF("File Found");
					FileInputStream fin=new FileInputStream(filename);
					int ch=1;
					do
					{
						ch=fin.read();
						dout.writeUTF(String.valueOf(ch));
					}while (ch!=-1);
					fin.close();
				}
				break;
				
				case "Put" :
				String st=din.readUTF();
				if(st.equals("F"))
				{
					filename=din.readUTF();
					FileOutputStream fout=new FileOutputStream(filename);
					int ch;
					String temp;
					do
					{
						temp=din.readUTF();
						ch=Integer.parseInt(temp);
						if (ch!=-1) fout.write(ch);
					}
					while(ch!=-1);
				}

				//dout.writeUTF("You want to upload");
				break;
		
				case "Disconnect" :
				dout.writeUTF("Exited");
				dout.close();
				din.close();
				out.close();
				sok.close();
				System.exit(1);

				default :
				dout.writeUTF("Yoh have entered wrong");
				continue;
			}
		
		}
		

	}
}

