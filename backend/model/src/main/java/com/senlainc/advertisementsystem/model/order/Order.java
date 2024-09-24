package com.senlainc.advertisementsystem.model.order;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedEntityGraph(
        name = "order_entity_graph",
        attributeNodes = {
                @NamedAttributeNode("orderStatuses"),
        }
)
public class Order extends AbstractModel {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    @EqualsAndHashCode.Include
    private LocalDateTime date;

    @EqualsAndHashCode.Include
    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @EqualsAndHashCode.Include
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderStatusModel> orderStatuses = new ArrayList<>();
}
