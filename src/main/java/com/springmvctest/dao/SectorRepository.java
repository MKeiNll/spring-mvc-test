package com.springmvctest.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springmvctest.model.Sector;

/**
 * 
 * Spring Data JPA repository for the Sector entity.
 *
 */
@Repository
public interface SectorRepository extends CrudRepository<Sector, Integer> {

	/**
	 * 
	 * Finds all child sectors by their parent.
	 * 
	 * @param parentSector the name of the parent sector.
	 * @return child sectors.
	 * 
	 */
	List<Sector> findByParent(String parentSector);
}
