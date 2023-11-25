package com.useclient.zenvironment.model.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exchange {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String receiverId;
  private String description;

  @ManyToOne
  private Garden garden;

}
