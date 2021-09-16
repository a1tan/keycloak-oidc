package com.example.keycloakuserstore.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.sql.Date;
import java.util.UUID;

@NamedQueries({
        @NamedQuery(name="getUserByUsername", query="select u from User u where u.username = :username and u.DURUM = 1"),
        @NamedQuery(name="getUserByEmail", query="select u from User u where u.email = :email and u.DURUM = 1"),
        @NamedQuery(name="getUserCount", query="select count(id) from User u where u.DURUM = 1"),
        @NamedQuery(name="getAllUsers", query="select u from User u where u.DURUM = 1"),
        @NamedQuery(name="searchForUser", query="select u from User u where " +
                "( lower(u.username) like :search or u.email like :search )  and u.DURUM = 1 order by u.username"),
})
@Entity
@Table(name = "AGR", schema="USERKOD")
@Data
@Accessors(chain = true)
public class User {
    // @Id
    // @GeneratedValue
    @Id
    @Column(unique = true, length = 15, name = "CAGR")
    private String id;

    @Column(length = 9)
    private String CAGRTYP;

    @Column(length = 9)
    private String CAGRTYPIMM;

    @Column(length = 20)
    private String ETAT;

    private Date ETATDATE;

    @Column(length = 60, name = "LAGR")
    private String firstName;

    @Column(length = 10)
    private String LAGRCDP;

    @Column(length = 15)
    private String LAGRFAX;

    @Column(length = 300)
    private String LAGRRUE;

    @Column(length = 15)
    private String LAGRTEL;

    @Column(length = 15)
    private String LAGRTLX;

    @Column(length = 35)
    private String LAGRVIL;

    @Column(length = 15)
    private String NAGRIMM;

    @Column(length = 15)
    private String PERIMM;

    @Column(length = 150)
    private String FIRMNAME;

    @Column(length = 100)
    private String ADRESS;

    @Column(length = 18)
    private String TIMESTAMP;

    @Column(length = 9)
    private String EDICODE;

    @Column(length = 16)
    private String SICNO;

    @Column(length = 10)
    private String CAGRPASS;

    @Column(length = 15)
    private String CAGRPFL;

    @Column(length = 9)
    private String CAGRNIV;

    @Column(length = 9)
    private String CAGRPREF;

    @Column(length = 350)
    private String LAGRCOM;

    @Column(length = 16)
    private String CAGRVZN;

    @Column(length = 2048)
    private String CAGRCRY;

    @Column(length = 2048)
    private String CAGRCRYUPD;

    @Column(length = 3)
    private String CAGRCRYFLG;

    @Column(length = 9)
    private String CAGRBUR;

    @Column(length = 5)
    private String MUHKOD;

    @Column(length = 11)
    private String TCKIMLIKNO;

    private Integer IBUR;

    @Column(length = 110, name = "HASHAGR")
    private String password;

    private Integer DURUM;

    @Column(length = 4000)
    private String LANNESOYAD;

    @Column(length = 50)
    private String LCEPTEL;

    @Column(length = 50)
    private String LAGRUNV;

    @Column(length = 25)
    private String LAGRBABAD;

    @Column(length = 4000)
    private String LAGRSORU;

    @Column(length = 4000)
    private String LAGRCEVAP;

    private Date SIFREOLUSUMTARIH;

    @Column(length = 50)
    private String LSTATUS;

    @Column(length = 11, name = "IAGR")
    private String username;

    @Column(length = 15)
    private String OLDCAGR;

    @Column(length = 100, name = "EPOSTA")
    private String email;

    @Column(length = 100)
    private String IZINBELGENO;
    // @Column(unique = true)
    // private String username;
    // @Column(unique = true)
    // private String email;
    // private String password;
    // private String phone;
}
