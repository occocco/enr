package com.enr.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Table(name = "regions")
public class Region {

    @Id
    @Column(name = "region_id")
    private Integer id;

    @Column(name = "region_name", length = 25)
    private String name;

    @OneToMany(mappedBy = "region")
    private List<Country> countries = new ArrayList<>();

}
