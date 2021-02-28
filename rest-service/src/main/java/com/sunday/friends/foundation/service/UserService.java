package com.sunday.friends.foundation.service;

import com.sunday.friends.foundation.model.Family;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.repository.FamilyRepository;
import com.sunday.friends.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @PersistenceContext
	private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyRepository familyRepository;

    public List<Users> listAll(){
        return userRepository.findAll();
    }

    public boolean updateFamilyLink(Integer userId, Integer familyId){
        Optional<Users> usersOptional = userRepository.findById(userId);
        usersOptional.ifPresent((Users result) -> {
                result.setFamilyId(familyId);
                userRepository.save(result);
        });

        return true;
    }

    public List<Users> getDeactivateList(String searchQuery, Integer offset, Integer limit) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);

        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("active"), false));
        if (null != searchQuery && !searchQuery.isEmpty() && "null" != searchQuery) {
            criteriaQuery.where(builder.like(root.get("email"),"%"+searchQuery+"%"));
        }
        TypedQuery typedQuery = em.createQuery(criteriaQuery);
        if (null != offset && null != limit) {
            typedQuery.setFirstResult(offset);
            typedQuery.setMaxResults(limit);
        }
        return typedQuery.getResultList();
    }
    public List<Users> getTotalList(String searchQuery, Integer offset, Integer limit) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);

        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("admin"), false));
        criteriaQuery.where(builder.equal(root.get("active"), true));
        if (null != searchQuery && !searchQuery.isEmpty() && "null" != searchQuery) {
            criteriaQuery.where(builder.like(root.get("email"),"%"+searchQuery+"%"));
        }
        TypedQuery typedQuery = em.createQuery(criteriaQuery);
        if (null != offset && null != limit) {
            typedQuery.setFirstResult(offset);
            typedQuery.setMaxResults(limit);
        }
        List<Users> list = typedQuery.getResultList();

        Iterator<Users> itr = list.iterator();
        while (itr.hasNext()) {
            Users user = itr.next();
            if (user.isAdmin()) {
                itr.remove();
            }
            if(!user.isActive()){
                itr.remove();
            }
        }
        return list;
    }

    public List<Users> getFamilyList(Integer familyId, String searchQuery, Integer offset, Integer limit) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);

		Root<Users> root = criteriaQuery.from(Users.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get("familyId"),familyId));
        criteriaQuery.where(builder.equal(root.get("admin"), false));
        criteriaQuery.where(builder.equal(root.get("active"), true));
        if (null != searchQuery && !searchQuery.isEmpty() && "null" != searchQuery) {
            criteriaQuery.where(builder.like(root.get("email"),"%"+searchQuery+"%"));
        }
        TypedQuery typedQuery = em.createQuery(criteriaQuery);
        if (null != offset && null != limit) {
            typedQuery.setFirstResult(offset);
            typedQuery.setMaxResults(limit);
        }

        List<Users> list = typedQuery.getResultList();

        Iterator<Users> itr = list.iterator();
        while (itr.hasNext()) {
            Users user = itr.next();
            if (user.isAdmin()) {
                itr.remove();
            }
            else if(user.getFamilyId() != familyId) {
                itr.remove();
            }
        }
        return list;
    }

    @Transactional
    public Users onboardUser(Users user) {
//        em.createNativeQuery("INSERT INTO Users ( name,  email,  familyId,  imageUrl, balance, isAdmin) VALUES (?,?,?,?,?,?)")
//                .setParameter(1, user.getName())
//                .setParameter(2, user.getEmail())
//                .setParameter(3, user.getFamilyId())
//                .setParameter(4, user.getImageUrl())
//                .setParameter(5,0)
//                .setParameter(6, false)
//                .executeUpdate();

        user.setBalance(0.0f);
        user.setAdmin(false);
        userRepository.save(user);
        userRepository.flush();
        return user;
//        Integer userId = user.getUserId();
//        Optional<Users> usersOptional = userRepository.findById(userId);
//        return usersOptional;
    }

    public Float getBalance(Integer userId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);

        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("userId"),userId));
        List<Users> list = em.createQuery(criteriaQuery).getResultList();
        return list.get(0).getBalance();
    }

    public void updateBalance(Integer userId, Float balanceAfterAction) {
        Optional<Users> usersOptional = userRepository.findById(userId);
        usersOptional.ifPresent((Users result) -> {
            result.setBalance(balanceAfterAction);
            userRepository.save(result);
        });
    }

    public Users getUser(String email) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);

        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("email"),email));
        List<Users> list = em.createQuery(criteriaQuery).getResultList();
        if(list.size() == 0)
            return null;
        return list.get(0);
    }

    public Users getUser(Integer userId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);

        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("userId"),userId));
        List<Users> list = em.createQuery(criteriaQuery).getResultList();
        if(list.size() == 0)
            return null;
        return list.get(0);
    }

    public boolean deactivateUser(Integer userId, String deactivate) {
        Optional<Users> usersOptional = userRepository.findById(userId);
        usersOptional.ifPresent((Users result) -> {
            if(deactivate.equals("false"))
                result.setActive(true);
            else
                result.setActive(false);
            userRepository.save(result);
        });

        return true;
    }

    public void updateUser(Integer userId, String name, String email, Integer familyId) {
        Optional<Users> usersOptional = userRepository.findById(userId);
        usersOptional.ifPresent((Users result) -> {
            if(name != null && name.length() != 0)
                result.setName(name);
            if(email != null && email.length() != 0)
                result.setEmail(email);
            if(familyId != null)
                result.setFamilyId(familyId);

            userRepository.save(result);
        });
    }

    public void deleteUser(Integer userId) {
//        Users user = getUser(userId);
//        em.remove(user);
//        em.flush();
//        em.clear();
//        Query query = (Query) em.createNativeQuery("DELETE FROM USERS WHERE ID = " + userId);
//        query.executeUpdate();
          userRepository.deleteById(userId);
    }


}
