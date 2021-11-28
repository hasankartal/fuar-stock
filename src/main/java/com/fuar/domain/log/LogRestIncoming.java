package com.fuar.domain.log;

import com.fuar.domain.EntityBase;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

//@Document(collection = "logtable")
@Entity
@Table
@Data
public class LogRestIncoming extends EntityBase {

    private String endpointUrl;
    private String operationType;
    private String serverIp;
    private String clientIp;
    private Date startTime;
    private Date endTime;
    private long elapsedTime;
    private String restRequestId;
    private String contentPayload;

    public LogRestIncoming() {

    }
}
