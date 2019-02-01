package com.cham.hazelcastmapstoreloader.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Trade implements Serializable {
    private String id;
    private String type;
    private Date date;
    private String payLoad;
}
