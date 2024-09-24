package com.senlainc.advertisementsystem.model.user.profile;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.message.Message;
import com.senlainc.advertisementsystem.model.order.Order;
import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.profile.raiting.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile extends AbstractModel {

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @EqualsAndHashCode.Include
    @Column(name = "first_name")
    private String firstName;

    @EqualsAndHashCode.Include
    @Column(name = "second_name")
    private String secondName;

    @EqualsAndHashCode.Include
    private String email;

    @EqualsAndHashCode.Include
    @Column(name = "date_of_birth")
    private LocalDate date;

    @EqualsAndHashCode.Include
    private boolean gender;

    @EqualsAndHashCode.Include
    @Column(name = "amount_of_money")
    private double amountOfMoney;

    @EqualsAndHashCode.Include
    @Column(name = "average_rating")
    private double averageRating;

    @EqualsAndHashCode.Include
    @ToString.Exclude
    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<Advertisement> advertisements = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Message> sentMessages = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> receivedMessages = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rating> sentRatings = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rating> receivedRatings = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}
