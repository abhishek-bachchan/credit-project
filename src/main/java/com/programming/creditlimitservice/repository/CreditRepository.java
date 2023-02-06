package com.programming.creditlimitservice.repository;
import com.programming.creditlimitservice.model.Account;
import com.programming.creditlimitservice.model.CreditLimitOffer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CreditRepository extends JpaRepository<CreditLimitOffer, Long> {

    @Query("SELECT MAX(c.newLimit) FROM CreditLimitOffer c WHERE c.accountId = :accountId " +
                                                                " AND c.limitType = :limitType" +
                                                                " AND c.status = :status ")
    Double findMaxActiveLimitPerAccountByLimitType(@Param("accountId") String accountId,
                                             @Param("limitType")CreditLimitOffer.LimitType limitType,
                                             @Param("status")CreditLimitOffer.StatusType status);

    @Query("SELECT c FROM CreditLimitOffer c WHERE c.accountId = :accountId " +
                                                    " AND c.offerExpiryTime >= :activeDate " +
                                                    " AND c.status = :status")
    List<CreditLimitOffer> findByAccountIdAndActiveDate(@Param("accountId") String accountId,
                                                        @Param("activeDate") Date activeDate,
                                                        @Param("status")CreditLimitOffer.StatusType status);

    @Transactional
    @Modifying
    @Query("UPDATE CreditLimitOffer c SET c.status = :status WHERE c.id = :id")
    int updateCreditRequest(@Param("id") Long id, @Param("status") CreditLimitOffer.StatusType status);
}
