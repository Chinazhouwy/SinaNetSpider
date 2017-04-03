/*
	���ǳ��������쳣������ȡ�жϣ������д�main���������ݿ��ж�ȡ��δ��ȡ���û�������ȡ
*/
package pokeface.Sad.sina;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetData {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		
		//��ȡԭʼ�ڵ�ĺ����б�
		#FIXME
		//����Ҫ��ȡ���û���Ϣ����
		User user = new User();
		user.uid = "";
		user.username = "";
		user.url = "http://weibo.cn/"+user.uid;
		user = SinaNetSpider.getSocialNetwork(user);
		int followerNum = user.followers.size();
		
		//��ȡ���ݿ����Ѿ�explore�ĺ��ѵ�uid�б�
		Connection conn = dbUtil.getConn();
		PreparedStatement ps = conn.prepareStatement("select uid from user where isexplored='true'");
		ResultSet rs = ps.executeQuery();
		List<String> uidList = new ArrayList<String>();
		while(rs.next())
		{
			uidList.add(rs.getString(1));
			System.out.println(rs.getString(1));
		}
		dbUtil.close(ps, conn, rs);
		
		
		while(uidList.size()<=followerNum){
			
			try{
				
				/*int index = 0;
				List<Integer> removeList = new ArrayList<Integer>();
				for(User follower:user.followers)
				{
					System.out.println(follower.username);
					if(uidList.contains(follower.uid))
					{
						System.out.println("ɾ��"+user.followers.get(index));
						removeList.add(index);
					}
					index++;
				}
				for(int index1:removeList)
				{
					System.out.println(index1);
					System.out.println(user.followers.get(index1).username);
					user.followers.remove(index1);
				}
				System.out.println(user.followers.size());
				for(User follower:user.followers)
				{
					System.out.println(follower.username);
				}
				*/
				
				Iterator<User> it = user.followers.iterator();
				while(it.hasNext())
				{
					User follower = it.next();
					String uid = follower.uid;
					if(uidList.contains(uid))
					{
						it.remove();
					}
				}
				for(User follower:user.followers)
				{
					follower = SinaNetSpider.getSocialNetwork(follower);
					System.out.println("���ڽ�"+follower+"��Ϣд�����ݿ�");
					dbUtil.writeUserMsgIntoDB(follower);
				}
				
			}catch (IndexOutOfBoundsException e) {
				System.err.println("�����쳣");
			}
			
			conn = dbUtil.getConn();
			ps = conn.prepareStatement("select uid from user where isexplored='true'");
			rs = ps.executeQuery();
			uidList = new ArrayList<String>();
			while(rs.next())
			{
				uidList.add(rs.getString(1));
				System.out.println(rs.getString(1));
			}
			dbUtil.close(ps, conn, rs);
			
	}
			
	}

}
