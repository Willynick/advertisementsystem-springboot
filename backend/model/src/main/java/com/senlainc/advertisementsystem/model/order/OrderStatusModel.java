package com.senlainc.advertisementsystem.model.order;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "status_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusModel extends AbstractModel {

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @EqualsAndHashCode.Include
    @Column(name = "change_date")
    private LocalDateTime changeDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
