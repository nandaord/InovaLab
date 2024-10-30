package com.cesar.InovaLab.InovaLab.models;

import jakarta.persistence.*;

@Entity
public class Iniciativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}


