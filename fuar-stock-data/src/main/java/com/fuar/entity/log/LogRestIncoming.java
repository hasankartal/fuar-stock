package com.fuar.entity.log;

import com.fuar.entity.EntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//@Document(collection = "logtable")
@Entity
@Table
@Getter
@Setter
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
