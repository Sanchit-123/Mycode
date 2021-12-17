import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

class student
{
	public static void main(String args[])
	{
		try
		{
			Connection co;
			Statement st;
			ResultSet rs;
			
			int ch,student_no,ans,ch1;
			String student_name;
			String dateofbirth,dateofjoin;
			Date dob,doj;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			Class.forName("org.postgresql.Driver");

			co = DriverManager.getConnection("jdbc:postgresql://localhost/postgres","postgres","*");
			st = co.createStatement();

			do
			{
				System.out.println("1:Insert\n2:Update\n3:Delete\n4:View all\n5:Search\n6Exit");
				System.out.println("Enter your choice:");

				ch=Integer.parseInt(br.readLine());

				switch(ch)
				{
					case 1:
						System.out.println("Enter Roll Number:");
						student_no = Integer.parseInt(br.readLine());
						
						System.out.println("Enter name:");
						student_name = br.readLine();
						
						System.out.println("Enter Date of Birth:");
						dateofbirth = br.readLine();
						dob=new SimpleDateFormat("dd/mm/yyyy").parse(dateofbirth);
						System.out.println(" "+ dob.toString());
						
						System.out.println("Enter Date of Join:");
						dateofjoin = br.readLine();
					    doj=new SimpleDateFormat("dd/mm/yyyy").parse(dateofjoin);
						System.out.println(" "+ doj.toString());
						
						try
						{
							st.executeUpdate("insert into student values("+student_no+","+student_name+","+dateofbirth+","+dateofjoin+")");
							System.out.println("data inserted successfully");
						}
						catch(SQLException sqle)
						{
							System.out.println("Dupicate Roll No");
						}
						break;
						
					case 2:
						System.out.println("Enter roll number:");
						student_no = Integer.parseInt(br.readLine());
						
						System.out.println("1:Update Name\n2:Update Date of Birth\n3:Update Date of Join");
						System.out.println("Enter your choice:");
						ch1 = Integer.parseInt(br.readLine());

						if(ch1 == 1)
						{
							System.out.println("Enter new name:");
							student_name = br.readLine();
							ans = st.executeUpdate("update student set name='"+student_name+"'where roll="+student_no);

							if(ans==1)
								System.out.println("Updated Successfully");
							else
								System.out.println("Roll No Not Found");
						}
						else if(ch1 == 2)
						{
							System.out.println("Enter new date of birth:");
							dateofbirth = br.readLine();
							ans = st.executeUpdate("update student set dob="+dateofbirth+" where student_no="+student_no);

							if(ans==1)
								System.out.println("Updated Successfully");
							else
								System.out.println("Roll No Not Found");
						}
						else if(ch1 == 3)
						{
							System.out.println("Enter new date of join:");
							dateofjoin = br.readLine();
							ans = st.executeUpdate("update student set doj="+dateofjoin+" where student_no="+student_no);

							if(ans==1)
								System.out.println("Updated Successfully");
							else
								System.out.println("Roll No Not Found");
						}						
						else
							System.out.println("Invalid input");
						break;									

					case 3:
						System.out.println("Enter Roll no which you want to delete:");
						student_no = Integer.parseInt(br.readLine());
						ans = st.executeUpdate("delete from student where student_no="+student_no);

						if(ans==1)
							System.out.println("Deleted Successfully");
						else
							System.out.println("Roll No Not Found");
						break;
						
					case 4:
						rs = st.executeQuery("select * from student");
						if(rs.isBeforeFirst())
						{
							while(rs.next())
							{
								System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
							}
						}
						else
							System.out.println("There is no data");
						break;

					case 5:
						System.out.println("Enter Roll No to be searched:");
						student_no = Integer.parseInt(br.readLine());
						rs = st.executeQuery("select * from student where student_no="+student_no);
						if(rs.isBeforeFirst())
						{
							rs.next();
							System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
						}
						else
						{
							System.out.println("Roll No Not Found");
						}
						break;
				}
			}
			while(ch!=6);
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("Driver not found");
		}
		catch(SQLException sqle)
		{
			System.out.println("Syntax error in SQL");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
