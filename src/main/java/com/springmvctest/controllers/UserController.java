package com.springmvctest.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvctest.dao.SectorRepository;
import com.springmvctest.dao.UserRepository;
import com.springmvctest.model.Sector;
import com.springmvctest.model.User;

/**
 * 
 * Controller for managing users. This class accesses the User & Sector
 * entities.
 * 
 */
@Controller
public class UserController {

	@Autowired
	private SectorRepository sectorRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * GET /index : Returns an appropriate view depending on given parameters.
	 * 
	 * @param name   the name of the user. Optional.
	 * @param update whether the existing user should be redirected to
	 *               'sectorSelectionView'. Optional.
	 * @param model  the holder for model attributes.
	 * @return <b>"userNameInputView"</b> if no parameters are given.
	 *         <b>"userAlreadyExistsView"</b> if user with requested name already
	 *         exists & 'update' parameter is not given or is false.
	 *         <b>"sectorSelectionView"</b> if user with requested name doesn't
	 *         exist or already exists & 'update' parameter is true.
	 * 
	 */
	@GetMapping("/index")
	public String getAppropriateView(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "update", required = false, defaultValue = "false") boolean update, Model model) {
		if (name != null) {
			model.addAttribute("name", name);
			boolean userExists = userRepository.existsById(name);
			if (update || !userExists) {
				List<Sector> sectorList = new ArrayList<Sector>();
				sectorRepository.findByParent(null).forEach(sectorList::add);
				model.addAttribute("sectors", sectorList);

				List<Sector> selectedSectorList = new ArrayList<Sector>();
				boolean agreedToTerms = false;
				if (userExists) {
					User user = userRepository.findById(name).get();
					selectedSectorList.addAll(user.getSelectedSectors());
					agreedToTerms = user.hasAgreedToTerms();
				}
				model.addAttribute("selectedSectors", selectedSectorList);
				model.addAttribute("agreedToTerms", agreedToTerms);
				return "sectorSelectionView";
			}
			return "userAlreadyExistsView";
		}
		return "userNameInputView";
	}

	/**
	 * 
	 * GET /success : Returns the success view.
	 * 
	 * @return "successView"
	 * 
	 */
	@GetMapping("/success")
	public String getSuccessView() {
		return "successView";
	}

	/**
	 * 
	 * POST /index : Saves the user.
	 * 
	 * @param name          the name of the user. Required.
	 * @param sectors       the IDs of the selected sectors. Required.
	 * @param agreedToTerms whether the user has agreed to terms. Optional, default
	 *                      is false.
	 * @return redirect to <b>"/success"</b>
	 * 
	 */
	@PostMapping("/index")
	public String saveUser(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "sectors", required = true) List<Integer> sectors,
			@RequestParam(value = "agreeToTerms", required = false, defaultValue = "false") boolean agreedToTerms) {
		Set<Sector> sectorSet = new HashSet<Sector>();
		Iterator<Sector> selectedSectorsIterator = sectorRepository.findAllById(sectors).iterator();
		while (selectedSectorsIterator.hasNext()) {
			sectorSet.add(selectedSectorsIterator.next());
		}

		if (userRepository.existsById(name)) {
			User user = userRepository.findById(name).get();
			user.setSelectedSectors(sectorSet);
			user.setAgreedToTerms(agreedToTerms);
			userRepository.save(user);
		} else {
			userRepository.save(new User(name, sectorSet, agreedToTerms));
		}
		return "redirect:/success";
	}
}
