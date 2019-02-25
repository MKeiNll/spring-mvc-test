package com.springmvctest.model;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * An industry sector.
 *
 */
@Entity
@Table(name = "SECTOR")
public class Sector {

	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "SECTOR_NAME")
	private String name;
	@ManyToOne
	@JoinColumn(name = "PARENT_SECTOR")
	private Sector parent;
	@OneToMany(mappedBy = "parent")
	private List<Sector> childSectors;

	public Sector(Integer id, String name, Sector parent, List<Sector> childSectors) {
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.childSectors = childSectors;
	}

	public Sector() {
	}

	public Integer getId() {
		return id;
	}

	public void Integer(Integer id) {
		this.id = id;
	}

	public String getSectorName() {
		return name;
	}

	public void setSectorName(String name) {
		this.name = name;
	}

	public Sector getParentSector() {
		return parent;
	}

	public void setParentSector(Sector parent) {
		this.parent = parent;
	}

	public List<Sector> getChildSectors() {
		return childSectors;
	}

	public void setChildSectors(List<Sector> childSectors) {
		this.childSectors = childSectors;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Sector> childSectorIterator = childSectors.iterator();
		while (childSectorIterator.hasNext()) {
			Sector sector = childSectorIterator.next();
			builder.append(sector.getSectorName());
			if (childSectorIterator.hasNext()) {
				builder.append(", ");
			}
		}
		String parentSectorName = "null";
		if (parent != null) {
			parentSectorName = parent.getSectorName();
		}

		return "\nID: " + id + "\nName: " + name + "\nParent sector: " + parentSectorName + "\nChild sectors: "
				+ builder.toString();
	}
}
