package com.dh.userwallet.repository;

import com.dh.userwallet.model.Transference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenceRepository extends JpaRepository<Transference,Long> {

    @Query(value="select * from transferences t where t.id_account = ?1 limit 10 order by t.date DESC", nativeQuery = true)
    List<Transference> findTransferenceByIdLimitTen(Long id_account);
}
