package vn.kien.event.eventbe.entity;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:37
 */
@Entity
@Table(name = "sequence_value_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SequenceValueItem implements Serializable {
    @Id
    private String seqName;
    private Integer seqId;
    private Date lastUpdatedStamp;
    private Date lastUpdatedTxStamp;
    private Date createdAt;
}
