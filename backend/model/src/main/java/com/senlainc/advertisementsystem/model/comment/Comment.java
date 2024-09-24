package com.senlainc.advertisementsystem.model.comment;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends AbstractModel {

    @EqualsAndHashCode.Include
    private String text;

    @EqualsAndHashCode.Include
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;
}
