package com.kafaichan.model; 

public class Author2Paper{
	private Integer author_id;
	private Integer paper_id; 
	private Integer id; 

	public Integer getId(){ return id; }
        public Integer getAuthorId(){ return author_id; }
	public Integer getPaperId(){ return paper_id; }

        public Author2Paper(String id, String author_id, String paper_id){
		this.id = Integer.parseInt(id);
		this.author_id = Integer.parseInt(author_id);
		this.paper_id = Integer.parseInt(paper_id);
	}
	
}
