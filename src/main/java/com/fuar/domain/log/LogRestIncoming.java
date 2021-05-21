package com.fuar.domain.log;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "logtable")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class LogRestIncoming {

    @Id
    private Long id;

    private String endpointUrl;
    private String operationType;
    private String serverIp;
    private String clientIp;
    private Date startTime;
    private Date endTime;
    private long elapsedTime;
    private String restRequestId;
    private String contentPayload;

}
