package com.senlainc.advertisementsystem.dao.old;

import java.time.LocalDateTime;

public interface OrderRepositoryCustom {

    Double getTotalPrice(Long id);
    Double getAmountOfMoneyEarnedInTime(Long userId, LocalDateTime from, LocalDateTime to);
    Long getNumberCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to);
}
