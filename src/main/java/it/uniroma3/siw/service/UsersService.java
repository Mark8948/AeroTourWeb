package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The UsersService handles logic for the Users entity.
 */
@Service
public class UsersService {

    @Autowired
    protected UsersRepository usersRepository;

    /**
     * This method retrieves a Users entity from the DB based on its ID.
     * 
     * @param id the id of the Users to retrieve
     * @return the retrieved Users entity, or null if not found
     */
    @Transactional
    public Users getUsers(Long id) {
        Optional<Users> result = this.usersRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a Users entity in the DB.
     * 
     * @param users the Users entity to save
     * @return the saved Users entity
     * @throws DataIntegrityViolationException if a Users with the same CF already exists
     */
    @Transactional
    public Users saveUsers(Users users) {
        return this.usersRepository.save(users);
    }

    /**
     * This method retrieves all Users entities from the DB.
     * 
     * @return a List containing all Users entities
     */
    @Transactional
    public List<Users> getAllUsers() {
        List<Users> result = new ArrayList<>();
        Iterable<Users> iterable = this.usersRepository.findAll();
        for (Users u : iterable) {
            result.add(u);
        }
        return result;
    }
}
