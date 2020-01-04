package com.skilldistillery.filmquery.entities;

import java.util.List;

public class Film {
	// fields
	private int id;
	private String title;
	private short releaseYear;
	private String description;
	private int langId;
	private int rentDur;
	private double rate;
	private int length;
	private double repCost;
	private String rating;
	private String specialFeatures;
	private List<Actor> actors;
	
	// constructor
	public Film() {
		super();
	}
	




	

	public Film(int id, String title, String description, short releaseYear, int langId, int rentDur, double rate,
			int length, double repCost, String rating, String specialFeatures) {
		super();
		this.id = id;
		this.title = title;
		this.releaseYear = releaseYear;
		this.description = description;
		this.langId = langId;
		this.rentDur = rentDur;
		this.rate = rate;
		this.length = length;
		this.repCost = repCost;
		this.rating = rating;
		this.specialFeatures = specialFeatures;
	}







	public Film(int id, String title, short releaseYear, String description) {
		super();
		this.id = id;
		this.title = title;
		this.releaseYear = releaseYear;
		this.description = description;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Film id= ");
		builder.append(id);
		builder.append(". \ntitle= ");
		builder.append(title);
		builder.append(". \nRelease year= ");
		builder.append(releaseYear);
		builder.append(". \ndescription= ");
		builder.append(description);
		builder.append(". \nrating= ");
		builder.append(rating);
		builder.append(". \nList of actors: ");
		builder.append(actors);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((specialFeatures == null) ? 0 : specialFeatures.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (specialFeatures == null) {
			if (other.specialFeatures != null)
				return false;
		} else if (!specialFeatures.equals(other.specialFeatures))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public Film(int id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public int getRentDur() {
		return rentDur;
	}

	public void setRentDur(int rentDur) {
		this.rentDur = rentDur;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getRepCost() {
		return repCost;
	}

	public void setRepCost(double repCost) {
		this.repCost = repCost;
	}

	public void setYear(short year) {
		this.releaseYear = year;
	}

	// methods
	
}
