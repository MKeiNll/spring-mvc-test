package com.springmvctest.model;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

/**
 * 
 * A user.
 *
 */
@Entity
@Table(name = "USER")
@Validated
public class User {

	@Id
	@Length(min = 1)
	@Column(name = "USER_NAME")
	private String name;
	@OneToMany
	@JoinTable(name = "USER_SECTOR", joinColumns = @JoinColumn(name = "USER_NAME"), inverseJoinColumns = @JoinColumn(name = "SECTOR_ID"))
	private Set<Sector> selectedSectors;
	@Column(name = "AGREED_TO_TERMS")
	private boolean agreedToTerms;

	public User() {
	}

	public User(String name, Set<Sector> selectedSectors, boolean agreedToTerms) {
		this.name = name;
		this.selectedSectors = selectedSectors;
		this.agreedToTerms = agreedToTerms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Sector> getSelectedSectors() {
		return selectedSectors;
	}

	public void setSelectedSectors(Set<Sector> selectedSectors) {
		this.selectedSectors = selectedSectors;
	}

	public boolean hasAgreedToTerms() {
		return agreedToTerms;
	}

	public void setAgreedToTerms(boolean agreedToTerms) {
		this.agreedToTerms = agreedToTerms;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Sector> selectedSectorIterator = selectedSectors.iterator();
		while (selectedSectorIterator.hasNext()) {
			Sector sector = selectedSectorIterator.next();
			builder.append(sector.getSectorName());
			if (selectedSectorIterator.hasNext()) {
				builder.append(", ");
			}
		}
		return "Name: " + name + "\nSelected sectors: " + builder.toString() + "\nAgreed to terms: " + agreedToTerms;
	}
}
