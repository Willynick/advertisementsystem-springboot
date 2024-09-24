package com.senlainc.advertisementsystem.model.advertisement;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.category.Category;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "advertisements")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Advertisement extends AbstractModel {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @EqualsAndHashCode.Include
    private String title;
    @EqualsAndHashCode.Include
    private String description;
    @EqualsAndHashCode.Include
    private String manufacturer;

    @EqualsAndHashCode.Include
    @Column(name = "product_condition")
    @Enumerated(EnumType.STRING)
    private ProductCondition productCondition;

    @EqualsAndHashCode.Include
    @Column(name = "phone_number")
    private String phoneNumber;

    @EqualsAndHashCode.Include
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @EqualsAndHashCode.Include
    @Column(name = "change_date")
    private LocalDateTime changeDate;

    @EqualsAndHashCode.Include
    private double price;

    @EqualsAndHashCode.Include
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AdvertisementStatus advertisementStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ToString.Exclude
    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private List<Purchase> purchases = new ArrayList<>();
}
