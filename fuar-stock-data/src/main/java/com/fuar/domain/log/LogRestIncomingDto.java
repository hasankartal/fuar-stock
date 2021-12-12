package com.fuar.domain.log;

import lombok.Data;

import java.util.Date;

@Data
public class LogRestIncomingDto {

    private Long id;
    private String endpointUrl;
    private String httpType;
    private String operationType;
    private String serverIp;
    private String clientIp;
    private Date startTime;
    private Date endTime;
    private long elapsedTime;
    private String restRequestId;
    private String contentPayload;
}
