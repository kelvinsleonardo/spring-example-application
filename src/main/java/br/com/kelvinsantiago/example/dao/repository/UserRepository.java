package br.com.kelvinsantiago.example.dao.repository;

import br.com.kelvinsantiago.example.entity.User;
import br.com.kelvinsantiago.example.enums.Situation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByMail(String mail);

    boolean existsUserByMailAndIdNotLike(String mail, long id);

    boolean existsUserByCpfAndIdNotLike(String cpf, long id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE User u SET u.adm = ~u.adm WHERE u.id = :idUser")
    void changeRoleAdmFromUser(@Param("idUser") long idUser);

    @Modifying
    @Query("UPDATE User u SET u.situation = :newSituation WHERE u.id = :idUser")
    void changeSituationFromUser(@Param("idUser") long idUser, @Param("newSituation") Situation newSituation);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :idUser")
    void changePasswordFromUser(@Param("idUser") long idUser,@Param("newPassword") String newPassword);

    Page<User> findAll(Pageable pageRequest);
}
