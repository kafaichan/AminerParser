package com.kafaichan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.kafaichan.model.Paper;

public class Write2SQLTask{

	private String userName = null;
        private String passwd = null;
        private String sql = null;

        private Connection connection = null;
        PreparedStatement stmt_paper = null;

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
                        sql=  "insert into paper(paper_id,title,normalize_title,authors,affiliations,year,venue,normalize_venue,venue_id,abstract) values(?,?,?,?,?,?,?,?,?,?)";
                        stmt_paper = connection.prepareStatement(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}	
	
	public void insertPaper(Paper p){
		try{
			stmt_paper.setInt(1,p.getId());
                	stmt_paper.setString(2,p.getTitle());
                 	stmt_paper.setString(3,p.getNormalizeTitle());
                 	stmt_paper.setString(4,p.getAuthors());
                 	stmt_paper.setString(5,p.getAffiliations());
                 	stmt_paper.setInt(6,p.getYear()==null?0:Integer.parseInt(p.getYear()));
                 	stmt_paper.setString(7,p.getVenue());
                 	stmt_paper.setString(8,p.getNormalizeVenue());
                 	stmt_paper.setInt(9,p.getVenueId()==null?0:p.getVenueId()); //venueId=0 -> venue=null
                 	stmt_paper.setString(10,p.getPaper_abstract());
                 	stmt_paper.addBatch();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void executeBatch(){
		try{
			stmt_paper.executeBatch();
			connection.commit();
		}catch(SQLException e){
			e.printStackTrace();
		}
					
	}
}
