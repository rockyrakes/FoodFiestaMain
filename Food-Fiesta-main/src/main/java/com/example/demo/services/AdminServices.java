package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;

@Component
public class AdminServices
{
	@Autowired
	private AdminRepository adminRepository;
	

	public List<Admin>getAll()
	{
		 List<Admin> admins = (List<Admin>)this.adminRepository.findAll();
		 return admins;
	}

	public Admin getAdmin(int id)
	{
		Optional<Admin> optional = this.adminRepository.findById(id);
		return optional.orElse(null);
	}

	public void update(Admin admin, int id)
	{
		admin.setAdminId(id);
		Optional<Admin> existingOpt = this.adminRepository.findById(id);
		if (existingOpt.isPresent()) {
			Admin existingAdmin = existingOpt.get();
			if (admin.getAdminPassword() == null || admin.getAdminPassword().trim().isEmpty()) {
				admin.setAdminPassword(existingAdmin.getAdminPassword());
			}
		}
		this.adminRepository.save(admin);
	}
	
	public void delete(int id)
	{
		this.adminRepository.deleteById(id);
	}

	public void addAdmin(Admin admin)
	{
		this.adminRepository.save(admin);
	}

	public boolean validateAdminCredentials(String email,String password)
	{
		Admin admin=adminRepository.findByAdminEmail(email);
		if(admin!=null && admin.getAdminPassword().equals(password))
		{
			return true;
		}
		return false;
	}
}