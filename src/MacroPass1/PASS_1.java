package MacroPass1;
import java.io.*;
class arglist {
	String argname;
	arglist(String argument) {
		this.argname=argument;
	}
}

class mnt {
	String name;
	int addr;
	int arg_cnt;
	mnt(String nm, int address)
	{
		this.name=nm;
		this.addr=address;
		this.arg_cnt=0;
	}
}


class mdt {
	String stmnt;
	public mdt() {
		stmnt="";
	}
}

public class PASS_1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br1=new BufferedReader(new FileReader("src\\MacroPass1\\input.txt"));
		BufferedWriter bw1=new BufferedWriter(new FileWriter("src\\MacroPass1\\Output1.txt"));
	 	String line;
	 	mdt[] MDT=new mdt[20];
	 	mnt[] MNT=new mnt[4];
	 	arglist[] ARGLIST = new arglist[10]; 
	 	boolean macro_start=false,macro_end=false,fill_arglist=false,first = true,start = false;
	 	int mdt_cnt=0,mnt_cnt=0,arglist_cnt=0;
	 	while((line = br1.readLine())!=null)
	 	{
	 		line=line.replaceAll(",", " ");
	 		String[] words=line.split("\\s+");
	 		MDT[mdt_cnt] = new mdt();
	 		String stmnt = "";
			 if(line.contains("START")) start = true;
	 		for(int i=0;i<words.length;i++)
	 		{
	 			if(line.contains("MEND"))
	 			{
	 				MDT[mdt_cnt++].stmnt = "\t"+words[i];
	 				macro_end = true;
	 			}
	 			if(line.contains("MACRO"))
	 			{
					 first = true;
	 				macro_start = true;
	 				macro_end = false;
	 			}
	 			else if(!macro_end)
	 			{
	 				if(macro_start)
		 			{
		 				MNT[mnt_cnt++]=new mnt(words[i],mdt_cnt);
		 				macro_start=false;
		 				fill_arglist=true;
		 			}
		 			if(fill_arglist)
		 			{
		 				while(i<words.length)
		 				{

		 					if(words[i].matches("&[a-zA-Z]+")||words[i].matches("&[a-zA-Z]+[0-9]+")) {
								if(first) {
									MDT[mdt_cnt].stmnt += "\t" + words[i];
									first = false;
								}
								else MDT[mdt_cnt].stmnt += "\t," + words[i];
								ARGLIST[arglist_cnt++] = new arglist(words[i]);
							}
						    else MDT[mdt_cnt].stmnt = MDT[mdt_cnt].stmnt+ "\t" + words[i];
							stmnt +="\t"+ words[i];
		 					i++;
		 				}
		 				fill_arglist=false;
		 			}
		 			else {
		 				if(words[i].matches("[a-zA-Z]+") || words[i].matches("[a-zA-Z]+[0-9]+")||words[i].matches("[0-9]+")) {
		 					MDT[mdt_cnt].stmnt +="\t" + words[i];
		 					stmnt += "\t"+ words[i];
		 				}
		 				if(words[i].matches("&[a-zA-Z]+") || words[i].matches("&[a-zA-Z]+[0-9]+"))
		 				{
		 					for(int j=0;j<arglist_cnt;j++)
		 						if(words[i].equals(ARGLIST[j].argname)) {
									 if(i!=1)  MDT[mdt_cnt].stmnt += "\t,#"+(j+1);
									 else MDT[mdt_cnt].stmnt += "\t#"+(j+1);
		 							stmnt += "\t#"+(j+1);
		 						}
		 				}
		 			}
	 			}
				else if(!line.contains("MEND"))
					bw1.write(words[i]+"\t");
	 		}
			if(start) bw1.write("\n");
	 		if(stmnt!="" && !macro_end)
	 			mdt_cnt++;
	 	}
	 	br1.close();
		bw1.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter("src\\MacroPass1\\MNT.txt"));
		bw.write("INDEX\tNAME\tMDTC\n");
		for(int i=0;i<mnt_cnt;i++)
		{
			bw.write(" "+(i+1)+"\t\t"+MNT[i].name+"\t"+MNT[i].addr+"\n");
		}
		bw.close();

		
		bw1=new BufferedWriter(new FileWriter("src\\MacroPass1\\ARG.txt"));
		bw1.write("INDEX\tNAME\n");
		for(int i=0;i<arglist_cnt;i++)
		{
			bw1.write(" "+(i+1)+"\t\t"+ARGLIST[i].argname+"\n");
		}
		bw1.close();

		bw1=new BufferedWriter(new FileWriter("src\\MacroPass1\\MDT.txt"));
		bw1.write("INDEX\t\tSTATEMENT\n");

		for(int i=0;i<mdt_cnt;i++)
		{
			bw1.write(" "+(i+1)+"\t\t"+MDT[i].stmnt+"\n");
		}
		bw1.close();
	}
}
