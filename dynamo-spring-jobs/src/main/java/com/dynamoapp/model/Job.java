package com.dynamoapp.model;

public class Job {
    private String id;
    private String name;
    private String dateApplied;
    private String result;

    // Constructor, getters, and setters

    public Job(String id, String name, String dateApplied, String result) {
        this.id = id;
        this.name = name;
        this.dateApplied = dateApplied;
        this.result = result;
        
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(String dateApplied) {
        this.dateApplied = dateApplied;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
