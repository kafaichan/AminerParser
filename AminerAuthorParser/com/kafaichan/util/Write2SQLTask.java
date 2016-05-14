package com.kafaichan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.kafaichan.model.Person;

public class Write2SQLTask{

	private String userName = null;
        private String passwd = null;
        private String sql = null;

        private Connection connection = null;
        PreparedStatement stmt_person = null;

        private static final String dbDriver = "com.mysql.jdbc.Driver";
        private static final String dbURL = "jdbc:mysql://localhost:3306/aminerdata";

        public Write2SQLTask(String userName, String passwd){
                this.userName = userName;
                this.passwd = passwd;

                try {
                        Class.forName(dbDriver) ;
                } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                }

                try {

                        connection = DriverManager.getConnection(dbURL, this.userName, this.passwd);

                        connection.setAutoCommit(false);
                        sql=  "insert into author(author_id,name,normalize_name,affiliation,normalize_affiliation,pcount,ccount,hindex,pindex,upindex,keyterm) values(?,?,?,?,?,?,?,?,?,?,?)";
                        stmt_person = connection.prepareStatement(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}	
	
	public void insertPerson(Person p){
		try{
			stmt_person.setInt(1,p.getId());
                	stmt_person.setString(2,p.getName());
                 	stmt_person.setString(3,p.getNormalizeName());
                 	stmt_person.setString(4,p.getAffiliation());
                 	stmt_person.setString(5,p.getNormalizeAffiliation());
                 	stmt_person.setInt(6,p.getPublishedCnt());
                 	stmt_person.setInt(7,p.getTotalCn());
                 	stmt_person.setInt(8,p.getHindex());
                 	stmt_person.setDouble(9,p.getPindex());
                 	stmt_person.setDouble(10,p.getUPIndex());
			stmt_person.setString(11,p.getKeyterms());
                 	stmt_person.addBatch();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void executeBatch(){
		try{
			stmt_person.executeBatch();
			connection.commit();
		}catch(SQLException e){
			e.printStackTrace();
		}
					
	}
}
